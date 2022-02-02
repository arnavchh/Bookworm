package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;
	Random random = new Random();
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	public void setOrderDao(OrderDao orderDao) { this.orderDao = orderDao; }
	public void setLineItemDao(LineItemDao lineItemDao) { this.lineItemDao = lineItemDao; }
	public void setCustomerDao(CustomerDao customerDao) { this.customerDao = customerDao; }

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(customerForm.getCcExpiryMonth(),customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}

	}
	private Date getDate(String monthString, String yearString) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(monthString);
		calendar.set(Calendar.MONTH, Integer.parseInt(monthString)-1);
		calendar.set(Calendar.YEAR, Integer.parseInt(yearString));
		Date date = calendar.getTime();
		return date;
	}
	private int generateConfirmationNumber(){
		return random.nextInt(999999999);
	}

	private void validateCustomer(CustomerForm customerForm) {



		if (isNameInvalid(customerForm.getName())) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}
		if(isPhoneInvalid(customerForm.getPhone())){
			throw new ApiException.InvalidParameter("Invalid phone field");
		}
		if(isAddressInvalid(customerForm.getAddress())){
			throw new ApiException.InvalidParameter("Invalid address field");
		}
		if(isEmailInvalid(customerForm.getEmail())) {
			throw new ApiException.InvalidParameter("Invalid email id");
		}
		if(isCreditCardInvalid(customerForm.getCcNumber()))
		{
			throw new ApiException.InvalidParameter("Credit card number is invalid");
		}

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");

		}
	}

	private boolean isNameInvalid(String name)
	{
		if(name==null) return true;
		if(name.equals("")) return true;
		if(name.length()<4) return true;
		if(name.length()>45) return true;
		return false;
	}
	private boolean isAddressInvalid(String address){
		if(address==null) return true;
		if(address.equals("")) return true;
		if(address.length()<4) return true;
		if(address.length()>45) return true;
		return false;
	}
	private boolean isPhoneInvalid(String phone){
		if(phone==null) return true;
		if(phone.equals("")) return true;
		phone = phone.replaceAll(" ","");
		phone = phone.replaceAll("-","");
		phone = phone.replaceAll("\\(","");
		phone = phone.replaceAll("\\)","");
		if(!phone.matches("[\\d]+")) return true;
		if(phone.length()!=10) return true;
		return false;
	}
	private boolean isEmailInvalid(String emailID) {
		if(emailID==null) return true;
		if(emailID.equals("")) return true;
		if(emailID.contains(" ")) return true;
		if(!emailID.contains("@")) return true;
		if((emailID.charAt(emailID.length()-1)) == '.') return true;
		return false;
	}
	private boolean isCreditCardInvalid(String creditCardNumber){
		if(creditCardNumber==null) return true;
		if(creditCardNumber.equals("")) return true;
		creditCardNumber = creditCardNumber.replaceAll("-","");
		creditCardNumber = creditCardNumber.replaceAll(" ","");
		if(creditCardNumber.length()<14) return true;
		if(creditCardNumber.length()>16) return true;
		return false;
	}
	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		if(ccExpiryMonth==null)
			return true;
		if(ccExpiryMonth.equals(""))
			return true;
		if(ccExpiryYear==null)
			return true;
		if(ccExpiryYear.equals(""))
			return true;
		try {
			int expiryYear = Integer.parseInt(ccExpiryYear);
			int expiryMonth = Integer.parseInt(ccExpiryMonth);
			if (expiryMonth < 1 || expiryMonth > 12)
				return true;
			String[] monthYear = YearMonth.now().toString().split("-");
			int month = Integer.parseInt(monthYear[1]);
			int year = Integer.parseInt(monthYear[0]);
			if (expiryYear < year)
				return true;
			else if (expiryYear == year) {
				return expiryMonth < month;
			}
			return false;
		}
		catch (NumberFormatException e)
		{
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if(databaseBook.getPrice()!=item.getBookForm().getPrice()) {
				throw new ApiException.InvalidParameter("Invalid price");
			}
			if(databaseBook.getCategoryId()!=item.getBookForm().getCategoryId()){
				throw new ApiException.InvalidParameter("Invalid category");
			}
		});
	}
	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection,customerOrderId,item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}


}

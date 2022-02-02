import Vue from "vue";
import Vuex from "vuex";
import ApiService from "@/services/ApiService";
import { ShoppingCart } from "@/models/ShoppingCart";

Vue.use(Vuex);
export const CART_STORAGE_KEY = "cart";
export const ORDER_STORAGE_KEY = "orderDetails";
export default new Vuex.Store({
  state: {
    categories: [],
    selectedCategoryName: "",
    selectedCategoryBooks: [],
    cart: new ShoppingCart(),
    orderDetails: null,
  },
  mutations: {
    SET_CATEGORIES(state, newCategories) {
      state.categories = newCategories;
    },
    SET_CATEGORY_NAME(state, categoryName) {
      state.selectedCategoryName = categoryName;
    },
    SET_BOOKS(state, books) {
      state.selectedCategoryBooks = books;
    },
    ADD_TO_CART(state, book) {
      state.cart.addItem(book, 1);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    UPDATE_CART(state, { book, quantity }) {
      state.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_CART(state, shoppingCart) {
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(shoppingCart));
      let newCart = new ShoppingCart();
      shoppingCart.items.forEach((item) => {
        newCart.addItem(item.book, item.quantity);
      });
      state.cart = newCart;
    },
    CLEAR_CART(state) {
      state.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_ORDER_DETAILS(state, orderDetails) {
      state.orderDetails = orderDetails;
      sessionStorage.setItem("orderDetails", JSON.stringify(orderDetails));
    },
    CLEAR_ORDER_DETAILS(state) {
      state.orderDetails = null;
      sessionStorage.setItem(
        "orderDetails",
        JSON.stringify(this.state.orderDetails)
      );
    },
  },
  actions: {
    fetchCategories(context) {
      ApiService.fetchCategories()
        .then((categories) => {
          console.log("Data: ", categories);
          context.commit("SET_CATEGORIES", categories);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
        });
    },
    setCategoryName(context, categoryName) {
      context.commit("SET_CATEGORY_NAME", categoryName);
    },
    getBooksByCategory(context) {
      console.log("Calling books request api");
      return ApiService.fetchBooksByCategory(this.state.selectedCategoryName)
        .then((books) => {
          console.log("Data: ", books);
          context.commit("SET_BOOKS", books);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
          throw reason;
        });
    },
    addToCart(context, book) {
      context.commit("ADD_TO_CART", book);
    },
    updateCart(context, { book, quantity }) {
      context.commit("UPDATE_CART", { book, quantity });
    },
    clearCart(context) {
      context.commit("CLEAR_CART");
    },
    placeOrder({ commit, state }, customerForm) {
      commit("CLEAR_ORDER_DETAILS");
      ApiService.placeOrder({
        cart: state.cart,
        customerForm: customerForm,
      }).then((response) => {
        commit("SET_ORDER_DETAILS", response);
        commit("CLEAR_CART");
        console.log("Cleared cart");
        console.log("Recieved customer data", response);
      });
    },
    setOrderDetails({ commit }, orderDetails) {
      commit("SET_ORDER_DETAILS", orderDetails);
    },
    clearOrderDetails({ commit }) {
      commit("CLEAR_ORDER_DETAILS");
    },
  },
});

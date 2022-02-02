<template>
  <div class="cart-table">
    <ul>
      <li class="cart-heading">
        <div class="cart-heading-book">Title</div>
        <div class="cart-heading-quantity">Quantity</div>
        <div class="cart-heading-price">Price</div>
      </li>
      <div class="row-sep"></div>
      <li
        v-for="(item, index) in this.$store.state.orderDetails.lineItems"
        :key="item.productId"
      >
        <div class="cart-book-title">
          {{ $store.state.orderDetails.books[index].title }}
        </div>
        <div class="cart-book-quantity">
          <span class="row-book-quantity">{{ item.quantity }}</span>
        </div>
        <div class="cart-book-subtotal">
          {{
            (($store.state.orderDetails.books[index].price * item.quantity) /
              100)
              | asDollarsAndCents
          }}
        </div>
        <div class="row-sep"></div>
      </li>
      <div class="row-sep"></div>
      <li>
        <span class="cart-total-text">Subtotal</span>
        <span class="cart-all-books-total">{{
          (getSubTotal() / 100) | asDollarsAndCents
        }}</span>
      </li>
      <li>
        <span class="cart-total-text">Surcharge</span>
        <span class="cart-all-books-total">{{
          (this.$store.state.cart.surcharge / 100) | asDollarsAndCents
        }}</span>
      </li>
      <li>
        <span class="cart-total-text">Total</span>
        <span class="cart-all-books-total">
          {{
            ((getSubTotal() + this.$store.state.cart.surcharge) / 100)
              | asDollarsAndCents
          }}</span
        >
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "ConfirmationTable",
  methods: {
    getSubTotal() {
      let subTotal = 0;
      for (let item in this.$store.state.orderDetails.lineItems) {
        subTotal =
          subTotal +
          this.$store.state.orderDetails.books[item].price *
            this.$store.state.orderDetails.lineItems[item].quantity;
      }
      console.log(subTotal);
      return subTotal;
    },
  },
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  background-color: #52acff;
  margin: 0 auto;
  padding: 1em;
}
ul,
li {
  display: contents;
}
.row-sep {
  grid-column: 1 /-1;
  background-color: black;
  height: 2px;
}
.cart-heading {
  font-weight: bolder;
}
.cart-heading-book {
  grid-column: 1/2;
  padding: 0 1em;
}
.cart-heading-quantity {
  grid-column: 2/3;
  padding: 0 8em;
}
.cart-heading-price {
  grid-column: 4/5;
  text-align: right;
  padding-left: 2em;
}
.cart-book-title {
  padding: 0 1em;
  font-weight: bold;
}
.cart-book-quantity {
  grid-column: 2/3;
  text-align: center;
  font-weight: bold;
}
.cart-book-subtotal {
  grid-column: 4/5;
  text-align: right;
  font-weight: bold;
}
.cart-total-text {
  grid-column: 2/3;
  padding: 0 8em;
  font-weight: bold;
  text-align: center;
}
.cart-all-books-total {
  text-align: right;
  font-weight: bold;
  grid-column: 4/5;
}
</style>

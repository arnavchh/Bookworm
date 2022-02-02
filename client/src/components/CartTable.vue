<template>
  <div class="cart-table">
    <ul>
      <li class="cart-heading">
        <div class="cart-heading-book">Book</div>
        <div class="cart-heading-price">Price</div>
        <div class="cart-heading-quantity">Quantity</div>
        <div class="cart-heading-subtotal">Amount</div>
      </li>
      <div class="row-sep"></div>
      <li v-for="item in $store.state.cart.items" :key="item.book.bookId">
        <div class="cart-book-image">
          <img
            :src="
              require('@/assets/images/books/' + bookImageFileName(item.book))
            "
            :alt="item.book.title"
            width="100px"
          />
        </div>
        <div class="cart-book-title">{{ item.book.title }}</div>
        <div class="cart-book-price">
          {{ (item.book.price / 100) | asDollarsAndCents }}
        </div>
        <div class="cart-book-quantity">
          <span class="row-book-quantity">{{ item.quantity }}</span>
          <button
            class="icon-button inc-button"
            @click="updateCart(item.book, item.quantity + 1)"
          >
            <i class="fa fa-plus-circle"></i>
          </button>
          <button
            class="icon-button dec-button"
            @click="updateCart(item.book, item.quantity - 1)"
          >
            <i class="fa fa-minus-circle"></i>
          </button>
        </div>
        <div class="cart-book-subtotal">
          {{ (item.total / 100) | asDollarsAndCents }}
        </div>
        <div class="row-sep"></div>
      </li>
      <div class="row-sep"></div>
      <li>
        <span class="cart-total-text">Total</span>
        <span class="cart-all-books-quantity">{{
          this.$store.state.cart.numberOfItems
        }}</span>
        <span class="cart-all-books-total">{{
          (this.$store.state.cart.subtotal / 100) | asDollarsAndCents
        }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "CartTable",
  methods: {
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      name = name.replace(":", "");
      return `${name}.jpg`;
    },
    updateCart(book, quantity) {
      this.$store.dispatch("updateCart", { book, quantity });
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
  grid-column: 1/3;
  padding: 0 1em;
}
.cart-heading-quantity {
  grid-column: 4/5;
  padding: 0 2em;
}
.cart-heading-price {
  grid-column: 3/4;
  padding: 0 1em;
  text-align: right;
}
.cart-heading-subtotal {
  grid-column: -2/-1;
  padding: 0 1em;
  text-align: right;
}
.cart-book-image {
  padding: 0 1em;
}
.cart-book-title {
  padding: 0 1em;
}
.cart-book-price {
  padding: 0 1em;
  text-align: right;
}
.cart-book-quantity {
  padding: 0 2em;
}
.cart-book-subtotal {
  padding: 0 1em;
  text-align: right;
}
.icon-button {
  color: black;
  background-color: transparent;
  font-size: 1rem;
  border: none;
}
.inc-button {
  margin-left: 0.5em;
}
.dec-button {
  margin-left: -0.4em;
}
.icon-button:hover {
  cursor: pointer;
}
.cart-total-text {
  grid-column: 3/4;
  padding-left: 1.5em;
  font-weight: bold;
}
.cart-all-books-quantity {
  grid-column: 4/5;
  text-align: center;
  font-weight: bold;
}
.cart-all-books-total {
  text-align: center;
  font-weight: bold;
}
</style>

<template>
  <div class="cart-page">
    <section
      v-if="$store.state.cart.numberOfItems === 0"
      class="empty-cart-page"
    >
      <div class="row-sep"></div>
      <p class="empty-cart-message">There are no items in your cart :(</p>
      <div class="row-sep"></div>
      <div class="empty-cart-buttons">
        <router-link :to="'/Category/' + getSelectedCategoryName()">
          <button class="button continue-shopping">Continue shopping</button>
        </router-link>
      </div>
    </section>
    <section v-else class="non-empty-cart-page">
      <p
        class="cart-heading-content"
        v-if="this.$store.state.cart.numberOfItems === 1"
      >
        Your cart has {{ this.$store.state.cart.numberOfItems }} item in it
      </p>
      <p
        class="cart-heading-content"
        v-if="this.$store.state.cart.numberOfItems > 1"
      >
        Your cart has {{ this.$store.state.cart.numberOfItems }} items in it
      </p>
      <div class="cart-buttons">
        <router-link :to="'/Category/' + getSelectedCategoryName()">
          <button class="button continue-shopping">Continue shopping</button>
        </router-link>
        <router-link to="/checkout">
          <button class="button checkout">Proceed to checkout</button>
        </router-link>
      </div>
      <cartTable> </cartTable>
      <div class="clear-cart-button">
        <button class="button link-like-button" @click="clearCart()">
          Clear cart
        </button>
      </div>
    </section>
  </div>
</template>

<script>
import CartTable from "@/components/CartTable";
export default {
  name: "Cart",
  components: { CartTable },
  methods: {
    getSelectedCategoryName() {
      return this.$store.state.selectedCategoryName != ""
        ? this.$store.state.selectedCategoryName
        : "Adventure";
    },
    clearCart() {
      this.$store.dispatch("clearCart");
    },
  },
};
</script>

<style scoped>
.cart-page {
  padding: 1em;
  background-color: #b9ddff;
}
.non-empty-cart-page {
  display: flex;
  flex-direction: column;
  gap: 1em;
}
.cart-buttons {
  display: flex;
  justify-content: space-around;
}
.empty-cart-buttons {
  display: flex;
  justify-content: center;
  padding-top: 3em;
}
.clear-cart-button {
  margin-left: 11.5em;
}
.link-like-button {
  align-self: start;
}
.cart-heading-content {
  margin: 0 auto;
}
.empty-cart-message {
  font-weight: bolder;
  font-size: xx-large;
  text-align: center;
  padding-bottom: 1em;
  padding-top: 1em;
}
.continue-shopping {
  background-color: #52acff;
  position: center;
  transition: color 0.5s, background-color 0.5s;
}
.checkout {
  background-color: black;
  transition: color 0.5s, background-color 0.5s;
}
.continue-shopping:hover {
  background: #0b6895;
}
.checkout:hover {
  background-color: lightgray;
  color: black;
}
.link-like-button {
  background-color: transparent;
  color: gray;
}
.link-like-button:hover {
  color: blue;
  background-color: transparent;
}
.row-sep {
  grid-column: 1 /-1;
  background-color: black;
  height: 5px;
}
</style>

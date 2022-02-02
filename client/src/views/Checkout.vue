<template>
  <div class="checkout-page">
    <div class="Empty checkout-page" v-if="cart.empty">
      <div class="row-sep"></div>
      <p class="empty-cart-message">
        There are no items in your cart for you to checkout :(
      </p>
      <div class="row-sep"></div>
      <div class="empty-cart-buttons">
        <router-link :to="'/Category/' + getSelectedCategoryName()">
          <button class="button continue-shopping">Continue shopping</button>
        </router-link>
      </div>
    </div>

    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder" id="checkout-form">
        <div>
          <label for="name">Name</label>
          <input
            type="text"
            size="20"
            id="name"
            name="name"
            v-model.lazy="$v.name.$model"
          />
        </div>
        <template v-if="$v.name.$error">
          <span class="error" v-if="!$v.name.required">Name is required</span>
          <span class="error" v-else-if="!$v.name.minLength">
            Name must have at least
            {{ $v.name.$params.minLength.min }} letters.
          </span>
          <span class="error" v-else-if="!$v.name.maxLength">
            Name can have at most
            {{ $v.name.$params.maxLength.max }} letters.
          </span>
          <span class="error" v-else> An unexpected error occurred. </span>
        </template>
        <div>
          <label for="name">Address</label>
          <input
            type="text"
            size="20"
            id="address"
            name="address"
            v-model.lazy="$v.address.$model"
          />
        </div>
        <template v-if="$v.address.$error">
          <span class="error" v-if="!$v.address.required"
            >Address is required</span
          >
          <span class="error" v-else-if="!$v.address.minLength">
            Address must have at least
            {{ $v.address.$params.minLength.min }} letters.
          </span>
          <span class="error" v-else-if="!$v.address.maxLength">
            Address can have at most
            {{ $v.address.$params.maxLength.max }} letters.
          </span>
          <span class="error" v-else> An unexpected error occurred. </span>
        </template>
        <div>
          <label for="phone">Phone</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="phone"
            name="phone"
            v-model.lazy="$v.phone.$model"
          />
        </div>
        <template v-if="$v.phone.$error">
          <span class="error" v-if="!$v.phone.required">Phone is required</span>
          <span class="error" v-else-if="!$v.phone.phone"
            >Please enter a valid phone number</span
          >
          <span class="error" v-else>An unexpected error occurred</span>
        </template>
        <div>
          <label for="email">Email</label>
          <input
            type="text"
            size="20"
            id="email"
            name="email"
            v-model.lazy="$v.email.$model"
          />
        </div>
        <template v-if="$v.email.$error">
          <span class="error" v-if="!$v.email.required"
            >Email ID is required</span
          >
          <span class="error" v-else-if="!$v.email.email"
            >Please enter a valid email ID</span
          >
          <span class="error" v-else>An unexpected error occurred</span>
        </template>

        <div>
          <label for="ccNumber">Credit card</label>
          <input
            type="text"
            size="20"
            id="ccNumber"
            name="ccNumber"
            v-model.lazy="$v.ccNumber.$model"
          />
        </div>
        <template v-if="$v.ccNumber.$error">
          <span class="error" v-if="!$v.ccNumber.required"
            >Credit card number is required</span
          >
          <span class="error" v-else-if="!$v.ccNumber.creditCard"
            >Please enter a valid credit card</span
          >
          <span class="error" v-else>An unexpected error occurred</span>
        </template>

        <div>
          <label>Exp Date</label>
          <select v-model="ccExpiryMonth">
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
            >
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <select v-model="ccExpiryYear">
            <option
              v-for="(year, index) in 15"
              :key="index"
              :value="yearFrom(index)"
            >
              {{ yearFrom(index) }}
            </option>
          </select>
        </div>
      </form>
      <div class="checkout-summary">
        <div class="total-cost">
          <span class="AccountSummary">Account Summary</span>
          <div class="subtotal">
            <span>Subtotal</span>
            <span>{{ (cart.subtotal / 100) | asDollarsAndCents }}</span>
          </div>
          <div class="surcharge">
            <span>Surcharge</span>
            <span>{{ (cart.surcharge / 100) | asDollarsAndCents }}</span>
          </div>
          <div class="totalCost">
            <span>Total Cost</span>
            <span>{{ (cart.total / 100) | asDollarsAndCents }}</span>
          </div>
          <div class="checkout-button-container">
            <input
              type="submit"
              name="submit"
              class="checkout-button"
              :disabled="checkoutStatus == 'PENDING'"
              value="Complete Purchase"
              form="checkout-form"
            />
          </div>
          <section v-show="checkoutStatus != ''" class="checkoutStatusBox">
            <div v-if="checkoutStatus == 'ERROR'">
              Error: Please fix the problems on the left and try again.
            </div>

            <div v-else-if="checkoutStatus == 'PENDING'">Processing...</div>

            <div v-else-if="checkoutStatus == 'OK'">Order placed...</div>

            <div v-else>
              <!-- "checkoutStatus == 'SERVER_ERROR'" -->
              An unexpected error occurred, please try again.
            </div>
          </section>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import {
  required,
  // email,
  minLength,
  maxLength,
} from "vuelidate/lib/validators";

import isCreditCard from "validator/lib/isCreditCard";
import isMobilePhone from "validator/lib/isMobilePhone";
import isEmail from "validator/lib/isEmail";
const phone = (value) => isMobilePhone(value, "en-US");
const email = (value) => isEmail(value);
const creditCard = (value) => isCreditCard(value);

export default {
  data() {
    return {
      name: "",
      address: "",
      phone: "",
      email: "",
      ccNumber: "",
      ccExpiryMonth: new Date().getMonth() + 1,
      ccExpiryYear: new Date().getFullYear(),
      checkoutStatus: "",
    };
  },
  validations: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    phone: {
      required,
      phone,
    },
    address: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    email: {
      required,
      email,
    },
    ccNumber: {
      required,
      creditCard,
    },
  },

  computed: {
    cart() {
      return this.$store.state.cart;
    },
    months() {
      return [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
    },
  },

  methods: {
    submitOrder() {
      console.log("Submit order");
      this.$v.$touch(); // Ensures validators always run
      if (this.$v.$error) {
        this.checkoutStatus = "ERROR";
      } else {
        this.checkoutStatus = "PENDING";
        setTimeout(() => {
          this.$store
            .dispatch("placeOrder", {
              name: this.name,
              address: this.address,
              phone: this.phone,
              email: this.email,
              ccNumber: this.ccNumber,
              ccExpiryMonth: this.ccExpiryMonth,
              ccExpiryYear: this.ccExpiryYear,
            })
            .then(() => {
              this.checkoutStatus = "OK";
              this.$router.push({ name: "confirmation" });
            })
            .catch((reason) => {
              this.checkoutStatus = "SERVER_ERROR";
              console.log("Error placing order", reason);
            });
        }, 1000);
      }
    },
    getSelectedCategoryName() {
      return this.$store.state.selectedCategoryName != ""
        ? this.$store.state.selectedCategoryName
        : "Adventure";
    },

    /* NOTE: For example yearFrom(0) == 2021 */
    yearFrom(index) {
      return new Date().getFullYear() + index;
    },
  },
};
</script>

<style scoped>
.checkout-page {
  background-color: #b9ddff;
  padding: 1em;
}
.checkout-page-body {
  display: flex;
  justify-content: space-between;
  padding: 1.5em;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  display: flex;
  justify-content: right;
  margin-bottom: 0.5em;
}

form > div > input,
form > div > select {
  background-color: white;
  margin-left: 0.5em;
}
form > div > input {
  width: 25em;
  height: 2em;
}
form > div > select {
  width: 12.3em;
}

form > .error {
  color: red;
  text-align: right;
  margin-top: -0.3em;
  margin-bottom: 0.8em;
}

.checkoutStatusBox {
  /*display: flex;*/
  padding: 1em;
  text-align: center;
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
.continue-shopping:hover {
  background: #0b6895;
}
.row-sep {
  grid-column: 1 /-1;
  background-color: black;
  height: 5px;
}
.empty-cart-buttons {
  display: flex;
  justify-content: center;
  padding-top: 3em;
  /*margin: 0 auto;*/
}
.total-cost {
  display: grid;
  row-gap: 1em;
  text-align: center;
  margin-top: 1.5em;
}
.checkout-button {
  background-color: black;
  color: white;
  width: 13em;
  position: relative;
  /*left: 23.5em;*/
  /*bottom: 3.5em;*/
  transition: color 0.5s, background-color 0.5s;
}
.subtotal {
  display: flex;
  justify-content: space-evenly;
  font-weight: bolder;
}
.surcharge {
  display: flex;
  justify-content: space-evenly;
}
.checkout-button:hover:enabled {
  cursor: pointer;
  background-color: lightgray;
  color: black;
}
checkout-button:disabled {
}
label {
  font-weight: bolder;
  font-size: large;
  padding-top: 0.2em;
}

.totalCost {
  display: flex;
  justify-content: space-evenly;
  font-weight: bolder;
}
.AccountSummary {
  font-weight: bolder;
}
.checkout-summary {
  display: flex;
  flex-direction: column;
  margin-right: 7em;
  height: 14em;
  width: 20em;
  background-color: #52acff;
  border: 3px solid black;
}
</style>

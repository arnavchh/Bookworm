<template>
  <section v-if="$store.state.orderDetails === null">
    <div class="confirmationView">
      <span class="noConfirmation">
        Looks like you have have not placed an order yet. How about we shopping
        for some books so that you can place an order ?</span
      >
      <span class="noConfirmationButtons">
        <router-link :to="'/Category/' + getSelectedCategoryName()">
          <button class="button continue-shopping">Continue shopping</button>
        </router-link>
      </span>
    </div>
  </section>
  <section v-else>
    <div
      class="confirmationView"
      v-if="$store.state.orderDetails !== undefined"
    >
      <ul>
        <li>Congratulations you have successfully placed your order !!</li>
        <li>
          Confirmation #:
          {{ this.$store.state.orderDetails.order.confirmationNumber }}
        </li>
        <li>Time: {{ getCreatedTime() }}</li>
      </ul>
      <confirmation-table> </confirmation-table>
      <ul>
        <li><h2>Customer Information</h2></li>
        <li>Name : {{ orderDetails.customer.customerName }}</li>
        <li>Address : {{ orderDetails.customer.address }}</li>
        <li>Email ID : {{ orderDetails.customer.email }}</li>
        <li>Phone Number : {{ orderDetails.customer.phone }}</li>
        <li>
          Card Details : {{ displayCardNumber() }} ({{ getExpiryDate() }})
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
import ConfirmationTable from "@/components/ConfirmationTable";
import { mapState } from "vuex";
export default {
  name: "Confirmation",
  components: { ConfirmationTable },
  computed: {
    ...mapState(["orderDetails"]),
  },
  created: function () {
    setTimeout(function () {
      console.log("changing opacity");
      document.getElementsByClassName("confirmationView")[0].style.opacity = 1;
    }, 2000);
  },
  methods: {
    getExpiryDate() {
      let date = new Date(this.$store.state.orderDetails.customer.ccExpDate);
      let result = date.getMonth() + 1;
      result = result + "-" + date.getFullYear();
      return result;
    },
    getCreatedTime() {
      let date = new Date(this.$store.state.orderDetails.order.dateCreated);
      let result = date.getFullYear();
      result = result + "-" + (date.getMonth() + 1);
      result = result + "-" + date.getDate();
      result = result + " " + date.getHours();
      result = result + ":" + date.getMinutes();
      result = result + ":" + date.getSeconds();
      return result;
    },
    displayCardNumber() {
      let str = this.$store.state.orderDetails.customer.ccNumber;
      var trailingCharsIntactCount = 4;
      let result =
        new Array(str.length - trailingCharsIntactCount + 1).join("x") +
        str.slice(-trailingCharsIntactCount);
      return result;
    },
    getSelectedCategoryName() {
      return this.$store.state.selectedCategoryName != ""
        ? this.$store.state.selectedCategoryName
        : "Adventure";
    },
  },
};
</script>

<style scoped>
.confirmationView {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  text-align: left;
  background-color: #b9ddff;
  opacity: 0;
}
ul {
  text-align: center;
  font-size: larger;
  font-weight: bolder;
  padding: 1em 0em;
}
ul > li {
  margin: 0.25em;
  padding: 0.4em;
}
.continue-shopping {
  background-color: #52acff;
  position: center;
  transition: color 0.5s, background-color 0.5s;
}
.continue-shopping:hover {
  background: #0b6895;
}
.noConfirmation {
  font-weight: bolder;
  font-size: larger;
  padding-top: 1em;
  padding-bottom: 1em;
}
.noConfirmationButtons {
  padding-bottom: 1em;
}
</style>

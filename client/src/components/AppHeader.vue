<template>
  <header class="container">
    <section class="bookworm-logo">
      <router-link to="/">
        <img
          src="@/assets/images/site/bookstore-logo.png"
          alt="The Bookworm"
          width="100px"
        />
      </router-link>
    </section>
    <section class="title-and-search-bar">
      <router-link to="/" style="text-decoration: none"
        ><h1 class="text-logo">The Bookworm</h1></router-link
      >
      <form action="/category">
        <input
          type="text"
          class="search-bar"
          placeholder="Search for titles ...."
        /><button class="search-button" id="searchButton" style="color: black">
          <i class="fa fa-search"></i>
        </button>
      </form>
    </section>
    <section class="header-dropdown-and-cart">
      <header-dropdown-menu></header-dropdown-menu>
      <div class="cart">
        <router-link to="/cart">
          <i class="fa fa-shopping-cart" id="shopping-cart"
            ><p id="cart-number">{{ $store.state.cart.numberOfItems }}</p></i
          >
        </router-link>
      </div>
      <button class="button" id="login-button">AC</button>
    </section>
  </header>
</template>

<script>
import HeaderDropdownMenu from "@/components/HeaderDropdown";
export default {
  name: "AppHeader",
  components: { HeaderDropdownMenu },
  component: {
    HeaderDropdownMenu,
  },
};
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  padding: 1em;
  justify-content: space-around;
}
.text-logo {
  font-family: var(--title-font-family);
  font-size: xx-large;
  color: white;
  text-decoration: none;
}
.title-and-search-bar {
  width: 54%;
  margin-left: 2.5%;
}

.header-dropdown-and-cart {
  width: 34%;
  display: flex;
  flex-direction: row;
  gap: 1em;
  align-items: center;
  margin-top: 0.7em;
  margin-right: -2.5em;
}

.cart {
  font-size: xxx-large;
  position: relative;
}
.cart :hover {
  cursor: pointer;
}
#cart-number {
  font-size: large;
  color: white;
  position: absolute;
  top: 0.6em;
  left: 1.2em;
}
#login-button {
  height: 38px;
  background-color: black;
  border-color: black;
  border-radius: 50%;
  align-items: center;
  padding: 0.5em 0.5em;
  margin-left: 1em;
}
@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}
.search-bar {
  width: 90%;
  margin-top: 1.2%;
  height: 38px;
  border-radius: 1em 0em 0em 1em;
  border: 1px solid black;
}
.search-button {
  top: 3.75em;
  right: 31.15em;
  border-right: 1px solid black;
  border-top: 1px solid black;
  border-bottom: 1px solid black;
  border-radius: 0em 1em 1em 0em;
  height: 38px;
  width: 3em;
  border-left: 0;
  background-color: transparent;
}
.search-button:hover {
  cursor: pointer;
}
#shopping-cart {
  color: black;
}
</style>

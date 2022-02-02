<template>
  <div
    class="header-dropdown"
    v-on:mouseenter="toogleMainMenu"
    v-on:mouseleave="toogleMainMenu"
  >
    <button class="button categories-button" id="categories-button">
      <i class="fa fa-chevron-circle-down"></i> Categories
    </button>
    <ul
      class="dropdown-values"
      :class="{ mainMenuClose: mainMenuOpen == false }"
    >
      <template v-for="category in $store.state.categories">
        <router-link
          :key="category.categoryId"
          :to="'/category/' + category.name"
          tag="li"
          style="..."
          >{{ category.name }}</router-link
        >
      </template>
    </ul>
  </div>
</template>

<script>
export default {
  name: "HeaderDropdownMenu",
  data: function () {
    return {
      mainMenuOpen: false,
    };
  },
  methods: {
    toogleMainMenu() {
      this.mainMenuOpen = !this.mainMenuOpen;
    },
  },
};
</script>

<style scoped>
.header-dropdown {
  position: relative;
  z-index: 1;
}

.categories-button {
  background-color: var(--primary-color-dark);
  margin-top: 0.25em;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
}

.header-dropdown a {
  color: white;
  text-decoration: none;
}

.header-dropdown li:hover {
  background: darkblue;
}

#categories-button {
  background-color: black;
  min-width: 8em;
}

.mainMenuClose {
  height: 0;
  overflow: hidden;
  visibility: hidden;
}

ul {
  background-color: black;
  color: white;
  box-shadow: 4px 4px 10px 0px #fff4;
  position: absolute;
  min-width: 8em;
  cursor: pointer;
  visibility: visible;
  overflow: hidden;
  height: 8.5em;
  transition: visibility, max-height, 0.5s 0s ease-in-out;
}
</style>

<template>
  <nav class="category-nav">
    <section class="category-buttons">
      <template v-for="category in $store.state.categories">
        <div
          class="selected-category"
          v-if="category.name == $route.params.name"
          :to="'/category/' + category.name"
          :key="category.categoryId"
        >
          {{ category.name }}
        </div>
        <div v-else class="category" :key="category.categoryId">
          <router-link
            class="categories"
            :to="'/category/' + category.name"
            style="..."
            >{{ category.name }}</router-link
          >
        </div>
      </template>
    </section>
  </nav>
</template>

<script>
export default {
  name: "CategoryNav",
};
</script>

<style scoped>
.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  justify-content: space-evenly;
  width: 100%;
  background-color: black;
  border: 0;
  flex-wrap: wrap;
}
.category {
  border: 1px solid black;
}
.selected-category {
  background-color: #52acff;
}
nav a {
  text-decoration: none;
  color: white;
}
.category-buttons > div {
  font-weight: bold;
  padding: 0.65em 4em;
  text-decoration: none;
}

.category-buttons > div:hover {
  background-color: #52acff;
}
</style>

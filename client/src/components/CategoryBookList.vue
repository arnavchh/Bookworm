<template>
  <div class="all-books">
    <div
      class="books-list"
      v-if="this.$store.state.selectedCategoryBooks.length > 0"
    >
      <template v-for="book in this.$store.state.selectedCategoryBooks">
        <category-book-list-item
          :key="book.bookId"
          :book="book"
        ></category-book-list-item>
      </template>
    </div>
  </div>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";

export default {
  name: "CategoryBookList",
  components: {
    CategoryBookListItem,
  },
};
</script>

<style scoped>
.all-books {
  padding: 2em 1em 2em 1em;
  background-color: #b9ddff;
}
.books-list {
  display: flex;
  flex-wrap: wrap;
}
</style>

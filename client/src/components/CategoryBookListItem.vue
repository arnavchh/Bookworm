<template>
  <div class="each-book">
    <div class="book-image">
      <img
        :src="require('@/assets/images/books/' + bookImageFileName(book))"
        :alt="book.title"
        class="img-books"
      />
      <button v-if="book.isPublic" class="read-now">Read Now</button>
    </div>
    <div class="book-content">
      <div class="category-info">
        <div class="book-title">Title: {{ book.title }}</div>
        <div class="book-author">Author: {{ book.author }}</div>
        <div class="book-price">
          Price: {{ (book.price / 100) | asDollarsAndCents }}
        </div>
      </div>
      <button class="add-to-cart" @click="addToCart(book)">
        <i class="fa fa-cart-plus"></i> Add to Cart
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "categoryBookListItem",
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  methods: {
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      name = name.replace(":", "");
      return `${name}.jpg`;
    },
    addToCart(book) {
      this.$store.dispatch("addToCart", book);
    },
  },
};
</script>
<style scoped>
.book-image {
  margin-right: 1em;
  width: 170px;
  height: fit-content;
}
.each-book {
  margin: 0.5em;
  min-width: 500px;
  flex: 1;
  background-color: #52acff;
  display: flex;
  border: 3px solid black;
}

.book-content {
  padding: 1.5em 0em 1.5em 0em;
}
.book-title {
  font-weight: bold;
  font-size: large;
  padding-bottom: 1em;
}
.book-author {
  padding-bottom: 1em;
}
.book-price {
  padding-bottom: 1em;
  font-size: smaller;
  font-weight: bold;
}
.add-to-cart {
  background-color: black;
  color: white;
  border: 0;
  font-size: medium;
  height: 2em;
  border-radius: 0.2em;
  width: 8em;
  transition: box-shadow 0.5s, transform 0.5s, scale 0.5s;
}
.add-to-cart:hover {
  cursor: pointer;
  box-shadow: 1px 1px #fff4, 1px 1px #fff4, 1px 1px #fff4;
  transform: translate(8px, 3px) scale(1.2);
}
.read-now {
  position: absolute;
  background-color: #fa2d2d;
  border: 0;
  height: 1.5em;
  color: #fff4f4;
  margin-top: -1.7em;
  width: 170px;
  transition: color 1s, background-color 1s;
}
.read-now:hover {
  cursor: pointer;
  background-color: lightgray;
  color: black;
}
.img-books {
  width: 170px;
  height: 230px;
}
.category-info {
  height: 9em;
}
</style>

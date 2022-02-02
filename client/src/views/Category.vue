<template>
  <div>
    <category-nav></category-nav>
    <category-book-list> </category-book-list>
  </div>
</template>

<script>
import CategoryNav from "@/components/CategoryNav";
import CategoryBookList from "@/components/CategoryBookList";

export default {
  name: "category",
  components: {
    CategoryNav,
    CategoryBookList,
  },
  created: function () {
    if (
      this.$store.state.selectedCategoryName == "" ||
      this.$store.state.selectedCategoryName != this.$route.params.name
    ) {
      const self = this;
      this.changeName();
      this.fetchBooksByCategory().catch(function () {
        console.log("Coming into wrong category page");
        console.log(self);
        console.log(self.$router);
        self.$router.push("/404");
      });
    }
  },
  methods: {
    changeName() {
      this.$store.dispatch("setCategoryName", this.$route.params.name);
    },
    fetchBooksByCategory() {
      return this.$store.dispatch("getBooksByCategory");
    },
  },
  watch: {
    $route: function () {
      if (this.$route.params.name != "") {
        const self = this;
        this.changeName();
        this.fetchBooksByCategory().catch(function () {
          console.log("Coming into wrong category page");
          self.$router.push({ name: "NotFound" });
        });
      }
    },
  },
};
</script>

<style scoped></style>

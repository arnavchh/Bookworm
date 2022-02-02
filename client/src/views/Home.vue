<template>
  <div class="home-page">
    <HomeMessage></HomeMessage>
    <home-category-list></home-category-list>
  </div>
</template>

<script>
import HomeCategoryList from "@/components/HomeCategoryList";
import HomeMessage from "@/components/HomeMessage";
export default {
  name: "Home",
  components: { HomeMessage, HomeCategoryList },
};
</script>

<style scoped>
.home-page {
  display: flex;
  background-color: #b9ddff;
}
.category-image-items a {
  text-align: center;
  cursor: pointer;
}
</style>

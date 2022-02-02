<template>
  <section class="category-images container">
    <div class="category-image-items">
      <img src="@/assets/images/categories/1984.jpg" alt="Classics category" />
      <img
        src="@/assets/images/categories/atomic-habits.jpg"
        alt="Atomic habits"
      />
      <img
        src="@/assets/images/categories/silent-patient.jpg"
        alt="Silent patient"
      />
      <img src="@/assets/images/categories/no-rules.jpg" alt="No rules" />
      <img
        src="@/assets/images/categories/design-book.jpg"
        alt="Designer book"
      />
      <img src="@/assets/images/categories/long-range.jpg" alt="Long range" />
    </div>
  </section>
</template>
<script>
export default {
  name: "HomeCategoryList",
};
</script>

<style scoped>
.category-image-items {
  display: flex;
  width: 100%;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 1em;
  color: white;
  margin-bottom: -1em;
  margin-top: -1em;
  padding: 0.3em;
  flex: 1 auto;
  margin-right: 7em;
}
.category-image-items:hover {
  cursor: pointer;
}
.category-image-items > img {
  height: 195px;
  width: 130px;
}
</style>

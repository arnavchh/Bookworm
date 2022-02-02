<template>
  <div class="invalid-page">
    <span class="invalid-text">
      Looks like you have come to invalid page. How about you go back to the
      home page of the website ?
    </span>
    <router-link to="/">
      <button class="call-to-action">The Bookworm</button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped>
.call-to-action {
  background-color: #52acff;
  border: 0;
  height: 38px;
  border-radius: 10px;
  color: white;
  transition: background 1s, transform 1s;
}
.call-to-action:hover {
  cursor: pointer;
  background: #0b6895;
  transform: scale(1.2);
}
.invalid-page {
  padding-bottom: 1em;
  padding-top: 1em;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1em;
}
.invalid-text {
  font-weight: bolder;
  font-size: larger;
}
</style>

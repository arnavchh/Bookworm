<template>
  <div class="home-message">
    <section class="welcome-text flow-content container">
      <div class="welcome-text-and-action-button">
        <h2>Welcome to The Bookworm</h2>
        <p>Hope we can help you with finding what you want</p>
        <br />
        <router-link to="../category/Adventure">
          <button class="call-to-action">LETS GET SOME BOOKS !!!!</button>
        </router-link>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "HomeMessage",
};
</script>

<style scoped>
.home-message {
  width: 80%;
  display: flex;
  align-items: center;
  padding-left: 5em;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
}
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
</style>

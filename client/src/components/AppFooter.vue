<template>
  <footer class="container">
    <section class="copyright">
      <p>© 2021 The Bookworm</p>
    </section>
    <section class="links">
      <a href="index.html" style="color: white">About</a>
      <a href="index.html" style="color: white">Contact Us</a>
      <a href="index.html" style="color: white">Directions</a>
    </section>
    <section class="social-media-icons">
      <i class="fa fa-facebook-square" id="facebook-icon"></i>
      <i class="fa fa-twitter-square" id="twitter-icon"></i>
    </section>
  </footer>
</template>

<script>
export default {
  name: "AppFooter",
};
</script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: flex;
  justify-content: space-evenly;
  text-align: center;
  gap: 13em;
}
.copyright {
  color: white;
  display: flex;
  align-items: center;
  text-align: left;
}
.links,
.social-media-icons {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-left: 2em;
}
.links {
  gap: 1.3em;
  margin-left: -0.5em;
}
.social-media-icons {
  gap: 0.25em;
  font-size: xx-large;
}
.social-media-icons:hover {
  cursor: pointer;
}
</style>

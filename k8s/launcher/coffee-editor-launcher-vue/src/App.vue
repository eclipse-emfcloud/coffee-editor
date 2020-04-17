<template>
  <div id="app">
    <img alt="EclipseSource" src="./assets/es-logo-white.png" />
    <CoffeeLauncher url="http://35.234.104.175:9091/services/launch" />
    <Terms />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import CoffeeLauncher from "./components/CoffeeLauncher.vue";
import Terms from "./components/Terms.vue";

@Component({
  components: {
    CoffeeLauncher,
    Terms
  }
})
export default class App extends Vue {}
</script>

<style>
:root {
  background-color: #313131;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
  margin-top: 60px;
}
</style>

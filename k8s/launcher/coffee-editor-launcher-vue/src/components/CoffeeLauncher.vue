<template>
  <div class="coffee">
    <div>
      <v-btn
        rounded
        color="#11b3bb"
        dark
        :loading="launching"
        :disabled="launching"
        @click="launch"
        >{{ buttonText }}</v-btn
      >
    </div>
    <div class="loading">
      <p>{{ loadingText }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import axios from "axios";

@Component
export default class CoffeeLauncher extends Vue {
  @Prop() private url!: string;

  private loadingText = " ";
  private buttonText = "Launch the Coffee Editor";
  private launching = false;

  private async launch() {
    this.launching = true;
    this.loadingText =
      "The Coffee Editor is being created. You will be redirected once finished.";
    await axios
      .get(this.url)
      .then(response => {
        // handle success
        console.log(response);
        const coffeeEditorURL = response.data;
        location.replace(coffeeEditorURL);
      })
      .catch(error => {
        alert(error.response.data);
      });
    this.launching = false;
    this.loadingText = " ";
  }
}
</script>

<style scoped>
.coffee {
  margin: 40px 0 0;
}
.loading {
  height: 20px;
  margin: 40px 0 0;
}
</style>

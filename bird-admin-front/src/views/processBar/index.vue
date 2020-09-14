<template>
  <div class="p-grid">
    <div class="p-col-12">
      <div class="card">
        <h5>ProgressBar</h5>
        <div class="p-grid">
          <div class="p-col">
            <ProgressBar :value="value" />
          </div>
          <div class="p-col">
            <ProgressBar :value="50" :showValue="false"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: 0
    }
  },
  interval: null,
  methods: {
    startProgress() {
      this.interval = setInterval(() => {
        let newValue = this.value + Math.floor(Math.random() * 10) + 1;
        if (newValue >= 100) {
          newValue = 100;
        }
        this.value = newValue;
      }, 2000);
    },
    endProgress() {
      clearInterval(this.interval);
      this.interval = null;
    }
  },
  mounted() {
    this.startProgress();
  },
  beforeDestroy() {
    this.endProgress();
  }
}
</script>

<style lang="scss" scoped>
.badges {
  .p-badge, .p-tag {
    margin-right: .5rem;
  }
}

.p-button {
  margin-right: .5rem;
}

.p-overlay-badge {
  margin-right: 2rem;
}
</style>

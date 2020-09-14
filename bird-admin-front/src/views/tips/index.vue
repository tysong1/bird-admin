<template>
  <div class="p-grid messages-demo">
    <div class="p-col-12 p-lg-6">
      <div class="card">
        <h5>Growl</h5>

        <Toast/>
        <Button @click="showSuccess()" label="Success" class="p-button-success p-mr-2"/>
        <Button @click="showInfo()" label="Info" class="p-button-info p-mr-2"/>
        <Button @click="showWarn()" label="Warn" class="p-button-warning p-mr-2"/>
        <Button @click="showError()" label="Error" class="p-button-danger p-mr-2"/>
      </div>
    </div>

    <div class="p-col-12 p-lg-6">
      <div class="card">
        <h5>Messages</h5>

        <Button label="Success" @click="addSuccessMessage()" class="p-button-success p-mr-2"/>
        <Button label="Info" @click="addInfoMessage()" class="p-button-info p-mr-2"/>
        <Button label="Warn" @click="addWarnMessage()" class="p-button-warning p-mr-2"/>
        <Button label="Error" @click="addErrorMessage()" class="p-button-danger p-mr-2"/>

        <transition-group name="p-messages" tag="div">
          <Message v-for="msg of message" :severity="msg.severity" :key="msg.content">{{ msg.content }}</Message>
        </transition-group>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: [],
      username: null,
      email: null
    }
  },
  methods: {
    addSuccessMessage() {
      this.message = [{severity: 'success', content: 'Message Detail'}]
    },
    addInfoMessage() {
      this.message = [{severity: 'info', content: 'Message Detail'}]
    },
    addWarnMessage() {
      this.message = [{severity: 'warn', content: 'Message Detail'}]
    },
    addErrorMessage() {
      this.message = [{severity: 'error', content: 'Message Detail'}]
    },
    showSuccess() {
      this.$toast.add({severity: 'success', summary: 'Success Message', detail: 'Message Detail', life: 3000});
    },
    showInfo() {
      this.$toast.add({severity: 'info', summary: 'Info Message', detail: 'Message Detail', life: 3000});
    },
    showWarn() {
      this.$toast.add({severity: 'warn', summary: 'Warn Message', detail: 'Message Detail', life: 3000});
    },
    showError() {
      this.$toast.add({severity: 'error', summary: 'Error Message', detail: 'Message Detail', life: 3000});
    },
  }
}
</script>

<style lang="scss" scoped>
.p-field > label {
  width: 125px;
}


.p-inputtext {
  margin-right: .5rem;
}
</style>

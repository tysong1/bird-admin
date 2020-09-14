<template>
  <div class="p-grid">
    <div class="p-col-12">
      <div class="card">
        <h5>文件上传</h5>
        <FileUpload name="file" :url="url" @upload="onUpload" :multiple="true" accept="*" :maxFileSize="1000000"/>
      </div>
    </div>
  </div>
</template>

<script>
import net from "@/api/baseUrl"
export default {
  data() {
    return {
      url:null,
    }
  },
  methods: {
    onUpload() {
      this.$toast.add({severity: 'info', summary: 'Success', detail: 'File Uploaded', life: 3000});
    }
  },
  created() {
  },
  mounted() {
    this.url=net.BASE_URL+'/file/upload';
  }
}
</script>

<style scoped>

</style>

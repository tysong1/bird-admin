<template>
  <div class="app-container">
    <editor :init="editorInit" v-model="article"/>
    <el-button type="primary" @click="submit">提交</el-button>
  </div>
</template>

<script>
import Editor from '@tinymce/tinymce-vue'
import { upload } from '@/api/storage'
import { MessageBox } from 'element-ui'
export default {
  components: { Editor },
  data() {
    return {
      article: {},
      editorInit: {
        language: 'zh_CN',
        convert_urls: false,
        plugins: [
          'advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'
        ],
        toolbar: [
          'searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample',
          'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'
        ],
        images_upload_handler: function(blobInfo, success, failure) {
          const formData = new FormData()
          formData.append('file', blobInfo.blob())
          upload(formData).then(res=>success(res.data.data))
        }
      }
    }
  },
  computed: {

  },
  created() {
  },
  mounted() {

  },
  methods: {
    submit: function() {
      this.$notify.success({
        title: '成功',
        message: '修改成功'
      })
      console.log(tinyMCE.activeEditor.getContent())
      MessageBox.alert('数据：' + JSON.stringify(tinyMCE.activeEditor.getContent()), 'data', {
        confirmButtonText: '确定',
        type: 'error'
      })
    }
  }
}
</script>

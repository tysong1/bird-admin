<template>
  <div class="login-container">
    <el-form :model="loginForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="login-form">
      <div class="title" style="text-align: center; position: relative;">
        <h3 style="font-size: 26px;
      font-weight: 400;
      color:#707070;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;">管理员登录</h3>
      </div>
      <el-form-item label="账号" prop="username">
        <el-input type="text" name="username" v-model="loginForm.username" autocomplete="on" placeholder="username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input :type="passwordType" v-model="loginForm.password" name="password" auto-complete="on" placeholder="password"></el-input>
      </el-form-item>
      <div style="text-align: center">
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" style="width:100%;margin-bottom:30px;">登陆</el-button>
        </el-form-item>
      </div>
    </el-form>

    <div style="position:fixed; bottom:0px; width:100%; text-align:center;font-size: 20px">
      欢迎使用bird-admin
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      passwordType: 'password',
      loginForm: {
        username: '',
        password: '',
      },
      rules: {
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('LoginByUsername', this.loginForm).then(() => {
            this.$store.dispatch('GetUserInfo').then(()=>{
              this.$router.push({ path: this.redirect || '/' })
            })
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data
            })
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style>
.login-form {
  position: absolute;
  left: 0;
  right: 0;
  width: 520px;
  padding: 35px 35px 15px 35px;
  margin: 120px auto;
}
</style>

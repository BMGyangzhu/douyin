<template>
  <div>
    <v-card-title class="py-5 font-weight-black">登录账号</v-card-title>
    <v-card-text class="pt-0">
      <div class="text-subtitle-2 font-weight-black mb-1">邮箱</div>
      <v-text-field label="请输入邮箱" v-model="loginInfo.email" single-line variant="outlined"></v-text-field>
      <div class="text-subtitle-2 font-weight-black mb-1">密码</div>
      <v-text-field label="请输入密码" type="password" v-model="loginInfo.password" single-line variant="outlined" @keyup.enter="loginVertify()"></v-text-field>

      <v-btn :disabled="loading" :loading="loading" block class="text-none mb-4" color="blue" size="x-large"
        variant="flat" @click="loginVerify()">
        登入账号
      </v-btn>
    </v-card-text>
  </div>
</template>
<script setup>
import { reactive, ref } from 'vue';
import { apiAuth } from '../../apis/user/auth';
const {showMessage, closeEvent} = defineProps({
  showMessage: {
    type: Function,
    default:()=>{}
  },
  closeEvent: {
    type: Function,
    default: ()=>{}
  }
})
const loading = ref(false)
const loginInfo = reactive({
  email: "",
  password: ""
})

const loginVerify =()=>{
  loading.value = true
  apiAuth(1, loginInfo).then(({data})=>{
    loading.value = false
    showMessage(data.message, data.state?'success':'error')
    if(!data.state) {
      return;
    }
    closeEvent({info: {}, token: data.data.token})
  })
}
</script>
<template>
  <div>
    <v-card
      class="mx-auto"
      color="#36393f"
      max-width="650"
      theme="dark"
      variant="flat"
    >
      <v-sheet color="#202225">
        <v-card-item>
          <template v-slot:prepend>
            <v-card-title>
              <v-icon
                icon="mdi-account"
                start
              ></v-icon>
              注册账号
            </v-card-title>
          </template>
        </v-card-item>
      </v-sheet>

      <v-card
        class="ma-4"
        color="#2f3136"
        rounded="lg"
        variant="flat"
      >
        <v-card-item>
          <v-card-title class="text-body-2 d-flex align-center">
            <v-icon
              color="#949cf7"
              icon="mdi-step-forward"
              start
            ></v-icon>

            <span class="text-medium-emphasis font-weight-bold">{{
              stepData[step].title
            }}</span>

            <v-spacer></v-spacer>
            <v-chip
              class="ms-2 text-medium-emphasis"
              color="grey-darken-4"
              size="small"
              variant="flat"
              >{{ step }}</v-chip
            >
          </v-card-title>
        </v-card-item>

        <v-divider></v-divider>

        <v-window v-model="step">
          <v-window-item :value="1">
            <v-form :disabled="isLoading">
              <v-card-text>
                <v-text-field
                  v-model="registerInfo.email"
                  label="邮箱"
                  hint="tips: 邮箱必须是真实有效的，它将用于登录账号使用。"
                  placeholder="请输入邮箱"
                ></v-text-field>
                <v-row no-gutters>
                  <v-col>
                    <v-img
                      :src="captchaImg"
                      @click="getCaptchaImg"
                    ></v-img>
                  </v-col>
                  <v-col>
                    <v-text-field
                      v-model="registerInfo.captchaCode"
                      label="图形验证码"
                      hide-details
                      placeholder="请输入图形验证码"
                    ></v-text-field>
                  </v-col>
                </v-row>

                <v-btn
                  color="blue"
                  block
                  :variant="'flat'"
                  @click="getEmailCode()"
                  :disabled="isCounting"
                >
                  {{
                    isCounting
                      ? countdown + "s 后重试"
                      : "获取邮箱验证码"
                  }}
                </v-btn>
              </v-card-text>
              <v-otp-input
                variant="underlined"
                v-model="registerInfo.emailCode"
              ></v-otp-input>
            </v-form>
          </v-window-item>

          <v-window-item :value="2">
            <v-card-text>
              <v-text-field
                v-model="registerInfo.nickName"
                label="请输入昵称"
                type="text"
              ></v-text-field>
              <v-text-field
                v-model="registerInfo.password"
                label="请输入密码"
                type="password"
                :rules="passwordRules"
              ></v-text-field>
              <v-text-field
                v-model="registerInfo.confirmPassword"
                label="请确认密码"
                type="password"
                :rules="confirmPasswordRules"
              ></v-text-field>
              <span class="text-caption text-grey-darken-1">
                tips: 请为您的账号设置一个密码
              </span>
            </v-card-text>
          </v-window-item>

          <v-window-item :value="3">
            <div
              class="pa-4 text-center"
              style="color: greenyellow"
            >
              <v-icon :size="60">mdi-check-circle</v-icon>
              <h3 class="text-h6 font-weight-light mb-2">注册成功</h3>
              <span class="text-caption text-grey">Thanks for signing up!</span>
            </div>
          </v-window-item>
        </v-window>
        <v-card-actions>
          <v-btn
            v-if="step == 2"
            variant="text"
            @click="step--"
            :loading="isLoading"
          >
            Back
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            v-if="step < 3"
            color="blue"
            variant="flat"
            @click="stepData[step].next ? stepData[step].next() : step++"
            :loading="isLoading"
          >
            Next
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-card>
  </div>
</template>
<script setup>
import { reactive, ref } from "vue";
import useCountdown from "../../utils/useCountdown";
import { minLength, matchPassword } from '../../utils/rules'
import { apiAuth, apiCheckEmailCode, apiGetCode } from "../../apis/user/auth";
import buildUtils from "../../utils/buildUtil";
const step = ref(1);
const isLoading = ref(false);
const captchaImg = ref();
const stepData = ref({
  1: {
    title: "邮箱验证",
    next: () => {
      verifyCode();
    },
  },
  2: {
    title: "设置密码",
    next: () => {
      setPassword();
    },
  },
  3: {
    title: "注册成功",
  },
});
const { showMessage, closeEvent } = defineProps({
  showMessage: {
    type: Function,
    default: () => {},
  },
  closeEvent: {
    type: Function,
    default: () => {},
  },
});

const getCaptchaImg = () => {
  registerInfo.uuid = buildUtils.guid();
  captchaImg.value = apiGetCode(1, registerInfo.uuid);
};
const registerInfo = reactive({
  email: "",
  nickName: "",
  emailCode: "",
  captchaCode: "",
  password: "",
  confirmPassword: "",
  uuid: "",
});

// 密码规则校验
const passwordRules = [
    minLength(6,'密码')
]
const confirmPasswordRules = [
    matchPassword(() => registerInfo.password, '密码')
]

// 倒计时逻辑
const { countdown, isCounting, start: startCountdown, reset: resetCountdown } = useCountdown(60);
getCaptchaImg();
// =========== methods 函数 ==============
const getEmailCode = () => {
  if (isCounting.value) return;// 防止重复点击


  apiGetCode(0, { ...registerInfo, code: registerInfo.captchaCode }).then(
    ({ data }) => {
      showMessage(data.message, data.state ? "success" : "error");
      if (!data.state) {
        getCaptchaImg();
        resetCountdown(); //请求失败时结束倒计时
        return;
      }
    }
  );


  // 成功后开始倒计时
    startCountdown(); 
};

const verifyCode = () => {
  isLoading.value = true;
  apiCheckEmailCode({ ...registerInfo }).then(({ data }) => {
    isLoading.value = false;

    if (!data.state) {
      showMessage("邮箱验证码无效", "red");
      getCaptchaImg();
      return;
    }
    step.value++;
  });
};

const setPassword = () => {

  apiAuth(0, registerInfo).then(({ data }) => {
    if (!data.state) {
      showMessage(data.message, "error");
      return;
    }
    step.value++;
  });
};
</script>

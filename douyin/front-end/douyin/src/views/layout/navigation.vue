<template>
  <v-navigation-drawer color="#252632">
    <v-list v-if="userStore.token">
      <v-list-item
        :prepend-avatar="
          userStore.info.avatar
            ? apiFileGet(userStore.info.avatar)
            : '/logo.png'
        "
        :title="userStore.info.nickName"
        :subtitle="userStore.info.description"
      ></v-list-item>
    </v-list>
    <v-list v-else>
      <v-list-item
        prepend-icon="mdi-account"
        title="未登录"
        subtitle="请先登录，享受更多服务"
      ></v-list-item>
    </v-list>
    <v-divider />

    <v-list
      density="compact"
      nav
    >
      <template v-if="userStore.token">
        <v-list-item
          prepend-icon="mdi-account"
          title="个人中心"
          to="/user"
        ></v-list-item>
        <!-- <v-list-item prepend-icon="mid-heart" title="关注的人" to="/followVideo"></v-list-item> -->
      </template>
    </v-list>
  </v-navigation-drawer>
</template>
<script setup>
import { useUserStore } from "../../stores";

const userStore = useUserStore();

</script>
<style lang="scss" scoped>
.v-navigation-drawer {
  border: none !important;
}
</style>

<template>
    <v-app-bar floating name="app-bar" :elevation="0" color="#252632">
      <v-app-bar-nav-icon icon="mdi-menu" @click="clickEvent(1, 1)"></v-app-bar-nav-icon>
      <v-app-bar-title>幸运日</v-app-bar-title>

      <v-toolbar-items variant="plain" class="pr-2">
        <v-btn class="d700-hide" @click="openSearch()" :variant="'text'"><v-icon>mdi-magnify</v-icon>搜索</v-btn>
         <!-- 登录按钮 -->
        <auth />
      </v-toolbar-items>
     
      <!-- <v-text-field class="e700-hide" hide-details :density="'comfortable'" prepend-inner-icon="mdi-magnify" single-line
        clearable @click="openSearch()" placeholder="搜索视频">
      </v-text-field>
      <v-spacer />
      <v-toolbar-items variant="plain" class="pr-2">
        <v-btn class="d700-hide" @click="openSearch()" :variant="'text'"><v-icon>mdi-magnify</v-icon>搜索</v-btn>
        <auth />
      </v-toolbar-items> -->
      <!-- <v-dialog v-model="showSearch" max-width="500px" :location="'top'">
        <v-card>
          <v-toolbar flat color="background">
            <v-text-field autofocus ref="searchInputRef" hide-details prepend-inner-icon="mdi-magnify" single-line clearable
              v-model="searchKey" @click:clear="search()" v-on:keyup.enter="search" placeholder="搜索视频">
            </v-text-field>
          </v-toolbar>
          <v-divider></v-divider>
          <v-card-text>
            <h2 class="text-h6 mb-2" v-if="serarchHistory.length>0">
              搜索历史
            </h2>
            <v-chip-group v-model="searchKey" column @update:model-value="search">
              <v-chip filter :value="item" variant="outlined" v-for="item in serarchHistory">
                {{ item }}
              </v-chip>
            </v-chip-group>
          </v-card-text>
          <v-divider />
          <v-card-text class="pt-0">
            <hotList elevation="0" />
          </v-card-text>
        </v-card>
      </v-dialog> -->
    </v-app-bar>
  </template>
  <script setup>
  import { onMounted, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import auth from '../auth/index.vue';
  import { apiGetUserSearchHistory } from '../../apis/user/user';
//   import hotList from '../../components/hotList.vue';
  const { clickEvent } = defineProps({
    clickEvent: {
      type: Function,
      default: (type, data) => { }
    }
  })
//   const searchInputRef = ref()
  const showSearch = ref(false)
//   const searchKey = ref("")
  const router = useRouter()
//   const openSearch = () => {
//     showSearch.value = !showSearch.value
//   }
//   const serarchHistory = ref([])
//   onMounted(()=>{
//     apiGetUserSearchHistory().then(({data})=>{
//       serarchHistory.value = data.data
//     })
//   })
//   const search = () => {
//     showSearch.value = false
//     if (!searchKey.value || searchKey.value.length == 0) {
//       router.push({
//         path: "/",
//       })
//       return
//     }
//     router.push({
//       path: "/video/search/" + searchKey.value,
//     })
//     // if(serarchHistory.value.indexOf(searchKey.value)==-1) 
//     //   serarchHistory.value.unshift(searchKey.value)
//     apiGetUserSearchHistory().then(({data})=>{
//       serarchHistory.value = data.data
//     })
//     searchKey.value = ""
//   }
  </script>
  <style scoped>
  ::v-deep(.v-field__outline) {
    --v-field-border-width: 0px
  }
  
  @media only screen and (min-width: 700px) {
    .d700-hide {
      display: none;
    }
  }
  
  @media only screen and (max-width: 700px) {
    .e700-hide {
      display: none;
    }
  }
  </style>
<template>
  <div class="index-main-container">

    <EquipmentCard/>

  </div>
</template>

<script setup>
import {useUserStore} from "@/store/user";
import {onMounted, ref, watch} from "vue";
import {appendUrls, getRequestBaseUrl} from "@/utils/request";
import {useThemeStore} from "@/store/theme";
import {storeToRefs} from "pinia";
import EquipmentCard from "./components/EquipmentCard"
//==========================================================================================
//                                        属性
//==========================================================================================
const userStore = useUserStore();
const themeStore = useThemeStore()

const {theme} = storeToRefs(themeStore);

const user = userStore.getUser;
let uri = user ? user.avatarUri : "default.jpg";
let avatarUrl = ref(appendUrls(getRequestBaseUrl(), "api", uri));

const screenWidth = ref(window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
//==========================================================================================
//                                        方法
//==========================================================================================


onMounted(() => {
  window.onresize = () => {
    screenWidth.value = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
  }
})

</script>

<style lang="scss" scoped>

.index-main-container {
  padding: 18px;

  .row {
    margin: 10px 0 10px 0;

    .col {
      padding-right: 10px;

      .box-card {
        overflow: hidden;

        .card-title {
          font-size: 18px;
          font-weight: 700;
        }

      }
    }

    .col:last-child {
      padding-right: 0;
    }
  }

  .row:first-child {
    margin: 0 0 10px 0;
  }

}

.circle {
  border-radius: 50%;
  height: 10px;
  width: 10px;
  display: inline-block;
}

.item {
  margin-bottom: 18px;
}


</style>

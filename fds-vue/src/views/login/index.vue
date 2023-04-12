<template>
  <div class="root-container">
    <!--左上背景圆圈-->
    <div class="background-left"></div>
    <!--右下背景圆圈-->
    <div class="background-right"></div>

    <!--Banner-->
    <div class="login-banner">
      <span style="color: #0e0d0d;font-size: 12rem; line-height: 4">Welcome!</span>
    </div>

    <!--登录/注册/找回密码框-->
    <div class="info-box">
      <!--登录框外容器-->
      <router-view
          name="UserLoginInfoView"
      >
      </router-view>
    </div>


  </div>
</template>

<script setup>
console.log(JSON.parse(sessionStorage.getItem("editableTabsValue")))
</script>

<style lang="scss" scoped>

.root-container {
  position: relative;
  overflow: hidden;
  width: 100%;
}

.background-left {
  position: absolute;
  width: 35vw;
  height: 35vw;
  border-radius: 50%;
  left: -10vw;
  top: -10vw;
  background-image: linear-gradient(to bottom, #44a241, #44a241, #3e8f52, #38757a);
}


.background-right {
  position: absolute;
  width: 45vw;
  height: 45vw;
  border-radius: 50%;
  right: -10vw;
  bottom: -10vw;

  background-image: linear-gradient(#db5a22, #e67827, #eea22e, #f5bd34);
}

.login-banner {
  min-height: 43vh;
  min-width: 35vw;
  top: 21vh;
  right: 45vw;
  position: absolute;
  border-radius: 7px;
  box-shadow: 0 0 18px #424040;
  text-align: center;
  background-color: rgba(255, 255, 255, 0.3) !important;
  -webkit-backdrop-filter: saturate(150%) blur(8px) !important;
  backdrop-filter: saturate(150%) blur(8px) !important;
}

.info-box {
  width: 20vw;
  right: 12vw;
  top: 20vh;
  position: absolute;
  border-radius: 7px;
  padding: 5px;

}

/*2500px +*/
@media all and (min-width: 2500px) {

}

/* 1900 ~ 2499 */
@media all and (min-width: 1900px) and (max-width: 2499px) {

  .info-box {
    width: 25vw;
    top: 20vh;
  }

}

/* 1462 ~ 1899*/
@media (min-width: 1462px) and (max-width: 1899px) {

  .info-box {
    width: 30vw;
    right: 7vw;
  }
}

@media (min-width: 769px) and (max-width: 1461px) {
  .info-box {
    width: 30vw;
    right: 7vw;
  }
}

/* 横向放置的手机及分辨率更小的设备 */
@media (max-width: 768px) {
  .login-banner {
    display: none;
  }

  .info-box {
    width: 70vw;
    min-width: 350px;
    left: 50%;
    transform: translateX(-50%);
  }

}
</style>

<template>
  <div class="app-wrapper">
    <el-container>
      <!-- side bar -->
      <el-aside width="collapse" class="sidebar-container">
        <Menu/>
      </el-aside>

      <!-- Main -->
      <el-container>
        <!-- 头部 -->
        <el-header>
          <Header/>
        </el-header>
        <!-- 主内容区 -->
        <el-main class="main-content">
          <Tabs/>
          <router-view
              name="Redirect">
          </router-view>
        </el-main>
        <!-- Footer -->
        <el-footer>
          <Footer/>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import Menu from '@/layout/menu'
import Header from '@/layout/header'
import Footer from '@/layout/footer'
import Tabs from '@/layout/tabs'</script>

<style lang="scss" scoped>

.app-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
}

.sidebar-container {
  @include background-color("color-primary");
  height: 100%;
  border-right: 1px groove #e7e7e7;
}

.main-content {
  @include background-color("background-color");
}

.el-container {
  height: 100%
}

.el-header {
  padding-left: 0;
  padding-right: 0;
}

:deep(ul.el-menu) {
  border-right-width: 0;
}


</style>

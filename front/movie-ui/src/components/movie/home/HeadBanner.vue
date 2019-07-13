<template>
    <div class="head-banner">
        <div class="wrap">
            <Logo class="logo"/>
            <SearchBox class="search-box" v-bind="$attrs" v-on="$listeners"/>
          <LoginBox>登录</LoginBox>
        </div>
    </div>
</template>

<style lang="less" scoped>

.head-banner {
    position: relative;
    z-index: 199;
    height: 100px;

    .wrap {
        position: relative;
        height: 100%;
    }

    .logo {
        position: absolute;
        left: 24px;
        top: 20px;
        font-size: 16px;
    }

    .search-box {
        position: absolute;
        left: 340px;
        top: 30px;
        width: 460px;
        height: 40px;
    }
}
</style>

<script>
import Logo from "@/components/Logo.vue"
import SearchBox from "@/components/movie/home/SearchBox.vue"
import LoginBox from "@/components/movie/home/LoginBox.vue"

export default {
    components: {
        Logo,
        SearchBox,
        LoginBox
    },

    methods: {
    },
}
</script>


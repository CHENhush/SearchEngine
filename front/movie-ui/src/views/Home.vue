<template>
    <div class="home">
        <div class="logo-wrap">
            <Logo class="logo"/>
        </div>

        <div class="search-wrap">
            <SearchBox/>
        </div>

        <div class="wrap">
            <Rate/>
        </div>
    </div>
</template>

<style lang="less" scoped>

.home {
    // 防止页面视口太小，导致元素错位
    min-width: 1000px;
}

.logo-wrap {
    margin: 100px 0 20px 0;
    text-align: center;
    .logo {
        font-size: 1.6em;
    }
}


.search-wrap {
    text-align: center;
}

</style>

<script>
// @ is an alias to /src
import Logo from "@/components/Logo.vue";
import SearchBox from "@/components/SearchBox.vue";
import Rate from '@/components/common/Rate.vue'

export default {
    name: "home",
    components: {
        Logo,
        SearchBox,
        Rate

    },

};
</script>

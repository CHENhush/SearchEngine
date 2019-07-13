<template>
    <div class="topbar">
        <Logo class="logo"/>
        <!-- <SearchBox class="search-box" :initialSearchText="searchText"/> -->
        <SearchBox class="search-box"/>
    </div>
</template>


<style lang="less" scoped>
.topbar {
    height: 60px;
    width: 100%;
    min-width: 1000px;
    background: #fff;
    border-bottom: 1px solid #ebebeb;
    position: fixed;
    top: 0;
    z-index: 999;
    box-shadow: 0 0 5px #888;

    .logo {
        display: block;
        float: left;
        margin: 10px 0 0 20px;
        font-size: 0.7em;
    }

    .search-box {
        height: 40px;
        margin: 10px 0 0 40px;
    }
}
</style>

<script>
import Logo from "@/components/Logo.vue";
import SearchBox from "@/components/SearchBox.vue";

export default {
    components: {
        Logo,
        SearchBox
    },
    data() {
        return {
            // 绑定路径参数到变量中
            // searchText: this.$route.params.searchText,
            searchText: this.$route.query.searchText,
        }
    },
};

</script>


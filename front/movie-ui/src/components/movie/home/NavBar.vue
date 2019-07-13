<template>
    <div class="navbar-wrap">
        <div class="wrap">
            <!-- <h1>懂她ar</h1> -->
            <ul class="nav-list">
                <li class="nav-item" :class="{'active': this.$route.path == '/movie/home'}">
                    <a href="/movie/home">首页</a>
                </li>
                <li class="nav-item" :class="{'active': this.$route.path == '/movie/shome'}">
                    <a href="/movie/shome">搜索</a>
                </li>
                <li class="nav-item" :class="{'active': this.$route.path == '/movie/stat'}">
                    <a href="/movie/stat">统计</a>
                </li>
               <!--<li class="nav-item" :class="{'active': this.$route.path == '/movie/qa'}">-->
                <!--<a href="/movie/qa">问答系统</a>-->
              <!--</li>-->
            </ul>
        </div>
    </div>
</template>

<style lang="less" scoped>
.navbar-wrap {
    background: #333f51;
    height: 48px;
    line-height: 48px;
    // background-color: #333f51;

    .nav-item {
        display: inline-block;
        box-sizing: border-box;
        height: 45px;
        line-height: 42px;

        > a {
            display: inline-block;
            padding: 0 25px;
            font-size: 18px;
            font-weight: 700;
            text-align: center;
            color: #fff;
        }

        &.active {
            border-bottom: 3px solid #ff0676;
        }
    }
}
</style>

<script>
export default {
  method:{


  }
};
</script>


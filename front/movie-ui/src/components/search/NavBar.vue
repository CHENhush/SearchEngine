<template>
    <div class="navbar-wrap">
        <div class="navbar-inner">
            <!-- <h1>懂她ar</h1> -->
            <ul class="nav-list">
                <li class="nav-item active">
                    <a href>文章</a>
                </li>
                <li class="nav-item">
                    <a href>吃货</a>
                </li>
                <li class="nav-item">
                    <a href>网页</a>
                </li>
                <li class="nav-item">
                    <a href>网页</a>
                </li>
                <li class="nav-item">
                    <a href>网页</a>
                </li>
            </ul>
        </div>
    </div>
</template>

<style lang="less" scoped>
.navbar-wrap {
    padding: 66px 0 0 120px;
    background: #f8f8f8;
    height: 40px;

    .nav-item {
        display: inline-block;

        > a {
            display: inline-block;
            font-weight: 700;
            width: 60px;
            line-height: 36px;
            text-align: center;
            color: #323232;
        }

        &.active {
            
            border-bottom: 2px solid #38f;
        }
    }
}
</style>

<script>
export default {};
</script>


<template>
    <div class="movie-search-home-page">
        <div class="logo-wrap">
            <Logo class="logo"/>
        </div>
        <div class="search-box-wrap">
            <SearchBox/>
        </div>
        <div class="rec-wrap">
            <div class="rec-column hot-search">
                <i class="iconfont icon-hot title-icon"></i>
                <div class="title">热门搜索</div>
                <ol>
                    <li v-for="(item, index) in realTimeHotSearchList" :key="index">
                        <span :class="`hot-index hot${index + 1}`">{{ index + 1 }}</span>
                        <a target="_blank" :href="`/movie/detail/${item.id}`">{{ item.name }}</a>
                    </li>
                </ol>
            </div>
            <div class="rec-column search-history hot-search">
                <i class="iconfont icon-clock title-icon"></i>
                <span class="delete-history" @click="clearSearchHistroy">
                    <i class="iconfont icon-delete"></i>
                    <span>清空</span>
                </span>
                <div class="title">搜索历史</div>
                <ol>
                    <li v-for="(item, index) in searchHistoryList" :key="index">
                        <span :class="`hot-index hot${index + 1}`">{{ index + 1 }}</span>
                        <a target="_blank" :href="`/movie/search?searchText=${item}`">{{ item }}</a>
                    </li>
                </ol>
            </div>
        </div>
    </div>
</template>

<style lang="less">

.movie-search-home-page {
    .logo-wrap {
        text-align: center;
        margin: 80px auto 20px;

        .logo {
            font-size: 2em;
        }
    }
    .search-box-wrap {
        text-align: center;

        .search-box {
            width: 600px;

            .search-btn {
                width: 50px;
            }

            .info-wrap {
                width: 500px;
            }
        }
    }

    .rec-wrap {
        width: 980px;
        padding-top: 60px;
        margin: 0 auto;

        .rec-column {
            float: left;
            position: relative;
            padding: 20px;
            width: 280px;
            height: 360px;
            border: 1px solid #ddd;

            .title {
                margin-left: 40px;
                margin-bottom: 10px;
                font-size: 20px;
                line-height: 30px;
            }

            .title-icon {
                position: absolute;
                font-size: 30px;
                color: #d284a4;
                font-weight: 700;
            }

            li {
                border-bottom: 1px solid #f5f5f5;
                height: 32px;
                line-height: 32px;

                padding: 0 30px 0 5px;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                word-wrap: normal;
            }
        }



        .hot-search {
            margin: 0 20px 0 155px;

                li {

                    .hot-index {
                        display: inline-block;
                        width: 16px;
                        height: 16px;
                        line-height: 16px;
                        text-align: center;
                        color: #fff;
                        background: #a8c2f3;
                        font-size: 12px;

                        &.hot1 {
                            background-color: #f54545;
                        }

                        &.hot2 {
                            background-color: #fd9393;
                        }

                        &.hot3 {
                            background-color: #ff8547;
                        }

                        &.hot4 {
                            background-color: #ffac38;
                        }
                    }

                    a {
                        margin-left: 8px;
                        color: #000;
                    }
            }
        }

        .search-history {
            margin: 0 !important;

            .delete-history {
                position: absolute;
                top: 28px;
                right: 40px;
                font-size: 16px;
                color: #555;
                cursor: pointer;

                &:hover {
                    color: #ff0676;
                }
                i {
                    padding-right: 4px;
                }
            }
        }
    }
}

</style>

<script>
import Logo from '@/components/Logo.vue'
import SearchBox from '@/components/movie/home/SearchBox.vue'

export default  {
    components: {
        Logo,
        SearchBox
    },

    data() {
        return {
            realTimeHotSearchList: [
                // "徒手攀岩",
                // "驭风男孩",
                // "夏目友人帐",
                // "惊奇队长",
                // "速成家庭",
                // "阿丽塔：战斗天使",
                // "传控",
                // "羁押",
                // "集中营里的摄影师",
                // "东京偶像"
            ],


            searchHistoryList: [],
        }
    },

    created() {
        this.fetchData()
        this.bindStorageEvent()
    },

    methods: {
        fetchData() {
            this.fetchSearchHistory()
            this.fetchRealTimeHotSearchList()
        },

        fetchSearchHistory() {
            let searchHistory = localStorage.getItem("searchHistory")

            if (searchHistory) {
                // 取前10个
                this.searchHistoryList = searchHistory.split(",").slice(0, 10)
            } else {
                this.searchHistoryList = []
            }
        },

        fetchRealTimeHotSearchList() {
            this.axios.get("/movie/realTimeHotSearch").then(response => {
                this.realTimeHotSearchList = response.data.rows;
            });
        },

        bindStorageEvent() {
            // localStorage 改变时，其它页会触发该事件，但是当前页不会触发
            window.addEventListener('storage', event => {
                if (event.key == "searchHistory") {
                    this.fetchSearchHistory()
                }
            })
        },

        clearSearchHistroy() {
            localStorage.removeItem("searchHistory")
            this.fetchSearchHistory()
        }
    }
}

</script>

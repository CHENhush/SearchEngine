<template>
    <div class="movie-detail-page">
        <HeadBanner @searchCallback="searchCallback" 
            @suggestionClickCallback="suggestionClickCallback" />
        <NavBar/>
        <div class="wrap">
            <div class="filter-wrap">
                <ul class="filter-list">
                    <li v-for="(item, index) in sortTypeList" :key="index" 
                        :class="{'active': index === sortTypeId }"
                        @click="changeSortTypeId(index)">
                        {{ item }}
                    </li>
                </ul>          
                <ul class="filter-list">
                    <!-- 
                        <li v-for="(item, index) in regionFilterList" :key="index" 
                            :class="{'active': index === regionId }"
                            @click="regionId = index">
                            {{ item }}
                        </li>
                    -->
                    <li v-for="(item, index) in regionFilterList" :key="index"
                        :class="{'active': index === regionId }"
                        @click="changeRegionId(index)">
                        {{ item }}
                    </li>
                </ul>

                <ul class="filter-list">
                    <li v-for="(item, index) in minsFilterList" :key="index"
                        :class="{'active': index === minsId }"
                        @click="changeMinsId(index)">
                        {{ item }}
                    </li>
                </ul>

                <ul class="filter-list">
                    <li v-for="(item, index) in yearRangeFilterLabelList" :key="index"
                        :class="{'active': index === yearRangeId }"
                        @click="changeYearRangeId(index)">
                        {{ item }}
                    </li>
                </ul>

                
            </div>
        </div>

        <div class="wrap">
            <div class="loading-wrap" style="height: 550px;" v-if="loadingSearchResultList" v-loading="loadingSearchResultList">

            </div>

            <div class="search-result-wrap" v-if="searchResultList.length > 0">
                <div class="search-result-list">
                    <div class="search-result-item clearfix" v-for="(item, index) in searchResultList" :key="index">
                        <div class="cover-wrap">
                            <a :href="`/movie/detail/${item.id}`" target="_blank">
                                <img :src="item.coverUrl || 'https://img3.doubanio.com/f/movie/30c6263b6db26d055cbbe73fe653e29014142ea3/pics/movie/movie_default_large.png'">
                            </a>
                        </div>
                        <div class="info-wrap">
                            <div class="rate-wrap">
                                <el-rate
                                    v-show="item.avgScore"
                                    v-model="rateResultList[index]"
                                    disabled
                                    :max="5"
                                    text-color="#ff9900"
                                    score-template="{value}">
                                </el-rate>
                                <span class="el-rate__text" style="color: rgb(255, 153, 0);">{{ item.avgScore || '暂无评分' }}</span>
                            </div>

                            <div class="p-title p-ellipsis">
                                <a :href="`/movie/detail/${item.id}`" target="_blank">
                                    <span class="p-name" v-html="item.name"></span>
                                    <span class="p-year" v-show="item.year" v-text="`(${item.year})`"></span>
                                </a>
                            </div>
                            <p class="p-alias p-ellipsis" v-show="item.alias">{{ item.alias }}</p>
                            <p class="p-director p-ellipsis" v-show="item.directors">导演：{{ item.directors }}</p>
                            <p class="p-actor p-ellipsis" v-show="item.actors">主演：{{ item.actors }}</p>
                            <p class="p-genres p-ellipsis" v-show="item.genres">类型：{{ item.genres }}</p>                        
                            <p class="p-regions p-ellipsis" v-show="item.regions">地区：{{ item.regions }}</p>
                            <p class="p-mins p-ellipsis" v-show="item.mins">时长：{{ item.mins }}分钟</p>
                        </div>
                    </div>
                </div>

                <div class="pagination-wrap">
                    <el-pagination
                        background
                        layout="prev, pager, next"
                        :current-page="page"
                        :total="total"
                        @current-change="handleCurrentChange"
                    ></el-pagination>
                </div>
            </div>

            <div class="no-result-wrap" v-if="searchResultList.length == 0 && !loadingSearchResultList">
                
            </div>
        </div>

        <div class="wrap">
        </div>
    </div>
</template>

<style lang="less">

.movie-detail-page {
    .filter-wrap {
        padding: 50px 0 10px;

        .filter-list {
            margin-bottom: 10px;

            li {
                display: inline-block;
                margin: 6px 8px 0 0;
                padding: 1px 8px;
                line-height: 1.5;
                color: #333;
                border-radius: 4px;

                cursor: pointer;

                &:hover {
                    color: #00a1d6;
                }

                &.active {
                    color: #fff;
                    background-color: #00a1d6;
                }
            }
        }
    }

    .search-result-wrap {
        margin-top: 30px;

        .search-result-list {
            .search-result-item {
                margin-bottom: 40px;

                .cover-wrap {
                    float: left;
                    margin-right: 30px;

                    img {
                        width: 150px;
                        height: 225px;
                    }
                }

                .info-wrap {
                    float: left;
                    position: relative;
                    width: 920px;

                    .p-title {
                        display: block;
                        margin-bottom: 10px;
                        font-size: 30px;
                        line-height: 1.2;
                        color: #333;
                        width: 700px;

                        a {
                            color: #333;
                            
                            &:hover {
                                color: #f25d8e;
                            }
                        }

                        .p-year {
                            padding-left: 15px;
                        }
                    }

                    .p-alias {
                        margin-bottom: 20px;
                        font-size: 22px;
                        color: #999;
                    }

                    .p-director {
                        font-size: 18px;
                        line-height: 1.5;
                    }

                    .p-actor,
                    .p-genres,
                    .p-regions,
                    .p-mins {
                        font-size: 18px;
                        line-height: 1.5;
                    }

                    .rate-wrap {
                        position: absolute;
                        top: 0;
                        right: 0;

                        .el-rate {
                            display: inline-block
                        }
                        .el-rate__icon {
                            font-size: 30px;
                        }
                        .el-rate__text {
                            font-size: 26px;
                        }
                    }

                }
            }
        }

        .pagination-wrap {
            padding: 30px 27px 35px;
            text-align: center;

            .el-pagination.is-background .btn-next, 
            .el-pagination.is-background .btn-prev, 
            .el-pagination.is-background .el-pager li {
                padding: 0 13px;
                height: 38px;
                line-height: 38px;
            }
        }
    }

    .no-result-wrap {
        margin: 30px auto 0;
        width: 978px;
        background: url(https://s1.hdslb.com/bfs/static/jinkela/search/asserts/no-data.png) no-repeat 50% 50%;
        background-color: #fff;
        height: 240px;
        line-height: 240px;
    }

}


</style>

<script>
import HeadBanner from '@/components/movie/home/HeadBanner.vue'
import NavBar from '@/components/movie/home/NavBar.vue'
import { setInterval } from 'timers';

export default {
    components: {
        HeadBanner,
        NavBar
    },
    data() {
        return {
            sortTypeList: ["综合排序", "最多评论", "最高评分", "最新上映"],
            sortTypeId: Number(this.$route.query.sortTypeId) || 0,

            regionFilterList: ["全部地区", "中国大陆", "美国", "香港", "台湾", "日本", "韩国", "英国", "法国", "德国", "意大利", "西班牙", "印度", "泰国", "俄罗斯", "伊朗", "加拿大", "澳大利亚", "爱尔兰", "瑞典", "巴西", "丹麦"],
            regionId: Number(this.$route.query.regionId) || 0,

            minsFilterList: ["全部时长", "10分钟以下", "10-30分钟", "30-60分钟", "60-90分钟", "90-120分钟", "120分钟以上"],
            minsId: Number(this.$route.query.minsId) || 0,

            yearRangeFilterLabelList: ["全部年代", "2019", "2018", "2017", "2016", "2010年代", "2000年代", "90年代", "80年代", "70年代", "60年代", "更早"],
            yearRangeId: Number(this.$route.query.yearRangeId) || 0,

            searchText: this.$route.query.searchText,
            searchResultList: [],
            rateResultList: [],
            loadingSearchResultList: false,

            page: Number(this.$route.query.page) || 1, // 页码
            total: null, // 总记录数
            value5: 3.7,
        }
    },

    methods: {
        scrollToTop(interval = 10, step = 400) {
            let timer = window.setInterval(() => {
                let top = document.documentElement.scrollTop || document.body.scrollTop
                if (top === 0) {
                    window.clearInterval(timer)
                    return
                }
                top = Math.max(top - step, 0)
                document.documentElement.scrollTop = top
                document.body.scrollTop = top
                
            }, interval)
        },

        changeSortTypeId(index) {
            this.page = 1
            this.sortTypeId = index
        },

        changeRegionId(index) {
            this.page = 1
            this.regionId = index
            // this.doSearch()
        },

        changeMinsId(index) {
            this.page = 1
            this.minsId = index
        },

        changeYearRangeId(index) {
            this.page = 1
            this.yearRangeId = index
        },

        handleCurrentChange(page) {
            this.page = page
            this.doSearch()
        },

        searchCallback(searchText) {
            this.page = 1
            this.searchText = searchText 
        },

        doSearch() {
            let query = JSON.parse(JSON.stringify(this.$route.query))
            query['searchText'] = this.searchText
            query['page'] = this.page
            query['regionId'] = this.regionId      
            query['minsId'] = this.minsId
            query['yearRangeId'] = this.yearRangeId
            query['sortTypeId'] = this.sortTypeId
            
            
            /// this.$router.push({path: '/movie/search', query})    path还是当前页面，所以可以只传入query参数
            this.$router.push({query})            

            // 准备加载的状态
            this.loadingSearchResultList = true
            this.searchResultList = []
            this.scrollToTop()
            
            // 开始加载
            this.axios.get('movie/search', { params: query }).then(response => {
                this.loadingSearchResultList = false
                this.searchResultList = response.data.rows
                this.rateResultList = this.searchResultList.map(item => item.avgScore && item.avgScore / 2)
                this.total = response.data.total
            });
        },

        suggestionClickCallback(searchText) {

        },

    },

    created() {
        this.doSearch()
    },
    watch: {
        'searchText' () {
            this.doSearch()
        },

        'regionId' () {
            this.doSearch()
        },

        'yearRangeId' () {
            this.doSearch()
        },

        'minsId' () {
            this.doSearch()
        },

        'sortTypeId' () {
            this.doSearch()
        },
        // page 单独考虑
        // 'page' () {
        //     this.doSearch()
        // }
        
        '$route' () {
            // 更新路由参数
            this.yearRangeId = Number(this.$route.query.yearRangeId) || 0
            this.regionId = Number(this.$route.query.regionId) || 0
            this.minsId = Number(this.$route.query.minsId) || 0
            this.sortTypeId = Number(this.$route.query.sortTypeId) || 0
            this.searchText = this.$route.query.searchText || ""
            this.page = Number(this.$route.query.page) || 1
            
        }
    }
}

</script>


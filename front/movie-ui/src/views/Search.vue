<template>
    <div class="search-page">
        <TopBar/>
        <NavBar/>

        <!-- 结果筛选 -->
        <div class="search-filter-wrap">
            <span class="total-tip" v-text="`BuKaoPu为您找到相关结果约 ${total} 个`" v-show="total"></span>
            <el-date-picker
                style="float: right;"
                v-model="dateFilterValue"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions"
            ></el-date-picker>
        </div>
        <div class="container clearfix">
            <div class="container-left">
                <div class="search-result-list">
                    <!-- 搜索结果 -->
                    <div
                        class="search-result-item clearfix"
                        v-for="item in searchResultList"
                        :key="item.urlObjectId"
                    >
                        <div class="item-head">
                            <a :href="item.url" class="title" target="_blank" v-html="item.title"></a>
                            <span class="split">-</span>
                            <span class="source">
                                来源：
                                <span>伯乐在线</span>
                            </span>
                            <span class="score">
                                得分：
                                <span v-text="item.score"></span>
                            </span>
                        </div>
                        <div class="item-body">
                            <div class="front-img-wrap">
                                <a :href="item.url" target="_blank">
                                    <img :src="item.frontImageUrl">
                                </a>
                                <!-- <a :href="item.url" target="_blank"><img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551803202182&di=98dea8105c1b0a703ea1adf409da9ebb&imgtype=0&src=http%3A%2F%2Fs16.sinaimg.cn%2Fmw690%2F006dTkDdzy7h30Mkri76f%26690"></a> -->
                            </div>
                            <div class="content-wrap">
                                <div class="content">
                                    <span style="color: #507858;">{{ item.createDate | dateFormat('YYYY-MM-DD') }}</span>
                                    <span>&nbsp;-&nbsp;</span>
                                    <span v-html="item.content"></span>
                                </div>
                            </div>
                            <div class="foot-wrap">
                                <a :href="item.url" target="_blank">{{item.url}}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 搜索不到 -->
            <div class="content-not-found" v-if="total === 0">
                <p class="p-sorry">很抱歉，没有找到与 "<em>{{ searchText }}</em>"相关的文章</p>
                <p class="tip-head">温馨提示：</p>
                <ul class="tip-list">
                    <li>请检查您的输入是否正确</li>
                    <li>请检查您的筛选条件是否合理</li>
                    <li>如网页未收录或者新站未收录，请提交网址给我们</li>
                </ul>
            </div>

            <div class="container-right" v-if="total">
                <!-- 实时热搜榜 -->
                <div class="real-time-hot-search">
                    <h3 class="p-title">
                        <em>实时</em>热搜榜
                    </h3>
                    <ol>
                        <li v-for="(item, index) in realTimeHotSearchList" :key="index">
                            <span :class="`hot-index hot${index + 1}`">{{ index + 1 }}</span>
                            <a target="_blank" :href="`/search?searchText=${item}`">{{ item }}</a>
                        </li>
                    </ol>
                </div>
            </div>
        </div>

        <div class="pagination-wrap" v-if="total">
            <el-pagination
                background
                layout="prev, pager, next"
                :current-page="page"
                :total="total"
                @current-change="handleCurrentChange"
            ></el-pagination>
        </div>
    </div>
</template>

<style lang="less" scoped>

.search-filter-wrap {
    margin: 20px 0 20px 0;
    padding: 0 0 0 120px;
    height: 45px;
    line-height: 45px;
    width: 700px;

    .total-tip {
        font-size: 13px;
        color: #666;
    }
}

.container {
    width: 1280px;
}

.container-left {
    float: left;
    width: 640px;
    padding-left: 120px;
}

.container-right {
    border-left: 1px solid #e1e1e1;
    float: right;
    padding: 0 0 40px 17px;
    width: 384px;

    .real-time-hot-search {
        .p-title {
            font-size: 16px;
            line-height: 32px;
            font-weight: 700;
        }

        ol {
            li {
                border-top: 1px solid #f5f5f5;
                height: 32px;
                line-height: 32px;

                padding-right: 30px;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                word-wrap: normal;

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
                    color: #00c;
                }
            }
        }
    }
}

.search-result-list {
    .search-result-item {
        margin-bottom: 20px;

        .item-head {
            line-height: 2;
        }

        .item-body {
            line-height: 1.2;

            .front-img-wrap {
                float: left;
                margin-right: 17px;
                img {
                    width: 120px;
                    height: 80px;
                }
            }
            .content-wrap {
                .content {
                    text-overflow: ellipsis;
                    font-size: 14px;
                    line-height: 1.3;
                }
            }

            .foot-wrap {
                line-height: 1.54;
            }
        }

        em {
            color: #d90909;
        }

        .split {
            margin: 0 10px;
        }

        .source span {
            color: green;
        }

        .score {
            margin-left: 20px;
            span {
                color: green;
            }
        }
    }
}

.pagination-wrap {
    margin: 40px 0 60px;
    padding-left: 120px;
}

// 修改el-pagination每个按钮的默认模式
.el-pagination.is-background .btn-next,
.el-pagination.is-background .btn-prev,
.el-pagination.is-background .el-pager li {
    font-size: 16px;
    height: 36px;
    min-width: 36px !important;
    line-height: 36px;
    border: 1px solid rgb(103, 154, 226);
}

.content-not-found {
    padding: 45px 0 25px 120px;

    .p-sorry {
        font-size: 22px;
        font-family: microsoft yahei;
        color: #000;

        em {
            color: #c00;
        }
    }

    .tip-head {
        margin-top: 20px;
        color: #666;
        font-size: 18px;
        line-height: 40px;
    }

    .tip-list {
        li {
            color: #333;
            line-height: 30px;
            font-size: 16px;
            padding-left: 30px;
            list-style-position: inside;
            list-style-type: disc;
        }
    }
}



</style>

<script>
import TopBar from "@/components/search/TopBar.vue";
import NavBar from "@/components/search/NavBar.vue";
import SearchResultItem from "@/components/search/SearchResultItem.vue";
/// import DB from '@/data/data.json'
import querystring from "querystring";

export default {
    name: "search",
    components: {
        TopBar,
        NavBar,
        SearchResultItem
    },
    data() {
        return {
            searchResultList: [], // 保存搜索到的数据

            /// searchText: this.$route.params.searchText,
            searchText: this.$route.query.searchText, // 搜索的文本

            page: Number(this.$route.query.page) || 1, // 页码
            total: null, // 总记录数

            // 日期选择
            pickerOptions: {
                shortcuts: [
                    {
                        text: "一天内",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(
                                start.getTime() - 3600 * 1000 * 24
                            );
                            picker.$emit("pick", [start, end]);
                        }
                    },
                    {
                        text: "一周内",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(
                                start.getTime() - 3600 * 1000 * 24 * 7
                            );
                            picker.$emit("pick", [start, end]);
                        }
                    },
                    {
                        text: "一月内",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(
                                start.getTime() - 3600 * 1000 * 24 * 30
                            );
                            picker.$emit("pick", [start, end]);
                        }
                    },
                    {
                        text: "三月内",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(
                                start.getTime() - 3600 * 1000 * 24 * 90
                            );
                            picker.$emit("pick", [start, end]);
                        }
                    },
                    {
                        text: "半年内",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(
                                start.getTime() - 3600 * 1000 * 24 * 180
                            );
                            picker.$emit("pick", [start, end]);
                        }
                    },
                    {
                        text: "一年内",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(
                                start.getTime() - 3600 * 1000 * 24 * 365
                            );
                            picker.$emit("pick", [start, end]);
                        }
                    }
                ]
            },

            dateFilterValue: this.getDateFilterValue(),  // 日期搜索条件

            // 实时热搜
            realTimeHotSearchList: [
                // '康宁研发柔性玻璃',
                // '中国10座超级城市',
                // '卡纳瓦罗接手国足',
                // '养老金足额发放',
                // '鹿晗发民国表情包',
                // '华为起诉美国政府',
                // '三成男性无陪产假',
                // '求职救人直接录取',
                // '2019KPL春季赛',
                // '特斯拉年底投产'
            ],
        };
    },
    created() {
        this.getSearchResultList();

        /// this.getRealTimeHotSearchList(); // getSearchResultList先执行完，再去查热点搜索列表，所以放在getSearchResultList的回调中执行
    },
    watch: {
        dateFilterValue(value) {
            if (value === null) {
                this.$route.query.dateFilter = null;
            } else {
                let t1 = this.dateFilterValue[0].getTime();
                let t2 = this.dateFilterValue[1].getTime();
                this.$route.query.dateFilter = `${t1}_${t2}`;
            }
            window.location.href =
                "/search?" + querystring.stringify(this.$route.query);
        }
    },
    methods: {
        handleCurrentChange(page) {
            /// this.page = page
            this.$route.query.page = page;
            window.location.href =
                "/search?" + querystring.stringify(this.$route.query);
        },
        getSearchResultList() {
            this.axios
                .get(
                    "/article/search?" +
                        querystring.stringify(this.$route.query)
                )
                .then(response => {
                    this.total = response.data.total;

                    response.data.rows.forEach(item => {
                        if (item.content) {
                            item.content += "...";
                        }
                        this.searchResultList.push(item);

                        // 先搜索完，再去查热点搜索列表
                        this.getRealTimeHotSearchList();
                    });
                });
            /* 测试数据
            DB.forEach(item => {
                item["content"] = item["content"].substring(0, 200)
                item["front_image_url"] = item["front_image_url"]
                this.searchResultList.push(item)
            });
            */
        },
        getDateFilterValue() {
            let dateFilter = this.$route.query.dateFilter;
            if (!dateFilter) {
                return null;
            }
            let terms = dateFilter.split("_");
            return [new Date(Number(terms[0])), new Date(Number(terms[1]))];
        },

        getRealTimeHotSearchList() {
            this.axios.get("/article/realTimeHotSearch")
                .then(response => {
                    this.realTimeHotSearchList = response.data.rows;
                });
        }
    }
};
</script>

<template>
    <div class="search-box" @blur="hideSearchSuggestion">
        <!--
            v-model: 数据双向绑定
            @keydown.enter: 按下回车搜索
         -->
        <!-- <input v-model="searchText" class="search-input" type="text"
            @keydown.enter="searchHandle"
            @input="showSearchSuggestion"
            @focus="showSearchSuggestion"
            @click="showSearchSuggestion"
            @blur="hideSearchSuggestion"

            去掉 @input="showSearchSuggestion"，改为对 searchText的watch
            > -->
        <input v-model="searchText" class="search-input" type="text"
            @keydown.enter="searchHandle"
            @focus="showSearchSuggestion"
            @click="showSearchSuggestion"
            @blur="hideSearchSuggestion"
            >
        <button class="search-btn" @click="searchHandle">
            <i class="iconfont icon-sousuo"></i>
        </button>

        <!-- isShowSearchSuggestion决定是否显示搜索提示框 -->
        <div class="search-suggestion"
            v-show="isShowSearchSuggestion"
            @mouseenter="enterSearchSuggestion"
            @mouseleave="leaveSearchSuggestion">
            <ul>
                <li v-for="(item, index) in searchSuggestionList" :key="index">
                    <a :href="`/movie/detail/${item.id}`">
                        <!-- 没有图片就用默认的来凑 -->
                        <img :src="item.coverUrl || 'https://img3.doubanio.com/f/movie/30c6263b6db26d055cbbe73fe653e29014142ea3/pics/movie/movie_default_large.png'" width="50" height="70">
                        <div class="info-wrap">
                            <div class="p-ellipsis">
                                <span class="p-name" v-html="getHighlightText(item)"></span>
                                <span class="p-year" v-text="`(${item.year})`"></span>
                            </div>
                            <p class="p-alias p-ellipsis">{{ item.alias }}</p>
                        </div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
// 导入nodejs的querystring
import querystring from 'querystring'

export default {
    data() {
        return {
            searchText: this.$route.query.searchText || "",
            searchSuggestionList: [],
            isShowSearchSuggestion: false,  // 是否显示搜索提示
            isMouseInSearchSuggestion: false,   // 鼠标是否在搜索提示框中
        }
    },
    methods: {
        getMovieSearchSuggestions() {
            if (this.searchText === "") {
                // 清空列表
                this.searchSuggestionList = [];
                return;
            }
            this.axios.get('movie/suggest', { params: {"keyword" : this.searchText}}).then(response => {
                this.searchSuggestionList = response.data.rows;
            });
        },

        searchHandle() {
            // 判断搜索的内容是否为空
            if (!this.searchText) {
                // alert(1)
                // 如果在搜索首页，则什么都不做
                if (/^\/movie\/shome/.test(this.$route.path)) {
                    return
                }
                // 回到搜索首页
                this.$router.push('/movie/shome')
                return
            }

            // 添加搜索历史
            let searchHistory = localStorage.getItem("searchHistory")
            let searchHistoryList = []
            if (searchHistory) {
                searchHistoryList = searchHistory.split(",").filter(x => x != this.searchText)
            }
            searchHistoryList.unshift(this.searchText)
            localStorage.setItem("searchHistory", searchHistoryList)   // 数组作为localStorage的value时，会自动转为以逗号分隔的字符串

            // 回调自定义事件
            this.$emit("searchCallback", this.searchText)

            // 跳转页面
            let query = JSON.parse(JSON.stringify(this.$route.query))
            query['searchText'] = this.searchText
            this.$router.push({path: '/movie/search', query})
        },

        suggestionClickHandle(searchText) {
            // this.$router.push({
            //     path: '/movie/de', query: {'searchText': searchText }
            // })
            // this.$emit("suggestionClickCallback", searchText)
        },

        showSearchSuggestion() {
            this.isShowSearchSuggestion = true;
            this.getMovieSearchSuggestions();
        },

        hideSearchSuggestion() {
            if (this.isMouseInSearchSuggestion) {
                // 点击了搜索提示框时，搜索输入框会失去焦点，但是此时搜索提示框应该仍保持显示状态，所以什么都不做
                // 结合事件改变isMouseInSearchSuggestion状态，通过该状态来判断
                return;
            }
            this.isShowSearchSuggestion = false;
            this.searchSuggestionList = [];
        },

        getHighlightText(item) {
            if (item.nameHighlight) {
                return item.nameHighlight
            }
            return this.$options.filters.highlight(item.name, this.searchText)
        },

        enterSearchSuggestion() {
            this.isMouseInSearchSuggestion = true;
        },

        leaveSearchSuggestion() {
            this.isMouseInSearchSuggestion = false;
        }
    },

    watch: {
        searchText(newValue, oldValue) {
            this.getMovieSearchSuggestions()
        }
    },
};
</script>


<style lang="less">
// less的@import指令，可导入另一个less文件，当然不限于less文件
@import "./../../../themes/base.less";
// @import "./src/themes/base.less";
.search-box {
    display: inline-block;  // 搜索框设置为内联块
    position: relative;     // 供下拉框定位
    height: 50px;

    .search-suggestion {
        // display: none;
        box-sizing: border-box;
        position: absolute;
        z-index: 1000;
        top: 100%;
        width: 100%;
        border: 1px solid #ccc;
        background: #fff;
        box-shadow: 1px 1px 3px #ededed;

        li {
            border-bottom: 1px solid #eee;
            overflow: hidden;

            padding: 2px 10px;
            cursor: pointer;
            font-size: 14px;
            line-height: 30px;
            text-align: left;

            a {
                display: block;
                overflow: hidden;
                padding: 6px;

                img {
                    float: left;
                    margin-right: 12px;
                    // margin-top: 3px;
                }

                .info-wrap {
                    float: left;
                    width: 325px;
                }

                .p-name {
                    font-size: 18px;
                    line-height: 1.5;
                    color: #333;
                }

                .p-year {
                    margin-left: 10px;
                    font-size: 18px;
                    color: #444;
                }
            }

            em {
                font-weight: 600;
                color: #f25d8e;
            }

            &:hover {
                // background: #f3f3f3;
                background-color: #e5f2fc;
            }
        }
    }

    .search-input {
        box-sizing: border-box;
        padding: 9px 50px 9px 10px;
        width: 100%;
        height: 100%;
        font-size: 14px;
        vertical-align: top;  // vertical-align设置为top或者bottom，与search-btn对齐，该属性放在search-btn处写也可以
        outline: 0;    // 去掉3D边框
        border: 1px solid rgba(0,0,0,.25);
        border-radius: 2px 0 0 2px;

        &:focus {
            border: 1px solid @color-primary;
        }
    }

    .search-btn {
        box-sizing: border-box;
        position: absolute;
        top: 0;
        right: 0;
        min-width: 40px;
        width: 40px;
        height: 100%;
        line-height: 1;
        color: #fff;
        background-color: @color-primary;
        border: none;
        border-radius: 0 2px 2px 0;

        i {
            font-size: 20px;
        }
    }
}
</style>


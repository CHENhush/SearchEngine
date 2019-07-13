<template>
    <div class="search-box" @blur="hideSearchSuggestion">
        <!-- 
            v-model: 数据双向绑定
            @keydown.enter: 按下回车搜索
         -->
        <input v-model="searchText" class="search-input" type="text" 
            @keydown.enter="searchHandle"
            @input="showSearchSuggestion"
            @focus="showSearchSuggestion"
            @click="showSearchSuggestion"
            @blur="hideSearchSuggestion"
            >
        <button class="search-btn" @click="searchHandle">
            <i class="iconfont icon-sousuo"></i>
        </button>

        <!-- isShowSearchSuggestion决定是否显示搜索提示框 -->
        <div class="search-suggestion" 
            v-show="isShowSearchSuggestion">
            <ul>
                <li v-for="(item, index) in searchSuggestionList" :key="index" 
                    >
                    <p>1</p>
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
            searchSuggestionList: [1, 2, 3],
            isShowSearchSuggestion: false,  // 是否显示搜索提示
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
            
        },

        suggestionClickHandle(searchText) {
        },

        showSearchSuggestion() {
            this.isShowSearchSuggestion = true;
            this.getMovieSearchSuggestions();
        },

        hideSearchSuggestion() {
            this.isShowSearchSuggestion = false;  
            this.searchSuggestionList = [];          
        },

        getHighlightText(item) {
            if (item.nameHighlight) {
                return item.nameHighlight
            }
            return this.$options.filters.highlight(item.name, this.searchText)
        },
    },
};
</script>


<style lang="less">
// less的@import指令，可导入另一个less文件，当然不限于less文件
@import "./../../themes/base.less";
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


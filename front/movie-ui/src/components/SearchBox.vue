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
        <button class="search-btn" @click="searchHandle">搜索一下</button>

        <!-- isShowSearchSuggestion决定是否显示搜索提示框 -->
        <div class="search-suggestion" 
            v-show="isShowSearchSuggestion"
            @mouseenter="enterSearchSuggestion"
            @mouseleave="leaveSearchSuggestion">
            <ul>
                <li v-for="(item, index) in searchSuggestionList" :key="index" 
                    @click="suggestionClickHandle(item)"
                    v-html="$options.filters.highlight(item, searchText)">
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
        getArticleSearchSuggestions() {
            if (this.searchText === "") {
                // 清空列表
                this.searchSuggestionList = [];
                return;
            }
            this.axios.get('article/suggest', { params: {"keyword" : this.searchText}}).then(response => {
                this.searchSuggestionList = []
                response.data.forEach(item => {
                    this.searchSuggestionList.push(item.title)
                })
                // this.searchSuggestionList = response.data;
            });
        },

        searchHandle() {
            // 判断搜索的内容是否为空
            if (this.searchText === "") {
                // 如果当前在搜索页，且内容为空，则跳转到首页
                if (/^\/search/.test(this.$route.path)) {
                    this.$router.push('/')                    
                }
                // 如果就在首页，则什么都不做
                return;
            }
        
            let queryParams = this.$route.query;
            queryParams["searchText"] = this.searchText;
            // 因为要重新从服务器获取请求，所以直接修改location.href，而不使用前端路由（不使用单页面特性）
            window.location.href = '/search?' + querystring.stringify(queryParams);
        },

        suggestionClickHandle(searchText) {
            this.searchText = searchText;
            this.searchHandle();
        },

        showSearchSuggestion() {
            this.isShowSearchSuggestion = true;
            this.getArticleSearchSuggestions();
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

        enterSearchSuggestion() {
            this.isMouseInSearchSuggestion = true;
        },

        leaveSearchSuggestion() {
            this.isMouseInSearchSuggestion = false;
        }
    },
};
</script>


<style lang="less">
// less的@import指令，可导入另一个less文件，当然不限于less文件
@import "./../themes/base.less";
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
            padding: 0 10px;
            cursor: pointer;
            font-size: 14px;
            line-height: 30px;
            text-align: left;
            
            em {
                font-weight: 600;
                color: #f25d8e;
            }

            &:hover {
                background: #f3f3f3;
            }
        }
    }

    .search-input {
        box-sizing: border-box;
        padding: 9px 12px;
        width: 600px;
        height: 100%;
        font-size: 1.2em;
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
        width: 120px;
        height: 100%;
        color: #fff;
        font-size: 1.2em;
        background-color: @color-primary;
        border: none;
        border-radius: 0 2px 2px 0;
    }
}
</style>


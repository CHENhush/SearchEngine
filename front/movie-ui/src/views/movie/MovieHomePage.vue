<template>
  <div class="movie-home-page">
    <HeadBanner @searchCallback="searchCallback"
                @suggestionClickCallback="suggestionClickCallback"/>
    <NavBar/>
    <div class="main-container">
      <div class="wrap">
        <!-- 轮播 -->
        <el-carousel trigger="click" height="360px">
          <el-carousel-item v-for="item in carouselImageList" :key="item">
            <a href="#"><img :src="item"></a>
          </el-carousel-item>
        </el-carousel>

        <div class="column hot-column">
          <div class="left-side">
            <div class="column-head">
              <h3 class="column-title">为您推荐</h3>
            </div>
            <div class="column-body">
              <el-carousel trigger="click" height="440px" :autoplay="false" indicator-position="outside">
                <el-carousel-item v-for="item in carouselImageList" :key="item">
                  <a :href="`/movie/detail/${item.id}`" class="movie-item" v-for="item in recommedList " :key="item">
                    <a :href="`/movie/detail/${item.id}`"><img :src="item.coverUrl"></a>
                    <p class="movie-title">{{item.name}}</p>
                  </a>

                </el-carousel-item>
              </el-carousel>
            </div>
          </div>

          <div class="right-side">
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
      </div>
    </div>

  </div>
</template>

<style lang="less" scoped>

  .left-side {
    float: left;
    margin-right: 30px;
    width: 700px;
  }

  .main-container {
    padding: 40px 0;
    overflow: hidden;

    .column {
      margin-top: 40px;

      .column-title {
        font-size: 20px;
        color: #111;
        padding-bottom: 10px;
        border-bottom: 1px solid #eaeaea;
        margin-bottom: 18px;
      }
    }

    .hot-column {
      .movie-item {
        display: block;
        float: left;
        color: #37a;
        font-size: 13px;
        text-align: center;
        margin: 0 25px 10px 0;
        width: 115px;

        img {
          width: 115px;
          height: 160px;
        }

        .movie-title {
          margin: 5px 0 0;
          height: 44px;
          overflow: hidden;
        }
      }

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
  }


</style>

<script>
  import HeadBanner from '@/components/movie/home/HeadBanner.vue'
  import NavBar from '@/components/movie/home/NavBar.vue'

  export default {
    data() {
      return {
        carouselImageList: [
          'http://img5.mtime.cn/mg/2019/03/11/100042.39938311.jpg',
          'http://img5.mtime.cn/mg/2019/03/10/090505.73960044.jpg',
          'http://img5.mtime.cn/mg/2019/03/09/093214.51486938.jpg',
          'http://img5.mtime.cn/mg/2019/03/08/091944.49544262.jpg'
        ],

        recommedImageList: [],
        recommedIdList: [],
        recommedNameList: [],
        recommedList: [],


        realTimeHotSearchList: [
          "徒手攀岩",
          "驭风男孩",
          "夏目友人帐",
          "惊奇队长",
          "速成家庭",
          "阿丽塔：战斗天使",
          "传控",
          "羁押",
          "集中营里的摄影师",
          "东京偶像"
        ],
      }
    },
    components: {
      HeadBanner,
      NavBar
    },
    mounted() {
        this.recommend();
    },

    methods: {
      suggestionClickCallback(searchText) {

      },
      searchCallback(searchText) {
        this.$router.push({
          path: '/movie/search', query: {'searchText': searchText}
        })
      },
      recommend() {
        this.axios.get(`/user/recommend/${window.localStorage.getItem("id")}`).then(response => {
            for (var i = 0; i < response.data.length; i++) {
              this.recommedList.push(response.data[i]);
              console.log(this.recommedList[1]);
              // this.recommedIdList.push(response.data[i].id);
              // this.recommedNameList.push(response.data[i].name);
            }


            this.recommendMovies = response.data;
          }
        )
      }
    },
  }
</script>


<template xmlns="http://www.w3.org/1999/html">
  <div class="movie-detail-page">
    <HeadBanner/>
    <NavBar/>

    <div class="wrap">
      <!-- 防止movieItem为null，渲染时空指针异常 -->
      <div class="detail-wrap clearfix" v-if="movieItem != null">
        <div class="cover-wrap">
          <a :href="`/movie/detail/${this.movieId}`" target="_blank">
            <img
              :src="movieItem.coverUrl || 'https://img3.doubanio.com/f/movie/30c6263b6db26d055cbbe73fe653e29014142ea3/pics/movie/movie_default_large.png'">
          </a>
        </div>
        <div class="info-wrap">
          <div class="rate-wrap">
            <el-rate
              v-show="movieItem.avgScore"
              v-model="halfAvgScore"
              disabled
              :max="5"
              text-color="#ff9900"
              score-template="{value}">
            </el-rate>
            <span class="el-rate__text" style="color: rgb(255, 153, 0);">{{ movieItem.avgScore || '暂无评分' }}</span>
          </div>

          <div class="p-title p-ellipsis">
            <a :href="`/movie/detail/${movieItem.id}`" target="_blank">
              <span class="p-name" v-html="movieItem.name"></span>
              <span class="p-year" v-show="movieItem.year" v-text="`(${movieItem.year})`"></span>
            </a>
          </div>
          <p class="p-alias p-ellipsis" v-show="movieItem.alias">{{ movieItem.alias }}</p>
          <p class="p-director p-ellipsis" v-show="movieItem.directors">导演：{{ movieItem.directors }}</p>
          <p class="p-actor p-ellipsis" v-show="movieItem.actors">主演：{{ movieItem.actors }}</p>
          <p class="p-genres p-ellipsis" v-show="movieItem.genres">类型：{{ movieItem.genres }}</p>
          <p class="p-regions p-ellipsis" v-show="movieItem.regions">地区：{{ movieItem.regions }}</p>
          <p class="p-mins p-ellipsis" v-show="movieItem.mins">时长：{{ movieItem.mins }}分钟</p>
          <p class="p-languages p-ellipsis" v-show="movieItem.languages">语言：{{ movieItem.languages }}</p>
          <p class="p-release-date p-ellipsis" v-show="movieItem.releaseDate">首映日：{{ movieItem.releaseDate |
            dateFormat('YYYY-MM-DD') }}</p>
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="section-wrap" v-if="movieItem != null">
        <h3 class="section-title">剧情简介</h3>
        <div class="section-body">
          {{ movieItem.storyline }}
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="section-wrap comment-wrap" v-if="movieItem != null">
        <h3 class="section-title">用户评论</h3>
        <div class="section-body">
          <el-tabs>
            <div class="comment-list">
              <div class="comment-item clearfix">
                <div class="avatar-wrap">
                  <img class="avatar" :src="this.url" width="50">
                </div>
                <div class="comment-info">
                  <div class="p-user">
                    <span class="user-nickname">{{this.name}}</span>
                    <el-rate
                      v-model="value"
                      text-color="#ff9900"
                      change
                      score-template="{value}">
                    </el-rate>
                  </div>
                  <input class="input-content" v-model="text" ></input>
                  <button class="com-button" @click="putData">提交</button>
                </div>
                <!--<span class="create-time">{{ item.createTime | dateFormat }}</span>-->
              </div>
            </div>
          </el-tabs>
          <el-tabs v-model="commentTabAcitveName" @tab-click="handleClick">
            <el-tab-pane label="评论内容" name="first">
              <div class="comment-list">
                <div class="comment-item clearfix" v-for="(item, index) in commentList" :key="index">
                  <div class="avatar-wrap">
                    <img class="avatar" :src="item.doubanUserAvatar" width="50">
                  </div>
                  <div class="comment-info">
                    <div class="p-user">
                      <span class="user-nickname">{{ item.doubanUserNickname }}</span>
                      <el-rate
                        v-model="item.ratingValue"
                        disabled
                        text-color="#ff9900"
                        score-template="{value}">
                      </el-rate>
                      <span class="create-time">{{ item.createTime | dateFormat }}</span>
                    </div>
                    <p class="p-content">{{ item.content }}</p>
                  </div>
                </div>
              </div>

              <div class="pagination-wrap">
                <el-pagination
                  background
                  layout="prev, pager, next"
                  :current-page="commentPage"
                  :total="commentTotal"
                  :pagesize="20"
                  @current-change="handlePageChange"
                ></el-pagination>
              </div>

            </el-tab-pane>
            <el-tab-pane label="评论词云" name="second">
              <div id="wordcloud" style="height: 500px"></div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>

  </div>
</template>

<style lang="less">

  .left-side {
    float: left;
    margin-right: 30px;
    width: 700px;
  }

  .movie-detail-page {
    .detail-wrap {
      margin: 40px 0 60px 0;

      .cover-wrap {
        float: left;
        margin-right: 30px;

        img {
          width: 180px;
          height: 270px;
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
          font-size: 22px;
          color: #999;
        }

        .p-director {
          margin-top: 20px;
          font-size: 18px;
          line-height: 1.5;
        }

        .p-actor,
        .p-genres,
        .p-regions,
        .p-mins,
        .p-languages,
        .p-release-date {
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
    .input-content{
      background-color: silver;
      border-color: salmon;
      width: 900px;
      height:100px;
    }
    .com-button{
      text-align: center;
      background: #3385ff;
      border-bottom: 1px solid #2d78f4;
      outline: medium;
      -webkit-appearance: none;
      -webkit-border-radius: 0;
      color: #fff;
      display: inline-block;
      width: 80px;
      height:30px;
      cursor: pointer;
      padding-left: auto;
      padding-top: 0px;
      margin-left: 20px;

    }


    .section-wrap {
      .section-title {
        font-size: 26px;
        line-height: 1.5;

        padding-bottom: 9px;
        // margin-bottom: 16px;
        // border-bottom: 1px solid #ccc;
      }

      .section-body {
        margin-bottom: 40px;
        font-size: 18px;
        line-height: 1.5;
      }
    }

    .comment-wrap {
      .comment-list {
        margin-bottom: 20px;

        .comment-item {
          padding: 20px 0;
          border-bottom: 1px solid #ccc;

          .avatar-wrap {
            float: left;

            .avatar {
              width: 60px;
              height: 60px;
            }
          }

          .comment-info {
            margin-left: 80px;

            .p-user {
              margin-bottom: 10px;

              .user-nickname {
                margin-right: 20px;
                font-size: 18px;
                color: #f25d8e;
              }

              .el-rate {
                display: inline-block;
                margin-right: 40px;
                vertical-align: top;
              }

              .create-time {
                font-size: 18px;
              }
            }
          }
        }


      }


      .pagination-wrap {
        padding: 0 27px 35px;
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
  }

</style>

<script>
  import HeadBanner from '@/components/movie/home/HeadBanner.vue'
  import NavBar from '@/components/movie/home/NavBar.vue'

  export default {
    components: {
      HeadBanner,
      NavBar
    },
    data() {
      return {
        value: null,
        movieId: this.$route.params.id,
        movieItem: null,
        halfAvgScore: null,
        url: "" ,
        name : "",
        text: "",
        commentList: [],
        commentTabAcitveName: 'first',
        commentPage: 1,
        commentTotal: 0,
        commentWordCountList: [],
      }
    },

    created() {

      this.url =window.localStorage.getItem("url");
      this.name =window.localStorage.getItem("name");
      this.fetchData()
    },

    mounted() {
      // this.drawMovieCommentWordCloud()

      // window.setTimeout(() => {
      //     // alert(document.getElementById('wordcloud'))
      //     this.drawMovieCommentWordCloud()
      // }, 2000)
      // setTimeout(() => {
      // this.$nextTick(() => {
      //     alert(document.getElementById('wordcloud'))
      // })

      // }, 1000)
    },

    methods: {
      putData(){
        this.axios.get("user/comment", {params: {'value': this.value, 'url': this.url,'name':this.name,'movieId':this.movieId,"text":this.text}}).then(response => {
          this.$layer.confirm('提交成功', {
            btn: ['确定','取消']     //按钮
          }, function(){           //点击确定访问后台
            that.$layer.closeAll();
          }, function(){   	//点击取消则中断操作
            that.$layer.closeAll();
            that.$layer.msg('已取消');
          })

          this.fetchData()

         })
        },


      fetchData() {
        this.fetchMovieItem()
      },

      fetchMovieItem() {
        this.axios.get(`movie/${this.movieId}`).then(response => {
          this.movieItem = response.data
          this.halfAvgScore = this.movieItem.avgScore && this.movieItem.avgScore / 2
          this.fetchMovieComment()
        })
      },

      fetchMovieComment() {
        this.axios.get("comment/list", {
          params: {
            'doubanId': this.movieItem.doubanId,
            'page': this.commentPage
          }
        }).then(response => {
          this.commentList = response.data.rows
          this.commentTotal = response.data.total
          // this.fetchMovieCommentSegments()
          // this.scrollToCommentElementTop(document.getElementsByClassName("comment-list")[0])
        })
      },

      fetchMovieCommentSegments() {
        if (this.commentWordCountList.length > 0) {
          return
        }
        // let text = this.commentList.map(x => x["content"]).join(" ")
        // this.axios.get("comment/segments", { params: {'text' : text }}).then(response => {
        //     this.commentWordCountList = response.data.rows
        //     this.drawMovieCommentWordCloud()
        // })

        this.axios.get("comment/segments", {params: {'doubanId': this.movieItem.doubanId}}).then(response => {
          this.commentList = response.data.rows
          this.commentTotal = response.data.total
          let wordMap = response.data
          this.commentWordCountList = []
          for (let key in wordMap) {
            this.commentWordCountList.push({
              name: key,
              value: wordMap[key]
            })
          }
          this.drawMovieCommentWordCloud()
          // console.log(response.data)
          // this.fetchMovieCommentSegments()
          // this.scrollToCommentElementTop(document.getElementsByClassName("comment-list")[0])
        })
      },

      drawMovieCommentWordCloud() {

        var option = {
          series: [{
            type: 'wordCloud',
            // shape: 'rect',
            drawOutOfBound: false,
            width: '98%',
            height: '98%',
            sizeRange: [20, 60],

            gridSize: 8,
            textStyle: {
              normal: {
                fontFamily: '微软雅黑',
                color: function () {
                  var colors = ['#fda67e', '#81cacc', '#cca8ba', "#88cc81", "#82a0c5", '#fddb7e',
                    '#735ba1', '#bda29a', '#6e7074', '#546570', '#c4ccd3'
                  ];
                  return colors[parseInt(Math.random() * 10)];
                }
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: 'red'
              }
            },

            data: this.commentWordCountList,
          }],
        }
        var myChart = this.$echarts.init(document.getElementById('wordcloud'))
        myChart.setOption(option)
      },

      handleClick(tab, event) {
        console.log(tab, event, event.target)
        if (this.commentTabAcitveName === 'first') {
          this.fetchMovieComment()
        }

        if (this.commentTabAcitveName === 'second') {

          this.fetchMovieCommentSegments()
        }
      },

      handlePageChange(page) {
        this.commentPage = page
        //
        // this.scrollToCommentElementTop(document.getElementsByClassName("comment-wrap")[0])
        this.fetchMovieComment()
      },

      // scrollToCommentElementTop(targetElement, interval = 10, step = 100) {
      // alert(targetElement.offsetTop)
      // console.log(targetElement)
      // let timer = window.setInterval(() => {
      //     let top = document.documentElement.scrollTop || document.body.scrollTop
      //     if (top <= targetElement.offsetTop) {
      //         window.clearInterval(timer)
      //         return
      //     }
      //     top = Math.max(top - step, targetElement.offsetTop)
      //     document.documentElement.scrollTop = top
      //     document.body.scrollTop = top
      // }, interval)
      // },
    },
  }

</script>


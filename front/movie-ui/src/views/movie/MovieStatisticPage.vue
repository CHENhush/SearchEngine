<template>
  <div class="movie-stat-page">
    <HeadBanner/>
    <NavBar/>

    <div class="wrap;center" style="display:flex;flex-direction: row;justify-content:center">
      <div id="commentCountBar" style="width: 1000px;height:800px"></div>
      <div id="ratingCountLines" style="width: 1000px;height:800px"></div>
    </div>

    <div class="wrap;center" style="display:flex;flex-direction: row;justify-content:center">
      <div id="KmeansTypes" style="width: 1000px;height:800px"></div>
      <div id="KmeansSSE" style="width: 1000px;height:800px"></div>
    </div>


  </div>
</template>

<style lang="less" scoped>

</style>

<script>
  import HeadBanner from '@/components/movie/home/HeadBanner.vue'
  import NavBar from '@/components/movie/home/NavBar.vue'

  export default {
    components: {
      HeadBanner,
      NavBar
    },

    mounted() {
      this.drawCommentBar();
      this.drawRatingLines();
      this.drawKmeansTypes();
      this.drawKmeansSSE();
    },

    methods: {
      drawCommentBar() {

        let myChart = this.$echarts.init(document.getElementById('commentCountBar'));
        this.yearCount = null;
        this.xAaisData = [];
        this.yValueData = [];
        this.axios.get("/comment/countGroupByYear").then(response => {
          this.yearCount = response.data;

          for (var i = 0; i < this.yearCount.length; i++) {
            this.xAaisData.push(this.yearCount[i].year);
            this.yValueData.push(this.yearCount[i].count);
          }

          var option = {
            title: {
              text: '每年评论数量'
            },
            toolbox: {
              feature: {
                dataZoom: {
                  yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
              }
            },
            tooltip: {},
            legend: {
              data: ['评论数']
            },
            xAxis: {
              data: this.xAaisData
            },
            yAxis: {},
            dataZoom: [{
              type: 'inside',
              start: 0,
              end: 10
            }, {
              start: 0,
              end: 10,
              handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
              handleSize: '80%',
              handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
              }
            }],
            series: [{
              name: '评论数',
              type: 'bar',
              data: this.yValueData,
              itemStyle: {
                normal: {
                  color: '#359ac1',
                  label: {
                    show: true, //开启显示
                    position: 'top', //在上方显示
                    textStyle: { //数值样式
                      color: 'black',
                      fontSize: 16
                    }
                  }
                }
              },
            }]
          };
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        });
      },
      drawRatingLines() {
        let myChart = this.$echarts.init(document.getElementById('ratingCountLines'));
        let linesAxias = [];
        let yValueArrays = [[], [], [], [], []];
        this.axios.get("/comment/countRatingGroupByYear").then(response => {
          for (var i = 0; i < response.data.length; i++) {
            if (linesAxias.indexOf(response.data[i].year) < 0) {
              linesAxias.push(response.data[i].year);
            }
            yValueArrays[parseInt(response.data[i].rating_value) - 1].push(response.data[i].count);
          }


          var option = {
            title: {
              text: '每年评分总览'
            },
            tooltip: {},
            toolbox: {
              feature: {
                dataZoom: {
                  yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
              }
            },
            legend: {
              data: ['1', '2', '3', '4', '5']
            },
            xAxis: {
              data: linesAxias
            },
            yAxis: {},
            dataZoom: [{
              type: 'inside',
              start: 0,
              end: 10
            }, {
              start: 0,
              end: 10,
              handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
              handleSize: '80%',
              handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
              }
            }],
            series: [{
              name: '1',
              type: 'line',
              data: yValueArrays[0]
            }, {
              name: '2',
              type: 'line',
              data: yValueArrays[1]
            }, {
              name: '3',
              type: 'line',
              data: yValueArrays[2]
            }, {
              name: '4',
              type: 'line',
              data: yValueArrays[3]
            }, {
              name: '5',
              type: 'line',
              data: yValueArrays[4]
            },
            ]
          };
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        });


      },

      drawKmeansTypes() {
        let myChart = this.$echarts.init(document.getElementById('KmeansTypes'));
        let option = {
          title: {
            text: 'Kmeans电影类别统计'
          },
          tooltip: {
            trigger: 'item'
          },
          toolbox: {
            show: true,
            feature: {
              dataView: {show: true, readOnly: false},
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          calculable: true,
          grid: {
            borderWidth: 0,
            y: 80,
            y2: 60
          },
          xAxis: [
            {
              type: 'category',
              show: false,
              data: ['喜剧', '动作', '剧情/悬疑/犯罪', '动作/科幻/恐怖', '剧情/历史', '纪录片', '剧情', '动作/奇幻/冒险', '恐怖']
            }
          ],
          yAxis: [
            {
              type: 'value',
              // show: false
            }
          ],
          series: [
            {
              name: 'ECharts例子个数统计',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: function (params) {
                    // build a color map as your need.
                    var colorList = [
                      '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                      '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                      '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                    ];
                    return colorList[params.dataIndex]
                  },
                  label: {
                    show: true,
                    position: 'top',
                    formatter: '{b}\n{c}'
                  }
                }
              },
              data: [7536, 7420, 32085, 4314, 4217, 297, 543, 1492, 2723],
            }
          ]
        };
        myChart.setOption(option);
      },

      drawKmeansSSE() {
        let myChart = this.$echarts.init(document.getElementById('KmeansSSE'));
        let option = {
          title: {
            text: 'Kmeans SSE'
          },
          tooltip: {
            trigger: 'item'
          },
          toolbox: {
            show: true,
            feature: {
              dataView: {show: true, readOnly: false},
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          calculable: true,
          grid: {
            borderWidth: 0,
            y: 80,
            y2: 60
          },
          xAxis: [
            {
              type: 'category',
              data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
            }
          ],
          yAxis: [
            {
              type: 'value',
              min: 10,
              max: 26,
            }
          ],
          series: [
            {
              name: 'ECharts例子个数统计',
              type: 'line',
              itemStyle: {
                normal: {
                  color: '#C1232B'
                }
              },
              data: [24, 18.8, 16.3, 15.6, 15.1, 14.7, 14.3, 13.9, 13.74, 13.64, 13.43, 13.28, 13.10, 12.86, 12.74, 12.63, 12.39, 12.21, 12.10],
            }
          ]
        };
        myChart.setOption(option);
      }


    },
  }
</script>

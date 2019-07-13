<template>
  <div class="movie-qa-page">
    <NavBar/>
    <div data-v-75efd93d="" class="head-banner">
      <div data-v-75efd93d="" class="wrap"><a data-v-4ef0a45b="" data-v-75efd93d="" href="/movie/home"
                                              class="logo"><span data-v-4ef0a45b="" class="logo logo-blue">B</span><span
        data-v-4ef0a45b="" class="logo logo-red">u</span><span data-v-4ef0a45b="" class="logo logo-yellow">K</span><span
        data-v-4ef0a45b="" class="logo logo-green">a</span><span data-v-4ef0a45b="" class="logo logo-red">o</span><span
        data-v-4ef0a45b="" class="logo logo-yellow">P</span><span data-v-4ef0a45b="" class="logo logo-blue">u</span></a>
        <div data-v-75efd93d="" class="search-box search-box">
          <form  action="http://localhost:5000/home" method="get">
          <input type="text" class="search-input" name="query">
          <button class="search-btn" ><i class="iconfont icon-sousuo"></i></button>
          <div class="search-suggestion" style="display: none;">
            <ul></ul>
          </div>
          </form>
        </div>
      </div>
    </div>
    <div id="main" style="height: 600px; width: 900px; border: 1px solid #ccc; padding: 10px;padding-left: auto;"></div>
    <div class="wrap">
      <div id="main1" style="width: 600px;height:400px;"></div>
    </div>
  </div>
</template>


<script src="http://cdn.bootcss.com/cytoscape/2.3.16/cytoscape.min.js"></script>
<script src="drawl.js" type="text/javascript" charset="utf-8"></script>


<script>
  var myChart = echarts.init(document.getElementById('main'), 'macarons');
  //创建Nodes
  nodes=[
    {category:0, name: '乔布斯', value : 10, label: '乔布斯\n（主要）'},
    {category:1, name: '丽萨-乔布斯',value : 2},
    {category:1, name: '保罗-乔布斯',value : 3},
    {category:1, name: '克拉拉-乔布斯',value : 3},
    {category:1, name: '劳伦-鲍威尔',value : 7},
    {category:2, name: '史蒂夫-沃兹尼艾克',value : 5},
    {category:2, name: '奥巴马',value : 8},
    {category:2, name: '比尔-盖茨',value : 9},
    {category:2, name: '乔纳森-艾夫',value : 4},
    {category:2, name: '蒂姆-库克',value : 4},
    {category:2, name: '龙-韦恩',value : 1},
  ],
    //创建links
    links=[
      {source : '丽萨-乔布斯', target : '乔布斯', weight : 1, name: '女儿\r'},
      {source : '保罗-乔布斯', target : '乔布斯', weight : 2, name: '父亲'},
      {source : '克拉拉-乔布斯', target : '乔布斯', weight : 1, name: '母亲'},
      {source : '劳伦-鲍威尔', target : '乔布斯', weight : 2},
      {source : '史蒂夫-沃兹尼艾克', target : '乔布斯', weight : 3, name: '合伙人'},
      {source : '奥巴马', target : '乔布斯', weight : 1},
      {source : '比尔-盖茨', target : '乔布斯', weight : 6, name: '竞争对手'},
      {source : '乔纳森-艾夫', target : '乔布斯', weight : 1, name: '爱将'},
      {source : '蒂姆-库克', target : '乔布斯', weight : 1},
      {source : '龙-韦恩', target : '乔布斯', weight : 1},
      {source : '克拉拉-乔布斯', target : '保罗-乔布斯', weight : 1},
      {source : '奥巴马', target : '保罗-乔布斯', weight : 1},
      {source : '奥巴马', target : '克拉拉-乔布斯', weight : 1},
      {source : '奥巴马', target : '劳伦-鲍威尔', weight : 1},
      {source : '奥巴马', target : '史蒂夫-沃兹尼艾克', weight : 1},
      {source : '比尔-盖茨', target : '奥巴马', weight : 6},
      {source : '比尔-盖茨', target : '克拉拉-乔布斯', weight : 1},
      {source : '蒂姆-库克', target : '奥巴马', weight : 1}
    ]
  categoryArray=[{name:"核心人物"},{name:"家人"},{name:"朋友"}]
  jsondata={"categories":categoryArray,"nodes":nodes,"links":links}
  //数据格式为Json格式
  createGraph(myChart,jsondata)
</script>

<script>
  function getOption(graphInfo){
//给节点设置样式
    graphInfo.nodes.forEach(function (node) {
      //node.itemStyle = null;//
      //node.symbolSize = node.size;//强制指定节点的大小
      // Use random x, y
      node.x = node.y = null;
      node.draggable = true;
    });


    title=graphInfo['title']
    nodes=graphInfo['nodes']
    links=graphInfo['links']
    categories=graphInfo['categories']

//设置option样式
    option = {
      title : {
        text:title,
        x:'right',
        y:'bottom'
      },
      tooltip : {
        trigger: 'item',
        formatter: '{a} : {b}'
        //formatter: function(params){//触发之后返回的参数，这个函数是关键
        //if (params.data.category !=undefined) //如果触发节点
        //   window.open("http://www.baidu.com")
        //}
      },
      color:['#EE6A50','#4F94CD','#B3EE3A','#DAA520'],
      toolbox: {
        show : true,
        feature : {
          restore : {show: true},
          magicType: {show: true, type: ['force', 'chord']},
          saveAsImage : {show: true}
        }
      },
      legend: {
        x: 'left',
        data: categories.map(function (a) {//显示策略
          return a.name;
        })
      },
      series : [
        {
          type:'force',
          name : title,
          ribbonType: false,
          categories : categories,
          itemStyle: {
            normal: {
              label: {
                show: true,
                textStyle: {
                  color: '#333'
                }
              },
              nodeStyle : {
                brushType : 'both',
                borderColor : 'rgba(255,215,0,0.4)',
                borderWidth : 1
              },
              linkStyle: {
                type: 'curve'
              }
            },
            emphasis: {
              label: {
                show: false
                // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
              },
              nodeStyle : {
                //r: 30
              },
              linkStyle : {}
            }
          },
          useWorker: false,
          minRadius : 15,
          maxRadius : 25,
          gravity: 1.1,
          scaling: 1.1,
          roam: 'move',
          nodes:nodes,
          links :links
        }
      ]
    };
    return option
  }
  function createGraph(myChart,mygraph){
//设置option样式
    option=getOption(mygraph)
//使用Option填充图形
    myChart.setOption(option);
//点可以跳转页面
    myChart.on('click', function (params) {
      var data=params.value
      //点没有source属性
      if(data.source==undefined){
        nodeName=params.name
        window.open("http://www.baidu.com")
      }

    });
//myChart.hideLoading();
  }

</script>

<script>
  import NavBar from '@/components/movie/home/NavBar.vue'
  import

    HeadBanner from '@/components/movie/home/HeadBanner.vue'

  export default {
    data() {
      return {
        carouselImageList: [
          'http://img5.mtime.cn/mg/2019/03/11/100042.39938311.jpg',
          'http://img5.mtime.cn/mg/2019/03/10/090505.73960044.jpg',
          'http://img5.mtime.cn/mg/2019/03/09/093214.51486938.jpg',
          'http://img5.mtime.cn/mg/2019/03/08/091944.49544262.jpg'
        ],

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
      NavBar,
      HeadBanner
    },

    methods: {
      suggestionClickCallback(searchText) {

      },

      searchCallback(searchText) {
        this.$router.push({
          path: '/movie/map', query: {'searchText': searchText}
        })
      }
    },
  }
</script>




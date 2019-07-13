import Vue from 'vue'
import App from './App.vue'
import router from './router'

// HTML默认样式标准化
import 'normalize.css'

Vue.config.productionTip = false

/**
 * axios配置
 */
// 导入axios
import axios from 'axios'
import VueAxios from 'vue-axios'
// 全局注册axios到vue。注意两个参数的顺序必须是"VueAxios, axios"，不能反过来
Vue.use(VueAxios, axios)
// axios全局配置
axios.defaults.timeout = 5000;
axios.defaults.baseURL = 'http://localhost:8080';

/**
 * element-ui配置
 */
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

/**
 * moment配置
 */
import moment from 'moment'
// 定义全局时间格式化过滤器
Vue.filter('dateFormat', (dateStr, pattern = 'YYYY-MM-DD HH:mm:ss') => {
    return moment(dateStr).format(pattern);
});

Vue.filter('highlight', (text, keyword, pre='<em>', post='</em>') => {
    let reg = new RegExp(`(${keyword})`, "ig")
    return text.replace(reg, pre + '$1' + post)
})

/**
 * echarts配置
 */
import echarts from 'echarts'
// import a from 'echarts'
Vue.prototype.$echarts = echarts
require('echarts-wordcloud')

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')


import layer from 'vue-layer'
Vue.prototype.$layer = layer(Vue);


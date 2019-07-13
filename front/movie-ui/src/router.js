import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Search from './views/Search.vue'
import MovieHomePage from './views/movie/MovieHomePage.vue'
import MovieSearchPage from './views/movie/MovieSearchPage.vue'
import MovieDetailPage from './views/movie/MovieDetailPage.vue'
import MovieSearchHomePage from './views/movie/MovieSearchHomePage.vue'
import MovieStatisticPage from './views/movie/MovieStatisticPage.vue'
import MovieQuestionPage from './views/movie/MovieQuestionPage.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/search',
            name: 'search',
            component: Search
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            // component: () => import(/* webpackChunkName: "search" */ './views/Search.vue')
        },
        {
            path: '/movie/home',
            component: MovieHomePage
        },
        {
            path: '/movie/search',
            component: MovieSearchPage
        },
        {
            path: '/movie/detail/:id',
            component: MovieDetailPage
        },
        {
            path: '/movie/shome',
            component: MovieSearchHomePage,
        },
      {
        path: '/movie/stat',
        component: MovieStatisticPage,
      },
      {
            path: '/movie/qa',
            component: MovieQuestionPage,
      }
    ]
})

package cn.zucc.searchfinal.service.impl;


import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.mapper.MovieMapper;
import cn.zucc.searchfinal.query.MovieSearchQuery;
import cn.zucc.searchfinal.service.MovieService;
import cn.zucc.searchfinal.vo.MovieSuggestionVO;
import cn.zucc.searchfinal.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction.Modifier;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
//import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.GaussDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private String INDEX_NAME = "my_index";
    private String TYPE_NAME = "_doc";
    private Integer FRAGMENT_SIZE = 50;
    private Integer MAX_NUMBER_OF_FRAGMENTS = 3;
    private String[] REGION_FILTER_LIST = new String[]{"全部地区", "法国", "德国", "意大利", "西班牙", "印度", "泰国", "俄罗斯", "伊朗", "加拿大", "澳大利亚", "爱尔兰", "瑞典", "巴西", "丹麦"};
    private String SEARCH_HOT_KEYWORDS_ZSET_KEY = "SEARCH_HOT_KEYWORDS_ZSET_KEY";
    private String SEARCH_HOT_KEYWORDS_TIMESTAMP_PREFIX = "SEARCH_HOT_KEYWORDS_TIMESTAMP_PREFIX";
    private Long SEARCH_HOT_KEYWORDS_EXPIRE = 600L;

    public MovieServiceImpl() {
    }

    public PageResult<MovieSuggestionVO> listSearchSuggestions(String keyword) {
        keyword = StringUtils.trim(keyword);
        if (StringUtils.isBlank(keyword)) {
            return PageResult.ofEmpty();
        } else {
            Client client = this.elasticsearchTemplate.getClient();
            DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
            disMaxQueryBuilder.add(QueryBuilders.termQuery("name.autocomplete", keyword).boost(10.0F));
            if (keyword.length() > 1) {
                disMaxQueryBuilder.add(QueryBuilders.termQuery("name.pinyin_full", keyword).boost(2.0F));
            }

            if (keyword.length() > 1) {
                disMaxQueryBuilder.add(QueryBuilders.matchPhraseQuery("name.pinyin_full", keyword).boost(0.6F));
            }

            disMaxQueryBuilder.add(QueryBuilders.termQuery("name.pinyin_first", keyword));
            disMaxQueryBuilder.add(QueryBuilders.termQuery("name", keyword));
            disMaxQueryBuilder.add(QueryBuilders.matchQuery("name", keyword));
            HighlightBuilder highlightBuilder = (HighlightBuilder)((HighlightBuilder)((HighlightBuilder)((HighlightBuilder)(new HighlightBuilder()).preTags(new String[]{"<em>"})).postTags(new String[]{"</em>"})).field(new Field("name")).fragmentSize(this.FRAGMENT_SIZE)).numOfFragments(this.MAX_NUMBER_OF_FRAGMENTS);
            SearchResponse response = (SearchResponse)client.prepareSearch(new String[]{this.INDEX_NAME}).setTypes(new String[]{this.TYPE_NAME}).setQuery(disMaxQueryBuilder).highlighter(highlightBuilder).setFetchSource(new String[]{"id","name", "alias", "coverUrl", "year"}, (String[])null).setSize(5).get();
            List<MovieSuggestionVO> movieList = new ArrayList();
            SearchHits hits = response.getHits();
            System.out.println(hits);

            MovieSuggestionVO movie;
            for(Iterator var8 = hits.iterator(); var8.hasNext(); movieList.add(movie)) {
                SearchHit hit = (SearchHit)var8.next();
                movie = new MovieSuggestionVO();
                Map<String, Object> sourceMap = hit.getSourceAsMap();
//                movie.setId(Integer.valueOf(hit.getId()));
                System.out.println((String) sourceMap.get("id"));
                System.out.println(Integer.parseInt((String) sourceMap.get("id")));
                movie.setId(Integer.parseInt((String) sourceMap.get("id")));
                movie.setName((String)sourceMap.get("name"));
                movie.setAlias((String)sourceMap.get("alias"));
                movie.setCoverUrl((String)sourceMap.get("coverUrl"));
                movie.setYear(Integer.parseInt((String) sourceMap.get("year")));
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields != null) {
                    String fragments = this.getAllHighlightFragments((HighlightField)highlightFields.get("name"));
                    if (StringUtils.isNotBlank(fragments)) {
                        movie.setNameHighlight(fragments);
                    }
                }
            }

            return PageResult.ofList(movieList);
        }
    }

    private String getAllHighlightFragments(HighlightField highlightField) {
        String result = null;
        if (highlightField != null) {
            StringBuilder sb = new StringBuilder();
            Text[] var4 = highlightField.fragments();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Text fragment = var4[var6];
                sb.append(fragment);
            }

            result = sb.toString();
        }

        return result;
    }

    private void printOneExplanation(Explanation explanation) {
        System.out.println(explanation);
    }

    private void printExplanation(Explanation explanation) {
        try {
            Explanation[] tfIdfList = explanation.getDetails()[0].getDetails()[0].getDetails();
            List<String> tfIdfStrList = new ArrayList();
            Explanation[] var4 = tfIdfList;
            int var5 = tfIdfList.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Explanation e = var4[var6];
                e = e.getDetails()[0];
                Explanation tf = e.getDetails()[0];
                Explanation idf = e.getDetails()[1];
                tfIdfStrList.add(String.format("%f * %f", tf.getValue(), idf.getValue()));
            }

            Explanation functionScore = explanation.getDetails()[1].getDetails()[0];
            Explanation releaseDate = functionScore.getDetails()[0];
            Explanation votes = functionScore.getDetails()[1];
            System.out.println(String.format("  s0 = %f = %s", explanation.getDetails()[0].getValue(), StringUtils.join(tfIdfStrList, " + ")));
            System.out.println(String.format("  s1 = %f = %f + %f", functionScore.getValue(), releaseDate.getValue(), votes.getValue()));
        } catch (Exception var10) {
            System.out.println("[DEBUG] ==> " + var10.getMessage());
        }

    }

    public PageResult<Movie> search(Integer page, Integer pageSize, String keyword, MovieSearchQuery movieSearchQuery) {
        keyword = StringUtils.trim(keyword);
        System.out.println(keyword);
        if (StringUtils.isBlank(keyword)) {
            return PageResult.ofEmpty();
        } else {
            if (page <= 0) {
                page = 1;
            }
            System.out.println("page："+page);
            System.out.println("pageSize："+pageSize);
            System.out.println("keyword："+keyword);



            Client client = this.elasticsearchTemplate.getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            DisMaxQueryBuilder disMaxQueryBuilder = (DisMaxQueryBuilder)(new DisMaxQueryBuilder()).tieBreaker(0.0F).boost(1.0F);
            boolQueryBuilder.must(disMaxQueryBuilder);
            disMaxQueryBuilder.add(QueryBuilders.matchQuery("name", keyword).boost(1.0F));
            disMaxQueryBuilder.add(QueryBuilders.termQuery("name.autocomplete", keyword).boost(2.0F));
            disMaxQueryBuilder.add(QueryBuilders.termQuery("name.pinyin_first", keyword).boost(0.6F));
            Integer regionId = movieSearchQuery.getRegionId();
            System.out.println("21314");
            System.out.println(regionId);
            Integer minsId = movieSearchQuery.getMinsId();
            if (minsId != null && minsId != 0) {
                switch(minsId) {
                    case 1:
                        boolQueryBuilder.filter(QueryBuilders.rangeQuery("mins").from(0).to(10));
                        break;
                    case 2:
                        boolQueryBuilder.filter(QueryBuilders.rangeQuery("mins").from(10).to(30));
                        break;
                    case 3:
                        boolQueryBuilder.filter(QueryBuilders.rangeQuery("mins").from(30).to(60));
                        break;
                    case 4:
                        boolQueryBuilder.filter(QueryBuilders.rangeQuery("mins").from(60).to(90));
                        break;
                    case 5:
                        boolQueryBuilder.filter(QueryBuilders.rangeQuery("mins").from(90).to(120));
                        break;
                    case 6:
                        boolQueryBuilder.filter(QueryBuilders.rangeQuery("mins").gt(120));
                }
            }

            Integer[][] YEAR_RANGE_LIST = new Integer[][]{null, {2019, 2019}, {2018, 2018}, {2017, 2017}, {2016, 2016}, {2010, 2019}, {2000, 2009}, {1990, 1999}, {1980, 1989}, {1970, 1979}, {1960, 1969}, {0, 1959}};
            Integer yearRangeId = movieSearchQuery.getYearRangeId();
            if (yearRangeId != null && yearRangeId != 0 && yearRangeId < YEAR_RANGE_LIST.length) {
                int i = yearRangeId;
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("year").from(YEAR_RANGE_LIST[i][0]).to(YEAR_RANGE_LIST[i][1]));
            }

            HighlightBuilder highlightBuilder = (HighlightBuilder)((HighlightBuilder)((HighlightBuilder)((HighlightBuilder)(new HighlightBuilder()).preTags(new String[]{"<em>"})).postTags(new String[]{"</em>"})).field(new Field("name")).fragmentSize(this.FRAGMENT_SIZE)).numOfFragments(this.MAX_NUMBER_OF_FRAGMENTS);
            SortBuilder sortBuilder = null;
            Integer sortTypeId = movieSearchQuery.getSortTypeId();
            System.out.println("sortTypeId"+sortTypeId);
            if (sortTypeId == 1) {
                sortBuilder = SortBuilders.fieldSort("votes").order(SortOrder.DESC);
            } else if (sortTypeId == 2) {
                sortBuilder = SortBuilders.fieldSort("avgScore").order(SortOrder.DESC);
            } else if (sortTypeId == 3) {
                sortBuilder = SortBuilders.fieldSort("releaseDate").order(SortOrder.DESC);
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("releaseDate").lte(System.currentTimeMillis()));
            }

            FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilderList = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{new FunctionScoreQueryBuilder.FilterFunctionBuilder((new GaussDecayFunctionBuilder("releaseDate", "now", "3000d", "0d", 0.5D)).setWeight(0.1F)), new FunctionScoreQueryBuilder.FilterFunctionBuilder((new FieldValueFactorFunctionBuilder("votes")).modifier(FieldValueFactorFunction.Modifier.LN1P).factor(1.0F).missing(0.0D).setWeight(0.001F))};
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(boolQueryBuilder, filterFunctionBuilderList).scoreMode(FunctionScoreQuery.ScoreMode.SUM).boostMode(CombineFunction.SUM);
            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(new String[]{this.INDEX_NAME}).setTypes(new String[]{this.TYPE_NAME}).setQuery(functionScoreQueryBuilder).setFrom((page - 1) * pageSize).setSize(pageSize).highlighter(highlightBuilder).setFetchSource(new String[]{"id", "name", "alias", "year", "coverUrl", "avgScore", "genres", "directors", "actors", "regions", "mins", "votes"}, (String[])null).setExplain(true);
            if (sortBuilder != null) {
                searchRequestBuilder.addSort(sortBuilder);
            } else {
                searchRequestBuilder.addSort(new ScoreSortBuilder());
                searchRequestBuilder.addSort(SortBuilders.fieldSort("releaseDate").order(SortOrder.DESC));
                searchRequestBuilder.addSort(SortBuilders.fieldSort("votes").order(SortOrder.DESC));
            }
            System.out.println(searchRequestBuilder.toString());
            SearchResponse response = (SearchResponse)searchRequestBuilder.get();
            List<Movie> movieList = new ArrayList();
            SearchHits hits = response.getHits();
            Iterator var21 = hits.iterator();

            while(var21.hasNext()) {
                SearchHit hit = (SearchHit)var21.next();
                Movie movie = new Movie();
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                System.out.println(sourceMap.get("id"));
                movie.setId(Integer.parseInt((String) sourceMap.get("id")));
                movie.setName((String)sourceMap.get("name"));
                movie.setAlias((String)sourceMap.get("alias"));
                movie.setYear(Integer.parseInt((String) sourceMap.get("year")));
                movie.setCoverUrl((String)sourceMap.get("coverUrl"));
                movie.setAvgScore(Double.parseDouble((String) sourceMap.get("avgScore")));
                movie.setGenres((String)sourceMap.get("genres"));
                movie.setReleaseDate((Date) sourceMap.get("releaseDate"));
                movie.setDirectors((String)sourceMap.get("directors"));
                movie.setActors((String)sourceMap.get("actors"));
                movie.setRegions((String)sourceMap.get("regions"));
                movie.setMins(Integer.parseInt((String) sourceMap.get("mins")));
                movie.setVotes(Integer.parseInt((String) sourceMap.get("votes")));
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields != null) {
                    String fragments = this.getAllHighlightFragments((HighlightField)highlightFields.get("name"));
                    if (StringUtils.isNotBlank(fragments)) {
                        movie.setName(fragments);
                    }
                }

                System.out.println("==================================================");
                System.out.println(String.format("SEARCH DEBUG: name=%s, score=%f, votes=%d", movie.getName(), hit.getScore(), movie.getVotes()));
                Explanation explanation = hit.getExplanation();
                this.printExplanation(explanation);
                movieList.add(movie);
            }

            return PageResult.of(hits.totalHits, movieList);
        }
    }

    public void accumulateSearchKeywordsFrequency(String keyword) {
        String key = this.SEARCH_HOT_KEYWORDS_TIMESTAMP_PREFIX + ":" + keyword;
        if (this.redisTemplate.hasKey(key)) {
            this.redisTemplate.opsForZSet().incrementScore(this.SEARCH_HOT_KEYWORDS_ZSET_KEY, keyword, 1.0D);
        } else {
            System.out.println("DEBUG: " + this.SEARCH_HOT_KEYWORDS_ZSET_KEY);
            this.redisTemplate.opsForZSet().add(this.SEARCH_HOT_KEYWORDS_ZSET_KEY, keyword, 1.0D);
        }

        this.redisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()));
        this.redisTemplate.expire(key, this.SEARCH_HOT_KEYWORDS_EXPIRE, TimeUnit.SECONDS);
    }

    public PageResult<Movie> listRealTimeHotSearch(Integer topN) {
        Set<String> set = this.redisTemplate.opsForZSet().reverseRangeByScore(this.SEARCH_HOT_KEYWORDS_ZSET_KEY, 0.0D, 1.7976931348623157E308D);
        System.out.println("连接上了redis");
        List<Movie> result = new ArrayList();
        Iterator var4 = set.iterator();

        while(var4.hasNext()) {
            String keyword = (String)var4.next();
            if (this.redisTemplate.hasKey(this.SEARCH_HOT_KEYWORDS_TIMESTAMP_PREFIX + ":" + keyword)) {
                Movie movie = new Movie();
                movie.setId(Integer.valueOf(keyword));
                movie = (Movie)this.movieMapper.selectByPrimaryKey(movie);
                result.add(movie);
                if (result.size() >= topN) {
                    break;
                }
            } else {
                this.redisTemplate.opsForZSet().remove(this.SEARCH_HOT_KEYWORDS_ZSET_KEY, new Object[]{keyword});
            }
        }
        
        return PageResult.ofList(result);
    }

    public Movie getMovieById(Integer id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie = (Movie)this.movieMapper.selectByPrimaryKey(movie);
        this.accumulateSearchKeywordsFrequency(movie.getId().toString());
        return movie;
    }

}

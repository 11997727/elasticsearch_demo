package com.example.elasticsearch_demo.controller;

import com.example.elasticsearch_demo.entity.NewsDetail;
import com.example.elasticsearch_demo.mapper.NewsDetailDao;
import com.example.elasticsearch_demo.mapper.NewsDetailMapper;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 吴成卓
 * @version V1.0
 * @Project: elasticsearch_demo
 * @Package com.example.elasticsearch_demo.controller
 * @Description:
 * @date 2020/3/19 星期四 23:15
 */
@RestController
public class NewsDetailController {
    @Resource
    private NewsDetailDao newsDetailDao;
    @Resource
    private NewsDetailMapper newsDetailMapper;

    //springboot集成了es，所以直接用操作对象
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //创建索引和映射
    @RequestMapping("/create")
    public void create(){
        // 创建索引，会根据NewsDetail类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(NewsDetail.class);
        // 配置映射，会根据NewsDetail类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(NewsDetail.class);
    }

    //删除索引
    @RequestMapping("/delete")
    public void delete(){
        //indexName = "product2"
        //elasticsearchTemplate.deleteIndex("product2");
        elasticsearchTemplate.deleteIndex(NewsDetail.class);
    }

    //--------------------------------------es本身提供的基础方法---------------------------------------------------------------------

    /**
     * 把数据库的信息存入es
     */
    @RequestMapping("/saveAllNewsDetail")
    public void saveAllNewsDetail(){
        List<NewsDetail> list = newsDetailMapper.selectAll();
        newsDetailDao.saveAll(list);
    }

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping("/save")
    public String save(){
        NewsDetail newsDetail=new NewsDetail();
        newsDetail.setAuthor("成卓");
        newsDetail.setId(System.currentTimeMillis());
        newsDetail.setCategoryId(78);
        newsDetail.setSummary("这是一篇文章。");
        newsDetail.setTitle("标题");
        newsDetail.setCreateDate(new Date());
        newsDetailDao.save(newsDetail);
        return "success";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/del")
    public String del(Long id){
        newsDetailDao.deleteById(id);
        return "success";
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @RequestMapping("/get")
    public NewsDetail get(Long id){
        return newsDetailDao.findById(id).orElse(null);
    }


    /**
     * 修改
     * @param
     * @param
     * @return
     */
    @RequestMapping("/update")
    public String update(Long id,String title){
        NewsDetail newsDetail = newsDetailDao.findById(id).orElse(null);
        if(newsDetail!=null){
            newsDetail.setTitle(title);
            newsDetailDao.save(newsDetail);
            return "success";
        }
        return "failed";
    }

    /**
     * 查看全部
     */
    String result;
    @RequestMapping("/getAll")
    public String getAll(){
        result="";
        Iterable<NewsDetail> newsDetails = newsDetailDao.findAll();
        newsDetails.forEach(newsDetail ->{
            result+=newsDetail.getAuthor()+"\n";
        });
        return result;
    }

    /**
     * 查询所有按照创建时间降序排列
     */
    @RequestMapping("/getAlll")
    public void getAlll(){
        Iterable<NewsDetail> newsDetails = newsDetailDao.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        newsDetails.forEach(System.out::println);
    }

    //--------------------------------------按照一定的方法名关键字规则 Spring data 提供实现类---------------------------------------------------------------------

    /**
     * 创建时间区间查找
     */
    @RequestMapping("/findByCreateDateBetween")
    public void findByCreateDateBetween(){
        List<NewsDetail> list = newsDetailDao.findByCreateDateBetween("2019-09-01 00:00:00", "2019-09-05 20:55:22");
        list.forEach(System.out::println);
    }

    /**
     * 条件查询并且   作者是张三并且新闻类型是1
     */
    @RequestMapping("/findByAuthorAndcAndCategoryId")
    public void findByAuthorAndcAndCategoryId(){
        List<NewsDetail> list = newsDetailDao.findByAuthorAndCategoryId("张三", 1);
        list.forEach(System.out::println);
    }

    /**
     * 条件查询或者  作者是张三或者摘要包含9月21日结果按照创建时间倒序
     */
    @RequestMapping("/findByAuthorOrSummaryOrderByCreateDateDesc")
    public void findByAuthorOrSummary(){
        List<NewsDetail> list = newsDetailDao.findByAuthorOrSummaryOrderByCreateDateDesc("张三", "9月21日");
        list.forEach(System.out::println);
    }

    /**
     * 模糊查询摘要包含 9月
     */
    @RequestMapping("/findBySummaryLike")
    public void findBySummaryLike(){
        List<NewsDetail> list = newsDetailDao.findBySummaryLike("9月");
        list.forEach(System.out::println);
    }

    //--------------------------------------高级查询---------------------------------------------------------------------
    //QueryBuilders 提供了大量的静态方法，用于生成各种不同类型的查询对象，例如：词条、模糊、通配符等QueryBuilder对象
    /**
     * 词条查询
     */
    @RequestMapping("/search1")
    public void search1(){
        // 词条查询
        MatchQueryBuilder queryBuilder= QueryBuilders.matchQuery("author", "张三");
        // 执行查询
        Iterable<NewsDetail> newsDetails = newsDetailDao.search(queryBuilder);
        newsDetails.forEach(System.out::println);
    }


    /**
     *自定义查询
     * @param Keyword
     * @return
     */
    @RequestMapping("/search2")
    public String search2(String Keyword){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        // 添加基本的分词查询   matchQuery带分词功能的全文搜索
        queryBuilder.withQuery(QueryBuilders.matchQuery("summary",Keyword));
        // 执行搜索，获取结果
        Page<NewsDetail> newsDetails = newsDetailDao.search(queryBuilder.build());
        //检索出来的条数
        long total = newsDetails.getTotalElements();
        System.out.println(total);
        newsDetails.forEach(System.out::println);
        result="";
        newsDetails.forEach(newsDetail ->{
            result+=newsDetail.getAuthor()+":"+newsDetail.getTitle()+"\n";
        });
        return result;
    }

    /**
     * 分页查询
     */
    @RequestMapping("/search3")
    public void search3(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询  termQuery不带分词功能的精准匹配
        queryBuilder.withQuery(QueryBuilders.termQuery("author", "张三"));

        // 初始化分页参数
        int page = 0;
        int size = 1;
        // 设置分页参数
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 执行搜索，获取结果
        Page<NewsDetail> items = this.newsDetailDao.search(queryBuilder.build());
        // 打印总条数
        System.out.println("总条数："+items.getTotalElements());
        // 打印总页数
        System.out.println("总页数:"+items.getTotalPages());
        // 每页大小
        System.out.println("每页大小："+items.getSize());
        // 当前页
        System.out.println("当前页："+items.getNumber());
        items.forEach(System.out::println);
    }

    /**
     * 排序 查询作者是张三 按照创建时间倒序
     */
    @RequestMapping("/search4")
    public void search4(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询    termQuery不带分词功能的精准匹配
        queryBuilder.withQuery(QueryBuilders.termQuery("author", "张三"));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("createDate").order(SortOrder.DESC));
        // 执行搜索，获取结果
        Page<NewsDetail> items = this.newsDetailDao.search(queryBuilder.build());
        // 打印总条数
        System.out.println("总条数："+items.getTotalElements());
        items.forEach(System.out::println);
    }
    //AggregationBuilders.terms("categorys").field("categoryId") 聚合的构建工厂类AggregationBuilders，所有聚合都由这个类来构建
    //aggPage.getAggregation("categorys")返回的结果都是Aggregation类型对象，不过根据字段类型不同，又有不同的子类表示
    /**
     * 普通聚合
     */
    @RequestMapping("/search5")
    public void search5(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为categorys，聚合字段为categoryId
        queryBuilder.addAggregation(AggregationBuilders.terms("categorys").field("categoryId"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<NewsDetail> aggPage = (AggregatedPage<NewsDetail>) this.newsDetailDao.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为categorys的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("categorys");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即类型主键 和 文档数量
            System.out.println(bucket.getKeyAsString() + "类型文章有" +bucket.getDocCount()+"篇");
        }
    }

    /**
     * 嵌套聚合
     */
    @RequestMapping("/search6")
    public void search6(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为categorys，聚合字段为categoryId
        queryBuilder.addAggregation(
                AggregationBuilders.terms("categorys").field("categoryId")
                        .subAggregation(AggregationBuilders.avg("avgPrice").field("price")) // 在分类聚合桶内进行嵌套聚合，求文章打赏平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<NewsDetail> aggPage = (AggregatedPage<NewsDetail>) this.newsDetailDao.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为categorys的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("categorys");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        buckets.forEach(bucket -> {
            // 3.4、获取桶中的key，即类型主键 和 文档数量 ;获取该类型平均值结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("avgPrice");
            System.out.println(bucket.getKeyAsString() + "类型文章有" + bucket.getDocCount() +"篇,平均打赏："+ avg.getValue() );
        });
    }
}

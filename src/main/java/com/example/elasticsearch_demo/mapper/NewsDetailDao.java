package com.example.elasticsearch_demo.mapper;

import com.example.elasticsearch_demo.entity.NewsDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Project: elasticsearch_demo
 * @Package com.example.elasticsearch_demo.mapper
 * @Description: ${todo}
 * @author 吴成卓
 * @date 2020/3/19 星期四 22:24
 * @version V1.0 
 *
 */
public interface NewsDetailDao extends ElasticsearchRepository<NewsDetail,Long> {
    /**
     * 创建时间区间查询
     * @param s1
     * @param s2
     * @return
     */
    List<NewsDetail> findByCreateDateBetween(String s1, String s2);

    /**
     * 条件查询并且
     * @param author
     * @param categoryId
     * @return
     */
    List<NewsDetail>findByAuthorAndCategoryId(String author ,Integer categoryId);

    /**
     * 条件查询或者
     * @param author
     * @param str
     * @return
     */
    List<NewsDetail>findByAuthorOrSummaryOrderByCreateDateDesc(String author,String str);

    /**
     * 模糊查询摘要
     * @param str
     * @return
     */
    List<NewsDetail>findBySummaryLike(String str);
}
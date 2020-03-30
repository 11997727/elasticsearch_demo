package com.example.elasticsearch_demo;

import com.example.elasticsearch_demo.entity.NewsDetail;
import com.example.elasticsearch_demo.mapper.NewsDetailDao;
import com.example.elasticsearch_demo.mapper.NewsDetailMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchDemoApplicationTests {

    //springboot集成了es，所以直接用操作对象
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private NewsDetailMapper newsDetailMapper;
    @Autowired
    private NewsDetailDao newsDetailDao;
    //创建索引和映射
    @Test
    public void create(){
        //创建索引，会根据实体类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(NewsDetail.class);
        //配置映射，根据id，field声明来自动完成映射
        elasticsearchTemplate.putMapping(NewsDetail.class);
    }

    //删除索引
    @Test
    public void delete(){
        elasticsearchTemplate.deleteIndex("product2");
//        elasticsearchTemplate.deleteIndex(NewsDetail.class);
    }

    //新增文档（往库中添加数据）
    @Test
    public void save(){
        NewsDetail newsDetail=new NewsDetail();
        newsDetail.setAuthor("成卓");
        newsDetail.setId(84L);
        newsDetail.setCategoryId(78);
        newsDetail.setSummary("这是一篇文章。");
        newsDetail.setTitle("标题");
        newsDetail.setCreateDate(new Date());
        newsDetailDao.save(newsDetail);
    }

    @Test
    public void saveList(){
        List<NewsDetail> list = newsDetailMapper.selectAll();
        newsDetailDao.saveAll(list);
    }
    @Test
    public void contextLoads() {
    }

}

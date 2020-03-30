package com.example.elasticsearch_demo.mapper;

import com.example.elasticsearch_demo.entity.NewsDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 吴成卓
 * @version V1.0
 * @Project: elasticsearch_demo
 * @Package com.example.elasticsearch_demo.mapper
 * @Description:
 * @date 2020/3/19 星期四 23:06
 */
@Mapper
public interface NewsDetailMapper {
    List<NewsDetail>selectAll();
}

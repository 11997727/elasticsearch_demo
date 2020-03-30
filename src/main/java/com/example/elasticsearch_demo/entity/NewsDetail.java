package com.example.elasticsearch_demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Project: elasticsearch_demo
 * @Package com.example.elasticsearch_demo.entity
 * @Description: ${todo}
 * @author 吴成卓
 * @date 2020/3/19 星期四 22:24
 * @version V1.0 
 *
 */
@Document(indexName = "product2",type = "item2",shards = 1,replicas = 0)
public class NewsDetail {

    /*
     * indexName：对应索引库的名称
     * type：对应在索引库中的类型
     * shards：分片数量，默认是5
     * replicas：副本数量，默认是1
     *
     * @Id：标记主键
     * @Field：指定映射属性
     * index：是否索引，默认为true
     * type：字段类型
     * store：是否存储，默认是false
     * analyzer：分词器类型
     *
     */


    /**
    * 新闻编号
    */
    @Id
    private Long id;

    /**
    * 新闻分类编号
    */
    @Field(type = FieldType.Keyword)
    private Integer categoryId;

    /**
    * 新闻标题
    */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    /**
    * 新闻摘要
    */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String summary;

    /**
    * 作者
    */
    @Field(type = FieldType.Keyword)
    private String author;

    /**
     * 打赏
     */
    @Field(type = FieldType.Double)
    private Double price;

    /**
     * 图片地址
     */
    @Field(index = false,type = FieldType.Keyword)
    private String image;


//    DateFormat.custom：意思自定义属性格式；
//
//    pattern：时间格式，我们在java程序中可以传入这些格式的时间；
//
//    epoch_millis：也就是时间戳 示例1515150699465, 1515150699；
//
//    @JsonFormat：该注解中的时间格式为我们存入es索引库中的时间格式；

    /**
    * 创建时间
    */
    @Field( type = FieldType.Date,format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd || yyyy/MM/dd HH:mm:ss|| yyyy/MM/dd ||epoch_millis")//不行没有格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
    * 更新时间
    */
    @Field( type = FieldType.Date,format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd || yyyy/MM/dd HH:mm:ss|| yyyy/MM/dd ||epoch_millis")//不行没有格式
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public NewsDetail() {
    }

    public NewsDetail(Long id, Integer categoryId, String title, String summary, String author, Double price, String image, Date createDate, Date updateDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.price = price;
        this.image = image;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "NewsDetail{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", author='" + author + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
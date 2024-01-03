//package edu.poly.duantotnghiep.model.dto;
//
//import jakarta.persistence.Id;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.util.List;
//
//@Getter
//@Setter
//@Document(indexName="products")
//public class ElasticSearch {
//    @Id
//    private String id;
//
//    @Field(type = FieldType.Text)
//    private String name;
//
//    @Field(type = FieldType.Text)
//    private String brand;
//
//    @Field(type = FieldType.Text)
//    private String category;
//
//    @Field(type = FieldType.Text)
//    private String season;
//
//    @Field(type = FieldType.Text)
//    private String gender;
//
//    @Field(type = FieldType.Integer)
//    private Integer order_count;
//
//    @Field(type = FieldType.Double)
//    private Double min_price;
//
//    @Field(type = FieldType.Double)
//    private Double max_price;
//
//    @Field(type = FieldType.Double)
//    private Double rate;
//
//    @Field(type = FieldType.Text)
//    private String main_image;
//
//    private List<String> hashtags;
//
//    @Field(type = FieldType.Double)
//    private Double discount;
//
//}
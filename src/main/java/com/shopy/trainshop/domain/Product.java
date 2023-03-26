package com.shopy.trainshop.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final String SEQ_NAME = "product_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Lob
    private String image;
//    @Column(columnDefinition = "bytea")
//    @Type(type = "org.hibernate.type.BinaryType")

//    private byte[] image;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;


}

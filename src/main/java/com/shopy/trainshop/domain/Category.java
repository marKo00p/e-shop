package com.shopy.trainshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    private static final String SEQ_NAME = "category_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    @Column(name = "category_id")
    private Long id;
    @Column(name="title")
    private String title;
    @Transient
    @Column(name="products_quantity")
    private Integer productsQuantity;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private Set<Product> products ;
    public int getProductsQuantity() {
        return this.products.size();
    }


}

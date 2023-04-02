package com.shopy.trainshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bucket_item")
public class BucketItem {
    private static final String SEQ_NAME = "bucket_items_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    @Column(name = "bucket_item_id")
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",nullable=false, updatable=false)
    private Product product;

    public Long getId() {
        return id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Product getProduct() {
        return product;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BucketItem that)) return false;
        return quantity == that.quantity && id.equals(that.id) &&  product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, product);
    }
}

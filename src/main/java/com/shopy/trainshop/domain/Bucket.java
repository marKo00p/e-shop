package com.shopy.trainshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "buckets")
public class Bucket {
    private static final String SEQ_NAME = "buckets_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    @Column(name = "bucket_id")
    private Long id;

    @Column(name="total_items")
    private Integer totalItems;

    @Column(name="total_price")
    private BigDecimal totalPrice;
    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date date;

    @Column(name="session_token")
    private String sessionToken;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BucketItem> bucketItems = new HashSet<BucketItem>();

    public Set<BucketItem> getBucketItems(){
        return bucketItems;
    }
    public void setBucketItems( Set<BucketItem> bucketItems){
        this.bucketItems = bucketItems;
    }


    public BigDecimal getTotalPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for(BucketItem item : this.bucketItems) {
            sum = sum.add((item.getProduct().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return sum;
    }

    public int getTotalItems() {
        return this.bucketItems.size();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bucket bucket)) return false;
        return totalItems == bucket.totalItems && id.equals(bucket.id) && totalPrice.equals(bucket.totalPrice) && sessionToken.equals(bucket.sessionToken) && user.equals(bucket.user) && Objects.equals(bucketItems, bucket.bucketItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalItems, totalPrice, sessionToken, user, bucketItems);
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "id=" + id +
                ", totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", sessionToken='" + sessionToken + '\'' +
                ", user=" + user +
                ", bucketItems=" + bucketItems +
                '}';
    }
}

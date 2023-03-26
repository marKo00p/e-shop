package com.shopy.trainshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    private static final String SEQ_NAME = "order_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    @Column(name = "order_id")
    private Long id;
    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "address")
    private String address;
    @Column(name = "notes")
    private String notes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetails> details;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", totalPrice=" + totalPrice +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                ", details=" + details +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}

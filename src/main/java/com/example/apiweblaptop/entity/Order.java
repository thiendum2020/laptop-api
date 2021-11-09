package com.example.apiweblaptop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "ngaydat")
    private LocalDate ngaydat;
    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Float total_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<DetailOrder> detailOrders;
}

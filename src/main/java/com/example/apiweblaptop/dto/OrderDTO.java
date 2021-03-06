package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngaydat;
    private String status;
    private Float total_price;
    private Long id_user;

    public OrderDTO(Long id, LocalDate ngaydat, String status, Float total_price, Long id_user) {
        this.id = id;
        this.ngaydat = ngaydat;
        this.status = status;
        this.total_price = total_price;
        this.id_user = id_user;
    }

    public Order dtoToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setTotal_price(dto.getTotal_price());
        order.setStatus(dto.getStatus());
        order.setNgaydat(dto.getNgaydat());
        return order;
    }

    public OrderDTO entityToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setNgaydat(order.getNgaydat());
        dto.setStatus(order.getStatus());
        dto.setTotal_price(order.getTotal_price());
        dto.setId_user(order.getUser().getId());
        return dto;
    }

    public List<Order> dtoToEntity(List<OrderDTO> dtos) {
        List<Order> list = new ArrayList<>();
        dtos.forEach(x -> list.add(this.dtoToEntity(x)));
        return list;
    }

    public List<OrderDTO> entityToDTO(List<Order> orders) {
        List<OrderDTO> list = new ArrayList<>();
        orders.forEach(x->{list.add(entityToDTO(x));});
        return list;
    }
}

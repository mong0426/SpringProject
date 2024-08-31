package com.example.project3.Service;

import com.example.project3.DTO.OrderHistoryDTO;
import com.example.project3.Entity.OrderHistory;
import com.example.project3.Repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    @Override
    public void saveOrderHistory(OrderHistoryDTO orderHistoryDTO) {
        OrderHistory orderHistory = convertToEntity(orderHistoryDTO);
        // 엔티티 저장
        orderHistoryRepository.save(orderHistory);
    }

    private OrderHistory convertToEntity(OrderHistoryDTO orderHistoryDTO) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(orderHistoryDTO.getId());
        orderHistory.setStore(orderHistoryDTO.getStore());
        orderHistory.setFood(orderHistoryDTO.getFood());
        orderHistory.setTotalPrice(orderHistoryDTO.getTotalPrice());
        orderHistory.setOrderDate(orderHistoryDTO.getOrderDate());
        return orderHistory;
    }

    @Override
    public List<OrderHistoryDTO> showOrderList(String id) {
        List<OrderHistory> orderHistories = orderHistoryRepository.findByIdOrderByOrderDateDesc(id);
        return orderHistories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderHistory(String id) {
        List<OrderHistory> orderHistory = orderHistoryRepository.findAllById(id);
        orderHistoryRepository.deleteAll(orderHistory);
    }

    private OrderHistoryDTO convertToDTO(OrderHistory orderHistory) {
        OrderHistoryDTO dto = new OrderHistoryDTO();
        dto.setOno(orderHistory.getOno());
        dto.setId(orderHistory.getId());
        dto.setStore(orderHistory.getStore());
        dto.setFood(orderHistory.getFood());
        dto.setTotalPrice(orderHistory.getTotalPrice());
        dto.setOrderDate(orderHistory.getOrderDate());
        dto.setReviewed(orderHistory.getReviewed());
        return dto;
    }
}

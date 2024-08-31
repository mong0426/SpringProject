package com.example.project3.Service;

import com.example.project3.DTO.OrderHistoryDTO;

import java.util.List;

public interface OrderHistoryService {

    void saveOrderHistory(OrderHistoryDTO orderHistoryDTO);

    public List<OrderHistoryDTO> showOrderList(String id);

    void deleteOrderHistory(String id);
}

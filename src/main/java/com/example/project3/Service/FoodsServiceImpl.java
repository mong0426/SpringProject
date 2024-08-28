package com.example.project3.Service;

import com.example.project3.DTO.FoodsDTO;
import com.example.project3.Entity.Foods;
import com.example.project3.Repository.FoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodsServiceImpl implements FoodsService {

    @Autowired
    FoodsRepository foodsRepository;

    @Override
    public void addNewFood(FoodsDTO foodsDTO) {
        Foods foods = convertToEntity(foodsDTO);
        foodsRepository.save(foods);
    }

    private Foods convertToEntity(FoodsDTO foodsDTO) {
        Foods foods = new Foods();
        foods.setFood(foodsDTO.getFood());
        foods.setDescription(foodsDTO.getDescription());
        foods.setPrice(foodsDTO.getPrice());
        foods.setImageUrl(foodsDTO.getImageUrl());
        foods.setStore(foodsDTO.getStore());
        return foods;
    }
}

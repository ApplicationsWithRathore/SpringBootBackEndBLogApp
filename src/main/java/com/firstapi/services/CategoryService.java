package com.firstapi.services;

import com.firstapi.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
    void deleteCategory(Integer id);
    List<CategoryDto> getAllCategory();
    CategoryDto getById(Integer id);

}

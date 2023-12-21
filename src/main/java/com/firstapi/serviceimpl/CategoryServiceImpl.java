package com.firstapi.serviceimpl;

import com.firstapi.entity.Category;
import com.firstapi.exceptionhandler.ResourceNotFoundException;
import com.firstapi.payloads.CategoryDto;
import com.firstapi.repositories.CategoryRepo;
import com.firstapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
      Category create =   this.categoryRepo.save(category);
        return this.categoryToDto(create);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Id",id));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updated = this.categoryRepo.save(category);
        return this.categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Id",id));
        this.categoryRepo.delete(category);

    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> category = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = category.stream().map(this::categoryToDto).toList();
        return categoryDtos;
    }

    @Override
    public CategoryDto getById(Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Id",id));

        return this.categoryToDto(category);
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        Category category = this.modelMapper.map(categoryDto,Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category){
        return this.modelMapper.map(category,CategoryDto.class);
    }
}

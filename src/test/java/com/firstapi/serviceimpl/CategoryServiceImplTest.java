package com.firstapi.serviceimpl;

import com.firstapi.entity.Category;
import com.firstapi.payloads.CategoryDto;
import com.firstapi.repositories.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepo categoryRepo;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CategoryServiceImpl categoryService;
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImplTest.class);

    @Test
    void createCategory() {
        //arrange
        Category category = Category.builder().categoryTitle("Java").categoryDescription("all about java").build();
        CategoryDto categoryDto = CategoryDto.builder().categoryTitle("Java").categoryDescription("all about java").build();
        when(modelMapper.map(categoryDto,Category.class)).thenReturn(category);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
        when(modelMapper.map(category,CategoryDto.class)).thenReturn(categoryDto);
//Act
        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        logger.info("{}",savedCategory);

        verify(modelMapper).map(category,CategoryDto.class);
        verify(categoryRepo).save(category);
// Assert
       assertNotNull(savedCategory);
    }

    @Test
    void updateCategory() {

        Category category = Category.builder().id(1).categoryTitle("Android").categoryDescription("").build();
        CategoryDto categoryDto = CategoryDto.builder().categoryTitle("Java").categoryDescription("all about java").build();

        when(categoryRepo.findById(1)).thenReturn(Optional.ofNullable(category));
        when(categoryRepo.save(category)).thenReturn(category);
        when(modelMapper.map(category,CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto,1);

        assertThat(categoryDto1).isNotNull();
        assertThat(categoryDto1).isEqualTo(categoryDto);
        assertThat(categoryDto1).hasFieldOrPropertyWithValue("categoryTitle","Java");
    }

    @Test
    void deleteCategory() {
        Category category1 = Category.builder().id(1).categoryTitle("Android").categoryDescription("").build();

        when(categoryRepo.findById(2)).thenReturn(Optional.ofNullable(category1));

       assertAll(()->categoryService.deleteCategory(2));
       verify(categoryRepo).delete(category1);
       //verify(categoryRepo).save(category1);

    }

    @Test
    void getAllCategory() {
        Category category1 = Category.builder().categoryTitle("Android").categoryDescription("").build();
        Category category2 = Category.builder().categoryTitle("UnitTesting").categoryDescription("").build();
        List<Category> categories = Arrays.asList(category1,category2);

        when(categoryRepo.findAll()).thenReturn(categories);
        when(modelMapper.map(category1,CategoryDto.class))
                .thenReturn(CategoryDto.builder().categoryTitle(category1.getCategoryTitle())
                        .categoryDescription(category1.getCategoryDescription()).build());
        when(modelMapper.map(category2,CategoryDto.class))
                .thenReturn(CategoryDto.builder().categoryTitle(category2.getCategoryTitle())
                        .categoryDescription(category2.getCategoryDescription()).build());

        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        logger.info("Category : {}",categoryDtoList);

        assertThat(categoryDtoList).hasSize(2);
        assertThat(categoryDtoList).isNotEmpty();
        assertThat(categoryDtoList).hasSizeGreaterThan(1);
        //assertThat(categoryDtoList).isEqualTo(categories);

    }

    @Test
    void getById() {
        Category category = Category.builder().id(1).categoryTitle("sports").build();
        when(categoryRepo.findById(1)).thenReturn(Optional.ofNullable(category));
        when(modelMapper.map(category,CategoryDto.class))
                .thenReturn(CategoryDto.builder().id(1).categoryDescription("sports").build());

        CategoryDto categoryDto = categoryService.getById(1);
        logger.info("{}",categoryDto);

        assertThat(categoryDto).isNotNull();

    }
}
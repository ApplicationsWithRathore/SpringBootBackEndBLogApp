package com.firstapi.controllers;

import com.firstapi.payloads.ApiResponse;
import com.firstapi.payloads.CategoryDto;
import com.firstapi.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDto = this.categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer id){
                CategoryDto categoryDto = this.categoryService.getById(id);
                return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updatedCategories(@Valid @PathVariable Integer id,@RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto,id);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
    }



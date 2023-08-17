package com.nikhilkalamdane.electronic.store.controllers;

import com.nikhilkalamdane.electronic.store.dtos.ApiResponseMessage;
import com.nikhilkalamdane.electronic.store.dtos.CategoryDto;
import com.nikhilkalamdane.electronic.store.dtos.PageableResponse;
import com.nikhilkalamdane.electronic.store.dtos.UserDto;
import com.nikhilkalamdane.electronic.store.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Category is deleted successfully !!!")
                .status(HttpStatus.OK)
                .success(true)
                .build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //get by id
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        return new ResponseEntity<>(categoryService.get(categoryId), HttpStatus.FOUND);
    }
}

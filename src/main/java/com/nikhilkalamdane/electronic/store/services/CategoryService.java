package com.nikhilkalamdane.electronic.store.services;

import com.nikhilkalamdane.electronic.store.dtos.CategoryDto;
import com.nikhilkalamdane.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get category by id
    CategoryDto get(String categoryId);

    //search

}

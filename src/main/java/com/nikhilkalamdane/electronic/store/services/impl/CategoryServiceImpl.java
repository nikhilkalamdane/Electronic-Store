package com.nikhilkalamdane.electronic.store.services.impl;

import com.nikhilkalamdane.electronic.store.dtos.CategoryDto;
import com.nikhilkalamdane.electronic.store.dtos.PageableResponse;
import com.nikhilkalamdane.electronic.store.entities.Category;
import com.nikhilkalamdane.electronic.store.exceptions.ResourceNotFoundException;
import com.nikhilkalamdane.electronic.store.helper.Helper;
import com.nikhilkalamdane.electronic.store.repositories.CategoryRepository;
import com.nikhilkalamdane.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        String categoryID = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryID);

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(("Category not fount with given ID")));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(category.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);

        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(("Category not fount with given ID")));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending()) ;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(("Category not fount with given ID")));
        return mapper.map(category, CategoryDto.class);
    }
}

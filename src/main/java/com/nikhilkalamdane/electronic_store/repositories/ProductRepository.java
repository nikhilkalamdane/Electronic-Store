package com.nikhilkalamdane.electronic_store.repositories;

import com.nikhilkalamdane.electronic_store.entities.Category;
import com.nikhilkalamdane.electronic_store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

    //search
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
    Page<Product> findByCategory(Category category, Pageable pageable);
}

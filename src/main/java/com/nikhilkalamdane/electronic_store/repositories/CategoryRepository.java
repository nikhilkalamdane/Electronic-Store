package com.nikhilkalamdane.electronic_store.repositories;

import com.nikhilkalamdane.electronic_store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}

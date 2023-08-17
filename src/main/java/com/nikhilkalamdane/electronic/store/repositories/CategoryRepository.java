package com.nikhilkalamdane.electronic.store.repositories;

import com.nikhilkalamdane.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}

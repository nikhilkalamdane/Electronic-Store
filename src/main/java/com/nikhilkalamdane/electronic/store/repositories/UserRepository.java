package com.nikhilkalamdane.electronic.store.repositories;

import com.nikhilkalamdane.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Searches for users whose name contains the specified keyword.
     *
     * @param keyword The keyword to search for in user names.
     * @return A list of users whose names contain the keyword.
     */
    List<User> findByNameContaining(String keyword);
}

package com.myproject.ecommerce_service.repository.user;

import com.myproject.ecommerce_service.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    User registration(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String email);
    void update(User user);
}

package com.subrat.user_service.Repository;

import com.subrat.user_service.Emtity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}

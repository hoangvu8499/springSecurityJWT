package io.getarrays.userService.repo;

import io.getarrays.userService.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}

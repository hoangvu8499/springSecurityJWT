package io.getarrays.userService.service;

import io.getarrays.userService.domain.Role;
import io.getarrays.userService.domain.User;
import io.getarrays.userService.repo.RoleRepo;
import io.getarrays.userService.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            log.info("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database " + username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("saving new user " + user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role " + role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("add role " + roleName + " to user " + userName);
        User user = userRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
        userRepo.save(user);
    }

    @Override
    public User getUser(String userName) {
        log.info("get  user with userName " + userName);
        return userRepo.findByUserName(userName);
    }

    @Override
    public List<User> getUser() {
        log.info("fetch all user");
        return userRepo.findAll();
    }

}

package com.example.ProjectCuoiKy.services;

import com.example.ProjectCuoiKy.models.users;
import com.example.ProjectCuoiKy.repository.usersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class usersService {
    @Autowired
    private usersRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public usersService(usersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<users> findAll() {
        return userRepository.findAll();
    }


    public users findById(long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }


    public users save(users user) {
        return userRepository.save(user);
    }


    public users update(users user) {
        return userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public void registerUser(users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public boolean findByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

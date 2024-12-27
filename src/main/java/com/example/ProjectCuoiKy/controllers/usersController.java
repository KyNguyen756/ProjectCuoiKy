package com.example.ProjectCuoiKy.controllers;

import com.example.ProjectCuoiKy.models.users;
import com.example.ProjectCuoiKy.services.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class usersController {
    @Autowired
    private usersService usersService;
    @Autowired
    private com.example.ProjectCuoiKy.repository.usersRepository usersRepository;


    public usersController() {
    }


    @GetMapping("/users")
    @ResponseBody
    public List<users> getUserList() {
        return usersService.findAll();
    }



    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<users> getUserById(@PathVariable("id") long userId) {
        for (users user : usersService.findAll()) {
            if (user.getId() == userId) {
                return ResponseEntity.status(200).body(user);
            }
        }
        return ResponseEntity.status(404).body(null);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public List<users> removeUserById(@PathVariable("id") long id) {
        usersService.delete(id);
        return  usersService.findAll();
    }

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<users> createUser(@RequestBody users user) {
        usersService.save(user);
        return ResponseEntity.status(201).body(user);
    }


    @PutMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<users> udpateUser(@PathVariable("id") long id, @RequestBody users updateUser){
        users user = usersService.findById(id);
        if (user != null) {
            user.setUsername(updateUser.getUsername());
            user.setPassword(updateUser.getPassword());
            user.setEmail(updateUser.getEmail());
            user.setUpdatedAt(updateUser.getUpdatedAt());
            usersService.save(user);
            return ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(404).body(null);
    }
}

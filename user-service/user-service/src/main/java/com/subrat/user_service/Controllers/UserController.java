package com.subrat.user_service.Controllers;

import com.subrat.user_service.Emtity.UserEntity;
import com.subrat.user_service.Service.UserService;
import jakarta.persistence.Entity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


//    @PostMapping
//    public ResponseEntity<UserEntity> create(@RequestBody UserEntity userEntity){
//        return ResponseEntity.ok(userService.create(userEntity));
//    }


    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        return ResponseEntity.ok(userService.createUser(userEntity));
    }
}

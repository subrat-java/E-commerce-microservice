package com.subrat.user_service.Service;

import com.subrat.user_service.Emtity.UserEntity;
import com.subrat.user_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getAll(){
        return  userRepository.findAll();
    }
}

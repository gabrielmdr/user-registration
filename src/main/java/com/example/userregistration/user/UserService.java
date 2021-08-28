package com.example.userregistration.user;

import com.example.userregistration.user.exception.UserMissingFieldException;
import com.example.userregistration.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        if (user.getBirthdate() == null || user.getName() == null) {
            throw new UserMissingFieldException("The fields birthdate and name are required.");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new UserNotFoundException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        Long id = user.getId();
        if (id == null) {
            throw new UserMissingFieldException("No user ID specified.");
        }
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new UserNotFoundException("User with ID " + id + " does not exist.");
        }
        userRepository.save(user);
    }
}
package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) {
        user.setU_id(id);
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByUemail(email);
    }

    public boolean validateLoginCredentials(String email, String password) {
        User user = userRepository.findByUemail(email);
        return user != null && user.getUpassword().equals(password);
    }

	public User login(String userEmail, String userPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	public User UserLogin(String userEmail, String userPassword) {
		// TODO Auto-generated method stub
		return null;
	}
}

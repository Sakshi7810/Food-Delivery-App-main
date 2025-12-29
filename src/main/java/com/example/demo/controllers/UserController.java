package com.example.demo.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.User;
import com.example.demo.loginCredentials.UserLogin;
import com.example.demo.services.UserServices;

@Controller
public class UserController {

    @Autowired
    private UserServices userServices;

 // Show Registration Page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("userRegistration", new User());
        return "Register";  // Your HTML file name
    }

    // Handle Registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRegistration") User user, Model model) {

        // Check if email already exists
        User existingUser = userServices.getUserByEmail(user.getUemail());
        if (existingUser != null) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        userServices.addUser(user);
        return "redirect:/user-login"; // After register go to login
    }

    // Show user login page
    @GetMapping("/user-login")
    public String showUserLogin(Model model) {
        model.addAttribute("userLogin", new UserLogin());
        return "Login";   // matches Login.html
    }

    // Process login
    @PostMapping("/user-login")
    public String userLogin(@ModelAttribute("userLogin") UserLogin login,
                            HttpSession session,
                            Model model) {

        User user = userServices.getUserByEmail(login.getUserEmail());

        if (user == null || !user.getUpassword().equals(login.getUserPassword())) {
            model.addAttribute("error2", "Invalid Email or Password");
            return "Login";  // same template
        }

        session.setAttribute("loggedUser", user);

        return "redirect:/buy-product";  // make sure this mapping exists
    }
    

    // Logout
    @GetMapping("/logout-user")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}

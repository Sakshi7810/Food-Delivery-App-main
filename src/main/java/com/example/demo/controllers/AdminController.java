package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.*;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.services.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private OrderServices orderServices;
    
    private String email;
    private User user;
    
    @PostMapping("/adminLogin")
    public String getallData(@ModelAttribute("adminLogin") AdminLogin login, Model model)
    {
        String email = login.getEmail();
        String password = login.getPassword();

        if(adminServices.validateAdminCredentials(email, password)) {
            return "redirect:/admin/services";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Login";
        }
    }
 
    /* ---------- ADMIN DASHBOARD ---------- */
    @GetMapping("/services")
    public String adminDashboard(Model model) {

        // Null-safe initialization
        List<User> users = userServices.getAllUsers();
        List<Admin> admins = adminServices.getAllAdmins();
        List<Product> products = productServices.getAllProducts();
        List<Orders> orders = orderServices.getAllOrders();

        model.addAttribute("users", users != null ? users : new ArrayList<>());
        model.addAttribute("admins", admins != null ? admins : new ArrayList<>());
        model.addAttribute("products", products != null ? products : new ArrayList<>());
        model.addAttribute("orders", orders != null ? orders : new ArrayList<>());

        return "Admin_Page"; // Make sure Admin_Page.html exists in templates
    }

    /* ---------- ADD ADMIN ---------- */
    @GetMapping("/addAdmin")
    public String addAdminPage(Model model) {
        model.addAttribute("admin", new Admin());
        return "Add_Admin";
    }

    @PostMapping("/addingAdmin")
    public String addAdmin(@ModelAttribute("admin") Admin admin) {
        adminServices.addAdmin(admin);
        return "redirect:/admin/services";
    }

    /* ---------- UPDATE ADMIN ---------- */
    @GetMapping("/updateAdmin/{id}")
    public String updateAdminPage(@PathVariable int id, Model model) {
        Admin admin = adminServices.getAdmin(id);
        if (admin == null) return "redirect:/admin/services";

        model.addAttribute("admin", admin);
        return "Update_Admin";
    }

    @PostMapping("/updatingAdmin/{id}")
    public String updateAdmin(@PathVariable int id, @ModelAttribute("admin") Admin admin) {
        adminServices.updateAdmin(id, admin);
        return "redirect:/admin/services";
    }

    /* ---------- DELETE ADMIN ---------- */
    @PostMapping("/deleteAdmin/{id}")
    public String deleteAdmin(@PathVariable int id) {
        adminServices.deleteAdmin(id);
        return "redirect:/admin/services";
    }
    
    

    /* ---------- ADD PRODUCT ---------- */
    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "Add_Product";
    }

    /* ---------- UPDATE PRODUCT ---------- */
    @GetMapping("/updateProduct/{id}")
    public String updateProductPage(@PathVariable int id, Model model) {
        Product product = productServices.getProduct(id);
        if (product == null) return "redirect:/admin/services";

        model.addAttribute("product", product);
        return "Update_Product";
    }

    @PostMapping("/updatingProduct/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") Product product) {
        productServices.updateProduct(id, product);
        return "redirect:/admin/services";
    }

    /* ---------------------- USER CRUD ---------------------- */

    // Add User page
    @GetMapping("/addUser")
    public String addUserPage(Model model) {
        model.addAttribute("user", new User());
        return "Add_User";
    }

    // Add User handling
    @PostMapping("/addingUser")
    public String addUser(@ModelAttribute User user) {
        userServices.addUser(user);
        return "redirect:/admin/services";
    }

    // Update User page
    @GetMapping("/updateUser/{id}")
    public String loadUserForm(@PathVariable int id, Model model) {
        User user = userServices.getUser(id);
        model.addAttribute("user", user);
        return "Update_User";
    }

    // Update User handling
    @PostMapping("/updateUser/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable int id) {
        userServices.updateUser(id, user);
        return "redirect:/admin/services";
    }

    // Delete User
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        userServices.deleteUser(id);
        return "redirect:/admin/services";
    } 

    /* ---------- PLACE ORDER ---------- */
    @PostMapping("/product/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {

        // Search products
        List<Product> products = productServices.search(keyword);

        // Always load all dashboard data (required for Admin_Page)
        model.addAttribute("products", products != null ? products : new ArrayList<>());
        model.addAttribute("admins", adminServices.getAllAdmins());
        model.addAttribute("users", userServices.getAllUsers());
        model.addAttribute("orders", orderServices.getAllOrders());

        return "Admin_Page";  // return the SAME dashboard
    }

    @PostMapping("/product/order")
    public String orderHandler(@ModelAttribute Orders order,
                               HttpSession session,
                               Model model) {

        User sessionUser = (User) session.getAttribute("loggedUser");
        if (sessionUser == null) {
            model.addAttribute("error", "Please login to place an order.");
            return "Login";
        }

        order.setUser(sessionUser);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(order.getoPrice() * order.getoQuantity());

        orderServices.saveOrder(order);

        model.addAttribute("amount", order.getTotalAmount());
        return "Order_success";
    }
}

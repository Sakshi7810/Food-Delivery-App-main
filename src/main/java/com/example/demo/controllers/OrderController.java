package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired private ProductServices productServices;
    @Autowired private OrderServices orderServices;

    @GetMapping("/buy-product")
    public String buyProductPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/user-login";

        model.addAttribute("name", user.getUname());
        
        model.addAttribute("orders", Optional.ofNullable(orderServices.getOrdersForUser(user))
                .orElse(new ArrayList<>()));

        return "BuyProduct";
    }

    @RequestMapping(value="/product/search", method={RequestMethod.GET, RequestMethod.POST})
    public String searchProduct(@RequestParam(value="productName", required=false) String productName,
                                HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/user-login";

        List<Product> products = new ArrayList<>();
        if (productName != null && !productName.isEmpty()) {
            products = productServices.findByNameContaining(productName);
            if (products.isEmpty()) model.addAttribute("message", "Product not found!");
        }

        model.addAttribute("name", user.getUname());
        model.addAttribute("products", products);
        model.addAttribute("orders", orderServices.getOrdersForUser(user));
        return "BuyProduct";
    }


    @PostMapping("/product/order")
    public String placeOrder(@RequestParam("oName") String oName,
                             @RequestParam("oPrice") double oPrice,
                             @RequestParam("oQuantity") int oQuantity,
                             HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/user-login";

        // Validate quantity
        if (oQuantity <= 0) {
            model.addAttribute("message", "Quantity must be at least 1");
        } else {
            Orders order = new Orders();
            order.setoName(oName);
            order.setoPrice(oPrice);
            order.setoQuantity(oQuantity);
            order.setTotalAmount(oPrice * oQuantity);
            order.setOrderDate(LocalDateTime.now());
            order.setUser(user);

            orderServices.saveOrder(order);

            model.addAttribute("message", "Order placed successfully!");
        }

        // Fetch product to display
        List<Product> products = productServices.findByNameContaining(oName);
        Product product = products.isEmpty() ? null : products.get(0);
        model.addAttribute("product", product);

        // Display user orders
        model.addAttribute("orders", Optional.ofNullable(orderServices.getOrdersForUser(user)).orElse(new ArrayList<>()));
        model.addAttribute("name", user.getUname());

        return "BuyProduct";
    }

}

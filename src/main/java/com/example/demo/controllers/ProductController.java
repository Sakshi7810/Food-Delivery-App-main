package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    // Add Product
    @PostMapping("/addingProduct")
    public String addProduct(@ModelAttribute Product product) {
        productServices.addProduct(product);
        return "redirect:/admin/services";
    }

    // Update Product
    @PostMapping("/updateProduct/{productId}")
    public String updateProduct(@ModelAttribute Product product,
                                @PathVariable("productId") int id) {
        productServices.updateProduct(id, product);
        return "redirect:/admin/services";
    }

    // Delete Product
    @PostMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable("productId") int id) {
        productServices.deleteProduct(id);
        return "redirect:/admin/services";
    }
}

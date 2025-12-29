package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product p) {
        return productRepository.save(p);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(int id, Product product) {
        product.setPid(id);
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findByPname(name);
    }

	public Product findByName(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Product> findByNameContaining(String productName) {
	    List<Product> products = productRepository.findByPname(productName);
	    return products != null ? products : new ArrayList<>(); // never null
	}

	public List<Product> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	}


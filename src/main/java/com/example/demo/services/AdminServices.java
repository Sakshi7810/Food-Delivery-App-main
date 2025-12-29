package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;

@Service
public class AdminServices {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdmin(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(int id, Admin updatedAdmin) {
        updatedAdmin.setAdminId(id);
        return adminRepository.save(updatedAdmin);
    }

    public void deleteAdmin(int id) {
        adminRepository.deleteById(id);
    }

    public boolean validateAdminCredentials(String email, String password) {
        Admin admin = adminRepository.findByAdminEmail(email);
        return admin != null && admin.getAdminPassword().equals(password);
    }
}

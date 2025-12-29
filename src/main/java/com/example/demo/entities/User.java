package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int u_id;

    private String uname;
    private String uemail;
    private String upassword;
    private Long unumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> orders;

    // Constructors
    public User() {}

    public User(String uemail, String upassword) {
        this.uemail = uemail;
        this.upassword = upassword;
    }

    // Getters & Setters
    public int getU_id() { return u_id; }
    public void setU_id(int u_id) { this.u_id = u_id; }

    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }

    public String getUemail() { return uemail; }
    public void setUemail(String uemail) { this.uemail = uemail; }

    public String getUpassword() { return upassword; }
    public void setUpassword(String upassword) { this.upassword = upassword; }

    public Long getUnumber() { return unumber; }
    public void setUnumber(Long unumber) { this.unumber = unumber; }

    public List<Orders> getOrders() { return orders; }
    public void setOrders(List<Orders> orders) { this.orders = orders; }

    @Override
    public String toString() {
        return "User [u_id=" + u_id + ", uname=" + uname +
                ", uemail=" + uemail + "]";
    }
}

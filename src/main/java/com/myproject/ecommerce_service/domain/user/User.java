package com.myproject.ecommerce_service.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    public void updateProfile(String name, String phoneNumber, String address){
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void changePassword(String password){
        this.password = password;
    }
}

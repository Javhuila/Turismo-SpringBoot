package com.product.service;

import com.product.model.UserDtls;

public interface UserService {

    public UserDtls createUser(UserDtls user, String url);

    public Boolean checkEmail(String email);

    public Boolean verificaCuenta(String code);
}

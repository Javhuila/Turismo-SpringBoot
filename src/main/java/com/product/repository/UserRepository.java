package com.product.repository;

import com.product.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public Boolean existsByEmail(String email);

    public UserDtls findByEmail(String email);

    public UserDtls findByEmailAndAndTelefono(String em, String tel);

    public UserDtls findByVerificaCode(String code);
}

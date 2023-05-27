package com.product.repository;

import com.product.model.ContactDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactDtls, Integer> {

}

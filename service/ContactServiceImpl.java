package com.product.service;

import com.product.model.ContactDtls;
import com.product.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepo;

    @Override
    public ContactDtls createComm(ContactDtls contuser) {

        return contactRepo.save(contuser);
    }
}

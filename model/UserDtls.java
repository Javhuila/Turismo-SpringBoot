package com.product.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;


@Data
@Entity
public class UserDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String email;

    private String telefono;

    private String direccion;

    private String ciudad;

    private int edad;

    private String password;

    private String role;

    private boolean cuentaNoBlock;

    private boolean desHabil;

    private String verificaCode;

}

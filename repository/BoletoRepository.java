package com.product.repository;

import com.product.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    /*List<Boleto> findByBoletoId(Integer id);*/

}

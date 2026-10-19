package com.ensaf.products.repository;

import com.ensaf.products.model.DetailProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailProduitRepository extends JpaRepository<DetailProduit, Long> {
}

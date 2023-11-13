package com.apex.eqp.inventory.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apex.eqp.inventory.entities.Product;


@Repository
public interface InventoryRepository extends JpaRepository<Product, Integer> {
    // This JpaRepository functions only returns exact matching name
    // public Collection<Product> findByName(String name);
}

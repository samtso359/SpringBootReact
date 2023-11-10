package com.apex.eqp.inventory.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/inventory/product")
public class InventoryController {

    @Autowired
    private final ProductService productService;

    /**
     *
     * @return all the products that are not recalled
     */
    @GetMapping
    public ResponseEntity<Collection<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProductWithoutRecalled());
    }

    @GetMapping("/all")
    /**
     *
     * @return all the products
     */
    public ResponseEntity<Collection<Product>> getProducts(){
        return ResponseEntity.ok(productService.getAllProduct());

    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> findProduct(@PathVariable Integer id) {
        Optional<Product> byId = productService.findById(id);

        return byId.map(ResponseEntity::ok).orElse(null);
    }

    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable Integer id) {
        Optional<Product> byId = productService.updateById(id);
        return byId.map(ResponseEntity::ok).orElse(null);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
        Optional<Product> byId = productService.deleteById(id);

        return byId.map(ResponseEntity::ok).orElse(null);
    }

}

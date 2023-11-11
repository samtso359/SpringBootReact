package com.apex.eqp.inventory.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<Collection<Product>> searchProductByName(@PathVariable String name){
        return ResponseEntity.ok(productService.searchProductByName(name));
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
    ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Integer id) {
        if (product.getId() != id){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"ID parem and payload ID do not match!");
        }
        Optional<Product> target = productService.update(product);
        return ResponseEntity.ok(target.get());
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
        Optional<Product> byId = productService.deleteById(id);

        return byId.map(ResponseEntity::ok).orElse(null);
    }
    

}

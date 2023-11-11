package com.apex.eqp.inventory.controllers;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.services.RecalledProductService;
import lombok.RequiredArgsConstructor;

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

import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/inventory/recalled")
public class RecalledProductController {

    private final RecalledProductService recalledProductService;

    @PostMapping
    public ResponseEntity<RecalledProduct> createProduct(@RequestBody RecalledProduct recalledProduct) {
        return ResponseEntity.ok(recalledProductService.save(recalledProduct));
    }

    @GetMapping("/")
    public ResponseEntity<Collection<RecalledProduct>> findRecallProducts() {
        Collection<RecalledProduct> allRecalledProducts = recalledProductService.getAllRecalledProducts();

        return ResponseEntity.ok(allRecalledProducts);
    }

    @GetMapping("/{id}")
    ResponseEntity<RecalledProduct> findProduct(@PathVariable Integer id) {
        Optional<RecalledProduct> byId = recalledProductService.findById(id);
        return byId.map(ResponseEntity::ok).orElse(null);
    }

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<Collection<RecalledProduct>> searchRecalledProductByName(@PathVariable String name){
        return ResponseEntity.ok(recalledProductService.searchRecalledProductByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecalledProduct> deleteRecallProduct(@PathVariable Integer id) {
            Optional<RecalledProduct> byId = recalledProductService.deleteById(id);
            return byId.map(ResponseEntity::ok).orElse(null); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecalledProduct> updateRecallProduct(@RequestBody RecalledProduct recalledProduct, @PathVariable Integer id) {
        if (recalledProduct.getId() != id){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"ID parem and payload ID do not match!");
        }
        Optional<RecalledProduct> target = recalledProductService.update(recalledProduct);
        return ResponseEntity.ok(target.get());
    }
}

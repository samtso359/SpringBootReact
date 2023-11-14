package com.apex.eqp.inventory.services;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.helpers.ProductFilter;
import com.apex.eqp.inventory.repositories.InventoryRepository;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final InventoryRepository inventoryRepository;
    private final RecalledProductRepository recalledProductRepository;
    // ProductFilter filter = new ProductFilter();
    @Autowired
    ProductFilter filter;
    @Transactional
    public Product save(Product product) {
        return inventoryRepository.save(product);
    }

    public Collection<Product> getAllProduct() {
        return inventoryRepository.findAll();
    }

    public Collection<Product> getAllProductWithoutRecalled(){
        filter = new ProductFilter(recalledProductRepository.findAll());
        return filter.removeRecalledFrom(inventoryRepository.findAll());
    }

    public Collection<Product> searchProductByName(String name){
     
        return filter.searchByName(inventoryRepository.findAll(), name);
        // return inventoryRepository.findByName(name);
    }

    public Collection<Product> searchProductByNameWithoutRecalled(String name){
        filter = new ProductFilter(recalledProductRepository.findAll());

        return filter.searchByNameWithoutRecalled(inventoryRepository.findAll(), name);
        // return filter.searchByName(getAllProductWithoutRecalled(), name);
    }

    public Optional<Product> findById(Integer id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Product> update(Product product){
        Optional<Product> target = inventoryRepository.findById(product.getId());
        if (target.isPresent()){
            Product Foundedtarget = target.get();
            Foundedtarget.setName(product.getName());
            Foundedtarget.setPrice(product.getPrice());
            Foundedtarget.setQuantity(product.getQuantity());
            inventoryRepository.save(Foundedtarget);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Product "+product.getId()+" does not exist!");
        
        }
        
        return target;
    }

    public Optional<Product> deleteById(Integer id){
        Optional<Product> target = this.findById(id);
        if(target.isPresent()){
            inventoryRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Product "+id+" does not exist!");

        }
        return target;
    }
}

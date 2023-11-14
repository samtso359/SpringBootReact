package com.apex.eqp.inventory.services;

import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.helpers.RecalledProductFilter;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecalledProductService {

    private final RecalledProductRepository recalledProductRepository;

    public RecalledProduct save(RecalledProduct recalledProduct) {
        return recalledProductRepository.save(recalledProduct);
    }

    public Collection<RecalledProduct> getAllRecalledProducts() {
        return recalledProductRepository.findAll();
    }

    public Optional<RecalledProduct> findById(Integer id) {
        return recalledProductRepository.findById(id);
    }

    public Collection<RecalledProduct> searchRecalledProductByName(String name){
        RecalledProductFilter filter = new RecalledProductFilter();
        return filter.searchByName(recalledProductRepository.findAll(), name);
    }
    public Optional<RecalledProduct> deleteById(Integer id){
        Optional<RecalledProduct> target = this.findById(id);
        if(target.isPresent()){
            recalledProductRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Recalled Product "+id+" does not exist!");
        }
        return target;
    }

    public Optional<RecalledProduct> update(RecalledProduct recalledProduct){

        Optional<RecalledProduct> target = recalledProductRepository.findById(recalledProduct.getId());

        if (target.isPresent()){
            RecalledProduct Foundedtarget = target.get();
            Foundedtarget.setName(recalledProduct.getName());
            Foundedtarget.setExpired(recalledProduct.getExpired());
        
            recalledProductRepository.save(Foundedtarget);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Recalled Product "+recalledProduct.getId()+" does not exist!");
        
        }
        
        return target;
    }
}

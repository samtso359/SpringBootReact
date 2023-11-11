package com.apex.eqp.inventory.helpers;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class RecalledProductFilter {
    public RecalledProductFilter(){
    }
    public List<RecalledProduct> searchByName(Collection<RecalledProduct> allRecalledProduct, String name){
        return allRecalledProduct.stream().filter(p-> p.getName().contains(name)).collect(Collectors.toList());
    }   
}

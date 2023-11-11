package com.apex.eqp.inventory.helpers;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import com.apex.eqp.inventory.services.RecalledProductService;
import com.apex.eqp.inventory.entities.RecalledProduct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;


public class ProductFilter {

    private static Set<String> recalledProductsStrings;

    // public ProductFilter(Set<String> recalledProducts) {
    public ProductFilter(){
    }
    public ProductFilter(Collection<RecalledProduct> recalledProducts) {
        recalledProductsStrings = new HashSet<String>();
        recalledProducts.forEach(rp->recalledProductsStrings.add(rp.getName()));
    }

    public List<Product> removeRecalledFrom(Collection<Product> allProduct) {

        return allProduct.stream().filter(ProductFilter::filterByName).collect(Collectors.toList());
    }

    public List<Product> searchByName(Collection<Product> allProduct, String name){
        return allProduct.stream().filter(p-> p.getName().contains(name)).collect(Collectors.toList());
    }


    private static boolean filterByName(Product product) {
        return !recalledProductsStrings.contains(product.getName())?true : false ;
    }
}

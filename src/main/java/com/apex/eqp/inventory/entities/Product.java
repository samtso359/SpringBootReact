package com.apex.eqp.inventory.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String name;
    Double price;
    Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    // public void setName(String name){
    //     this.name = name;
    // }
    // public void setPrice(Double price){
    //     this.price = price;
    // }
    // public void setQuantity(int quantity){
    //     this.quantity  = quantity;
    // }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

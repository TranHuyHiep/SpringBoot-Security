package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Product;

public interface ProductService {

	Optional<Product> findById(Long id);

	List<Product> findAll();

	Boolean save(Product newProduct);

	Optional<Product> update(Product newProduct, Long id);

	Boolean deleteById(Long id);

}

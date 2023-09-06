package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository repository;

	
	@Override
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}


	@Override
	@Transactional
	public List<Product> findAll() {
		return repository.findAll();
	}


	@Override
	public Boolean save(Product newProduct) {
		// 2 product must not have the same name
		List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
		if(foundProducts.size() > 0) {
			return false;
		}
		repository.save(newProduct);
		return true;
	}


	@Override
	public Optional<Product> update(Product newProduct, Long id) {
		Product updateProduct = repository.findById(id)
				.map(product -> {
					product.setProductName(newProduct.getProductName());
					product.setPrice(newProduct.getPrice());
					product.setYear(newProduct.getYear());
					product.setCreatedAt(newProduct.getCreatedAt());
					product.setUpdatedAt(newProduct.getUpdatedAt());
					return repository.save(product);
		}).orElseGet(() -> {
			return repository.save(newProduct);
		});
		return Optional.of(updateProduct);
	}


	@Override
	public Boolean deleteById(Long id) {
		Boolean productExistBoolean = repository.existsById(id);
		if(productExistBoolean) {
			repository.deleteById(id);
			return true;
		}			
				
		return false; 
	}

}

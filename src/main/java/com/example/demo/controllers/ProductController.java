package com.example.demo.controllers;

import java.util.List;
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

import com.example.demo.models.Product;
import com.example.demo.models.ResponseObject;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	List<Product> getAllProduct()  {
		return productService.findAll();
	}
	
	//Get detail product
	@GetMapping("/{id}")
	//let's return an object with: data, message, status
	ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
		Optional<Product> foundProduct = productService.findById(id);

		return foundProduct.isPresent() ? 
			ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("ok", "Query product successful", foundProduct)
			):
			ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject("false", "Can't find product with id = " + id, "")
			);	
	}	
	
	// Insert Product with POST api 
	@PostMapping("/insert")
	ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
		return productService.save(newProduct) ? 
			ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("ok", "Insert product successfully", newProduct)
				):
				ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("false", "Product name " + newProduct.getProductName() + " already taken", "")
				);	
	}
	
	// Update, upsert = update if found, otherwise insert
	@PutMapping("/{id}")
	ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseObject("ok", "Update product successfully", productService.update(newProduct, id)));
	}
	
	// Delete product
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
		if(productService.deleteById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("ok", "Delete product successfully", "")
				);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ResponseObject("false", "Not found product id + " + id, "")
					);
		}
		
	}
}

package com.example.demo01.backend.services;

import com.example.demo01.backend.enums.ProductStatus;
import com.example.demo01.backend.models.Product;
import com.example.demo01.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        for (Product p:productRepository.findAll(pageable)             ) {
//            System.out.println(p);
//        }
        return productRepository.findAll(pageable);
    }
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(long id){
        return productRepository.findById(id).orElse(new Product());
    }

    public void deleteProductById(long id){
        Product product = productRepository.findById(id).orElse(new Product());
        product.setStatus(ProductStatus.TERMINATED);
        productRepository.save(product);
    }

    public void save(Product product) {
        product.setStatus(ProductStatus.ACTIVE);
        productRepository.save(product);
    }
    public void update(Product product) {
        Product product1 = productRepository.findById(product.getId()).orElse(new Product());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setUnit(product.getUnit());
        product1.setStatus(product.getStatus());
        productRepository.save(product1);
    }
}

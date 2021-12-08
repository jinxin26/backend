package tw.milktea.service;

import org.springframework.stereotype.Service;
import tw.milktea.model.Products;
import tw.milktea.repository.ProductsRepository;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Products addProducts(Products products) {
        return productsRepository.save(products);
    }
}

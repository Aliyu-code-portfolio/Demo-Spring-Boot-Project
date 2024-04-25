package product.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import product.demo.entity.Product;
import product.demo.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        }
        catch (Exception e) {
            throw  new RuntimeException("Failed saving the product: "+e.getMessage());
        }
    }

    public List<Product> fetchAllProducts(){
        try {
            return productRepository.findAll();
        }
        catch (Exception e){
            throw  new RuntimeException("Failed fetching all products: "+e.getMessage());
        }
    }

    public Optional<Product> fetchProductById(Long id) {
        try {
            return productRepository.findById(id);
        }
        catch (Exception e){
            throw  new RuntimeException("Failed fetching product by id: "+e.getMessage());
        }
    }

    public Optional<Product> updateProduct(Long id, Product product) {
        try {
            Optional<Product> existingProductOptional = productRepository.findById(id);
            if(existingProductOptional.isPresent()){
                Product existingProduct = existingProductOptional.get();
                existingProduct.setProductName(product.getProductName());
                existingProduct.setQuantity(product.getQuantity());
                existingProduct.setPrice(product.getPrice());
                Product productUpdated = productRepository.save(existingProduct);
                return Optional.of(productUpdated);
            }
            else{
                return Optional.empty();
            }
        }
        catch(Exception e){
            throw new RuntimeException("Failed to update product: "+ e.getMessage());
        }

    }
    public boolean deleteProduct(Long id){
        try {
                productRepository.deleteById(id);
                return true;
        }
        catch (Exception e){
            throw new RuntimeException("Failed to delete product: "+e.getMessage());
        }
    }
}

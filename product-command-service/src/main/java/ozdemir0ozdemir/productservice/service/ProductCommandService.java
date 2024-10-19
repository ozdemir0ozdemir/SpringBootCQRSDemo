package ozdemir0ozdemir.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ozdemir0ozdemir.productservice.dto.ProductEvent;
import ozdemir0ozdemir.productservice.entity.Product;
import ozdemir0ozdemir.productservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private static final Logger log = LoggerFactory.getLogger(ProductCommandService.class);
    private final ProductRepository repository;
    private final KafkaTemplate<String, Object> kafka;


    public Product createProduct(Product product) {
        Product result = repository.save(product);

        ProductEvent event =ProductEvent.ofCreatedEvent(product);
        kafka.send("product-event-topic", event);
        log.info("Product Created: {}", event);

        return result;
    }

    @Transactional
    public Product updateProduct(Long id, Product product){
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));

        existingProduct.setId(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        Product result = repository.save(existingProduct);

        ProductEvent event = ProductEvent.ofUpdatedEvent(result);
        kafka.send("product-event-topic", event);
        log.info("Product Updated: {}", event);

        return result;
    }
}

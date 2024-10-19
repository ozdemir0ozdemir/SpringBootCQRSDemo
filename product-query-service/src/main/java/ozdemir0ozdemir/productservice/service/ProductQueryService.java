package ozdemir0ozdemir.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ozdemir0ozdemir.productservice.dto.ProductEvent;
import ozdemir0ozdemir.productservice.entity.Product;
import ozdemir0ozdemir.productservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private static final Logger log = LoggerFactory.getLogger(ProductQueryService.class);
    private final ProductRepository repository;

    public List<Product> allProducts() {
        log.info("All Product Fetch Requested");
        return repository.findAll();
    }

    @KafkaListener(topics = {"product-event-topic"}, groupId = "product-event-group")
    public void processProductEvents(ProductEvent event) {
        log.info("Product event published: {}", event);
        if (ProductEvent.isCreateEvent(event)) {
            repository.save(event.getProduct());
        } else if (ProductEvent.isUpdateEvent(event)) {
            Product existingProduct = repository.findById(event.getProduct().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product Not Found!"));

            existingProduct.setName(event.getProduct().getName());
            existingProduct.setDescription(event.getProduct().getDescription());
            existingProduct.setPrice(event.getProduct().getPrice());

            repository.save(existingProduct);
        }
    }

}

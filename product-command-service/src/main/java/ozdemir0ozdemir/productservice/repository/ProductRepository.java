package ozdemir0ozdemir.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

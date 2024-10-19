package ozdemir0ozdemir.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ozdemir0ozdemir.productservice.entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductEvent {

    private String eventType;
    private Product product;

    public static ProductEvent ofCreatedEvent(Product product){
        return new ProductEvent("create-product", product);
    }

    public static ProductEvent ofUpdatedEvent(Product product){
        return new ProductEvent("update-product", product);
    }
}

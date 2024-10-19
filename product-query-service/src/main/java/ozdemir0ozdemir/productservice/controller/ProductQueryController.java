package ozdemir0ozdemir.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ozdemir0ozdemir.productservice.entity.Product;
import ozdemir0ozdemir.productservice.service.ProductQueryService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService service;

    @GetMapping
    public List<Product> allProducts() {
        return service.allProducts();
    }


}

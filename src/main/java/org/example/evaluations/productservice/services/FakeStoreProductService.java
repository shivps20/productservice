package org.example.evaluations.productservice.services;

import org.example.evaluations.productservice.dtos.FakeStoreProductDTO;
import org.example.evaluations.productservice.exceptions.ProductNotFoundException;
import org.example.evaluations.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService{

    private ResourceUrlProvider resourceUrlProvider;

    //To call external APIs, we can use something known as a Rest Template
    private RestTemplate restTemplate ;


    public FakeStoreProductService(RestTemplate restTemplate,
                                   ResourceUrlProvider resourceUrlProvider){
        this.restTemplate = restTemplate;
        this.resourceUrlProvider = resourceUrlProvider;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        FakeStoreProductDTO response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class);

        if(response == null){
            //Isn't null response an exception / unexpected
            //Should we throw an exception here
            //Should we handle this exception in some sort
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return response.toProduct();
    }

    @Override
    public Product getProductByName(String name) {
        return null;
    }

    @Override
    public Product createProduct(String name, String description, Double price, Long categoryId, String image) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        //https://fakestoreapi.com/products

        FakeStoreProductDTO[] fakeStoreProductDTOS = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class);

        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDTO fakeStoreProductDTO: fakeStoreProductDTOS){
            products.add(fakeStoreProductDTO.toProduct());
        }
        return products;
    }
}

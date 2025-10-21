package org.example.evaluations.productservice.services;

import org.example.evaluations.productservice.dtos.FakeStoreProductDTO;
import org.example.evaluations.productservice.exceptions.ProductNotFoundException;
import org.example.evaluations.productservice.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class FakeStoreProductService implements IProductService{

//    private ResourceUrlProvider resourceUrlProvider;
    private RedisTemplate<String, Object> redisTemplate;

    //To call external APIs, we can use something known as a Rest Template
    private RestTemplate restTemplate ;


    public FakeStoreProductService(RestTemplate restTemplate,
                                   RedisTemplate <String, Object> redisTemplate){
        this.restTemplate = restTemplate;
//        this.resourceUrlProvider = resourceUrlProvider;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {

        /**
         * If REDIS is implemented, we can check if the product with the given id exists in the cache
         * if exists then return from cache else call the external API and store the response in cache for future requests
         *
         * Make sure that the Object type is serializable as Redis while storing the object in cache converts the object into byte stream
         * and while fetching the object from cache it converts the byte stream back to Object
         *
         * To handle this we have BaseModle implements Serializable
         */
        Product product = (Product) redisTemplate.opsForHash().get("Product", id.toString());
        if(product != null){
            return product;
        }

        FakeStoreProductDTO response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class);

        if(response == null){
            //Isn't null response an exception / unexpected
            //Should we throw an exception here
            //Should we handle this exception in some sort
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        /**
         * Before returning the product, we can store it in REDIS cache for future requests
         * put() method takes 3 parameters
         * 1st parameter is the "type of Data" e.g. Product, this is needed since we can store multiple types of data in REDIS.
         *          Assume this is table/map name
         * 2nd parameter is the hash key  - this is hash of the key e.g. hash of product id
         * 3rd parameter is the value e.g. Product object
         */
        redisTemplate.opsForHash().put("Product", id.toString(), response.toProduct());

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

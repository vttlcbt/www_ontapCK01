package com.example.demo01;

import com.example.demo01.backend.enums.ProductStatus;
import com.example.demo01.backend.models.Product;
import com.example.demo01.backend.repositories.ProductRepository;
import net.datafaker.Faker;
import net.datafaker.providers.base.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo01Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo01Application.class, args);
    }

    @Autowired
    private ProductRepository productRepository;

//    @Bean
    CommandLineRunner createSampleProducts(){
        return args -> {
            Faker faker =new Faker();
            Device devices = faker.device();
            for (int i = 0; i < 10; i++) {
                Product product =new Product(
                        devices.modelName(),
                        "mo ta 001",
                        "piece",
                        ProductStatus.ACTIVE
                );
                productRepository.save(product);
            }
        };
    }
}

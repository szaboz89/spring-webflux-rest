package com.szabodev.webflux.rest.bootstrap;

import com.szabodev.webflux.rest.domain.Category;
import com.szabodev.webflux.rest.domain.Vendor;
import com.szabodev.webflux.rest.repository.CategoryRepository;
import com.szabodev.webflux.rest.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count().block() == 0) {
            System.out.println("Loading categories...");
            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();
            System.out.println("Categories loaded, count: " + categoryRepository.count().block());
        } else {
            System.out.println("Categories already loaded, count: " + categoryRepository.count().block());
        }

        if (vendorRepository.count().block() == 0) {
            System.out.println("Loading vendors...");
            vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Micheal").lastName("Weston").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Waters").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nershi").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffett").build()).block();
            System.out.println("Vendors loaded, count: " + vendorRepository.count().block());
        } else {
            System.out.println("Vendors already loaded, count: " + vendorRepository.count().block());
        }

    }
}

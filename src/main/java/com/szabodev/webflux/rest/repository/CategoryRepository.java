package com.szabodev.webflux.rest.repository;

import com.szabodev.webflux.rest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}

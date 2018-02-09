package com.szabodev.webflux.rest.repository;

import com.szabodev.webflux.rest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}

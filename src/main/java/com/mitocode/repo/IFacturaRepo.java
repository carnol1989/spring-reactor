package com.mitocode.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mitocode.document.Factura;

public interface IFacturaRepo extends ReactiveMongoRepository<Factura, String> {

}

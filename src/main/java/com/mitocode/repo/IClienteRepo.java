package com.mitocode.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mitocode.document.Cliente;

public interface IClienteRepo extends ReactiveMongoRepository<Cliente, String> {

}

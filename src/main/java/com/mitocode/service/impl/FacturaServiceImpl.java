package com.mitocode.service.impl;

import java.util.stream.Collectors;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.reactive.TransactionCallback;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.mitocode.document.Factura;
import com.mitocode.document.Plato;
import com.mitocode.pagination.PageSupport;
import com.mitocode.repo.IFacturaRepo;
import com.mitocode.repo.IPlatoRepo;
import com.mitocode.service.IFacturaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl implements IFacturaService {

//	@Autowired
//	private TransactionalOperator txo;
	
	@Autowired
	private IFacturaRepo repo;
	
	@Autowired
	private IPlatoRepo platoRepo;

	@Override
	public Mono<Factura> registrarService(Factura t) {
		return repo.save(t);
	}

	@Override
	public Mono<Factura> modificarService(Factura t) {
		return repo.save(t);
	}

	@Override
	public Flux<Factura> listarService() {
		return repo.findAll();
	}

	@Override
	public Mono<Factura> listarPorIdService(String v) {
		return repo.findById(v);
	}

	@Override
	public Mono<Void> eliminarService(String v) {
		return repo.deleteById(v);
	}

	@Override
	public Mono<PageSupport<Factura>> listarPaginaService(Pageable page) {
		return repo.findAll().collectList()
				.map(lista -> new PageSupport<>(
						lista
						.stream()
						.skip(page.getPageNumber() * page.getPageSize())
						.limit(page.getPageSize())
						.collect(Collectors.toList()), 
					page.getPageNumber(), page.getPageSize(), lista.size()));
	}

	//Could not start Mongo transaction for session null.; nested exception is 
	//com.mongodb.MongoClientException: Sessions are not supported by the MongoDB 
	//cluster to which this client is connected
	@Override
	public Mono<Factura> registrarTransaccionalService(Factura factura) throws InterruptedException {
		Plato plato = new Plato();
		plato.setEstado(true);
		plato.setNombre("CHAUFA MARISCOS");
		plato.setPrecio(35);
		
		Plato p2 = new Plato();
		p2.setEstado(true);
		p2.setNombre("CECINA");
		p2.setPrecio(27);
		
		//Versión antigua
		/*this.txo.execute(new TransactionCallback<Factura>() {

			@Override
			public Publisher<Factura> doInTransaction(ReactiveTransaction status) {
				// TODO Auto-generated method stub
				return null;
			}
		});*/
		
//		return this.txo.execute(status -> platoRepo.save(plato)).then(repo.save(factura));
		
		//Generar error para probar el trx
//		this.txo.execute(status -> platoRepo.save(plato)).then(repo.save(factura));
//		throw new InterruptedException("FALLO");
		
		//Haciendo varias llamadas
//		return this.txo.execute(status -> platoRepo.save(plato))
//				.then(platoRepo.save(p2))
//				.then(repo.save(factura));
		return null;
	}
	
}

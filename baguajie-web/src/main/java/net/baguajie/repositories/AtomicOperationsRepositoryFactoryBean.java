package net.baguajie.repositories;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class AtomicOperationsRepositoryFactoryBean <T extends Repository<S, ID>, S, ID extends Serializable> 
	extends MongoRepositoryFactoryBean<T, S, ID> {
	
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new AtomicOperationsRepositoryFactory(operations);
	}
	
}

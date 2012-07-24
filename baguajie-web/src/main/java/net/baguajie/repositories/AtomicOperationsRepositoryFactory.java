package net.baguajie.repositories;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

public class AtomicOperationsRepositoryFactory 
	extends MongoRepositoryFactory {

	private final MongoOperations mongoOperations;
	
	public AtomicOperationsRepositoryFactory(MongoOperations mongoOperations) {
		super(mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object getTargetRepository(RepositoryMetadata metadata) {
		MongoEntityInformation<?, Serializable> entityInformation = 
				getEntityInformation(metadata.getDomainClass());

		return new AtomicOperationsRepositoryImpl(entityInformation, 
				mongoOperations);
	}
	
	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return AtomicOperationsRepository.class;
	}
}

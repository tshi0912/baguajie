package net.baguajie.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class AtomicOperationsRepositoryImpl<T, ID extends Serializable> 
		extends SimpleMongoRepository<T, ID> implements AtomicOperationsRepository<T, ID> {

	private final MongoOperations mongoOperations;
	private final MongoEntityInformation<T, ID> entityInformation;
	
	public AtomicOperationsRepositoryImpl(
			MongoEntityInformation<T, ID> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.entityInformation = metadata;
		this.mongoOperations = mongoOperations;
	}

	public void inc(ID id, String property, Number inc) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().inc(property, inc), entityInformation.getJavaType());
	}

	public void set(ID id, String property, Object value) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().set(property, value), entityInformation.getJavaType());
	}

	public void unset(ID id, String property) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().unset(property), entityInformation.getJavaType());
	}

	public void push(ID id, String property, Object value) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().push(property, value), entityInformation.getJavaType());
	}

	public void pushAll(ID id, String property, Object[] values) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().pushAll(property, values), entityInformation.getJavaType());
	}

	public void pull(ID id, String property, Object value) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().pull(property, value), entityInformation.getJavaType());
	}

	public void pullAll(ID id, String property, Object[] values) {
		mongoOperations.updateFirst(getIdQuery(id), 
				new Update().pullAll(property, values), entityInformation.getJavaType());
	}

	public void bit(ID id, String property) {
		//TODO:
	}
	
	private Query getIdQuery(Object id) {
		return new Query(getIdCriteria(id));
	}

	private Criteria getIdCriteria(Object id) {
		return where(entityInformation.getIdAttribute()).is(id);
	}
}

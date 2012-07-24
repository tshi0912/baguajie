package net.baguajie.repositories;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AtomicOperationsRepository<T, ID extends Serializable>
		extends MongoRepository<T, ID> {

	void inc (ID id, String property, Number inc);
	
	void set (ID id, String property, Object value);
	
	void unset(ID id, String property);
	
	void push(ID id, String property, Object value);
	
	void pushAll(ID id, String property, Object[] values);
	
	void pull(ID id, String property, Object value);
	
	void pullAll(ID id, String property, Object[] values);
	
	void bit(ID id, String property);
}

package net.baguajie.repositories;

import net.baguajie.domains.Forward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface ForwardRepository extends 
		AtomicOperationsRepository<Forward, String> {

	@Query("{ 'act': {'$ref': 'activity', '$id': { '$oid': ?0 } }}")
	Page<Forward> findByAct(String id, Pageable pageable);
}

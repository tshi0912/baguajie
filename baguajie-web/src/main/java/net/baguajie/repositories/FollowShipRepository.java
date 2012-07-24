package net.baguajie.repositories;

import java.util.List;

import net.baguajie.domains.FollowShip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface FollowShipRepository extends
		AtomicOperationsRepository<FollowShip, String> {
	
	@Query("{ 'target': {'$ref': 'user', '$id': { '$oid': ?0 } } , " +
			" 'followed': {'$ref': 'user', '$id': { '$oid': ?1 } }}")
	FollowShip getByTargetAndFollowed(String targetId, String followedId);
	
	@Query("{ 'target': {'$ref': 'user', '$id': { '$oid': ?0 } } , " +
			" 'status':  ?1 }")
	Page<FollowShip> findByTargetAndStatus(String targetId, int status, Pageable pageable);
	
	@Query("{ 'followed': {'$ref': 'user', '$id': { '$oid': ?0 } } , " +
			" 'status':  ?1 }")
	Page<FollowShip> findByFollowedAndStatus(String targetId, int status, Pageable pageable);
	
	@Query("{ 'followed': {'$ref': 'user', '$id': { '$oid': ?0 } } , " +
			" 'status':  ?1 }")
	List<FollowShip> findByFollowedAndStatus(String targetId, int status);
}

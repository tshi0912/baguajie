package net.baguajie.repositories;

import java.util.List;

import net.baguajie.constants.ActivityType;
import net.baguajie.domains.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface ActivityRepository extends
	AtomicOperationsRepository<Activity, String> {

	Page<Activity> findByOwner(String id, Pageable pageable);
	
	Activity getByOwnerAndTargetSpotAndType(String uid, String sid, String type);
	
	@Query("{ 'owner': {$in : ?0}, 'type': { $in : ?1 } }")
	Page<Activity> findByOwnerInAndTypeIn(List<String> uids, List<String> types, Pageable pageable);
	
	@Query("{ 'owner': ?0, 'type': { $in : ?1 } }")
	List<Activity> findByOwnerAndTypeIn(String id, List<String> types);
	
	@Query("{ 'targetSpot': {$in : ?0}, 'type': { $in : ?1 } }")
	Page<Activity> findByTragetSpotInAndTypeIn(List<String> sids, List<String> types, Pageable pageable);
	
	@Query("{ 'targetUser': ?0, 'type': { $in : ?1 } }")
	Page<Activity> findByTragetUserAndTypeIn(String id, List<String> types, Pageable pageable);
	
	@Query("{ 'basedOn': {$in : ?0}, 'type': { $in : ?1 }, 'owner': { $ne : ?2 } }")
	Page<Activity> findByBasedOnInAndTypeInAndOwnerNot(List<String> bids, List<String> types, String oid, Pageable pageable);
	
	Page<Activity> findByOwnerAndType(String id, ActivityType type, Pageable pageable);
}

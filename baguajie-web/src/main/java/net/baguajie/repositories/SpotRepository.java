package net.baguajie.repositories;

import net.baguajie.domains.Spot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface SpotRepository extends 
		AtomicOperationsRepository<Spot, String> {
	
	//search api......................................................
	
	@Query("{ '$where': 'function() { return (?0 ? this.city== ?0 : true) && " +
			" (?1 ? this.category== ?1 : true) && (?2 ? (this.summary? this.summary.indexOf(?2)!=-1 : false) : true); } ' }")
	Page<Spot> search(String city, String category,
			String summaryLike, Pageable pageable);
	
	@Query("{ '$where': 'function() { return this.lngLat && (?0 ? this.city== ?0 : true) && " +
			" (?1 ? this.category== ?1 : true) && (?2 ? (this.summary? this.summary.indexOf(?2)!=-1 : false) : true); } ' }")
	Page<Spot> searchMarker(String city, String category,
			String summaryLike, Pageable pageable);
	
	Page<Spot> findByCityAndCategoryAndSummaryLike(String city, String category,
			String summaryLike, Pageable pageable);
	
	Page<Spot> findByCityAndCategory(String city, String category,
			Pageable pageable);
	
	Page<Spot> findByCityAndSummaryLike(String city, String summaryLike,
			Pageable pageable);
	
	Page<Spot> findByCategoryAndSummaryLike(String category, String summaryLike,
			Pageable pageable);
	
	Page<Spot> findByCity(String city, Pageable pageable);
	
	Page<Spot> findByCategory(String category, Pageable pageable);
	
	Page<Spot> findBySummaryLike(String summaryLike, Pageable pageable);

	//..............................................................
	
	@Query("{ 'createdBy': {'$ref': 'user', '$id': { '$oid': ?0 } } , 'category' : ?1 }")
	Page<Spot> findByCreatedByAndCategory(String id, String category,
			Pageable pageable);
	
	@Query("{ 'createdBy': {'$ref': 'user', '$id': { '$oid': ?0 } } }")
	Page<Spot> findByCreatedBy(String id, Pageable pageable);
	
	@Query("{'lngLat' : { '$nearSphere' : [ ?0 , ?1] , '$maxDistance' : ?2 } }")
	Page<Spot> findByLngLatNear(Double lng, Double lat, Double distance,
			Pageable pageable);
}

package net.baguajie.repositories;

import net.baguajie.constants.Gender;
import net.baguajie.domains.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends 
		AtomicOperationsRepository<User, String> {
	
	User getByEmail(String email);
	
	User getByName(String name);
	
	//search api......................................................
	
	@Query("{ '$where': 'function() { return (?0 ? this.city== ?0 : true) && " +
			" (?1 ? this.gender== ?1 : true) && (?2 ? (this.name? this.name.indexOf(?2)!=-1 : false) : true); } ' }")
	Page<User> search(String city, String gender, String nameLike, Pageable pageable);
	
	Page<User> findByCityAndGenderAndNameLike(String city, Gender gender, String keyword,
			Pageable pageable);
	
	Page<User> findByCityAndGender(String city, Gender gender, Pageable pageable);
	
	Page<User> findByGenderAndNameLike(Gender gender, String keyword, Pageable pageable);
	
	Page<User> findByCityAndNameLike(String city, String keyword, Pageable pageable);
	
	Page<User> findByCity(String city, Pageable pageable);
	
	Page<User> findByGender(Gender gender, Pageable pageable);
	
	Page<User> findByNameLike(String keyword, Pageable pageable);
	
	//..................................................................
}

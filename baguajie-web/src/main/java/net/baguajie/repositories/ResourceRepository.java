package net.baguajie.repositories;

import net.baguajie.domains.Resource;

public interface ResourceRepository extends 
		AtomicOperationsRepository<Resource, String> {
	
	Resource getByResId(String resId);
}

package net.baguajie.repositories;

import net.baguajie.domains.User;
import net.baguajie.domains.UserPreference;

public interface UserPreferenceRepository extends
		AtomicOperationsRepository<UserPreference, String> {
	
	UserPreference getByUser(User user);
}

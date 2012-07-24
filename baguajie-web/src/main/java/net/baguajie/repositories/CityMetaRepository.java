package net.baguajie.repositories;

import net.baguajie.domains.CityMeta;

public interface CityMetaRepository extends
		AtomicOperationsRepository<CityMeta, String> {
	
	CityMeta getByPinyin(String pinyin);
	
	CityMeta getByName(String name);
}

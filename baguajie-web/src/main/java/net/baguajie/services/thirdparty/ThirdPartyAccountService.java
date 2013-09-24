package net.baguajie.services.thirdparty;

import net.baguajie.vo.AccountVo;

public interface ThirdPartyAccountService {
	
	AccountVo getAccount(String thirdPartyId);
	
}

package net.baguajie.web.ros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.SpotStatus;
import net.baguajie.constants.UserStatus;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Component;

@Component
@RemotingDestination("adminRO")
public class AdminRemoteObject {

	@Autowired
	private SpotRepository spotRepository;
	@Autowired
	private UserRepository userRepository;

	@RemotingInclude
	public ROResult getSpotsAtPage(int no) {
		ROResult result = new ROResult();
		try {
			Pageable pageable = new PageRequest(Math.max(no, 0),
					ApplicationConfig.masonryPageSize, new Sort(new Order(
							Direction.DESC, "createdAt")));
			Page<Spot> page = spotRepository.findAll(pageable);
			result.setResult(page.getContent());
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}

	@RemotingInclude
	public ROResult getUsersAtPage(int no) {
		ROResult result = new ROResult();
		try {
			Pageable pageable = new PageRequest(Math.max(no, 0),
					ApplicationConfig.masonryPageSize, new Sort(new Order(
							Direction.DESC, "createdAt")));
			Page<User> page = userRepository.findAll(pageable);
			result.setResult(page.getContent());
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}
	
	@RemotingInclude
	public ROResult updateSpotStatus(String id, SpotStatus status)
	{
		ROResult result = new ROResult();
		try {
			Spot spot = spotRepository.findOne(id);
			if(spot != null){
				spot.setStatus(status);
				spot.setUpdatedAt(new Date());
				spotRepository.save(spot);
				result.setResult(spot);
			}else{
				throw new RuntimeException("Could not find spot with id \"" + id + "\"");
			}
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}
	
	@RemotingInclude
	public ROResult updateUserStatus(String id, UserStatus status)
	{
		ROResult result = new ROResult();
		try {
			User user = userRepository.findOne(id);
			if(user != null){
				user.setStatus(status);
				user.setUpdatedAt(new Date());
				userRepository.save(user);
				result.setResult(user);
			}else{
				throw new RuntimeException("Could not find user with id \"" + id + "\"");
			}
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}
}

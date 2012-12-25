package net.baguajie.web.ros;

import java.util.Date;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.Role;
import net.baguajie.constants.SpotStatus;
import net.baguajie.constants.UserStatus;
import net.baguajie.domains.Spot;
import net.baguajie.domains.User;
import net.baguajie.repositories.SpotRepository;
import net.baguajie.repositories.UserRepository;
import net.baguajie.vo.PageVo;

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
	public ROResult signIn(String name, String pwd) {
		ROResult result = new ROResult();
		try {
			User existed = null;
			if (name != null && pwd != null) {
				existed = userRepository.getByName(name);
			} else {
				throw new RuntimeException("用户名或密码不能为空");
			}
			if (existed == null) {
				throw new RuntimeException("用户\"" + name + "\"不存在");
			} else if (!pwd.equals(existed.getPassword())) {
				throw new RuntimeException("登陆密码错误");
			} else if (Role.ADMIN != existed.getRole()) {
				throw new RuntimeException("该用户无此权限");
			}
			result.setResult(existed);
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}

	@RemotingInclude
	public ROResult getSpotsAtPage(int at, int size) {
		ROResult result = new ROResult();
		try {
			Pageable pageable = new PageRequest(Math.max(at, 0), size,
					new Sort(new Order(Direction.DESC, "createdAt")));
			Page<Spot> page = spotRepository.findAll(pageable);
			result.setResult(PageVo.from(page));
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}

	@RemotingInclude
	public ROResult getUsersAtPage(int at, int size) {
		ROResult result = new ROResult();
		try {
			Pageable pageable = new PageRequest(Math.max(at, 0), size,
					new Sort(new Order(Direction.DESC, "createdAt")));
			Page<User> page = userRepository.findAll(pageable);
			result.setResult(PageVo.from(page));
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}

	@RemotingInclude
	public ROResult updateSpotStatus(String id, SpotStatus status) {
		ROResult result = new ROResult();
		try {
			Spot spot = spotRepository.findOne(id);
			if (spot != null) {
				spot.setStatus(status);
				spot.setUpdatedAt(new Date());
				spotRepository.save(spot);
				result.setResult(spot);
			} else {
				throw new RuntimeException("Could not find spot with id \""
						+ id + "\"");
			}
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}

	@RemotingInclude
	public ROResult updateUser(User u) {
		ROResult result = new ROResult();
		try {
			User user = userRepository.findOne(u.getId());
			if (user != null) {
				user.setStatus(u.getStatus());
				user.setRole(u.getRole());
				user.setUpdatedAt(new Date());
				userRepository.save(user);
				result.setResult(user);
			} else {
				throw new RuntimeException("Could not find user with id \""
						+ u.getId() + "\"");
			}
		} catch (Exception e) {
			result.setErrorCode(new ErrorCode(ErrorCode.BUSINESS_ERROR,
					ErrorCode.ERROR, e.getMessage()));
			result.addErrorMsg(e.getMessage());
		}
		return result;
	}
}

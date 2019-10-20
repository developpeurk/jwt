package org.sid.service;

import org.h2.api.UserToRolesMapper;
import org.sid.dao.RoleRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class AccountServiceImp implements AccountService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public AppUser saveUser(AppUser user) {
		String hashPw = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashPw);
		return userRepository.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppRole role = roleRepository.findByRoleName(roleName);
		AppUser user = userRepository.findByUserName(username);
		user.getRoles().add(role);
		

	}

	@Override
	public AppUser findUserByUserName(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(username);
	}

}

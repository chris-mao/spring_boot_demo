/**
 * 
 */
package com.example.demo.auth.service;

import java.util.List;
import java.util.Set;

import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;

/**
 * com.example.demo.auth.service AuthRoleService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthRoleService {
	
	/**
	 * 
	 * @return
	 */
	public List<AuthRole> findAll();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public AuthRole findById(Integer id);
	
	/**
	 * 
	 * @param roleName
	 * @return
	 */
	public AuthRole findByName(String roleName);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public Set<AuthRole> findAllByUser(AuthUser user);
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public Set<AuthRole> findAllByUser(String userName);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	public boolean insert(AuthRole role);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	public boolean update(AuthRole role);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id);
}

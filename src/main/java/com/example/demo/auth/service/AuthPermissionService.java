/**
 * 
 */
package com.example.demo.auth.service;

import java.util.List;
import java.util.Set;

import com.example.demo.auth.entity.AuthPermission;
import com.example.demo.auth.entity.AuthRole;
import com.example.demo.auth.entity.AuthUser;

/**
 * com.example.demo.auth.service AuthPermissionService
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AuthPermissionService {

	/**
	 * 
	 * @return
	 */
	public List<AuthPermission> findAll();

	/**
	 * 
	 * @param roleName
	 * @return
	 */
	public AuthPermission findByName(String permissionName);
	
	/**
	 * grant the specific permission to the role
	 * 
	 * @param permissionName
	 * @param role
	 */
	public void grant(String permissionName, AuthRole role);
	
	/**
	 * grant the specific permission to the user
	 * 
	 * @param permissionName
	 * @param user
	 */
	public void grant(String permissionName, AuthUser user);
	
	/**
	 * revoke the permission from the specific role
	 * 
	 * @param permissionName
	 * @param role
	 */
	public void revoke(String permissionName, AuthRole role);
	
	/**
	 * revoke the permission from the specific user
	 * 
	 * @param permissionName
	 * @param user
	 */
	public void revoke(String permissionName, AuthUser user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	public Set<AuthPermission> findAllByUser(AuthUser user);
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public Set<AuthPermission> findAllByUser(String userName);
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	public Set<AuthPermission> findAllByRole(AuthRole role);

	/**
	 * 
	 * @param roleName
	 * @return
	 */
	public Set<AuthPermission> findAllByRole(String roleName);
	
	/**
	 * 
	 * @param permission
	 * @return
	 */
	public AuthPermission insert(AuthPermission permission);
	
	/**
	 * 
	 * @param permission
	 * @return
	 */
	public int update(AuthPermission permission);
	
	/**
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}

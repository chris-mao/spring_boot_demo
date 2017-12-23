/**
 * 
 */
package com.jrsoft.organization.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.jrsoft.organization.dao.sqlprovider.DepartmentDynaSqlProvider;
import com.jrsoft.organization.entity.Department;

/**
 * 部门数据访问类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface DepartmentDAO {

	/**
	 * 查询所有部门
	 * 
	 * @param onlyAvailable
	 *            <code>true</code>仅查询所有可用部门，否则查询所有部门
	 * 
	 * @return List
	 */
	@SelectProvider(method = "findAllSql", type = DepartmentDynaSqlProvider.class)
	@Results({ @Result(property = "departmentId", column = "department_id", id = true),
			@Result(property = "departmentName", column = "department_name"),
			@Result(property = "parentId", column = "parent_id"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Department> findAll(@Param(value = "available") boolean onlyAvailable);

	/**
	 * 按部门编号查询
	 * 
	 * @param departmentId
	 * @return AuthPermission
	 */
	@Select("SELECT department_id, department_name, parent_id, available, created_time, update_time FROM department WHERE department_id = #{id}")
	@Results({ @Result(property = "departmentId", column = "department_id", id = true),
			@Result(property = "departmentName", column = "department_name"),
			@Result(property = "parentId", column = "parent_id"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Department findById(@Param(value = "id") int id);

	/**
	 * 按部门名称查询
	 * 
	 * @param departmentName
	 * @return AuthPermission
	 */
	@Select("SELECT department_id, department_name, parent_id, available, created_time, update_time FROM department WHERE department_name = #{name}")
	@Results({ @Result(property = "departmentId", column = "department_id", id = true),
			@Result(property = "departmentName", column = "department_name"),
			@Result(property = "parentId", column = "parent_id"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public Department findByName(@Param(value = "name") String deptName);

	/**
	 * 按部门名模糊查询
	 * 
	 * @since 1.1
	 * @param department
	 * @return
	 */
	@Select("SELECT department_id, department_name, parent_id, available, created_time, update_time FROM department WHERE department_name LIKE #{departmentName}")
	@Results({ @Result(property = "departmentId", column = "department_id", id = true),
			@Result(property = "departmentName", column = "department_name"),
			@Result(property = "parentId", column = "parent_id"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Department> fuzzyQuery(Department department);

	/**
	 * 按父节点编号查询子部门
	 * 
	 * @since 1.1
	 * @param parentId
	 * @return
	 */
	@Select("SELECT department_id, department_name, parent_id, available, created_time, update_time FROM department WHERE parent_id = #{parentId}")
	@Results({ @Result(property = "departmentId", column = "department_id", id = true),
			@Result(property = "departmentName", column = "department_name"),
			@Result(property = "parentId", column = "parent_id"), @Result(property = "available", column = "available"),
			@Result(property = "createdTime", column = "created_time"),
			@Result(property = "updateTime", column = "update_time") })
	public List<Department> findChildNodes(@Param(value = "parentId") int parentId);

	/**
	 * @since 1.1
	 * @param parentId
	 * @return
	 */
	@Select("SELECT COUNT(department_id) FROM department WHERE parent_id = #{parentId}")
	public int getChildrenCount(@Param(value = "parentId") int parentId);

	/**
	 * 创建新部门
	 * 
	 * @param department
	 * @return 受影响的行数
	 */
	@Insert("INSERT INTO department(department_name, parent_id, available, created_time) VALUES(#{departmentName}, #{parentId}, #{available}, NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "departmentId")
	public int insert(Department department);

	/**
	 * 更新部门
	 * 
	 * @param department
	 * @return 受影响的行数
	 */
	@Update("UPDATE department SET department_name = #{departmentName}, parent_id = #{parentId}, available = #{available} WHERE department_id = #{departmentId}")
	public int udpate(Department department);

	/**
	 * 删除部门
	 * 
	 * @param id
	 * @return 受影响的行数
	 */
	@Delete("DELETE FROM department WHERE department_id = #{id}")
	public int delete(@Param(value = "id") int id);

}

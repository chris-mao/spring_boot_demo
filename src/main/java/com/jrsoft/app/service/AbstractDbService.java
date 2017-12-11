package com.jrsoft.app.service;

import java.util.List;
import com.github.pagehelper.PageInfo;
import com.jrsoft.common.EasyDataGrid;

/**
 * 数据库操作接口
 * 
 * 做为与数据库交互的服务接口的基本接口，定义了数据的增、删、改、查等基础方法
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public interface AbstractDbService<T> {
	
	/**
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 查询所有数据，不具备分页功能
	 * 
	 * @param onlyAvailable
	 *            仅返回有效的数据
	 * 
	 * @since 1.0
	 * @return List
	 */
	public List<T> findAll(boolean onlyAvailable);

	/**
	 * 查询所有数据，具有分页功能
	 * 
	 * @since 1.0
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @return {@link PageInfo}
	 */
	public PageInfo<T> findAll(int pageNum, int pageSize);

	/**
	 * 根据传入的查询条件查询数据，具有分页功能 如果参数searchStr为空，则查询所有数据，否则查询符合查询条件的数据
	 * 
	 * @since 1.0
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param searchStr
	 *            模糊查询内容
	 * @return
	 */
	public EasyDataGrid<T> findAll(int pageIndex, int pageSize, String searchStr);

	/**
	 * 根据传入的实体类有值的属性进行查询
	 * 
	 * @since 1.0
	 * @param T
	 * @return T
	 */
	public T findOne(T entity);

	/**
	 * 创建新实体
	 * 
	 * @since 1.0
	 * @param T
	 * @return 成功返回true,否则返回false
	 */
	public boolean insert(T entity);

	/**
	 * 更新实体
	 * 
	 * @since 1.0
	 * @param T
	 * @return 成功返回true,否则返回false
	 */
	public boolean update(T entity);

	/**
	 * 删除实体数据
	 * 
	 * @param id
	 * @return 成功返回true,否则返回false
	 */
	public boolean delete(int id);

}

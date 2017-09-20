/**
 * 
 */
package com.example.demo.auth.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.demo.auth.AuthUserState;

/**
 * com.example.demo.auth.dao.handler AuthUserStateTypeHandler
 * 
 * 用户状态类型转换类，用于将数据表中的枚举转为Java中的枚举类型
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthUserStateTypeHandler extends BaseTypeHandler<AuthUserState> {
	
	private Class<AuthUserState> typeClass;
	
	private AuthUserState[] enums;
	
	public AuthUserStateTypeHandler(Class<AuthUserState> typeClass) {
		if (null == typeClass) {
			throw new IllegalArgumentException("Type argument cannot be null.");
		}
		this.typeClass = typeClass;
		this.enums = typeClass.getEnumConstants();
		if (null == this.enums) {
			throw new IllegalArgumentException(typeClass.getSimpleName() + " does not repserent an enum type.");
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, AuthUserState parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AuthUserState getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String stateName = rs.getString(columnName);
		return rs.wasNull() ? null : translateToAuthUserStateType(stateName);
	}

	@Override
	public AuthUserState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String stateName = rs.getString(columnIndex);
		return rs.wasNull() ? null : translateToAuthUserStateType(stateName);
	}

	@Override
	public AuthUserState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String stateName = cs.getString(columnIndex);
		return cs.wasNull() ? null : translateToAuthUserStateType(stateName);
	}
	
	private AuthUserState translateToAuthUserStateType(String state) {
		AuthUserState result = null;
		for(AuthUserState userState: this.enums) {
			if (userState.getText().equals(state)) {
				result = userState;
				break;
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("未知枚举" + state + ", 请核对 " + typeClass.getSimpleName());			
		}
		return result;
	}

}

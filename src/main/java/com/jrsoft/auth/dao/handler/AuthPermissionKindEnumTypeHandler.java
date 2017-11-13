/**
 * 
 */
package com.jrsoft.auth.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.jrsoft.auth.AuthPermissionKindEnum;

/**
 * com.jrsoft.auth.dao.handler AuthPermissionKindEnumTypeHandler
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class AuthPermissionKindEnumTypeHandler extends BaseTypeHandler<AuthPermissionKindEnum> {

	private Class<AuthPermissionKindEnum> typeClass;

	private AuthPermissionKindEnum[] enums;
	
	public AuthPermissionKindEnumTypeHandler(Class<AuthPermissionKindEnum> typeClass) {
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
	public void setNonNullParameter(PreparedStatement ps, int i, AuthPermissionKindEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getText());
	}

	@Override
	public AuthPermissionKindEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String kind = rs.getString(columnName);
		return rs.wasNull() ? null : translateToAuthPermissionKindType(kind);
	}

	@Override
	public AuthPermissionKindEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String kind = rs.getString(columnIndex);
		return rs.wasNull() ? null : translateToAuthPermissionKindType(kind);
	}

	@Override
	public AuthPermissionKindEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String kind = cs.getString(columnIndex);
		return cs.wasNull() ? null : translateToAuthPermissionKindType(kind);
	}
	
	private AuthPermissionKindEnum translateToAuthPermissionKindType(String kind) {
		AuthPermissionKindEnum result = null;
		for(AuthPermissionKindEnum permissionKind: this.enums) {
			if (permissionKind.getText().equals(kind)) {
				result = permissionKind;
				break;
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("未知枚举" + kind + ", 请核对 " + typeClass.getSimpleName());			
		}
		return result;
	}

}

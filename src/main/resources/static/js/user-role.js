//打开用户角色对话框
//查看、修改、新增用户角色关联关系
function showUserRolesDialog() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (!row) {
		$.messager.alert("提示", "请选择一个待编辑的数据行！");
		return false;
	}
	$("#userRoleDlg").dialog({
		onBeforeOpen : function() {
			var userId = row.userId;
			$("#userRoleDatagrid").datagrid({
				method : "get",
				url : "/users/api/" + userId + "/roles",
				emptyMsg : "该用户尚未分配任何角色！",
				singleSelect : true,
				striped : true,
				fit : true,
				striped : true,
				onBeforeEdit : function(index, row) {
					row.editing = true;
					$(this).datagrid("refreshRow", index);
				},
				onBeginEdit : function(index, row) {
					// alert("BeginEdit");
				},
				onEndEdit : function(index, row, changes) {
					var editingRow = $("#userRoleDatagrid").datagrid("getSelected");
					var ed = $("#userRoleDatagrid").datagrid("getEditor", {
							index : index,
							field : "roleId"
						});
						if (ed != null) {
							console.log("role id: "+ $(ed.target).combobox("getValue"));
							console.log("role name: "+ $(ed.target).combobox("getText"));
							editingRow.roleId = $(ed.target).combobox("getValue");
							editingRow.roleName = $(ed.target).combobox("getText");
						}
				},
				onAfterEdit : function(index, row, changes) {
					row.editing = false;
					$(this).datagrid("refreshRow", index);
				},
				onCancelEdit : function(index, row) {
					row.editing = false;
					$(this).datagrid("refreshRow", index);
				}
			});
		}
	}).dialog("open").dialog("center").dialog("setTitle","关联角色【" + row.userName + "】");
}

// 用于查看用户角色对话框
// 将角色编号转为角色名称显示
function roleIdDisplayFormatter(value, row, index) {
	return row.roleName;
}

// 保存用户角色关联关系
function saveUserRoles() {
	if ($("#userRoleDatagrid").datagrid("getChanges").length <= 0) {
		return;
	}
	var effectedRows = {};
	var insertedRows = $("#userRoleDatagrid").datagrid("getChanges", "inserted");
	var updatedRows = $("#userRoleDatagrid").datagrid("getChanges", "updated");
	var deletedRows = $("#userRoleDatagrid").datagrid("getChanges", "deleted");
	if (insertedRows.length > 0) {
		effectedRows.inserted = insertedRows;
	}
	if (updatedRows.length > 0) {
		effectedRows.updated = updatedRows;
	}
	if (deletedRows.length > 0) {
		effectedRows.deleted = deletedRows;
	}
	var jsonData = JSON.stringify(effectedRows);
	console.log(jsonData);
	var row = $("#userDatagrid").datagrid("getSelected");
	$.ajax({
		url : "/users/api/" + row.userId + "/roles",
		type : "POST",
		data : jsonData,
		contentType : "application/json;charset=UTF-8",
		success : function(data, textStatus) {
			console.log(data);
			console.log(textStatus);
			$("#userRoleDlg").dialog("close");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("status: " + textStatus);
			console.log("error: " + errorThrown);
		}
	});
}

function closeUserRolesDialog() {
	if ($("#userRoleDatagrid").datagrid("getChanges").length > 0) {
		$.messager.confirm("确认", "您对用户角色进行了修改，需要保存吗？", function(r) {
			if (r) {
				saveUserRoles();
			}
		})
	}
	$('#userRoleDlg').dialog('close');
}

// 添加一个可编辑的数据行
function appendUserRole() {
	var myDate = new Date();
	$("#userRoleDatagrid").datagrid("appendRow", {
		startDate : myDate.toLocaleDateString()
	});
	var editIndex = $("#userRoleDatagrid").datagrid("getRows").length - 1;
	$("#userRoleDatagrid").datagrid("selectRow", editIndex);
	$("#userRoleDatagrid").datagrid("beginEdit", editIndex);
}

// 把目标行设为编辑状态
function editRow(target) {
	console.log("编辑第" + getRowIndex(target) + "行")
	$("#userRoleDatagrid").datagrid("beginEdit", getRowIndex(target));
}

// 删除目标行
function deleteRow(target) {
	$.messager.confirm("确认", "确认要移除此角色?", function(r) {
		if (r) {
			console.log("删除第" + getRowIndex(target) + "行")
			$("#userRoleDatagrid").datagrid("deleteRow", getRowIndex(target));
		}
	});
}

// 保存行修改
function saveRow(target) {
	console.log("保存第" + getRowIndex(target) + "行")
	$("#userRoleDatagrid").datagrid("endEdit", getRowIndex(target));
}

// 取消行修改
function cancelRow(target) {
	var editIndex = getRowIndex(target);
	$("#userRoleDatagrid").datagrid("cancelEdit", editIndex);
}

// 保存客户端的所有修改
function accept() {
	$("#userRoleDatagrid").datagrid("acceptChanges");
}

// 放弃客户端的所有修改
function reject() {
	$.messager.confirm("确认", "是要撤消所有的修改吗?", function(r) {
		if (r) {
			$("#userRoleDatagrid").datagrid("rejectChanges");
		}
	});
}
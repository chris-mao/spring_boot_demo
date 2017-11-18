$(document).ready(function() {
	$("#roleDatagrid").datagrid({
		method : "get",
		url : "/roles/rest/list",
		idField : "roleId",
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect : true,
		rownumbers : true,
		striped : true,
		fit : true,
		pagination : true,
		pagePosition : "bottom",
		pageSize : 20,
		pageList : [ 10, 20, 30, 50, 80 ],
		onSelect: function(index, row) {
			console.log("加载角色 " + row.roleName + " 的权限");
			$("#rolePermissionsPanel").panel({"title": "角色【" + row.roleName + "】的权限"});
			$("#permissionTree").tree("expandAll");
		},
		onLoadSuccess: function(data) {
			console.log("角色数据加载完成");
			$("#roleDatagrid").datagrid("selectRow", 0);
		}
	});

	$("#roleName").searchbox({
		searcher : function(value, name) {
			if ($.trim(value).length > 0) {
				$("#roleDatagrid").datagrid("load", {
					searchValue : value
				});
			} else {
				$("#roleDatagrid").datagrid("load", {});
			}
		}
	});
	
	$("#permissionTree").tree({
		onLoadSuccess:function(node, data) {
			console.log("tree data: " + data);
			if (!data) {
//				$("#permissionTree").innerText("该角色尚未分配任何权限！");
			}
		}
	});
});

var post_url;
function newRole() {
	$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新角色");
	$("#userEditForm").form("clear");
	post_url = "/users/rest/save";
}

function editRole() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
				"编辑角色信息");
		$("#userEditForm").form("load", row);
		post_url = "/users/rest/update/" + row.id;
	} else {
		$.messager.alert("提示", "请选择一个待编辑的数据行！");
	}
}

function saveRole() {
	$("#userEditForm").form("submit", {
		url : post_url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			alsert(result);
			var result = eval("(" + result + ")");
			if (result.errorMsg) {
				$.messager.show({
					title : "Error",
					msg : result.errorMsg
				});
			} else {
				$("#userEditDlg").dialog("close"); // close the dialog
				$("#userDatagrid").datagrid("reload"); // reload the user data
			}
		}
	});
}

function deleteRole() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$.messager.confirm("确认", "确要定删除角色 " + row.nickName + " 吗？",
				function(r) {
					if (r) {
						$.get("/users/rest/delete/" + row.id, function(result) {
							if (result.success) {
								$("#userDatagrid").datagrid("reload");
							} else {
								$.messager.show({ // show error message
									title : "Error",
									msg : result.errorMsg
								});
							}
						}, "json");
					}
				});
	} else {
		$.messager.alert("提示", "请选择一个待删除的数据行！");
	}
}

function assignPermissions() {
	$("#userRoleDlg").dialog({
		onBeforeOpen : function() {
			var row = $("#userDatagrid").datagrid("getSelected");
			if (!row) {
				return false;
			}
			var userId = row.userId;
			$("#userRoleDatagrid").datagrid({
				method : "get",
				url : "/users/rest/" + userId + "/roles",
				saveUrl : "/users/rest/save",
				upadteUrl : "/users/rest/save",
				destroyUrl : "/users/rest/save",
				emptyMsg : "该用户尚未分配任何角色！",
				singleSelect : true,
				striped : true,
				fit : true,
				striped:true,
				onBeforeEdit: function(index, row){
					row.editing = true;
					$(this).datagrid("refreshRow", index);
				},
				onBeginEdit: function(index, row) {
					// alert("BeginEdit");
				},
				onEndEdit:function(index, row, changes){
					// alert("EndEdit");
					var ed = $("#userRoleDatagrid").datagrid("getEditor", { index: index, field: "roleName" });
	                if (ed != null) {
	                    var editingRow = $("#userRoleDatagrid").datagrid("getSelected");
	                    editingRow["roleId"] = $(ed.target).combobox("getValue");
	                    editingRow["roleName"] = $(ed.target).combobox("getText");
	                }
				},
				onAfterEdit:function(index, row, changes){
					row.editing = false;
					$(this).datagrid("refreshRow", index);
				},
				onCancelEdit:function(index,row){
					row.editing = false;
					$(this).datagrid("refreshRow", index);
				}
			});
		}
	}).dialog("open").dialog("center");
}

function saveRoleRoles() {
	var insertedRows = $("#userRoleDatagrid").datagrid("getChanges", "inserted");
	var updatedRows = $("#userRoleDatagrid").datagrid("getChanges", "updated");
	var deletedRows = $("#userRoleDatagrid").datagrid("getChanges", "deleted");
	if (insertedRows.length > 0) {
		alert(insertedRows.length+" rows have been inserted!");
		alert(insertedRows[0].roleId);
		alert(insertedRows[0].roleName);
	}
	if (updatedRows.length > 0) {
		alert(updatedRows.length+" rows have been updated!");
	}
	if (deletedRows.length > 0) {
		alert(deletedRows.length+" rows have been deleted!");
	}
	$("#userRoleDlg").dialog("close");
}
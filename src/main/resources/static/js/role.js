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
			$("#permissionTree").tree({
				method:"get",
				url:"roles/rest/" + row.roleId + "/permissions/tree",
				animate:true,
				lines:true,
				checkbox:true,
				onLoadSuccess: function(node, data) {
				    $("#permissionTree").tree("expandAll");
				}
			});
			
		},
		onLoadSuccess: function(data) {
			console.log("角色数据加载完成");
			$("#roleDatagrid").datagrid("selectRow", 0);
		},
		onDblClickRow : function(index, row) {
			editRole();
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
	$("#roleEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新角色");
	$("#roleEditForm").form("clear");
	var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
	switchbuttonObj.switchbutton("check");
	post_url = "/roles/rest/new";
}

function editRole() {
	var row = $("#roleDatagrid").datagrid("getSelected");
	if (row) {
		console.log(row);
		$("#roleEditDlg").dialog("open").dialog("center").dialog("setTitle",
				"编辑角色信息");
		$("#roleEditForm").form("load", row);
		if (row.available) {
			var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
		    switchbuttonObj.switchbutton("check");
		}
		post_url = "/roles/rest/" + row.roleId;
	} else {
		$.messager.alert("提示", "请选择一个待编辑的数据行！");
	}
}

function saveRole() {
	$("#roleEditForm").form("submit", {
		url : post_url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(data, textStatus) {
			console.log(data);
			console.log(textStatus);
			var data = eval('(' + data + ')'); //将字符串转为JSON对象
			if (data.state == 0) {
				$.messager.alert("消息", "数据保存成功！", "info");
				$("#roleEditDlg").dialog("close"); // close the dialog
				$("#roleDatagrid").datagrid("reload"); // reload the user data
			} else {
				$.messager.alert("错误", data.message, "error");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("status: " + textStatus);
			console.log("error: " + errorThrown);
		}
	});
}

function deleteRole() {
	var row = $("#roleDatagrid").datagrid("getSelected");
	if (row) {
		$.messager.confirm("确认", "确要定删除角色 " + row.roleName + " 吗？",
				function(r) {
					if (r) {
						$.ajax({
							url : "/roles/rest/" + row.roleId,
							type : "DELETE",
							success : function(data, textStatus) {
								console.log(data);
								console.log(textStatus);
								if (data.state == 0) {
									$("#roleDatagrid").datagrid("reload");
									// 清空选中的行
									$('#roleDatagrid').datagrid('clearSelections');
								} else {
									$.messager.alert("错误", data.message, "error");
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								console.log("status: " + textStatus);
								console.log("error: " + errorThrown);
							}
						});
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
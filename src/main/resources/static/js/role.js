$(document).ready(function() {
	$("#roleDatagrid").datagrid({
		method : "get",
		url : "/roles/api/list",
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
			getRolePermissions(row);
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
	
	$("#rolePermissionTree").tree({
		onLoadSuccess:function(node, data) {
			console.log("tree data: " + data);
			if (!data) {
//				$("#rolePermissionTree").innerText("该角色尚未分配任何权限！");
			}
		}
	});
});

function getRolePermissions(row) {
	console.log("加载角色 " + row.roleName + " 的权限");
	$("#rolePermissionsPanel").panel({"title": "角色【" + row.roleName + "】的权限"});
	$("#rolePermissionTree").tree({
		method:"get",
		url:"roles/api/" + row.roleId + "/permissions/tree",
		animate:true,
		lines:true,
//		checkbox:true,
		onLoadSuccess: function(node, data) {
		    $("#rolePermissionTree").tree("expandAll");
		}
	});
}

var post_url;
function newRole() {
	$("#roleEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新角色");
	$("#roleEditForm").form("clear");
	var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
	switchbuttonObj.switchbutton("check");
	post_url = "/roles/api/new";
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
		post_url = "/roles/api/" + row.roleId;
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
				$("#roleDatagrid").datagrid("reload"); // reload the role data
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
							url : "/roles/api/" + row.roleId,
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

function showRolesPermissionDialog() {
	var row = $("#roleDatagrid").datagrid("getSelected");
	if (!row) {
		return false;
	}
	$("#rolePermissionDlg").dialog({
		onBeforeOpen : function() {
			var roleId = row.roleId;
			$("#permissionTree").tree({
				method:"get",
				url:"permissions/api/tree",
				animate:true,
				lines:true,
				checkbox:true,
				onLoadSuccess: function(node, data) {
					var permissions = $("#permissionTree").tree("getChildren");
				    var rolePermissions = $("#rolePermissionTree").tree("getChildren");
				    console.log(rolePermissions.length);
				    for (var j = 0; j < permissions.length; j++) {
				    	for(var i = 0; i < rolePermissions.length; i++) {
				    		if (rolePermissions[i].id == permissions[j].id) {
				    			if (true == $("#permissionTree").tree("isLeaf", permissions[j].target)) {
				    			$("#permissionTree").tree("check", permissions[j].target);
				    			}
				    			break;
				    		}
				        }
				    }
					$("#permissionTree").tree("expandAll");
				}
			});
		}
	}).dialog("open").dialog("center").dialog("setTitle","权限分配【" + row.roleName + "】");
}

function saveRolePermissions() {
	var permissions = [];
	var checkedNodes = $("#permissionTree").tree("getChecked", ["checked","indeterminate"]);
	for (var i = 0; i < checkedNodes.length; i++) {
		console.log("Checked Permission: " + checkedNodes[i]);
		permissions.push(checkedNodes[i].id);
	}
	var jsonData = JSON.stringify(permissions);
	console.log("json Data: " + jsonData);
	var row = $("#roleDatagrid").datagrid("getSelected");
	$.ajax({
		url : "/roles/api/" + row.roleId + "/permissions",
		type : "POST",
		data : jsonData,
		contentType : "application/json;charset=UTF-8",
		success : function(data, textStatus) {
			console.log(data);
			console.log(textStatus);
			getRolePermissions(row);
			$("#rolePermissionDlg").dialog("close");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("status: " + textStatus);
			console.log("error: " + errorThrown);
		}
	});
}

function closeRolesPermissionDialog() {
	$('#rolePermissionDlg').dialog('close');
}
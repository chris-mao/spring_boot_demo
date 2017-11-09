$(document).ready(function() {
	$("#userDatagrid").datagrid({
		method : "get",
		url : "/users/rest/list",
		idField : "userId",
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
		onDblClickRow : function(index, row) {
			editUser();
		}
	});

	$("#userName").searchbox({
		searcher : function(value, name) {
			if ($.trim(value).length > 0) {
				$("#userDatagrid").datagrid("load", {
					searchValue : value
				});
			} else {
				$("#userDatagrid").datagrid("load", {});
			}
		}
	});
});

var post_url;
function newUser() {
	$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新用户");
	$("#userEditForm").form("clear");
	post_url = "/users/rest/save";
}

function editUser() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
				"编辑用户信息");
		$("#userEditForm").form("load", row);
		post_url = "/users/rest/update/" + row.id;
	} else {
		$.messager.alert("提示", "请选择一个待编辑的数据行！");
	}
}

$.extend($.fn.validatebox.defaults.rules, {
	confirmPass : {
		validator : function(value, param) {
			var pass = $(param[0]).passwordbox("getValue");
			// alert("confirmation is " + value);
			// alert("your password is " + pass);
			return value == pass;
		},
		message : "两次输入的密码不匹配"
	}
});

function changePsd() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$("#changePasswordDlg").dialog("open").dialog("center");
		$("#changePasswordDlg").form("load", row);
		post_url = "/users/rest/save";
		$("#changePasswordDlg").dialog("open").dialog("center");
	}
}

function saveUser() {
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

function deleteUser() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$.messager.confirm("确认", "确要定删除用户 " + row.nickName + " 吗？",
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

function assignRoles() {
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

function saveUserRoles() {
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

//添加一个可编辑的数据行
function append(){
	var myDate = new Date();			
	$("#userRoleDatagrid").datagrid("appendRow",{startDate: myDate.toLocaleDateString()});
	var editIndex = $("#userRoleDatagrid").datagrid("getRows").length-1;
	$("#userRoleDatagrid").datagrid("selectRow", editIndex);
	$("#userRoleDatagrid").datagrid("beginEdit", editIndex);
}

//获取当前行的索引
function getRowIndex(target){
    var tr = $(target).closest("tr.datagrid-row");
    return parseInt(tr.attr("datagrid-row-index"));
}

//把目标行设为编辑状态
function editRow(target){
    $("#userRoleDatagrid").datagrid("beginEdit", getRowIndex(target));
}

//删除目标行
function deleteRow(target){
    $.messager.confirm("确认","确认要移除此角色?", function(r) {
        if (r){
            $("#userRoleDatagrid").datagrid("deleteRow", getRowIndex(target));
        }
    });
}

//保存修改
function saveRow(target){
    $("#userRoleDatagrid").datagrid("endEdit", getRowIndex(target));
}

//取消修改
function cancelRow(target){
	var editIndex = getRowIndex(target);
   $("#userRoleDatagrid").datagrid("cancelEdit", getRowIndex(target)).datagrid("deleteRow", editIndex);
}

//保存客户端的所有修改
function accept(){
	$("#userRoleDatagrid").datagrid("acceptChanges");
}

//放弃客户端的所有修改
function reject(){
	$.messager.confirm("确认","此操作会撤消当前所做的所有修改，请确认要撤消吗?", function(r){
        if (r){
            $("#userRoleDatagrid").datagrid("rejectChanges");
        }
    });
}

function getChanges(){
	var insertedRows = $("#userRoleDatagrid").datagrid("getChanges", "inserted");
	var updatedRows = $("#userRoleDatagrid").datagrid("getChanges", "updated");
	var deletedRows = $("#userRoleDatagrid").datagrid("getChanges", "deleted");
	if (insertedRows.length > 0) {
		alert(insertedRows.length+" rows have been inserted!");
	}
	if (updatedRows.length > 0) {
		alert(updatedRows.length+" rows have been updated!");
	}
	if (deletedRows.length > 0) {
		alert(deletedRows.length+" rows have been deleted!");
	}
}
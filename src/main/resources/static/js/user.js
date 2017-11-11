$(document).ready(function() {
	//初始货用户表格
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

	//绑定快速查询
	$("#userName").searchbox({
		searcher : function(value, name) {
			if ($.trim(value).length > 0) {
				$("#userDatagrid").datagrid("load", {
					searchValue : value
				});
			} else {
				$("#userDatagrid").datagrid("load", {});
			}
			//清空选中的行
			$('#userDatagrid').datagrid('clearSelections'); 
		}
	});
});

var post_url;

//打开创建新用户对话框
function newUser() {
	$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新用户");
	$("#userEditForm").form("clear");
	post_url = "/users/rest/save";
}

//打开编加用户对话框
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

//检查在修改密码时两次输入的密码是否匹配
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

//打开修改密码对话框
function changePsd() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$("#changePasswordDlg").dialog("open").dialog("center");
		$("#changePasswordDlg").form("load", row);
		post_url = "/users/rest/save";
		$("#changePasswordDlg").dialog("open").dialog("center");
	}
	else {
		$.messager.alert("提示", "请选择一个待编辑的数据行！");
	}
}

//保存用户
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

//删除用户
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

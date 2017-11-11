$(document).ready(function() {
	// 初始货用户表格
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

	// 绑定快速查询
	$("#userSearch").searchbox({
		searcher : function(value, name) {
			if ($.trim(value).length > 0) {
				$("#userDatagrid").datagrid("load", {
					searchValue : value
				});
			} else {
				$("#userDatagrid").datagrid("load", {});
			}
			// 清空选中的行
			$('#userDatagrid').datagrid('clearSelections');
		}
	});
});

var post_url;

// 打开创建新用户对话框
function newUser() {
	$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新用户");
	$("#userEditForm").form("clear");
	$("#userName1").removeAttr("readOnly");
	$("#userName1").removeAttr("disabled");
	post_url = "/users/rest/new";
}

// 打开编加用户对话框
function editUser() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$("#userEditDlg").dialog("open").dialog("center").dialog("setTitle",
				"编辑用户");
		$("#userEditForm").form("load", row);
		$("#userName1").attr("readOnly", "readOnly");
		$("#userName1").attr("disabled","disabled")
		post_url = "/users/rest/" + row.userId;
	} else {
		$.messager.alert("提示", "没有选中的数据行");
	}
}

// 检查在修改密码时两次输入的密码是否匹配
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

// 打开修改密码对话框
function changePsd() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$("#changePasswordDlg").dialog("open").dialog("center");
		$("#changePasswordDlg").form("load", row);
		post_url = "/users/rest/save";
		$("#changePasswordDlg").dialog("open").dialog("center");
	} else {
		$.messager.alert("提示", "没有选中的数据行");
	}
}

// 保存用户
function saveUser() {
	$("#userEditForm").form("submit", {
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
				$("#userEditDlg").dialog("close"); // close the dialog
				$("#userDatagrid").datagrid("reload"); // reload the user data
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

// 删除用户
function deleteUser() {
	var row = $("#userDatagrid").datagrid("getSelected");
	if (row) {
		$.messager.confirm("确认", "删除用户【" + row.nickName + "】？", function(r) {
			if (r) {
				$.ajax({
					url : "/users/rest/" + row.userId,
					type : "DELETE",
					success : function(data, textStatus) {
						console.log(data);
						console.log(textStatus);
						if (data.state == 0) {
							$("#userDatagrid").datagrid("reload");
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
		$.messager.alert("提示", "没有选中的数据行");
	}
}

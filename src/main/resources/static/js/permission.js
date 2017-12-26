$(document).ready(function() {
	$("#permissionTreegrid").treegrid({
		method : "get",
		url : "permissions/api/list",
		idField : "permissionId",
		treeField : "permissionText",
		singleSelect : true,
		rownumbers : true,
		striped : true,
		fit : true,
		lines : true,
		animate : true,
		pagination : true,
		pagePosition : "bottom",
		pageSize : 20,
		pageList : [ 10, 20, 30, 50, 80 ],
		onDblClickRow : function(index, row) {
			editPermission();
		}
	});

	$("#permissionName").searchbox({
		searcher : function(value, name) {
			if ($.trim(value).length > 0) {
				$("#permissionTreegrid").treegrid("load", {
					searchValue : value
				});
			} else {
				$("#permissionTreegrid").treegrid("load", {});
			}
		}
	});
});

var post_url;

// 打开创建新权限对话框
function newPermission() {
	post_url = "/permissions/api/new";
	$("#parentId").combotree("reload");
	$("#permissionEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新权限");
	$("#permissionEditForm").form("clear");
	var row = $("#permissionTreegrid").treegrid("getSelected");
	if (row) {
		$("#permissionEditForm").form("load", {"parentId":row.permissionId});
	}
	var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
	switchbuttonObj.switchbutton("check");
}

// 打开编加权限对话框
function editPermission() {
	var row = $("#permissionTreegrid").treegrid("getSelected");
	if (row) {
		console.log(row);
		post_url = "/permissions/api/" + row.permissionId;
		$("#permissionEditDlg").dialog("open").dialog("center").dialog("setTitle",
				"编辑权限");
		$("#permissionEditForm").form("load", row);
		if (row.available) {
			var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
		    switchbuttonObj.switchbutton("check");
		}
	} else {
		$.messager.alert("提示", "没有选中的数据行");
	}
}

//保存权限
function savePermission() {
	console.log(post_url);
	$("#permissionEditForm").form("submit", {
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
				$("#permissionEditDlg").dialog("close"); // close the dialog
				$("#permissionTreegrid").treegrid("reload"); // reload the permission data
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

// 删除权限
function deletePermission() {
	var row = $("#permissionTreegrid").treegrid("getSelected");
	if (row) {
		$.messager.confirm("确认", "删除权限【" + row.permissionText + "】？", function(r) {
			if (r) {
				$.ajax({
					url : "/permissions/api/" + row.permissionId,
					type : "DELETE",
					success : function(data, textStatus) {
						console.log(data);
						console.log(textStatus);
						if (data.state == 0) {
							$("#permissionTreegrid").treegrid("reload");
							// 清空选中的行
							$('#permissionTreegrid').treegrid('clearSelections');
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
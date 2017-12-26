$(document).ready(function() {
	$("#departmentTreegrid").treegrid({
		method : "get",
		url : "departments/api/list",
		idField : "departmentId",
		treeField : "departmentName",
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
			editDepartment();
		}
	});

	$("#departmentName").searchbox({
		searcher : function(value, name) {
			if ($.trim(value).length > 0) {
				$("#departmentTreegrid").treegrid("load", {
					searchValue : value
				});
			} else {
				$("#departmentTreegrid").treegrid("load", {});
			}
		}
	});
});

var post_url;

// 打开创建新部门对话框
function newDepartment() {
	post_url = "/departments/api/new";
	$("#parentId").combotree("reload");
	$("#departmentEditDlg").dialog("open").dialog("center").dialog("setTitle",
			"创建新部门");
	$("#departmentEditForm").form("clear");
	var row = $("#departmentTreegrid").treegrid("getSelected");
	if (row) {
		$("#departmentEditForm").form("load", {"parentId":row.departmentId});
	}
	var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
	switchbuttonObj.switchbutton("check");
}

// 打开编加部门对话框
function editDepartment() {
	var row = $("#departmentTreegrid").treegrid("getSelected");
	if (row) {
		console.log(row);
		post_url = "/departments/api/" + row.departmentId;
		$("#departmentEditDlg").dialog("open").dialog("center").dialog("setTitle",
				"编辑部门");
		$("#departmentEditForm").form("load", row);
		if (row.available) {
			var switchbuttonObj = $(".easyui-switchbutton[switchbuttonName='available']");
		    switchbuttonObj.switchbutton("check");
		}
	} else {
		$.messager.alert("提示", "没有选中的数据行");
	}
}

//保存部门
function saveDepartment() {
	console.log(post_url);
	$("#departmentEditForm").form("submit", {
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
				$("#departmentEditDlg").dialog("close"); // close the dialog
				$("#departmentTreegrid").treegrid("reload"); // reload the department data
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

// 删除部门
function deleteDepartment() {
	var row = $("#departmentTreegrid").treegrid("getSelected");
	if (row) {
		$.messager.confirm("确认", "删除部门【" + row.departmentName + "】？", function(r) {
			if (r) {
				$.ajax({
					url : "/departments/api/" + row.departmentId,
					type : "DELETE",
					success : function(data, textStatus) {
						console.log(data);
						console.log(textStatus);
						if (data.state == 0) {
							$("#departmentTreegrid").treegrid("reload");
							// 清空选中的行
							$('#departmentTreegrid').treegrid('clearSelections');
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
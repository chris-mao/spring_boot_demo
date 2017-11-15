$(document).ready(function() {
	$("#permissionTreegrid").treegrid({
		method : "get",
		url : "permissions/rest/list",
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
		pageList : [ 10, 20, 30, 50, 80 ]
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
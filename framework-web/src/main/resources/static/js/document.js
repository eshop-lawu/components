$(function(){
	var paths = {};
	$.ajax({
		url: "v2/api-docs?group=API",
		type: "GET",
		cache: true,
		async: false,
		success: function(data, statusText, xhr) {
			if (statusText == "success") {
				paths = data["paths"];
			}
		}
	})
	
	$('#contentTable').bootstrapTable({
	    columns: [{
	        field: 'date',
	        title: '发布日期',
	        sortable: true
	    }, {
	        field: 'name',
	        title: '接口名称',
	        sortable: true
	    }, {
	        field: 'apiName',
	        title: '接口组别',
	        sortable: true,
	        formatter: function(value, row, index) {
	        	var group = row.apiName.replace("[", "").replace("]", "");
	        	var path = "swagger-ui.html#!/" + group;
	        	return "<a href=\"" + path + "\" target=\"_blank\">" + value + "</a>";
	        }
	    }, {
	        field: 'path',
	        title: '路径',
	        sortable: true,
	        formatter: function(value, row, index) {
	        	var group = row.apiName.replace("[", "").replace("]", "");
	        	value = value.replace("[", "").replace("]", "");
	        	var path_obj = paths[value.indexOf("/") == 0 ? value : ("/" + value)];
	        	if (path_obj != undefined) {
		        	var operationId = path_obj[row.httpMethod.toLowerCase()].operationId;
		        	var path = "swagger-ui.html#!/" + group + "/" + operationId;
		        	return "<a " + (row.isDeprecated ? "style=\"text-decoration: line-through;\"" : "") +  " href=\"" + path + "\" target=\"_blank\">" + value + "</a>";
	        	} else {
	        		return "<span " + (row.isDeprecated ? "style=\"text-decoration: line-through;\"" : "") + ">" + value + "</span>";
	        	}
	        }
	    }, {
	        field: 'httpMethod',
	        title: '请求方法',
	        sortable: true
	    }, {
	        field: 'notes',
	        title: '接口介绍',
	        sortable: true
	    }, {
	        field: 'reviewer',
	        title: '审核人',
	        sortable: true
	    }],
	    url: "document",
	    queryParams: function(params){
	    	$.each($('#searchForm').serializeArray(), function(i, item) {
				params[item.name] = item.value;
			})
			return params;
	    },
	    cache: true,
	    pagination: true,
	    pageNumber: "1",
	    pageSize: "10",
	    search: true,
	    showRefresh: true,
	    showExport: true,
	    exportDataType: "basic"
	});
	
	$("#search").click(function(){
		$('#contentTable').bootstrapTable('refreshOptions',{currentPage:1});
		$('#contentTable').bootstrapTable('removeAll');
		$('#contentTable').bootstrapTable('refresh');
	});

	//------------------------------------------------------------------------------------------------------------------

    $('#contentTable1').bootstrapTable({
        columns: [{
            field: 'code',
            title: '返回码',
            sortable: true
        }, {
            field: 'desc',
            title: '描述',
            sortable: true
        }],
        url: "resultCode",
        cache: true,
        pagination: true,
        pageNumber: "1",
        pageSize: "10",
        search: true,
        showRefresh: true,
        showExport: true,
        exportDataType: "basic"
    });

});
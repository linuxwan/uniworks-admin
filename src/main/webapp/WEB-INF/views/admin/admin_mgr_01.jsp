<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.companyMgr"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.popupwindow.js"></script>

    <script type="text/javascript">	
    var mode = "ADD";
    
    $(function(){
    	$('#adminList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());    	
    });
    
    /**
    * 팝업창에서 호출하기 위한 함수(refresh)
    */
    function reload() {
    	$('#adminList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    }
    
    /**
     * Grid에 가져올 관리자 목록을 호출
    */
    function getData() {  
    	getAjaxData();
    	return rows;    	
    }
    
    /*
     * 등록된 관리자 목록을 Ajax로 호출
    */
    function getAjaxData() {
    	rows = [];
    	
    	var url="<c:out value="${contextPath}"/>/rest/admin";
    	$.ajaxSetup({async: false});
    	$.getJSON(url, function (data, status){
    		if (status == 'success') {    			
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					adminId: entry["adminId"],
    					empNo: entry["empNo"],
    					pswd: entry["pswd"],
    					pswdChngDate: entry["pswdChngDate"],
    					useStDate: entry["useStDate"],
    					useFinDate: entry["useFinDate"],
    					adminType: entry["adminType"]
    				});
    			});
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
                            
    function pagerFilter(data){    	
		if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
			data = {
				total: data.length,
				rows: data
			}
		}
		
		var dg = $(this);
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage:function(pageNum, pageSize){
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh',{
					pageNumber:pageNum,
					pageSize:pageSize
				});
				dg.datagrid('loadData',data);
			},
			onRefresh:function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh',{
					pageNumber:pageNum,
					pageSize:pageSize
				});
				$("#adminList").datagrid("load", {});
				$('#adminList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
			}
		});
		if (!data.originalRows){
			data.originalRows = (data.rows);
		}
		var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = (data.originalRows.slice(start, end));
		
		return data;
	}
    </script>
</head>
<body>
	<input type="hidden" id="mode" name="mode" value=""/>		
    <table id="adminList" class="easyui-datagrid" style="width:100%;height:650px" 		        
       		title="<spring:message code="resc.label.adminList"/>" 
       		data-options="iconCls:'icon-edit', rownumbers:true, singleSelect:true, toolbar:'#tb', pagination:true, autoRowHeight:false, pageSize:20">
        <thead>
            <tr>
                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
                <th data-options="field:'adminId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.adminID"/></th>
                <th data-options="field:'empNo',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.empNo"/></th>
                <th data-options="field:'pswd',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.pswd"/></th>
                <th data-options="field:'pswdChngDate',halign:'center',align:'center',width:'15%',formatter:formatDate"><spring:message code="resc.label.pswdChngDate"/></th>
                <th data-options="field:'useStDate',halign:'center',align:'center',width:'15%', formatter:formatDate"><spring:message code="resc.label.useStDate"/></th>
                <th data-options="field:'useFinDate',halign:'center',align:'center',width:'15%', formatter:formatDate"><spring:message code="resc.label.useFinDate"/></th>
                <th data-options="field:'adminType',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.adminType"/></th>
            </tr>
        </thead>
    </table>    	
    <div id="tb" style="height:auto">    
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="resc.btn.add"/></a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="resc.btn.delete"/></a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modify()"><spring:message code="resc.btn.modify"/></a>        
    </div>	
    <script type="text/javascript">
    function append() {    	
    	var apprLevel = $("#apprLevel").val();
		var url = "<c:out value="${contextPath}"/>/admin/adminAddForm";
		$.popupWindow(url, { height: 270, width: 700 });		    	
    }
    </script>	    
</body>
</html>
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
	var url = "";
    
    $(function(){    	
    	var coId = $("#selCoId").combobox('getValue');
    	url = "<c:out value="${contextPath}"/>/rest/role/coId/" + coId;
    	
    	$("#roleList").datagrid({
    		onClickRow: function(rowIndex, rowData) {
    			var coId = $("#selCoId").combobox('getValue');
    			var userListByRolUrl = "<c:out value="${contextPath}"/>/rest/userListByRole/coId/" + coId + "/role/" + rowData.role;
    			$('#roleUserList').datagrid('loadData', getUserListByRole(userListByRolUrl));    			
    		}
    	});
    	
    	$('#roleList').datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 컨텐츠 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				
				url = "<c:out value="${contextPath}"/>/rest/role/coId/" + coId;				
				$('#roleList').datagrid('loadData', getData());
    		}
    	});     	    	    	   
    });
    
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
     	$('#roleList').datagrid('loadData', getData());
     }
    
    /*
     * Role 목록 정보를 가져온다.
    */
    function getData() {
    	getAjaxData(url);
    	return rows; 
    }
    
    /*
     * 등록된 컨텐츠 목록을 Ajax로 호출
    */
    function getAjaxData(url) {
    	rows = [];
   	    	
    	$.ajaxSetup({async: false});    	 
    	$.getJSON(url, function (data, status){
    		if (status == 'success') {    			 
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					role: entry["role"],
    					roleName: entry["roleName"],
    					useIndc: entry["useIndc"],
    					roleDetl: entry["roleDetl"]
    				});
    			});
    			
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    
    function getUserListByRole(userListByRolUrl) {
    	getAjaxUserListByRole(userListByRolUrl);
    	return rows; 
    }
    
    function getAjaxUserListByRole(userListByRolUrl) {
    	rows = [];
	    	
    	$.ajaxSetup({async: false});    	 
    	$.getJSON(userListByRolUrl, function (data, status){
    		if (status == 'success') {    			 
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					userId : entry["userId"],
    					empName : entry["empName"],
    					deptDesc: entry["deptDesc"],
    					duthDesc: entry["duthDesc"],
    					pstnDesc: entry["pstnDesc"],
    					role: entry["role"],
    					useIndc: entry["useIndc"]
    				});
    			});
    			
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    </script>
</head>
<body>
	<form id="roleUserManagerForm">
	<table style="width:100%">
		<tr>
			<td colspan="2" style="width:100%">
				<div class="easyui-panel" title="" style="width:100%;max-width:100%;padding:5px 5px;">   
			    <select class="easyui-combobox" id="selCoId" name="coId" style="width:25%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:60">
			    	<c:forEach items="${coList}" var="opt" varStatus="st">
		            <option value="${opt.coId}" <c:if test="${opt.coId == userSession.coId}">selected="selected"</c:if> >${opt.coId}: ${opt.coName}</option>
		            </c:forEach>		            	
		        </select>
		        </div>
			</td>
		</tr>
		<tr>
			<td style="width:50%">				
       			<table id="roleList" class="easyui-datagrid" style="width:100%;height:590px;"		        
			       		title="<spring:message code="resc.label.roleList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,pagination:false,autoRowHeight:false">
			        <thead>
			        	<tr>
			           		<th data-options="field:'coId',width:'10%',halign:'center',align:'center'"><spring:message code="resc.label.coId"/></th>
			        		<th data-options="field:'role',width:'15%',halign:'center',align:'left'"><spring:message code="resc.label.role"/></th>
			        		<th data-options="field:'roleName',width:'25%',halign:'center',align:'left'"><spring:message code="resc.label.roleName"/></th>			        		
			        		<th data-options="field:'roleDetl',width:'40%',halign:'center',align:'left'"><spring:message code="resc.label.roleDetl"/></th>
			        		<th data-options="field:'useIndc',width:'10%',halign:'center',align:'center'"><spring:message code="resc.label.useIndc"/></th>			        		
			        	</tr>  
			        </thead>
			    </table>    				    	
			</td>
			<td style="width:50%">
				<table id="roleUserList" class="easyui-datagrid" style="width:100%;height:590px"		        
			       		title="<spring:message code="resc.label.roleUserList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,toolbar:'#subCodeTb',pagination:false,autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'userId',halign:'center',align:'left',width:'13%'"><spring:message code="resc.label.userId"/></th>
			                <th data-options="field:'empName',halign:'center',align:'left',width:'12%'"><spring:message code="resc.label.empName"/></th>
			                <th data-options="field:'deptDesc',halign:'center',align:'left',width:'20%'"><spring:message code="resc.label.deptDesc"/></th>
			                <th data-options="field:'duthDesc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.dutyDesc"/></th>
			                <th data-options="field:'pstnDesc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.pstnDesc"/></th>
			                <th data-options="field:'role',halign:'center',align:'left',width:'15%'"><spring:message code="resc.label.role"/></th>
			                <th data-options="field:'useIndc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.useIndc"/></th>			                			                
			            </tr>
			        </thead>
			    </table>    	
			    <div id="subCodeTb" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendSubCode()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitSubCode()"><spring:message code="resc.btn.delete"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifySubCode()"><spring:message code="resc.btn.modify"/></a>        
			    </div> 			    
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
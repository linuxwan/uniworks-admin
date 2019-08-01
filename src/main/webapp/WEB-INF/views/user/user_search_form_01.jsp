<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="resc.label.userSearch"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.serializeObject.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.popupwindow.js"></script>
	<script type="text/javascript">
	$(function(){    	
		$("#searchKind").combobox('select', '<spring:message code="resc.label.empName"/>');
		//검색버튼 클릭
		$('#btnSearchUser').bind('click', function(){
			console.log('aaaaaa');
		});
		//선택버튼 클릭
		$('#btnSelect').bind('click', function(){
			console.log('bbbb');
		});
    });
	</script>
</head>
<body>	
	<div class="easyui-panel" title="<spring:message code="resc.label.userSearch"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="roleAddForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
	<div style="width:100%">
	<table style="width:100%">
		<tr>
			<td colspan="2" style="width:100%">
				<div class="easyui-panel" title="" style="width:100%;max-width:100%;padding:5px 5px;">   
			    <select class="easyui-combobox" id="searchKind" name="searchKind" style="width:20%;" data-options="panelHeight:'auto'">
			    	<c:forEach items="${searchTypeList}" var="searchType" varStatus="st">
		            <option value="${searchType.subCode}" <c:if test="${searchType.subCode == searchType}">selected="selected"</c:if> >${searchType.rescKeyValue}</option>
		            </c:forEach>		            	
		        </select>
		        <input class="easyui-textbox" id="coId" name="coId" style="width:40%" value="${coId}" data-options="label:'<spring:message code="resc.label.searchWord"/>:',readonly:true,required:true,labelWidth:50">
		        <a href="javascript:void(0)" id="btnSearchUser" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.search"/></a>
		        </div>
			</td>
		</tr>		
		<tr>
			<td colspan="2" style="width:100%">
			<div>
	      		<table id="roleList" class="easyui-datagrid" style="width:100%;height:490px;"		        
			       		title="<spring:message code="resc.label.userList"/>" 
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
		    </div>
		    </td>
		</tr>
		<tr>
			<td colspan="2" style="width:100%">
				<div style="text-align:center;padding:5px 0">
	        		<a href="javascript:void(0)" id="btnSelect" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.btn.select"/></a>			            
	        		<a href="javascript:void(0)" id="btnClose" class="easyui-linkbutton" onclick="window.close()" style="width:80px"><spring:message code="resc.btn.close"/></a>
	    		</div> 
			</td>
		</tr>
	</table>    			
	</div>	
	</form>
	</div>
</body>
</html>	
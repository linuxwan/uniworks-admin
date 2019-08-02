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
	var url = "";
	var userId = "${userId}";
	var userName = "${userName}";
	
	$(function(){    	
		$("#searchKind").combobox('select', 'NAME');
		//검색버튼 클릭
		$('#btnSearchUser').bind('click', function(){	
			var searchWord = $("#searchWord").textbox('getValue');
			if (searchWord == "" || searchWord.length < 1) {
				var msg = '<spring:message code="resc.msg.enterSearchQuery"/>';
	    		var title = '<spring:message code="resc.label.confirm"/>';		    			
	    		alertMsg(title, msg);	
	    		return;
			}
			$('#userList').datagrid('loadData', getData());
		});				
		
		//datagrid 더블 클릭 시
		$('#userList').datagrid({
			onDblClickRow : function() {
				selectUser();
			}
		});
		
		//선택버튼 클릭
		$('#btnSelect').bind('click', function(){
			selectUser();
		});
    });
	
	function selectUser() {
		var rowData = $("#userList").datagrid('getSelected');
		
		if (rowData == null) {
			var msg = '<spring:message code="resc.msg.noSelectUser"/>';
    		var title = '<spring:message code="resc.label.confirm"/>';		    			
    		alertMsg(title, msg);	
    		return;
		} else {
			//Opener에서 object 명칭을 지정하지 않았을 때와 지정했을 때 처리
			if ((userId == "" || userId.length < 1) && (userName == "" || userName.length < 1)) {
				window.opener.$("#userId").val(rowData.userId);
				window.opener.$("#userName").textbox('setValue', rowData.empName);
				window.close();
			} else {
				window.opener.$("#" + userId).val(rowData.userId);
				window.opener.$("#" + userName).textbox('setValue', rowData.empName);
				window.close();
			}			
		}
	}
	
	/*
     * Role 목록 정보를 가져온다.
    */
    function getData() {
		var coId = '${userSession.coId}';
    	var searchKind = $("#searchKind").combobox('getValue');
		var searchWord = $("#searchWord").textbox('getValue');
		url = "<c:out value="${contextPath}"/>/rest/user/search/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord;
		
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
    					userId: entry["userId"],
    					empName: entry["empName"],
    					deptDesc: entry["deptDesc"],
    					dutyDesc: entry["dutyDesc"],
    					pstnDesc: entry["pstnDesc"]
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
		        <input class="easyui-textbox" id="searchWord" name="searchWord" style="width:40%" value="${coId}" data-options="label:'<spring:message code="resc.label.searchWord"/>:',required:true,labelWidth:50">
		        <a href="javascript:void(0)" id="btnSearchUser" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.search"/></a>
		        </div>
			</td>
		</tr>		
		<tr>
			<td colspan="2" style="width:100%">
			<div>
	      		<table id="userList" class="easyui-datagrid" style="width:100%;height:340px;"		        
			       		title="<spring:message code="resc.label.userList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,pagination:false,autoRowHeight:false">
			        <thead>
			        	<tr>			           					        		
			        		<th data-options="field:'empName',width:'20%',halign:'center',align:'left'"><spring:message code="resc.label.empName"/></th>
			        		<th data-options="field:'userId',width:1,hidden:true"><spring:message code="resc.label.userId"/></th>
			        		<th data-options="field:'deptDesc',width:'40%',halign:'center',align:'left'"><spring:message code="resc.label.dept"/></th>
			        		<th data-options="field:'dutyDesc',width:'20%',halign:'center',align:'left'"><spring:message code="resc.label.dutyDesc"/></th>			        		
			        		<th data-options="field:'pstnDesc',width:'20%',halign:'center',align:'left'"><spring:message code="resc.label.pstnDesc"/></th>			        					        	
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
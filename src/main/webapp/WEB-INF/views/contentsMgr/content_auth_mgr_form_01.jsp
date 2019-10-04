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
	var cntnId = "";
    
    $(function(){    	
    	var coId = $("#selCoId").combobox('getValue');    	
    	url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/cntnName/searchWord/0/orderBy/cntnId";	
    	
    	$("#contentList").datagrid({
    		onClickRow: function(rowIndex, rowData) {
    			var coId = $("#selCoId").combobox('getValue');
    			var contentAuthList = "<c:out value="${contextPath}"/>/rest/contentAuth/coId/" + coId + "/cntnId/" + rowData.cntnId;
    			cntnId = rowData.cntnId;
    			
    			$('#contentAuthList').datagrid('loadData', getContentAuthList(contentAuthList));    			
    		}
    	});
    	
    	$('#contentList').datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 컨텐츠 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				
				url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/cntnName/searchWord/0/orderBy/cntnId";	
				$('#contentList').datagrid('loadData', getData());
    		}
    	});     	    	    	   
    });
            
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
    	var coId = $("#selCoId").combobox('getValue');
    	var contentAuthList = "<c:out value="${contextPath}"/>/rest/contentAuth/coId/" + coId + "/cntnId/" + cntnId;
     	
     	$('#contentAuthList').datagrid('loadData', getContentAuthList(contentAuthList));
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
    					cntnId: entry["cntnId"],
    					cntnName: entry["cntnName"]
    				});
    			});
    			
    			$('#contentAuthList').datagrid('loadData', []);
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    
    function getContentAuthList(contentAuthList) {
    	getAjaxContentAuthList(contentAuthList);
    	return rows; 
    }
    
    function getAjaxContentAuthList(contentAuthList) {
    	rows = [];
	    	
    	$.ajaxSetup({async: false});    	 
    	$.getJSON(contentAuthList, function (data, status){
    		if (status == 'success') {    			 
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					cntnId : entry["cntnId"],
    					useAuthType : entry["useAuthType"],
    					useAuthTypeDesc: entry["useAuthTypeDesc"],
    					useAuthGrpCode: entry["useAuthGrpCode"],
    					useAuthGrpDesc: entry["useAuthGrpDesc"],
    					crtAuth: entry["crtAuth"],
    					rdAuth: entry["rdAuth"],
    					updtAuth: entry["updtAuth"],
    					delAuth: entry["delAuth"],
    					prntAuth: entry["prntAuth"],
    					upldAuth: entry["upldAuth"],
    					dnldAuth: entry["dnldAuth"]
    				});
    			});
    			
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    
    function appendContentAuth() {
    	if (cntnId == "" || cntnId == null) {
    		var msg = '<spring:message code="resc.msg.noSelectContentId"/>';
    		var title = '<spring:message code="resc.label.confirm"/>';		    			
    		alertMsg(title, msg);			
    		return;
    	}
    	var coId = $("#selCoId").combobox('getValue');					    	
		url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentRightsAddForm?coId=" + coId + "&cntnId=" + cntnId;		
		var formHeight = 250;
		
		$.popupWindow(url, { name: 'useAuthAddForm', height: formHeight, width: 800 });
		
    }
    
    function modifyContentAuth() {    	
    	var coId = $("#selCoId").combobox('getValue');
    	var rowData = $("#contentAuthList").datagrid('getSelected');	
    	if (rowData == null) {
    		var title = '<spring:message code="resc.label.confirm"/>';
    		var msg = '<spring:message code="resc.msg.noSelectAuth"/>';
    		alertMsg(title, msg);
			return;
    	}
    	
		url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentRightsModifyForm?coId=" + coId + "&cntnId=" + rowData.cntnId + "&useAuthType=" + rowData.useAuthType + "&useAuthGrpCode=" + rowData.useAuthGrpCode;		
		var formHeight = 250;
		
		$.popupWindow(url, { name: 'useAuthModifyForm', height: formHeight, width: 800 });		    
    }
    
    function removeitContentAuth() {
    	var coId = $("#selCoId").combobox('getValue');	
    	var rowData = $("#contentAuthList").datagrid('getSelected');	
    	
    	if (rowData == null) {
    		var title = '<spring:message code="resc.label.confirm"/>';
    		var msg = '<spring:message code="resc.msg.noSelectAuth"/>';
    		alertMsg(title, msg);
			return;
    	}
    	
    	msg = '<spring:message code="resc.msg.confirmDel"/>';    		
		$.messager.confirm(title, msg, function(r) {
			if (r) {
				deleteContentAuth();
			}
		});
    }
            
    function deleteContentAuth() {
    	var coId = $("#selCoId").combobox('getValue');	
    	var rowData = $("#contentAuthList").datagrid('getSelected');			    				    	
    
		var strUrl = "<c:out value="${contextPath}"/>/rest/contentAuth/delete/cntnId/" + cntnId + "/useAuthType/" + rowData.useAuthType + "/useAuthGrpCode/" + rowData.useAuthGrpCode;
    	
    	$.ajax({
			type: 'DELETE',
			url: strUrl,						 				
			dataType: 'json',						
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
		        xhr.setRequestHeader("Content-Type", "application/json");
				//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},  				
			success : function(msg) {
				var title = '<spring:message code="resc.label.confirm"/>';		    			
				$.messager.alert(title, msg, "info",  function(){
					reload();
				});						
			},
			error : function(xhr, status, error) {
				console.log("error: " + status);
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
			    <select class="easyui-combobox" id="selCoId" name="coId" style="width:20%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:60">
			    	<c:forEach items="${coList}" var="opt" varStatus="st">
		            <option value="${opt.coId}" <c:if test="${opt.coId == userSession.coId}">selected="selected"</c:if> >${opt.coId}: ${opt.coName}</option>
		            </c:forEach>		            	
		        </select>
		        </div>
			</td>
		</tr>
		<tr>
			<td style="width:25%;">	
			<div>
       			<table id="contentList" class="easyui-datagrid" style="width:100%;height:580px;"		        
			       		title="<spring:message code="resc.label.cntnList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,pagination:false,autoRowHeight:false">
			        <thead>
			        	<tr>
			           		<th data-options="field:'coId',width:'20%',halign:'center',align:'center'"><spring:message code="resc.label.coId"/></th>
			        		<th data-options="field:'cntnId',width:'35%',halign:'center',align:'left'"><spring:message code="resc.label.cntnId"/></th>
			        		<th data-options="field:'cntnName',width:'45%',halign:'center',align:'left'"><spring:message code="resc.label.cntnName"/></th>			        					        					        		
			        	</tr>  
			        </thead>
			    </table>
			</div>    				    	
			</td>
			<td style="width:75%;">	
			<div>		
				<table id="contentAuthList" class="easyui-datagrid" style="width:100%;height:580px"		        
			       		title="<spring:message code="resc.label.contentAuthList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,toolbar:'#subCodeTb',pagination:false,autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'cntnId',halign:'center',align:'left',width:'12%'"><spring:message code="resc.label.cntnId"/></th>
			                <th data-options="field:'useAuthType',halign:'center',align:'left',width:1,hidden:true"><spring:message code="resc.label.useAuthType"/></th>
			                <th data-options="field:'useAuthTypeDesc',halign:'center',align:'left',width:'15%'"><spring:message code="resc.label.useAuthType"/></th>
			                <th data-options="field:'useAuthGrpCode',halign:'center',align:'center',width:1,hidden:true"><spring:message code="resc.label.useAuthGrpCode"/></th>
			                <th data-options="field:'useAuthGrpDesc',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.useAuthGrpCode"/></th>
			                <th data-options="field:'crtAuth',halign:'center',align:'left',width:'7%'"><spring:message code="resc.label.crt"/></th>
			                <th data-options="field:'rdAuth',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.read"/></th>			                			                
			                <th data-options="field:'updtAuth',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.updt"/></th>
			                <th data-options="field:'delAuth',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.del"/></th>
			                <th data-options="field:'prntAuth',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.prnt"/></th>
			                <th data-options="field:'upldAuth',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.upload"/></th>
			                <th data-options="field:'dnldAuth',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.download"/></th>
			            </tr>
			        </thead>
			    </table> 
			</div>   	
			    <div id="subCodeTb" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendContentAuth()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyContentAuth()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitContentAuth()"><spring:message code="resc.btn.delete"/></a>			                
			    </div>						   
			</td>
		</tr>
	</table>	
	</div>	
	</form>
</body>
</html>
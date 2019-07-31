<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.menu.contentsMgr"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.popupwindow.js"></script>

    <script type="text/javascript">
    var strMasterId = "";
    
    $(function(){
    	$("#masterList").datagrid({
    		onClickRow: function() {
    			var rowData = $("#masterList").datagrid('getSelected');
    			
    			strMasterId = rowData.masterId;    				
    		}
    	});
    	
    	$('#btnSelect').bind('click', function(){
    		var typeCodeName = '${typeCodeName}';
    		window.opener.$('#' + typeCodeName).textbox('setValue', strMasterId);
    		window.close();
    	});
    });
        
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function masterCodeReload() {
     	$('#masterList').datagrid('reload');
     }        
    </script>
</head>
<body>
	<input type="hidden" id="mode" name="mode" value=""/>			
	<table style="width:100%">		
		<tr>
			<td style="width:50%">				
       			<table id="masterList" class="easyui-datagrid" style="width:100%;height:390px;"		        
			       		title="<spring:message code="resc.label.masterList"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false,url:'<c:out value="${contextPath}"/>/rest/contents/coId/${coId}/cntnType/${cntnType}', method:'get', toolbar:'#masterCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'20%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'masterId',halign:'center',align:'center',width:'30%'"><spring:message code="resc.label.masterId"/></th>			                
			                <th data-options="field:'masterName',halign:'center',align:'left',width:'50%'"><spring:message code="resc.label.masterName"/></th>
			            </tr>
			        </thead>
			    </table>    				   
			</td>
		</tr>		
	</table>
	<div style="text-align:center;padding:5px 0">
		<a href="javascript:void(0)" id="btnSelect" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.btn.select"/></a>			            
	    <a href="javascript:void(0)" id="btnClose" class="easyui-linkbutton" onclick="window.close()" style="width:80px"><spring:message code="resc.btn.close"/></a>
	</div> 	
</body>
</html>
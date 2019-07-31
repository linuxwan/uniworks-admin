<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.menu.codeMgr"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.popupwindow.js"></script>

    <script type="text/javascript">
    var strMajCode = "";
    
    $(function(){
    	$("#masterCodeList").datagrid({
    		onClickRow: function() {
    			var rowData = $("#masterCodeList").datagrid('getSelected');
    			
				strMajCode = rowData.majCode;    				
    		}
    	});
    	
    	$('#btnSelect').bind('click', function(){
    		var typeCodeName = '${typeCodeName}';
    		window.opener.$('#' + typeCodeName).textbox('setValue', strMajCode);
    		window.close();
    	});
    });
        
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function masterCodeReload() {
     	$('#masterCodeList').datagrid('reload');
     }        
    </script>
</head>
<body>
	<input type="hidden" id="mode" name="mode" value=""/>			
	<table style="width:100%">		
		<tr>
			<td style="width:50%">				
       			<table id="masterCodeList" class="easyui-datagrid" style="width:100%;height:580px;"		        
			       		title="<spring:message code="resc.label.mainCodeList"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false,url:'<c:out value="${contextPath}"/>/rest/mastercode/coId/${coId}', method:'get', toolbar:'#masterCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'majCode',halign:'center',align:'left',width:'15%'"><spring:message code="resc.label.majCode"/></th>			                
			                <th data-options="field:'majCodeName',halign:'center',align:'left',width:'34%'"><spring:message code="resc.label.majCodeName"/></th>
			                <th data-options="field:'useIndc',halign:'center',align:'center',width:'16%'"><spring:message code="resc.label.useIndc"/></th>
			                <th data-options="field:'prntMajCode',halign:'center',align:'center',width:'20%'"><spring:message code="resc.label.prntMajCode"/></th>
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
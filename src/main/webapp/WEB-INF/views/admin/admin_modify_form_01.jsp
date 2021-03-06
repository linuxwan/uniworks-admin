<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.adminModifyForm"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript">
    $(function(){
	    $('#btnSave').bind('click', function(){
	    	if($('#adminModifyForm').form('enableValidation').form('validate')) {  
	    		var formData = parseFormHelper('adminModifyForm');
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/admin/update';
	    		
	    		$.ajax({
					type: 'PUT',
					url: strUrl,
					data: formData, 
					dataType: 'json',						
					beforeSend: function(xhr) {
						xhr.setRequestHeader("Accept", "application/json");
				        xhr.setRequestHeader("Content-Type", "application/json");
						//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},  				
					success : function(msg) {												
						$.messager.alert('<spring:message code="resc.label.confirm"/>', msg,"info", function(){
							window.opener.reload();
							window.close();
						});												
					},
					error : function(xhr, status, error) {
						console.log("error: " + error);
					}
	    		});
	    		return false;
	    	}
		});   
    });
    
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.adminModifyForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="adminModifyForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" value="${cm010c.coId}" data-options="label:'<spring:message code="resc.label.coId"/>:',required:true,labelWidth:100,readonly:true">		            
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="adminId" name="adminId" style="width:100%" value="${cm010c.adminId}" data-options="label:'<spring:message code="resc.label.adminID"/>:',required:true,labelWidth:100,readonly:true">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="useStDate" name="useStDate" style="width:100%" value='<fmt:formatDate value="${cm010c.useStDate}" pattern="yyyy-MM-dd" />' data-options="label:'<spring:message code="resc.label.useStDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="useFinDate" name="useFinDate" style="width:100%" value='<fmt:formatDate value="${cm010c.useFinDate}" pattern="yyyy-MM-dd" />' data-options="label:'<spring:message code="resc.label.useFinDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empNo" name="empNo" style="width:100%" value="${cm010c.empNo}" data-options="label:'<spring:message code="resc.label.empNo"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="pswd" name="pswd" type="password" style="width:100%" data-options="label:'<spring:message code="resc.label.pswd"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>	        	       
	        <tr>
	        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="adminType" name="adminType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.adminType"/>:',required:true,labelWidth:100">
		            	<c:forEach items="${codeList}" var="code" varStatus="st">
		            		<option value="${code.subCode}" <c:if test="${code.subCode == cm010c.adminType}">selected="selected"</c:if> >${code.subCode}: ${code.rescKeyValue}</option>
		            	</c:forEach>
		            	</select>
		            </div>
	            </td>
	        </tr>
        </table>
    </form>
    	<div style="text-align:center;padding:5px 0">
	        <a href="javascript:void(0)" id="btnSave" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.btn.save"/></a>			            
	        <a href="javascript:void(0)" id="btnClose" class="easyui-linkbutton" onclick="window.close()" style="width:80px"><spring:message code="resc.btn.close"/></a>
	    </div> 
    </div> 
</body>
</html>    
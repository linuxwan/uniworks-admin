<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.majCodeAddForm"/></title>
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
	    	if($('#majCodeAddForm').form('enableValidation').form('validate')) {
	    		var majCode = $('#majCode').textbox('getValue');	
	    		$('#rescKey').textbox('setValue', majCode);
	    		var formData = parseFormHelper('majCodeAddForm');	    		
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/mastercode/create';	    		    		
	    		
	    		$.ajax({
					type: 'POST',
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
						var title = '<spring:message code="resc.label.confirm"/>';		    			
						$.messager.alert(title, msg, "info",  function(){
							window.opener.masterCodeReload();
							window.close();
						});						
					},
					error : function(xhr, status, error) {
						console.log("error: " + status);
					}
	    		});
	    		return false;
	    	}
		});
	    
	    var coId = window.opener.$('#coId').combobox('getValue'); 
	    $('#coId').textbox('setValue', coId);
	    $('#coId').textbox('readonly', true);
	    $('#rescKey').textbox('readonly', true);
    });                   
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.majCodeAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="majCodeAddForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" data-options="label:'<spring:message code="resc.label.coId"/>:',required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="majCode" name="majCode" style="width:100%" data-options="label:'<spring:message code="resc.label.majCode"/>:',required:true,labelWidth:120">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="rescKey" name="rescKey" style="width:100%" data-options="label:'<spring:message code="resc.label.rescKey"/>:',labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		            	<spring:message code="resc.label.useIndc"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <input class="easyui-validatebox" id="useIndcY" name="useIndc" type="radio" data-options="required:true" value="Y" checked="checked"><spring:message code="resc.label.use"/>
		                <input class="easyui-validatebox" id="useIndcN" name="useIndc" type="radio" data-options="required:true" value="N"><spring:message code="resc.label.unused"/>
		            </div>
	            </td>
	        </tr>
	        <c:forEach items="${langList}" var="lang">	        	       
	        <tr>	        	
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="majCodeName_<c:out value="${lang.rescKeyValue}"/>" name="majCodeName_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.majCodeName"/>(${lang.rescKeyValue}):',required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="majCodeDesc_<c:out value="${lang.rescKeyValue}"/>" name="majCodeDesc_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.majCodeDesc"/>(${lang.rescKeyValue}):',labelWidth:120">
		            </div>
	            </td>	            
	        </tr>
	        </c:forEach>
	        <tr>	        	
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="prntMajCode" name="prntMajCode" style="width:100%" data-options="label:'<spring:message code="resc.label.prntMajCode"/>:',labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        			            
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
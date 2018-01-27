<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.majCodeModifyForm"/></title>
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
	    	if($('#majCodeModifyForm').form('enableValidation').form('validate')) {
	    		var majCode = $('#majCode').textbox('getValue');	
	    		$('#rescKey').textbox('setValue', majCode);
	    		var formData = parseFormHelper('majCodeModifyForm');	    		
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/mastercode/update';	    		    		
	    		
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
	    
	    var useIndc = '${cm001c.useIndc}';
	    if (useIndc == 'Y') {
	    	$('#useIndcY').attr('checked', true);
	    } else {
	    	$('#useIndcN').attr('checked', true);
	    }
    });                   
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.majCodeModifyForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="majCodeModifyForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:120" value="${cm001c.coId}">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="majCode" name="majCode" style="width:100%" data-options="label:'<spring:message code="resc.label.majCode"/>:',readonly:true,required:true,labelWidth:120" value="${cm001c.majCode}">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="rescKey" name="rescKey" style="width:100%" data-options="label:'<spring:message code="resc.label.rescKey"/>:',readonly:true,labelWidth:120" value="${cm001c.rescKey}">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		            	<spring:message code="resc.label.useIndc"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <input type="radio" class="easyui-validatebox" id="useIndcY" name="useIndc" type="radio" data-options="required:true" value="Y"><spring:message code="resc.label.use"/>
		                <input type="radio" class="easyui-validatebox" id="useIndcN" name="useIndc" type="radio" data-options="required:true" value="N"><spring:message code="resc.label.unused"/>		                
		            </div>
	            </td>
	        </tr>
	        <c:forEach items="${cm003mList}" var="cm003m">	        	       
	        <tr>	        	
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="majCodeName_<c:out value="${cm003m.locale}"/>" name="majCodeName_<c:out value="${cm003m.locale}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.majCodeName"/>(${cm003m.locale}):',required:true,labelWidth:120" value="${cm003m.rescKeyValue}">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="majCodeDesc_<c:out value="${cm003m.locale}"/>" name="majCodeDesc_<c:out value="${cm003m.locale}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.majCodeDesc"/>(${cm003m.locale}):',labelWidth:120" value="${cm003m.rescKeyDesc}">
		            </div>
	            </td>	            
	        </tr>
	        </c:forEach>
        </table>
    </form>
    	<div style="text-align:center;padding:5px 0">
	        <a href="javascript:void(0)" id="btnSave" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.btn.save"/></a>			            
	        <a href="javascript:void(0)" id="btnClose" class="easyui-linkbutton" onclick="window.close()" style="width:80px"><spring:message code="resc.btn.close"/></a>
	    </div> 
    </div> 
</body>
</html>
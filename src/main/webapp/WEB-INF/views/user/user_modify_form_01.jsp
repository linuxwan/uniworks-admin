<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.userAddForm"/></title>
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
	    	if($('#userAddForm').form('enableValidation').form('validate')) {  
	    		var formData = parseFormHelper('userAddForm');
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/user/update';
	    		
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
	    
	    var useIndc = '${nw100m.useIndc}';
	    if (useIndc == 'Y') {
	    	$('#useIndcY').attr('checked', true);
	    } else {
	    	$('#useIndcN').attr('checked', true);
	    }
	    
	    var pswdInitIndc = '${nw100m.pswdInitIndc}';
	    if (pswdInitIndc == '1') {
	    	$('#pswdInitIndcY').attr('checked', true);
	    } else {
	    	$('#pswdInitIndcN').attr('checked', true);
	    }
    });
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.userAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="userAddForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:120" value="${nw100m.coId}">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="userId" name="userId" style="width:100%" data-options="label:'<spring:message code="resc.label.empNo"/>:',readonly:true,required:true,labelWidth:120" value="${nw100m.userId}">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-passwordbox" id="pswd" name="pswd" style="width:100%" data-options="label:'<spring:message code="resc.label.pswd"/>:',labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		            	<input class="easyui-passwordbox" id="cnfmPswd" name="cnfmPswd" style="width:100%" data-options="label:'<spring:message code="resc.label.cnfmPswd"/>:',labelWidth:120">		            			            	
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="internalMailAddr" name="internalMailAddr" style="width:100%" data-options="label:'<spring:message code="resc.label.internalMailAddr"/>:',required:true,labelWidth:120" value="${nw100m.internalMailAddr}">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="outsideMailAddr" name="outsideMailAddr" style="width:100%" data-options="label:'<spring:message code="resc.label.outsideMailAddr"/>:',required:true,labelWidth:120" value="${nw100m.outsideMailAddr}">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <spring:message code="resc.label.pswdInitIndc"/>:&nbsp;
		                <input class="easyui-validatebox" id="pswdInitIndcY" name="pswdInitIndc" type="radio" data-options="required:true" value="1"><spring:message code="resc.label.yes"/>
		                <input class="easyui-validatebox" id="pswdInitIndcN" name="pswdInitIndc" type="radio" data-options="required:true" value="2"><spring:message code="resc.label.no"/>
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <spring:message code="resc.label.useIndc"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <input class="easyui-validatebox" id="useIndcY" name="useIndc" type="radio" data-options="required:true" value="Y"><spring:message code="resc.label.yes"/>
		                <input class="easyui-validatebox" id="useIndcN" name="useIndc" type="radio" data-options="required:true" value="N"><spring:message code="resc.label.no"/>
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
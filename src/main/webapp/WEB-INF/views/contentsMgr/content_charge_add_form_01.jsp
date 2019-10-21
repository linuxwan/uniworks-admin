<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="resc.label.cntnAuthAddForm"/></title>
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
    	$('#btnSave').bind('click', function(){
	    	if($('#authAddForm').form('enableValidation').form('validate')) {	    		
	    		var formData = JSON.stringify($('#authAddForm').serializeObject());
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/contentCharge/create';	    		    		
	    			    		
	    		$.ajax({
					type: 'POST',
					url: strUrl,
					data: formData, 					
					dataType: 'json',	
					contentType: 'application/json',
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
						console.log("error: " + status);
					}
	    		});	    		
	    		return false;
	    	}
		});	      	    	
    });               
    
    function fnSelectChargeEmp(targetObj) {    	    	    	
    	var url = "<c:out value="${contextPath}"/>/admin/userSearchForm?targetObj=" + targetObj;						    		
    	$.popupWindow(url, { name: "userId", height: 500, width: 450 });	    	
    }
    
    function fnSelectChargeEmpClear(targetObj) {
    	$('#empName').textbox('setValue', '');
    	$('#' + targetObj).val('');
    }
          	
  	//직원 검색 PopUp 창에서 호출하는 Function
  	function callBackEmpInfo(targetObj, userId, userName) {
  		$('#empName').textbox('setValue', userName + "(" + userId + ")");
    	$('#' + targetObj).val(userId);
  	}  	
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.cntnAuthAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="authAddForm">
	<input type="hidden" id="empNo" name="empNo"/>
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">			
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="cntnId" name="cntnId" style="width:100%" value="${cntnId}" data-options="label:'<spring:message code="resc.label.cntnId"/>:',readonly:true,required:true,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" value="${coId}" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:100">
		            </div>
	            </td>	            	            	          
	        </tr>	           
	        <tr>	        	
	        	<td style="width:100%;padding:0px 10px;" colspan="2">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empName" name="empName" style="width:30%" data-options="label:'<spring:message code="resc.label.useAuthGrpCode"/>:',readonly:true,labelWidth:100">
		                <a href="javascript:fnSelectChargeEmp('empNo')" id="btnSelUseAuthGrpCode" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.label.selectCharge"/></a>
		                <a href="javascript:fnSelectChargeEmpClear('empNo')" id="btnEraseUseAuthGrpCode" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
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
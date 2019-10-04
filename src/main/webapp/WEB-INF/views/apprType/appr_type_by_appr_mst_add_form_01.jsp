<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.apprTypeByApprListAddForm"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript">
    $(function(){
    	var crntDate = getCurrentDate('-');
    	$('#createDate').textbox('setValue', crntDate);
    	
	    $('#btnSave').bind('click', function(){
	    	if($('#apprTypeByApprMstAddForm').form('enableValidation').form('validate')) {
	    		var formData = parseFormHelper('apprTypeByApprMstAddForm');	    		
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/apprMstByApprovalType/create';	    		    		
	    		
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
							window.opener.apprTypeByApprListReload();
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
    });                   
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.apprTypeByApprListAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="apprTypeByApprMstAddForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" value="${coId}" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		            	
		                <select class="easyui-combobox" id="apprMstId" name="apprMstId" style="width:100%;" data-options="panelHeight:100,label:'<spring:message code="resc.label.apprMasterId"/>:',required:true,labelWidth:120">
		                <c:forEach items="${apprMstList}" var="apprMst">
		                	<option value="${apprMst.apprMstId}">${apprMst.apprMstId}:${apprMst.apprDesc}</option>
		                </c:forEach>
		                </select>		                		                
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="apprItemId" name="apprItemId" style="width:100%" value="${apprItemId}" data-options="label:'<spring:message code="resc.label.apprType"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">	            	
		            <div style="margin-bottom:10px">		            		    
		                <input class="easyui-textbox" id="seqNo" name="seqNo" style="width:100%" data-options="label:'<spring:message code="resc.label.seqNo"/>:',required:true,labelWidth:120">		                
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
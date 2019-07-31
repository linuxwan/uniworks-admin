<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="resc.label.userRoleRgstForm"/></title>
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
    	fnCheckedRadioButtonById("useIndcY");
    });
    
    function fnSelectUser() {
    	var coId = $("#coId").textbox('getValue');					    	
		var url = "<c:out value="${contextPath}"/>/admin/userSearchForm";						
		
		$.popupWindow(url, { name: "userId", height: 650, width: 450 });		
    }        
    
    function fnSelectUserClear(userId) {
    	$('#userId').textbox('setValue', '');
    }
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.userRoleRgstForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="roleAddForm">
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
		                <input class="easyui-textbox" id="userId" name="userId" style="width:60%" data-options="label:'<spring:message code="resc.label.userId"/>:',readonly:true,required:true,labelWidth:120">		                
		                <a href="javascript:fnSelectUser()" id="btnSelectUser" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.btn.userSearch"/></a>
		                <a href="javascript:fnSelectUserClear()" id="btnEraseSelectUser" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
		            </div>
	            </td>	            	            	          
	        </tr>	        
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="role" name="role" style="width:100%" value="${role}" data-options="label:'<spring:message code="resc.label.role"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>	            	            	        
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="roleName" name="roleName" style="width:100%" value="${roleName}" data-options="label:'<spring:message code="resc.label.roleName"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>
	        </tr>	        	        
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.useIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="useIndc" id="useIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="useIndc" id="useIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">			        		            
		            <div style="margin-bottom:10px">			            	
		            	
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
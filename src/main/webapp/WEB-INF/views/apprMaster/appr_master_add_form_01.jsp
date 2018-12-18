<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.apprMasterAddForm"/></title>
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
	    	if($('#apprTypeAddForm').form('enableValidation').form('validate')) {
	    		var formData = parseFormHelper('apprTypeAddForm');	    		
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/approvalMaster/create';	    		    		
	    		
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
	    
	    var coId = window.opener.$('#coId').combobox('getValue'); 
	    $('#coId').textbox('setValue', coId);
	    $('#coId').textbox('readonly', true);	    
    });                   
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.apprMasterAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="apprTypeAddForm">
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
		                <select class="easyui-combobox" id="basePrsvTerm" name="basePrsvTerm" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.prsvTerm"/>:',required:true,labelWidth:120">
		                <c:forEach items="${prsvTermList}" var="prsvTerm">
		                	<option value="${prsvTerm.subCode}">${prsvTerm.subCode}:${prsvTerm.rescKeyDesc}</option>
		                </c:forEach>
		                </select>		 
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="apprMstId" name="apprMstId" style="width:100%" data-options="label:'<spring:message code="resc.label.apprMasterId"/>:',required:true,labelWidth:120">
		            </div>
	            </td>	           
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <select class="easyui-combobox" id="apprItemId" name="apprItemId" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.apprType"/>:',required:true,labelWidth:120">
		                <c:forEach items="${apprItemList}" var="apprItem">
		                	<option value="${apprItem.apprItemId}">${apprItem.apprItemId}:${apprItem.apprItemName}</option>
		                </c:forEach>
		                </select>
		            </div>
	            </td>
	        </tr>
	        <c:forEach items="${langList}" var="lang">	        	       
	        <tr>	        	
	            <td colspan="2" style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="apprDesc_<c:out value="${lang.rescKeyValue}"/>" name="apprDesc_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.apprDesc"/>(${lang.rescKeyValue}):',required:true,labelWidth:120">
		            </div>
	            </td>	                 
	        </tr>
	        </c:forEach>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="rcptIndc" name="rcptIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.rcptIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y"><spring:message code="resc.label.yes"/></option>
		                	<option value="N"><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="rfncIndc" name="rfncIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.rfncIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y"><spring:message code="resc.label.yes"/></option>
		                	<option value="N"><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>			        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="cprtnIndc" name="cprtnIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cprtnIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y"><spring:message code="resc.label.yes"/></option>
		                	<option value="N"><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="cprtnType" name="cprtnType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cprtnType"/>:',labelWidth:120">
		                	<option value=""></option>		                
		                	<c:forEach items="${cprtnTypeList}" var="cprtnType">
		                	<option value="${cprtnType.subCode}">${cprtnType.subCode}:${cprtnType.rescKeyDesc}</option>
		                	</c:forEach>
		                </select>		 
		            </div>			        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="crcltnIndc" name="crcltnIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.crcltnIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y"><spring:message code="resc.label.yes"/></option>
		                	<option value="N"><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="dcsnRuleIndc" name="dcsnRuleIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.dcsnRuleIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y"><spring:message code="resc.label.yes"/></option>
		                	<option value="N" selected="selected"><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>			        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        			            
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="atndIndc" name="atndIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.atndIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y"><spring:message code="resc.label.yes"/></option>
		                	<option value="N"><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>	           
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="apprLevel" name="apprLevel" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.apprLevel"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="1">1<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="2">2<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="3">3<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="4">4<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="5">5<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="6">6<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="7">7<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="8">8<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="9">9<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="10">10<spring:message code="resc.label.orderApproval"/></option>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.apprMasterModifyForm"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript">
    $(function(){    	  
    	var chkCprtnIndc = $("#cprtnIndc").combobox('getValue');
    	if (chkCprtnIndc == "Y") {
    		$("#cprtnType").combobox('options').required = true;
			$("#cprtnType").combobox('textbox').validatebox('options').required = true;
			$("#cprtnType").combobox('validate');
    	}
    	
    	$('#cprtnIndc').combobox({
    		onChange(newValue, oldValue) {
    			var cprtnType = $("#cprtnType").combobox('getValue');
    			
    			if (newValue == "Y") {    				
    				if (cprtnType == "" || cprtnType == null) {
    					$("#cprtnType").combobox('options').required = true;
    					$("#cprtnType").combobox('textbox').validatebox('options').required = true;
    					$("#cprtnType").combobox('validate');
	    				var title = '<spring:message code="resc.label.confirm"/>';		
	    				var msg = '<spring:message code="resc.msg.selectCprtnType"/>';
	    				$.messager.alert(title, msg, "info");
    				}
    			} else if (newValue == "N") {
    				$("#cprtnType").combobox('options').required = false;
					$("#cprtnType").combobox('textbox').validatebox('options').required = false;
					$("#cprtnType").combobox('validate');
    			}
    		}
    	});    	        	
    	
	    $('#btnSave').bind('click', function(){
	    	if($('#apprTypeModifyForm').form('enableValidation').form('validate')) {
	    		var formData = parseFormHelper('apprTypeModifyForm');	    		
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/approvalMaster/modify';	    		    		
	    		
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
						console.log("error: " + status);
					}
	    		});	    		
	    		return false;
	    	}
		});
	    
	    var coId = window.opener.$('#selCoId').combobox('getValue'); 
	    $('#coId').textbox('setValue', coId);
	    $('#coId').textbox('readonly', true);	    
    });              
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.apprMasterModifyForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="apprTypeModifyForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" value="${apprMstInfo.coId}" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="basePrsvTerm" name="basePrsvTerm" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.prsvTerm"/>:',required:true,labelWidth:120">
		                <c:forEach items="${prsvTermList}" var="prsvTerm">
		                	<option value="${prsvTerm.subCode}" <c:if test="${prsvTerm.subCode == apprMstInfo.basePrsvTerm}">selected="selected"</c:if> >${prsvTerm.subCode}:${prsvTerm.rescKeyDesc}</option>
		                </c:forEach>
		                </select>		 
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="apprMstId" name="apprMstId" value="${apprMstInfo.apprMstId}" style="width:100%" data-options="label:'<spring:message code="resc.label.apprMasterId"/>:',required:true,labelWidth:120">
		            </div>
	            </td>	           
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="apprLevel" name="apprLevel" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.apprLevel"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="1" <c:if test="${apprMstInfo.apprLevel == '1'}">selected="selected"</c:if> >1<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="2" <c:if test="${apprMstInfo.apprLevel == '2'}">selected="selected"</c:if> >2<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="3" <c:if test="${apprMstInfo.apprLevel == '3'}">selected="selected"</c:if> >3<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="4" <c:if test="${apprMstInfo.apprLevel == '4'}">selected="selected"</c:if> >4<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="5" <c:if test="${apprMstInfo.apprLevel == '5'}">selected="selected"</c:if> >5<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="6" <c:if test="${apprMstInfo.apprLevel == '6'}">selected="selected"</c:if> >6<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="7" <c:if test="${apprMstInfo.apprLevel == '7'}">selected="selected"</c:if> >7<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="8" <c:if test="${apprMstInfo.apprLevel == '8'}">selected="selected"</c:if> >8<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="9" <c:if test="${apprMstInfo.apprLevel == '9'}">selected="selected"</c:if> >9<spring:message code="resc.label.orderApproval"/></option>
		                	<option value="10" <c:if test="${apprMstInfo.apprLevel == '10'}">selected="selected"</c:if> >10<spring:message code="resc.label.orderApproval"/></option>
		                </select>
		            </div>
	            </td>
	        </tr>
	        <c:forEach items="${nw011mList}" var="nw011m">	        	       
	        <tr>	        	
	            <td colspan="2" style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="apprDesc_<c:out value="${nw011m.locale}"/>" name="apprDesc_<c:out value="${nw011m.locale}"/>" value="${nw011m.apprDesc}"  style="width:100%" data-options="label:'<spring:message code="resc.label.apprDesc"/>(${nw011m.locale}):',required:true,labelWidth:120">
		            </div>
	            </td>	                 
	        </tr>
	        </c:forEach>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="rcptIndc" name="rcptIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.rcptIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y" <c:if test="${apprMstInfo.rcptIndc == 'Y'}">selected="selected"</c:if> ><spring:message code="resc.label.yes"/></option>
		                	<option value="N" <c:if test="${apprMstInfo.rcptIndc == 'N'}">selected="selected"</c:if> ><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="rfncIndc" name="rfncIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.rfncIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y" <c:if test="${apprMstInfo.rfncIndc == 'Y'}">selected="selected"</c:if> ><spring:message code="resc.label.yes"/></option>
		                	<option value="N" <c:if test="${apprMstInfo.rfncIndc == 'N'}">selected="selected"</c:if> ><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>			        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="cprtnIndc" name="cprtnIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cprtnIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y" <c:if test="${apprMstInfo.cprtnIndc == 'Y'}">selected="selected"</c:if> ><spring:message code="resc.label.yes"/></option>
		                	<option value="N" <c:if test="${apprMstInfo.cprtnIndc == 'N'}">selected="selected"</c:if> ><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="cprtnType" name="cprtnType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cprtnType"/>:',labelWidth:120">
		                	<option value=""></option>		                
		                	<c:forEach items="${cprtnTypeList}" var="cprtnType">
		                	<option value="${cprtnType.subCode}" <c:if test="${apprMstInfo.cprtnIndc == cprtnType.subCode}">selected="selected"</c:if> >${cprtnType.subCode}:${cprtnType.rescKeyDesc}</option>
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
		                	<option value="Y" <c:if test="${apprMstInfo.crcltnIndc == 'Y'}">selected="selected"</c:if> ><spring:message code="resc.label.yes"/></option>
		                	<option value="N" <c:if test="${apprMstInfo.crcltnIndc == 'N'}">selected="selected"</c:if> ><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="dcsnRuleIndc" name="dcsnRuleIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.dcsnRuleIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y" <c:if test="${apprMstInfo.dcsnRuleIndc == 'Y'}">selected="selected"</c:if> ><spring:message code="resc.label.yes"/></option>
		                	<option value="N" <c:if test="${apprMstInfo.dcsnRuleIndc == 'N'}">selected="selected"</c:if> ><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>			        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        			            
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="atndIndc" name="atndIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.atndIndc"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                	<option value="Y" <c:if test="${apprMstInfo.atndIndc == 'Y'}">selected="selected"</c:if> ><spring:message code="resc.label.yes"/></option>
		                	<option value="N" <c:if test="${apprMstInfo.atndIndc == 'N'}">selected="selected"</c:if> ><spring:message code="resc.label.no"/></option>
		                </select>		 
		            </div>
	            </td>	           
	            <td style="width:50%;padding:0px 10px;"></td>
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
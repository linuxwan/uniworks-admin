<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.contentModifyForm"/></title>
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
    var registCompany = '${nw032ms}';           
    
    $(function(){    	    	    	
    	$('#cntnType').combobox({
    		onChange(newValue, oldValue) {    			
    			
    		}
    	});    	        	
    	    	    	
    	$('#cntnJointFlagY').radiobutton({
    		onChange(checked) {
    			if (checked == true) {
    				<c:forEach items="${companyList}" var="company">   		               	                			            
    				fnDisabledCheckBoxById('company_${company.coId}', true);
    				fnCheckedCheckBoxById('company_${company.coId}');
                	</c:forEach>
    			}
    		}
    	});
    	
    	$('#cntnJointFlagN').radiobutton({
    		onChange(checked) {
    			if (checked == true) {
    				var coId = $('#coId').textbox('getValue');    				    				
    				<c:forEach items="${companyList}" var="company">
    				if (coId == '${company.coId}') {
    					fnCheckedCheckBoxById('company_${company.coId}');
    					fnDisabledCheckBoxById('company_${company.coId}', true);    					
    				} else {
    					fnDisabledCheckBoxById('company_${company.coId}', false);
    					fnUnCheckedCheckBoxById('company_${company.coId}');
    				} 	       				    				
                	</c:forEach>                         	                	
    			}
    		}
    	});
    	
    	$('#btnSave').bind('click', function(){
	    	if($('#contentModifyForm').form('enableValidation').form('validate')) {
	    		disableObjectChangeEnabled();
	    		var formData = JSON.stringify($('#contentModifyForm').serializeObject());
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/contents/modify';	    		    		
	    			    		
	    		$.ajax({
					type: 'PUT',
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
	    
	    var coId = window.opener.$('#selCoId').combobox('getValue'); 
	    $('#coId').textbox('setValue', coId);
	    $('#coId').textbox('readonly', true);	 	    	    
	    
	    initialObjectValue();
    });           
    
    //radio 버튼의 값들을 초기화한다.
    function initialObjectValue() {
    	fnCheckedRadioButtonById("cntnJointFlag" + '${contentInfo.cntnJointFlag}');
    	    	    	    	
    	var coId = $('#coId').textbox('getValue');
    	<c:forEach items="${companyList}" var="company">
		if (coId == '${company.coId}') {
			fnCheckedCheckBoxById('company_${company.coId}');
			fnDisabledCheckBoxById('company_${company.coId}', true);    					
		} 	   
		
		if (registCompany.indexOf('${company.coId}') > -1 && coId != '${company.coId}') {
			fnCheckedCheckBoxById('company_${company.coId}');
			fnDisabledCheckBoxById('company_${company.coId}', false);
		}
    	</c:forEach>       	    	
    }
    
    function disableObjectChangeEnabled() {    	   
    	fnDisabledRadioButtonById('cntnJointFlag', false);  
    	<c:forEach items="${companyList}" var="company">
    	fnDisabledCheckBoxById('company_${company.coId}', false);
    	</c:forEach>
    }       
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.contentModifyForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="contentModifyForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">			
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" value="${contentInfo.coId}" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="cntnId" name="cntnId" value="${contentInfo.cntnId}" style="width:100%" data-options="label:'<spring:message code="resc.label.cntnId"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>	            	            	          
	        </tr>
	        <tr>	        	
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="cntnType" name="cntnType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cntnType"/>:',required:true,labelWidth:120">		                	
		                <c:forEach items="${cntnTypeList}" var="cntnType">
		                	<option value="${cntnType.subCode}" <c:if test="${cntnType.subCode == contentInfo.cntnType}">selected="selected"</c:if> >${cntnType.subCode}:${cntnType.rescKeyDesc}</option>
		                </c:forEach>
		                </select>		 
		            </div>		            
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="masterId" name="masterId" style="width:60%" data-options="label:'<spring:message code="resc.label.masterId"/>:',readonly:true,labelWidth:120" value="${contentInfo.masterId}">
		                <a href="javascript:fnSelectTypeCode('typeCode4')" id="btnSelTypeCode4" class="easyui-linkbutton" style="width:70px"><spring:message code="resc.btn.masterId"/></a>
		                <a href="javascript:fnSelectTypeClear('typeCode4')" id="btnEraseTypeCode4" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>		                
		            </div>		            
	            </td>	         	            
	        </tr>
	        <c:forEach items="${nw031mList}" var="nw031m">	        	       
	        <tr>	        	
	            <td colspan="2" style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="cntnName_<c:out value="${nw031m.locale}"/>" name="cntnName_<c:out value="${nw031m.locale}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.cntnName"/>(${nw031m.locale}):',required:true,labelWidth:120" value="${nw031m.cntnName}">
		            </div>
	            </td>	                 
	        </tr>
	        </c:forEach>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">	        				        
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="cntnOfferType" name="cntnOfferType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cntnOfferType"/>:',required:true,labelWidth:120">		                	
		                <c:forEach items="${cntnOfferTypeList}" var="cntnOfferType">
		                	<option value="${cntnOfferType.subCode}" <c:if test="${cntnOfferType.subCode == contentInfo.cntnOfferType}">selected="selected"</c:if> >${cntnOfferType.subCode}:${cntnOfferType.rescKeyDesc}</option>
		                </c:forEach>
		                </select>		 
		            </div>		        
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.linkSystemHostname"/>:</td>
		            		<td>            		               
		                	<input class="easyui-textbox" id="linkSysHost" name="linkSysHost" value="${contentInfo.linkSysHost}" style="width:63%" data-options="labelWidth:20"> :
		                	<input class="easyui-textbox" id="portNo" name="portNo" value="${contentInfo.portNo}" style="width:20%" data-options="labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>			   		            		               			            
	            </td>
	        </tr>	        
	        <tr>	        	
	        	<td colspan="2" style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="url" name="url" style="width:100%" data-options="label:'<spring:message code="resc.label.url"/>',labelWidth:120" value="${contentInfo.url}">
		            </div>
	            </td>	              	            
	        </tr>
	        <tr>	        	
	        	<td colspan="2" style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="cntnDesc" name="cntnDesc" style="width:100%" data-options="label:'<spring:message code="resc.label.cntnDesc"/>',labelWidth:120" value="${contentInfo.cntnDesc}">
		            </div>
	            </td>	              	            
	        </tr>		
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.cntnJointFlag"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="cntnJointFlag" id="cntnJointFlagY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="cntnJointFlag" id="cntnJointFlagN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">			        		            
		            <div style="margin-bottom:10px">			            	
		            	<c:forEach items="${companyList}" var="company">   		               	                			            
	                	<input class="easyui-checkbox" id="company_${company.coId}" name="company" value="${company.coId}" data-options="label:'${company.coName}',labelPosition:'after',labelWidth:80">
	                	</c:forEach>
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
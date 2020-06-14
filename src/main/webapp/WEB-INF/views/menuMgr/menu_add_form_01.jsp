<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.menuAddForm"/></title>
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
    var registCompany = '${nw003ms}';    
        
    $(function(){    	    	    	    	    	    	    
    	$('#btnSave').bind('click', function(){
	    	if($('#roleAddForm').form('enableValidation').form('validate')) {	    		
	    		var formData = JSON.stringify($('#roleAddForm').serializeObject());
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/menu/create';	    		    		
	    			    		
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
    	
    	$('#btnSelectContent').bind('click', function(){
    		fnSelectTypeCode('cntnId');
    	});    	
    	
    	$('#cntnLinkIndcY').radiobutton({
    		onChange(checked) {
    			$('#cntnName').textbox('readonly', false);
    			$('#btnSelectContent').linkbutton('enable');
    		}
    	});
    	
		$('#cntnLinkIndcN').radiobutton({
			onChange(checked) {
    			$('#cntnName').textbox('readonly', true);
    			$('#btnSelectContent').linkbutton('disable');    			
    		}
    	});
    	
		$('#btnSelectContent').linkbutton('disable');
    	fnCheckedRadioButtonById("menuDsplIndcY");
    	fnCheckedRadioButtonById("dfltMenuIndcY");
    	fnCheckedRadioButtonById("cntnLinkIndcN");
    	fnCheckedRadioButtonById("dlgtMenuIndcN");
    	fnCheckedRadioButtonById("myMenuSetIndcN");
    });  
    
    function fnSelectTypeCode(typeCodeName) {
    	var coId = '${menuInfo.coId}';
		var url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentSearchForm?coId=" + coId + "&typeCodeName=" + typeCodeName;						
		$.popupWindow(url, { name: typeCodeName, height: 460, width: 400 });		
    }     
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.menuAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
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
		                <input class="easyui-textbox" id="highMenuId" name="highMenuId" style="width:100%" value="${highMenuId}" data-options="label:'<spring:message code="resc.label.highMenuId"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>	            	            	          
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="menuId" name="menuId" style="width:100%" value="" data-options="label:'<spring:message code="resc.label.menuId"/>:',required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="menuLevel" name="menuLevel" style="width:100%" value="${menuLevel}" data-options="label:'<spring:message code="resc.label.menuLevel"/>:',readonly:true,required:true,labelWidth:120">
		            </div>
	            </td>	            	            	          
	        </tr>	
	        <c:forEach items="${langList}" var="lang">	  	           
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">		                
		                <input class="easyui-textbox" id="menuDsplName_<c:out value="${lang.rescKeyValue}"/>" name="menuDsplName_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.menuDsplName"/>(${lang.rescKeyValue}):',required:true,labelWidth:120">		                		                
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		                
		                <input class="easyui-textbox" id="menuDesc_<c:out value="${lang.rescKeyValue}"/>" name="menuDesc_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.menuDesc"/>(${lang.rescKeyValue}):',labelWidth:120">
		            </div>
	            </td>	            	            	          
	        </tr>
	        </c:forEach>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.menuDsplIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="menuDsplIndc" id="menuDsplIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="menuDsplIndc" id="menuDsplIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="menuOrd" name="menuOrd" style="width:100%" value="" data-options="label:'<spring:message code="resc.label.menuOrd"/>:',required:true,labelWidth:120">
		            </div>
	            </td>	            	            	          
	        </tr>
	        <tr>
	        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="bodyUrl" name="bodyUrl" style="width:100%" value="" data-options="label:'<spring:message code="resc.label.bodyUrl"/>:',labelWidth:120">
		            </div>
	            </td>	                  	            	         
	        </tr>
	        <tr>
	        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="iconFileUrl" name="iconFileUrl" style="width:100%" value="" data-options="label:'<spring:message code="resc.label.iconFileUrl"/>:',labelWidth:120">
		            </div>
	            </td>	                  	            	         
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.dfltMenuIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="dfltMenuIndc" id="dfltMenuIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="dfltMenuIndc" id="dfltMenuIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">			        		            
		            <div style="margin-bottom:10px">
		                <select class="easyui-combobox" id="linkType" name="linkType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.linkType"/>:',labelWidth:120">
			    			<c:forEach items="${linkTypeList}" var="linkType" varStatus="st">
		            		<option value="${linkType.subCode}">${linkType.rescKeyDesc}</option>
		            		</c:forEach>		            	
		        		</select>
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">			            	
		                <table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.cntnlinkIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="cntnLinkIndc" id="cntnLinkIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="cntnLinkIndc" id="cntnLinkIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">			        		            
		            <div style="margin-bottom:10px" >
		                <input class="easyui-textbox" id="cntnId" name="cntnId" style="width:70%" value="" data-options="label:'<spring:message code="resc.label.cntnId"/>:',labelWidth:120">	                
		                <a href="#" id="btnSelectContent" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.btn.cntnSelect"/></a>
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.dlgtMenuIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="dlgtMenuIndc" id="dlgtMenuIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="dlgtMenuIndc" id="dlgtMenuIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.myMenuSetIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="myMenuSetIndc" id="myMenuSetIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="myMenuSetIndc" id="myMenuSetIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
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
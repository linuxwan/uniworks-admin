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
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/contentAuth/modify';	    		    		
	    			    		
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
    	
    	$('#crtAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#crtAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#crtAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	$('#rdAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#rdAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#rdAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	$('#updtAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#updtAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#updtAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	$('#delAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#delAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#delAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	$('#prntAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#prntAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#prntAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	$('#upldAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#upldAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#upldAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	$('#dnldAuth').checkbox({
    		onChange(checked) {
    			if (checked == true) {    				
    				$("#dnldAuth").checkbox('setValue', 'Y');
    			} else {
    				$("#dnldAuth").checkbox('setValue', 'N');
    			}
    		}
    	});
    	
    	if ("${nw033m.crtAuth}" == "Y") fnCheckedCheckBoxById("crtAuth");
    	if ("${nw033m.rdAuth}" == "Y") fnCheckedCheckBoxById("rdAuth");
    	if ("${nw033m.updtAuth}" == "Y") fnCheckedCheckBoxById("updtAuth");
    	if ("${nw033m.delAuth}" == "Y") fnCheckedCheckBoxById("delAuth");
    	if ("${nw033m.prntAuth}" == "Y") fnCheckedCheckBoxById("prntAuth");
    	if ("${nw033m.upldAuth}" == "Y") fnCheckedCheckBoxById("upldAuth");
    	if ("${nw033m.dnldAuth}" == "Y") fnCheckedCheckBoxById("dnldAuth");
    });                      
    
    function fnSelectUseAuthGrpCode(targetObj) {
    	var useAuthType = $("#useAuthType").combobox('getValue');
    	var coId = '${coId}';
    	
    	if (useAuthType == "G") {
    		var url = "<c:out value="${contextPath}"/>/admin/groupMgr/searchGroupForm?targetObj=" + targetObj;
    		$.popupWindow(url, { name: "userId", height: 500, width: 350 });
    	} else if (useAuthType == "P") {    						    
    		var url = "<c:out value="${contextPath}"/>/admin/userSearchForm?targetObj=" + targetObj;						    		
    		$.popupWindow(url, { name: "userId", height: 500, width: 450 });	
    	} else if (useAuthType == "O") {    					        	    
    		var url = "<c:out value="${contextPath}"/>/admin/selectOgan?coId=" + coId + "&targetObj=" + targetObj;
    		$.popupWindow(url, { name: 'selectOgan', createNew: false, height: 500, width: 350 });    	
    	} else if (useAuthType == "R") {
    		var url = "<c:out value="${contextPath}"/>/admin/roleMgr/roleSearchForm?targetObj=" + targetObj;
    		$.popupWindow(url, { name: "userId", height: 500, width: 350 });
    	}
    }
    
    //그룹 선택 PopUp 창에서 호출되는 Function
    function callBackGroupInfo(targetObj, grpCode, grpName) {
    	$('#' + targetObj).textbox('setValue', grpName + "(" + grpCode + ")");
    	$('#' + targetObj + "Code").val(grpCode);
    	$('#' + targetObj + "Lev").val("");
    }    
  	//조직 선택 PopUp 창에서 호출하는 Function
    function callBackOganInfo(targetOgan, oganLev, oganCode, oganName) {
    	$('#' + targetOgan).textbox('setValue', oganCode + ":" + oganName);
    	$('#' + targetOgan + "Code").val(oganCode);
    	$('#' + targetOgan + "Lev").val(oganLev);
    }
  	//직원 검색 PopUp 창에서 호출하는 Function
  	function callBackEmpInfo(targetObj, userId, userName) {
  		$('#' + targetObj).textbox('setValue', userName + "(" + userId + ")");
    	$('#' + targetObj + "Code").val(userId);
    	$('#' + targetObj + "Lev").val("");
  	}
  	//Role 검색 PopUp 창에서 호출하는 Function
  	function callBackRoleInfo(targetObj, role, roleName) {
  		$('#' + targetObj).textbox('setValue', roleName + "(" + role + ")");
    	$('#' + targetObj + "Code").val(role);
    	$('#' + targetObj + "Lev").val("");  		
  	}
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.cntnAuthAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="authAddForm">
	<input type="hidden" id="useAuthGrpCode" name="useAuthGrpCode" value="${nw033m.useAuthGrpCode}"/>
	<input type="hidden" id="useAuthGrpLev" name="useAuthGrpLev"/>
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
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="useAuthType" name="useAuthType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.cntnType"/>:',required:true,labelWidth:100">		                	
		                <c:forEach items="${useAuthTypeList}" var="useAuthType">
		                	<option value="${useAuthType.subCode}" <c:if test="${useAuthType.subCode == nw033m.useAuthType}">selected="selected"</c:if>  >${useAuthType.subCode}:${useAuthType.rescKeyDesc}</option>
		                </c:forEach>
		                </select>		 
		            </div>		            
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="useAuthGrp" name="useAuthGrp" value="${nw033m.useAuthGrpCode}" style="width:60%" data-options="label:'<spring:message code="resc.label.useAuthGrpCode"/>:',readonly:true,labelWidth:100">
		                <a href="javascript:fnSelectUseAuthGrpCode('useAuthGrp')" id="btnSelUseAuthGrpCode" class="easyui-linkbutton" style="width:70px"><spring:message code="resc.btn.selectGrp"/></a>
		                <a href="javascript:fnSelectUseAuthGrpCodeClear('useAuthGrpCode')" id="btnEraseUseAuthGrpCode" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
		            </div>		            
	            </td>	         	            
	        </tr>	        	        
	        <tr>
	        	<td style="width:50%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">	
		            	<table style="width:100%">
		            		<tr>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="crtAuth" name="crtAuth" value="${nw033m.crtAuth}" data-options="label:'<spring:message code="resc.label.crtAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="rdAuth" name="rdAuth" value="${nw033m.rdAuth}" data-options="label:'<spring:message code="resc.label.rdAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="updtAuth" name="updtAuth" value="${nw033m.updtAuth}" data-options="label:'<spring:message code="resc.label.updtAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="delAuth" name="delAuth" value="${nw033m.delAuth}" data-options="label:'<spring:message code="resc.label.delAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            		</tr>
		            		<tr>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="prntAuth" name="prntAuth" value="${nw033m.prntAuth}" data-options="label:'<spring:message code="resc.label.prntAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="upldAuth" name="upldAuth" value="${nw033m.upldAuth}" data-options="label:'<spring:message code="resc.label.upldAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            			<td style="width:25%">
		            				<input class="easyui-checkbox" id="dnldAuth" name="dnldAuth" value="${nw033m.dnldAuth}" data-options="label:'<spring:message code="resc.label.dnldAuth"/>',labelPosition:'after',labelWidth:100">
		            			</td>
		            			<td style="width:25%">
		            				
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
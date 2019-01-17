<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.boardMasterAddForm"/></title>
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
    
    console.log("registCompany : " + registCompany);
    
    $(function(){    	    	    	
    	$('#boardType').combobox({
    		onChange(newValue, oldValue) {    			
    			fnCheckedBoardTypeControl(newValue);
    		}
    	});    	        	
    	
    	$('#entrOpenIndcY').radiobutton({
    		onChange(checked) {
    			if (checked == true) {
    				<c:forEach items="${companyList}" var="company">   		               	                			            
    				fnDisabledCheckBoxById('company_${company.coId}', true);
    				fnCheckedCheckBoxById('company_${company.coId}');
                	</c:forEach>
    			}
    		}
    	});
    	
    	$('#entrOpenIndcN').radiobutton({
    		onChange(checked) {
    			if (checked == true) {
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
    		}
    	});
    	
    	$('#validTermIndcY').radiobutton({
    		onChange(checked) {
    			if (checked == true) {
    				$('#validTermCode').combobox('enable');
    			}
    		}
    	});
    	
    	$('#validTermIndcN').radiobutton({
    		onChange(checked) {
    			if (checked == true) {
    				$('#validTermCode').combobox('select', '');
    				$('#validTermCode').combobox('disable');
    			}
    		}
    	});
    	
	    $('#btnSave').bind('click', function(){
	    	if($('#boardMasterAddForm').form('enableValidation').form('validate')) {
	    		disableObjectChangeEnabled();
	    		var formData = JSON.stringify($('#boardMasterAddForm').serializeObject());
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/boardMaster/modify';	    		    		
	    			    		
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
	    
	    var boardType = $('#boardType').combobox('getValue');
	    fnCheckedBoardTypeControl(boardType);
	    
	    initialObjectValue();
    });           
    
    //radio 버튼의 값들을 초기화한다.
    function initialObjectValue() {
    	fnCheckedRadioButtonById("useIndc" + '${nw001m.useIndc}');
    	fnCheckedRadioButtonById("atchIndc" + '${nw001m.atchIndc}');
    	fnCheckedRadioButtonById("validTermIndc" + '${nw001m.validTermIndc}');
    	fnCheckedRadioButtonById("entrOpenIndc" + '${nw001m.entrOpenIndc}');
    	fnCheckedRadioButtonById("rplyIndc" + '${nw001m.rplyIndc}');
    	fnCheckedRadioButtonById("cmntIndc" + '${nw001m.cmntIndc}');
    	fnCheckedRadioButtonById("apprIndc" + '${nw001m.apprIndc}');
    	fnCheckedRadioButtonById("evalIndc" + '${nw001m.evalIndc}');
    	fnCheckedRadioButtonById("anceIndc" + '${nw001m.anceIndc}');
    	fnCheckedRadioButtonById("anonyIndc" + '${nw001m.anonyIndc}');
    	
    	$(validTermCode).combobox('select', '${nw001m.validTermCode}');    	    	
    }
    
    function disableObjectChangeEnabled() {
    	fnDisabledRadioButtonById('rplyIndc', false);  
    	fnDisabledRadioButtonById('cmntIndc', false);
    	fnDisabledRadioButtonById('apprIndc', false);
    	fnDisabledRadioButtonById('evalIndc', false);
    	fnDisabledRadioButtonById('anceIndc', false);
    	fnDisabledRadioButtonById('anonyIndc', false);
    	<c:forEach items="${companyList}" var="company">
    	fnDisabledCheckBoxById('company_${company.coId}', false);
    	</c:forEach>
    }
    
    /**
     * Board type에 따라 Radio 버튼 제어 및 활성화
     */
    function fnCheckedBoardTypeControl(boardType) {    	
    	fnCheckedRadioButtonById("useIndcY");    	
    	fnCheckedRadioButtonById("atchIndcN");
    	fnCheckedRadioButtonById("validTermIndcN");
    	fnCheckedRadioButtonById("entrOpenIndcN");
    	
    	switch(boardType) {
    		case 'B01':	//댓글게시판
    			fnCheckedRadioButtonById("rplyIndcN"); fnDisabledRadioButtonById('rplyIndc', false);   	
    			fnCheckedRadioButtonById("cmntIndcY"); fnDisabledRadioButtonById('cmntIndc', true);
    			fnCheckedRadioButtonById("apprIndcN"); fnDisabledRadioButtonById('apprIndc', true);
    			fnCheckedRadioButtonById("evalIndcN"); fnDisabledRadioButtonById('evalIndc', false);
    			fnCheckedRadioButtonById("anceIndcN"); fnDisabledRadioButtonById('anceIndc', false);
    			fnCheckedRadioButtonById("anonyIndcY"); fnDisabledRadioButtonById('anonyIndc', false);
    			break;
    		case 'B02':	//답글게시판
    			fnCheckedRadioButtonById("rplyIndcY"); fnDisabledRadioButtonById('rplyIndc', true);   	
    			fnCheckedRadioButtonById("cmntIndcN"); fnDisabledRadioButtonById('cmntIndc', false);
    			fnCheckedRadioButtonById("apprIndcN"); fnDisabledRadioButtonById('apprIndc', true);
    			fnCheckedRadioButtonById("evalIndcN"); fnDisabledRadioButtonById('evalIndc', false);
    			fnCheckedRadioButtonById("anceIndcN"); fnDisabledRadioButtonById('anceIndc', false);
    			fnCheckedRadioButtonById("anonyIndcY"); fnDisabledRadioButtonById('anonyIndc', false);
    			break;
    		case 'B03': //승인게시판
    			fnCheckedRadioButtonById("rplyIndcN"); fnDisabledRadioButtonById('rplyIndc', true);   	
    			fnCheckedRadioButtonById("cmntIndcN"); fnDisabledRadioButtonById('cmntIndc', true);
    			fnCheckedRadioButtonById("apprIndcY"); fnDisabledRadioButtonById('apprIndc', true);
    			fnCheckedRadioButtonById("evalIndcN"); fnDisabledRadioButtonById('evalIndc', true);
    			fnCheckedRadioButtonById("anceIndcN"); fnDisabledRadioButtonById('anceIndc', false);
    			fnCheckedRadioButtonById("anonyIndcN"); fnDisabledRadioButtonById('anonyIndc', true);
    			break;
    		case 'B04': //이미지 게시판
    			fnCheckedRadioButtonById("rplyIndcN"); fnDisabledRadioButtonById('rplyIndc', true);   	
    			fnCheckedRadioButtonById("cmntIndcY"); fnDisabledRadioButtonById('cmntIndc', false);
    			fnCheckedRadioButtonById("apprIndcN"); fnDisabledRadioButtonById('apprIndc', true);
    			fnCheckedRadioButtonById("evalIndcN"); fnDisabledRadioButtonById('evalIndc', false);
    			fnCheckedRadioButtonById("anceIndcN"); fnDisabledRadioButtonById('anceIndc', false);
    			fnCheckedRadioButtonById("anonyIndcY"); fnDisabledRadioButtonById('anonyIndc', false);
    			break;
    		case 'B99': //사용자정의 게시판
    			fnCheckedRadioButtonById("rplyIndcN"); fnDisabledRadioButtonById('rplyIndc', true);   	
    			fnCheckedRadioButtonById("cmntIndcN"); fnDisabledRadioButtonById('cmntIndc', true);
    			fnCheckedRadioButtonById("apprIndcN"); fnDisabledRadioButtonById('apprIndc', false);
    			fnCheckedRadioButtonById("evalIndcN"); fnDisabledRadioButtonById('evalIndc', false);
    			fnCheckedRadioButtonById("anceIndcN"); fnDisabledRadioButtonById('anceIndc', false);
    			fnCheckedRadioButtonById("anonyIndcY"); fnDisabledRadioButtonById('anonyIndc', false);
    			break;    			
    	}
    }
    
    function fnSelectTypeCode(typeCodeName) {
    	var coId = $("#coId").textbox('getValue');					    	
		var url = "<c:out value="${contextPath}"/>/admin/codeMgr/majCodeListPopup?coId=" + coId + "&typeCodeName=" + typeCodeName;						
		
		$.popupWindow(url, { name: typeCodeName, height: 650, width: 450 });		
    }        
    
    function fnSelectTypeClear(typeCodeName) {
    	$('#' + typeCodeName).textbox('setValue', '');
    }
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.boardMasterAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="boardMasterAddForm">
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
		                <select class="easyui-combobox" id="boardType" name="boardType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.boardType"/>:',required:true,labelWidth:120">		                	
		                <c:forEach items="${boardTypeList}" var="boardType">
		                	<option value="${boardType.subCode}" <c:if test="${boardType.subCode == nw001m.boardType}">selected="selected"</c:if> >${boardType.subCode}:${boardType.rescKeyDesc}</option>
		                </c:forEach>
		                </select>		 
		            </div>		            
	            </td>	            	           
	        </tr>
	        <tr>	        	
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="boardId" name="boardId" value="${nw001m.boardId}" style="width:100%" data-options="label:'<spring:message code="resc.label.boardId"/>:',required:true,labelWidth:120">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		            
	            </td>	         	            
	        </tr>
	        <c:forEach items="${nw002mList}" var="nw002m">	        	       
	        <tr>	        	
	            <td colspan="2" style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="boardName_<c:out value="${nw002m.locale}"/>" name="boardName_<c:out value="${nw002m.locale}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.boardName"/>(${nw002m.locale}):',required:true,labelWidth:120" value="${nw002m.boardName}">
		            </div>
	            </td>	                 
	        </tr>
	        </c:forEach>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">	        				        
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.rplyIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="rplyIndc" id="rplyIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="rplyIndc" id="rplyIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.cmntIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="cmntIndc" id="cmntIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="cmntIndc" id="cmntIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>			        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.atchIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="atchIndc" id="atchIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="atchIndc" id="atchIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.evalIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="evalIndc" id="evalIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="evalIndc" id="evalIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	        			            
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.validTermIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="validTermIndc" id="validTermIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="validTermIndc" id="validTermIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">	        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="validTermCode" name="validTermCode" style="width:70%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.validTerm"/>:',required:true,labelWidth:120">
		                	<option value=""></option>
		                <c:forEach items="${prsvTermList}" var="prsvTerm">
		                	<option value="${prsvTerm.subCode}" <c:if test="${prsvTerm.subCode == nw001m.validTermCode}">selected="selected"</c:if> >${prsvTerm.subCode}:${prsvTerm.rescKeyDesc}</option>		                	                                    
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
		            		<td style="width:50%;"><spring:message code="resc.label.anonyIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="anonyIndc" id="anonyIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="anonyIndc" id="anonyIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	            <td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.anceIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="anceIndc" id="anceIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="anceIndc" id="anceIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.apprIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="apprIndc" id="apprIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="apprIndc" id="apprIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
		                	</td>
		                </tr>
		                </table>		                
		            </div>	
	            </td>
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
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">	
		            	<table>	
		            	<tr>	
		            		<td style="width:50%;"><spring:message code="resc.label.entrOpenIndc"/>:</td>
		            		<td>            		               
		                	<input class="easyui-radiobutton" name="entrOpenIndc" id="entrOpenIndcY" value="Y" data-options="label:'<spring:message code="resc.label.yes"/>',labelPosition:'after',labelWidth:20">&nbsp;		            
		                	<input class="easyui-radiobutton" name="entrOpenIndc" id="entrOpenIndcN" value="N" data-options="label:'<spring:message code="resc.label.no"/>',labelPosition:'after',labelWidth:45">
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
	        <tr>	        	
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="typeCode1" name="typeCode1" style="width:60%" data-options="label:'<spring:message code="resc.label.type1"/>:',labelWidth:120" value="${nw001m.typeCode1}">
		                <a href="javascript:fnSelectTypeCode('typeCode1')" id="btnSelTypeCode1" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.type1"/></a>
		                <a href="javascript:fnSelectTypeClear('typeCode1')" id="btnEraseTypeCode1" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
	            	<div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="typeCode2" name="typeCode2" style="width:60%" data-options="label:'<spring:message code="resc.label.type2"/>:',labelWidth:120" value="${nw001m.typeCode2}">
		                <a href="javascript:fnSelectTypeCode('typeCode2')" id="btnSelTypeCode2" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.type2"/></a>
		                <a href="javascript:fnSelectTypeClear('typeCode2')" id="btnEraseTypeCode2" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
		            </div>		            
	            </td>	         	            
	        </tr>
	        <tr>	        	
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="typeCode3" name="typeCode3" style="width:60%" data-options="label:'<spring:message code="resc.label.type3"/>:',labelWidth:120" value="${nw001m.typeCode3}">
		                <a href="javascript:fnSelectTypeCode('typeCode3')" id="btnSelTypeCode3" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.type3"/></a>
		                <a href="javascript:fnSelectTypeClear('typeCode3')" id="btnEraseTypeCode3" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">		            
	            	<div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="typeCode4" name="typeCode4" style="width:60%" data-options="label:'<spring:message code="resc.label.type4"/>:',labelWidth:120" value="${nw001m.typeCode4}">
		                <a href="javascript:fnSelectTypeCode('typeCode4')" id="btnSelTypeCode4" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.type4"/></a>
		                <a href="javascript:fnSelectTypeClear('typeCode4')" id="btnEraseTypeCode4" class="easyui-linkbutton" style="width:50px"><spring:message code="resc.btn.erase"/></a>
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
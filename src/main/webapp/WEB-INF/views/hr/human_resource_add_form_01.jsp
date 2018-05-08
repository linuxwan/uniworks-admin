<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.oganMbrRgsr"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.popupwindow.js"></script>
    
    <script type="text/javascript">
    $(function(){
	    $('#btnSave').bind('click', function(){
	    	if($('#hrInfoForm').form('enableValidation').form('validate')) {
	    		var formData = parseFormHelper('hrInfoForm');	    		
	    		var strUrl = '<c:out value="${contextPath}"/>/rest/hr/create';	    		    		
	    		
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
							window.opener.subCodeReload();
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
	    
	    var oganCode = '${oganCode}';
	    var oganLev = '${oganLev}';
	    var oganDesc = '${oganDesc}';
	    
	    if (oganCode != "" && oganLev != "" && oganDesc != "") {
	    	$('#workOgan').textbox('setValue', oganCode + ":" + oganDesc);
	    	$('#orgnOgan').textbox('setValue', oganCode + ":" + oganDesc);
	    }
    });            
    
    //조직선택 Popup창 호출
    function selectOgan(targetObj) {
    	var coId = '${coId}';			        	    
		var url = "<c:out value="${contextPath}"/>/admin/selectOgan?coId=" + coId + "&targetObj=" + targetObj;
		$.popupWindow(url, { name: 'selectOgan', createNew: false, height: 500, width: 350 });    	
    }
    
    //PopUp 창에서 호출하는 Function
    function callBackOganInfo(targetOgan, oganLev, oganCode, oganName) {
    	$('#' + targetOgan).textbox('setValue', oganCode + ":" + oganName);
    	$('#' + targetOgan + "Code").textbox('setValue', oganCode);
    	$('#' + targetOgan + "Lev").textbox('setValue', oganLev);
    }
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.oganMbrRgsr"/>" style="width:100%;max-width:100%;padding:10px 10px;">     
	<form id="hrInfoForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" data-options="label:'<spring:message code="resc.label.coId"/>:',readonly:true,required:true,labelWidth:100" value="${coId}">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empNo" name="empNo" style="width:100%" data-options="label:'<spring:message code="resc.label.empNo"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empNameKor" name="empNameKor" style="width:100%" data-options="label:'<spring:message code="resc.label.empName"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empNameEng" name="empNameEng" style="width:100%" data-options="label:'<spring:message code="resc.label.empNameEng"/>:',required:true,labelWidth:100">
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empNameChn" name="empNameChn" style="width:100%" data-options="label:'<spring:message code="resc.label.empNameChn"/>:',required:true,labelWidth:100" value="${empInfo.empNameChn}">
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="workIndc" name="workIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.workIndc"/>:',required:true,labelWidth:100">
		                	<option value=""></option>
				    	<c:forEach items="${workIndcList}" var="opt" varStatus="st">
			            	<option value="${opt.subCode}">${opt.subCode}:${opt.rescKeyValue}</option>
			            </c:forEach>		            	
			        	</select>	
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="dutyCode" name="dutyCode" style="width:100%;" data-options="label:'<spring:message code="resc.label.dutyDesc"/>:',required:true,labelWidth:100">
		                	<option value=""></option>
				    	<c:forEach items="${dutyList}" var="opt" varStatus="st">
			            	<option value="${opt.subCode}">${opt.subCode}:${opt.rescKeyValue}</option>
			            </c:forEach>		            	
			        	</select>
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <select class="easyui-combobox" id="pstnCode" name="pstnCode" style="width:100%;" data-options="label:'<spring:message code="resc.label.pstnDesc"/>:',labelWidth:100">
		                	<option value=""></option>
				    	<c:forEach items="${pstnList}" var="opt" varStatus="st">
			            	<option value="${opt.subCode}">${opt.subCode}:${opt.rescKeyValue}</option>
			            </c:forEach>		            	
			        	</select>
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="workOgan" name="workOgan" style="width:70%" data-options="label:'<spring:message code="resc.label.workOgan"/>:',required:true,labelWidth:100">
		                <a href="javascript:selectOgan('workOgan')" id="btnSelectWorkOgan" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.label.selectOgan"/></a>
		                <input type="hidden" id="workOganCode" name="workOganCode" />
		                <input type="hidden" id="workOganLev" name="workOganLev" />
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="orgnOgan" name="orgnOgan" style="width:70%" data-options="label:'<spring:message code="resc.label.orgnOgan"/>:',required:true,labelWidth:100">
		                <a href="javascript:selectOgan('orgnOgan')" id="btnSelectOrgnOgan" class="easyui-linkbutton" style="width:80px"><spring:message code="resc.label.selectOgan"/></a>
		                <input type="hidden" id="orgnOganCode" name="orgnOganCode" />
		                <input type="hidden" id="orgnOganLev" name="orgnOganLev" />
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="offcTelNo" name="offcTelNo" style="width:100%" data-options="label:'<spring:message code="resc.label.offcTelNo"/>:',labelWidth:100">
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="moblPhonNo" name="moblPhonNo" style="width:100%" data-options="label:'<spring:message code="resc.label.moblPhonNo"/>:',labelWidth:100">
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="birthDate" name="birthDate" style="width:100%" data-options="label:'<spring:message code="resc.label.birthDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="mailAddr" name="mailAddr" style="width:100%" data-options="label:'<spring:message code="resc.label.mailAddr"/>:',labelWidth:100">
		            </div>
	            </td>	            
	        </tr>	        
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="entrDate" name="entrDate" style="width:100%" data-options="label:'<spring:message code="resc.label.entrDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="rsgnDate" name="rsgnDate" style="width:100%" data-options="label:'<spring:message code="resc.label.rsgnDate"/>:',formatter:dashformatter,parser:dashparser,labelWidth:100">
		            </div>
	            </td>	            
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">		                
		                <select class="easyui-combobox" id="marriageIndc" name="marriageIndc" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.marriageIndc"/>:',required:true,labelWidth:100">
		                	<option value=""></option>
				    	<c:forEach items="${mrgList}" var="opt" varStatus="st">
			            	<option value="${opt.subCode}">${opt.subCode}:${opt.rescKeyValue}</option>
			            </c:forEach>		            	
			        	</select>
		            </div>
	            </td>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="marriageDate" name="marriageDate" style="width:100%" data-options="label:'<spring:message code="resc.label.marriageDate"/>:',formatter:dashformatter,parser:dashparser,labelWidth:100">
		            </div>
	            </td>	            
	        </tr>	           
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="offcOrdDate" name="offcOrdDate" style="width:100%" data-options="label:'<spring:message code="resc.label.offcOrdDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
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
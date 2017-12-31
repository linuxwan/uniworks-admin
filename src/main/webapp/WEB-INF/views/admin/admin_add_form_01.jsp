<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.adminAddForm"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript">
    
    </script>
</head>
<body>
	<div class="easyui-panel" title="<spring:message code="resc.label.adminAddForm"/>" style="width:100%;max-width:100%;padding:10px 10px;">
	<form id="adminAddForm">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
		<table style="width:100%">
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" data-options="label:'<spring:message code="resc.label.coId"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="prntCoCode" name="prntCoCode" style="width:100%" data-options="label:'<spring:message code="resc.label.parentCoCode"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coName" name="coName" style="width:100%" data-options="label:'<spring:message code="resc.label.coName"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="stDate" name="stDate" style="width:100%" data-options="label:'<spring:message code="resc.label.stDate"/>:',required:true,formatter:pointformatter,parser:pointparser,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="finDate" name="finDate" style="width:100%" data-options="label:'<spring:message code="resc.label.finDate"/>:',required:true,formatter:pointformatter,parser:pointparser,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		            	<spring:message code="resc.label.parentCoDivision"/>:&nbsp;&nbsp;&nbsp;&nbsp;
		                <input type="radio" id="prntCoClsfY" name="prntCoClsf" class="easyui-validatebox" value="Y" /><spring:message code="resc.label.parentCompany"/>
						<input type="radio" id="prntCoClsfN" name="prntCoClsf" class="easyui-validatebox" value="N" /><spring:message code="resc.label.subCompany"/>
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		            	<spring:message code="resc.label.useIndc"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <input type="radio" id="useIndcY" name="useIndc" class="easyui-validatebox" value="Y" /><spring:message code="resc.label.use"/>
						<input type="radio" id="useIndcN" name="useIndc" class="easyui-validatebox" value="N" /><spring:message code="resc.label.unused"/>
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="baseOganLev" name="baseOganLev" style="width:100%" data-options="label:'<spring:message code="resc.label.baseOganLev"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="sprtLang" name="sprtLang" style="width:100%" data-options="label:'<spring:message code="resc.label.sprtLang"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="bizRgsrNo" name="bizRgsrNo" style="width:100%" data-options="label:'<spring:message code="resc.label.bizRgsrNo"/>:',labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="rprsntName" name="rprsntName" style="width:100%" data-options="label:'<spring:message code="resc.label.rprsntName"/>:',labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="zipCode" name="zipCode" style="width:100%" data-options="label:'<spring:message code="resc.label.zipCode"/>:',labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="homePageUrl" name="homePageUrl" style="width:100%" data-options="label:'<spring:message code="resc.label.homePageUrl"/>:',labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coAddr" name="coAddr" style="width:100%" data-options="label:'<spring:message code="resc.label.coAddr"/>:',labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coPhonNo" name="coPhonNo" style="width:100%" data-options="label:'<spring:message code="resc.label.coPhonNo"/>:',labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="coFaxNo" name="coFaxNo" style="width:100%" data-options="label:'<spring:message code="resc.label.coFaxNo"/>:',labelWidth:100">
		            </div>
	            </td>
	        </tr>
        </table>
    </form>
	    <div style="text-align:center;padding:5px 0">
	        <a href="javascript:void(0)" id="btnSave" class="easyui-linkbutton" onclick="submitForm()" style="width:80px"><spring:message code="resc.btn.save"/></a>			            
	        <a href="javascript:void(0)" id="btnClear" class="easyui-linkbutton" onclick="formReset()" style="width:80px"><spring:message code="resc.btn.clear"/></a>
	    </div> 
    </div> 
</body>
</html>
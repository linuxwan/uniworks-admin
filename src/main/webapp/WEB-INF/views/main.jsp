<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Uniworks Management</title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/uniworks-admin.css">
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>

    <script type="text/javascript">
    	$(function(){
    		$('.easyui-layout').layout();
    			setHeight();
    		
    		$(window).resize(function(){
    			setHeight();
    		});
    		    		    		
    		$('#selLang').combobox({
    		    onChange:function(newValue,oldValue){
    		        location.href = '<c:out value="${contextPath}"/>/changeLanguage?lang=' + newValue;
    		    }
    		});       		    		    		
    	});            
    	
    	function resizeTopIframe(dynheight) {  
        	document.getElementById("frmMain").style.height = parseInt(dynheight) + 'px';  
        } 
    </script>
        
</head>
<body>
    <div style="margin:0px 0;"></div>
    <div class="easyui-layout" style="width:100%;height:100%;">
        <div data-options="region:'north'" style="height:50px;"></div>
        <div data-options="region:'south',split:true" style="height:50px;"><div style="padding:8px;" align="center">Copyright(c) 2020 Park Chung Wan. All Right Reserved.</div></div>
        <div data-options="region:'east',split:true" title="<spring:message code="resc.msg.welcome" />" style="width:10%;">
        	<div id="user" class="easyui-panel" title="${userSession.adminId}" style="width:100%;height:auto;border:false;padding:1px" data-options="border:false">
				<a href="<c:out value="${contextPath}"/>/logout" class="easyui-linkbutton" style="width:100%"><spring:message code="resc.btn.logout"/></a>
			</div>			
			<div id="multiLanguage" class="easyui-panel" title="<spring:message code="resc.label.multiLanguage" />" style="width:100%;height:auto;border:false;padding:5px">
				<select id="selLang" class="easyui-combobox" name="state" style="width:100%;" data-options="panelHeight:'auto'">
				<c:forEach items="${langList}" var="lang">
					<option value="${lang.rescKeyValue}" <c:if test="${lang.rescKeyValue == userSession.lang}">selected="selected"</c:if> ><c:out value="${lang.rescKeyDesc}"/></option>
				</c:forEach>
				</select>				
			</div>
        </div>
        <div data-options="region:'west',split:true" title='<spring:message code="resc.label.adminMenu" />' style="width:11%;">
        		<div class="easyui-panel" title="Menu" style="width:100%;" data-options="border:false">
		        <div class="easyui-menu" data-options="inline:true,border:false" style="width:100%">
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.companyMgr"/>','/admin/companyMgr')"><spring:message code="resc.menu.companyMgr"/></div>		            
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.adminInfo"/>','/admin/adminMgr')"><spring:message code="resc.menu.adminInfo"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.codeMgr"/>','/admin/codeMgr')"><spring:message code="resc.menu.codeMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.oganMgr"/>','/admin/oganMgr')"><spring:message code="resc.menu.oganMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.hrMgr"/>', '/admin/hrMgr')"><spring:message code="resc.menu.hrMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.userMgr"/>', '/admin/userMgr')"><spring:message code="resc.menu.userMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.apprMasterMgr"/>', '/admin/apprMasterMgr')"><spring:message code="resc.menu.apprMasterMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.apprTypeMgr"/>', '/admin/apprTypeMgr')"><spring:message code="resc.menu.apprTypeMgr"/></div>		            		            
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.boardMasterMgr"/>', '/admin/boardMasterMgr')"><spring:message code="resc.menu.boardMasterMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.contentsMgr"/>', '/admin/contentsMgr')"><spring:message code="resc.menu.contentsMgr"/></div>
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.roleMgr"/>', '/admin/roleMgr')"><spring:message code="resc.menu.roleMgr"/></div>		            
		            <div onclick="javascript:goPage('<spring:message code="resc.menu.menuMgr"/>', '/admin/menuMgr')"><spring:message code="resc.menu.menuMgr"/></div>
		            <div onclick="javascript:goPage('new')">양식함 관리</div>
		        </div>
		    </div>
        </div>
        <div id="content" data-options="region:'center',fit:true,title:'Main Title',iconCls:'icon-ok'" style="width:100%;">
        	<div style="height:auto" data-options="border:false,plain:true">
				<iframe id="frmMain" name="frmMain" width="100%"  src="" sandbox="allow-same-origin allow-scripts allow-popups allow-forms allow-top-navigation allow-pointer-lock" seamless="seamless" style="overflow-x:hidden; overflow:auto; width:100%;"></iframe>
			</div>
        </div>
    </div>    
</body>
</html>
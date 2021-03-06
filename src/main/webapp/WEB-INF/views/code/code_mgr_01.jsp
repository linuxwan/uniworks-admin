<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.companyMgr"/></title>
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
    	$("#masterCodeList").datagrid({
    		onClickRow: function() {
    			var rowData = $("#masterCodeList").datagrid('getSelected');
    			
    			getSubCoodeInfo(rowData.coId, rowData.majCode);    			
    		}
    	});      	
    	
    	$("#coId").combobox({
    		onChange: function(param) {
    			var coId = $("#coId").combobox('getValue');
    			var url = "<c:out value="${contextPath}"/>/rest/mastercode/coId/" + coId;
    			
    			$("#masterCodeList").datagrid({
    	    		url: url,
    	    		method: 'get'
    	    	});
    			
    			//부코드 목록을 Clear
    			$("#subCodeList").datagrid('loadData', []);
    		}
    	});
    });
    
    /**
     * 주코드 정보를 삭제한다.
    **/
    function removeMasterCode() {
    	var rowData = $("#masterCodeList").datagrid('getSelected');
    	
		var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectMajCode"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var majCode = rowData.majCode;   
    	var rescKey = rowData.rescKey;
    	var strUrl = "<c:out value="${contextPath}"/>/rest/mastercode/delete/coId/" + coId + "/majCode/" + majCode + "/rescKey/" + rescKey;

    	var title = '<spring:message code="resc.label.confirm"/>';
    	msg = majCode + " : " + '<spring:message code="resc.msg.confirmDel"/>';    		
    	$.messager.confirm(title, msg, function(r) {
			if (r) {
				$.ajax({
					type: 'DELETE',
					url: strUrl,					
					beforeSend: function(xhr) {
						//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},  				
					success : function(msg) {
						var title = '<spring:message code="resc.label.confirm"/>';		    			
						$.messager.alert(title, msg, "info",  function(){
							$("#masterCodeList").datagrid('reload');
						});					
					},
					error : function(xhr, status, error) {
						console.log("error: " + status);
					}
				});
			}
		});    
    }
    
    /**
     * 부코드 정보를 삭제한다.
    **/
    function removeitSubCode() {
    	var rowData = $("#subCodeList").datagrid('getSelected');
    	
		var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectSubCode"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var majCode = rowData.majCode;
    	var subCode = rowData.subCode;
    	var rescKey = rowData.rescKey;
    	var strUrl = "<c:out value="${contextPath}"/>/rest/subcode/delete/coId/" + coId + "/majCode/" + majCode + "/subCode/" + subCode + "/rescKey/" + rescKey;

    	var title = '<spring:message code="resc.label.confirm"/>';
    	msg = rescKey + " : " + '<spring:message code="resc.msg.confirmDel"/>';    		
    	$.messager.confirm(title, msg, function(r) {
			if (r) {
				$.ajax({
					type: 'DELETE',
					url: strUrl,					
					beforeSend: function(xhr) {
						//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},  				
					success : function(msg) {
						var title = '<spring:message code="resc.label.confirm"/>';		    			
						$.messager.alert(title, msg, "info",  function(){
							$("#subCodeList").datagrid('reload');
						});					
					},
					error : function(xhr, status, error) {
						console.log("error: " + status);
					}
				});
			}
		});    
    }
    
    /*
     * 부코드 정보를 가져온다.
    */
    function getSubCoodeInfo(coId, majCode) {
    	var url = "<c:out value="${contextPath}"/>/rest/subcode/coId/" + coId + "/majCode/" + majCode;
    	$("#subCodeList").datagrid({
    		url: url,
    		method: 'get'
    	});
    }
    
    /*
     * 주코드 등록 화면 호출
    */
    function appendMasterCode() {
    	var coId = $("#coId").combobox('getValue');
		var url = "<c:out value="${contextPath}"/>/admin/codeMgr/masterCodeAddForm?coId=" + coId;
		var cnt = ${fn:length(langList)};		
		var formHeight = 230 + (30 * cnt);
		$.popupWindow(url, { name: 'addMasterCode', height: formHeight, width: 800 });
    }
    
    /*
     * 주코드 수정 화면 호출
    */
    function modifyMasterCode() {
    	var rowData = $("#masterCodeList").datagrid('getSelected');
    	
    	var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectMajCode"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var majCode = rowData.majCode;
		var url = "<c:out value="${contextPath}"/>/admin/codeMgr/masterCodeModifyForm?coId=" + coId + "&majCode=" + majCode;
		var cnt = ${fn:length(langList)};		
		var formHeight = 230 + (30 * cnt);
		$.popupWindow(url, { name: 'modifyMasterCode', height: formHeight, width: 800 });
    }
    
    /*
     * 부코드 등록 화면 호출
    */
    function appendSubCode() {
    	var rowData = $("#masterCodeList").datagrid('getSelected');
    	
    	var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectMajCode"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var majCode = rowData.majCode;
		var url = "<c:out value="${contextPath}"/>/admin/codeMgr/subCodeAddForm?coId=" + coId + "&majCode=" + majCode;
		var cnt = ${fn:length(langList)};		
		var formHeight = 230 + (30 * cnt);
		$.popupWindow(url, { name: 'addSubCode', height: formHeight, width: 800 });
    }
    
    /*
     * 부코드 수정 화면 호출
    */
    function modifySubCode() {
    	var rowData = $("#subCodeList").datagrid('getSelected');
    	
    	var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectSubCode"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var majCode = rowData.majCode;
    	var subCode = rowData.subCode;
		var url = "<c:out value="${contextPath}"/>/admin/codeMgr/subCodeModifyForm?coId=" + coId + "&majCode=" + majCode + "&subCode=" + subCode;
		var cnt = ${fn:length(langList)};		
		var formHeight = 230 + (30 * cnt);
		$.popupWindow(url, { name: 'modifySubCode', height: formHeight, width: 800 });
    }
    
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function masterCodeReload() {
     	$('#masterCodeList').datagrid('reload');
     }
    
     /**
      * 팝업창에서 호출하기 위한 함수(refresh)
      */
      function subCodeReload() {
      	$('#subCodeList').datagrid('reload');
      }
    </script>
</head>
<body>
	<form id="frmHr" style="width:98%;">
	<input type="hidden" id="mode" name="mode" value=""/>			
	<table style="width:98%">
		<tr>
			<td colspan="2">
				<div class="easyui-panel" title="" style="width:100%;max-width:100%;padding:5px 5px;">   
			    <select class="easyui-combobox" id="coId" name="coId" style="width:25%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:50">
			    	<c:forEach items="${coList}" var="opt" varStatus="st">
		            <option value="${opt.coId}" <c:if test="${opt.coId == userSession.coId}">selected="selected"</c:if> >${opt.coId}: ${opt.coName}</option>
		            </c:forEach>		            	
		        </select>
		        </div>
			</td>
		</tr>
		<tr>
			<td style="width:50%">				
       			<table id="masterCodeList" class="easyui-datagrid" style="width:100%;height:660px;"		        
			       		title="<spring:message code="resc.label.mainCodeList"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false,url:'<c:out value="${contextPath}"/>/rest/mastercode/coId/${userSession.coId}', method:'get', toolbar:'#masterCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'majCode',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.majCode"/></th>
			                <th data-options="field:'rescKey',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.rescKey"/></th>
			                <th data-options="field:'majCodeName',halign:'center',align:'left',width:'23%'"><spring:message code="resc.label.majCodeName"/></th>
			                <th data-options="field:'majCodeDesc',halign:'center',align:'left',width:'23%'"><spring:message code="resc.label.majCodeDesc"/></th>
			                <th data-options="field:'useIndc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.useIndc"/></th>
			                <th data-options="field:'prntMajCode',halign:'center',align:'center',width:'14%'"><spring:message code="resc.label.prntMajCode"/></th>
			            </tr>
			        </thead>
			    </table>    	
			    <div id="masterCodeTb" style="height:auto">			    	    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendMasterCode()"><spring:message code="resc.btn.add"/></a>			        
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyMasterCode()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeMasterCode()"><spring:message code="resc.btn.delete"/></a>        
			    </div>  			
			</td>
			<td style="width:50%">
				<table id="subCodeList" class="easyui-datagrid" style="width:100%;height:660px"		        
			       		title="<spring:message code="resc.label.subCodeList"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false, toolbar:'#subCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'majCode',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.majCode"/></th>
			                <th data-options="field:'subCode',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.subCode"/></th>
			                <th data-options="field:'rescKey',halign:'center',align:'left',width:'15%'"><spring:message code="resc.label.rescKey"/></th>
			                <th data-options="field:'subCodeName',halign:'center',align:'left',width:'25%'"><spring:message code="resc.label.subCodeName"/></th>
			                <th data-options="field:'subCodeDesc',halign:'center',align:'left',width:'20%'"><spring:message code="resc.label.subCodeDesc"/></th>
			                <th data-options="field:'useIndc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.useIndc"/></th>
			            </tr>
			        </thead>
			    </table>    	
			    <div id="subCodeTb" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendSubCode()"><spring:message code="resc.btn.add"/></a>			        
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifySubCode()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitSubCode()"><spring:message code="resc.btn.delete"/></a>        
			    </div> 			    
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
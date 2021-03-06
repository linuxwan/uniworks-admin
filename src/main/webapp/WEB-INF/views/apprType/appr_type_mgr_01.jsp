<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.apprTypeMgr"/></title>
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
    	$("#apprTypeList").datagrid({
    		onClickRow: function() {
    			var rowData = $("#apprTypeList").datagrid('getSelected');
    			
    			getApprTypeByApprMasterList(rowData.coId, rowData.apprItemId);    			
    		}
    	});      	
    	
    	$("#coId").combobox({
    		onChange: function(param) {
    			var coId = $("#coId").combobox('getValue');
    			var url = "<c:out value="${contextPath}"/>/rest/approvalType/coId/" + coId;
    			
    			$("#apprTypeList").datagrid({
    	    		url: url,
    	    		method: 'get'
    	    	});
    			
    			//부코드 목록을 Clear
    			$("#apprTypeByApprList").datagrid('loadData', []);
    		}
    	});
    });
    
    /**
     * 결재 유형 정보를 삭제한다.
    **/
    function removeMasterCode() {
    	var rowData = $("#apprTypeList").datagrid('getSelected');
    	
		var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectApprType"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var apprItemId = rowData.apprItemId;    
    	var apprItemName = rowData.apprItemName;
    	var strUrl = "<c:out value="${contextPath}"/>/rest/approvalType/delete/coId/" + coId + "/apprItemId/" + apprItemId;

    	var title = '<spring:message code="resc.label.confirm"/>';
    	msg = apprItemName + "(" + apprItemId + ")" + " : " + '<spring:message code="resc.msg.confirmDel"/>';    		
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
							$("#apprTypeList").datagrid('reload');
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
     * 결재 유형별 결재 마스터 정보를 삭제한다.
    **/
    function removeitApprMstIdByApprType() {
    	var rowData = $("#apprTypeByApprList").datagrid('getSelected');
    	
		var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectApprMstByApprType"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var apprItemId = rowData.apprItemId;
    	var apprMstId = rowData.apprMstId;    	
    	var strUrl = "<c:out value="${contextPath}"/>/rest/apprMstByApprovalType/delete/coId/" + coId + "/apprItemId/" + apprItemId + "/apprMstId/" + apprMstId;
    	var strReloadUrl = "<c:out value="${contextPath}"/>/rest/apprMstByApprovalType/delete/coId/" + coId + "/apprItemId/" + apprItemId;

    	var title = '<spring:message code="resc.label.confirm"/>';
    	msg = rowData.apprDesc + "(" + apprMstId + ")" + " : " + '<spring:message code="resc.msg.confirmDel"/>';    		
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
							getApprTypeByApprMasterList(rowData.coId, rowData.apprItemId);    	
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
     * 결재유형별 결재 Master 목록을 가져온다.
    */
    function getApprTypeByApprMasterList(coId, apprItemId) {
    	var url = "<c:out value="${contextPath}"/>/rest/apprTypeByApprInfo/coId/" + coId + "/apprItemId/" + apprItemId;
    	
    	$("#apprTypeByApprList").datagrid({
    		url: url,
    		method: 'get'
    	});
    }
    
    /*
     * 결재 유형 등록 화면 호출
    */
    function appendApprovalType() {
    	var coId = $("#coId").combobox('getValue');
		var url = "<c:out value="${contextPath}"/>/admin/apprTypeMgr/apprTypeAddForm?coId=" + coId;
		var cnt = ${fn:length(langList)};		
		var formHeight = 200 + (30 * cnt);
		$.popupWindow(url, { name: 'addApprType', height: formHeight, width: 600 });
    }
    
    /*
     * 결재 유형 수정 화면 호출
    */
    function modifyApprovalType() {
    	var rowData = $("#apprTypeList").datagrid('getSelected');
    	
    	var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectApprType"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var apprItemId = rowData.apprItemId;
		var url = "<c:out value="${contextPath}"/>/admin/apprTypeMgr/apprTypeModifyForm?coId=" + coId + "&apprItemId=" + apprItemId;
		var cnt = ${fn:length(langList)};		
		var formHeight = 200 + (30 * cnt);
		$.popupWindow(url, { name: 'modifyApprovalType', height: formHeight, width: 800 });
    }
    
    /*
     * 결재 유형별 결재 마스터 등록
    */
    function appendApprMstIdByApprType() {
    	var rowData = $("#apprTypeList").datagrid('getSelected');
    	
    	var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectApprType"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var apprItemId = rowData.apprItemId;
		var url = "<c:out value="${contextPath}"/>/admin/apprTypeMgr/apprTypeByApprMstAddForm?coId=" + coId + "&apprItemId=" + apprItemId;
		var formHeight = 180;
		$.popupWindow(url, { name: 'addApprMstByApprType', height: formHeight, width: 700 });
    }
    
    /*
     * 결재 유형별 결재 마스터 수정 화면 호출
    */
    function modifyApprMstIdByApprType() {
    	var rowData = $("#apprTypeByApprList").datagrid('getSelected');
    	
    	var title = '<spring:message code="resc.label.confirm"/>';
		var msg = '<spring:message code="resc.msg.selectApprMstByApprType"/>';
		if ($('#mode').val() != '' || rowData == null) {    			    			    			
			alertMsg(title, msg);
			return;
		}
		
    	var coId = rowData.coId;
    	var apprItemId = rowData.apprItemId;
    	var apprMstId = rowData.apprMstId;
		var url = "<c:out value="${contextPath}"/>/admin/apprTypeMgr/apprTypeByApprMstModifyForm?coId=" + coId + "&apprItemId=" + apprItemId + "&apprMstId=" + apprMstId;			
		var formHeight = 180;
		$.popupWindow(url, { name: 'modifyApprMstByApprType', height: formHeight, width: 700 });
    }
    
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function approvalTypeReload() {
     	$('#apprTypeList').datagrid('reload');
     }
    
     /**
      * 팝업창에서 호출하기 위한 함수(refresh)
      */
      function apprTypeByApprListReload() {
      	$('#apprTypeByApprList').datagrid('reload');
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
       			<table id="apprTypeList" class="easyui-datagrid" style="width:100%;height:660px;"		        
			       		title="<spring:message code="resc.label.apprTypeMgr"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false,url:'<c:out value="${contextPath}"/>/rest/approvalType/coId/${userSession.coId}', method:'get', toolbar:'#masterCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'seqNo',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.seqNo"/></th>
			                <th data-options="field:'apprItemId',halign:'center',align:'center',width:'20%'"><spring:message code="resc.label.apprTypeId"/></th>
			                <th data-options="field:'apprItemName',halign:'center',align:'center',width:'23%'"><spring:message code="resc.label.apprTypeName"/></th>			                
			                <th data-options="field:'createDate',halign:'center',align:'center',width:'15%',formatter:formatDateYYYYMMDD"><spring:message code="resc.label.crtDate"/></th>
			                <th data-options="field:'imgUrl',halign:'center',align:'left',width:'25%'"><spring:message code="resc.label.imgUrl"/></th>
			            </tr>
			        </thead>
			    </table>    	
			    <div id="masterCodeTb" style="height:auto">			    	    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendApprovalType()"><spring:message code="resc.btn.add"/></a>			        
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyApprovalType()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeMasterCode()"><spring:message code="resc.btn.delete"/></a>        
			    </div>  			
			</td>
			<td style="width:50%">
				<table id="apprTypeByApprList" class="easyui-datagrid" style="width:100%;height:660px"		        
			       		title="<spring:message code="resc.label.apprTypeByApprList"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false, toolbar:'#subCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'apprItemId',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.apprTypeId"/></th>
			                <th data-options="field:'apprItemName',halign:'center',align:'center',width:'17%'"><spring:message code="resc.label.apprTypeName"/></th>
			                <th data-options="field:'apprMstId',halign:'center',align:'center',width:'23%'"><spring:message code="resc.label.apprMasterId"/></th>
			                <th data-options="field:'apprDesc',halign:'center',align:'left',width:'25%'"><spring:message code="resc.label.apprDesc"/></th>
			                <th data-options="field:'seqNo',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.seqNo"/></th>			                
			            </tr>
			        </thead>
			    </table>    	
			    <div id="subCodeTb" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendApprMstIdByApprType()"><spring:message code="resc.btn.add"/></a>			        
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyApprMstIdByApprType()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitApprMstIdByApprType()"><spring:message code="resc.btn.delete"/></a>        
			    </div> 			    
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
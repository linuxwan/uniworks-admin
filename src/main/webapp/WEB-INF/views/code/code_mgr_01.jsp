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
     * 회사 정보를 삭제한다.
    **/
    function deleteMasterCode() {
    	var coId = $('#coId').textbox('getValue');
    	var stDate = replaceAll($('#stDate').textbox('getValue'), ".", "");    	
    	var strUrl = "<c:out value="${contextPath}"/>/rest/company/delete/" + coId + "/stDate/" + stDate;

    	$.ajax({
			type: 'DELETE',
			url: strUrl,					
			beforeSend: function(xhr) {
				//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},  				
			success : function(json) {
				$("#coTree").treegrid('reload');	
			},
			error : function(xhr, status, error) {
				console.log("error: " + status);
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
		var formHeight = 200 + (30 * cnt);
		$.popupWindow(url, { height: formHeight, width: 800 });
    }
    </script>
</head>
<body>
	<input type="hidden" id="mode" name="mode" value=""/>			
	<table style="width:100%">
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
       			<table id="masterCodeList" class="easyui-datagrid" style="width:100%;height:686px;"		        
			       		title="<spring:message code="resc.label.mainCodeList"/>" 
			       		data-options="rownumbers:true, singleSelect:true, collapsible:false,url:'<c:out value="${contextPath}"/>/rest/mastercode/coId/${userSession.coId}', method:'get', toolbar:'#masterCodeTb', pagination:false, autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'majCode',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.majCode"/></th>
			                <th data-options="field:'rescKey',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.rescKey"/></th>
			                <th data-options="field:'majCodeName',halign:'center',align:'left',width:'30%'"><spring:message code="resc.label.majCodeName"/></th>
			                <th data-options="field:'majCodeDesc',halign:'center',align:'left',width:'30%'"><spring:message code="resc.label.majCodeDesc"/></th>
			                <th data-options="field:'useIndc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.useIndc"/></th>
			            </tr>
			        </thead>
			    </table>    	
			    <div id="masterCodeTb" style="height:auto">			    	    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendMasterCode()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeMasterCode()"><spring:message code="resc.btn.delete"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyMasterCode()"><spring:message code="resc.btn.modify"/></a>        
			    </div>  			
			</td>
			<td style="width:50%">
				<table id="subCodeList" class="easyui-datagrid" style="width:100%;height:686px"		        
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
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="resc.btn.delete"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modify()"><spring:message code="resc.btn.modify"/></a>        
			    </div> 			    
			</td>
		</tr>
	</table>	
</body>
</html>
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
	var url = "";
	var cntnId = "";
    
    $(function(){    	
    	var coId = $("#selCoId").combobox('getValue');    	
    	url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/cntnName/searchWord/0/orderBy/cntnId";	
    	
    	$("#contentList").datagrid({
    		onClickRow: function(rowIndex, rowData) {
    			var coId = $("#selCoId").combobox('getValue');
    			var contentChargeList = "<c:out value="${contextPath}"/>/rest/contentCharge/coId/" + coId + "/cntnId/" + rowData.cntnId;
    			cntnId = rowData.cntnId;
    			
    			$('#contentChargeList').datagrid('loadData', getContentAuthList(contentChargeList));    			
    		}
    	});
    	
    	$('#contentList').datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 컨텐츠 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				
				url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/cntnName/searchWord/0/orderBy/cntnId";	
				$('#contentList').datagrid('loadData', getData());
    		}
    	});     	    	    	   
    });
            
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
    	var coId = $("#selCoId").combobox('getValue');
    	var contentChargeList = "<c:out value="${contextPath}"/>/rest/contentCharge/coId/" + coId + "/cntnId/" + cntnId;
     	
     	$('#contentChargeList').datagrid('loadData', getContentAuthList(contentChargeList));
     }
    
    /*
     * Role 목록 정보를 가져온다.
    */
    function getData() {
    	getAjaxData(url);
    	return rows; 
    }
    
    /*
     * 등록된 컨텐츠 목록을 Ajax로 호출
    */
    function getAjaxData(url) {
    	rows = [];
   	    	
    	$.ajaxSetup({async: false});    	 
    	$.getJSON(url, function (data, status){
    		if (status == 'success') {    			 
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					cntnId: entry["cntnId"],
    					cntnName: entry["cntnName"]
    				});
    			});
    			
    			$('#contentChargeList').datagrid('loadData', []);
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    
    function getContentAuthList(contentChargeList) {
    	getAjaxContentAuthList(contentChargeList);
    	return rows; 
    }
    
    function getAjaxContentAuthList(contentChargeList) {
    	rows = [];
	    	
    	$.ajaxSetup({async: false});    	 
    	$.getJSON(contentChargeList, function (data, status){
    		if (status == 'success') {    			 
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					cntnId : entry["cntnId"],
    					cntnName : entry["cntnName"],
    					userId: entry["userId"],
    					empName: entry["empName"],
    					deptDesc: entry["deptDesc"],
    					dutyDesc: entry["dutyDesc"],
    					pstnDesc: entry["pstnDesc"]
    				});
    			});
    			
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    
    function appendContentCharge() {
    	if (cntnId == "" || cntnId == null) {
    		var msg = '<spring:message code="resc.msg.noSelectContentOwner"/>';
    		var title = '<spring:message code="resc.label.confirm"/>';		    			
    		alertMsg(title, msg);			
    		return;
    	}
    	var coId = $("#selCoId").combobox('getValue');					    	
		url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentChargeAddForm?coId=" + coId + "&cntnId=" + cntnId;		
		var formHeight = 190;
		
		$.popupWindow(url, { name: 'chargeAddForm', height: formHeight, width: 800 });
		
    }        
    
    function removeitContentCharge() {
    	var coId = $("#selCoId").combobox('getValue');	
    	var rowData = $("#contentChargeList").datagrid('getSelected');	
    	
    	if (rowData == null) {
    		var title = '<spring:message code="resc.label.confirm"/>';
    		var msg = '<spring:message code="resc.msg.noSelectCharge"/>';
    		alertMsg(title, msg);
			return;
    	}
    	
    	msg = '<spring:message code="resc.msg.confirmDel"/>';    		
		$.messager.confirm(title, msg, function(r) {
			if (r) {
				deleteContentCharge();
			}
		});
    }
            
    function deleteContentCharge() {
    	var coId = $("#selCoId").combobox('getValue');	
    	var rowData = $("#contentChargeList").datagrid('getSelected');			    				    	
    
		var strUrl = "<c:out value="${contextPath}"/>/rest/contentCharge/delete/cntnId/" + cntnId + "/empNo/" + rowData.userId;
    	
    	$.ajax({
			type: 'DELETE',
			url: strUrl,						 				
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
					reload();
				});						
			},
			error : function(xhr, status, error) {
				console.log("error: " + status);
			}
		});
    }
    </script>
</head>
<body>
	<form id="roleUserManagerForm">	
	<table style="width:100%">
		<tr>
			<td colspan="2" style="width:100%">
				<div class="easyui-panel" title="" style="width:100%;max-width:100%;padding:5px 5px;">   
			    <select class="easyui-combobox" id="selCoId" name="coId" style="width:20%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:60">
			    	<c:forEach items="${coList}" var="opt" varStatus="st">
		            <option value="${opt.coId}" <c:if test="${opt.coId == userSession.coId}">selected="selected"</c:if> >${opt.coId}: ${opt.coName}</option>
		            </c:forEach>		            	
		        </select>
		        </div>
			</td>
		</tr>
		<tr>
			<td style="width:25%;">	
			<div>
       			<table id="contentList" class="easyui-datagrid" style="width:100%;height:580px;"		        
			       		title="<spring:message code="resc.label.cntnList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,pagination:false,autoRowHeight:false">
			        <thead>
			        	<tr>
			           		<th data-options="field:'coId',width:'20%',halign:'center',align:'center'"><spring:message code="resc.label.coId"/></th>
			        		<th data-options="field:'cntnId',width:'35%',halign:'center',align:'left'"><spring:message code="resc.label.cntnId"/></th>
			        		<th data-options="field:'cntnName',width:'45%',halign:'center',align:'left'"><spring:message code="resc.label.cntnName"/></th>			        					        					        		
			        	</tr>  
			        </thead>
			    </table>
			</div>    				    	
			</td>
			<td style="width:75%;">	
			<div>		
				<table id="contentChargeList" class="easyui-datagrid" style="width:100%;height:580px"		        
			       		title="<spring:message code="resc.label.contentChargeList"/>" 
			       		data-options="rownumbers:true,singleSelect:true,collapsible:false,toolbar:'#subCodeTb',pagination:false,autoRowHeight:false">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'cntnId',halign:'center',align:'center',width:'12%'"><spring:message code="resc.label.cntnId"/></th>			                
			                <th data-options="field:'cntnName',halign:'center',align:'left',width:'15%'"><spring:message code="resc.label.cntnName"/></th>
			                <th data-options="field:'userId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.userId"/></th>
			                <th data-options="field:'empName',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.empName"/></th>
			                <th data-options="field:'deptDesc',halign:'center',align:'left',width:'20%'"><spring:message code="resc.label.dept"/></th>
			                <th data-options="field:'dutyDesc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.dutyDesc"/></th>			                			                
			                <th data-options="field:'pstnDesc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.pstnDesc"/></th>			                
			            </tr>
			        </thead>
			    </table> 
			</div>   	
			    <div id="subCodeTb" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendContentCharge()"><spring:message code="resc.btn.add"/></a>			        
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitContentCharge()"><spring:message code="resc.btn.delete"/></a>			                
			    </div>						   
			</td>
		</tr>
	</table>	
	</div>	
	</form>
</body>
</html>
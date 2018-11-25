<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.apprMasterList"/></title>
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
    
    $(function(){    	
    	var coId = $("#selCoId").combobox('getValue');
    	url = "<c:out value="${contextPath}"/>/rest/approvalMaster/coId/" + coId + "/searchKind/apprMstId/searchWord/0/orderBy/apprMstId";	
    	$('#apprMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 사용자 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				var searchKind = "apprMstId";
				var searchWord = "0";
				var orderBy = "apprMstId";
				
				url = "<c:out value="${contextPath}"/>/rest/approvalMaster/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord + "/orderBy/" + orderBy;				
				$('#apprMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    		}
    	});    	        	
    });

    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
     	$('#apprMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
     }
    
    /*
     * 조직 구성원 정보를 가져온다.
    */
    function getData() {
    	getAjaxData(url);
    	return rows; 
    }
    
    /*
     * 등록된 조직 구성원 목록을 Ajax로 호출
    */
    function getAjaxData(url) {
    	rows = [];
   	    	
    	$.ajaxSetup({async: false});    	 
    	$.getJSON(url, function (data, status){
    		if (status == 'success') {    			 
    			$.each(data, function(index, entry) {
    				rows.push({
    					coId: entry["coId"],
    					apprMstId: entry["apprMstId"],
    					apprTmplType: entry["apprTmplType"],
    					apprDesc: entry["apprDesc"],
    					rcptIndc: entry["rcptIndc"],
    					rfncIndc: entry["rfncIndc"],
    					cprtnIndc: entry["cprtnIndc"],
    					atndIndc: entry["atndIndc"],
    					crcltnIndc: entry["crcltnIndc"],
    					apprLevel: entry["apprLevel"],
    					basePrsvTerm: entry["basePrsvTerm"],
    				});
    			});
    			
    			return rows;
    		} else {
    			return;
    		}
    	});
    }
    
    function pagerFilter(data){    	
		if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
			data = {
				total: data.length,
				rows: data
			}
		}
		
		var dg = $(this);
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage:function(pageNum, pageSize){
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh',{
					pageNumber:pageNum,
					pageSize:pageSize
				});
				dg.datagrid('loadData',data);
			},
			onRefresh:function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh',{
					pageNumber:pageNum,
					pageSize:pageSize
				});
				$("#apprMstList").datagrid("load", {});
				$('#apprMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
			}
		});
		if (!data.originalRows){
			data.originalRows = (data.rows);
		}
		var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = (data.originalRows.slice(start, end));
		
		return data;
	}
    </script>
</head>
<body>
	<form id="frmHr">
	<input type="hidden" id="mode" name="mode" value=""/>
	<input type="hidden" id="coId" name="coId" value=""/>	
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>			
	<table style="width:99%;height:89vh">
		<tr>
			<td colspan="2">
				<div id="tb" class="easyui-panel" title="" style="width:100%;max-width:100%;padding:5px 5px;"> 
					<select class="easyui-combobox" id="selCoId" name="selCoId" style="width:20%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:50">
			    	<c:forEach items="${coList}" var="opt" varStatus="st">
		            	<option value="${opt.coId}" <c:if test="${opt.coId == userSession.coId}">selected="selected"</c:if> >${opt.coId}: ${opt.coName}</option>
		            </c:forEach>		            	
		        	</select>		        			           
				</div>
			</td>
		</tr>
		<tr>					
			<td style="width:100%">				
			    <table id="apprMstList" class="easyui-datagrid" style="width:100%;height:100%" 		        
		       		title="<spring:message code="resc.label.apprMasterList"/>" 
		       		data-options="iconCls:'icon-edit', rownumbers:true, singleSelect:true, toolbar:'#tm', pagination:true, autoRowHeight:false, pageSize:20">
			        <thead>
			            <tr>
			            	<th data-options="field:'coId',halign:'center',align:'center',width:'5%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'apprMstId',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.apprMasterId"/></th>
			                <th data-options="field:'apprTmplType',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.apprTmplType"/></th>
			                <th data-options="field:'apprDesc',halign:'center',align:'left',width:'18%'"><spring:message code="resc.label.apprDesc"/></th>
			                <th data-options="field:'rcptIndc',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.rcptIndc"/></th>
			                <th data-options="field:'rfncIndc',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.rfncIndc"/></th>
			                <th data-options="field:'cprtnIndc',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.cprtnIndc"/></th>
			                <th data-options="field:'atndIndc',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.atndIndc"/></th>
			                <th data-options="field:'crcltnIndc',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.crcltnIndc"/></th>			                
			                <th data-options="field:'apprLevel',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.apprLevel"/></th>
			                <th data-options="field:'basePrsvTerm',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.prsvTerm"/></th>
			            </tr>
			        </thead>
		    	</table>    	
			    <div id="tm" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modify()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="resc.btn.delete"/></a>			               
			    </div>
			    <script type="text/javascript">
			    function append() {    	
			    	var coId = $("#selCoId").combobox('getValue');					    	
					var url = "<c:out value="${contextPath}"/>/admin/userAddForm?coId=" + coId;
								
					$.popupWindow(url, { name: 'addEmpPopup', height: 250, width: 750 });		    	
			    }
			    
			    function modify() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#apprMstList").datagrid('getSelected');			    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectEmp"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	var url = "<c:out value="${contextPath}"/>/admin/userModifyForm?coId=" + coId + "&userId=" + rowData.userId;
			    	$.popupWindow(url, { name: 'modifyEmpPopup', height: 250, width: 750 });	
			    }
			    
			    function removeit() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#apprMstList").datagrid('getSelected');		    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectEmp"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	msg = '<spring:message code="resc.msg.confirmDel"/>';    		
		    		$.messager.confirm(title, msg, function(r) {
		    			if (r) {
		    				deleteUserId();
		    			}
		    		});
			    }
			    
			    function deleteUserId() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#apprMstList").datagrid('getSelected');			    				    	
			    
					var strUrl = "<c:out value="${contextPath}"/>/rest/user/delete/coId/" + coId + "/userId/" + rowData.userId;
			    	
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
								refreshUserList();
							});						
						},
						error : function(xhr, status, error) {
							console.log("error: " + status);
						}
		    		});
			    }
			    
			    function refreshUserList() {
			    	var coId = $("#selCoId").combobox('getValue');			    	
			    	var searchKind = "apprMstId";
					var searchWord = "0";
					var orderBy = "apprMstId";
					
					url = "<c:out value="${contextPath}"/>/rest/approvalMaster/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord + "/orderBy/" + orderBy;
					$('#apprMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
			    }
			    </script>
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
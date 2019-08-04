<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title><spring:message code="resc.menu.roleMgr"/></title>
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
    	url = "<c:out value="${contextPath}"/>/rest/role/coId/" + coId;	
    	$('#roleList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 컨텐츠 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				
				url = "<c:out value="${contextPath}"/>/rest/role/coId/" + coId;				
				$('#roleList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    		}
    	});
    	
    	//Role별 사용자 현황
		addTabLayer("001", "<spring:message code='resc.label.roleUserMgr'/>", "<c:out value="${contextPath}"/>/admin/roleMgr/roleUserManagerForm");		
		
		$("#tabsLayer").tabs("select", '<spring:message code="resc.label.roleMgr"/>');
    });
        
    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
     	$('#roleList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
     }
    
    /*
     * 컨텐츠 정보를 가져온다.
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
    					role: entry["role"],
    					roleName: entry["roleName"],
    					useIndc: entry["useIndc"],
    					roleDetl: entry["roleDetl"],
    					crtId: entry["crtId"],
    					crtDate: entry["crtDate"],
    					chngId: entry["chngId"],
    					chngDate: entry["chngDate"]
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
				$("#roleList").datagrid("load", {});
				$('#roleList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
    
    function addTabLayer(tabId, tabName, src) {
    	var frameId = "listTabsFrame-" + tabId;
    	
    	if ($("#tabsLayer").tabs("exists", tabId)) {
    		$("#tabsLayer").tabs("close", tabId);
    	}
    	
    	var securityParam = "?_csrf=" + $('#_csrf').val();
    	if (src.indexOf('?') > -1) securityParam = "&_csrf=" + $('#_csrf').val();
    	var src = src + securityParam;    	
    	
    	var content = "<iframe name='" + frameId + "' src='" + src + "' id='" + frameId + "' frameborder='0' style='border:0;width:100%;height:98%;padding:10px 10px 0 0;' sandbox='allow-same-origin allow-scripts allow-popups allow-forms allow-top-navigation allow-pointer-lock' seamless='seamless'></iframe>";
    	
    	$("#tabsLayer").tabs("add", {
    		title: tabName,
    		content: content,    		
    		closable: false
    	});	
    }
    </script>
</head>
<body>
	<form id="roleMgrTabForm" method="post" action="">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>		
	<div id="tabsLayer" class="easyui-tabs" style="width:100%;height:710px;">
		<div class="noscroll" title="<spring:message code="resc.label.roleMgr"/>" style="padding:10px;display:none;">
			<table style="width:99%;height:80vh">
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
				    <table id="roleList" class="easyui-datagrid" style="width:100%;height:100%" 		        
				        title="<spring:message code="resc.label.role"/>" 
				        data-options="rownumbers:true, singleSelect:true, pagination:true, autoRowHeight:false, pageSize:20, toolbar:'#tm'">
				    <thead>
				        <tr>
				        	<th data-options="field:'coId',width:'8%',halign:'center',align:'center'"><spring:message code="resc.label.coId"/></th>
			        		<th data-options="field:'role',width:'8%',halign:'center',align:'left'"><spring:message code="resc.label.role"/></th>
			        		<th data-options="field:'roleName',width:'15%',halign:'center',align:'left'"><spring:message code="resc.label.roleName"/></th>			        		
			        		<th data-options="field:'roleDetl',width:'15%',halign:'center',align:'left'"><spring:message code="resc.label.roleDetl"/></th>
			        		<th data-options="field:'useIndc',width:'8%',halign:'center',align:'center'"><spring:message code="resc.label.useIndc"/></th>
			        		<th data-options="field:'crtId',width:'10%',halign:'center',align:'left'"><spring:message code="resc.label.crtId"/></th>
			        		<th data-options="field:'crtDate',width:'13%',halign:'center',align:'center',formatter:formatDate"><spring:message code="resc.label.crtDate"/></th>
			        		<th data-options="field:'chngId',width:'10%',halign:'center',align:'left'"><spring:message code="resc.label.chngId"/></th>
			        		<th data-options="field:'chngDate',width:'13%',halign:'center',align:'center',formatter:formatDate"><spring:message code="resc.label.chngDate"/></th>        		
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
					var url = "<c:out value="${contextPath}"/>/admin/roleMgr/roleAddForm?coId=" + coId;
					var cnt = ${fn:length(langList)};	
					var formHeight = 200 + (30 * cnt);
					
					$.popupWindow(url, { name: 'roleAddForm', height: formHeight, width: 750 });	
			    }
			    
			    function modify() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#roleList").datagrid('getSelected');			    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectRole"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	var url = "<c:out value="${contextPath}"/>/admin/roleMgr/roleModifyForm?coId=" + coId + "&role=" + rowData.role;
			    			
			    	var cnt = ${fn:length(langList)};	
					var formHeight = 200 + (30 * cnt);
					
			    	$.popupWindow(url, { name: 'contentModifyForm', height: formHeight, width: 750 });	
			    }
			    
			    function removeit() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#roleList").datagrid('getSelected');		    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectRole"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	msg = '<spring:message code="resc.msg.confirmDel"/>';    		
		    		$.messager.confirm(title, msg, function(r) {
		    			if (r) {
		    				deleteRole();
		    			}
		    		});
			    }
			    
			    function deleteRole() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#roleList").datagrid('getSelected');			    				    	
			    
					var strUrl = "<c:out value="${contextPath}"/>/rest/role/delete/coId/" + coId + "/role/" + rowData.role;
			    	
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
								refreshRoleList();
							});						
						},
						error : function(xhr, status, error) {
							console.log("error: " + status);
						}
		    		});
			    }
			    
			    function refreshRoleList() {
			    	var coId = $("#selCoId").combobox('getValue');			    	
			    	var searchKind = "boardName";
					var searchWord = "0";
					var orderBy = "boardMstName";
					
					url = "<c:out value="${contextPath}"/>/rest/role/coId/" + coId;
						
					$('#roleList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
			    }
			</script>
			    </td>
		    </tr>
		    </table>
		</div>
	</div>
	</form>	
</body>
</html>
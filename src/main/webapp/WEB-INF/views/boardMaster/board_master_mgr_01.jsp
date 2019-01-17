<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.boardMasterList"/></title>
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
    	url = "<c:out value="${contextPath}"/>/rest/boardMaster/coId/" + coId + "/searchKind/boardName/searchWord/0/orderBy/boardMstName";	
    	$('#boardMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 사용자 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				var searchKind = "boardName";
				var searchWord = "0";
				var orderBy = "boardMstName";
				
				url = "<c:out value="${contextPath}"/>/rest/boardMaster/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord + "/orderBy/" + orderBy;				
				$('#boardMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    		}
    	});    	        	
    });

    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
     	$('#boardMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
    					boardId: entry["boardId"],
    					boardTypeName: entry["boardTypeName"],
    					boardName: entry["boardName"],
    					rplyIndc: entry["rplyIndc"],
    					cmntIndc: entry["cmntIndc"],
    					atchIndc: entry["atchIndc"],
    					evalIndc: entry["evalIndc"],
    					anonyIndc: entry["anonyIndc"],
    					anceIndc: entry["anceIndc"],
    					apprIndc: entry["apprIndc"],
    					validTermIndc: entry["validTermIndc"]
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
				$("#boardMstList").datagrid("load", {});
				$('#boardMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
	<form id="frmHr" style="width:98%;">
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
			    <table id="boardMstList" class="easyui-datagrid" style="width:100%;height:100%" 		        
		       		title="<spring:message code="resc.label.boardMasterList"/>" 
		       		data-options="rownumbers:true, singleSelect:true, toolbar:'#tm', pagination:true, autoRowHeight:false, pageSize:20">
			        <thead>
			            <tr>
			            	<th data-options="field:'coId',halign:'center',align:'center',width:'6%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'boardId',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.boardId"/></th>			                
			                <th data-options="field:'boardTypeName',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.boardType"/></th>
			                <th data-options="field:'boardName',halign:'center',align:'left',width:'15%'"><spring:message code="resc.label.boardName"/></th>
			                <th data-options="field:'rplyIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.rplyIndc"/></th>
			                <th data-options="field:'cmntIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.cmntIndc"/></th>
			                <th data-options="field:'atchIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.atchIndc"/></th>
			                <th data-options="field:'evalIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.evalIndc"/></th>			                			                
			                <th data-options="field:'anonyIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.anonyIndc"/></th>
			                <th data-options="field:'anceIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.anceIndc"/></th>			            
			                <th data-options="field:'apprIndc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.apprIndc"/></th>    
			                <th data-options="field:'validTermIndc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.validTermIndc"/></th>
			            </tr>
			        </thead>
		    	</table>    	
			    <div id="tm" style="height:auto">    
			    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="retrieve()"><spring:message code="resc.btn.retrieve"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modify()"><spring:message code="resc.btn.modify"/></a>			        
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="resc.btn.delete"/></a>			               
			    </div>
			    <script type="text/javascript">
			    function retrieve() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#boardMstList").datagrid('getSelected');			    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectBoardMstId"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	var url = "<c:out value="${contextPath}"/>/admin/boardMasterMgr/boardMasterRetrieveForm?coId=" + coId + "&boardId=" + rowData.boardId;
			    	
			    	var cnt = ${fn:length(langList)};		
					var formHeight = 500 + (30 * cnt);
					
			    	$.popupWindow(url, { name: 'retrieveBoardMstForm', height: formHeight, width: 750 });
			    }
			    
			    function append() {    	
			    	var coId = $("#selCoId").combobox('getValue');					    	
					var url = "<c:out value="${contextPath}"/>/admin/boardMasterMgr/boardMasterAddForm?coId=" + coId;
					var cnt = ${fn:length(langList)};		
					var formHeight = 500 + (30 * cnt);
					
					$.popupWindow(url, { name: 'boardMstAddForm', height: formHeight, width: 800 });		    	
			    }
			    
			    function modify() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#boardMstList").datagrid('getSelected');			    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectBoardMstId"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	var url = "<c:out value="${contextPath}"/>/admin/boardMasterMgr/boardMasterModifyForm?coId=" + coId + "&boardId=" + rowData.boardId;
			    	
			    	var cnt = ${fn:length(langList)};		
					var formHeight = 500 + (30 * cnt);
					
			    	$.popupWindow(url, { name: 'modifyBoardMstForm', height: formHeight, width: 750 });	
			    }
			    
			    function removeit() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#boardMstList").datagrid('getSelected');		    	
			    	
			    	if (rowData == null) {
			    		var title = '<spring:message code="resc.label.confirm"/>';
			    		var msg = '<spring:message code="resc.msg.noSelectBoardMstId"/>';
			    		alertMsg(title, msg);
						return;
			    	}
			    	
			    	msg = '<spring:message code="resc.msg.confirmDel"/>';    		
		    		$.messager.confirm(title, msg, function(r) {
		    			if (r) {
		    				deleteBoardMstId();
		    			}
		    		});
			    }
			    
			    function deleteBoardMstId() {
			    	var coId = $("#selCoId").combobox('getValue');	
			    	var rowData = $("#boardMstList").datagrid('getSelected');			    				    	
			    
					var strUrl = "<c:out value="${contextPath}"/>/rest/boardMaster/delete/coId/" + coId + "/boardId/" + rowData.boardId;
			    	
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
								refreshBoardMstList();
							});						
						},
						error : function(xhr, status, error) {
							console.log("error: " + status);
						}
		    		});
			    }
			    
			    function refreshBoardMstList() {
			    	var coId = $("#selCoId").combobox('getValue');			    	
			    	var searchKind = "boardName";
					var searchWord = "0";
					var orderBy = "boardMstName";
					
					url = "<c:out value="${contextPath}"/>/rest/boardMaster/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord + "/orderBy/" + orderBy;					
					$('#boardMstList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
			    }
			    </script>
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
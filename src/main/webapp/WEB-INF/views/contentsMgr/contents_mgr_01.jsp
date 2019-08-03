<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.cntnList"/></title>
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
    	url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/cntnName/searchWord/0/orderBy/cntnId";	
    	$('#contentList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    	
    	//회사 선택이 변경될 경우 컨텐츠 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			var coId = $("#selCoId").combobox('getValue');
				var searchKind = "cntnName";
				var searchWord = "0";
				var orderBy = "cntnId";
				
				url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord + "/orderBy/" + orderBy;				
				$('#contentList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    		}
    	});   
    	    	
    	//Role별 사용자 현황
		addTabLayer("001", "<spring:message code='resc.label.contentsRightsMgr'/>", "<c:out value="${contextPath}"/>/admin/contentsMgr/contentRightsMgr");
		$("#tabsLayer").tabs("select", '<spring:message code="resc.label.contentsMgr"/>');
    });

    /**
     * 팝업창에서 호출하기 위한 함수(refresh)
     */
     function reload() {
     	$('#contentList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
    					cntnId: entry["cntnId"],
    					cntnName: entry["cntnName"],
    					cntnOfferType: entry["cntnOfferType"],
    					cntnOfferTypeDesc: entry["cntnOfferTypeDesc"],
    					linkSysHost: entry["linkSysHost"],
    					url: entry["url"],
    					cntnType: entry["cntnType"],
    					cntnTypeDesc: entry["cntnTypeDesc"],
    					masterId: entry["masterId"],
    					cntnJointFlag: entry["cntnJointFlag"],
    					cntnDesc: entry["cntnDesc"],
    					portNo: entry["portNo"]
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
				$("#contentList").datagrid("load", {});
				$('#contentList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
	<form id="frmHr" style="width:98%;">
	<input type="hidden" id="mode" name="mode" value=""/>
	<input type="hidden" id="coId" name="coId" value=""/>	
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>
	<div id="tabsLayer" class="easyui-tabs" style="width:100%;height:710px;">			
		<div class="noscroll" title="<spring:message code="resc.label.contentsMgr"/>" style="padding:10px;display:none;">
			<table style="width:100%;height:80vh">
				<tr>
					<td style="width:100%" colspan="2">
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
					    <table id="contentList" class="easyui-datagrid" style="width:100%;height:100%" 		        
				       		title="<spring:message code="resc.label.cntnList"/>" 
				       		data-options="rownumbers:true, singleSelect:true, toolbar:'#tm', pagination:true, autoRowHeight:false, pageSize:20">
					        <thead>
					            <tr>
					            	<th data-options="field:'coId',halign:'center',align:'center',width:'5%'"><spring:message code="resc.label.coId"/></th>
					                <th data-options="field:'cntnId',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.cntnId"/></th>			                
					                <th data-options="field:'cntnName',halign:'center',align:'left',width:'10%'"><spring:message code="resc.label.cntnName"/></th>
					                <th data-options="field:'cntnOfferTypeDesc',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.cntnOfferType"/></th>
					                <th data-options="field:'linkSysHost',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.linkSystemHostname"/></th>
					                <th data-options="field:'url',halign:'center',align:'center',width:'14%'"><spring:message code="resc.label.url"/></th>
					                <th data-options="field:'cntnTypeDesc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.cntnType"/></th>
					                <th data-options="field:'masterId',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.masterId"/></th>			                			                
					                <th data-options="field:'cntnJointFlag',halign:'center',align:'center',width:'10%'"><spring:message code="resc.label.cntnJointFlag"/></th>
					                <th data-options="field:'cntnDesc',halign:'center',align:'left',width:'12%'"><spring:message code="resc.label.cntnDesc"/></th>			            
					                <th data-options="field:'portNo',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.portNo"/></th>    			                
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
					    	var rowData = $("#contentList").datagrid('getSelected');			    	
					    	
					    	if (rowData == null) {
					    		var title = '<spring:message code="resc.label.confirm"/>';
					    		var msg = '<spring:message code="resc.msg.noSelectContentId"/>';
					    		alertMsg(title, msg);
								return;
					    	}
					    	
					    	var url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentRetrieveForm?coId=" + coId + "&cntnId=" + rowData.cntnId;
					    	
					    	var cnt = ${fn:length(langList)};		
							var formHeight = 340 + (30 * cnt);
							
					    	$.popupWindow(url, { name: 'retrieveContentForm', height: formHeight, width: 750 });
					    }
					    
					    function append() {    	
					    	var coId = $("#selCoId").combobox('getValue');					    	
							var url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentAddForm?coId=" + coId;
							var cnt = ${fn:length(langList)};		
							var formHeight = 350 + (30 * cnt);
							
							$.popupWindow(url, { name: 'contentAddForm', height: formHeight, width: 750 });		    	
					    }
					    
					    function modify() {
					    	var coId = $("#selCoId").combobox('getValue');	
					    	var rowData = $("#contentList").datagrid('getSelected');			    	
					    	
					    	if (rowData == null) {
					    		var title = '<spring:message code="resc.label.confirm"/>';
					    		var msg = '<spring:message code="resc.msg.noSelectContentId"/>';
					    		alertMsg(title, msg);
								return;
					    	}
					    	
					    	var url = "<c:out value="${contextPath}"/>/admin/contentsMgr/contentModifyForm?coId=" + coId + "&cntnId=" + rowData.cntnId;
					    	
					    	var cnt = ${fn:length(langList)};		
							var formHeight = 350 + (30 * cnt);
							
					    	$.popupWindow(url, { name: 'contentModifyForm', height: formHeight, width: 750 });	
					    }
					    
					    function removeit() {
					    	var coId = $("#selCoId").combobox('getValue');	
					    	var rowData = $("#contentList").datagrid('getSelected');		    	
					    	
					    	if (rowData == null) {
					    		var title = '<spring:message code="resc.label.confirm"/>';
					    		var msg = '<spring:message code="resc.msg.noSelectContentId"/>';
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
					    	var rowData = $("#contentList").datagrid('getSelected');			    				    	
					    
							var strUrl = "<c:out value="${contextPath}"/>/rest/contents/delete/cntnId/" + rowData.cntnId;
					    	
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
										refreshContentList();
									});						
								},
								error : function(xhr, status, error) {
									console.log("error: " + status);
								}
				    		});
					    }
					    
					    function refreshContentList() {
					    	var coId = $("#selCoId").combobox('getValue');			    	
					    	var searchKind = "boardName";
							var searchWord = "0";
							var orderBy = "boardMstName";
							
							url = "<c:out value="${contextPath}"/>/rest/contents/coId/" + coId + "/searchKind/" + searchKind + "/searchWord/" + searchWord + "/orderBy/" + orderBy;
								
							$('#contentList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
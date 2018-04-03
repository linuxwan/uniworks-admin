<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.menu.hrMgr"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>

    <script type="text/javascript">	        
    var url = "";
    
    $(function(){    	
    	var coId = $("#selCoId").combobox('getValue');
    	getOganList('oganTree', coId);
    	
    	//회사 선택이 변경될 경우 조직도를 다시 그린다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			$('#coId').val($("#selCoId").combobox('getValue'));
    			getOganList('oganTree', newValue);
    		}
    	});
    	    
    	//조직도 클릭 이벤트
    	$("#oganTree").tree({
    		onClick: function(node) {
    			var coId = $("#selCoId").combobox('getValue');
				var oganCode = node.id;
				var oganLev = node.oganLev;				
				url = "<c:out value="${contextPath}"/>/rest/hr/coId/" + coId + "/oganLev/" + oganLev + "/oganCode/" + oganCode + "/workIndc/1";				
				$('#oganMemberList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
    		}
    	});      	    	
    });
        
    /**
     * 조직도를 그리기 위한 ajax 호출 함수
     */
    function getOganList(oganTree, coId) {
    	$("#" + oganTree).tree({
    		url: '<c:out value="${contextPath}"/>/rest/ogan?coId=' + coId,
    		method: "get",
    		animation: true,
    		cascadeCheck: true,
    		loadFilter: function(rows) {
    			return convertOganList(rows);
    		}
    	});	
    }

    /**
     * easyui tree를 Dispaly한다.
     * @param rows
     * @returns {Array}
     */
    function convertOganList(rows) {
    	function exists(rows, parentId, parentLev) {		
    		for (var i = 0; i < rows.length; i++) {
    			if (rows[i].oganCode == parentId && rows[i].oganLev == (parentLev + 1)) {
    				return true;
    			}
    			return false;
    		}
    	}

    	var nodes = [];
    	// get the top level nodes	
    	for (var i = 0; i < rows.length; i++) {
    		var row = rows[i];    		    		
    		
    		if (!exists(rows, row.highOganCode, row.highOganLev) && (row.oganLev == 0 || row.oganLev == 1)) {
    			nodes.push({
    				id: row.oganCode,
    				text: row.oganName,
    				oganLev:row.oganLev
    			});
    		}
    	}	

    	var toDo = [];
    	for(var i = 0; i < nodes.length; i++){
            toDo.push(nodes[i]);
        }
    		
        while(toDo.length){
            var node = toDo.shift();    // the parent node
            // get the children nodes
            for(var i=0; i<rows.length; i++){
                var row = rows[i];            
                if (row.highOganCode == node.id) { //&& (row.parentLev + 1) == node.oganLev){
                    var child = "";
                    child = {id:row.oganCode,text:row.oganName,oganLev:row.oganLev};
                    if (node.children){
                        node.children.push(child);
                    } else {
                        node.children = [child];
                    }
                    toDo.push(child);
                }
            }
        }
            
        return nodes;
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
    					empNo: entry["empNo"],
    					empNameKor: entry["empNameKor"],
    					deptDesc: entry["deptDesc"],
    					dutyDesc: entry["dutyDesc"],
    					pstnDesc: entry["pstnDesc"],
    					entrDate: entry["entrDate"],
    					offcTelNo: entry["offcTelNo"],
    					mailAddr: entry["mailAddr"]
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
				$("#oganMemberList").datagrid("load", {});
				$('#oganMemberList').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
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
	<table style="width:99%">
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
			<td style="width:25%">									      		
				<div class="easyui-panel" title="<spring:message code="resc.label.oganChart"/>" style="padding:10px;height:675px">		
					<ul id="oganTree" class="easyui-tree" style="width:100%;"></ul>
       			</div>
			</td>
			<td style="width:75%">				
			    <table id="oganMemberList" class="easyui-datagrid" style="width:100%;height:675px" 		        
		       		title="<spring:message code="resc.label.oganMember"/>" 
		       		data-options="iconCls:'icon-edit', rownumbers:true, singleSelect:true, toolbar:'#tm', pagination:true, autoRowHeight:false, pageSize:20">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'empNo',halign:'center',align:'center',width:'9%'"><spring:message code="resc.label.empNo"/></th>
			                <th data-options="field:'empNameKor',halign:'center',align:'center',width:'9%'"><spring:message code="resc.label.empName"/></th>
			                <th data-options="field:'deptDesc',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.deptDesc"/></th>
			                <th data-options="field:'dutyDesc',halign:'center',align:'center',width:'7%'"><spring:message code="resc.label.dutyDesc"/></th>
			                <th data-options="field:'pstnDesc',halign:'center',align:'center',width:'8%'"><spring:message code="resc.label.pstnDesc"/></th>
			                <th data-options="field:'entrDate',halign:'center',align:'center',width:'10%', formatter:formatDateYYYYMMDD"><spring:message code="resc.label.entrDate"/></th>
			                <th data-options="field:'offcTelNo',halign:'center',align:'center',width:'15%'"><spring:message code="resc.label.offcTelNo"/></th>			                
			                <th data-options="field:'mailAddr',halign:'center',align:'center',width:'20%'"><spring:message code="resc.label.mailAddr"/></th>
			            </tr>
			        </thead>
		    	</table>    	
			    <div id="tm" style="height:auto">    
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="resc.btn.add"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modify()"><spring:message code="resc.btn.modify"/></a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="resc.btn.delete"/></a>			               
			    </div>	
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
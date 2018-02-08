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

    <script type="text/javascript">	
    $(function(){    	
    	var coId = $("#selCoId").combobox('getValue');
    	getOganList('oganTree', coId);
    	
    	//회사 선택이 변경될 경우 조직도를 다시 그린다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			getOganList('oganTree', newValue);
    		}
    	});
    	
    	//조직도 클릭 이벤트
    	$("#oganTree").tree({
    		onClick: function(node) {
				var oganCode = node.id;
				var oganLev = node.oganLev;

    		}
    	});  
    	
    	//저장, 초기화 버튼 비활성화
    	$('#btnClear').linkbutton('disable');
    	$('#btnSave').linkbutton('disable');
    	
    	//등록 버튼 클릭 시
    	$('#btnAdd').bind('click', function(){
    		formReset();
    		var root = $('#oganTree').tree('getRoot');    
    		if (root == null || root == undefined) {
    			$('#highOganCode').textbox('setValue', "root");
    			$('#highOganLev').textbox('setValue', "0");
    		} else {
    			var node = $('#oganTree').tree("getSelected");
    			$('#highOganCode').textbox('setValue', node.id);
    			$('#highOganLev').textbox('setValue', node.oganLev);
    		}    		
    	});
    });
    
    /*
     * form 내의 입력 관련 Object를 리셋한다.
    */
    function formReset() {
    	$('#coId').textbox('setValue', "");
    	$('#oganCode').textbox('setValue', "");
    	$('#oganLev').textbox('setValue', "");
    	$('#oganEstbDate').textbox('setValue', "");
    	$('#oganName').textbox('setValue', "");
    	$('#rescKey').textbox('setValue', "");
    	$('#oganClsDate').textbox('setValue', "9999-12-31");
    	$('#highOganCode').textbox('setValue', "");
    	$('#highOganLev').textbox('setValue', "");
    	$('#oganType').combobox('setValue', "01");
    	$('#oganDesc').textbox('setValue', "");
    }
    
    /**
     * 조직도를 그리기 위한 ajax 호출 함수
     */
    function getOganList(oganTree, coId) {
    	$("#" + oganTree).tree({
    		url: '<c:out value="${contextPath}"/>/rest/oganList?coId=' + coId,
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
    </script>
</head>
<body>
	<input type="hidden" id="mode" name="mode" value=""/>			
	<table style="width:99%">
		<tr>
			<td colspan="2">
				<div id="tb" class="easyui-panel" title="" style="width:100%;max-width:100%;padding:5px 5px;"> 
					<select class="easyui-combobox" id="selCoId" name="selCoId" style="width:20%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:50">
			    	<c:forEach items="${coList}" var="opt" varStatus="st">
		            	<option value="${opt.coId}" <c:if test="${opt.coId == userSession.coId}">selected="selected"</c:if> >${opt.coId}: ${opt.coName}</option>
		            </c:forEach>		            	
		        	</select>
		            
					<a href="#" id="btnAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"><spring:message code="resc.btn.enrollment"/></a>
					<a href="#" id="btnModify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"><spring:message code="resc.btn.modify"/></a>			
		        	<a href="#" id="btnDelete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"><spring:message code="resc.btn.delete"/></a>
				</div>
			</td>
		</tr>
		<tr>		
			<td style="width:40%">									      		
				<div class="easyui-panel" title="<spring:message code="resc.label.oganChart"/>" style="padding:10px;height:675px" data-options="toolbor:'#tb'">		
					<ul id="oganTree" class="easyui-tree" style="width:100%;"></ul>
       			</div>
			</td>
			<td style="width:60%">
				<div class="easyui-panel" title='<spring:message code="resc.label.oganDesc"/>' style="padding:10px;height:675px">
			        <form id="frmCompany">			
			        <jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>        
			        <table style="width:100%">			        			        		       
			        <tr>
			        	<td style="width:50%;padding:0px 10px;">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="highOganCode" name="highOganCode" style="width:100%" data-options="label:'<spring:message code="resc.label.highOganCode"/>:',readonly:true,required:true,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="highOganLev" name="highOganLev" style="width:100%" data-options="label:'<spring:message code="resc.label.highOganLev"/>:',readonly:true,required:true,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			        	<td style="width:50%;padding:0px 10px;">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="oganCode" name="oganCode" style="width:100%" data-options="label:'<spring:message code="resc.label.oganCode"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="oganLev" name="oganLev" style="width:100%" data-options="label:'<spring:message code="resc.label.oganLev"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-datebox" id="oganEstbDate" name="oganEstbDate" style="width:100%" data-options="label:'<spring:message code="resc.label.oganEstbDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-datebox" id="oganClsDate" name="oganClsDate" style="width:100%" data-options="label:'<spring:message code="resc.label.oganClsDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
				            </div>
			            </td>
			        </tr>			        
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="rescKey" name="rescKey" style="width:100%" data-options="label:'<spring:message code="resc.label.rescKey"/>:',readonly:true,required:true,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px"> 
								<select class="easyui-combobox" id="oganType" name="oganType" style="width:100%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.oganType"/>:',labelWidth:100">
						    	<c:forEach items="${oganTypeList}" var="type" varStatus="st">
					            	<option value="${type.subCode}" <c:if test="${type.subCode == selSubCode}">selected="selected"</c:if> >${type.subCode}: ${type.rescKeyValue}</option>
					            </c:forEach>		            	
					        	</select>					            
							</div>
			            </td>
			        </tr>
			        </table>
			        </form>
			        <div style="text-align:center;padding:5px 0">
			            <a href="javascript:void(0)" id="btnSave" class="easyui-linkbutton" onclick="submitForm()" style="width:80px"><spring:message code="resc.btn.save"/></a>			            
			            <a href="javascript:void(0)" id="btnClear" class="easyui-linkbutton" onclick="formReset()" style="width:80px"><spring:message code="resc.btn.clear"/></a>
			        </div>
			    </div>
			</td>
		</tr>
	</table>	
</body>
</html>
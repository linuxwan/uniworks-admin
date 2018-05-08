<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.selectOgan"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
    <script type="text/javascript">
    var oganCode ="";
	var oganLev = "";
	var oganName = "";
	
    $(function(){    
	    var coId = '${coId}';
	    var targetObj = '${targetObj}';
		getOganList('oganTree', coId);			
		
		//조직도 클릭 이벤트
		$("#oganTree").tree({
			onClick: function(node) {				
				oganCode = node.id;
				oganLev = node.oganLev;		
				oganName = node.text;				
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
    
    //조직선택 버튼 클릭 Event
    function selectOgan(targetObj) {
    	window.opener.callBackOganInfo(targetObj, oganLev, oganCode, oganName);
    	window.close();
    }
    </script>
</head>
<body>
	<form id="frmOgan">
	<input type="hidden" id="mode" name="mode" value=""/>
	<input type="hidden" id="coId" name="coId" value=""/>
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>			
	<table style="width:100%">
	<tr>		
		<td style="width:100%">									      		
			<div class="easyui-panel" title="<spring:message code="resc.label.oganChart"/>" style="padding:10px;height:440px" data-options="toolbor:'#tb'">		
				<ul id="oganTree" class="easyui-tree" style="width:100%;"></ul>
      			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div style="text-align:center;padding:5px 0">
	            <a href="javascript:void(0)" id="btnSelect" class="easyui-linkbutton" onclick="selectOgan('${targetObj}')" style="width:80px"><spring:message code="resc.btn.selectOgan"/></a>			            
	            <a href="javascript:void(0)" id="btnCancel" class="easyui-linkbutton" onclick="window.close()" style="width:80px"><spring:message code="resc.btn.cancel"/></a>
			</div>
		</td>
	</tr>
	</table>
	</form>
</body>
</html>
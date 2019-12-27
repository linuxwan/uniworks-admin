<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="resc.label.menuMgr"/></title>
	<link rel='shortcut icon' type='image/x-icon' href='<c:out value="${contextPath}"/>/image/testimonials.png' />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/easyui/css/themes/icon.css">    
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/locale/easyui-lang-${userSession.lang}.js"></script>
    <script type="text/javascript" src="<c:out value="${contextPath}"/>/easyui/js/common.js"></script>
	<script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.serializeObject.js"></script>
    <script type="text/javascript">
    var coId = '';
    $(function(){    	
    	coId = $("#selCoId").combobox('getValue');
    	var strUrl = '<c:out value="${contextPath}"/>/rest/menuList/coId/' + coId;
		$('#menuTree').treegrid('options').url = strUrl;
		$('#menuTree').treegrid('load');  
		
    	//회사 선택이 변경될 경우 컨텐츠 목록을 새롭게 가져온다.
    	$('#selCoId').combobox({
    		onChange(newValue, oldValue) {
    			coId = $("#selCoId").combobox('getValue');
    			strUrl = '<c:out value="${contextPath}"/>/rest/menuList/coId/' + coId;
    			$('#menuTree').treegrid('options').url = strUrl;
    			$('#menuTree').treegrid('reload');    			
    		}
    	});
    	
    	$("#menuTree").treegrid({
    		onClickRow: function(rowData) {

    		}
    	});  
    });
    
    function convert(rows){
        function exists(rows, _parentId){        	
            for(var i=0; i<rows.length; i++){            	
                if (rows[i].menuId == _parentId) return true;
            }
            return false;
        }
        
        var nodes = [];
        // get the top level nodes
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (!exists(rows, row.highMenuId)){
                nodes.push({
                    menuId: row.menuId,
                    highMenuId: row.highMenuId,
                    menuDsplName: row.menuDsplName,                    
                    menuLevel: row.menuLevel,
                    menuOrd: row.menuOrd,
                    cntnId: row.cntnId,
                    menuDsplIndc: row.menuDsplIndc,
                    cntnLinkIndc: row.cntnLinkIndc,
                    dfltMenuIndc: row.dfltMenuIndc,
                    dlgtMenuIndc: row.dlgtMenuIndc,
                    myMenuSetIndc: row.myMenuSetIndc
                });
            }
        }
        
        var toDo = [];
        for(var i=0; i<nodes.length; i++){
            toDo.push(nodes[i]);
        }
        while(toDo.length){
            var node = toDo.shift();    // the parent node
            // get the children nodes
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                if (row.highMenuId == node.menuId){
                    var child = {menuId:row.menuId,highMenuId:row.highMenuId,menuDsplName:row.menuDsplName,menuLevel:row.menuLevel,menuOrd:row.menuOrd,cntnId:row.cntnId,
                    		     menuDsplIndc:row.menuDsplIndc,cntnLinkIndc:row.cntnLinkIndc,dfltMenuIndc:row.dfltMenuIndc,dlgtMenuIndc:row.dlgtMenuIndc,myMenuSetIndc:row.myMenuSetIndc};
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
	<form id="frmMenuMgr" style="width:98%;">
	<jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>   			
	<table style="width:100%">
		<tr>
			<td style="width:100%">				
       			<table id="menuTree" title="<spring:message code="resc.label.menuMgr"/>" class="easyui-treegrid" style="width:100%;height:720px"
			            data-options="			                
			                lines: false,
			                rownumbers: true,
			                fitColumns: true,
			                toolbar:'#tb',			                
			                method: 'get',
			                idField: 'menuId',
			                treeField: 'menuDsplName',
			                loadFilter: function(rows) {
			                	return convert(rows);
			               	}
			            ">
			        <thead>
			            <tr>
			            	<th data-options="field:'menuDsplName',halign:'center',align:'left',width:150"><spring:message code="resc.label.menuDsplName"/></th>
			                <th data-options="field:'menuId',halign:'center',align:'center',width:60"><spring:message code="resc.label.menuId"/></th>
			                <th data-options="field:'cntnId',halign:'center',align:'center',width:60"><spring:message code="resc.label.cntnId"/></th>
			                <th data-options="field:'highMenuId',halign:'center',align:'center',width:60"><spring:message code="resc.label.highMenuId"/></th>			                
			                <th data-options="field:'menuLevel',halign:'center',align:'center',width:50"><spring:message code="resc.label.menuLevel"/></th>
			                <th data-options="field:'menuOrd',halign:'center',align:'center',width:50"><spring:message code="resc.label.menuOrd"/></th>
			                <th data-options="field:'menuDsplIndc',halign:'center',align:'center',width:80"><spring:message code="resc.label.menuDsplIndc"/></th>
			                <th data-options="field:'cntnLinkIndc',halign:'center',align:'center',width:80"><spring:message code="resc.label.cntnlinkIndc"/></th>
			                <th data-options="field:'dfltMenuIndc',halign:'center',align:'center',width:80"><spring:message code="resc.label.dfltMenuIndc"/></th>
			                <th data-options="field:'dlgtMenuIndc',halign:'center',align:'center',width:80"><spring:message code="resc.label.dlgtMenuIndc"/></th>
			                <th data-options="field:'myMenuSetIndc',halign:'center',align:'center',width:80"><spring:message code="resc.label.myMenuSetIndc"/></th>
			            </tr>
			        </thead>
			    </table>  
			    <div id="tb" style="height:auto">    			    	
					<select class="easyui-combobox" id="selCoId" name="selCoId" style="width:20%;" data-options="panelHeight:'auto',label:'<spring:message code="resc.label.coId"/>:',labelWidth:60">
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
	</table>
	</form>	
</body>
</html>
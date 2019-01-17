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
    			$('#coId').val($("#selCoId").combobox('getValue'));
    			getOganList('oganTree', newValue);
    			formReset();
    		}
    	});
    	
    	//조직 코드 입력 후 focus를 잃었을 때 rescKey 값을 설정
    	var oganCode = $('#oganCode'); 
    	oganCode.textbox('textbox').bind('blur', function(e) {
    		var strRescKey = $('#highOganCode').val() + "." + $('#oganCode').val();    		
        	$('#rescKey').textbox('setValue', strRescKey);
    	});
    	
    	//조직도 클릭 이벤트
    	$("#oganTree").tree({
    		onClick: function(node) {
    			var coId = $("#selCoId").combobox('getValue');
				var oganCode = node.id;
				var oganLev = node.oganLev;
				getOganInfo(coId, oganCode, oganLev);
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
    			$('#oganCode').textbox('setValue', $("#selCoId").combobox('getValue'));
    			$('#oganLev').textbox('setValue', "1");
    			$('#oganCode').textbox('textbox').focus();
    			setReadOnly(false);
    			$('#oganCode').textbox('readonly', true);
    	    	$('#oganLev').textbox('readonly', true);
    		} else {
    			var node = $('#oganTree').tree("getSelected");    			    			
        		
        		if (node == null) {
        			var title = '<spring:message code="resc.label.confirm"/>';
        			var msg = '<spring:message code="resc.msg.selHighOgan"/>';
        			
        			alertMsg(title, msg);
        			return;
        		}
    			
    			$('#highOganCode').textbox('setValue', node.id);
    			$('#highOganLev').textbox('setValue', node.oganLev);
    			setReadOnly(false);
    		}    
    		
    		$('#mode').val('ADD'); 
    		$('#btnSave').linkbutton('enable');
    		$('#btnClear').linkbutton('enable');
    	});
    	
    	//수정 버튼 클릭 시
    	$('#btnModify').bind('click', function(){
    		var node = $('#oganTree').tree("getSelected");
    		if (node == null) {
    			var title = '<spring:message code="resc.label.confirm"/>';
    			var msg = '<spring:message code="resc.msg.noSelectOgan"/>';
    			
    			alertMsg(title, msg);
    			return;
    		}
    		
    		setReadOnly(false);
   			$('#highOganCode').textbox('readonly', true);
   	     	$('#highOganLev').textbox('readonly', true);
   	    	$('#oganCode').textbox('readonly', true);
   	    	$('#oganLev').textbox('readonly', true); 
   	    	
   	    	$('#mode').val('MODIFY'); 
    		$('#btnSave').linkbutton('enable');
    	});
    	
    	//삭제 버튼 클릭 시
    	$('#btnDelete').bind('click', function(){
    		var rowData = $("#oganTree").tree('getSelected');
    		var title = '<spring:message code="resc.label.confirm"/>';
    		var msg = '<spring:message code="resc.msg.selectOgan"/>';
    		if ($('#mode').val() != '' || rowData == null) {    			    			    			
    			alertMsg(title, msg);
    			return;
    		}
    		
    		// 하위 노드가 있는지 확인. 있으면 배열이 리턴
    		var childNode = $("#oganTree").tree('getChildren', rowData.target); 
    		// childNode 배열의 개수가 1보다 작을 경우, 하위 노드가 없음
    		if (childNode.length < 1) {    		
    			msg = '<spring:message code="resc.msg.confirmDel"/>';    		
	    		$.messager.confirm(title, msg, function(r) {
	    			if (r) {
	    				deleteOgan();
	    			}
	    		});
    		} else { //하위 노드가 있을 경우
    			msg = '<spring:message code="resc.msg.subOganExist"/>';
    			alertMsg(title, msg);
    			return;
    		}
    	});
    });
    
    /*
     * 조직 정보를 삭제한다.
     */
    function deleteOgan() {
    	var rowData = $("#oganTree").tree('getSelected');
    	var coId = $("#selCoId").combobox('getValue');    	    	
    	var strUrl = "<c:out value="${contextPath}"/>/rest/ogan/delete/coId/" + coId + "/oganCode/" + rowData.id + "/oganLev/" + rowData.oganLev;
    	
    	$.ajax({
			type: 'DELETE',
			url: strUrl,					
			beforeSend: function(xhr) {
				//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},  				
			success : function(msg) {
				var title = '<spring:message code="resc.label.confirm"/>';		    			
				$.messager.alert(title, msg, "info",  function(){
					$("#oganTree").tree('reload');	
					formReset();
					//저장, 초기화 버튼 비활성화
			    	$('#btnClear').linkbutton('disable');
			    	$('#btnSave').linkbutton('disable');
				});								
			},
			error : function(xhr, status, error) {
				console.log("error: " + status);
			}
		});
    }
    
    /*
     * form 내의 입력 관련 Object를 리셋한다.
    */
    function formReset() {
    	$('#mode').val("");
    	$('#oganCode').textbox('setValue', "");
    	$('#oganLev').textbox('setValue', "");
    	$('#oganEstbDate').textbox('setValue', "");
    	$('#rescKey').textbox('setValue', "");
    	$('#oganClsDate').textbox('setValue', "9999-12-31");
    	$('#highOganCode').textbox('setValue', "");
    	$('#highOganLev').textbox('setValue', "");
    	$('#oganType').combobox('setValue', "01");
    	
    	<c:forEach items="${langList}" var="lang">	
    	$('#oganName_' + '${lang.rescKeyValue}').textbox('setValue', "");
    	$('#oganDesc_' + '${lang.rescKeyValue}').textbox('setValue', "");
    	</c:forEach>    	    	
    }
    
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
     * 조직 정보를 가져온다.
    */
    function getOganInfo(coId, oganCode, oganLev) {
    	var url = "<c:out value="${contextPath}"/>/rest/ogan/oganInfo/coId/" + coId + "/oganCode/" + oganCode + "/oganLev/" + oganLev;
    	$.getJSON(url, function (data, status){
    		if (status == 'success') {    		
    			setOganInfo(data);
    			setReadOnly(true);
    		} else {
    			return;
    		}
    	});
    }
    
    /*
     * Object를 readonly 여부를 결정. true: readonly
    */    
    function setReadOnly(readOnlyIndc) {
    	$('#highOganCode').textbox('readonly', true);
     	$('#highOganLev').textbox('readonly', true);
    	$('#oganCode').textbox('readonly', readOnlyIndc);
    	$('#oganLev').textbox('readonly', readOnlyIndc);
    	$('#oganEstbDate').textbox('readonly', readOnlyIndc);
    	$('#rescKey').textbox('readonly', readOnlyIndc);
    	$('#oganClsDate').textbox('readonly', readOnlyIndc);    	
    	$('#oganType').combobox('readonly', readOnlyIndc);    	
    	<c:forEach items="${langList}" var="lang">	
    	$('#oganName_' + '${lang.rescKeyValue}').textbox('readonly', readOnlyIndc);
    	$('#oganDesc_' + '${lang.rescKeyValue}').textbox('readonly', readOnlyIndc);
    	</c:forEach>
    }
    
    /*
     * 가지고온 조직 정보를 Object에 셋팅
    */
    function setOganInfo(oganInfoList) {
    	var count = 0;
    	$.each(oganInfoList, function(index, entry) {
			if (count == 0) {
				$('#highOganCode').textbox('setValue', entry["highOganCode"]);
				$('#highOganLev').textbox('setValue', entry["highOganLev"]);
				$('#oganCode').textbox('setValue', entry["oganCode"]);
				$('#oganLev').textbox('setValue', entry["oganLev"]);
				$('#oganEstbDate').textbox('setValue', dateBoxformatDate(entry["oganEstbDate"]));
				$('#oganClsDate').textbox('setValue', dateBoxformatDate(entry["oganClsDate"]));
				$('#rescKey').textbox('setValue', entry["rescKey"]);
				$('#oganType').combobox('setValue', entry["oganType"]);
				$('#highOganLev').textbox('setValue', entry["highOganLev"]);
				var lang = entry["lang"];
				$('#oganName_' + lang).textbox('setValue', entry["oganName"]);
				$('#oganName_' + lang).textbox('readonly', 'true');
				$('#oganDesc_' + lang).textbox('setValue', entry["oganDesc"]);
				$('#oganDesc_' + lang).textbox('readonly', 'true');
			} else {
				var lang = entry["lang"];
				$('#oganName_' + lang).textbox('setValue', entry["oganName"]);
				$('#oganName_' + lang).textbox('readonly', 'true');
				$('#oganDesc_' + lang).textbox('setValue', entry["oganDesc"]);
				$('#oganDesc_' + lang).textbox('readonly', 'true');
			}	
			count++;
		});
    }  
    
    /*
     * form submit 시 add/modify를 구분해서 처리한다.
    */
    function submitForm() {
    	var mode = $('#mode').val();
    	var strUrl = "";
    	var strMethod = "";
    	var strRescKey = $('#highOganCode').val() + "." + $('#oganCode').val();
    	$('#rescKey').textbox('setValue', strRescKey);
    	
    	if (mode == "ADD") {
    		strUrl = '<c:out value="${contextPath}"/>/rest/ogan/create';
    		strMethod = 'POST';
    	} else if (mode == "MODIFY") {
    		strUrl = '<c:out value="${contextPath}"/>/rest/ogan/update';
    		strMethod = 'PUT';
    	}    	        	
    	
    	if($('#frmOgan').form('enableValidation').form('validate')) {    		
    		var formData = parseFormHelper('frmOgan');
    		$.ajax({
				type: strMethod,
				url: strUrl,
				data: formData, 
				dataType: 'json',						
				beforeSend: function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
			        xhr.setRequestHeader("Content-Type", "application/json");
					//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},  				
				success : function(msg) {
					if (mode == "ADD") {
						var title = '<spring:message code="resc.label.confirm"/>';		    			
						$.messager.alert(title, msg, "info",  function(){
							$("#oganTree").tree('reload');	
							formReset();
							//저장, 초기화 버튼 비활성화
					    	$('#btnClear').linkbutton('disable');
					    	$('#btnSave').linkbutton('disable');
						});												
					} else if (mode == "MODIFY") {
						var title = '<spring:message code="resc.label.confirm"/>';		 
						$.messager.alert(title, msg, "info",  function() {
							var rowData = $("#oganTree").tree('getSelected');
							var coId = $("#selCoId").combobox('getValue');
							getOganInfo(coId, rowData.id, rowData.oganLev);
							//저장, 초기화 버튼 비활성화
					    	$('#btnClear').linkbutton('disable');
					    	$('#btnSave').linkbutton('disable');
						});	
					}
				},
				error : function(xhr, status, error) {
					console.log("error: " + error);
				}
    		});
    		return false;
    	}    	
    }
    </script>
</head>
<body>
	<form id="frmOgan" style="width:98%;">
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
			        <c:forEach items="${langList}" var="lang">	 
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="oganName_<c:out value="${lang.rescKeyValue}"/>" name="oganName_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.oganName"/>(${lang.rescKeyValue}):',required:true,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="oganDesc_<c:out value="${lang.rescKeyValue}"/>" name="oganDesc_<c:out value="${lang.rescKeyValue}"/>" style="width:100%" data-options="label:'<spring:message code="resc.label.oganDesc"/>(${lang.rescKeyValue}):',required:true,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        </c:forEach>
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
				                <input class="easyui-textbox" id="rescKey" name="rescKey" style="width:100%" data-options="label:'<spring:message code="resc.label.rescKey"/>:',required:true,labelWidth:100">
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
			        <div style="text-align:center;padding:5px 0">
			            <a href="javascript:void(0)" id="btnSave" class="easyui-linkbutton" onclick="submitForm()" style="width:80px"><spring:message code="resc.btn.save"/></a>			            
			            <a href="javascript:void(0)" id="btnClear" class="easyui-linkbutton" onclick="formReset()" style="width:80px"><spring:message code="resc.btn.clear"/></a>
			        </div>
			    </div>
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>
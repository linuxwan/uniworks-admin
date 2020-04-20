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
	<script type="text/javascript" src="<c:out value="${contextPath}"/>/plugin/jquery.serializeObject.js"></script>
    <script type="text/javascript">	
    $(function(){
    	$("#coTree").treegrid({
    		onClickRow: function(rowData) {
    			getCompanyInfo(rowData.coId, rowData.stDate);
    			$('#mode').val('');
    			$('#btnClear').linkbutton('disable');
    			$('#btnSave').linkbutton('disable');
    		}
    	});  
    	
    	$('#btnClear').linkbutton('disable');
    	$('#btnSave').linkbutton('disable');
    	
    	$('#btnAdd').bind('click', function(){
    		formReset();
    		setReadOnly(false);
    		
    		var rowData = $("#coTree").treegrid('getSelected');
    		
    		if (rowData != null && rowData.prntCoCode != 'root') {
    			var title = '<spring:message code="resc.label.confirm"/>';
    			var msg = '<spring:message code="resc.msg.childCoNotMake"/>';
    			
    			alertMsg(title, msg);
    			return;
    		}
    		
    		if ($('#mode').val() == 'ADD' || rowData == null) {    			    			    			
    			$('#prntCoCode').textbox('setValue', "root");
        		$('#prntCoCode').textbox('readonly', true);
    		} else {
    			$('#prntCoCode').textbox('setValue', rowData.coId);
        		$('#prntCoCode').textbox('readonly', true);
    		}
    		    		
    		$('#finDate').textbox('setValue', "9999-12-31");
    		$('#finDate').textbox('readonly', true);
    		$('#mode').val('ADD');    	
    		$('#btnSave').linkbutton({
    			'text' : '<spring:message code="resc.btn.add"/>'	
    		});
    		$('#btnSave').linkbutton('enable');
    		$('#btnClear').linkbutton('enable');    		
    	});   
    	
    	$('#btnModify').bind('click', function(){
    		var rowData = $("#coTree").treegrid('getSelected');
    		if ($('#mode').val() != '' || rowData == null) {
    			var title = '<spring:message code="resc.label.confirm"/>';
    			var msg = '<spring:message code="resc.msg.selectCompany"/>';
    			
    			alertMsg(title, msg);
    			return;
    		}
    		setReadOnly(false);
    		$('#mode').val('MODIFY');
    		$('#btnSave').linkbutton('enable');
    		$('#btnSave').linkbutton({
    			'text' : '<spring:message code="resc.btn.save"/>'	
    		});    		
    		$('#btnClear').linkbutton('disable');
    	});
    	
    	$('#btnDelete').bind('click', function() {
    		var rowData = $("#coTree").treegrid('getSelected');
    		var title = '<spring:message code="resc.label.confirm"/>';
    		var msg = '<spring:message code="resc.msg.selectCompany"/>';
    		if ($('#mode').val() != '' || rowData == null) {    			    			    			
    			alertMsg(title, msg);
    			return;
    		}
    		
    		// 하위 노드가 있는지 확인. 있으면 배열이 리턴
    		var childNode = $("#coTree").treegrid('getChildren', rowData.coId); 
    		// childNode 배열의 개수가 1보다 작을 경우, 하위 노드가 없음
    		if (childNode.length < 1) {    		
    			msg = '<spring:message code="resc.msg.confirmDel"/>';    		
	    		$.messager.confirm(title, msg, function(r) {
	    			if (r) {
	    				deleteCompany();
	    			}
	    		});
    		} else { //하위 노드가 있을 경우
    			msg = '<spring:message code="resc.msg.childNodeExist"/>';
    			alertMsg(title, msg);
    			return;
    		}
    	});
    });
    
    /**
     * 회사 정보를 삭제한다.
    **/
    function deleteCompany() {
    	var coId = $('#coId').textbox('getValue');
    	var stDate = replaceAll($('#stDate').textbox('getValue'), ".", "");    	
    	var strUrl = "<c:out value="${contextPath}"/>/rest/company/delete/" + coId + "/stDate/" + stDate;

    	$.ajax({
			type: 'DELETE',
			url: strUrl,					
			beforeSend: function(xhr) {
				//데이터를 전송하기 전에 헤더에 csrf값을 설정한다.					
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			},  				
			success : function(json) {
				$("#coTree").treegrid('reload');	
			},
			error : function(xhr, status, error) {
				console.log("error: " + status);
			}
		});
    }
    
    function convert(rows){
        function exists(rows, _parentId){
            for(var i=0; i<rows.length; i++){
                if (rows[i].coId == _parentId) return true;
            }
            return false;
        }
        
        var nodes = [];
        // get the top level nodes
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (!exists(rows, row.prntCoCode)){
                nodes.push({
                    coId: row.coId,
                    coName: row.coName,
                    prntCoCode: row.prntCoCode,
                    stDate: row.stDate,
                    finDate: row.finDate
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
                if (row.prntCoCode == node.coId){
                    var child = {coId:row.coId,coName:row.coName,prntCoCode:row.prntCoCode,stDate:row.stDate,finDate:row.finDate};
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
     * form 내의 입력 관련 Object를 리셋한다.
    */
    function formReset() {
    	$('#coId').textbox('setValue', "");
    	$('#prntCoCode').textbox('setValue', "");
    	$('#coName').textbox('setValue', "");
    	$('#stDate').textbox('setValue', "");
    	$('#finDate').textbox('setValue', "");
    	$('#baseOganLev').textbox('setValue', "");
    	$('#sprtLang').textbox('setValue', "");
    	$('#bizRgsrNo').textbox('setValue', "");
    	$('#rprsntName').textbox('setValue', "");
    	$('#zipCode').textbox('setValue', "");
    	$('#homePageUrl').textbox('setValue', "");
    	$('#coAddr').textbox('setValue', "");
    	$('#coPhonNo').textbox('setValue', "");
    	$('#coFaxNo').textbox('setValue', "");
    	$('#prntCoClsfY').prop('checked', false);
    	$('#prntCoClsfN').prop('checked', false);
    	$('#useIndcY').prop('checked', false);
    	$('#useIndcN').prop('checked', false);
    }
    
    /*
     * 회사 정보를 가져온다.
    */
    function getCompanyInfo(coId, stDate) {
    	var url = "<c:out value="${contextPath}"/>/rest/company/coId/" + coId + "/stDate/" + stDate;
    	$.ajaxSetup({async: false});
    	$.getJSON(url, function (data, status){
    		if (status == 'success') {    		
    			setCompanyInfo(data);
    			setReadOnly(true);
    		} else {
    			return;
    		}
    	});
    }
    
    /*
     * 가져온 회사 정보를 셋팅
    */
    function setCompanyInfo(data) {
    	$('#coId').textbox('setValue', data.coId);
    	$('#prntCoCode').textbox('setValue', data.prntCoCode);
    	$('#coName').textbox('setValue', data.coName);
    	$('#stDate').textbox('setValue', stringFormatDate(data.stDate, "."));
    	$('#finDate').textbox('setValue', stringFormatDate(data.finDate, "."));
    	$('#baseOganLev').textbox('setValue', data.baseOganLev);
    	$('#sprtLang').textbox('setValue', data.sprtLang);
    	$('#bizRgsrNo').textbox('setValue', data.bizRgsrNo);
    	$('#rprsntName').textbox('setValue', data.rprsntName);
    	$('#zipCode').textbox('setValue', data.zipCode);
    	$('#homePageUrl').textbox('setValue', data.homePageUrl);
    	$('#coAddr').textbox('setValue', data.coAddr);
    	$('#coPhonNo').textbox('setValue', data.coPhonNo);
    	$('#coFaxNo').textbox('setValue', data.coFaxNo);
    	
    	if (data.prntCoClsf.trim() == "Y") {
    		$('#prntCoClsfY').prop('checked', true);
    	} else {
    		$('#prntCoClsfN').prop('checked', true);
    	}
    	
    	if (data.useIndc.trim() == "Y") {
    		$('#useIndcY').prop('checked', true);
    	} else {
    		$('#useIndcN').prop('checked', true);
    	}
    }
    
    /*
     * Object를 readonly 여부를 결정. true: readonly
    */    
    function setReadOnly(readOnlyIndc) {
    	$('#coId').textbox('readonly', readOnlyIndc);
    	$('#prntCoCode').textbox('readonly', readOnlyIndc);
    	$('#coName').textbox('readonly', readOnlyIndc);
    	$('#stDate').textbox('readonly', readOnlyIndc);
    	$('#finDate').textbox('readonly', readOnlyIndc);
    	$('#baseOganLev').textbox('readonly', readOnlyIndc);
    	$('#sprtLang').textbox('readonly', readOnlyIndc);
    	$('#bizRgsrNo').textbox('readonly', readOnlyIndc);
    	$('#rprsntName').textbox('readonly', readOnlyIndc);
    	$('#zipCode').textbox('readonly', readOnlyIndc);
    	$('#homePageUrl').textbox('readonly', readOnlyIndc);
    	$('#coAddr').textbox('readonly', readOnlyIndc);
    	$('#coPhonNo').textbox('readonly', readOnlyIndc);
    	$('#coFaxNo').textbox('readonly', readOnlyIndc);
    	$("input:radio[name='prntCoClsf']").prop("disabled", readOnlyIndc);
    	$("input:radio[name='useIndc']").prop("disabled", readOnlyIndc);
    	
    }
    
    /*
     * form submit 시 add/modify/delete를 구분해서 처리한다.
    */
    function submitForm() {
    	var mode = $('#mode').val();
    	var strUrl = "";
    	var strMethod = "";
    	if (mode == "ADD") {
    		strUrl = '<c:out value="${contextPath}"/>/rest/company/create';
    		strMethod = 'POST';
    	} else if (mode == "MODIFY") {
    		strUrl = '<c:out value="${contextPath}"/>/rest/company/update';
    		strMethod = 'PUT';
    	}    	        	
    	
    	if($('#frmCompany').form('enableValidation').form('validate')) {    		
    		//var formData = parseFormHelper('frmCompany');
    		var formData = JSON.stringify($('#frmCompany').serializeObject());
    		
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
				success : function(json) {
					if (mode == "ADD") {
						$("#coTree").treegrid('reload');	
					} else if (mode == "MODIFY") {
						var coId = $('#coId').textbox('getValue');
						var stDate = $('#stDate').textbox('getValue');
						getCompanyInfo(coId, stDate);
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
	<input type="hidden" id="mode" name="mode" value=""/>			
	<table style="width:100%">
		<tr>
			<td style="width:40%">				
       			<table id="coTree" title="<spring:message code="resc.label.companyMgr"/>" class="easyui-treegrid" style="width:100%;height:100%"
			            data-options="			                
			                lines: false,
			                rownumbers: true,
			                fitColumns: true,
			                toolbar:'#tb',
			                url: '<c:out value="${contextPath}"/>/rest/company',
			                method: 'get',
			                idField: 'coId',
			                treeField: 'coId',
			                loadFilter: function(rows) {
			                	return convert(rows);
			               	}
			            ">
			        <thead>
			            <tr>
			                <th data-options="field:'coId',halign:'center',align:'center',width:100"><spring:message code="resc.label.coId"/></th>
			                <th data-options="field:'coName',halign:'center',align:'left',width:150"><spring:message code="resc.label.coName"/></th>
			                <th data-options="field:'prntCoCode',halign:'center',align:'center',width:0,hidden:true"><spring:message code="resc.label.parentCompany"/></th>
			                <th data-options="field:'stDate',halign:'center',align:'center',width:80,formatter:formatDateYYYYMMDD"><spring:message code="resc.label.stDate"/></th>
			                <th data-options="field:'finDate',halign:'center',align:'center',width:80,formatter:formatDateYYYYMMDD"><spring:message code="resc.label.finDate"/></th>
			            </tr>
			        </thead>
			    </table>  
			    <div id="tb" style="height:auto">    
					<sec:authorize access="hasAuthority('SYS_ADM')">
						<a href="#" id="btnAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"><spring:message code="resc.btn.enrollment"/></a>
					</sec:authorize>
						<a href="#" id="btnModify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"><spring:message code="resc.btn.modify"/></a>
					<sec:authorize access="hasAuthority('SYS_ADM')">				
		        		<a href="#" id="btnDelete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"><spring:message code="resc.btn.delete"/></a>
		        	</sec:authorize>
				</div>  			
			</td>
			<td style="width:60%">
				<div class="easyui-panel" title="" style="width:100%;padding:10px 10px;">
			        <form id="frmCompany">			
			        <jsp:include page="/WEB-INF/views/include/hidden_type_01.jsp"></jsp:include>        
			        <table style="width:100%">
			        <tr>
			        	<td style="width:50%;padding:0px 10px;">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="coId" name="coId" style="width:100%" data-options="label:'<spring:message code="resc.label.coId"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="prntCoCode" name="prntCoCode" style="width:100%" data-options="label:'<spring:message code="resc.label.parentCoCode"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="coName" name="coName" style="width:100%" data-options="label:'<spring:message code="resc.label.coName"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-datebox" id="stDate" name="stDate" style="width:100%" data-options="label:'<spring:message code="resc.label.stDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-datebox" id="finDate" name="finDate" style="width:100%" data-options="label:'<spring:message code="resc.label.finDate"/>:',required:true,formatter:dashformatter,parser:dashparser,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				            	<spring:message code="resc.label.parentCoDivision"/>:&nbsp;&nbsp;&nbsp;&nbsp;
				                <input type="radio" id="prntCoClsfY" name="prntCoClsf" class="easyui-validatebox" value="Y" /><spring:message code="resc.label.parentCompany"/>
								<input type="radio" id="prntCoClsfN" name="prntCoClsf" class="easyui-validatebox" value="N" /><spring:message code="resc.label.subCompany"/>
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				            	<spring:message code="resc.label.useIndc"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                <input type="radio" id="useIndcY" name="useIndc" class="easyui-validatebox" value="Y" /><spring:message code="resc.label.use"/>
								<input type="radio" id="useIndcN" name="useIndc" class="easyui-validatebox" value="N" /><spring:message code="resc.label.unused"/>
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="baseOganLev" name="baseOganLev" style="width:100%" data-options="label:'<spring:message code="resc.label.baseOganLev"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="sprtLang" name="sprtLang" style="width:100%" data-options="label:'<spring:message code="resc.label.sprtLang"/>:',required:true,labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="bizRgsrNo" name="bizRgsrNo" style="width:100%" data-options="label:'<spring:message code="resc.label.bizRgsrNo"/>:',labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="rprsntName" name="rprsntName" style="width:100%" data-options="label:'<spring:message code="resc.label.rprsntName"/>:',labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="zipCode" name="zipCode" style="width:100%" data-options="label:'<spring:message code="resc.label.zipCode"/>:',labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="homePageUrl" name="homePageUrl" style="width:100%" data-options="label:'<spring:message code="resc.label.homePageUrl"/>:',labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="coAddr" name="coAddr" style="width:100%" data-options="label:'<spring:message code="resc.label.coAddr"/>:',labelWidth:100">
				            </div>
			            </td>
			        </tr>
			        <tr>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="coPhonNo" name="coPhonNo" style="width:100%" data-options="label:'<spring:message code="resc.label.coPhonNo"/>:',labelWidth:100">
				            </div>
			            </td>
			            <td style="width:50%;padding:0px 10px;">
				            <div style="margin-bottom:10px">
				                <input class="easyui-textbox" id="coFaxNo" name="coFaxNo" style="width:100%" data-options="label:'<spring:message code="resc.label.coFaxNo"/>:',labelWidth:100">
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
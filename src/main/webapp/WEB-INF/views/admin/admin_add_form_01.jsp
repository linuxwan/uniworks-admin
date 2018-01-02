<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="adminAdd" class="easyui-dialog" style="width:700px;height:270px;padding:10px 30px;"
        title='<spring:message code="resc.label.adminAddForm"/>' data-options="modal: true, closed: true, buttons: '#dlg-buttons'">
        
	<form id="adminAddForm">
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
		                <input class="easyui-textbox" id="adminId" name="adminId" style="width:100%" data-options="label:'<spring:message code="resc.label.adminID"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	        	<td style="width:50%;padding:0px 10px;">		        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="empNo" name="empNo" style="width:100%" data-options="label:'<spring:message code="resc.label.empNo"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="pswd" name="pswd" type="password" style="width:100%" data-options="label:'<spring:message code="resc.label.pswd"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
	        <tr>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="useStDate" name="useStDate" style="width:100%" data-options="label:'<spring:message code="resc.label.useStDate"/>:',required:true,formatter:pointformatter,parser:pointparser,labelWidth:100">
		            </div>
	            </td>
	            <td style="width:50%;padding:0px 10px;">
		            <div style="margin-bottom:10px">
		                <input class="easyui-datebox" id="useFinDate" name="useFinDate" style="width:100%" data-options="label:'<spring:message code="resc.label.useFinDate"/>:',required:true,formatter:pointformatter,parser:pointparser,labelWidth:100">
		            </div>
	            </td>
	        </tr>	        
	        <tr>
	        	<td style="width:100%;padding:0px 10px;" colspan="2">			        	
		            <div style="margin-bottom:10px">
		                <input class="easyui-textbox" id="adminType" name="adminType" style="width:100%" data-options="label:'<spring:message code="resc.label.adminType"/>:',required:true,labelWidth:100">
		            </div>
	            </td>
	        </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savereg()"><spring:message code="resc.btn.enrollment"/></a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#adminAdd').dialog('close')"><spring:message code="resc.btn.close"/></a>
</div>

<script type="text/javascript">	

</script>
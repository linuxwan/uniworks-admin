var webRootPath = "/uniworks-admin";
var topMenuId;
var $element=$(window),lastWidth=$element.width(),lastHeight=$element.height();	

//브라우저 사이즈를 기준으로 resize
function setHeight() {
	var c = $('.easyui-layout');   		   				
	var width = $(window).width() - 40;
	var height = $(window).height() - 40;	
	
	c.layout('resize',{
		width: width,
		height: height
	});	
	/*
	if ($element.width()!=lastWidth||$element.height()!=lastHeight){
		$('#panel').panel('resize',{
			width: lastWidth,
			height: lastHeight
		});
		$('#datagrid').datagrid('resize', {
			width: lastWidth,
			height: lastHeight
		}); 
		lastWidth = $element.width();lastHeight=$element.height();	 
	}
	*/
}

/**
 * 좌측 sub menu 표시
 * @param coId
 * @param highMenuId
 * @param lang
 * @param title
 */
function getSidebarMenu(coId, highMenuId, lang, title) {
	topMenuId = highMenuId;
	var subMenu = "";
	var menuId = highMenuId.substring(0,6);
	var url = webRootPath + "/rest/subMenu/coId/" + coId + "/highMenuId/" + menuId + "/lang/" + lang;	
	
	$.getJSON(url, function (data, status){
		if (status == 'success') {
			setSubMenuHead(title);
			subMenu = getSubMenuLevel2(data);			
		} else {
			return;
		}
	});	
}

/**
 * 좌측 sub menu를 표기하기 위한 헤드 부분
 * @param title
 * @returns {String}
 */
function setSubMenuHead(title) {
	var p = $('#selTopMenu').layout();
	p.panel('setTitle', title);	
}

/**
 * 좌측 sub menu - 2레벨
 * @param data
 * @returns {String}
 */
function getSubMenuLevel2(data) {
	var subMenu = "";
	var strHighMenu = "";
	
	//3레벨의 상위 레벨 코드를 모두 기록.
	$.each(data, function(index, entry) {
		if (entry["menuLevel"] == "3") {
			strHighMenu += entry["highMenuId"] + ",";
		}
	});
	
	//accordion을 생성
	$('#leftSubMenu').accordion({
		multiple: true,
		border: false
	});			
	
	removeMenuAccordion();
	var noChildNo = 0;	
	$.each(data, function(index, entry) {
		if (entry["menuLevel"] == "2") {
			var contentMenu = getSubMenuLevel3(entry["menuId"], data);
			var param = entry["menuId"] + ',' + entry["menuDsplName"] + ',' + entry["bodyUrl"] + ',' + entry["highMenuId"] + ',' + entry["menuLevel"];
			//하위 메뉴가 없는 경우
			if (contentMenu == null || contentMenu.length < 1) {
				$('#leftSubMenu').accordion('add', {
					title: entry["menuDsplName"],
					selected: false,	
					content: '<div id="subPanel" class="easyui-panel" style="width:100%;border:false;padding:0px"><input id="' + 'noChildNo' + noChildNo + '" class="hidden" type="hidden" value="' + param + '"></div>'
				});				
			} else {
				$('#leftSubMenu').accordion('add', {
					title: entry["menuDsplName"],
					content: contentMenu,
					selected: false
				});
			}
			noChildNo++;
		} 
	});	
	
	return subMenu;
}

/**
 * 좌측 sub menu - 3레벨의 메뉴를 표기 
 * @param data
 * @returns {String}
 */
function getSubMenuLevel3(hightMenuId, data) {
	var subMenu = "";	
	var no = 0;
	$.each(data, function(index, entry) {
		if (entry["menuLevel"] == "3") {						
			if (hightMenuId == entry["highMenuId"]) {
				no++;
				subMenu += '<div id="p' + no + '" class="easyui-panel" style="width:100%;border:false;padding:1px"><a href="javascript:showContentPage(\'' + entry["menuId"] + '\', \'' + entry["menuDsplName"] + '\', \'' + entry["bodyUrl"] + '\', \'' + entry["highMenuId"] + '\',\'' + entry["menuLevel"] + '\');"' + 'class="easyui-linkbutton" plain="true" style="width:100%;">' + entry["menuDsplName"] + '</a></div> \r\n';
			}
		} 
	});	
	
	return subMenu;
}

/**
 * 이전에 Display했던 accordion 메뉴 제거.
 */
function removeMenuAccordion() {
	var thepanels = $('#leftSubMenu').accordion('panels');
	while (thepanels.length){
	  $('#leftSubMenu').accordion('remove', 0);
	}
}

function goPage(cntnName, bodyUrl) {
	var param = "cntnName=" + cntnName;
	var mh = $(window).height() - 100;
	var mw = $(window).width() - 398;
	var url = webRootPath + bodyUrl;
	
	var p = $('#content').layout();
	p.panel('setTitle', cntnName);
	
	document.getElementById('frmMain').onload = resizeIframe;
	window.onresize = resizeIframe;	
	
	$('#frmMain').css('height', mh + 'px');
	$('#frmMain').css('width', mw + 'px');
	$('#frmMain').attr("src", webRootPath + bodyUrl + "?" + param);	
}

/**
 * 컨텐츠 페이지로 이동
 * @param menuId
 * @param menuDsplName
 * @param bodyUrl
 */
function showContentPage(menuId, menuDsplName, bodyUrl, highMenuId, menuLevel) {
	var param = "headMenuId=" + topMenuId + "&menuId=" + menuId + "&menuLevel=" + menuLevel;
	var mh = $(window).height() - 100;
	
	getMenuHierarchyInfo(topMenuId, menuId, menuLevel);
	var url = webRootPath + bodyUrl + "?" + param;
	
	$('#frmMain').css('height', mh + 'px');
	$('#frmMain').attr("src", webRootPath + bodyUrl + "?" + param);
	
	document.getElementById('frmMain').onload = resizeIframe;
	window.onresize = resizeIframe;
	
	$('#frmMain').attr('src', url);
}

/**
 * 메뉴 네비게이션 표기
 * @param startMenuId
 * @param endMenuId
 * @param menuLevel
 */
function getMenuHierarchyInfo(startMenuId, endMenuId, menuLevel) {
	var url = webRootPath + "/rest/startMenuId/" + startMenuId + "/endMenuId/" + endMenuId + "/menuLevel/" + menuLevel + ".json";
	var navMenu = '';
	
	$.getJSON(url, function (data, status){
		if (status == 'success') {			
			navMenu = getMenuHierarchyInfoFormat(data);
			var p = $('#content').layout();
	    	p.panel('setTitle', navMenu);
		} else {
			return;
		}		
	});
}

/**
 * 1레벨에서 최종 선택된 메뉴까지의 path를 표기
 * @param data
 * @returns {String}
 */
function getMenuHierarchyInfoFormat(data) {
	var navMenu = '';
	if (data.menuDsplName1 != undefined && data.menuDsplName1 != null && data.menuDsplName1 != "") {
		navMenu += data.menuDsplName1;
		if (data.menuDsplName2 != undefined && data.menuDsplName2 != null && data.menuDsplName2 != "") {
			navMenu += " > " + data.menuDsplName2;
			if (data.menuDsplName3 != undefined && data.menuDsplName3 != null && data.menuDsplName3 != "") {
				navMenu += " > " + data.menuDsplName3;
			}
		}
	}
	return navMenu;
}

function resizeIframe() {
    //var height = parent.document.documentElement.clientHeight;   
    //height -= parent.document.getElementById('frmMain').offsetTop;
    //height -= 20; /* whatever you set your body bottom margin/padding to be */
    //parent.document.getElementById('frmMain').style.height = height +"px";
	var fullHeight = parent.document.documentElement.clientHeight;
	var minusHeight = fullHeight * 0.20;
	parent.document.getElementById('frmMain').style.height = (fullHeight - minusHeight) +"px";   
};

/**
 * DataGrid에서 날짜 포맷에 맞게 변환하는 함수
 * @param val
 * @param row
 * @returns {String}
 */
function formatDate(val, row) {
	if (val == null || val == "") return "";
	var date = new Date(Number(val));
	
	/*
	var year = 1900 + Number(val.year);
	var month = Number(val.month) + 1;
	var date = Number(val.date);
	var hours = Number(val.hours);
	var minutes = Number(val.minutes);
	var seconds = Number(val.seconds);
	
	var date = new Date(year, month, date, hours, minutes, seconds);
	*/	
	
	var strMonth = '';
	if (date.getMonth() + 1 < 10) {
		strMonth = '0' + (date.getMonth() + 1);
	} else {
		strMonth = (date.getMonth() + 1);
	}
	var strDate = '';
	if (date.getDate() < 10) {
		strDate = '0' + date.getDate();
	} else {
		strDate = date.getDate();
	}
	
	var strHours = '';
	if (date.getHours() < 10) {
		strHours = '0' + date.getHours();
	} else {
		strHours = date.getHours();
	}
	
	var strMinutes = '';
	if (date.getMinutes() < 10) {
		strMinutes = '0' + date.getMinutes();
	} else {
		strMinutes = date.getMinutes();
	}
	
	var strSeconds = '';
	if (date.getSeconds() < 10) {
		strSeconds = '0' + date.getSeconds();
	} else {
		strSeconds = date.getSeconds();
	}
	
	var strDate = date.getFullYear() + '.' + strMonth + '.' + strDate + ' ' + strHours + ':' + strMinutes + ':' + strSeconds; 
	return strDate;
}

/***
 * 8자리의 날짜정보에 "."를 추가해서 반환. (grid에서 날짜타입으로 변환하기 위한 용도)
 * @param val
 * @param row
 * @returns
 */
function formatDateYYYYMMDD(val, row)  {
	if (val == null || val == "") return "";
	var strDate = "";
	
	if (val.length == 8) {
		strDate = val.substring(0,4) + '.' + val.substring(4,6) + '.' + val.substring(6,8);
	} else {
		return "";		
	}
	return strDate;
}
/***
 * 
 * @param val
 * @param row
 * @returns
 */
function stringFormatDate(strDate, sign)  {
	if (strDate == null || strDate == "") return "";
	
	
	if (strDate.length == 8) {
		strDate = strDate.substring(0,4) + '.' + strDate.substring(4,6) + '.' + strDate.substring(6,8);
	} else {
		return "";		
	}
	return strDate;
}
/**
 * 결재문서 작성 화면으로 이동
 * @param apprMstId
 * @param apprDesc
 * @param cntnId
 */
function approvalFormToWrite (apprMstId, apprDesc, cntnId) {
	var url = webRootPath + '/approval/approval_write?apprMstId=' + apprMstId + '&cntnId=' + cntnId + '&apprDesc=' + apprDesc;
	parent.$('#frmMain').attr('src', url);
};

/* 
 * 선택한 보존연한 정보를 입력받아서 현재일자를 기준으로 보존연한 정보를 셋팅한다.
 * 00-1달, 01-1년, 03-3년, 05-5년, 10-10년, 99-영구
 * source : 보존연한값을 선택할 수 있는 select box
 * target : 보존연한에 해당하는 일자를 계산해서 보여주기 위한 대상(div) 
 */
function getServiceLifeDate(source, target, target2) {
	var serviceLife = source;
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth();
	var day = now.getDate();
	
	if (serviceLife == '00' && month == 11) {
		now.setFullYear(year + 1, 0);
		year = now.getFullYear();
		month = now.getMonth() + 1;
	} else if (serviceLife == '00' && month != 11) {
		now.setFullYear(year, month + 1);
		year = now.getFullYear();
		month = now.getMonth(month) + 1;
	} else if (serviceLife == '99') {
		year = 9999;
		month = 12;
		day = 31;
	} else {
		now.setFullYear(year + Number(serviceLife));
		year = now.getFullYear();
		month = now.getMonth() + 1;
	}
	
	if (month < 10) month = "0" + month;
	if (day < 10) day = "0" + day;

	$('#' + target).text(year + "." + month + "." + day);
	$('#' + target2).val(year + "" + month + "" + day);
}

/**
 * 조직 목록을 가져와서 트리를 그린다.
 * @param rootUrl
 * @param level
 * @param oganCode
 */
function getAllOganList(rootUrl, oganTree, topNode) {
	$("#" + oganTree).tree({
		url: rootUrl + '/rest/ogan/getAllOganList',
		method: "get",
		cascadeCheck: true,
		loadFilter: function(rows) {
			return convert(rows);
		}
	});	
}

/**
 * easyui tree를 Dispaly한다.
 * @param rows
 * @returns {Array}
 */
function convert(rows) {
	function exists(rows, parentId, parentLev) {		
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].id == parentId && rows[i].oganLev == (parentLev + 1)) {
				return true;
			}
			return false;
		}
	}

	var nodes = [];
	// get the top level nodes	
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		if (!exists(rows, row.parentId, row.parentLev) && row.oganLev == 1) {
			nodes.push({
				id: row.id,
				text: row.text,
				oganLev:row.oganLev
			})
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
            if (row.parentId == node.id) { //&& (row.parentLev + 1) == node.oganLev){
                var child = "";
                child = {id:row.id,text:row.text,oganLev:row.oganLev};
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

/**
 * alert 창을 띄운다.
 * @param msg
 */
function alertMsg(title, msg) {
	$.messager.defaults.ok = "OK";
	$.messager.alert({
		width : 350,
		title : title,
		msg : msg,
		icon : 'warning',
		fn: function(r) {
		}
	});
}

/**
 * alert 창을 띄운다.
 * @param msg
 */
function alertMsgCallbackFocus(title, msg, obj) {
	$.messager.defaults.ok = "OK";
	$.messager.alert({
		width : 350,
		title : title,
		msg : msg,
		icon : 'warning',
		fn: function(r) {
			obj.focus();
		}
	});	
}

/**
 * 파일 사이즈를 KB 사이즈로 변환해서 소수점 2자리까지만 표기
 * @param num
 */
function formatterFileSize(num, row) {
	var size = num / 1024;
	var convertSize = comma(size.toFixed(2));
	return convertSize + " KB";
}

//1000단위에 콤마찍기
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

//1000단위의 콤마풀기
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

function fileDownload(cntnId, dcmtRgsrNo, fileId) {
	var url = webRootPath + "/download/cntnId/" + cntnId + "/dcmtRgsrNo/" + dcmtRgsrNo + "/fileId/" + fileId;
	self.location = url;	
}

/***
 * Datebox에서 사용 : 날짜 사이에 -로 구분
 * @param date
 * @returns
 */
function dashformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

/***
 * Datebox에서 사용 : -를 제거한 날짜 반환
 * @param s
 * @returns
 */
function dashparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

/***
 * Datebox에서 사용 : 날짜 사이에 .로 구분
 * @param date
 * @returns
 */
function pointformatter(date){
	console.log(date);
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'.'+(m<10?('0'+m):m)+'.'+(d<10?('0'+d):d);
}

/***
 * Datebox에서 사용 : .를 제거한 날짜 반환
 * @param s
 * @returns
 */
function pointparser(s){
    if (!s) return new Date();
    var ss = (s.split('.'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}


/***
 * form 내의 객체의 값들을 모두 json 형태로 변환한다.
 * @param formId
 * @returns
 */
function parseFormHelper (formId) {
    var serialized = $("#"+formId).serializeArray();
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    
    return JSON.stringify(data);    
}

/***
 * 특정 문자를 다른 문자로 모두 치환
 * @param str
 * @param orgn
 * @param change
 * @returns
 */
function replaceAll(str, orgn, dest) {
	return str.split(orgn).join(dest);
}
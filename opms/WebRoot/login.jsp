<%@ page contentType="text/html; charset=GBK"%>
<html>
<title>${sysTitle}</title>
<meta http-equiv="keywords" content="海辉金信系统集成与应用开发平台">
<meta http-equiv="content-type" content="text/html; charset=GBK">
<meta http-equiv="description" content="hisoft">
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache, must-revalidate">  
<meta http-equiv="expires" content="0">
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<link rel="icon" href="<%=_contextPath_%>/images/favicon.ico" type="image/x-icon" />
<link href="<%=_contextPath_%>/resource/ext/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
<link href="<%=_contextPath_%>/resource/ext/resources/css/xtheme-gray.css" rel="stylesheet" type="text/css"/>
<link href="<%=_contextPath_%>/resource/css/ext_icon.css" rel="stylesheet" type="text/css"/>
<link href="<%=_contextPath_%>/css/layout-browser.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=_contextPath_%>/resource/css/ext_patch_firefox.css" />
<link rel="stylesheet" type="text/css" href="<%=_contextPath_%>/resource/css/eredg4.css"/>

<script type="text/javascript" src="<%=_contextPath_%>/resource/commonjs/eredg4.js"></script>
<script language="javascript" src="<%=_contextPath_%>/resource/ext/adapter/ext/ext-base-debug.js" type="text/javascript"></script>
<script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-all-debug.js" type="text/javascript"></script>
<script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-basex-debug.js"></script>
<script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-lang-zh_CN.js" type="text/javascript"></script>

<script language="javascript" src="<%=_contextPath_%>/resource/ext/Vtype.js" type="text/javascript"></script>

<script>
	Ext.onReady(function (){
		Ext.Ajax.request({
			 		url:'login.ered?reqCode=logout',
			 		callback:function(opts,success,response){
			 		}
		});
	});
	var LOGIN_WINDOW_TITLE='${LOGIN_WINDOW_TITLE}';
	var pageLoadMsg='${PAGE_LOAD_MSG}';
	var runMode = '${runMode}';
	 
	var webContext = '<%=_contextPath_%>';
  	var ajaxErrCode = '${ajaxErrCode}';
  	Ext.QuickTips.init();
  	Ext.form.Field.prototype.msgTarget = 'qtip';
  	Ext.BLANK_IMAGE_URL = webContext+'/resource/image/ext/s.gif';
  	
  	var BOTTOM_COPYRIGHT='${BOTTOM_COPYRIGHT}';
	var DEFAULT_ADMIN_ACCOUNT='${DEFAULT_ADMIN_ACCOUNT}';
	var DEFAULT_DEVELOP_ACCOUNT='${DEFAULT_DEVELOP_ACCOUNT}';
	var INDEX_BANNER='${INDEX_BANNER}';
	var LOGIN_WINDOW_BANNER='${LOGIN_WINDOW_BANNER}';
	var MENU_FIRST='${MENU_FIRST}';
	var MULTI_SESSION='${MULTI_SESSION}';
	var PAGE_LOAD_MSG='${PAGE_LOAD_MSG}';
	var SYS_DEFAULT_PORTAL='${SYS_DEFAULT_PORTAL}';
	var SYS_DEFAULT_THEME='${SYS_DEFAULT_THEME}';
	var SYS_TITLE='${SYS_TITLE}';
	var WEBTELLER_HOME='${WEBTELLER_HOME}';
	var WELCOME_PAGE_TITLE='${WELCOME_PAGE_TITLE}';
	var WEST_CARDMENU_ACTIVEONTOP='${WEST_CARDMENU_ACTIVEONTOP}';
	var WEST_NAVIGATE_TITLE='${WEST_NAVIGATE_TITLE}';

</script>
<script language="javascript" src="<%=_contextPath_%>/arm/js/login.js" type="text/javascript"></script>


<body>
<div id="hello-win" class="x-hidden">
		<div id="hello-tabs">
			<img border="0" width="450" height="70" src="${bannerPath}" />
		</div>
	</div>
	<div id="aboutDiv" class="x-hidden"
		style='color: black; padding-left: 10px; padding-top: 10px; font-size: 12px'>

	</div>
	<div id="infoDiv" class="x-hidden"
		style='color: black; padding-left: 10px; padding-top: 10px; font-size: 12px'>

	</div>
</body>
</html>
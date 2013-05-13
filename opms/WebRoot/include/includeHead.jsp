<%@ page import="org.eredlab.g4.rif.util.WebUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: ritchrs
  Date: 11-4-7
  Time: ?¦¶1:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<html>
<head>
    <title></title>
    <link href="<%=_contextPath_%>/resource/ext/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/resource/ext/resources/css/xtheme-gray.css" rel="stylesheet" type="text/css"/>
    
    <link href="<%=_contextPath_%>/resource/css/ext_icon.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/css/layout-browser.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/resource/ext/ux/css/LockingGridView.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/resource/ext/ux/fileuploadfield/css/fileuploadfield.css" rel="stylesheet" type="text/css"/>
    
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/adapter/ext/ext-base-debug.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-all-debug.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-basex-debug.js"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-lang-zh_CN.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/PageSizePlugin.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/ProgressBarPager.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/LockingGridView.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/RowExpander.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/fileuploadfield/FileUploadField.js" type="text/javascript"></script>
    <script type="text/javascript">
        var _contextPath_ = "<%=_contextPath_%>";
        Ext.BLANK_IMAGE_URL = _contextPath_ + '/resource/ext/resources/images/default/s.gif';
        Ext.QuickTips.init();
        var isPortal = <%=request.getParameter("isPortal")%>;
    </script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/Ext.ux.AllowBlank.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/UnitField.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/DecimalField.js" type="text/javascript"></script>
    <%--<script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/Ext.ux.PanelCollapsedTitle.js" type="text/javascript"></script>--%>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/HttpRequest.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/CheckSession.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/FunctionGrid-debug.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/FunctionCombo.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/FunctionRadioGroup.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/Vtype.js" type="text/javascript"></script>
	<script language="javascript" src="<%=_contextPath_%>/common/authorization/authorization.js" type="text/javascript"></script>
	<script language="javascript" src="<%=_contextPath_%>/resource/ext/MultiComboBox.js"></script>
	<script language="javascript" src="<%=_contextPath_%>/resource/ext/validate.js"></script>
	

    <style type="text/css">
 #loading-mask {
	Z-INDEX: 20000;
	LEFT: 0px;
	WIDTH: 100%;
	POSITION: absolute;
	TOP: 0px;
	HEIGHT: 100%;
	BACKGROUND-COLOR: white
}
#loading {
	PADDING-RIGHT: 2px;
	PADDING-LEFT: 2px;
	Z-INDEX: 20001;
	LEFT: 32%;
	PADDING-BOTTOM: 2px;
	PADDING-TOP: 2px;
	POSITION: absolute;
	TOP: 40%;
	HEIGHT: auto
}
#loading IMG {
	MARGIN-BOTTOM: 5px
}
#loading .loading-indicator {
	PADDING-RIGHT: 10px;
	PADDING-LEFT: 10px;
	BACKGROUND: white;
	PADDING-BOTTOM: 10px;
	MARGIN: 0px;
	FONT: 12px "ËÎÌå", arial, helvetica;
	COLOR: #555;
	PADDING-TOP: 10px;
	HEIGHT: auto;
	TEXT-ALIGN: center
}
        .tip-target {
            width: 100px;
            text-align:center;
            padding: 5px 0;
            border:1px dotted #99bbe8;
            background:#dfe8f6;
            color: #15428b;
            cursor:default;
            margin:10px;
            font:bold 11px tahoma,arial,sans-serif;
            float:left;
        }
</style>
<script type="text/javascript">
Ext.EventManager.on(window, 'load', function(){
	 setTimeout(
		 function() {
			Ext.get('loading').remove();
			Ext.get('loading-mask').fadeOut({remove:true});
			}, 5);
});
</script>
<DIV id=loading-mask></DIV>
<DIV id=loading>
<DIV class=loading-indicator><IMG style="MARGIN-RIGHT: 5px"
	height=16
	src="<%=_contextPath_%>/resource/image/ajax.gif"
	width=16 align=absMiddle><%=WebUtils.getParamValue("PAGE_LOAD_MSG", request)%></DIV>
</DIV>

<%--
  Created by IntelliJ IDEA.
  User: ritchrs
  Date: 11-6-1
  Time: 上午10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<html>
<head>
    <title>代码生成器</title>
    <link href="<%=_contextPath_%>/resource/ext/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/css/layout-browser.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/resource/ext/ux/css/LockingGridView.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/resource/ext/plugins/Ext.ux.form.CheckboxCombo.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=_contextPath_%>/resource/css/ext_icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=_contextPath_%>/resource/ext/ux/css/Portal.css"/>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/adapter/ext/ext-base.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-all-debug.js" type="text/javascript"></script>
    <%--<script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-basex.js" type="text/javascript"></script>--%>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-lang-zh_CN.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/CheckColumn.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/LockingGridView.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/CheckSession.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/HttpRequest.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/Ext.ux.form.CheckboxCombo.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var _contextPath_ = "<%=_contextPath_%>";
        Ext.BLANK_IMAGE_URL = _contextPath_ + '/resource/ext/resources/images/default/s.gif';
        Ext.QuickTips.init();
    </script>
    <script language="javascript" src="<%=_contextPath_%>/codegen/codeGenerator.js"></script>
</head>
</html>
<%@ page import="org.eredlab.g4.arm.vo.UserInfoVo" %>
<%@ page import="org.eredlab.g4.ccl.datastructure.Dto" %>
<%@ page import="org.eredlab.g4.ccl.util.G4Utils" %>
<%@ page import="org.eredlab.g4.rif.util.WebUtils" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: ritchrs
  Date: 11-4-14
  Time: 下午5:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<%UserInfoVo userInfoVo = WebUtils.getSessionContainer(request).getUserInfo();%>
<html>
<head>
    <title><%=WebUtils.getParamValue("SYS_TITLE", request)%>
    </title>
    <link rel="icon" href="<%=_contextPath_%>/images/favicon.ico" type="image/x-icon" />
    <link href="<%=_contextPath_%>/resource/ext/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
    <link href="<%=_contextPath_%>/resource/ext/resources/css/xtheme-gray.css" rel="stylesheet" type="text/css"/>
    
    <%--<link href="<%=_contextPath_%>/resource/theme/lightGreen/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>--%>
    <link href="<%=_contextPath_%>/css/layout-browser.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=_contextPath_%>/resource/css/ext_icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=_contextPath_%>/resource/ext/ux/css/Portal.css"/>
    
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/adapter/ext/ext-base.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-all-debug.js"
            type="text/javascript"></script>
    <%--<script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-basex.js" type="text/javascript"></script>--%>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ext-lang-zh_CN.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/slidingtabs.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/IFrameComponent.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/TabScrollerMenu.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/commonjs/extTabCloseMenu.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/CheckSession.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/Vtype.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/plugins/Notification.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/Portlet.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/PortalColumn.js"
            type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/ux/Portal.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/PortalUI.js" type="text/javascript"></script>
    <script language="javascript" src="<%=_contextPath_%>/resource/ext/HttpRequest.js" type="text/javascript"></script>
    <script type="text/javascript">
        var _contextPath_ = "<%=_contextPath_%>";
        Ext.BLANK_IMAGE_URL = _contextPath_ + '/resource/ext/resources/images/default/s.gif';
        Ext.QuickTips.init();
        var style = '<%=userInfoVo!=null&&userInfoVo.getPortal()!=null?userInfoVo.getPortal():WebUtils.getParamValue("SYS_DEFAULT_PORTAL", request)%>';
        <%List list = WebUtils.getParamList(request);
           for (Object o : list) {
             Dto paramDto  = (Dto)o;
             String param = paramDto.getAsString("paramkey")+"='" +paramDto.getAsString("paramvalue")+"';";
             out.println(param);
           }
        %>
    </script>
    <script language="javascript" src="<%=_contextPath_%>/main.js"></script>
</head>
<body>

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
        LEFT: 45%;
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
        FONT: 12px "宋体", arial, helvetica;
        COLOR: #555;
        PADDING-TOP: 10px;
        HEIGHT: auto;
        TEXT-ALIGN: center
    }
</style>
<DIV id=loading-mask></DIV>
<DIV id=loading>
    <DIV class=loading-indicator>
        <IMG style="MARGIN-RIGHT: 8px" height=32 src="./resource/image/ajax1.gif" width=36 align=absMiddle alt=""/>正在初始化,请稍等...
    </DIV>
</DIV>
<script type="text/javascript">
    if (Ext.isIE) {
        document.getElementById('loading').style.display = "none";
        document.getElementById('loading-mask').style.display = "none";
    } else
        Ext.EventManager.on(window, 'load', function() {
            setTimeout(
                    function() {
                        Ext.get('loading').remove();
                        Ext.get('loading-mask').fadeOut({remove:true});
                    }, 200);
        });
</script>
<div id="header">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="310" valign="middle"><img src="images/logo.gif" alt=""></td>
            <td align="center" valign="middle">&nbsp;</td>
            <td align="right" width="353" valign="middle"
                style="background:url(images/rightTopBg.gif) left 0 no-repeat;">
                <div class="systemButton" align="right">
                    <a href="javascript:void(0);" id="changePassword"><img src="images/passwordModify1.gif" onmouseover="this.src='images/passwordModify.gif';" onmouseout="this.src='images/passwordModify1.gif';" border="0" style="display:inline;border: 0px;" alt="修改密码"/></a>
                    <a onclick="logout();" href="#"><img src="images/loginOut1.gif" width="56" height="22" onmouseover="this.src='images/loginOut.gif';" onmouseout="this.src='images/loginOut1.gif';" border="0" alt="注销"/></a>&nbsp;&nbsp;
                </div>
            </td>
        </tr>
    </table>
</div>
<div id="bottomer">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" class="bottom1">
        <tr>
            <td align="left" width="350">
                &nbsp;
                <img src="images/person.gif" align="absmiddle"/>
                <%if (userInfoVo != null) {%>
                欢迎您&nbsp;<%=userInfoVo.getUsername()%>&nbsp;所属机构：<%=userInfoVo.getDeptname()%>
                <%} else {%>
                "未登录"
                <%}%>
                <%--<%=userInfoVo != null ? "欢迎您&nbsp;" + userInfoVo.getUsername() : "未登录"%>&nbsp;--%>
            </td>
            <td align="center"><%=WebUtils.getParamValue("BOTTOM_COPYRIGHT", request)%>
            </td>
            <td width="350" align="right">交易日期 &nbsp;${TranDate}  &nbsp;&nbsp;|&nbsp;&nbsp;<%=G4Utils.getCurDate()%>&nbsp;<%=G4Utils.getWeekDayByDate(G4Utils.getCurDate())%>&nbsp;<span id="rTime"></span>&nbsp;&nbsp;
            </td>
        </tr>
    </table>
</div>
</body>
</html>
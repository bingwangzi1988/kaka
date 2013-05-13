<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<script type="text/javascript" src="<%=_contextPath_%>/dwr/engine.js"></script> 
<script type="text/javascript" src="<%=_contextPath_%>/dwr/util.js"></script> 
<script type="text/javascript">
 var _contextPath_ = "<%=_contextPath_%>";
</script>
<style type="text/css">
	.x-grid-back-red { 
		background: red; color: white;
	}
	.x-grid-back-yellow { 
		background: yellow; color: red;
	}
	.x-grid-back-green { 
		background: green; color: yellow;
	} 
    </style>
<eRedG4:html title="菜单资源管理">
<eRedG4:import src="/jsp/batch/batchexecmonitor.js"/>
<eRedG4:body onload="dwr.engine.setActiveReverseAjax(true);">
<eRedG4:div key="menuTreeDiv"></eRedG4:div>
<eRedG4:div key="menuGridDiv"></eRedG4:div>
</eRedG4:body>
<eRedG4:script>
   var root_menuname = '<eRedG4:out key="rootMenuName" scope="request"/>';
</eRedG4:script>
</eRedG4:html>
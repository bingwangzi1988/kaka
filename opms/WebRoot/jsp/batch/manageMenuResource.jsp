<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<script type="text/javascript">
 var _contextPath_ = "<%=_contextPath_%>";
</script>
<eRedG4:html title="菜单资源管理">
<eRedG4:import src="/arm/js/manageMenuResource.js"/>
<eRedG4:body>
<eRedG4:div key="menuTreeDiv"></eRedG4:div>
<eRedG4:div key="menuGridDiv"></eRedG4:div>
</eRedG4:body>
<eRedG4:script>
   var root_menuname = '<eRedG4:out key="rootMenuName" scope="request"/>';
</eRedG4:script>
</eRedG4:html>
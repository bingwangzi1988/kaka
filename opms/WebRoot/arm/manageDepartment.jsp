<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<% String _contextPath_ = request.getContextPath().equals("/") ? "" : request.getContextPath();%>
<script type="text/javascript">
 var _contextPath_ = "<%=_contextPath_%>";
</script>
<eRedG4:html title="���Ź���">
<eRedG4:import src="/arm/js/manageDepartment.js"/>
<eRedG4:body>
<eRedG4:div key="deptTreeDiv"></eRedG4:div>
<eRedG4:div key="deptGridDiv"></eRedG4:div>
</eRedG4:body>
<eRedG4:script>
   var root_deptid = '<eRedG4:out key="rootDeptid" scope="request"/>';
   var root_deptname = '<eRedG4:out key="rootDeptname" scope="request"/>';
</eRedG4:script>
</eRedG4:html>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="����2">
<eRedG4:import src="/demo/test/js/test2.js"/>
<eRedG4:body>
</eRedG4:body>
	<script type="text/javascript">
	window.onload = function(){
		var id = '<%=request.getAttribute("ID")%>';
		alert(id);
	}
	</script>
</eRedG4:html>
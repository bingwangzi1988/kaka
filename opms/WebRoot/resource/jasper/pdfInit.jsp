<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="eRedG4.Report:由海辉金信报表组件驱动">
<eRedG4:body>
</eRedG4:body>
<script language="JavaScript">
window.onload = function(){
    window.location.href = '<%=request.getAttribute("dataUrl")%>';
}
</script>

</eRedG4:html>
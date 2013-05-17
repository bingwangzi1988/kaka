<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="获取服务器信息及内存CPU实时监控" fcfEnabled="true">
<eRedG4:import src="/jsp/monitor/serverInfo3.js" />
<eRedG4:body>
	<eRedG4:flashReport type="L" dataVar="xmlString" id="OSChart" align="center" visible="false" />
	<eRedG4:flashReport type="L" dataVar="xmlString" id="MemChart" align="center" visible="false" />
	<eRedG4:flashReport type="L" dataVar="xmlString" id="CpuChart" align="center" visible="false" />
	<eRedG4:flashReport type="L" dataVar="xmlString" id="JavaChart" align="center" visible="false" />
</eRedG4:body>
<eRedG4:script>
<!-- var jsonInfo = <eRedG4:out key="jsonInfo" scope="request" />; -->
</eRedG4:script>
</eRedG4:html>
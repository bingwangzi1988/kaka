<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="����ۺ���ʾһ" uxEnabled="true" >
<eRedG4:import src="/demo/grid/js/gridDemo1.js" />
<eRedG4:ext.codeRender fields="QYBZ,SEX"/>
<%-- �Զ������и� 
<style type="text/css">
    .x-grid3-row{
        height:80px;
    }
</style>
--%>
<eRedG4:body>
<eRedG4:div key="gridDiv" ></eRedG4:div>

</eRedG4:body>
</eRedG4:html>
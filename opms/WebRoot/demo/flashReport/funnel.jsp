<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="FCF_©��ͼ" fcfEnabled="true">
<eRedG4:body>
	<eRedG4:flashReport type="F" dataVar="xmlString" id="myFunnelChart" width="250" align="center"
		visible="false" />
	<eRedG4:div key="myFunnelChart_panel_div" />
</eRedG4:body>
<script language="JavaScript">
Ext.onReady(function() {
	 var panel = new Ext.Panel({
        contentEl:'myFunnelChart_div',
        applyTo:'myFunnelChart_panel_div'
        });
     
		var window = new Ext.Window({
			layout : 'fit',
			width : 570,
			height : 390,
			resizable : false,
			draggable : true,
			closeAction : 'hide',
			title : 'Google���2010���¶�����ҵ��ͼ��',
			collapsible : true,
			titleCollapse : false,
			//������Ķ���Ч������ر�,���򽫳���Flashͼ���������������쳣������
			animCollapse : false,
			maximizable : true,
			border : false,
			closable : false,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [panel]
		});

     window.show();
});
</script>
</eRedG4:html>
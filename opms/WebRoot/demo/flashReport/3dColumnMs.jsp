<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="FCF_3d��״�ۺ�ͼ" fcfEnabled="true">
<eRedG4:body>
	<eRedG4:flashReport type="3DC_MS" dataVar="xmlString" id="my3Dc_MsChart" align="center"
		visible="false" />
	<eRedG4:div key="my3Dc_MsChart_panel_div" />
</eRedG4:body>
<script language="JavaScript">
Ext.onReady(function() {
	 var panel = new Ext.Panel({
        contentEl:'my3Dc_MsChart_div',
        applyTo:'my3Dc_MsChart_panel_div'
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
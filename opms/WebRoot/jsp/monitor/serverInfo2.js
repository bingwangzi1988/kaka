/**
 * ��ȡ��������Ϣ���ڴ�CPUʵʱ���
 * 
 * @author XiongChun
 * @since 2010-11-27
 */
Ext.onReady(function() {

//			var serverInfoGrid = new Ext.grid.PropertyGrid({
//						title : '<span class="commoncss">��������Ϣ</span>',
//						iconCls: 'server_connectIcon',
//						width : 320,
//						collapsible : true,
//						split : true,
//						region : 'west',
//						source : jsonInfo
//					});
//
//			serverInfoGrid.on("beforeedit", function(e) {
//						// e.cancel = true;
//						// return false;
//					});

			var jvmMemPanel = new Ext.Panel({
						title : '<span class="commoncss">JVM�ڴ�����ͼ</span>',
						contentEl : 'jvmMemChart_div',
						region : 'east',
						bodyStyle:'5,5,5,5',
						autoScroll:true
					});
			
			var hostCpuPanel = new Ext.Panel({
				title : '<span class="commoncss">����CPU�����ͼ</span>',
				contentEl : 'hostCpuChart_div',
				region : 'south',
				bodyStyle:'5,5,5,5',
				autoScroll:true
			});
			
			var hostMemPanel = new Ext.Panel({
				title : '<span class="commoncss">���������ڴ�����ͼ</span>',
				contentEl : 'hostMemChart_div',
				region : 'center',
				bodyStyle:'5,5,5,5',
				autoScroll:true
			});

			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [hostMemPanel, jvmMemPanel, hostCpuPanel]
					});

			var task = {
				run : function() {
					updateJVMChart();
					updateHostChart();
					updateHostCpu();
				},
				interval : 3000
			};

			var taskRunner = new Ext.util.TaskRunner();
			taskRunner.start(task);

			function updateJVMChart() {
				Ext.Ajax.request({
							url : 'jsp/monitor/ServerInfo_updateJvmChart.action',
							success : function(response, opts) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								var xmlstring = resultArray.xmlstring;
								updateChartXML('jvmMemChart', xmlstring);
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('��ʾ', '��ȡ�������ʧ��');
							},
							params : {}
						});
			}
			
			function updateHostChart() {
				Ext.Ajax.request({
							url : 'jsp/monitor/ServerInfo_updateHostMemChart.action',
							success : function(response, opts) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								var xmlstring = resultArray.xmlstring_hostmem;
								updateChartXML('hostMemChart', xmlstring);
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('��ʾ', '��ȡ�������ʧ��');
							},
							params : {}
						});
			}
			
			function updateHostCpu() {
				Ext.Ajax.request({
							url : 'jsp/monitor/ServerInfo_updateCpuChart.action',
							success : function(response, opts) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								var xmlstring = resultArray.xmlstring_cpu;
								updateChartXML('hostCpuChart', xmlstring);
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('��ʾ', '��ȡ�������ʧ��');
							},
							params : {}
						});
			}

		});
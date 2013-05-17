Ext.onReady(function() {

	 var hostOSGrid = new Ext.grid.PropertyGrid({
		 title : '<span class="commoncss">信息列表</span>',
		 iconCls: 'server_connectIcon',
//		 width : 320,
//		 height: 320,
//		 collapsible : true,
		 split : true,
		 region : 'center',
		 source : {'a':'b'}
	 });
	 var hostOSChartPanel = new Ext.Panel({
			title : '<span class="commoncss">监控视图</span>',
			contentEl : 'OSChart_div',
//			collapsible : true,
			split : true,
			width: 200,
			bodyStyle : '5,5,5,5',
			autoScroll : true,
			region:"south"
		});
	var hostOSPanel = new Ext.Panel({
		title : '<span class="commoncss">操作系统监控视图</span>',
		columnWidth : .25,
//		collapsible : true,
		height:window.screen.height-240,
		bodyStyle : '5,5,5,5',
		autoScroll : true,
		layout:"border",
		items:[  
		        hostOSGrid, hostOSChartPanel
		    ] 
	});

	 var hostMemGrid = new Ext.grid.PropertyGrid({
		 title : '<span class="commoncss">信息列表</span>',
		 iconCls: 'server_connectIcon',
//		 collapsible : true,
		 split : true,
		 region : 'center',
		 source : "{'a':'b'}"
	 });
	 var hostMemChartPanel = new Ext.Panel({
			title : '<span class="commoncss">监控视图</span>',
			contentEl : 'MemChart_div',
//			collapsible : true,
			split : true,
			width: 200,
			bodyStyle : '5,5,5,5',
			autoScroll : true,
			region:"south"
		});
	var hostMemPanel = new Ext.Panel({
		title : '<span class="commoncss">主机内存监控视图</span>',
		columnWidth : .25,
//		collapsible : true,
		height:window.screen.height-240,
		bodyStyle : '5,5,5,5',
		autoScroll : true,
		layout:"border",
		items:[  
		       hostMemGrid, hostMemChartPanel
		    ]
	});

	 var hostCpuGrid = new Ext.grid.PropertyGrid({
		 title : '<span class="commoncss">信息列表</span>',
		 iconCls: 'server_connectIcon',
		 width : 320,
		 height: 320,
//		 collapsible : true,
		 split : true,
		 region : 'center',
		 source : "{'a':'b'}"
	 });	 
	 var hostCpuChartPanel = new Ext.Panel({
			title : '<span class="commoncss">监控视图</span>',
			contentEl : 'CpuChart_div',
//			collapsible : true,
			split : true,
			width: 200,
			bodyStyle : '5,5,5,5',
			autoScroll : true,
			region:"south"
		});
	var hostCpuPanel = new Ext.Panel({
		title : '<span class="commoncss">主机CPU监控视图</span>',
		columnWidth : .25,
//		collapsible : true,
		height:window.screen.height-240,
		bodyStyle : '5,5,5,5',
		autoScroll : true,
		layout:"border",
		items:[  
		       hostCpuGrid, hostCpuChartPanel
		    ]
	});
	
	var hostJavaGrid = new Ext.grid.PropertyGrid({
		 title : '<span class="commoncss">信息列表</span>',
		 iconCls: 'server_connectIcon',
		 width : 320,
		 height: 320,
//		 collapsible : true,
		 split : true,
		 region : 'center',
		 source : "{'a':'b'}"
	 });
	var hostJavaChartPanel = new Ext.Panel({
		title : '<span class="commoncss">监控视图</span>',
		contentEl : 'JavaChart_div',
//		collapsible : true,
		split : true,
		width: 200,
		bodyStyle : '5,5,5,5',
		autoScroll : true,
		region:"south"
	});
	var hostJavaPanel = new Ext.Panel({
		title : '<span class="commoncss">主机Java监控视图</span>',
		columnWidth : .25,
//		collapsible : true,
		height:window.screen.height-240,
		bodyStyle : '5,5,5,5',
		autoScroll : true,
		layout:"border",
		items:[  
		       hostJavaGrid, hostJavaChartPanel
		    ]
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
				updateChartXML('MemChart', xmlstring);
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
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
				updateChartXML('OSChart', xmlstring);
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
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
				updateChartXML('CpuChart', xmlstring);
				updateChartXML('JavaChart', xmlstring);
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
			},
			params : {}
		});
	}

	var viewport = new Ext.Viewport({
		layout:'column',  
		items : [hostOSPanel, hostMemPanel, hostCpuPanel, hostJavaPanel]
	});

});
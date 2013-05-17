Ext.onReady(function() {

	var mainTabs = new Ext.TabPanel({
		region : 'center',
		enableTabScroll : true,
		// autoWidth : true,
		height : 400
	});
	
	Ext.Ajax.request({
		url : '/opms/jsp/osgi/OpmsSystemCfg_list.action',
		params : {
		},
		waitMsg : '正在查询主机,请稍候...',
		callback : function(opts, success, response) {
			var datas = response.responseText;
			var objs = Ext.decode(datas);
			try{
				var host = [{"hostIp":"127.0.0.1","hostPort":22225,"id":"40284e813db96046013dba39cb8b0000","state":"1","systemId":"core"},{"hostIp":"192.168.1.2","hostPort":22225,"id":"40284e813dc3877e013dc392ca460002","state":"1","systemId":"core"}];
				
				 host = eval(host);
				  for(var i=0; i<host.length; i++)  
				  {  
					  addTab(host[i].hostIp);
				  }
				  mainTabs.activate(0);
			}catch(err){
//				alert(err.message);
			}
			cmd='';
		}
	});
	var task = {
		run : function() {
//			updateJVMChart();
//			updateHostChart();
			try {
				id = mainTabs.getActiveTab().id;
//				alert(Ext.getCmp(id+"_subTabs").getActiveTab().id);
//				if(Ext.getCmp(id+"_subTabs").getActiveTab().id.indexOf("CPU") > 0) {
//					alert("ip: " + ip);
					updateHostCpu(id);
//				}
			} catch(err) {
			}
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

	function updateHostCpu(ip) {
		Ext.Ajax.request({
			url : 'jsp/monitor/ServerInfo_updateCpuChart.action',
			success : function(response, opts) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				var xmlstring = resultArray.xmlstring_cpu;
				if('hostCpu' == tabs.getActiveTab().id) {
					updateChartXML('CpuChart', xmlstring);
				}
//				updateChartXML('JavaChart', xmlstring);
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
			},
			params : {}
		});
	}

	
	
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [mainTabs]
			});
	
	function addTab(ip) {
		var tabs = new Ext.TabPanel({
			id: ip+"_subTabs",
			region : 'center',
			enableTabScroll : true,
			// autoWidth : true,
			height : 200
		});
		
		var hostOSGrid = new Ext.grid.PropertyGrid({
			title : '<span class="commoncss">信息列表</span>',
			iconCls: 'server_connectIcon',
			width : 320,
			collapsible : true,
			split : true,
			region : 'west',
			source : {}
		});
		var hostOSChartPanel = new Ext.Panel({
			title : '<span class="commoncss">监控视图</span>',
			contentEl : 'OSChart_div',
			region : 'center',
			bodyStyle:'5,5,5,5',
			autoScroll:true
		});
		var hostOSPanel = new Ext.Panel({
			id:ip+'_OS',
			title : '<span class="commoncss">操作系统</span>',
			autoScroll : true,
			layout : "border",
			items : [ hostOSGrid, hostOSChartPanel ]
		});
		
		var hostCpuGrid = new Ext.grid.PropertyGrid({
			 title : '<span class="commoncss">信息列表</span>',
			 iconCls: 'server_connectIcon',
			 width : 320,
			 collapsible : true,
			 split : true,
			 region : 'west',
			 source : {'a':'b'}
		 });	 
		 var hostCpuChartPanel = new Ext.Panel({
				title : '<span class="commoncss">监控视图</span>',
				contentEl : 'CpuChart_div',
				region : 'center',
				bodyStyle:'5,5,5,5',
				autoScroll:true
		});
		var hostCpuPanel = new Ext.Panel({
			id:ip+'_CPU',
			title : '<span class="commoncss">CPU</span>',
			autoScroll : true,
			layout : "border",
			items:[  
			       hostCpuGrid, hostCpuChartPanel
			    ]
		});

		var hostMemGrid = new Ext.grid.PropertyGrid({
			 title : '<span class="commoncss">信息列表</span>',
			 iconCls: 'server_connectIcon',
			 width : 320,
			 collapsible : true,
			 split : true,
			 region : 'west',
			 source : {'a':'b'}
		 });
		 var hostMemChartPanel = new Ext.Panel({
				title : '<span class="commoncss">监控视图</span>',
				contentEl : 'MemChart_div',
				region : 'center',
				bodyStyle:'5,5,5,5',
				autoScroll:true
			});
		var hostMemPanel = new Ext.Panel({
			id:ip+'_MEM',
			title : '<span class="commoncss">内存</span>',
			autoScroll : true,
			layout : "border",
			items:[  
			       hostMemGrid, hostMemChartPanel
			    ]
		});
		
		var hostJavaGrid = new Ext.grid.PropertyGrid({
			 title : '<span class="commoncss">信息列表</span>',
			 iconCls: 'server_connectIcon',
			 width : 320,
			 collapsible : true,
			 split : true,
			 region : 'west',
			 source : {'a':'b'}
		 });
		var hostJavaChartPanel = new Ext.Panel({
			title : '<span class="commoncss">监控视图</span>',
			contentEl : 'JavaChart_div',
			region : 'center',
			bodyStyle:'5,5,5,5',
			autoScroll:true
		});
		var hostJavaPanel = new Ext.Panel({
			id: ip+'_JAVA',
			title : '<span class="commoncss">Java环境</span>',
			autoScroll : true,
			layout : "border",
			items:[  
			       hostJavaGrid, hostJavaChartPanel
			    ]
		});
		
		// 每一个Tab都可以看作为一个Panel
		tabs.add(hostOSPanel);
		tabs.add(hostCpuPanel);
		tabs.add(hostMemPanel);
		tabs.add(hostJavaPanel);
		tabs.activate(0);
		
		mainTabs.add(new Ext.Panel({
			id:ip,
			title : '<span class="commoncss">' + ip + '</span>',
			autoScroll : true,
			layout : "border",
			items:[  
			       tabs
			    ]
		}));
	}
	
});	
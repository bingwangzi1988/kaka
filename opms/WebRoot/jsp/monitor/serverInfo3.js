Ext.onReady(function() {

	var mainTabs = new Ext.TabPanel({
		region : 'center',
		enableTabScroll : true,
		height : 300
	});
	
	Ext.Ajax.request({
		url : webContext + '/jsp/conmg/OpmsHostCfg_list.action',
		params : {
			start:0,
			limit:20
		},
		waitMsg : '正在查询主机,请稍候...',
		callback : function(opts, success, response) {
			var datas = response.responseText;
			var objs = Ext.decode(datas);
			try{
				var host = eval(objs.root);
				for(var i=0; i<host.length; i++){  
					addTab(host[i].hostIp);
				}
				mainTabs.activate(0);
			}catch(err){
//				alert(err.message);
			}
		}
	});
	
	var task = {
		run : function() {
			try {
				id = mainTabs.getActiveTab().id;
				var subId = Ext.getCmp(id+"_subTabs").getActiveTab().id; 
				if(subId.indexOf("os") > 0) {
					updateHostOs(id);
				}
				if(subId.indexOf("cpu") > 0) {
					updateHostCpu(id);
				}
				if(subId.indexOf("mem") > 0) {
					updateHostMem(id);
				}
				if(subId.indexOf("java") > 0) {
					updateHostJava(id);
				}
			} catch(err) {
			}
		},
		interval : 3000
	};

	var taskRunner = new Ext.util.TaskRunner();
	taskRunner.start(task);

//	function updateJVMChart() {
//		Ext.Ajax.request({
//			url : 'jsp/monitor/ServerInfo_updateJvmChart.action',
//			success : function(response, opts) {
//				var resultArray = Ext.util.JSON.decode(response.responseText);
//				var xmlstring = resultArray.xmlstring;
//				updateChartXML('mem_chart', xmlstring);
//			},
//			failure : function(response, opts) {
//				Ext.MessageBox.alert('提示', '获取监控数据失败');
//			},
//			params : {}
//		});
//	}
//
//	function updateHostChart() {
//		Ext.Ajax.request({
//			url : webContext+'/jsp/monitor/ServerInfo_updateHostMemChart.action',
//			success : function(response, opts) {
//				var resultArray = Ext.util.JSON.decode(response.responseText);
//				var xmlstring = resultArray.xmlstring_hostmem;
//				alert(xmlstring);
//				updateChartXML('mem_chart', xmlstring);
//			},
//			failure : function(response, opts) {
//				Ext.MessageBox.alert('提示', '获取监控数据失败');
//			},
//			params : {}
//		});
//	}

	function updateHostOs(ip) {
		Ext.Ajax.request({
			url : 'jsp/monitor/ServerInfo_updateHostOSChart.action',
			success : function(response, opts) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				var xmlstring = resultArray.xmlstring_hostos;
				id = mainTabs.getActiveTab().id;
				if(id == ip) {
					if(Ext.getCmp(id+"_subTabs").getActiveTab().id.indexOf("os") > 0) {
						updateChartXML(id+'_os_chart', xmlstring);
						osInfo = eval("(" + resultArray.OSInfo + ")");
						Ext.getCmp(ip+'_os_grid').setSource(osInfo);
					}
				}
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
			},
			params : {}
		});
	}

	function updateHostCpu(ip) {
		Ext.Ajax.request({
			url : 'jsp/monitor/ServerInfo_updateHostCPUChart.action',
			success : function(response, opts) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				var xmlstring = resultArray.xmlstring_hostcpu;
				id = mainTabs.getActiveTab().id;
				if(id == ip) {
					if(Ext.getCmp(id+"_subTabs").getActiveTab().id.indexOf("cpu") > 0) {
						updateChartXML(id+'_cpu_chart', xmlstring);
						cpuInfo = eval("(" + resultArray.CPUInfo + ")");
						Ext.getCmp(ip+'_cpu_grid').setSource(cpuInfo);
					}
				}
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
			},
			params : {}
		});
	}
	
	function updateHostMem(ip) {
		Ext.Ajax.request({
			url : 'jsp/monitor/ServerInfo_updateHostMEMChart.action',
			success : function(response, opts) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				var xmlstring = resultArray.xmlstring_hostmem;
				id = mainTabs.getActiveTab().id;
				if(id == ip) {
					if(Ext.getCmp(id+"_subTabs").getActiveTab().id.indexOf("mem") > 0) {
						updateChartXML(id+'_mem_chart', xmlstring);
						memInfo = eval("(" + resultArray.MEMInfo + ")");
						Ext.getCmp(ip+'_mem_grid').setSource(memInfo);
					}
				}
			},
			failure : function(response, opts) {
				Ext.MessageBox.alert('提示', '获取监控数据失败');
			},
			params : {}
		});
	}
	
	function updateHostJava(ip) {
		Ext.Ajax.request({
			url : 'jsp/monitor/ServerInfo_updateHostJAVAChart.action',
			success : function(response, opts) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				var xmlstring = resultArray.xmlstring_hostjava;
				id = mainTabs.getActiveTab().id;
				if(id == ip) {
					if(Ext.getCmp(id+"_subTabs").getActiveTab().id.indexOf("java") > 0) {
						updateChartXML(id+'_java_chart', xmlstring);
						javaInfo = eval("(" + resultArray.JAVAInfo + ")");
						Ext.getCmp(ip+'_java_grid').setSource(javaInfo);
					}
				}
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
		
		var os_chart_div = document.createElement("div"); 
		os_chart_div.setAttribute("id",ip+"_os_chart_div"); 
		document.body.appendChild(os_chart_div); 
		var os_chart_object = new FusionCharts(webContext+"/resource/fcf/FCF_Line.swf", ip+"_os_chart", "550", "350");
		os_chart_object.setDataXML("${reportXMLData}");
		os_chart_object.addParam("wmode","Opaque");
		os_chart_object.render(ip+"_os_chart_div");
		var hostOSGrid = new Ext.grid.PropertyGrid({
			id:ip+'_os_grid',
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
			contentEl:ip+'_os_chart_div',
			region : 'center',
			bodyStyle:'5,5,5,5',
			autoScroll:true
		});
		var hostOSPanel = new Ext.Panel({
			id:ip+'_os',
			title : '<span class="commoncss">操作系统</span>',
			autoScroll : true,
			layout : "border",
			items : [ hostOSGrid, hostOSChartPanel ]
		});
		
		var cpu_chart_div = document.createElement("div"); 
		cpu_chart_div.setAttribute("id",ip+"_cpu_chart_div"); 
		document.body.appendChild(cpu_chart_div); 
		var cpu_chart_object = new FusionCharts(webContext+"/resource/fcf/FCF_Line.swf", ip+"_cpu_chart", "550", "350");
		cpu_chart_object.setDataXML("${reportXMLData}");
		cpu_chart_object.addParam("wmode","Opaque");
		cpu_chart_object.render(ip+"_cpu_chart_div");
		var hostCpuGrid = new Ext.grid.PropertyGrid({
			id:ip+'_cpu_grid',
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
				contentEl : ip+'_cpu_chart_div',
				region : 'center',
				bodyStyle:'5,5,5,5',
				autoScroll:true
		});
		var hostCpuPanel = new Ext.Panel({
			id:ip+'_cpu',
			title : '<span class="commoncss">CPU</span>',
			autoScroll : true,
			layout : "border",
			items:[  
			       hostCpuGrid, hostCpuChartPanel
			    ]
		});

		var mem_chart_div = document.createElement("div"); 
		mem_chart_div.setAttribute("id",ip+"_mem_chart_div"); 
		document.body.appendChild(mem_chart_div); 
		var mem_chart_object = new FusionCharts(webContext+"/resource/fcf/FCF_Line.swf", ip+"_mem_chart", "550", "350");
		mem_chart_object.setDataXML("${reportXMLData}");
		mem_chart_object.addParam("wmode","Opaque");
		mem_chart_object.render(ip+"_mem_chart_div");
		var hostMemGrid = new Ext.grid.PropertyGrid({
			id:ip+'_mem_grid',
			 title : '<span class="commoncss">信息列表</span>',
			 iconCls: 'server_connectIcon',
			 width : 320,
			 collapsible : true,
			 split : true,
			 region : 'west'
		 });
		 var hostMemChartPanel = new Ext.Panel({
				title : '<span class="commoncss">监控视图</span>',
				contentEl : ip+'_mem_chart_div',
				region : 'center',
				bodyStyle:'5,5,5,5',
				autoScroll:true
			});
		var hostMemPanel = new Ext.Panel({
			id:ip+'_mem',
			title : '<span class="commoncss">内存</span>',
			autoScroll : true,
			layout : "border",
			items:[  
			       hostMemGrid, hostMemChartPanel
			    ]
		});
		
		var java_chart_div = document.createElement("div"); 
		java_chart_div.setAttribute("id",ip+"_java_chart_div"); 
		document.body.appendChild(java_chart_div); 
		var java_chart_object = new FusionCharts(webContext+"/resource/fcf/FCF_Line.swf", ip+"_java_chart", "550", "350");
		java_chart_object.setDataXML("${reportXMLData}");
		java_chart_object.addParam("wmode","Opaque");
		java_chart_object.render(ip+"_java_chart_div");
		var hostJavaGrid = new Ext.grid.PropertyGrid({
			id:ip+'_java_grid',
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
			contentEl : ip+'_java_chart_div',
			region : 'center',
			bodyStyle:'5,5,5,5',
			autoScroll:true
		});
		var hostJavaPanel = new Ext.Panel({
			id: ip+'_java',
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
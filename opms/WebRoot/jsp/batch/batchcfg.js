Ext.onReady(function() {

	var stepExecutorClassStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'jsp/batch/BatchJobGroup_listClass.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	stepExecutorClassStore.load();
	
	var jobGrpTitle = '作业组';
	var jobTitle = '作业';
	var stepTitle = '步骤';
	
    var jobGrpGrid = new Ext.ux.FunctionGrid({
        title:jobGrpTitle + "列表",
        searchColumn:3,
        autoSearch:true,
        dataId:"id",
        columns:[
			{header:'ID',sortable:true,dataIndex:'id',hidden:true
			},
	        {header:'名称',sortable:true,dataIndex:'name'
             },
	        {header:'描述',sortable:true,dataIndex:'jobGroupDesc'
             },
 	        {
               	 header:jobTitle + '管理',
               	 sortable:true,
               	 dataIndex:'jobMng',
               	 renderer : function() {
   							return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
   				 }
            }
        ],
        stripeRows:true,
        columnLines:true,
        winWidth: 650,
        winHeight:680,
        listAction:"jsp/batch/BatchJobGroup_list.action",
        addTitle:"增加"+jobGrpTitle,
        editTitle:"编辑"+jobGrpTitle,
        viewTitle:"查看"+jobGrpTitle,
        initEditAction:"jsp/batch/BatchJobGroup_find",
        saveAction:"jsp/batch/BatchJobGroup_save",
        deleteAction:"jsp/batch/BatchJobGroup_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":jobGrpTitle+"名称","name":"name","xtype":"textfield"}
            ],
        formSet:[
                {xtype:'hidden',fieldLabel:'id',name:'id'},
                {
                layout:'column',
                items:[
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"名称","maxLength":32,"name":"name","xtype":"textfield"}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":true,"anchor":"95%","fieldLabel":"描述","maxLength":64,"name":"jobGroupDesc","xtype":"textfield"}
                        ]
                    }
                ]
            }
        ]
    });
    
    jobGrpGrid.on("cellclick",
			function(grid, rowIndex, columnIndex, e) {
				var store = grid.getStore();
				var record = store.getAt(rowIndex);
				var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
				
				if (fieldName == 'jobMng' && columnIndex == 3) {
					jobMng(record);
				}
			});

    function jobMng(record) {
    	var jobGrpId = record.get("id");
    	var jobGrid = new Ext.ux.FunctionGrid({
            title:jobTitle + "列表",
            searchColumn:3,
            autoSearch:true,
            dataId:"id",
            columns:[
				{header:'ID',sortable:true,dataIndex:'id',hidden:true
				},
    	        {header:'名称',sortable:true,dataIndex:'name'
                 },
    	        {header:'描述',sortable:true,dataIndex:'jobDesc'
                 },
    	        {header:'jobGroupId',sortable:true,dataIndex:'jobGroupId',hidden:true
                 },
    	        {header:'优先级',sortable:true,dataIndex:'priority'
                 },
    	        {header:'主机名',sortable:true,dataIndex:'hostName'
                 },
      	        {
                   	 header:'步骤管理',
                   	 sortable:true,
                   	 dataIndex:'stepMng',
                   	 renderer : function() {
       							return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
       				 }
                    }
            ],
            stripeRows:true,
            columnLines:true,
            winWidth: 650,
            winHeight:650,
            listAction:"jsp/batch/BatchJob_list.action?jobGroupId=" + jobGrpId,
            addTitle:"增加" + jobTitle,
            editTitle:"编辑" + jobTitle,
            viewTitle:"查看" + jobTitle,
            initEditAction:"jsp/batch/BatchJob_find",
            saveAction:"jsp/batch/BatchJob_save",
            deleteAction:"jsp/batch/BatchJob_delete",
            searchSet:[
                    {"anchor":"95%","fieldLabel":"作业名称","name":"name","xtype":"textfield"}
                ],
            formSet:[
                   {xtype:'hidden',fieldLabel:'id',name:'id'},
                    {
                    layout:'column',
                    items:[
                        {
                            columnWidth:.5,
                            layout: 'form',
                            items: [
                                   {"allowBlank":false,"anchor":"95%","fieldLabel":"名称","maxLength":32,"name":"name","xtype":"textfield"},
                                   {"allowBlank":false,"anchor":"95%","fieldLabel":"jobGroupId","maxLength":32,"name":"jobGroupId","xtype":"hidden", "value": jobGrpId},
                                   {"allowBlank":true,"anchor":"95%","fieldLabel":"主机名","maxLength":19,"name":"hostName","xtype":"textfield"}
                            ]
                        },
                        {
                            columnWidth:.5,
                            layout: 'form',
                            items: [
                                   {"allowBlank":true,"anchor":"95%","fieldLabel":"描述","maxLength":64,"name":"jobDesc","xtype":"textfield"},
                                   {"allowBlank":false,"allowDecimals":false,"anchor":"95%","fieldLabel":"优先级","name":"priority","xtype":"numberfield"}
                            ]
                        }
                    ]
                }
            ]
        });

		
    	jobGrid.on("cellclick",
				function(grid, rowIndex, columnIndex, e) {
					var store = grid.getStore();
					var record = store.getAt(rowIndex);
					var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
					if (fieldName == 'stepMng' && columnIndex == 6) {
						stepMng(record);
					}
					
				});
    	var jobWin = new Ext.Window({
//			layout : 'fit',
			width:window.screen.width-250,
			height:window.screen.height-340,
			closable : true,
			constrain : false,
			modal : true,
			title : '[' + record.get('name') + ']' + jobTitle + '管理',
			layout:'border',
			closeAction : 'close',
			plain : true,
			items : [jobGrid.panels]
		});
		jobWin.show();
		
    	function stepMng(record) {
    		var jobId = record.get('id');
    		var stepGrid = new Ext.ux.FunctionGrid({
    	        title:stepTitle+"列表",
    	        searchColumn:3,
    	        autoSearch:true,
    	        dataId:"id",
    	        columns:[
    		        {header:'名称',sortable:true,dataIndex:'name'
    	             },
    		        {header:'描述',sortable:true,dataIndex:'stepDesc'
    	             },
    		        {header:'jobId',sortable:true,dataIndex:'jobId', hidden:true
    	             },
    		        {header:'优先级',sortable:true,dataIndex:'priority'
    	             },
    		        {header:'线程数',sortable:true,dataIndex:'maxThread'
    	             },
    		        {
    	            	 header:'执行类',
    	            	 sortable:true,
    	            	 dataIndex:'clazz'
    	             }
    	        ],
    	        stripeRows:true,
    	        columnLines:true,
    	        winWidth: 650,
    	        winHeight:630,
    	        listAction:"jsp/batch/BatchStep_list.action?jobId=" + jobId,
    	        addTitle:"增加" + stepTitle,
    	        editTitle:"编辑" + stepTitle,
    	        viewTitle:"查看" + stepTitle,
    	        initEditAction:"jsp/batch/BatchStep_find",
    	        saveAction:"jsp/batch/BatchStep_save",
    	        deleteAction:"jsp/batch/BatchStep_delete",
    	        searchSet:[
    	                {"anchor":"95%","fieldLabel":stepTitle+"名称","name":"name","xtype":"textfield"}
    	            ],
    	        formSet:[
    	               {xtype:'hidden',fieldLabel:'id',name:'id'},
    	                {
    	                layout:'column',
    	                items:[
    	                    {
    	                        columnWidth:.5,
    	                        layout: 'form',
    	                        items: [
    	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"名称","maxLength":64,"name":"name","xtype":"textfield"},
    	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"jobId","maxLength":32,"name":"jobId","xtype":"hidden", "value": jobId},
    	                               {"allowBlank":true,"allowDecimals":false,"anchor":"95%","fieldLabel":"线程数","name":"maxThread","xtype":"numberfield"}
    	                        ]
    	                    },
    	                    {
    	                        columnWidth:.5,
    	                        layout: 'form',
    	                        items: [
    	                               {"allowBlank":true,"anchor":"95%","fieldLabel":"描述","maxLength":32,"name":"stepDesc","xtype":"textfield"},
    	                               {"allowBlank":false,"allowDecimals":false,"anchor":"95%","fieldLabel":"优先级","name":"priority","xtype":"numberfield"}
    	                        ]
    	                    }
    	                ]
    	            },
                    {"allowBlank":false,"anchor":"98%","fieldLabel":"执行类", "maxLength":255,"name":"clazz","xtype":"dcombo",store:stepExecutorClassStore}
    	        ]
    	    });
    		var stepWin = new Ext.Window({
//			layout : 'fit',
    			width:window.screen.width-350,
    			height:window.screen.height-440,
    			closable : true,
    			constrain : false,
    			modal : true,
    			title : '[' + record.get('name') + ']' + stepTitle + '管理',
    			layout:'border',
    			closeAction : 'close',
    			plain : true,
    			items : [stepGrid.panels]
    		});
    		stepWin.show();
    	}
    }
    
    new Ext.Viewport({
            layout:'border',
            items:[jobGrpGrid.panels]
        });
 });
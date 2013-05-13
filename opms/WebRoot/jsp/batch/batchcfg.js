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
	
	var jobGrpTitle = '��ҵ��';
	var jobTitle = '��ҵ';
	var stepTitle = '����';
	
    var jobGrpGrid = new Ext.ux.FunctionGrid({
        title:jobGrpTitle + "�б�",
        searchColumn:3,
        autoSearch:true,
        dataId:"id",
        columns:[
			{header:'ID',sortable:true,dataIndex:'id',hidden:true
			},
	        {header:'����',sortable:true,dataIndex:'name'
             },
	        {header:'����',sortable:true,dataIndex:'jobGroupDesc'
             },
 	        {
               	 header:jobTitle + '����',
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
        addTitle:"����"+jobGrpTitle,
        editTitle:"�༭"+jobGrpTitle,
        viewTitle:"�鿴"+jobGrpTitle,
        initEditAction:"jsp/batch/BatchJobGroup_find",
        saveAction:"jsp/batch/BatchJobGroup_save",
        deleteAction:"jsp/batch/BatchJobGroup_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":jobGrpTitle+"����","name":"name","xtype":"textfield"}
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
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"����","maxLength":32,"name":"name","xtype":"textfield"}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":true,"anchor":"95%","fieldLabel":"����","maxLength":64,"name":"jobGroupDesc","xtype":"textfield"}
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
            title:jobTitle + "�б�",
            searchColumn:3,
            autoSearch:true,
            dataId:"id",
            columns:[
				{header:'ID',sortable:true,dataIndex:'id',hidden:true
				},
    	        {header:'����',sortable:true,dataIndex:'name'
                 },
    	        {header:'����',sortable:true,dataIndex:'jobDesc'
                 },
    	        {header:'jobGroupId',sortable:true,dataIndex:'jobGroupId',hidden:true
                 },
    	        {header:'���ȼ�',sortable:true,dataIndex:'priority'
                 },
    	        {header:'������',sortable:true,dataIndex:'hostName'
                 },
      	        {
                   	 header:'�������',
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
            addTitle:"����" + jobTitle,
            editTitle:"�༭" + jobTitle,
            viewTitle:"�鿴" + jobTitle,
            initEditAction:"jsp/batch/BatchJob_find",
            saveAction:"jsp/batch/BatchJob_save",
            deleteAction:"jsp/batch/BatchJob_delete",
            searchSet:[
                    {"anchor":"95%","fieldLabel":"��ҵ����","name":"name","xtype":"textfield"}
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
                                   {"allowBlank":false,"anchor":"95%","fieldLabel":"����","maxLength":32,"name":"name","xtype":"textfield"},
                                   {"allowBlank":false,"anchor":"95%","fieldLabel":"jobGroupId","maxLength":32,"name":"jobGroupId","xtype":"hidden", "value": jobGrpId},
                                   {"allowBlank":true,"anchor":"95%","fieldLabel":"������","maxLength":19,"name":"hostName","xtype":"textfield"}
                            ]
                        },
                        {
                            columnWidth:.5,
                            layout: 'form',
                            items: [
                                   {"allowBlank":true,"anchor":"95%","fieldLabel":"����","maxLength":64,"name":"jobDesc","xtype":"textfield"},
                                   {"allowBlank":false,"allowDecimals":false,"anchor":"95%","fieldLabel":"���ȼ�","name":"priority","xtype":"numberfield"}
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
			title : '[' + record.get('name') + ']' + jobTitle + '����',
			layout:'border',
			closeAction : 'close',
			plain : true,
			items : [jobGrid.panels]
		});
		jobWin.show();
		
    	function stepMng(record) {
    		var jobId = record.get('id');
    		var stepGrid = new Ext.ux.FunctionGrid({
    	        title:stepTitle+"�б�",
    	        searchColumn:3,
    	        autoSearch:true,
    	        dataId:"id",
    	        columns:[
    		        {header:'����',sortable:true,dataIndex:'name'
    	             },
    		        {header:'����',sortable:true,dataIndex:'stepDesc'
    	             },
    		        {header:'jobId',sortable:true,dataIndex:'jobId', hidden:true
    	             },
    		        {header:'���ȼ�',sortable:true,dataIndex:'priority'
    	             },
    		        {header:'�߳���',sortable:true,dataIndex:'maxThread'
    	             },
    		        {
    	            	 header:'ִ����',
    	            	 sortable:true,
    	            	 dataIndex:'clazz'
    	             }
    	        ],
    	        stripeRows:true,
    	        columnLines:true,
    	        winWidth: 650,
    	        winHeight:630,
    	        listAction:"jsp/batch/BatchStep_list.action?jobId=" + jobId,
    	        addTitle:"����" + stepTitle,
    	        editTitle:"�༭" + stepTitle,
    	        viewTitle:"�鿴" + stepTitle,
    	        initEditAction:"jsp/batch/BatchStep_find",
    	        saveAction:"jsp/batch/BatchStep_save",
    	        deleteAction:"jsp/batch/BatchStep_delete",
    	        searchSet:[
    	                {"anchor":"95%","fieldLabel":stepTitle+"����","name":"name","xtype":"textfield"}
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
    	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"����","maxLength":64,"name":"name","xtype":"textfield"},
    	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"jobId","maxLength":32,"name":"jobId","xtype":"hidden", "value": jobId},
    	                               {"allowBlank":true,"allowDecimals":false,"anchor":"95%","fieldLabel":"�߳���","name":"maxThread","xtype":"numberfield"}
    	                        ]
    	                    },
    	                    {
    	                        columnWidth:.5,
    	                        layout: 'form',
    	                        items: [
    	                               {"allowBlank":true,"anchor":"95%","fieldLabel":"����","maxLength":32,"name":"stepDesc","xtype":"textfield"},
    	                               {"allowBlank":false,"allowDecimals":false,"anchor":"95%","fieldLabel":"���ȼ�","name":"priority","xtype":"numberfield"}
    	                        ]
    	                    }
    	                ]
    	            },
                    {"allowBlank":false,"anchor":"98%","fieldLabel":"ִ����", "maxLength":255,"name":"clazz","xtype":"dcombo",store:stepExecutorClassStore}
    	        ]
    	    });
    		var stepWin = new Ext.Window({
//			layout : 'fit',
    			width:window.screen.width-350,
    			height:window.screen.height-440,
    			closable : true,
    			constrain : false,
    			modal : true,
    			title : '[' + record.get('name') + ']' + stepTitle + '����',
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
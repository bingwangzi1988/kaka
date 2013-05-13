Ext.onReady(function() {

	var batchStatusStore = new Ext.data.Store( {
		baseParams : {
			code : 'BATCHSTATUS'
		},
		proxy : new Ext.data.HttpProxy( {
			url : _contextPath_ + '/codeSelect.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	batchStatusStore.load();
	
	var batchHostStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'jsp/batch/BatchJobgrpExecution_listHost.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	batchHostStore.load();
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	
	var cmWidth = (window.screen.width-280)/6;
	// ������ģ��
	var cm = new Ext.grid.ColumnModel([
	                                   sm,
	                                   {header:'ID',dataIndex:'id', hidden:true},
	                                   {header:'���κ�',sortable:true,dataIndex:'batchNo',width: cmWidth},
	                                   {header:'����������',sortable:true,dataIndex:'name',width: cmWidth},
	                                   {header:'IP��ַ',sortable:true,dataIndex:'ipAddr',width: cmWidth},
	                                   {header:'��ʼʱ��',sortable:true,dataIndex:'startTime',width: cmWidth},
	                                   {header:'����ʱ��',sortable:true,dataIndex:'endTime',width: cmWidth},
	                                   {header:'ִ��״̬',sortable:true,dataIndex:'status',width: cmWidth,
	                   					renderer : function(value, m) {
	                	  	 				var returnValue;
	                	  	 				batchStatusStore.each(function(record) {
	                	  	 					if (record.get('code') != value) {
	                	  	 					} else {
	                	  	 						returnValue = record.get('codedesc');
	                	  	 						return false;
	                	  	 					}
	                	  	 				});
	                	  	 				if (value == 'S') {
	                							m.css = 'x-grid-back-green';
	                						} else if (value == 'F') {
	                							m.css = 'x-grid-back-red';
	                						} else {
	                							m.css = 'x-grid-back-yellow';
	                						}
	                	  	 				return returnValue;
	                	  	 			}}
	                                  ]
									);
	// ���ݴ洢
	var store = new Ext.data.Store({
		// ��ȡ���ݵķ�ʽ
		proxy : new Ext.data.HttpProxy({
					url : 'jsp/opms/batch/BatchJobgrpExecution_list.action'
				}),
		// ���ݶ�ȡ��
		reader : new Ext.data.JsonReader({
					totalProperty : 'total', // ��¼����
					root : 'root' // Json�е��б����ݸ��ڵ�
				}, [{
							name : 'id' // Json�е�����Keyֵ
						}, {
							name : 'name'
						}, {
							name : 'batchNo'
						}, {
							name : 'ipAddr'
						}, {
							name : 'startTime'
						}, {
							name : 'endTime'
						}, {
							name : 'status'
						}])
			});
	
	// ��ҳ����ʱ���ϲ�ѯ����
	store.on('beforeload', function() {
				this.baseParams = {
						batchNo : Ext.getCmp('batchNo').getValue(),
						startTimeBegin : Ext.getCmp('startTimeBegin').getValue()
//						clrinstno : Ext.getCmp('clrinstno').getValue(),
//						dealtype : Ext.getCmp('dealtype').getValue(),
//						dealflag : Ext.getCmp('dealFlag').getValue()
				};
			});
	
	// ÿҳ��ʾ��������ѡ���
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10��/ҳ'], [20, '20��/ҳ'], [50, '50��/ҳ'], [100, '100��/ҳ'], [250, '250��/ҳ'], [500, '500��/ҳ']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});
	var number = parseInt(pagesize_combo.getValue());
	// �ı�ÿҳ��ʾ����reload����
	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(comboBox.getValue());
				number = parseInt(comboBox.getValue());
				store.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			});
	
	// ��ҳ������
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '��ʾ{0}����{1}��,��{2}��',
				plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
				emptyMsg : "û�з��������ļ�¼",
				items : [
				         '-', 
				         '&nbsp;&nbsp;', 
				         pagesize_combo
				       ]
			});
	// ��񹤾���
	var tbar = new Ext.Toolbar({
				items : [
				         {xtype:'label', text : '���κ� ', width: 80},
				         {
							xtype : 'textfield',
							id : 'batchNo',
							name : 'batchNo',
							width : 140
						},
						{xtype:'tbseparator'},
						{xtype:'label',text : 'ִ������'},
						{
							xtype : 'datefield',
							id : 'startTimeBegin',
							name : 'startTimeBegin',
							width : 150,
							format:"Y-m-d"
						},
						{
							text : '<span style="color: green; font-weight: bold;">��ѯ</span>',
							iconCls : 'page_findIcon',
							handler : function() {
								queryCatalogItem();
							}
						},
						{xtype:'tbseparator'},
						{
							text : '<span style="color: green; font-weight: bold;">����</span>',
//							style: 'background-color: yellow;',
							iconCls : 'runitIcon',
							handler : function() {
								continueRunJobGrp();
							}
						}
						]
			});
	
	var batchJobGrpGrid = new Ext.grid.GridPanel({
		// ���������,Ĭ��Ϊ���壬�Ҳ�ϲ�����壬����������ʽ�����ʽΪ��������
		title : '������ִ���б�',
//		renderTo : 'gridDiv', // ��JSPҳ���DIVԪ��ID��Ӧ
		height : 500,
		width: window.screen.width-300,
		frame : true,
		autoScroll : true,
		region : 'center', // ��VIEWPORT����ģ�Ͷ�Ӧ���䵱center���򲼾�
		store : store, // ���ݴ洢
		stripeRows : true, // ������
		cm : cm, // ��ģ��
		sm: sm,
		columnLines:true,
		tbar : tbar, // ��񹤾���
		bbar : bbar,// ��ҳ������
		loadMask : {
			msg : '���ڼ��ر������,���Ե�...'
		}
	});

 // �Ƿ�Ĭ��ѡ�е�һ������
	bbar.on("change", function() {
				// grid.getSelectionModel().selectFirstRow();

			});
	
	// ��ѯ�������
	function queryCatalogItem() {
		store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						batchNo : Ext.getCmp('batchNo').getValue(),
						startTimeBegin : Ext.getCmp('startTimeBegin').getValue()
					}
				});
	}
	
	// ��ѯ�������
	function continueRunJobGrp() {
		var c = batchJobGrpGrid.getSelectionModel().getSelections();
		if(c.length == 1){
			var record = batchJobGrpGrid.getSelectionModel().getSelected();
			if('F'==record.get('status')) {
				var continueRunform = new Ext.form.FormPanel({
	//		    	title:'��������',  
			    	labelWidth:0.1,  
			    	bodyStyle:'padding:5 5 5 5',
			    	labelWidth : 65, 
			    	defaultType : 'textfield', // ��Ԫ��Ĭ������
			    	frame:true,  
			    	height:210,  
			    	width:300,  
			    	region:'center',
	//		    	collapsible:true,
			    	items:[
							{
								fieldLabel : '���κ�', // ��ǩ
								id : 'batNo',
								name : 'batNo',
								value: record.get('batchNo'),
								readOnly: true,
								anchor : '100%' // ��Ȱٷֱ�
							},
							{
								fieldLabel : '������', // ��ǩ
								id : 'name',
								name : 'name',
								value: record.get('name'),
								readOnly: true,
								anchor : '100%' // ��Ȱٷֱ�
							},
							{
								fieldLabel : '���ܻ���', // ��ǩ
								id : 'hostIp',
								name : 'hostIp',
								"xtype":"dcombo",
								store: batchHostStore,
								allowBlank: false,
								anchor : '100%' // ��Ȱٷֱ�
							}
			    	      ],
			    	buttons:[  
			    	           {
			    	        	   text:'ִ������',
			    	        	   iconCls : 'runitIcon',
			    	        	   handler : function() {
			    	        		   if(continueRunform.getForm().isValid()) {
			    							Ext.Msg.confirm('��ʾ', 'ȷ��ִ�д˲���?', function(button, text) {
			    								if (button == 'yes') {
			    									var params = continueRunform.getForm().getValues();
			    							    	Ext.Ajax.request({
			    								 		   url:'jsp/opms/batch/BatchJobgrpExecution_continueRun.action',
			    								 		   params:params,
			    								 		   callback:function(opts,success,response){
			    								 			   var datas=response.responseText;
			    								 			   var objs=Ext.decode(datas);
			    								 			   if(objs.success){
			    								 				  Ext.MessageBox.alert('��ʾ', objs.msg);
			    								 			   } else {
			    								 				  Ext.MessageBox.show({
			    							                          title: '��ʾ',
			    							                          msg: objs.msg,
			    							                          buttons: Ext.MessageBox.OK,
			    							                          icon: Ext.MessageBox.WARNING
			    							                      });
			    								 			   }
			    								 			  jobWin.close();
			    								 			  batchJobGrpGrid.store.reload();
			    								 		   }
			    								 	   });
			    								}
			    							});
			    						}
			    	   			   }
			    	           }  
			    	        ]  
			    });
				var jobWin = new Ext.Window({
	//				layout : 'fit',
					width:350,
					height:240,
					closable : true,
					constrain : false,
					modal : true,
					title : '<span style="color: red; font-weight:bold;">��������</span>',
					layout:'border',
					closeAction : 'close',
					plain : true,
					items : [continueRunform]
				});
				jobWin.show();
			} else {
				Ext.MessageBox.show({
					title: '����',
					msg: "ֻ��ִ��ʧ�ܲ�������",
					buttons: Ext.MessageBox.OK,
					icon: Ext.MessageBox.WARNING
	        	});
			}
		}
		else
		{
			Ext.MessageBox.show({
				title: '����',
				msg: "��ѡ��һ������",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.WARNING
        	});
		}
	}

	// ҳ���ʼ�Զ���ѯ����
	store.load({params : {start : 0,limit : bbar.pageSize}});
	
    new Ext.Viewport({
            layout:'border',
            items:[batchJobGrpGrid]
        });
    
 });
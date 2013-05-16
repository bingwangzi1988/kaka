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
	
	var root = new Ext.tree.AsyncTreeNode( {
		text : '��������',
		iconCls:'runitIcon',
		expanded : true,
		id : '00'
	});
	var menuTree = new Ext.tree.TreePanel( {
		loader : new Ext.tree.TreeLoader( {
			baseAttrs : {},
			dataUrl : 'jsp/opms/batch/BatchJobgrpExecution_listJobgrp.action'
		}),
		root : root,
		title : '',
		applyTo : 'menuTreeDiv',
		autoScroll : false,
		animate : false,
		useArrows : false,
		border : false
	});

	menuTree.on('click', function(node) {
		menuid = node.attributes.id;
		menuname = node.attributes.text;
		menutype = node.attributes.type;
		Ext.getCmp('parentmenuname').setValue(menuname);
//		Ext.getCmp('parentid').setValue(menuid);
		store.load( {
			params : {
//				start : 0,
//				limit : bbar.pageSize,
				menuid : menuid,
				menuname: menuname,
				menutype: menutype
			}
		});
	});
	menuTree.root.select();
	
	var cm = new Ext.grid.ColumnModel( [ 
	{
		header : '����',
		dataIndex : 'name',
		width : 130
	}, {
		header : '����',
		dataIndex : 'type',
		width : 50
	}, {
		header : 'ID',
		dataIndex : 'id',
		width : 60
	}, {
		header : 'PARENTID',
		dataIndex : 'parentid',
		hidden: true,
		width : 80
	}, {
		header : '���κ�',
		dataIndex : 'batchNo',
		sortable : true,
		width : 110
	}, {
		header : '��ʼʱ��',
		dataIndex : 'starttime',
		width : 130
	}, {
		header : '����ʱ��',
		dataIndex : 'endtime',
		width : 130
	}, {
		header : 'ִ��״̬',
		dataIndex : 'status',
		width : 120,
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
			}
	}, {
		id : 'errmsg',
		header : '�쳣��Ϣ',
		dataIndex : 'errmsg'
	}, {
		header : '������ַ',
		dataIndex : 'ipAddr',
		width : 100
	}]);

	/**
	 * ���ݴ洢
	 */
	var store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'jsp/opms/batch/BatchJobgrpExecution_list3.action'
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty : 'TOTALCOUNT',
			root : 'ROOT'
		}, [ {
			name : 'name'
		}, {
			name : 'type'
		}, {
			name : 'id'
		}, {
			name : 'parentid'
		}, {
			name : 'batchNo'
		}, {
			name : 'starttime'
		}, {
			name : 'endtime'
		}, {
			name : 'status'
		}, {
			name : 'errmsg'
		}, {
			name : 'ipAddr'
		} ])
	});

	// ��ҳ����ʱ���ϲ�ѯ����
		store.on('beforeload', function() {
			this.baseParams = {
//				queryParam : Ext.getCmp('queryParam').getValue()
			};
		});

//		var pagesize_combo = new Ext.form.ComboBox( {
//			name : 'pagesize',
//			hiddenName : 'pagesize',
//			typeAhead : true,
//			triggerAction : 'all',
//			lazyRender : true,
//			mode : 'local',
//			store : new Ext.data.ArrayStore(
//					{
//						fields : [ 'value', 'text' ],
//						data : [ [ 10, '10��/ҳ' ], [ 20, '20��/ҳ' ],
//								[ 50, '50��/ҳ' ], [ 100, '100��/ҳ' ],
//								[ 250, '250��/ҳ' ], [ 500, '500��/ҳ' ] ]
//					}),
//			valueField : 'value',
//			displayField : 'text',
//			value : '50',
//			editable : false,
//			width : 85
//		});
//		var number = parseInt(pagesize_combo.getValue());
//		pagesize_combo.on("select", function(comboBox) {
//			bbar.pageSize = parseInt(comboBox.getValue());
//			number = parseInt(comboBox.getValue());
//			store.reload( {
//				params : {
//					start : 0,
//					limit : bbar.pageSize
//				}
//			});
//		});

//		var bbar = new Ext.PagingToolbar( {
//			pageSize : number,
//			store : store,
//			displayInfo : true,
//			displayMsg : '��ʾ{0}����{1}��,��{2}��',
//			emptyMsg : "û�з��������ļ�¼",
//			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
//		});

		var grid = new Ext.grid.GridPanel(
				{
					title : '<span style="font-weight:normal">��������ִ�����</span>',
					iconCls : 'runitIcon', // ͼ��
//					renderTo : 'menuGridDiv',
					height : 500,
					// width:600,
					autoScroll : true,
					region : 'center',
					store : store,
					loadMask : {
						msg : '���ڼ��ر������,���Ե�...'
					},
					stripeRows : true,
					frame : true,
//					autoExpandColumn : 'errmsg',
					cm : cm,
					tbar : [ 
//					new Ext.form.TextField( {
//						id : 'queryParam',
//						name : 'queryParam',
//						emptyText : '������˵�����',
//						enableKeyEvents : true,
//						listeners : {
//							specialkey : function(field, e) {
//								if (e.getKey() == Ext.EventObject.ENTER) {
//									queryMenuItem();
//								}
//							}
//						},
//						width : 130
//					}), {
//						text : '��ѯ',
//						iconCls : 'previewIcon',
//						handler : function() {
//							queryMenuItem();
//						}
//					}, '-', {
					{
						text : 'ˢ��',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							store.reload();
						}
					} ]
//					bbar : bbar
				});
		store.load();
//		store.load( {
//			params : {
//				start : 0,
//				limit : bbar.pageSize
//			}
//		});
		grid.on('rowdblclick', function(grid, rowIndex, event) {
//			editInit();
		});
		grid.on('sortchange', function() {
			// grid.getSelectionModel().selectFirstRow();
			});

//		bbar.on("change", function() {
//			// grid.getSelectionModel().selectFirstRow();
//			});

		var addMenuWindow, addMenuFormPanel;
		var comboxWithTree;
		var addRoot = new Ext.tree.AsyncTreeNode( {
			text : '���Խ��ż����뿪��ƽ̨',
			expanded : true,
			id : '00'
		});
		var addMenuTree = new Ext.tree.TreePanel( {
			loader : new Ext.tree.TreeLoader( {
				baseAttrs : {},
				dataUrl : 'jsp/opms/batch/BatchJobgrpExecution_listJobgrp.action'
			}),
			root : addRoot,
			autoScroll : true,
			animate : false,
			useArrows : false,
			border : false
		});
		// �����������Ľڵ㵥���¼�
		addMenuTree.on('click', function(node) {
			comboxWithTree.setValue(node.text);
			Ext.getCmp("addMenuFormPanel").findById('parentid').setValue(
					node.attributes.id);
			comboxWithTree.collapse();
		});

		comboxWithTree = new Ext.form.ComboBox(
				{
					id : 'parentmenuname',
					store : new Ext.data.SimpleStore( {
						fields : [],
						data : [ [] ]
					}),
					editable : false,
					value : ' ',
					emptyText : '��ѡ��...',
					fieldLabel : '�ϼ��˵�',
					anchor : '100%',
					mode : 'local',
					triggerAction : 'all',
					maxHeight : 390,
					// ���������ʾģ��,addMenuTreeDiv��Ϊ��ʾ������������
					tpl : "<tpl for='.'><div style='height:390px'><div id='addMenuTreeDiv'></div></div></tpl>",
					allowBlank : false,
					onSelect : Ext.emptyFn
				});
		// ���������������չ���¼�
		comboxWithTree.on('expand', function() {
			// ��UI���ҵ�treeDiv����
				addMenuTree.render('addMenuTreeDiv');
				// addMenuTree.root.expand(); //ֻ�ǵ�һ���������������
				addMenuTree.root.reload(); // ÿ�����������������

			});
		var expandedStore = new Ext.data.SimpleStore( {
			fields : [ 'value', 'text' ],
			data : [ [ '0', '0 ����' ], [ '1', '1 չ��' ] ]
		});
		var expandedCombo = new Ext.form.ComboBox( {
			name : 'expanded',
			hiddenName : 'expanded',
			store : expandedStore,
			mode : 'local',
			triggerAction : 'all',
			valueField : 'value',
			displayField : 'text',
			value : '0',
			fieldLabel : '�ڵ��ʼ',
			emptyText : '��ѡ��...',
			allowBlank : false,
			forceSelection : true,
			editable : false,
			typeAhead : true,
			anchor : "99%"
		});
		
		var form = new Ext.form.FormPanel({
	    	title:'<span style="font-weight:normal">����������־</span>',  
	    	labelWidth:0.1,  
	    	iconCls : 'runitIcon', // ͼ��
	    	bodyStyle:'padding:5 5 5 5',  
	    	frame:true,  
	    	height:200,  
	    	width:window.screen.width-550,
	    	region:'south',
	    	collapsible:true,
	    	items:[
	    	       new Ext.form.TextArea({
	    	    	   id:'log', 
//	    	    	   readOnly:true,
	    	    	   width:window.screen.width-240,
	    	    	   height: 150,
	    	    	   style : 'background:none repeat scroll 0 0 black;color:white;',
	    	    	   anchor : '100%'
	    	       })  
	    	      ]
//	    	buttons:[  
//	    	           {text:'ȷ��',handler:addLog}  
//	    	        ]  
	    });
		
		addLog = function(msg) {
	        var log = form.findById('log');       //ȡ�������ؼ�
	        log.setValue(log.getValue() + msg);
	        log.scrollTop = log.scrollHeight;
	    }
		
		reloadData = function() {
			store.load();
		}
		
		/**
		 * ����
		 */
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ {
				title : '<span style="font-weight:normal">���ܲ˵�</span>',
				iconCls : 'layout_contentIcon',
				tools : [ {
					id : 'refresh',
					handler : function() {
						menuTree.root.reload()
					}
				} ],
				collapsible : true,
				width : 210,
				minSize : 160,
				maxSize : 280,
				split : true,
				region : 'west',
				autoScroll : true,
				// collapseMode:'mini',
				items : [ menuTree ]
			}, {
				region : 'center',
				layout : 'border',
				items : [ grid, form ]
			}, {
				
			} ]
		});
//		/**
//		 * ����˵�����
//		 */
//		function saveMenuItem(operateParams) {
//			if (!addMenuFormPanel.form.isValid()) {
//				return;
//			}
//			addMenuFormPanel.form.submit( {
//				url : './resource.ered?reqCode=saveMenuItem',
//				waitTitle : '��ʾ',
//				method : 'POST',
//				waitMsg : '���ڴ�������,���Ժ�...',
//				success : function(form, action) {
//					addMenuWindow.hide();
//					store.reload();
//					refreshNode(Ext.getCmp('parentid').getValue());
//					form.reset();
//					Ext.MessageBox.alert('��ʾ', action.result.msg);
//					insertOperateRcd(operateParams);
//				},
//				failure : function(form, action) {
//					var msg = action.result.msg;
//					Ext.MessageBox.alert('��ʾ', '�˵����ݱ���ʧ��:<br>' + msg);
//				}
//			});
//		}
//
//		/**
//		 * ˢ��ָ���ڵ�
//		 */
//		function refreshNode(nodeid) {
//			var node = menuTree.getNodeById(nodeid);
//			/* �첽��������û��չ���ڵ�֮ǰ�ǻ�ȡ������Ӧ�ڵ����� */
//			if (Ext.isEmpty(node)) {
//				menuTree.root.reload();
//				return;
//			}
//			if (node.attributes.leaf) {
//				node.parentNode.reload();
//			} else {
//				node.reload();
//			}
//		}

		/**
		 * ����������ѯ�˵�
		 */
//		function queryMenuItem() {
//			store.load( {
//				params : {
//					start : 0,
//					limit : bbar.pageSize,
//					queryParam : Ext.getCmp('queryParam').getValue()
//				}
//			});
//		}

		
		/**
		 * ɾ���˵�
		 */
//		function deleteMenuItems(pType, pMenuid,operateParams) {
//			var rows = grid.getSelectionModel().getSelections();
//			var fields = '';
//			for ( var i = 0; i < rows.length; i++) {
//				if (rows[i].get('menutype') == '1') {
//					fields = fields + rows[i].get('menuname') + '<br>';
//				}
//			}
//			if (fields != '') {
//				Ext.Msg
//						.alert(
//								'��ʾ',
//								'<b>��ѡ�е���Ŀ�а�������ϵͳ���õ�ֻ����Ŀ</b><br>' + fields + '<font color=red>ֻ����Ŀ����ɾ��!</font>');
//				return;
//			}
//			if (Ext.isEmpty(rows)) {
//				if (pType == '1') {
//					Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
//					return;
//				}
//			}
//			var strChecked = jsArray2JsString(rows, 'menuid');
//			Ext.Msg
//					.confirm(
//							'��ȷ��',
//							'�����Ҫɾ��ѡ�в˵���������������Ӳ˵���?',
//							function(btn, text) {
//								if (btn == 'yes') {
//									if (runMode == '0') {
//										Ext.Msg
//												.alert('��ʾ',
//														'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
//										return;
//									}
//									showWaitMsg();
//									Ext.Ajax
//											.request( {
//												url : './resource.ered?reqCode=deleteMenuItems',
//												success : function(response) {
//													var resultArray = Ext.util.JSON
//															.decode(response.responseText);
//													store.reload();
//													if (pType == '1') {
//														menuTree.root.reload();
//													} else {
//														menuTree.root.reload();
//													}
//													Ext.Msg.alert('��ʾ',
//															resultArray.msg);
//													insertOperateRcd(operateParams);
//												},
//												failure : function(response) {
//													var resultArray = Ext.util.JSON
//															.decode(response.responseText);
//													Ext.Msg.alert('��ʾ',
//															resultArray.msg);
//												},
//												params : {
//													strChecked : strChecked,
//													type : pType,
//													menuid : pMenuid
//												}
//											});
//								}
//							});
//		}

		/**
		 * �޸Ĳ˵���ʼ��
		 */
//		function editInit() {
//			var record = grid.getSelectionModel().getSelected();
//			if (Ext.isEmpty(record)) {
//				Ext.Msg.alert('��ʾ', '����ѡ����Ҫ�޸ĵĲ˵���Ŀ');
//			}else{
//			record = grid.getSelectionModel().getSelected();
//			if (record.get('menutype') == '1') {
//				Ext.Msg.alert('��ʾ', '��ѡ�еļ�¼Ϊϵͳ���ò˵�,�������޸�');
//				return;
//			}
//			if (record.get('leaf') == '0') {
//				comboxWithTree.setDisabled(true);
//			} else {
//				comboxWithTree.setDisabled(false);
//			}
//			addMenuFormPanel.getForm().loadRecord(record);
//			addMenuWindow.show();
//			addMenuWindow
//					.setTitle('<span style="font-weight:normal">�޸Ĳ˵�</span>');
//			Ext.getCmp('windowmode').setValue('edit');
//			Ext.getCmp('parentid_old').setValue(record.get('parentid'));
//			Ext.getCmp('count').setValue(record.get('count'));
////			Ext.getCmp('btnReset').hide();
//           }
//		}

		/**
		 * �����˵���ʼ��
		 */
		
//		function addInit() {
//			// clearForm(addMenuFormPanel.getForm());
//			var flag = Ext.getCmp('windowmode').getValue();
//			if (typeof (flag) != 'undefined') {
//				addMenuFormPanel.form.getEl().dom.reset();
//			} else {
//				clearForm(addMenuFormPanel.getForm());
//			}
//			var selectModel = menuTree.getSelectionModel();
//			var selectNode = selectModel.getSelectedNode();
//			Ext.getCmp('parentmenuname').setValue(selectNode.attributes.text);
//			Ext.getCmp('parentid').setValue(selectNode.attributes.id);
//			expandedCombo.setValue('0');
//			addMenuWindow.show();
//			addMenuWindow
//					.setTitle('<span style="font-weight:normal">�����˵�</span>');
//			Ext.getCmp('windowmode').setValue('add');
////			Ext.getCmp('btnReset').show();
//		}

		/**
		 * �޸Ĳ˵�����
		 */
//		function updateMenuItem(operateParams) {
//			if (!addMenuFormPanel.form.isValid()) {
//				return;
//			}
//			var parentid = Ext.getCmp('parentid').getValue();
//			var parentid_old = Ext.getCmp('parentid_old').getValue();
//			var count = Ext.getCmp('count').getValue();
//			if (parentid != parentid_old) {
//				if (count != '0') {
//					Ext.Msg.confirm('��ȷ��',
//							'�˲˵��Ѿ�����Ȩ�޷���,�޸��ϼ��˵����Խ�������Ȩ����Ϣ��ʧ,����������?', function(
//									btn, text) {
//								if (btn == 'yes') {
//									update(operateParams);
//								} else {
//									return;
//								}
//							});
//				} else {
//					update(operateParams);
//				}
//			} else {
//				update(operateParams);
//			}
//
//		}

		/**
		 * ����
		 */
		function update(operateParams) {
			var parentid = Ext.getCmp('parentid').getValue();
			var parentid_old = Ext.getCmp('parentid_old').getValue();
			addMenuFormPanel.form.submit( {
				url : './resource.ered?reqCode=updateMenuItem',
				waitTitle : '��ʾ',
				method : 'POST',
				waitMsg : '���ڴ�������,���Ժ�...',
				success : function(form, action) {
					addMenuWindow.hide();
					store.reload();
					refreshNode(parentid);
					if (parentid != parentid_old) {
						refreshNode(parentid_old);
					}
					form.reset();
					Ext.MessageBox.alert('��ʾ', action.result.msg);
					insertOperateRcd(operateParams);
				},
				failure : function(form, action) {
					var msg = action.result.msg;
					Ext.MessageBox.alert('��ʾ', '�˵������޸�ʧ��:<br>' + msg);
				}
			});
		}
		
	});
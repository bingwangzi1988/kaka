/**
 * ��������
 *
 * @author XiongChun
 * @since 2010-02-13
 */
Ext.onReady(function() {
	var root = new Ext.tree.AsyncTreeNode( {
		text : root_menuname,
		expanded : true,
		id : '01'
	});
	var menuTree = new Ext.tree.TreePanel( {
		loader : new Ext.tree.TreeLoader( {
			baseAttrs : {},
			dataUrl : './resource.ered?reqCode=queryMenuItems'
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
		Ext.getCmp('parentmenuname').setValue(menuname);
		Ext.getCmp('parentid').setValue(menuid);
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				menuid : menuid
			}
		});
	});
	menuTree.root.select();
	var contextMenu = new Ext.menu.Menu( {
		id : 'menuTreeContextMenu',
		items : [ {
			text : '�����˵�',
			iconCls : 'page_addIcon',
			handler : function() {
				addInit();
			}
		}, {
			text : '�޸Ĳ˵�',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editInit();
			}
		}, {
			text : 'ɾ���˵�',
			iconCls : 'page_delIcon',
			handler : function() {
				var selectModel = menuTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
//				deleteMenuItems('2', selectNode.attributes.id);
				authoriseRole("delete","Eamenu",'2', selectNode.attributes.id);
			}
		}, {
			text : 'ˢ�½ڵ�',
			iconCls : 'page_refreshIcon',
			handler : function() {
				var selectModel = menuTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				if (selectNode.attributes.leaf) {
					selectNode.parentNode.reload();
				} else {
					selectNode.reload();
				}
			}
		} ]
	});
	menuTree.on('contextmenu', function(node, e) {
		e.preventDefault();
		menuid = node.attributes.id;
		menuname = node.attributes.text;
		Ext.getCmp('parentmenuname').setValue(menuname);
		Ext.getCmp('parentid').setValue(menuid);
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				menuid : menuid
			},
			callback : function(r, options, success) {
				for ( var i = 0; i < r.length; i++) {
					var record = r[i];
					var menuid_g = record.data.menuid;
					if (menuid_g == menuid) {
						grid.getSelectionModel().selectRow(i);
					}
				}
			}
		});
		node.select();
		contextMenu.showAt(e.getXY());
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(), sm, {
		header : '�˵�����',
		dataIndex : 'menuname',
		width : 130
	}, {
		header : '����·��',
		dataIndex : 'request',
		width : 200
	}, {
		header : '�����',
		dataIndex : 'sortno',
		sortable : true,
		width : 50
	}, {
		header : '�ϼ��˵�',
		dataIndex : 'parentmenuname',
		width : 130
	}, {
		header : '�˵�����',
		dataIndex : 'menutype',
		renderer : function(value) {
			if (value == '1')
				return 'ϵͳ�˵�';
			else if (value == '0')
				return 'ҵ��˵�';
			else
				return value;
		}
	}, {
		header : '�ڵ�����',
		dataIndex : 'leaf',
		hidden : true,
		renderer : function(value) {
			if (value == '1')
				return 'Ҷ�ӽڵ�';
			else if (value == '0')
				return '��֦�ڵ�';
			else
				return value;
		}
	}, {
		header : '�ڵ�ͼ��CSS����',
		dataIndex : 'iconcls',
		width : 120
	}, {
		header : '�ڵ�ͼ���ļ���',
		dataIndex : 'icon',
		width : 120
	}, {
		header : 'չ��״̬',
		dataIndex : 'expanded',
		renderer : function(value) {
			if (value == '1')
				return 'չ��';
			else if (value == '0')
				return '����';
			else
				return value;
		},
		width : 60
	}, {
		id : 'count',
		header : '��Ȩ״̬',
		dataIndex : 'count',
		hidden : true,
		renderer : function(value) {
			if (value == '0')
				return 'δ��Ȩ';
			else
				return '����Ȩ';
		}
	}, {
		header : '�˵����',
		dataIndex : 'menuid',
		hidden : false,
		width : 130,
		sortable : true
	}, {
		id : 'remark',
		header : '��ע',
		dataIndex : 'remark'
	}, {
		id : 'parentid',
		header : '���ڵ���',
		hidden : true,
		dataIndex : 'parentid'
	} ]);

	/**
	 * ���ݴ洢
	 */
	var store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : './resource.ered?reqCode=queryMenuItemsForManage'
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty : 'TOTALCOUNT',
			root : 'ROOT'
		}, [ {
			name : 'menuid'
		}, {
			name : 'menuname'
		}, {
			name : 'parentmenuname'
		}, {
			name : 'request'
		}, {
			name : 'iconcls'
		}, {
			name : 'icon'
		}, {
			name : 'expanded'
		}, {
			name : 'leaf'
		}, {
			name : 'sortno'
		}, {
			name : 'remark'
		}, {
			name : 'parentid'
		}, {
			name : 'count'
		}, {
			name : 'menutype'
		} ])
	});

	// ��ҳ����ʱ���ϲ�ѯ����
		store.on('beforeload', function() {
			this.baseParams = {
				queryParam : Ext.getCmp('queryParam').getValue()
			};
		});

		var pagesize_combo = new Ext.form.ComboBox( {
			name : 'pagesize',
			hiddenName : 'pagesize',
			typeAhead : true,
			triggerAction : 'all',
			lazyRender : true,
			mode : 'local',
			store : new Ext.data.ArrayStore(
					{
						fields : [ 'value', 'text' ],
						data : [ [ 10, '10��/ҳ' ], [ 20, '20��/ҳ' ],
								[ 50, '50��/ҳ' ], [ 100, '100��/ҳ' ],
								[ 250, '250��/ҳ' ], [ 500, '500��/ҳ' ] ]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '50',
			editable : false,
			width : 85
		});
		var number = parseInt(pagesize_combo.getValue());
		pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(comboBox.getValue());
			number = parseInt(comboBox.getValue());
			store.reload( {
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		});

		var bbar = new Ext.PagingToolbar( {
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '��ʾ{0}����{1}��,��{2}��',
			emptyMsg : "û�з��������ļ�¼",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
		});

		var grid = new Ext.grid.GridPanel(
				{
					title : '<span style="font-weight:normal">�˵���Ϣ��</span>',
					iconCls:'application_view_listIcon',
					renderTo : 'menuGridDiv',
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
					autoExpandColumn : 'remark',
					cm : cm,
					sm : sm,
					tbar : [ {
						text : '����',
						iconCls : 'page_addIcon',
						handler : function() {
							addInit();
						}
					}, '-', {
						text : '�޸�',
						iconCls : 'page_edit_1Icon',
						handler : function() {
							editInit();
						}
					}, '-', {
						text : 'ɾ��',
						iconCls : 'page_delIcon',
						handler : function() {
//							deleteMenuItems('1', '');
							authoriseRole("delete","Eamenu",'1','');
						}
					}, '->', new Ext.form.TextField( {
						id : 'queryParam',
						name : 'queryParam',
						emptyText : '������˵�����',
						enableKeyEvents : true,
						listeners : {
							specialkey : function(field, e) {
								if (e.getKey() == Ext.EventObject.ENTER) {
									queryMenuItem();
								}
							}
						},
						width : 130
					}), {
						text : '��ѯ',
						iconCls : 'previewIcon',
						handler : function() {
							queryMenuItem();
						}
					}, '-', {
						text : 'ˢ��',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							store.reload();
						}
					} ],
					bbar : bbar
				});
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
		grid.on('rowdblclick', function(grid, rowIndex, event) {
			editInit();
		});
		grid.on('sortchange', function() {
			// grid.getSelectionModel().selectFirstRow();
			});

		bbar.on("change", function() {
			// grid.getSelectionModel().selectFirstRow();
			});

		var addMenuWindow, addMenuFormPanel;
		var comboxWithTree;
		var addRoot = new Ext.tree.AsyncTreeNode( {
			text : '���Խ��ż����뿪��ƽ̨',
			expanded : true,
			id : '01'
		});
		var addMenuTree = new Ext.tree.TreePanel( {
			loader : new Ext.tree.TreeLoader( {
				baseAttrs : {},
				dataUrl : './resource.ered?reqCode=queryMenuItems'
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

		addMenuFormPanel = new Ext.form.FormPanel( {
			id : 'addMenuFormPanel',
			name : 'addMenuFormPanel',
			defaultType : 'textfield',
			labelAlign : 'right',
			labelWidth : 65,
			frame : false,
			bodyStyle : 'padding:5 5 0',
			items : [ {
				fieldLabel : '�˵�����',
				name : 'menuname',
				id : 'menuname',
                maxLength:50,
				allowBlank : false,
				anchor : '99%'
			}, comboxWithTree, {
				fieldLabel : '�����ַ',
				name : 'request',
				allowBlank : true,
                maxLength:100,
				anchor : '99%'
			}, expandedCombo, {
				fieldLabel : 'ͼ��CSS��',
				name : 'iconcls',
				allowBlank : true,
                maxLength:50,
				anchor : '99%'
			}, {
				fieldLabel : 'ͼ���ļ�',
				name : 'icon',
				allowBlank : true,
                maxLength:50,
				anchor : '99%'
			}, {
				fieldLabel : '�����',
				name : 'sortno',
                maxLength:4,
				allowBlank : true,
				anchor : '99%'
			}, {
				fieldLabel : '��ע',
				name : 'remark',
				allowBlank : true,
                maxLength:200,
				anchor : '99%'
			}, {
				id : 'parentid',
				name : 'parentid',
				hidden : true
			}, {
				id : 'windowmode',
				name : 'windowmode',
				hidden : true
			}, {
				id : 'menuid',
				name : 'menuid',
				hidden : true
			}, {
				id : 'parentid_old',
				name : 'parentid_old',
				hidden : true
			}, {
				id : 'count',
				name : 'count',
				hidden : true
			} ]
		});
		addMenuWindow = new Ext.Window( {
			layout : 'fit',
			width : 420,
			height : 290,
			resizable : false,
			draggable : true,
			closeAction : 'hide',
			title : '<span style="font-weight:normal">�����˵�</span>',
			// iconCls : 'page_addIcon',
			modal : true,
			collapsible : true,
			titleCollapse : true,
			maximizable : false,
			buttonAlign : 'right',
			border : false,
			animCollapse : true,
			pageY : 20,
			pageX : document.body.clientWidth / 2 - 420 / 2,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [ addMenuFormPanel ],
			buttons : [
					{
						text : '����',
						iconCls : 'acceptIcon',
						handler : function() {
							if (runMode == '0') {
								Ext.Msg.alert('��ʾ',
										'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
								return;
							}
							var mode = Ext.getCmp('windowmode').getValue();
							if (mode == 'add'){
//								saveMenuItem();
								authoriseRole("save","Eamenu",'','');
							}
							else{
//								updateMenuItem();
								authoriseRole("update","Eamenu",'','');
							}
						}
//					}, {
//						text : '����',
//						id : 'btnReset',
//						iconCls : 'tbar_synchronizeIcon',
//						handler : function() {
//							clearForm(addMenuFormPanel.getForm());
//						}
					}, {
						text : '�ر�',
						iconCls : 'deleteIcon',
						handler : function() {
							addMenuWindow.hide();
						}
					} ]
		});
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
				layout : 'fit',
				items : [ grid ]
			} ]
		});
		/**
		 * ����˵�����
		 */
		function saveMenuItem(operateParams) {
			if (!addMenuFormPanel.form.isValid()) {
				return;
			}
			addMenuFormPanel.form.submit( {
				url : './resource.ered?reqCode=saveMenuItem',
				waitTitle : '��ʾ',
				method : 'POST',
				waitMsg : '���ڴ�������,���Ժ�...',
				success : function(form, action) {
					addMenuWindow.hide();
					store.reload();
					refreshNode(Ext.getCmp('parentid').getValue());
					form.reset();
					Ext.MessageBox.alert('��ʾ', action.result.msg);
					insertOperateRcd(operateParams);
				},
				failure : function(form, action) {
					var msg = action.result.msg;
					Ext.MessageBox.alert('��ʾ', '�˵����ݱ���ʧ��:<br>' + msg);
				}
			});
		}

		/**
		 * ˢ��ָ���ڵ�
		 */
		function refreshNode(nodeid) {
			var node = menuTree.getNodeById(nodeid);
			/* �첽��������û��չ���ڵ�֮ǰ�ǻ�ȡ������Ӧ�ڵ����� */
			if (Ext.isEmpty(node)) {
				menuTree.root.reload();
				return;
			}
			if (node.attributes.leaf) {
				node.parentNode.reload();
			} else {
				node.reload();
			}
		}

		/**
		 * ����������ѯ�˵�
		 */
		function queryMenuItem() {
			store.load( {
				params : {
					start : 0,
					limit : bbar.pageSize,
					queryParam : Ext.getCmp('queryParam').getValue()
				}
			});
		}

		
		/**
		 * ɾ���˵�
		 */
		function deleteMenuItems(pType, pMenuid,operateParams) {
			var rows = grid.getSelectionModel().getSelections();
			var fields = '';
			for ( var i = 0; i < rows.length; i++) {
				if (rows[i].get('menutype') == '1') {
					fields = fields + rows[i].get('menuname') + '<br>';
				}
			}
			if (fields != '') {
				Ext.Msg
						.alert(
								'��ʾ',
								'<b>��ѡ�е���Ŀ�а�������ϵͳ���õ�ֻ����Ŀ</b><br>' + fields + '<font color=red>ֻ����Ŀ����ɾ��!</font>');
				return;
			}
			if (Ext.isEmpty(rows)) {
				if (pType == '1') {
					Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
					return;
				}
			}
			var strChecked = jsArray2JsString(rows, 'menuid');
			Ext.Msg
					.confirm(
							'��ȷ��',
							'�����Ҫɾ��ѡ�в˵���������������Ӳ˵���?',
							function(btn, text) {
								if (btn == 'yes') {
									if (runMode == '0') {
										Ext.Msg
												.alert('��ʾ',
														'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
										return;
									}
									showWaitMsg();
									Ext.Ajax
											.request( {
												url : './resource.ered?reqCode=deleteMenuItems',
												success : function(response) {
													var resultArray = Ext.util.JSON
															.decode(response.responseText);
													store.reload();
													if (pType == '1') {
														menuTree.root.reload();
													} else {
														menuTree.root.reload();
													}
													Ext.Msg.alert('��ʾ',
															resultArray.msg);
													insertOperateRcd(operateParams);
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON
															.decode(response.responseText);
													Ext.Msg.alert('��ʾ',
															resultArray.msg);
												},
												params : {
													strChecked : strChecked,
													type : pType,
													menuid : pMenuid
												}
											});
								}
							});
		}

		/**
		 * �޸Ĳ˵���ʼ��
		 */
		function editInit() {
			var record = grid.getSelectionModel().getSelected();
			if (Ext.isEmpty(record)) {
				Ext.Msg.alert('��ʾ', '����ѡ����Ҫ�޸ĵĲ˵���Ŀ');
			}else{
			record = grid.getSelectionModel().getSelected();
			if (record.get('menutype') == '1') {
				Ext.Msg.alert('��ʾ', '��ѡ�еļ�¼Ϊϵͳ���ò˵�,�������޸�');
				return;
			}
			if (record.get('leaf') == '0') {
				comboxWithTree.setDisabled(true);
			} else {
				comboxWithTree.setDisabled(false);
			}
			addMenuFormPanel.getForm().loadRecord(record);
			addMenuWindow.show();
			addMenuWindow
					.setTitle('<span style="font-weight:normal">�޸Ĳ˵�</span>');
			Ext.getCmp('windowmode').setValue('edit');
			Ext.getCmp('parentid_old').setValue(record.get('parentid'));
			Ext.getCmp('count').setValue(record.get('count'));
//			Ext.getCmp('btnReset').hide();
           }
		}

		/**
		 * �����˵���ʼ��
		 */
		
		function addInit() {
			// clearForm(addMenuFormPanel.getForm());
			var flag = Ext.getCmp('windowmode').getValue();
			if (typeof (flag) != 'undefined') {
				addMenuFormPanel.form.getEl().dom.reset();
			} else {
				clearForm(addMenuFormPanel.getForm());
			}
			var selectModel = menuTree.getSelectionModel();
			var selectNode = selectModel.getSelectedNode();
			Ext.getCmp('parentmenuname').setValue(selectNode.attributes.text);
			Ext.getCmp('parentid').setValue(selectNode.attributes.id);
			expandedCombo.setValue('0');
			addMenuWindow.show();
			addMenuWindow
					.setTitle('<span style="font-weight:normal">�����˵�</span>');
			Ext.getCmp('windowmode').setValue('add');
//			Ext.getCmp('btnReset').show();
		}

		/**
		 * �޸Ĳ˵�����
		 */
		function updateMenuItem(operateParams) {
			if (!addMenuFormPanel.form.isValid()) {
				return;
			}
			var parentid = Ext.getCmp('parentid').getValue();
			var parentid_old = Ext.getCmp('parentid_old').getValue();
			var count = Ext.getCmp('count').getValue();
			if (parentid != parentid_old) {
				if (count != '0') {
					Ext.Msg.confirm('��ȷ��',
							'�˲˵��Ѿ�����Ȩ�޷���,�޸��ϼ��˵����Խ�������Ȩ����Ϣ��ʧ,����������?', function(
									btn, text) {
								if (btn == 'yes') {
									update(operateParams);
								} else {
									return;
								}
							});
				} else {
					update(operateParams);
				}
			} else {
				update(operateParams);
			}

		}

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
		
		/*
	     * ��Ȩģ��
	     * */
	    
	    var userRoleStore = new Ext.data.Store({
			 baseParams:{valueflag:'1',valueflagvalue:':'},
	      proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/common/codeSelect_getUserRole.action'}),
	      reader: new Ext.data.JsonReader({}, [
	        {name: "code"},
	        {name: "codedesc"}
	      ]),
	      remoteSort:false
	      });
//		userRoleStore.load();
		
		var authoriseId=new Ext.form.ComboBox({
//			id:'authoriseId',
			name : 'authoriseId',
			hiddenName : 'authoriseId',
			fieldLabel : '��ȨԱ��',
			emptyText : '��ѡ��',
			triggerAction : 'all',
			store:userRoleStore,
			displayField : 'codedesc',
			valueField : 'code',
			mode : 'local',
			forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
			editable : false,
			typeAhead : true,
			resizable : true,
			allowBlank : false,
			anchor : '100%'
		});

	    function authoriseRole(operation,operateObject,pType, pMenuid){
	    	var authoriseRoleForm = new Ext.form.FormPanel({	
	    		height:200,
	    		labelWidth : 90,
	    		frame : true,
	    		defaultType : 'textfield',
	    		labelAlign : 'right',
	    		bodyStyle : 'padding:5 5 5 5',
	    		items : [
//	    		         {
//	    			fieldLabel : '��ȨԱ��',
//	    			name : 'authoriseId',
////	    			minLength:7,
////	    			store:userRoleStore,
////	    			"xtype":"dcombo",
//	    			allowBlank : false,
//	    			anchor : '100%'
//	    		}, 
	    		authoriseId
	    		,
	    		{
	    			fieldLabel : '��Ȩ����',
	    			name : 'authorisePwd',
	    			inputType : 'password',
	    			allowBlank : false,
	    			anchor : '100%'
	    		}, {
	    			fieldLabel : '��������',
	    			name : 'operateType',
	    			value:operation,
	    			hidden:true,
	    			anchor : '100%'
	    		}, {
	    			fieldLabel : '��������',
	    			name : 'operateObject',
	    			value:operateObject,
	    			hidden:true,
	    			anchor : '100%'
	    		}/*, {
	    			fieldLabel : '������Ϣ',
	    			name : 'operateInfo',
//	    			hidden:true,
	    			anchor : '100%'
	    		}*/]	
	    	});
	    	
	    	var authoriseRoleWin = new Ext.Window({
	    		title : '������Ȩ',
	    		layout : 'fit',
	    		width : 300,
	    		height : 220,
	    		collapsible : true,
	    		maximizable : true,
	    		modal : true, 
	    		closable : true,
	    		animCollapse : true,
	    		closeAction : 'hide',
	    		border : false,
	    		constrain : true,
	    		pageY : 40,
	    		pageX : document.body.clientWidth / 2 - 380 / 2,
	    		items : [authoriseRoleForm],
	    		buttons : [{
	    			text : '��Ȩ',
	    			iconCls : 'keyIcon',
	    			handler : function execute(){
	    				if(authoriseRoleForm.getForm().isValid()){
	    					authoriseRoleForm.form.submit({
	    						url : _contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
	    						success : function(form, action) {
	    							authoriseRoleWin.hide();
	    							var result = Ext.util.JSON.decode(action.response.responseText);
	    							var operateParams = Ext.util.JSON.encode(result.msg);
	    							execObject(operation,operateParams,pType, pMenuid);
	    						},
	    						failure : function(form, action) {
	    							var result = Ext.util.JSON.decode(action.response.responseText);
	    							authoriseRoleWin.hide();
	    							Ext.MessageBox.alert('��ʾ', result.msg);
	    						},
	    						waitTitle:"���Ժ�",
	            				waitMsg:"��Ȩ��֤�С���"
	    					});
	    				}
	    			}
	    		},{
	    			text : 'ȡ��',
	    			iconCls : 'lockIcon',
	    			handler : function cancel(){
	    				authoriseRoleWin.hide();
	    			}
	    		}]
	    	});
	    	
	    	
//	    	authoriseWin.show();
//	    	alert("operation=="+operation+"    operateObject=="+operateObject);
	    	
	    	//��ѯ�˵�Ȩ�ޱ�
	    	 Ext.Ajax.request({
	    		   url:_contextPath_ + '/jsp/conmg/Eaoperaterole_getRole.action',
	    		   params:{operateObject:operateObject},
	    		   callback:function(opts,success,response){
	    			   var jsonobjs=Ext.decode(response.responseText);
	    			   if(jsonobjs.success==true)
	    			   {
	    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
	    				   var params = Ext.util.JSON.decode(operateParams);
//	    				   alert("tableName=="+params.tableName);
	    				   if(params.tableName==operateObject)
	    				   {
	    					   var insertFlag=params.insertFlag;
	    					   var updateFlag=params.updateFlag;
	    					   var deleteFlag=params.deleteFlag;
	    					    if(operation == 'save'){
	    							if(insertFlag==1){
	    								userRoleStore.load({
	    									params : {
	    										tableName : params.tableName,
	    										valueflag:'1',
	    										valueflagvalue:':'
	    									}
	    								});
	    								authoriseRoleWin.show();//��Ҫ��Ȩ
	    							}
	    							else{
	    								execNoRole(operation,operateObject,pType, pMenuid);//����Ҫ��Ȩ
	    							}
	    						}else if(operation == 'update'){
	    							if(updateFlag==1){
	    								userRoleStore.load({
	    									params : {
	    										tableName : params.tableName,
	    										valueflag:'1',
	    										valueflagvalue:':'
	    									}
	    								});
	    								authoriseRoleWin.show();//��Ҫ��Ȩ
	    							}
	    							else{
	    								execNoRole(operation,operateObject,pType, pMenuid);//����Ҫ��Ȩ
	    							}
	    						}
	    						else if(operation == 'delete'){
	    							if(deleteFlag==1){
	    								userRoleStore.load({
	    									params : {
	    										tableName : params.tableName,
	    										valueflag:'1',
	    										valueflagvalue:':'
	    									}
	    								});
	    								authoriseRoleWin.show();//��Ҫ��Ȩ
	    							}
	    							else{
	    								execNoRole(operation,operateObject,pType, pMenuid);//����Ҫ��Ȩ
	    							}
	    						}else
	    						{
	    							execNoRole(operation,operateObject,pType, pMenuid);//����Ҫ��Ȩ
	    						}
	    				   }
	    			   }else{
	    				   execNoRole(operation,operateObject,pType, pMenuid);//Ĭ��Ϊ����Ҫ��Ȩ
	    			   }
	    		   }
	    	   });

	    	
	    }


	    //����Ȩ
	    execNoRole=function(operation,operateObject,pType, pMenuid)
	    {
	    	 Ext.Ajax.request({
	    		   url:_contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
	    		   params:{operateType:operation,operateObject:operateObject},
	    		   callback:function(opts,success,response){
	    			   var jsonobjs=Ext.decode(response.responseText);
	    			   if(jsonobjs.success==true)
	    			   {
	    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
	    				   execObject(operation,operateParams,pType, pMenuid);
	    			   }else
	    			   {
	    				   Ext.Msg.alert('��ʾ', jsonobjs.msg);
	    			   }
	    		   }
	    	   });
	    }

	    execObject = function(operation,operateParams,pType, pMenuid){
	    	if(operation == 'save'){
	    		saveMenuItem(operateParams);
	    	}else if(operation == 'update'){
	    		updateMenuItem(operateParams);
	    	}
	    	else if(operation == 'delete'){
	    		deleteMenuItems(pType, pMenuid,operateParams);
	    	}
	    }
	    
	  //��¼����
	    insertOperateRcd = function(operateParams){
	    	Ext.Ajax.request({
	        	url: _contextPath_ + '/jsp/conmg/Eaoperatercd_save.action',
	    		params:{params:operateParams},
	    		success : function(response) {
	    		},
	    		failure : function(form, action) {
	    		}
	        });
	    }
	    /*
	     * ��Ȩģ��
	     * */
	 
	});
/**
 * ���Ź���
 * 
 * @author XiongChun
 * @since 2010-04-11
 */
Ext.onReady(function() {
	var root = new Ext.tree.AsyncTreeNode( {
		text : root_deptname,
		expanded : true,
		id : root_deptid
	});
	var deptTree = new Ext.tree.TreePanel( {
		loader : new Ext.tree.TreeLoader( {
			baseAttrs : {},
			dataUrl : './organization.ered?reqCode=departmentTreeInit'
		}),
		root : root,
		title : '',
		applyTo : 'deptTreeDiv',
		autoScroll : false,
		animate : false,
		useArrows : false,
		border : false
	});
	deptTree.root.select();
	deptTree.on('click', function(node) {
		deptid = node.attributes.id;
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				deptid : deptid
			}
		});
	});

	var contextMenu = new Ext.menu.Menu( {
		id : 'deptTreeContextMenu',
		items : [ {
			text : '��������',
			iconCls : 'page_addIcon',
			handler : function() {
				addInit();
			}
		}, {
			text : '�޸Ĳ���',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editInit();
			}
		}, {
			text : 'ɾ������',
			iconCls : 'page_delIcon',
			handler : function() {
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
//				deleteDeptItems('2', selectNode.attributes.id);
				authoriseRole('delete','Eadept','2', selectNode.attributes.id);
			}
		}, {
			text : 'ˢ�½ڵ�',
			iconCls : 'page_refreshIcon',
			handler : function() {
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				if (selectNode.attributes.leaf) {
					selectNode.parentNode.reload();
				} else {
					selectNode.reload();
				}
			}
		} ]
	});
	deptTree.on('contextmenu', function(node, e) {
		e.preventDefault();
		deptid = node.attributes.id;
		deptname = node.attributes.text;
		Ext.getCmp('parentdeptname').setValue(deptname);
		Ext.getCmp('parentid').setValue(deptid);
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				deptid : deptid
			},
			callback : function(r, options, success) {
				for ( var i = 0; i < r.length; i++) {
					var record = r[i];
					var deptid_g = record.data.deptid;
					if (deptid_g == deptid) {
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
		header : '��������',
		dataIndex : 'deptname',
		width : 130
	}, {
		header : 'ҵ�������',
		dataIndex : 'customid',
		hidden:true,
		width : 100
	}, {
		header : '�ϼ�����',
		dataIndex : 'parentdeptname',
		width : 130
	}, {
		header : '�����',
		dataIndex : 'sortno',
		sortable : true,
		width : 50
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
		id : 'parentid',
		header : '���ڵ���',
		hidden : true,
		dataIndex : 'parentid'
	}, {
		id : 'usercount',
		header : '�����û���Ŀ',
		hidden : true,
		dataIndex : 'usercount'
	}, {
		id : 'rolecount',
		header : '������ɫ��Ŀ',
		hidden : true,
		dataIndex : 'rolecount'
	}, {
		header : '���ű��',
		dataIndex : 'deptid',
		hidden : false,
		hidden : false,
		width : 130,
		sortable : true
	}, {
		id : 'remark',
		header : '��ע',
		dataIndex : 'remark'
	} ]);

	/**
	 * ���ݴ洢
	 */
	var store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : './organization.ered?reqCode=queryDeptsForManage'
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty : 'TOTALCOUNT',
			root : 'ROOT'
		}, [ {
			name : 'deptid'
		}, {
			name : 'deptname'
		}, {
			name : 'sortno'
		}, {
			name : 'customid'
		}, {
			name : 'parentdeptname'
		}, {
			name : 'leaf'
		}, {
			name : 'remark'
		}, {
			name : 'parentid'
		}, {
			name : 'usercount'
		}, {
			name : 'rolecount'
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
		var grid = new Ext.grid.GridPanel( {
			title : '<span style="font-weight:normal">������Ϣ��</span>',
			iconCls : 'buildingIcon',
			renderTo : 'deptGridDiv',
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
//					deleteDeptItems('1', '');
					authoriseRole('delete','Eadept','1', '');
				}
			}, '->', new Ext.form.TextField( {
				id : 'queryParam',
				name : 'queryParam',
				emptyText : '�����벿������',
				enableKeyEvents : true,
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							queryDeptItem();
						}
					}
				},
				width : 130
			}), {
				text : '��ѯ',
				iconCls : 'previewIcon',
				handler : function() {
					queryDeptItem();
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
				limit : bbar.pageSize,
				firstload : 'true'
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

		var addRoot = new Ext.tree.AsyncTreeNode( {
			text : root_deptname,
			expanded : true,
			id : root_deptid
		});
		var addDeptTree = new Ext.tree.TreePanel( {
			loader : new Ext.tree.TreeLoader( {
				baseAttrs : {},
				dataUrl : './organization.ered?reqCode=departmentTreeInit'
			}),
			root : addRoot,
			autoScroll : true,
			animate : false,
			useArrows : false,
			border : false
		});
		// �����������Ľڵ㵥���¼�
		addDeptTree.on('click', function(node) {
			comboxWithTree.setValue(node.text);
			Ext.getCmp("addDeptFormPanel").findById('parentid').setValue(
					node.attributes.id);
			comboxWithTree.collapse();
		});
		var comboxWithTree = new Ext.form.ComboBox(
				{
					id : 'parentdeptname',
					store : new Ext.data.SimpleStore( {
						fields : [],
						data : [ [] ]
					}),
					editable : false,
					value : ' ',
					emptyText : '��ѡ��...',
					fieldLabel : '�ϼ�����',
					anchor : '100%',
					mode : 'local',
					triggerAction : 'all',
					maxHeight : 390,
					// ���������ʾģ��,addDeptTreeDiv��Ϊ��ʾ������������
					tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv'></div></div></tpl>",
					allowBlank : false,
					onSelect : Ext.emptyFn
				});
		// ���������������չ���¼�
		comboxWithTree.on('expand', function() {
			// ��UI���ҵ�treeDiv����
				addDeptTree.render('addDeptTreeDiv');
				// addDeptTree.root.expand(); //ֻ�ǵ�һ���������������
				addDeptTree.root.reload(); // ÿ�����������������

			});
		var addDeptFormPanel = new Ext.form.FormPanel( {
			id : 'addDeptFormPanel',
			name : 'addDeptFormPanel',
			defaultType : 'textfield',
			labelAlign : 'right',
			labelWidth : 65,
			frame : false,
			bodyStyle : 'padding:5 5 0',
			items : [ {
				fieldLabel : '��������',
				name : 'deptname',
				id : 'deptname',
				allowBlank : false,
                maxLength:50,
				anchor : '99%'
			}, comboxWithTree, {
				fieldLabel : 'ҵ�������',
				name : 'customid',
                maxLength:20,
				allowBlank : true,
				hidden:true,
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
                maxLength:100,
				allowBlank : true,
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
				id : 'deptid',
				name : 'deptid',
				hidden : true
			}, {
				id : 'parentid_old',
				name : 'parentid_old',
				hidden : true
			} ]
		});
		var addDeptWindow = new Ext.Window( {
			layout : 'fit',
			width : 400,
			height : 220,
			resizable : false,
			draggable : true,
			closable : true,
			modal : true,
			closeAction : 'hide',
			title : '<span style="font-weight:normal">��������</span>',
			// iconCls : 'page_addIcon',
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
			items : [ addDeptFormPanel ],
			buttons : [
					{
						text : '����',
						iconCls : 'acceptIcon',
						id : 'btn_id_save_update',
						handler : function() {
							if (runMode == '0') {
								Ext.Msg.alert('��ʾ',
										'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
								return;
							}
							var mode = Ext.getCmp('windowmode').getValue();
							if (mode == 'add'){
//								saveDeptItem();
								authoriseRole('save','Eadept','', '');
							}
							else{
//								updateDeptItem();
								authoriseRole('update','Eadept','', '');
							}
						}
//					}, {
//						text : '����',
//						id : 'btnReset',
//						iconCls : 'tbar_synchronizeIcon',
//						handler : function() {
//							clearForm(addDeptFormPanel.getForm());
//						}
					}, {
						text : '�ر�',
						iconCls : 'deleteIcon',
						handler : function() {
							addDeptWindow.hide();
						}
					} ]
		});
		/**
		 * ����
		 */
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ {
				title : '<span style="font-weight:normal">��֯����</span>',
				iconCls : 'chart_organisationIcon',
				tools : [ {
					id : 'refresh',
					handler : function() {
						deptTree.root.reload()
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
				items : [ deptTree ]
			}, {
				region : 'center',
				layout : 'fit',
				items : [ grid ]
			} ]
		});

		/**
		 * ����������ѯ����
		 */
		function queryDeptItem() {
			store.load( {
				params : {
					start : 0,
					limit : bbar.pageSize,
					queryParam : Ext.getCmp('queryParam').getValue()
				}
			});
		}

		/**
		 * �������ų�ʼ��
		 */
		function addInit() {
			// clearForm(addDeptFormPanel.getForm());
			var flag = Ext.getCmp('windowmode').getValue();
			if (typeof (flag) != 'undefined') {
				addDeptFormPanel.form.getEl().dom.reset();
			} else {
				clearForm(addDeptFormPanel.getForm());
			}
			var selectModel = deptTree.getSelectionModel();
			var selectNode = selectModel.getSelectedNode();
			Ext.getCmp('parentdeptname').setValue(selectNode.attributes.text);
			Ext.getCmp('parentid').setValue(selectNode.attributes.id);
			addDeptWindow.show();
			addDeptWindow
					.setTitle('<span style="font-weight:normal">��������</span>');
			Ext.getCmp('windowmode').setValue('add');
			comboxWithTree.setDisabled(false);
//			Ext.getCmp('btnReset').show();
		}

		/**
		 * ���沿������
		 */
		function saveDeptItem(operateParams) {
			if (!addDeptFormPanel.form.isValid()) {
				return;
			}
			addDeptFormPanel.form.submit( {
				url : './organization.ered?reqCode=saveDeptItem',
				waitTitle : '��ʾ',
				method : 'POST',
				waitMsg : '���ڴ�������,���Ժ�...',
				success : function(form, action) {
					addDeptWindow.hide();
					store.reload();
					refreshNode(Ext.getCmp('parentid').getValue());
					form.reset();
					Ext.MessageBox.alert('��ʾ', action.result.msg);
					insertOperateRcd(operateParams);
				},
				failure : function(form, action) {
					var msg = action.result.msg;
					Ext.MessageBox.alert('��ʾ', '�������ݱ���ʧ��:<br>' + msg);
				}
			});
		}

		/**
		 * ˢ��ָ���ڵ�
		 */
		function refreshNode(nodeid) {
			var node = deptTree.getNodeById(nodeid);
			/* �첽��������û��չ���ڵ�֮ǰ�ǻ�ȡ������Ӧ�ڵ����� */
			if (Ext.isEmpty(node)) {
				deptTree.root.reload();
				return;
			}
			if (node.attributes.leaf) {
				node.parentNode.reload();
			} else {
				node.reload();
			}
		}

		/**
		 * �޸Ĳ��ų�ʼ��
		 */
		function editInit() {
			var record = grid.getSelectionModel().getSelected();
			if (Ext.isEmpty(record)) {
				Ext.MessageBox.alert('��ʾ', '����ѡ��Ҫ�޸ĵĲ���!');
			}
			record = grid.getSelectionModel().getSelected();
			if (record.get('leaf') == '0' || record.get('usercount') != '0'
					|| record.get('rolecount') != '0') {
				comboxWithTree.setDisabled(true);
			} else {
				comboxWithTree.setDisabled(false);
			}
			if (record.get('deptid') == '001') {
				var a = Ext.getCmp('parentdeptname');
				a.emptyText = '�Ѿ��Ƕ�������';
			} else {
			}
			addDeptFormPanel.getForm().loadRecord(record);
			addDeptWindow.show();
			addDeptWindow
					.setTitle('<span style="font-weight:normal">�޸Ĳ���</span>');
			Ext.getCmp('windowmode').setValue('edit');
			Ext.getCmp('parentid_old').setValue(record.get('parentid'));
//			Ext.getCmp('btnReset').hide();
		}

		/**
		 * �޸Ĳ�������
		 */
		function updateDeptItem(operateParams) {
			if (!addDeptFormPanel.form.isValid()) {
				return;
			}
			update(operateParams);
		}

		/**
		 * ����
		 */
		function update(operateParams) {
			var parentid = Ext.getCmp('parentid').getValue();
			var parentid_old = Ext.getCmp('parentid_old').getValue();
			addDeptFormPanel.form.submit( {
				url : './organization.ered?reqCode=updateDeptItem',
				waitTitle : '��ʾ',
				method : 'POST',
				waitMsg : '���ڴ�������,���Ժ�...',
				success : function(form, action) {
					addDeptWindow.hide();
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
					Ext.MessageBox.alert('��ʾ', '���������޸�ʧ��:<br>' + msg);
				}
			});
		}

		/**
		 * ɾ������
		 */
		function deleteDeptItems(pType, pDeptid,operateParams) {
			var rows = grid.getSelectionModel().getSelections();
			var fields = '';
			for ( var i = 0; i < rows.length; i++) {
				if (rows[i].get('deptid') == '001') {
					fields = fields + rows[i].get('deptname') + '<br>';
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
			var strChecked = jsArray2JsString(rows, 'deptid');
			Ext.Msg
					.confirm(
							'��ȷ��',
							'<span style="color:red"><b>��ʾ:</b>ɾ�����Ž�ͬʱɾ������������Ա�ͽ�ɫ�Լ����ǵ�Ȩ����Ϣ,������.</span><br>����ɾ����?',
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
												url : './organization.ered?reqCode=deleteDeptItems',
												success : function(response) {
													var resultArray = Ext.util.JSON
															.decode(response.responseText);
													store.reload();
													if (pType == '1') {
														deptTree.root.reload();
													} else {
														deptTree.root.reload();
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
													deptid : pDeptid
												}
											});
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

	    function authoriseRole(operation,operateObject,pType, pDeptid){
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
	    							execObject(operation,operateParams,pType, pDeptid);
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
	    								execNoRole(operation,operateObject,pType, pDeptid);//����Ҫ��Ȩ
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
	    								execNoRole(operation,operateObject,pType, pDeptid);//����Ҫ��Ȩ
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
	    								execNoRole(operation,operateObject,pType, pDeptid);//����Ҫ��Ȩ
	    							}
	    						}else
	    						{
	    							execNoRole(operation,operateObject,pType, pDeptid);//����Ҫ��Ȩ
	    						}
	    				   }
	    			   }else{
	    				   execNoRole(operation,operateObject,pType, pDeptid);//Ĭ��Ϊ����Ҫ��Ȩ
	    			   }
	    		   }
	    	   });

	    	
	    }


	    //����Ȩ
	    execNoRole=function(operation,operateObject,pType, pDeptid)
	    {
	    	 Ext.Ajax.request({
	    		   url:_contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
	    		   params:{operateType:operation,operateObject:operateObject},
	    		   callback:function(opts,success,response){
	    			   var jsonobjs=Ext.decode(response.responseText);
	    			   if(jsonobjs.success==true)
	    			   {
	    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
	    				   execObject(operation,operateParams,pType, pDeptid);
	    			   }else
	    			   {
	    				   Ext.Msg.alert('��ʾ', jsonobjs.msg);
	    			   }
	    		   }
	    	   });
	    }

	    execObject = function(operation,operateParams,pType, pDeptid){
	    	if(operation == 'save'){
	    		saveDeptItem(operateParams);
	    	}else if(operation == 'update'){
	    		updateDeptItem(operateParams);
	    	}
	    	else if(operation == 'delete'){
	    		deleteDeptItems(pType, pDeptid,operateParams);
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
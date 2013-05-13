/**
 * G4����
 * 
 * @author XiongChun
 * @since 2010-12-24
 */
var store, tabPanel;
Ext.onReady(function() {
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				header : '������',
				dataIndex : 'topicid',
				hidden : true
			}, {
				header : '�û�ID',
				dataIndex : 'userid',
				hidden : true
			}, {
				header : '�������',
				dataIndex : 'topictype',
				hidden : true
			}, {
				header : '�Ƿ�ɻ���',
				dataIndex : 'replyable',
				hidden : true
			}, {
				id : 'title',
				header : '����',
				dataIndex : 'title',
				renderer : titleRender
			}, {
				header : '�ظ�/���',
				dataIndex : 'replyview',
				width : 80
			}, {
				header : '����',
				dataIndex : 'username',
				width : 80
			}, {
				header : '������ʱ��',
				dataIndex : 'updatetime',
				width : 130
			}]);

	/**
	 * ���ݴ洢
	 */
	store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'projectHome.ered?reqCode=queryTopics'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'TOTALCOUNT',
							root : 'ROOT'
						}, [{
									name : 'topicid'
								}, {
									name : 'userid'
								}, {
									name : 'topictype'
								}, {
									name : 'replyable'
								}, {
									name : 'title'
								}, {
									name : 'replyview'
								}, {
									name : 'username'
								}, {
									name : 'updatetime'
								}])
			});

	// ��ҳ����ʱ���ϲ�ѯ����
	store.on('beforeload', function() {
				this.baseParams = {
					topictype : topicTypeCombo.getValue(),
					username : Ext.getCmp('username').getValue(),
					title : Ext.getCmp('title').getValue()
				};
			});

	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				hiddenName : 'pagesize',
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10��/ҳ'], [20, '20��/ҳ'],
									[50, '50��/ҳ'], [100, '100��/ҳ'],
									[250, '250��/ҳ'], [500, '500��/ҳ']]
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
				store.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			});

	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '��ʾ{0}����{1}��,��{2}��',
				plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
				// emptyMsg
				// :
				// "û�з��������ļ�¼",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	var topicTypeStore = new Ext.data.SimpleStore({
				fields : ['text', 'value'],
				data : [['��ʾȫ��', '0'], ['��Ŀ���¶�̬', '1'], ['����|Bug�ύ', '2'],
						['��ѯ|��������', '3'], ['G4֪ʶ��', '4'], ['G4��ˮ��԰', '5']]
			});

	var topicTypeCombo = new Ext.form.ComboBox({
				// id : 'topictype',
				hiddenName : 'topictype',
				store : topicTypeStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				fieldLabel : '�������',
				emptyText : '�������',
				value : '0',
				forceSelection : false,
				editable : false,
				typeAhead : true,
				width : 130
			});

	topicTypeCombo.on('select', function(combo) {
				var value = combo.getValue();
				queryTopics();
			});

	var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">G4���������б�</span>',
				iconCls : 'user_commentIcon',
				store : store,
				loadMask : {
					msg : '���ڼ��ر������,���Ե�...'
				},
				stripeRows : true,
				// frame : true,
				autoExpandColumn : 'title',
				cm : cm,
				tbar : [{
							text : '��������',
							iconCls : 'message_editIcon',
							handler : function() {
								newTopic();
							}
						}, '-', {
							text : '�Ķ�����',
							iconCls : 'book_previousIcon',
							handler : function() {
								var record = grid.getSelectionModel()
										.getSelected();
								if (Ext.isEmpty(record)) {
									Ext.MessageBox.alert('��ʾ', '����ѡ��һ������!');
									return;
								}
								previewInit(record);
							}
						}, '->', topicTypeCombo, '-', new Ext.form.TextField({
									id : 'username',
									name : 'username',
									emptyText : '����',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												queryTopics();
											}
										}
									},
									width : 80
								}), '-', new Ext.form.TextField({
									id : 'title',
									name : 'title',
									emptyText : '����',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												queryTopics();
											}
										}
									},
									width : 180
								}), {
							text : '��ѯ',
							iconCls : 'previewIcon',
							handler : function() {
								queryTopics();
							}
						}, '-', {
							text : 'ˢ��',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}],
				bbar : bbar
			});

	grid.on("celldblclick", function(grid, rowIndex, columnIndex, e) {
				var store = grid.getStore();
				var record = store.getAt(rowIndex);
				previewInit(record);
			});

	// �����Ǳ��Ƕ�׵���Tab����,��һ�μ������ݲ��������Ӱ���֣������˴���
	grid.on("render", function() {
				setTimeout(function() {
							store.load({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}, 1);
			});

	var contextmenu = new Ext.menu.Menu({
				id : 'theContextMenu',
				items : [{
							text : '�Ķ�����',
							iconCls : 'book_previousIcon',
							handler : function() {
								var record = grid.getSelectionModel()
										.getSelected();
								previewInit(record);
							}
						}, {
							text : '��������',
							iconCls : 'message_editIcon',
							handler : function() {
								newTopic();
							}
						}]
			});

	grid.on("rowcontextmenu", function(grid, rowIndex, e) {
				e.preventDefault();
				grid.getSelectionModel().selectRow(rowIndex);
				contextmenu.showAt(e.getXY());
			});

	var newTopicTypeStore = new Ext.data.SimpleStore({
				fields : ['text', 'value'],
				data : [['����|Bug�ύ', '2'], ['��ѯ|��������', '3'], ['G4֪ʶ��', '4'],
						['G4��ˮ��԰', '5']]
			});

	var newTopicTypeCombo = new Ext.form.ComboBox({
				hiddenName : 'topictype',
				store : newTopicTypeStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				fieldLabel : '����',
				emptyText : '��ѡ���������',
				forceSelection : false,
				allowBlank : false,
				editable : false,
				typeAhead : true,
				anchor : '35%'
			});

	var newTopicPanel = new Ext.form.FormPanel({
				bodyStyle : 'padding:5 5 5 5',
				labelAlign : 'right', // ��ǩ���뷽ʽ
				frame : true,
				labelWidth : 35,
				items : [{
							layout : 'column',
							border : false,
							items : [{
										columnWidth : .99,
										layout : 'form',
										defaultType : 'textfield',
										border : false,
										items : [newTopicTypeCombo, {
													fieldLabel : '����',
													emptyText : '����������������(����ȡ��һ��Ŷ)',
													maxLength : 50,
													name : 'title',
													allowBlank : false,
													anchor : '100%'
												}]
									}]
						}, {
							layout : 'fit',
							border : false,
							items : [{
										id : 'contentAdd',
										name : 'contentAdd',
										xtype : 'htmleditor',
										allowBlank : false,
										enableSourceEdit : false,
										anchor : '99%'
									}]

						}]
			});

	var newTopicWindow = new Ext.Window({
				title : '<span style="font-weight:normal">������������</span>', // ���ڱ���
				layout : 'fit', // ���ô��ڲ���ģʽ
				iconCls : 'message_editIcon',
				modal : true,
				width : 650, // ���ڿ��
				height : 380, // ���ڸ߶�
				closable : true, // �Ƿ�ɹر�
				collapsible : false, // �Ƿ������
				maximizable : true, // �����Ƿ�������
				closeAction : 'hide', // �رղ���
				border : false, // �߿�������
				constrain : true, // ���ô����Ƿ�������������
				animateTarget : Ext.getBody(),
				pageY : 50, // ҳ�涨λY����
				pageX : document.body.clientWidth / 2 - 650 / 2, // ҳ�涨λX����
				items : [newTopicPanel], // Ƕ��ı����
				buttons : [{ // ���ڵײ���ť����
					text : '����', // ��ť�ı�
					iconCls : 'acceptIcon', // ��ťͼ��
					handler : function() { // ��ť��Ӧ����
						saveNewTopic();
					}
				}, {	// ���ڵײ���ť����
							text : '�ر�', // ��ť�ı�
							iconCls : 'deleteIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								newTopicWindow.hide();
							}
						}]
			});

	var replyPanel = new Ext.form.FormPanel({
				// bodyStyle : 'padding:5 5 5 5',
				labelAlign : 'right', // ��ǩ���뷽ʽ
				frame : true,
				labelWidth : 1,
				items : [{
							id : 'contentReply',
							name : 'contentReply',
							xtype : 'htmleditor',
							allowBlank : false,
							enableSourceEdit : false,
							anchor : '100%'
						}, {
							id : 'topicidReply',
							name : 'topicidReply',
							xtype : 'hidden'
						}]
			});

	var replyWindow = new Ext.Window({
				title : '<span style="font-weight:normal">����,��������</span>', // ���ڱ���
				layout : 'fit', // ���ô��ڲ���ģʽ
				iconCls : 'edit1Icon',
				modal : true,
				width : 650, // ���ڿ��
				height : 380, // ���ڸ߶�
				closable : true, // �Ƿ�ɹر�
				collapsible : false, // �Ƿ������
				maximizable : true, // �����Ƿ�������
				closeAction : 'hide', // �رղ���
				border : false, // �߿�������
				constrain : true, // ���ô����Ƿ�������������
				animateTarget : Ext.getBody(),
				pageY : 50, // ҳ�涨λY����
				pageX : document.body.clientWidth / 2 - 650 / 2, // ҳ�涨λX����
				items : [replyPanel], // Ƕ��ı����
				buttons : [{ // ���ڵײ���ť����
					text : '����', // ��ť�ı�
					iconCls : 'acceptIcon', // ��ťͼ��
					handler : function() { // ��ť��Ӧ����
						replyTopic();
					}
				}, {	// ���ڵײ���ť����
							text : '�ر�', // ��ť�ı�
							iconCls : 'deleteIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								replyWindow.hide();
							}
						}]
			});

	tabPanel = new Ext.TabPanel({
				activeTab : 0,
				region : 'center',
				border : false,
				items : [grid]

			});

	/**
	 * ����
	 */
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [tabPanel]
			});

	/**
	 * ��ѯ�¼�
	 */
	function queryTopics() {
		store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						topictype : topicTypeCombo.getValue(),
						username : Ext.getCmp('username').getValue(),
						title : Ext.getCmp('title').getValue()
					}
				});
	}

	/**
	 * ������
	 */
	function newTopic() {
		newTopicPanel.form.reset();
		newTopicWindow.show();
	}

	function saveNewTopic() {
		if (!newTopicPanel.getForm().isValid()) {
			return;
		}
		var value = Ext.getCmp('contentAdd').getValue();
		if (value.length < 1) {
			Ext.MessageBox.alert('��ʾ', '�������������������ݺ����ύ');
			return;
		}
		newTopicPanel.form.submit({
					url : 'projectHome.ered?reqCode=saveNewTopic',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) { // �ص�������2������
						// Ext.MessageBox.alert('��ʾ',
						// action.result.msg);
						store.reload();
						newTopicWindow.hide();
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
					}
				});
	}

	function replyTopic() {
		var value = Ext.getCmp('contentReply').getValue();
		if (value.length < 1 || value.trim() == '<br>') {
			Ext.MessageBox.alert('��ʾ', '�����������Ļ������ݺ����ύ');
			return;
		}
		replyPanel.form.submit({
					url : 'projectHome.ered?reqCode=saveReplyTopic',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						// Ext.MessageBox.alert('��ʾ',
						// action.result.msg);
						// store.reload();
						replyWindow.hide();
						var topicid = Ext.getCmp('topicidReply').getValue()
						var topPanel = Ext.getCmp('panel_top_' + topicid);
						var value = Ext.getCmp('contentReply').getValue();
						tabPanel.getItem('tab' + topicid).getUpdater().update({
									url : 'projectHome.ered?reqCode=previewTopicInit',
									params : {
										topicid : topicid
									}
								});
						/*
						 * topPanel.add({ title : '<span
						 * style="font-weight:normal">�Ҹոջظ�</span>', xtype :
						 * 'panel', collapsible : true, border : false,
						 * titleCollapse : true, html : '<div class="myDiv"
						 * style="margin: 8px;">' + value + '</div>' });
						 * topPanel.doLayout();
						 */
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('��ʾ', '�������ݱ���ʧ��');
					}
				});
	}

	function previewInit(rd) {
		var title = rd.get('title');
		if (title.length > 12) {
			title = title.substring(0, 12) + '...';
		}
		tabPanel.add({
					id : 'tab' + rd.get('topicid'),
					title : title,
					closable : true,
					autoScroll : true,
					tbar : [{
						text : '����,��������',
						iconCls : 'edit1Icon',
						handler : function() {
							replyPanel.getForm().reset();
							Ext.getCmp('topicidReply').setValue(rd
									.get('topicid'));
							replyWindow.show();
							// Ext.getCmp('contentReply').focus();
						}
					}],
					autoLoad : {
						url : 'projectHome.ered?reqCode=previewTopicInit',
						text : '<span class="font12">���Խ���ģ��������������ҳ��,��ȴ�...</span>',
						scripts : true,
						params : {
							topicid : rd.get('topicid')
						}
					}
				});
		tabPanel.setActiveTab('tab' + rd.get('topicid'));
	}

	function titleRender(value, cellMetaData, record) {
		var topictype = record.data.topictype;
		var type = '';
		if (topictype == '1')
			type = '[��Ŀ���¶�̬]';
		else if (topictype == '2')
			type = '[����|Bug�ύ]';
		else if (topictype == '3')
			type = '[��ѯ|��������]';
		else if (topictype == '4')
			type = '[G4֪ʶ��]';
		else if (topictype == '5')
			type = '[G4��ˮ��԰]';
		value = '<span style="color:blue">' + type + '</span>&nbsp;' + value;
		return value;
	}

});
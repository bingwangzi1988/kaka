/**
 * �ۺ�ʵ��������ά��(�ĺ�һ)
 * 
 * @author XiongChun
 * @since 2010-11-20
 */
Ext.onReady(function() {
			var qForm = new Ext.form.FormPanel({
						region : 'north',
						title : '<span style="font-weight:normal">��ѯ����<span>',
						collapsible : true,
						border : true,
						labelWidth : 50, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:3 5 0', // ��Ԫ�غͱ����ı߾�
						buttonAlign : 'center',
						height : 120,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��ĿID',
															name : 'xmid',
															xtype : 'numberfield', // ����Ϊ�������������
															anchor : '100%'
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��Ŀ����', // ��ǩ
															id : 'xmmc',
															name : 'xmmc', // name:��̨���ݴ�name����ȡֵ
															allowBlank : true, // �Ƿ�����Ϊ��
															maxLength : 50, // �����������ı�����,��������Ӣ���ַ�
															anchor : '100%' // ��Ȱٷֱ�
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [new Ext.form.ComboBox({
															hiddenName : 'sfdlbm',
															fieldLabel : '��Ŀ����',
															emptyText : '��ѡ��',
															triggerAction : 'all',
															store : new Ext.data.SimpleStore({
																		fields : ['name', 'code'],
																		data : [['��ҩ', '01'], ['�г�ҩ', '02']]
																	}),
															displayField : 'name',
															valueField : 'code',
															mode : 'local',
															forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
															editable : false,
															typeAhead : true,
															// value:'0002',
															resizable : true,
															anchor : '100%'
														})]
											}]
								}, {
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [new Ext.form.ComboBox({
															hiddenName : 'jx',
															fieldLabel : '����',
															triggerAction : 'all',
															emptyText : '��ѡ��',
															store : new Ext.data.SimpleStore({
																		fields : ['name', 'code'],
																		data : [['ע���', 'ע���'], ['���', '���'], ['Ƭ��', 'Ƭ��']]
																	}),
															displayField : 'name',
															valueField : 'code',
															mode : 'local',
															forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
															editable : false,
															typeAhead : true,
															resizable : true,
															anchor : '100%'
														})]
											}, {
												columnWidth : .67,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����',
															name : 'cd',
															maxLength : 50,
															xtype : 'textfield',
															anchor : '99%'
														}]
											}]
								}],
						buttons : [{
									text : '��ѯ',
									iconCls : 'previewIcon',
									handler : function() {
										Ext.getCmp('tbi_edit').disable();
										Ext.getCmp('tbi_del').disable();
										querySfxmDatas();
									}
								}, {
									text : '��ӡ',
									iconCls : 'printerIcon',
									handler : function() {
										printCatalog1();
									}
								},{
									text : '����',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										qForm.getForm().reset();
									}
								}]
					});

			// �����Զ���ǰҳ�к�
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});

			// ������ģ��
			var cm = new Ext.grid.ColumnModel([rownum, {
				header : '��ĿID', // �б���
				dataIndex : 'xmid', // ��������:��Storeģ�Ͷ�Ӧ
				sortable : true
					// �Ƿ������
				}, {
				header : '����',
				dataIndex : 'sfdlbm',
				hidden : true, // ������
				sortable : true,
				width : 50
					// �п�
				}, {
				header : '��Ŀ����',
				dataIndex : 'xmmc',
				sortable : true
			}, {
				header : '��Ŀ�ȼ�',
				dataIndex : 'xmrj'
			}, {
				header : '��������',
				dataIndex : 'zfbl'
			}, {
				header : '���',
				dataIndex : 'gg'
			}, {
				header : '��λ',
				dataIndex : 'dw',
				width : 60
			}, {
				header : '����״̬',
				dataIndex : 'qybz',
				// ��ʾrender���÷�(����ת��,��render��<eRedG4:ext.codeRender/>��ǩ����)
				renderer : QYBZRender,
				width : 60
			}, {
				header : '����',
				dataIndex : 'jx',
				width : 60
			}, {
				header : '����',
				dataIndex : 'cd',
				width : 200
			}, {
				header : 'ҽԺ����',
				dataIndex : 'yybm'
			}, {
				header : '����ʱ��',
				dataIndex : 'ggsj'
			}]);
			

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						// ��ȡ���ݵķ�ʽ
						proxy : new Ext.data.HttpProxy({
									url : 'integrateDemo.ered?reqCode=querySfxmDatas'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT', // ��¼����
									root : 'ROOT' // Json�е��б����ݸ��ڵ�
								}, [{
											name : 'xmid' // Json�е�����Keyֵ
										}, {
											name : 'sfdlbm'
										}, {
											name : 'xmmc'
										}, {
											name : 'xmrj'
										}, {
											name : 'gg'
										}, {
											name : 'dw'
										}, {
											name : 'qybz'
										}, {
											name : 'jx'
										}, {
											name : 'cd'
										}, {
											name : 'yybm'
										}, {
											name : 'ggsj'
										}, {
											name : 'zfbl'
										}])
					});

			/**
			 * ��ҳ����ʱ��Ĳ�������
			 */
			// ��ҳ����ʱ���ϲ�ѯ����
			store.on('beforeload', function() {
						this.baseParams = qForm.getForm().getValues();
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
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

			// ��񹤾���
			var tbar = new Ext.Toolbar({
						items : [{
									text : '����',
									iconCls : 'addIcon',
									handler : function() {
										addCatalogItem();
									}
								}, {
									text : '�޸�',
									id : 'tbi_edit',
									iconCls : 'edit1Icon',
									disabled : true,
									handler : function() {
										updateCatalogItem();
									}
								}, {
									text : 'ɾ��',
									id : 'tbi_del',
									iconCls : 'deleteIcon',
									disabled : true,
									handler : function() {
										deleteCatalogItem();
									}
								}, '->', {
									text : 'ˢ��',
									iconCls : 'arrow_refreshIcon',
									handler : function() {
										store.reload();
									}
								}]
					});

			// ���ʵ��
			var grid = new Ext.grid.GridPanel({
						// ���������,Ĭ��Ϊ���壬�Ҳ�ϲ�����壬����������ʽ�����ʽΪ��������
						title : '<span style="font-weight:normal">ҽԺ�շ���Ŀ</span>',
						height : 500,
						autoScroll : true,
						frame : true,
						region : 'center', // ��VIEWPORT����ģ�Ͷ�Ӧ���䵱center���򲼾�
						store : store, // ���ݴ洢
						stripeRows : true, // ������
						cm : cm, // ��ģ��
						tbar : tbar, // ��񹤾���
						bbar : bbar,// ��ҳ������
						viewConfig : {
			// ����������������, �����Զ���չ�Զ�ѹ��, �����������Ƚ��ٵ����
						// forceFit : true
						},
						loadMask : {
							msg : '���ڼ��ر������,���Ե�...'
						}
					});

			// ������ѡ���¼�
			grid.on('rowclick', function(pGrid, rowIndex, event) {
						Ext.getCmp('tbi_edit').enable();
						Ext.getCmp('tbi_del').enable();
					});

			grid.on('rowdblclick', function(grid, rowIndex, event) {
						updateCatalogItem();
					});

			var myForm = new Ext.form.FormPanel({
						collapsible : false,
						border : true,
						labelWidth : 60, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 0', // ��Ԫ�غͱ����ı߾�
						buttonAlign : 'center',
						height : 250,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��ĿID',
															name : 'xmid',
															disabled : true,
															fieldClass : 'x-custom-field-disabled',
															xtype : 'textfield', // ����Ϊ�������������
															anchor : '100%'
														}, {
															fieldLabel : '��Ŀ�ȼ�',
															name : 'xmrj',
															maxLength : 20,
															anchor : '100%'
														}, {
															fieldLabel : '��������',
															name : 'zfbl',
															maxValue : 1,
															decimalPrecision : 2,// С������
															allowBlank : false,
															xtype : 'numberfield',
															labelStyle : 'color:blue;',
															anchor : '100%'
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��Ŀ����', // ��ǩ
															name : 'xmmc', // name:��̨���ݴ�name����ȡֵ
															maxLength : 20, // �����������ı�����,��������Ӣ���ַ�
															allowBlank : false,
															labelStyle : 'color:blue;',
															anchor : '100%'// ��Ȱٷֱ�
														}, {
															fieldLabel : '���',
															name : 'gg',
															xtype : 'textfield', // ����Ϊ�������������
															maxLength : 25,
															anchor : '100%'
														}, new Ext.form.ComboBox({
																	hiddenName : 'jx',
																	fieldLabel : '����',
																	triggerAction : 'all',
																	emptyText : '��ѡ��',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['ע���', 'ע���'], ['���', '���'], ['Ƭ��', 'Ƭ��']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	resizable : true,
																	allowBlank : false,
																	anchor : '100%'
																})]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [new Ext.form.ComboBox({
																	hiddenName : 'sfdlbm',
																	fieldLabel : '��Ŀ����',
																	emptyText : '��ѡ��',
																	triggerAction : 'all',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['��ҩ', '01'], ['�г�ҩ', '02']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	// value:'0002',
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																}), new Ext.form.ComboBox({
																	hiddenName : 'dw',
																	fieldLabel : '��λ',
																	emptyText : '��ѡ��',
																	triggerAction : 'all',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['ƿ', 'ƿ'], ['��', '��']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	// value:'0002',
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																}), new Ext.form.ComboBox({
																	hiddenName : 'qybz',
																	fieldLabel : '���ñ�־',
																	triggerAction : 'all',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['����', '1'], ['ͣ��', '0']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	value : '1',
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																})]
											}]
								}, {
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															xtype : 'datefield',
															fieldLabel : '��Чʱ��', // ��ǩ
															name : 'ggsj', // name:��̨���ݴ�name����ȡֵ
															format : 'Y-m-d', // ���ڸ�ʽ��
															value : new Date(),
															anchor : '100%' // ��Ȱٷֱ�
														}]
											}, {
												columnWidth : .67,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����',
															name : 'cd',
															maxLength : 50,
															anchor : '99%'
														}]
											}]
								}, {
									fieldLabel : '��ע',
									name : 'remark',
									xtype : 'textarea',
									maxLength : 100,
									emptyText : '��ע��Ϣ.(˵��:���ֶν�������ʾ�����в���,���ݲ����г־û�)',
									anchor : '99%'
								}]
					});

			var firstWindow = new Ext.Window({
						title : '<span style="font-weight:normal">¼��ҽԺ�շ���Ŀ<span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 600, // ���ڿ��
						height : 260, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 600 / 2, // ҳ�涨λX����
						items : [myForm], // Ƕ��ı����
						buttons : [{
									text : '����1',
									iconCls : 'acceptIcon',
									handler : function() {
										submitTheForm();
									}
								},{
									text : '����2(batch������)',
									iconCls : 'acceptIcon',
									handler : function() {
										submitTheFormBasedBatch();
									}
								}, {
									text : '����',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										myForm.getForm().reset();
									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										firstWindow.hide();
									}
								}]
					});

			var updateForm = new Ext.form.FormPanel({
						collapsible : false,
						border : true,
						labelWidth : 60, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 0', // ��Ԫ�غͱ����ı߾�
						buttonAlign : 'center',
						height : 250,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��ĿID',
															name : 'xmid',
															readOnly : true,
															fieldClass : 'x-custom-field-disabled',
															xtype : 'textfield', // ����Ϊ�������������
															anchor : '100%'
														}, {
															fieldLabel : '��Ŀ�ȼ�',
															name : 'xmrj',
															maxLength : 20,
															anchor : '100%'
														}, {
															fieldLabel : '��������',
															name : 'zfbl',
															maxValue : 1,
															decimalPrecision : 2,// С������
															allowBlank : false,
															xtype : 'numberfield',
															labelStyle : 'color:blue;',
															anchor : '100%'
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��Ŀ����', // ��ǩ
															name : 'xmmc', // name:��̨���ݴ�name����ȡֵ
															maxLength : 20, // �����������ı�����,��������Ӣ���ַ�
															allowBlank : false,
															labelStyle : 'color:blue;',
															anchor : '100%'// ��Ȱٷֱ�
														}, {
															fieldLabel : '���',
															name : 'gg',
															xtype : 'textfield', // ����Ϊ�������������
															maxLength : 25,
															anchor : '100%'
														}, new Ext.form.ComboBox({
																	hiddenName : 'jx',
																	fieldLabel : '����',
																	triggerAction : 'all',
																	emptyText : '��ѡ��',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['ע���', 'ע���'], ['���', '���'], ['Ƭ��', 'Ƭ��']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	resizable : true,
																	allowBlank : false,
																	anchor : '100%'
																})]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [new Ext.form.ComboBox({
																	hiddenName : 'sfdlbm',
																	fieldLabel : '��Ŀ����',
																	emptyText : '��ѡ��',
																	triggerAction : 'all',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['��ҩ', '01'], ['�г�ҩ', '02']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	// value:'0002',
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																}), new Ext.form.ComboBox({
																	hiddenName : 'dw',
																	fieldLabel : '��λ',
																	emptyText : '��ѡ��',
																	triggerAction : 'all',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['ƿ', 'ƿ'], ['��', '��']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	// value:'0002',
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																}), new Ext.form.ComboBox({
																	hiddenName : 'qybz',
																	fieldLabel : '���ñ�־',
																	triggerAction : 'all',
																	store : new Ext.data.SimpleStore({
																				fields : ['name', 'code'],
																				data : [['����', '1'], ['ͣ��', '0']]
																			}),
																	displayField : 'name',
																	valueField : 'code',
																	mode : 'local',
																	forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
																	editable : false,
																	typeAhead : true,
																	value : '1',
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																})]
											}]
								}, {
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															xtype : 'datefield',
															fieldLabel : '��Чʱ��', // ��ǩ
															name : 'ggsj', // name:��̨���ݴ�name����ȡֵ
															format : 'Y-m-d', // ���ڸ�ʽ��
															value : new Date(),
															anchor : '100%' // ��Ȱٷֱ�
														}]
											}, {
												columnWidth : .67,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����',
															name : 'cd',
															maxLength : 50,
															anchor : '99%'
														}]
											}]
								}, {
									fieldLabel : '��ע',
									name : 'remark',
									xtype : 'textarea',
									maxLength : 100,
									emptyText : '��ע��Ϣ.(˵��:���ֶν�������ʾ�����в���,���ݲ����г־û�)',
									anchor : '99%'
								}]
					});

			var updateWindow = new Ext.Window({
						title : '<span style="font-weight:normal">�޸�ҽԺ�շ���Ŀ<span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 600, // ���ڿ��
						height : 260, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 600 / 2, // ҳ�涨λX����
						items : [updateForm], // Ƕ��ı����
						buttons : [{
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										updateTheForm();
									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										updateWindow.hide();
									}
								}]
					});

			// ����
			// �����form��Ϊcenter����Ļ�,��Height���Խ�ʧЧ��
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [qForm, grid]
					});

			/**
			 * ��ѯ��Ŀ�б�
			 */
			function querySfxmDatas() {
				var params = qForm.getForm().getValues();
				params.start = 0;
				params.limit = bbar.pageSize;
				store.load({
							params : params
						});
			}

			/**
			 * ���ύ(���Դ�Ajax�ύ)
			 */
			function submitTheForm() {
				if (!myForm.getForm().isValid())
					return;
				myForm.form.submit({
							url : 'integrateDemo.ered?reqCode=updateSfxm',
							waitTitle : '��ʾ',
							method : 'POST',
							waitMsg : '���ڴ�������,���Ժ�...',
							success : function(form, action) { // �ص�������2������
								Ext.MessageBox.alert('��ʾ', action.result.msg);
							},
							failure : function(form, action) {
								Ext.Msg.alert('��ʾ', '���ݲ�ѯʧ��,��������:' + action.failureType);
							}
						});
			}
			
			/**
			 * ���ύ(��Batch��ʽ����ִ��SQL���)
			 */
			function submitTheFormBasedBatch() {
				if (!myForm.getForm().isValid())
					return;
				myForm.form.submit({
							url : 'integrateDemo.ered?reqCode=batchSql',
							waitTitle : '��ʾ',
							method : 'POST',
							waitMsg : '���ڴ�������,���Ժ�...',
							success : function(form, action) { 
								Ext.MessageBox.alert('��ʾ', action.result.msg);
							},
							failure : function(form, action) {
								Ext.Msg.alert('��ʾ', '���ݲ�ѯʧ��,��������:' + action.failureType);
							}
						});
			}

			/**
			 * ������Ŀ
			 */
			function addCatalogItem() {
				firstWindow.show(); // ��ʾ����
			}

			/**
			 * ���ύ(���Դ�Ajax�ύ)
			 */
			function submitTheForm() {
				if (!myForm.getForm().isValid())
					return;
				myForm.form.submit({
							url : 'integrateDemo.ered?reqCode=saveSfxmDomain',
							waitTitle : '��ʾ',
							method : 'POST',
							waitMsg : '���ڴ�������,���Ժ�...',
							success : function(form, action) { // �ص�������2������
								// Ext.MessageBox.alert('��ʾ',
								// action.result.msg);
								var items = myForm.find('name', 'xmid');
								items[0].setValue(action.result.xmid);
								Ext.Msg.confirm('��ȷ��', '�����ɹ�,��Ҫ���������շ���Ŀ��?', function(btn, text) {
											if (btn == 'yes') {
												myForm.getForm().reset();
											} else {
												firstWindow.hide();
												store.reload();
											}
										});
							},
							failure : function(form, action) {
								Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
							}
						});
			}

			/**
			 * �޸���Ŀ
			 */
			function updateCatalogItem() {
				var record = grid.getSelectionModel().getSelected();
				if (Ext.isEmpty(record)) {
					Ext.Msg.alert('��ʾ:', '����ѡ����Ŀ');
					return;
				}
				updateForm.getForm().loadRecord(record);
				updateWindow.show(); // ��ʾ����
			}

			/**
			 * ���ύ(���Դ�Ajax�ύ)
			 */
			function updateTheForm() {
				if (!updateForm.getForm().isValid())
					return;
				updateForm.form.submit({
							url : 'integrateDemo.ered?reqCode=updateSfxm',
							waitTitle : '��ʾ',
							method : 'POST',
							waitMsg : '���ڴ�������,���Ժ�...',
							success : function(form, action) { // �ص�������2������
								Ext.MessageBox.alert('��ʾ', action.result.msg);
								updateWindow.hide();
								store.reload();
							},
							failure : function(form, action) {
								Ext.Msg.alert('��ʾ', '���ݱ���ʧ��,��������:' + action.failureType);
							}
						});
			}

			/**
			 * ɾ����Ŀ
			 */
			function deleteCatalogItem() {
				var record = grid.getSelectionModel().getSelected();
				if (Ext.isEmpty(record)) {
					Ext.Msg.alert('��ʾ:', '����ѡ����Ŀ');
					return;
				}
				Ext.MessageBox.confirm('��ȷ��', 'ȷ��ɾ����?', function(btn, text) {
							if (btn == 'yes') {
								if (runMode == '0') {
									Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
									return;
								}
								showWaitMsg();
								Ext.Ajax.request({
											url : 'integrateDemo.ered?reqCode=deleteSfxm',
											success : function(response) { // �ص�������1������
												var resultArray = Ext.util.JSON.decode(response.responseText);
												Ext.Msg.alert('��ʾ', resultArray.msg);
												store.reload();
											},
											failure : function(response) {
												Ext.MessageBox.alert('��ʾ', '��ɾ��ʧ��');
											},
											params : {
												xmid : record.data.xmid
											}
										});
							}
						})
			}
			
			/**
			 * ��ӡһ
			 */
			function printCatalog1() {
				showWaitMsg('����׼����������,���Ե�...');
				Ext.Ajax.request({
							url : 'integrateDemo.ered?reqCode=buildReportDataObject',
							success : function(response) {
								hideWaitMsg();
								doPrint('hisCatalogReport4App');
							},
							failure : function(response) {
								hideWaitMsg();
								Ext.Msg.alert('��ʾ', "׼���������ݶ���������,����!");
							}
						});
			}

		});
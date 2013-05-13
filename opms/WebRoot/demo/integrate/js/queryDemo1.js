/**
 * �ۺ�ʵ������ѯ1
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
						labelWidth : 90, // ��ǩ���
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
															fieldLabel : '���˱���',
															name : 'grbm',
															xtype : 'textfield', // ����Ϊ�������������
															anchor : '100%'
														}, {
															fieldLabel : '�������',
															name : 'xnl1',
															xtype : 'numberfield', // ����Ϊ�������������
															allowDecimals : false, // �Ƿ���������С��
															allowNegative : false, // �Ƿ��������븺��
															maxValue : 120,
															anchor : '100%'
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����', // ��ǩ
															id : 'xm',
															name : 'xm', // name:��̨���ݴ�name����ȡֵ
															allowBlank : true, // �Ƿ�����Ϊ��
															maxLength : 6, // �����������ı�����,��������Ӣ���ַ�
															anchor : '100%' // ��Ȱٷֱ�
														}, {
															fieldLabel : '��',
															name : 'xnl2',
															xtype : 'numberfield', // ����Ϊ�������������
															allowDecimals : false, // �Ƿ���������С��
															allowNegative : false, // �Ƿ��������븺��
															maxValue : 120,
															anchor : '100%'
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��λ����', // ��ǩ
															name : 'dwmc', // name:��̨���ݴ�name����ȡֵ
															maxLength : 20, // �����������ı�����,��������Ӣ���ַ�
															allowBlank : true,
															anchor : '100%'// ��Ȱٷֱ�
														}]
											}]
								}],
						buttons : [{
									text : '��ѯ',
									iconCls : 'previewIcon',
									handler : function() {
										queryBalanceInfo(qForm.getForm());
									}
								}, {
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
				header : '����˳���', // �б���
				dataIndex : 'sxh', // ��������:��Storeģ�Ͷ�Ӧ
				width : 200,
				sortable : true
					// �Ƿ������
				}, {
				header : '���˱���',
				dataIndex : 'grbm',
				sortable : true
			}, {
				header : '����',
				dataIndex : 'xm'
			}, {
				header : '�Ա�',
				dataIndex : 'xb'
			}, {
				header : '������',
				dataIndex : 'xnl'
			}, {
				header : '�����ܶ�',
				dataIndex : 'fyze'
			}, {
				header : 'ҽ������',
				dataIndex : 'ybbx'
			}, {
				header : '�Ը����',
				dataIndex : 'zfje'
			}, {
				header : '����ʱ��',
				dataIndex : 'jssj'
			}, {
				header : '����ҽԺ',
				dataIndex : 'yymc'
			}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						// ��ȡ���ݵķ�ʽ
						proxy : new Ext.data.HttpProxy({
									url : 'integrateDemo.ered?reqCode=queryBalanceInfo'
								}),
						// ���ݶ�ȡ��
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT', // ��¼����
									root : 'ROOT' // Json�е��б����ݸ��ڵ�
								}, [{
											name : 'sxh' // Json�е�����Keyֵ
										}, {
											name : 'grbm'
										}, {
											name : 'xm'
										}, {
											name : 'xb'
										}, {
											name : 'xnl'
										}, {
											name : 'fyze'
										}, {
											name : 'ybbx'
										}, {
											name : 'zfje'
										}, {
											name : 'jssj'
										}, {
											name : 'yymc'
										}])
					});

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
			var number = parseInt(pagesize_combo.getValue());
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

			// ���ʵ��
			var grid = new Ext.grid.GridPanel({
						region : 'center', // ��VIEWPORT����ģ�Ͷ�Ӧ���䵱center���򲼾�
						// collapsible : true,
						border : false,
						// ���������,Ĭ��Ϊ���壬�Ҳ�ϲ�����壬����������ʽ�����ʽΪ��������
						title : '<span style="font-weight:normal">���˽�����Ϣ</span>',
						// height : 500,
						autoScroll : true,
						frame : true,
						store : store, // ���ݴ洢
						stripeRows : true, // ������
						cm : cm, // ��ģ��
						bbar : bbar,// ��ҳ������
						viewConfig : {
							// ����������������, �����Զ���չ�Զ�ѹ��, �����������Ƚ��ٵ����
							forceFit : true
						},
						loadMask : {
							msg : '���ڼ��ر������,���Ե�...'
						}
					});

			// ����
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [qForm, grid]
					});

			// ��ѯ�������
			function queryBalanceInfo(pForm) {
				var params = pForm.getValues();
				params.start = 0;
				params.limit = bbar.pageSize;
				store.load({
							params : params
						});
			}

		});
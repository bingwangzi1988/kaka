/**
 * �ۺ�ʵ��������ά��
 * 
 * @author XiongChun
 * @since 2010-11-20
 */
Ext.onReady(function() {
			var qForm = new Ext.form.FormPanel({
						region : 'north',
						title : '<span style="font-weight:normal">��ѯ����<span>',
						collapsible : false,
						border : true,
						labelWidth : 50, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:3 5 0', // ��Ԫ�غͱ����ı߾�
						buttonAlign : 'center',
						height : 100,
						items : [{
									fieldLabel : '��ĿID',
									name : 'xmid',
									value : '1000107740',
									xtype : 'numberfield', // ����Ϊ�������������
									labelStyle : 'color:blue;',
									allowBlank : false,
									enableKeyEvents : true,
											listeners : {
												specialkey : function(field, e) {
													if (e.getKey() == Ext.EventObject.ENTER) {
														myForm.getForm().reset();
														loadCallBack();
													}
												}
											},
									anchor : '33%'
								}],
						buttons : [{
									text : '��ѯ',
									iconCls : 'previewIcon',
									handler : function() {
										myForm.getForm().reset();
										loadCallBack();
									}
								}, {
									text : '����',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										qForm.getForm().reset();
										myForm.getForm().reset();
										Ext.getCmp('btnSave').disable();
									}
								}]
					});
			var myForm = new Ext.form.FormPanel({
						region : 'north',
						title : '<span style="font-weight:normal">�޸�ҽԺ�շ���Ŀ<span>',
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
															fieldLabel : '��Ŀ����', // ��ǩ
															name : 'xmmc', // name:��̨���ݴ�name����ȡֵ
															maxLength : 20, // �����������ı�����,��������Ӣ���ַ�
															allowBlank : false,
															labelStyle : 'color:blue;',
															anchor : '100%'// ��Ȱٷֱ�

														}, {
															fieldLabel : '��������',
															name : 'zfbl',
															maxValue : 1,
															decimalPrecision : 2,// С������
															allowBlank : false,
															labelStyle : 'color:blue;',
															xtype : 'numberfield',
															anchor : '100%'
														}, {
															xtype : 'datefield',
															fieldLabel : '��Чʱ��', // ��ǩ
															name : 'ggsj', // name:��̨���ݴ�name����ȡֵ
															format : 'Y-m-d', // ���ڸ�ʽ��
															anchor : '100%' // ��Ȱٷֱ�
														}]
											}, {
												columnWidth : .33,
												layout : 'form',
												labelWidth : 60, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��Ŀ�ȼ�',
															name : 'xmrj',
															maxLength : 20,
															anchor : '100%'
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
																	labelStyle : 'color:blue;',
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
																	emptyText : '��ѡ��',
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
																	resizable : true,
																	allowBlank : false,
																	labelStyle : 'color:blue;',
																	anchor : '100%'
																})]
											}]
								}, {
									fieldLabel : '����',
									name : 'cd',
									maxLength : 50,
									xtype : 'textfield',
									anchor : '99%'
								}, {
									fieldLabel : '��ע',
									id : 'remark',
									name : 'remark',
									xtype : 'textarea',
									maxLength : 100,
									emptyText : '��ע��Ϣ.(˵��:���ֶν�������ʾ�����в���,���ݲ����г־û�)',
									anchor : '99%'
								}, {
									name : 'xmid',
									id : 'xmid',
									hidden : true,
									xtype : 'textfield' // ����Ϊ�������������
								}],
						buttons : [{
									text : '����',
									iconCls : 'acceptIcon',
									id : 'btnSave',
									disabled : true,
									handler : function() {
										submitTheForm();
									}
								}]
					});

			// ����
			// �����form��Ϊcenter����Ļ�,��Height���Խ�ʧЧ��
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [qForm, {
									region : 'center',
									layout : 'border',
									border : false,
									items : [myForm, {
												region : 'center'
											}]
								}]
					});

			// ���������ݵĻص�����
			function loadCallBack() {
				var params = qForm.getForm().getValues();
				myForm.form.load({
							waitMsg : '���ڴ�������,���Ժ�...',// ��ʾ��Ϣ
							waitTitle : '��ʾ',// ����
							url : 'integrateDemo.ered?reqCode=querySfxm',// �����url��ַ
							params : params,
							// method : 'GET',// ����ʽ
							success : function(form, action) {// ���سɹ��Ĵ�����
								var msg = action.result.data.msg;
								if (msg == 'ok') {
									Ext.getCmp('btnSave').enable();
									return;
								}
								Ext.Msg.alert('��ʾ', msg);
								Ext.getCmp('btnSave').disable();
							},
							failure : function(form, action) {// ����ʧ�ܵĴ�����
								Ext.Msg.alert('��ʾ', '���ݲ�ѯʧ��,��������:' + action.failureType);
							}
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
								Ext.Msg.alert('��ʾ', '���ݱ���ʧ��,��������:' + action.failureType);
							}
						});
			}

		});
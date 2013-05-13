/**
 * ��������ѡ���(�ֵ�����Դ)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var enabledCombo = new Ext.form.ComboBox({
						hiddenName : 'enabled', // ����̨��ֵ�ֶ���
						store : ENABLEDStore, // ���õĴ��������Դ��<eRedG4:ext.codeStore
												// />��ǩ��Ӧ
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text',
						value : '1', // ��ʼѡ�е��б���
						fieldLabel : '����״̬',
						emptyText : '��ѡ��...',
						allowBlank : false,
						forceSelection : true, // ѡ�����ݱ���Ϊ�����б������
						editable : true, // ѡ�������ɱ༭
						typeAhead : true, // �����ʱ���Զ�ƥ���ѡ��Ŀ
						anchor : '100%'
					});

			var userTypeCombo = new Ext.form.ComboBox({
						hiddenName : 'userType',
						store : USERTYPEStore,
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text',
						// value : '1',
						fieldLabel : '��Ա����',
						emptyText : '��ѡ��...',
						allowBlank : true,
						forceSelection : true,
						editable : false,
						typeAhead : true,
						anchor : '100%'
					});

			var editmodeCombo = new Ext.form.ComboBox({
						hiddenName : 'editmode',
						store : EDITMODEStore,
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text',
						// value : '1',
						fieldLabel : '�༭ģʽ',
						emptyText : '��ѡ��...',
						allowBlank : true,
						forceSelection : false,
						editable : true,
						typeAhead : true,
						anchor : '100%'
					});

			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 60, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [enabledCombo, userTypeCombo,editmodeCombo],
											tbar : [{
									text : '��ȡѡ��ֵ',
									handler : function() {
										Ext.MessageBox.alert('��ʾ', '����״̬��ֵΪ��' + enabledCombo.getValue());
									}
								}]
					});

			var firstWindow = new Ext.Window({
						title : '����ѡ���(�ֵ�����Դ)', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 300, // ���ڿ��
						height : 200, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 300 / 2, // ҳ�涨λX����
						items : [firstForm], // Ƕ��ı����
						buttons : [{ // ���ڵײ���ť����
							text : '����', // ��ť�ı�
							iconCls : 'tbar_synchronizeIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								firstForm.form.reset();
							}
						}]
					});
			firstWindow.show(); // ��ʾ����

		});
/**
 * �洢���̵���
 * 
 * @author XiongChun
 * @since 2011-03-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 50, // ��ǩ���
						// frame : true, // �Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [{
									fieldLabel : '������', // ��ǩ
									name : 'number1', // name:��̨���ݴ�name����ȡֵ
									xtype : 'numberfield', // ����Ϊ�������������
									allowDecimals : false, // �Ƿ���������С��
									allowNegative : false, // �Ƿ��������븺��
									allowBlank:false,
									anchor : '40%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '����', // ��ǩ
									name : 'number2', // name:��̨���ݴ�name����ȡֵ
									xtype : 'numberfield', // ����Ϊ�������������
									allowDecimals : false, // �Ƿ���������С��
									allowNegative : false, // �Ƿ��������븺��
									allowBlank:false,
									value : '�ܴ�',
									anchor : '40%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '���', // ��ǩ
									name : 'result', // name:��̨���ݴ�name����ȡֵ
									id:'result',
									readOnly : true,
									anchor : '100%' // ��Ȱٷֱ�
								}]
					});

			var firstWindow = new Ext.Window({
						title : '��ѧ����[��ʾ�洢���̵���]', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 400, // ���ڿ��
						height : 200, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						pageY : 20, // ҳ�涨λX����
						pageX : document.body.clientWidth / 2 - 400 / 2, // ҳ�涨λY����
						items : [firstForm], // Ƕ��ı����
						buttons : [{	// ���ڵײ���ť����
									text : '���ô洢���̽�����ѧ����', // ��ť�ı�
									iconCls : 'acceptIcon', // ��ťͼ��
									handler : function() { // ��ť��Ӧ����
										callPrc();
									}
								}, { // ���ڵײ���ť����
									text : '����', // ��ť�ı�
									iconCls : 'tbar_synchronizeIcon', // ��ťͼ��
									handler : function() { // ��ť��Ӧ����
										firstForm.getForm().reset();
									}
								}]
					});
			firstWindow.show(); // ��ʾ����
			firstForm.getForm().reset();

			/**
			 * �洢�����첽����
			 */
			function callPrc() {
				if (!firstForm.form.isValid()) {
					return;
				}
				var params = firstForm.getForm().getValues();
				Ext.Ajax.request({
							url : 'integrateDemo.ered?reqCode=callPrc',
							success : function(response) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								Ext.getCmp("result").setValue(resultArray.result);
								Ext.Msg.alert('��ʾ', resultArray.msg);
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								Ext.MessageBox.alert('��ʾ', resultArray.msg);
							},
							params : params
						});
			}

		});
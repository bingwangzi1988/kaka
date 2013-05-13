/**
 * ����ƽ�̲���(ȱʡ)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						labelWidth : 80, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						layout : 'form',
						items : [{
									fieldLabel : '��ͨ�ı�', // ��ǩ
									name : 'name', // name:��̨���ݴ�name����ȡֵ
									allowBlank : false, // �Ƿ�����Ϊ��
									maxLength : 8, // �����������ı�����,��������Ӣ���ַ�
									minLength : 2,// ���������С�ı�����,��������Ӣ���ַ�
									anchor : '100%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '���֤������', // ��ǩ
									name : 'personId', // name:��̨���ݴ�name����ȡֵ
									id : 'personId',
									emptyText : '���������֤����',
									maxLength : 18, // �����������ı�����,��������Ӣ���ַ�
									allowBlank : true,
									listeners : {
										'blur' : function(obj) {
											isIdCardNo(obj.getValue());
										}
									},
									anchor : '100%'// ��Ȱٷֱ�
								}, {
									fieldLabel : '�����',
									name : 'password',
									inputType : 'password', // ����Ϊ�������������
									anchor : '100%'
								}, {
									fieldLabel : '��������',
									name : 'num1',
									xtype : 'numberfield', // ����Ϊ�������������
									allowDecimals : false, // �Ƿ���������С��
									allowNegative : false, // �Ƿ��������븺��
									maxValue : 9999, // ������������ֵ
									minValue : 1000, // �����������Сֵ
									anchor : '100%'
								}, {
									fieldLabel : 'С��/����',
									name : 'num2',
									xtype : 'numberfield',
									decimalPrecision : 3,// С������
									anchor : '100%'
								}]
					});

			var firstWindow = new Ext.Window({
						title : '<span style="font-weight:normal">Form����(ȱʡƽ�̲���)</span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 380, // ���ڿ��
						height : 250, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 380 / 2, // ҳ�涨λX����
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
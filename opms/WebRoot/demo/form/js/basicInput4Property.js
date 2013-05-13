/**
 * �����ı���(��������)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 90, // ��ǩ���
						//frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [{
									fieldLabel : '��ͨ�ı�', // ��ǩ
									name : 'name', // name:��̨���ݴ�name����ȡֵ
									allowBlank : false, // �Ƿ�����Ϊ��
									maxLength : 8, // �����������ı�����,��������Ӣ���ַ�
									minLength : 2,// ���������С�ı�����,��������Ӣ���ַ�
									anchor : '100%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '�����ʼ�',
									name : 'email',
									regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,// ��֤�����ʼ���ʽ��������ʽ
									regexText : '�����ʼ���ʽ���Ϸ�', // ��֤����֮�����ʾ��Ϣ
									anchor : '100%'
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
									fieldLabel : 'С��/��������',
									name : 'num2',
									xtype : 'numberfield',
									decimalPrecision : 3,// С������
									anchor : '100%'
								}, {
									fieldLabel : '�����ı���',
									name : 'mutiRow',
									xtype : 'textarea',
									height : 50, // ���ö����ı���ĸ߶�
									emptyText : 'Ĭ�ϳ�ʼֵ', // ����Ĭ�ϳ�ʼֵ
									anchor : '100%'
								}, {
									fieldLabel : 'ֻ�������', // ��ǩ
									name : 'name2', // name:��̨���ݴ�name����ȡֵ
									readOnly : true, // ����ֻ������
									value : 'ҵ�����ڻ�����', // ��ʼֵ
									anchor : '100%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '���������', // ��ǩ
									name : 'name3', // name:��̨���ݴ�name����ȡֵ
									disabled : true, // ���ý�������
									value : '�г���˼������', // ��ʼֵ
									fieldClass : 'x-custom-field-disabled',
									anchor : '100%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '����һ�������ֶ�', // ��ǩ
									name : 'name4', // name:��̨���ݴ�name����ȡֵ
									xtype : 'hidden',
									anchor : '100%' // ��Ȱٷֱ�
								}]
					});
					

			var firstWindow = new Ext.Window({
						title : '�����(��������)', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 380, // ���ڿ��
						height : 350, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
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
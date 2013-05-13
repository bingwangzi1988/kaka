/**
 * ��������ʱ���������
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 65, // ��ǩ���
						frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [{
							        xtype : 'datefield',
									fieldLabel : '��������1', // ��ǩ
									name : 'data1', // name:��̨���ݴ�name����ȡֵ 
									format:'Y-m-d', //���ڸ�ʽ��
									value:new Date(),
									anchor : '100%' // ��Ȱٷֱ�
								},{
							        xtype : 'datefield',
									fieldLabel : '��������2', // ��ǩ
									name : 'data2', // name:��̨���ݴ�name����ȡֵ 
									format:'Y��m��d', //���ڸ�ʽ��
									disabledDays:[0,6], //��ֹѡ�������
									disabledDaysText:'��ֹѡ�������������6',
									anchor : '100%' // ��Ȱٷֱ�
								},{
							        xtype : 'datefield',
									fieldLabel : '��������3', // ��ǩ
									name : 'data3', // name:��̨���ݴ�name����ȡֵ 
									format:'Y-m-d', //���ڸ�ʽ��
									maxValue:'2010-12-31', //����ѡ����������
									minValue:'2010-01-01', //����ѡ�����С����
									anchor : '100%' // ��Ȱٷֱ�
								},{
							        xtype : 'datetimefield',
									fieldLabel : '����ʱ��1', // ��ǩ
									name : 'time1', // name:��̨���ݴ�name����ȡֵ 
									anchor : '100%' // ��Ȱٷֱ�
								}]
					});

			var firstWindow = new Ext.Window({
						title : '���������', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 300, // ���ڿ��
						height : 200, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						pageY : 20, //ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 300 / 2, //ҳ�涨λX����
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
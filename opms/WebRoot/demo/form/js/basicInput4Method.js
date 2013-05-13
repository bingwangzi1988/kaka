/**
 * �����ı���(��Ϊ����)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 65, // ��ǩ���
						// frame : true, // �Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [{
									fieldLabel : '��ͨ�ı�1', // ��ǩ
									id : 'name1',
									name : 'name1', // name:��̨���ݴ�name����ȡֵ
									//���һ�������¼�
									listeners : { 
										'blur' : function(obj) {
											Ext.Msg.alert('��ʾ', '��ͨ�ı�1��[onblur]�¼�������,����ֵΪ��' + obj.getValue());
										}
									},
									anchor : '100%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '��ͨ�ı�2', // ��ǩ
									id : 'name2',
									name : 'name2', // name:��̨���ݴ�name����ȡֵ
									value : '�ܴ�',
									anchor : '100%' // ��Ȱٷֱ�
								}, {
									fieldLabel : '��ͨ�ı�3', // ��ǩ
									id : 'name3',
									name : 'name3', // name:��̨���ݴ�name����ȡֵ
									anchor : '100%' // ��Ȱٷֱ�
								}],
						tbar : [{
									text : 'ֻ��',
									iconCls : 'lockIcon',
									handler : function() {
										firstForm.findById('name3').setReadOnly(true);
										Ext.MessageBox.alert('��ʾ', '��ͨ�ı�3������Ϊֻ��');
									}
								}, {
									text : '�༭',
									iconCls : 'page_edit_1Icon',
									handler : function() {
										firstForm.findById('name3').setReadOnly(false);
										Ext.MessageBox.alert('��ʾ', '��ͨ�ı�3������Ϊ�ɱ༭');
									}
								}, '-', {
									text : '����',
									iconCls : 'deleteIcon',
									handler : function() {
										firstForm.findById('name2').disable();
										firstForm.findById('name2').addClass('x-custom-field-disabled');
										Ext.MessageBox.alert('��ʾ', '��ͨ�ı�2������');
									}
								}, {
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										firstForm.findById('name2').enable();
										firstForm.findById('name2').removeClass('x-custom-field-disabled');
										Ext.MessageBox.alert('��ʾ', '��ͨ�ı�2������');
									}
								}, '-', {
									text : '��ֵ',
									handler : function() {
										firstForm.get('name1').setValue('��,��֮��.');
										Ext.MessageBox.alert('��ʾ', '��ͨ�ı�1����ֵΪ��' + '��,��֮��.');
									}
								}, {
									text : 'ȡֵ',
									handler : function() {
										// ����д����������������ʽָ��ID����
										// firstForm.findById('name3').getValue();
										// Ext.get('name3').getValue()
										var value = firstForm.get('name3').getValue();
										Ext.MessageBox.alert('��ʾ', '��ͨ�ı�3��ֵΪ��' + value);
									}
								}, {
									text : '����',
									handler : function() {
										var value = firstForm.get('name1').focus();
									}
								}]
					});

			var firstWindow = new Ext.Window({
						title : '�����(��������)', // ���ڱ���
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
/**
 * ��������ѡ���(��������Դ)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			// ׼����������
			var store = new Ext.data.SimpleStore({
						fields : ['name', 'code'],
						data : [['����ʡ', '0001'], ['������', '0002'], ['�Ĵ�ʡ', '0003'], ['����ر�������', '0004']]
					});

			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 50, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [new Ext.form.ComboBox({
											id : 'id_area1',
											hiddenName : 'area1',
											fieldLabel : '��ѡ��',
											triggerAction : 'all',
											store : store,
											displayField : 'name',
											valueField : 'code',
											mode : 'local',
											listWidth : 120, // �����б�Ŀ��,Ĭ��Ϊ����ѡ���Ŀ��
											forceSelection : true,
											typeAhead : true,
											value : '0002',
											resizable : true,
											anchor : '95%'
										}), new Ext.form.ComboBox({
											id : 'id_area2',
											hiddenName : 'area2',
											fieldLabel : '��ѡ��',
											emptyText : '��ѡ��',
											triggerAction : 'all',
											store : store,
											displayField : 'name',
											valueField : 'code',
											mode : 'local',
											forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
											editable : false,
											typeAhead : true,
											// value:'0002',
											resizable : true,
											anchor : '95%'
										})],
						tbar : [{
									text : 'ȡֵ',
									iconCls : 'lockIcon',
									handler : function() {
										var value = firstForm.findById('id_area1').getValue();
										Ext.MessageBox.alert('��ʾ', 'ѡ���1��ֵΪ��' + value);
									}
								},'-', {
									text : '����',
									iconCls : 'deleteIcon',
									handler : function() {
										firstForm.findById('id_area1').disable();
										firstForm.findById('id_area1').addClass('x-custom-field-disabled');
										Ext.MessageBox.alert('��ʾ', 'ѡ���1������');
									}
								}, {
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										firstForm.findById('id_area1').enable();
										firstForm.findById('id_area1').removeClass('x-custom-field-disabled');
										Ext.MessageBox.alert('��ʾ', 'ѡ���1������');
									}
								}]
					});

			var firstWindow = new Ext.Window({
						title : '����ѡ���(��������Դ)', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 300, // ���ڿ��
						height : 250, // ���ڸ߶�
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
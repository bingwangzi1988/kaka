/**
 * ������ͬ���ύ
 * 
 * @author XiongChun
 * @since 2011-03-22
 */
Ext
		.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
				id : 'firstForm',
				name : 'firstForm',
				labelWidth : 50, // ��ǩ���
				// frame : true, // �Ƿ���Ⱦ����屳��ɫ
				defaultType : 'textfield', // ��Ԫ��Ĭ������
				labelAlign : 'right', // ��ǩ���뷽ʽ
				bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
				items : [ {
					fieldLabel : '�ı�1', // ��ǩ
					name : 'text1', // name:��̨���ݴ�name����ȡֵ
					value : '���ӵ�',
					allowBlank : false,
					anchor : '100%' // ��Ȱٷֱ�
				}, {
					fieldLabel : '�ı�2', // ��ǩ
					name : 'text2', // name:��̨���ݴ�name����ȡֵ
					value : '�ܴ�',
					anchor : '100%' // ��Ȱٷֱ�
				}, {
					fieldLabel : '�ı�3', // ��ǩ
					name : 'text3', // name:��̨���ݴ�name����ȡֵ
					value : '�����',
					anchor : '100%' // ��Ȱٷֱ�
				} ]
			});

			var firstWindow = new Ext.Window({
				title : '��ͬ���ύ(�ύ��ɺ����ҳ����ת)', // ���ڱ���
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
				items : [ firstForm ], // Ƕ��ı����
				buttons : [ { // ���ڵײ���ť����
					text : 'ͬ���ύ,�ύ����ת', // ��ť�ı�
					iconCls : 'acceptIcon', // ��ťͼ��
					handler : function() { // ��ť��Ӧ����
						submitTheForm();
					}
				} ]
			});
			firstWindow.show(); // ��ʾ����

			/**
			 * ��ͬ���ύ
			 */
			function submitTheForm() {
				if (!firstForm.form.isValid()) {
					return;
				}
				firstForm.getForm().getEl().dom.action = 'formDemo.ered?reqCode=formSynForwardInit';
				firstForm.getForm().getEl().dom.submit();
			}

		});
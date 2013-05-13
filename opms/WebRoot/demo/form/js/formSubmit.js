/**
 * ����������(�ύ���������)
 * 
 * @author XiongChun
 * @since 2010-08-20
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
		items : [ {
			fieldLabel : '�ı�1', // ��ǩ
			name : 'text1', // name:��̨���ݴ�name����ȡֵ
			allowBlank : false,
			anchor : '100%' // ��Ȱٷֱ�
		}, {
			fieldLabel : '�ı�2', // ��ǩ
			id : 'text2',
			name : 'text2', // name:��̨���ݴ�name����ȡֵ
			value : '�ܴ�',
			anchor : '100%' // ��Ȱٷֱ�
		}, {
			fieldLabel : '�ı�3', // ��ǩ
			name : 'text3', // name:��̨���ݴ�name����ȡֵ
			anchor : '100%' // ��Ȱٷֱ�
		} ]
	});

	var firstWindow = new Ext.Window({
		title : '������(�ύ���������)', // ���ڱ���
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
			text : '�ύ1(���Դ�Ajax)', // ��ť�ı�
			iconCls : 'acceptIcon', // ��ťͼ��
			handler : function() { // ��ť��Ӧ����
				submitTheForm();
			}
		}, { // ���ڵײ���ť����
			text : '�ύ2(Extԭ��Ajax)', // ��ť�ı�
			iconCls : 'acceptIcon', // ��ťͼ��
			handler : function() { // ��ť��Ӧ����
				submitTheFormBasedAjaxRequest();
			}
		}, { // ���ڵײ���ť����
			text : '��ȡ', // ��ť�ı�
			iconCls : 'tbar_synchronizeIcon', // ��ťͼ��
			handler : function() { // ��ť��Ӧ����
				loadCallBack();
			}
		} ]
	});
	firstWindow.show(); // ��ʾ����

	/**
	 * ���ύ(���Դ�Ajax�ύ)
	 */
	function submitTheForm() {
		if (!firstForm.form.isValid())
			return;
		firstForm.form.submit({
			url : 'formDemo.ered?reqCode=saveTheSubmitInfo',
			waitTitle : '��ʾ',
			method : 'POST',
			waitMsg : '���ڴ�������,���Ժ�...',
			success : function(form, action) {
				Ext.MessageBox.alert('��ʾ', action.result.msg);
			},
			failure : function(form, action) {
				Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
			},
			params : {
				text4 : '�ı�4���Ӳ���',
				postType: 1
			}
		});
	}

	/**
	 * ���ύ(Ext.Ajax�ύ)
	 */
	function submitTheFormBasedAjaxRequest() {
		if (!firstForm.form.isValid())
			return;
		var params = firstForm.getForm().getValues();
		params.text4 = '�ı�4���Ӳ���';
		Ext.Ajax.request({
			url : 'formDemo.ered?reqCode=saveTheSubmitInfoBasedAjaxRequest',
			success : function(response, opts) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert('��ʾ', resultArray.msg);
			},
			failure : function(response, opts) {
					Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
			},
			params : params
		});
	}

	// ���������ݵĻص�����
	function loadCallBack() {
		firstForm.form.load({
			waitMsg : '���ڶ�ȡ��Ϣ',// ��ʾ��Ϣ
			waitTitle : '��ʾ',// ����
			url : 'formDemo.ered?reqCode=loadCallBack',// �����url��ַ
			// method : 'GET',// ����ʽ
			success : function(form, action) {// ���سɹ��Ĵ�����
				Ext.Msg.alert('��ʾ', '���ݶ�ȡ�ɹ�');
			},
			failure : function(form, action) {// ����ʧ�ܵĴ�����
				Ext.Msg.alert('��ʾ', '���ݶ�ȡʧ��:' + action.failureType);
			}
		});
	}

});
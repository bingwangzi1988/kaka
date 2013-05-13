/**
 * 
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
	
	var addUserFormPanel = new Ext.form.FormPanel({
		id : 'addUserFormPanel',
		name : 'addUserFormPanel',
		defaultType : 'textfield',
		labelAlign : 'right',
		labelWidth : 65,
		frame : false,
		bodyStyle : 'padding:5 5 0 0',
		items : [{
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 160, // ��ǩ���
						defaultType : 'textfield',
						border : false,
						items : [{
							fieldLabel : '����',
							name : 'proname',
							id : 'proname',
							allowBlank : false,
							anchor : '99%'
						}, comboxWithTree, sexCombo, {
							fieldLabel : '���֤����',
							name : 'cardno',
							id : 'cardno',
							allowBlank : false,
							anchor : '99%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 65, // ��ǩ���
						defaultType : 'textfield',
						border : false,
						items : [usertypeCombo, lockedCombo,{
							fieldLabel : '����',
							name : 'password',
							id : 'password',
							inputType : 'password',
							allowBlank : false,
							anchor : '99%'
						}, {
							fieldLabel : 'ȷ������',
							name : 'password1',
							id : 'password1',
							inputType : 'password',
							allowBlank : false,
							anchor : '99%'
						}]
					}]
		}, {
			fieldLabel : '��ע',
			labelWidth : 40,
			name : 'remark',
			xtype : 'textarea',
			anchor : '100%'
		}, {
			id : 'windowmode',
			name : 'windowmode',
			hidden : true
		}, {
			id : 'deptid',
			name : 'deptid',
			hidden : true
		}, {
			id : 'deptid_old',
			name : 'deptid_old',
			hidden : true
		}, {
			id : 'userid',
			name : 'userid',
			hidden : true
		}, {
			id : 'updatemode',
			name : 'updatemode',
			hidden : true
		}]
	});

	var addUserWindow = new Ext.Window(
			{
				layout : 'fit',// ���ô��ڲ���ģʽ
				width : 800,
				height : 610,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : true,
				title : '<span style="font-weight:normal">������Ա</span>',
				// iconCls : 'page_addIcon',
				collapsible : true,
				titleCollapse : true,
				maximizable : false, // �����Ƿ�������
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				pageY : 20,
				pageX : document.body.clientWidth / 2 - 820 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [ addUserFormPanel ],
				buttons : [
						{
							text : '����',
							iconCls : 'acceptIcon',
							handler : function() {
								if (runMode == '0') {
									Ext.Msg
											.alert('��ʾ',
													'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
									return;
								}
								var mode = Ext.getCmp('windowmode')
										.getValue();
								if (mode == 'add')
									saveUserItem();
								else
									updateUserItem();
							}
						}, {
							text : '����',
							id : 'btnReset',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
								clearForm(addUserFormPanel.getForm());
							}
						}, {
							text : '�ر�',
							iconCls : 'deleteIcon',
							handler : function() {
								addUserWindow.hide();
							}
						} ]
			});
	
	addUserWindow.show();

});
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
						labelWidth : 160, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
							fieldLabel : '姓名',
							name : 'proname',
							id : 'proname',
							allowBlank : false,
							anchor : '99%'
						}, comboxWithTree, sexCombo, {
							fieldLabel : '身份证号码',
							name : 'cardno',
							id : 'cardno',
							allowBlank : false,
							anchor : '99%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 65, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [usertypeCombo, lockedCombo,{
							fieldLabel : '密码',
							name : 'password',
							id : 'password',
							inputType : 'password',
							allowBlank : false,
							anchor : '99%'
						}, {
							fieldLabel : '确认密码',
							name : 'password1',
							id : 'password1',
							inputType : 'password',
							allowBlank : false,
							anchor : '99%'
						}]
					}]
		}, {
			fieldLabel : '备注',
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
				layout : 'fit',// 设置窗口布局模式
				width : 800,
				height : 610,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : true,
				title : '<span style="font-weight:normal">新增人员</span>',
				// iconCls : 'page_addIcon',
				collapsible : true,
				titleCollapse : true,
				maximizable : false, // 设置是否可以最大化
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
							text : '保存',
							iconCls : 'acceptIcon',
							handler : function() {
								if (runMode == '0') {
									Ext.Msg
											.alert('提示',
													'系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
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
							text : '重置',
							id : 'btnReset',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
								clearForm(addUserFormPanel.getForm());
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addUserWindow.hide();
							}
						} ]
			});
	
	addUserWindow.show();

});
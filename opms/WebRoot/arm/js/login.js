/**
 * ��½ҳ��
 * 
 * @author XiongChun
 * @since 2010-01-13
 */
Ext
		.onReady(function() {
			var panel = new Ext.Panel(
					{
						el : 'hello-tabs',
						autoTabs : true,
						deferredRender : false,
						border : false,
						items : {
							xtype : 'tabpanel',
							activeTab : 0,
							height : 180,
							border : false,
							items : [
									{
										title : "�����֤",
										xtype : 'form',
										id : 'loginForm',
										defaults : {
											width : 260
										},
										bodyStyle : 'padding:20 0 0 50',
										defaultType : 'textfield',
										labelWidth : 40,
										labelSeparator : '��',
										items : [
												{
													fieldLabel : '��&nbsp;��',
													name : 'account',
													id : 'account',
													cls : 'user',
													blankText : '�ʺŲ���Ϊ��,������!',
													maxLength : 30,
													maxLengthText : '�˺ŵ���󳤶�Ϊ30���ַ�',
													allowBlank : false,
													listeners : {
														specialkey : function(
																field, e) {
															if (e.getKey() == Ext.EventObject.ENTER) {
																Ext
																		.getCmp(
																				'password')
																		.focus();
															}
														}
													}
												},
												{
													fieldLabel : '��&nbsp;��',
													name : 'password',
													id : 'password',
													cls : 'key',
													inputType : 'password',
													blankText : '���벻��Ϊ��,������!',
													maxLength : 200,
													maxLengthText : '�������󳤶�Ϊ200���ַ�',
													allowBlank : false,
													listeners : {
														specialkey : function(
																field, e) {
															if (e.getKey() == Ext.EventObject.ENTER) {
																login();
															}
														}
													}
												},
												{
													id : 'id_reg_panel',
													xtype : 'panel',
													border : false,
													hidden : true,
													html : '<br><div id="id_reg_div" style="font-size:12px;color:blue; margin-left:105px">[��ż,1o����ע�����ʻ�]</div>'
												} ]
									}
//									, {
//										title : '��Ϣ����',
//										contentEl : 'infoDiv',
//										defaults : {
//											width : 230
//										}
//									}, {
//										title : '����',
//										contentEl : 'aboutDiv',
//										defaults : {
//											width : 230
//										}
//									} 
									]
						}
					});

			// �����ť�����Ĳ˵�
			var mainMenu = new Ext.menu.Menu({
				id : 'mainMenu',
				items : [
						{
							text : '�������',
							iconCls : 'status_awayIcon',
							handler : function() {
								clearCookie('eredg4.login.account');
								var account = Ext.getCmp('loginForm').findById(
										'account');
								Ext.getCmp('loginForm').form.reset();
								account.setValue('');
								account.focus();
							}
						}, {
							text : '�л���ȫ��ģʽ',
							iconCls : 'imageIcon',
							handler : function() {
								window.location.href = './fullScreen.jsp';
							}
						} , {
							text : '�л�������ģʽ',
							iconCls : 'imageIcon',
							handler : function() {
								window.location.href = './normalScreen.jsp';
							}
						}]
			});

			var win = new Ext.Window(
					{
						title : LOGIN_WINDOW_TITLE,
						renderTo : Ext.getBody(),
						layout : 'fit',
						width : 460,
						height : 300,
						closeAction : 'hide',
						plain : true,
						modal : true,
						collapsible : true,
						titleCollapse : true,
						maximizable : false,
						draggable : false,
						closable : false,
						resizable : false,
						animateTarget : document.body,
						items : panel,
						buttons : [
								{
									text : '&nbsp;��¼',
									iconCls : 'acceptIcon',
									handler : function() {
//										if (Ext.isIE6) {
//											top.Ext.MessageBox
//													.alert('��ܰ��ʾ',
//															'G4��ʾϵͳ�ܾ�IE6�ͻ��˵�¼<br>����ǿ�ҽ�������������IE�����л���<b>FireFox</b>��<b>GoogleChrome</b>�����');
//											return;
//										}
									
										login();
									}
								}, {
									text : '&nbsp;ѡ��',
									iconCls : 'tbar_synchronizeIcon',
									menu : mainMenu
								} ]
					});

			win.show();

			win.on('show', function() {
				setTimeout(
						function() {
							var account = Ext.getCmp('loginForm').findById(
									'account');
							var password = Ext.getCmp('loginForm').findById(
									'password');
							var c_account = getCookie('eredg4.login.account');
							account.setValue(c_account);
							if (Ext.isEmpty(c_account)) {
								account.focus();
							} else {
								password.focus();
							}
						}, 200);
			}, this);

			Ext.get('id_reg_div').on('click', function() {
				addUserFormPanel.getForm().reset();
				addUserWindow.show();
			});

			var addUserFormPanel = new Ext.form.FormPanel(
					{
						id : 'addUserFormPanel',
						name : 'addUserFormPanel',
						defaultType : 'textfield',
						labelAlign : 'right',
						labelWidth : 65,
						bodyStyle : 'padding:5 5 5 5',
						frame : false,
						items : [
								{
									fieldLabel : '��¼�ʻ�',
									name : 'account',
									allowBlank : false,
									emptyText : '��ʹ��Email��ΪG4�ʻ�',
									regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
									regexText : '���Ե��������ַ��ΪG4�ʻ�',
									maxLength : 30,
									anchor : '99%'
								}, {
									fieldLabel : '����/�ǳ�',
									name : 'username',
									allowBlank : false,
									anchor : '99%'
								}, {
									fieldLabel : '����',
									name : 'password',
									inputType : 'password',
									allowBlank : false,
									anchor : '99%'
								}, {
									fieldLabel : 'ȷ������',
									name : 'password1',
									inputType : 'password',
									allowBlank : false,
									anchor : '99%'
								} ]
					});

			var addUserWindow = new Ext.Window({
				layout : 'fit',
				width : 280,
				height : 180,
				resizable : false,
				draggable : false,
				closeAction : 'hide',
				title : '<span style="font-weight:normal">ע�����ʻ�</span>',
				iconCls : 'group_addIcon',
				modal : true,
				collapsible : false,
				maximizable : false,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [ addUserFormPanel ],
				buttons : [ {
					text : '����',
					iconCls : 'acceptIcon',
					handler : function() {
						regAccount();
					}
				}, {
					text : '����',
					id : 'btnReset',
					iconCls : 'tbar_synchronizeIcon',
					handler : function() {
						clearForm(addUserFormPanel.getForm());
					}
				} ]
			});

			/**
			 * �ύ��½����
			 */
			function login() {
				if (Ext.getCmp('loginForm').form.isValid()) {
					getPwd();
				}
			}
			
			function submitLoginInfo()
			{
				Ext.getCmp('loginForm').form
				.submit({
					url : 'login.ered?reqCode=login',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '������֤�������,���Ժ�.....',
					success : function(form, action) {
						var account = Ext.getCmp('loginForm')
								.findById('account');
						setCookie("eredg4.login.account", account
								.getValue());
						
						var chgPwdFlag=action.result.chgPwdFlag;
						if(chgPwdFlag=='1')
						{
							window.location.href = 'chgpwd.jsp';
						}else if(chgPwdFlag=='2'){
							Ext.Msg.alert('��ʾ', "��������ѹ���,�뼰ʱ�޸�", function() {
								window.location.href = 'chgpwd.jsp';
							});
						}
						else if(chgPwdFlag=='3'){
							var days=action.result.days;
							Ext.Msg.confirm('��ʾ', '�������'+days+'������,�Ƿ������޸�?', function(button, text) {
								if (button == 'yes') {
									window.location.href = 'chgpwd.jsp';
								}else
								{
									window.location.href = 'main.jsp';
								}
							});
						}
						else{
							window.location.href = 'main.jsp';
						}
					},
					failure : function(form, action) {
						var errmsg = action.result.msg;
						var errtype = action.result.errorType;
						Ext.Msg.alert('��ʾ', errmsg, function() {
							var account = Ext.getCmp('loginForm')
									.findById('account');
							var password = Ext.getCmp('loginForm')
									.findById('password');
							if (errtype == '1') {
								Ext.getCmp('loginForm').form
										.reset();
								account.focus();
								account.validate();
							} else if (errtype == '2') {

								password.focus();
								password.setValue('');
							} else if (errtype == '3') {
								account.focus();
							}
						});
					}
				});
			}

			function getPwd()
			{
				var pwd=Ext.getCmp('loginForm').findById('password');
				var password = pwd.getValue();
				Ext.Ajax.request({
			 		url:'login.ered?reqCode=getPwdCode',
			 		params:{password :password},
			 		callback:function(opts,success,response){
				 		   var datas=response.responseText;
				 		   var objs=Ext.decode(datas);
				 		   var newpwd=objs.password;
				 		   pwd.setValue(newpwd);
				 		  submitLoginInfo();
			 		}
					});
			}
			/**
			 * ע�����ʻ�
			 */
			function regAccount() {
				if (!addUserFormPanel.form.isValid()) {
					return;
				}
				var values = addUserFormPanel.getForm().getValues();
				if (values.password1 != values.password) {
					Ext.Msg.alert('��ʾ', '������������벻ƥ��,����������!');
					Ext.getCmp('password').setValue('');
					Ext.getCmp('password1').setValue('');
					return;
				}
				addUserFormPanel.form.submit({
					url : 'login.ered?reqCode=regAccount',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addUserWindow.hide();
						Ext.MessageBox.alert('��ʾ', '�ʻ�ע��ɹ�,���[��¼]��ť����ϵͳ!');
						var password = Ext.getCmp('loginForm').findById(
								'password');
						var account = Ext.getCmp('loginForm').findById(
								'account');
						password.setValue(values.password);
						account.setValue(values.account);
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('��ʾ', action.result.msg);
					}
				});
			}

			// ��ʾģʽ
			if (runMode == '0') {
				// Ext.getCmp('account').setValue('developer');
				// Ext.getCmp('password').setValue('111111');
				Ext.getCmp('id_reg_panel').show();
			}

//			setTimeout(
//					function() {
//						if (Ext.isIE) {
//							top.Ext.MessageBox
//									.alert(
//											'��ܰ��ʾ',
//											'ϵͳ��⵽������ʹ�û���MSIE�ں˵������<br>����ǿ�ҽ����������л���<b>FireFox</b>����<b>GoogleChrome</b>����������һ��ĸо�!')
//						}
//					}, 500);
		});
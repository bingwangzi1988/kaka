/**
 * ��ҳ����JS
 * 
 * @author XiongChun
 * @since 2010-03-13
 */
Ext
		.onReady(function() {
			var themeButton = new Ext.Button({
				text : '����',
				iconCls : 'themeIcon',
				iconAlign : 'left',
				scale : 'medium',
				width : 50,
				tooltip : '<span style="font-size:12px">�л�ϵͳ������ʽ</span>',
				pressed : true,
				arrowAlign : 'right',
				renderTo : 'themeDiv',
				handler : function() {
					themeWindowInit();
				}
			});

			var mainMenu = new Ext.menu.Menu({
				id : 'mainMenu',
				items : [ {
					text : '�޸ĸ�����Ϣ',
					iconCls : 'userIcon',
					handler : function() {
						updateUserInit();
					}
				} ]
			});

			var configButton = new Ext.Button({
				text : '��ѡ��',
				iconCls : 'config2Icon',
				iconAlign : 'left',
				scale : 'medium',
				width : 50,
				tooltip : '<span style="font-size:12px">��ѡ������</span>',
				pressed : true,
				renderTo : 'configDiv',
				menu : mainMenu
			});

			var closeButton = new Ext.Button({
				iconCls : 'cancel_48Icon',
				iconAlign : 'left',
				scale : 'medium',
				width : 30,
				tooltip : '<span style="font-size:12px">�л��û�,��ȫ�˳�ϵͳ</span>',
				pressed : true,
				arrowAlign : 'right',
				renderTo : 'closeDiv',
				handler : function() {
					window.location.href = './login.ered?reqCode=logout';
				}
			});

			var root = new Ext.tree.TreeNode({
				text : '���ڵ�',
				id : '00'
			});
			var node01 = new Ext.tree.TreeNode({
				text : '��ɫ����',
				theme : 'default',
				id : '01'
			});
			var node02 = new Ext.tree.TreeNode({
				text : '�ۺ�֮��',
				theme : 'lightRed',
				id : '02'
			});
			var node03 = new Ext.tree.TreeNode({
				text : '��̻Ի�',
				theme : 'lightYellow',
				id : '03'
			});
			var node04 = new Ext.tree.TreeNode({
				text : '����սʿ',
				theme : 'gray',
				id : '04'
			});
			var node05 = new Ext.tree.TreeNode({
				text : '��ˮ��ɽ',
				theme : 'lightGreen',
				id : '05'
			});
			var node06 = new Ext.tree.TreeNode({
				text : '��ɫ����',
				theme : 'purple2',
				id : '06'
			});
			root.appendChild(node01);
			root.appendChild(node02);
			root.appendChild(node03);
			root.appendChild(node04);
			root.appendChild(node05);
			root.appendChild(node06);
			var themeTree = new Ext.tree.TreePanel({
				autoHeight : false,
				autoWidth : false,
				autoScroll : false,
				animate : false,
				rootVisible : false,
				border : false,
				containerScroll : true,
				applyTo : 'themeTreeDiv',
				root : root
			});
			themeTree.expandAll();
			themeTree.on('click', function(node) {
				var theme = node.attributes.theme;
				var o = document.getElementById('previewDiv');
				o.innerHTML = '<img src="./resource/image/theme/' + theme
						+ '.jpg" />';
			});

			var previewPanel = new Ext.Panel({
				region : 'center',
				title : '<span style="font-weight:normal">����Ԥ��</span>',
				margins : '3 3 3 0',
				activeTab : 0,
				defaults : {
					autoScroll : true
				},
				contentEl : 'previewDiv'
			});

			var themenav = new Ext.Panel(
					{
						title : '<span style="font-weight:normal">�����б�</span>',
						region : 'west',
						split : true,
						width : 120,
						minSize : 120,
						maxSize : 150,
						collapsible : true,
						margins : '3 0 3 3',
						contentEl : 'themeTreeDiv',
						bbar : [
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
										var o = themeTree.getSelectionModel()
												.getSelectedNode();
										saveUserTheme(o);
									}
								}, '->', {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										themeWindow.hide();
									}
								} ]
					});

			var themeWindow = new Ext.Window({
				title : '<span style="font-weight:normal">��������</span>',
				closable : true,
				width : 500,
				height : 350,
				closeAction : 'hide',
				iconCls : 'theme2Icon',
				collapsible : true,
				titleCollapse : true,
				border : true,
				maximizable : false,
				resizable : false,
				modal : true,
				// border:false,
				plain : true,
				layout : 'border',
				items : [ themenav, previewPanel ]
			});

			/**
			 * ���ⴰ�ڳ�ʼ��
			 */
			function themeWindowInit() {
				for (i = 0; i < root.childNodes.length; i++) {
					var child = root.childNodes[i];
					if (default_theme == child.attributes.theme) {
						child.select();
					}
				}
				var o = document.getElementById('previewDiv');
				o.innerHTML = '<img src="./resource/image/theme/'
						+ default_theme + '.jpg" />';
				themeWindow.show();

			}

			/**
			 * �����û��Զ�������
			 */
			function saveUserTheme(o) {
				showWaitMsg();
				Ext.Ajax
						.request({
							url : './index.ered?reqCode=saveUserTheme',
							success : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								Ext.MessageBox
										.confirm(
												'��ȷ��',
												'��ѡ���['
														+ o.text
														+ ']���Ᵽ��ɹ�,����Ӧ�ø�������?<br>��ʾ��ҳ��ᱻˢ��,����ȷ���Ƿ�����δ�����ҵ������,���ⶪʧ!',
												function(btn, text) {
													if (btn == 'yes') {
														showWaitMsg('����Ϊ��Ӧ������...');
														location.reload();
													} else {
														Ext.Msg
																.alert(
																		'��ʾ',
																		'�����κ�ʱ��[F5]��ˢ��ҳ��������µ�¼ϵͳ������['
																				+ o.text
																				+ ']����!',
																		function() {
																			themeWindow
																					.hide();
																		});

													}
												});
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								Ext.Msg.alert('��ʾ', '���ݱ���ʧ��');
							},
							params : {
								theme : o.attributes.theme
							}
						});
			}

			var sexStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 ��' ], [ '2', '2 Ů' ], [ '0', '0 δ֪' ] ]
			});
			var sexCombo = new Ext.form.ComboBox({
				name : 'sex',
				hiddenName : 'sex',
				store : sexStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				value : '0',
				fieldLabel : '�Ա�',
				emptyText : '��ѡ��...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				anchor : "99%"
			});
			var userFormPanel = new Ext.form.FormPanel({
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 65,
				frame : false,
				bodyStyle : 'padding:5 5 0',
				items : [ {
					fieldLabel : '��¼�ʻ�',
					name : 'account',
					id : 'account',
					allowBlank : false,
					readOnly : true,
					fieldClass : 'x-custom-field-disabled',
					anchor : '99%'
				}, {
					fieldLabel : '����',
					name : 'username',
					id : 'username',
					allowBlank : false,
					anchor : '99%'
				}, sexCombo, {
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
				}, {
					id : 'userid',
					name : 'userid',
					hidden : true
				} ]
			});

			var userWindow = new Ext.Window({
				layout : 'fit',
				width : 300,
				height : 205,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : true,
				title : '<span style="font-weight:normal">�޸��ʻ���Ϣ</span>',
				iconCls : 'configIcon',
				collapsible : true,
				titleCollapse : true,
				maximizable : false,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [ userFormPanel ],
				buttons : [ {
					text : '����',
					iconCls : 'acceptIcon',
					handler : function() {
						updateUser();
					}
				}, {
					text : '�ر�',
					iconCls : 'deleteIcon',
					handler : function() {
						userWindow.hide();
					}
				} ]
			});

			/**
			 * ���ص�ǰ��¼�û���Ϣ
			 */
			function updateUserInit() {
				userFormPanel.form.reset();
				userWindow.show();
				userWindow.on('show', function() {
					setTimeout(function() {
						userFormPanel.form.load({
							waitTitle : '��ʾ',
							waitMsg : '���ڶ�ȡ�û���Ϣ,���Ժ�...',
							url : 'index.ered?reqCode=loadUserInfo',
							success : function(form, action) {
							},
							failure : function(form, action) {
								Ext.Msg.alert('��ʾ', '���ݶ�ȡʧ��:'
										+ action.failureType);
							}
						});
					}, 5);
				});
			}

			/**
			 * �޸��û���Ϣ
			 */
			function updateUser() {
				if (!userFormPanel.form.isValid()) {
					return;
				}
				password1 = Ext.getCmp('password1').getValue();
				password = Ext.getCmp('password').getValue();
				if (password1 != password) {
					Ext.Msg.alert('��ʾ', '������������벻ƥ��,����������!');
					Ext.getCmp('password').setValue('');
					Ext.getCmp('password1').setValue('');
					return;
				}
				userFormPanel.form.submit({
					url : 'index.ered?reqCode=updateUserInfo',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						Ext.MessageBox.alert('��ʾ', '��¼�ʻ���Ϣ�޸ĳɹ�');
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '��Ա���ݱ���ʧ��');
					}
				});
			}
		});

/**
 * ��ʾϵͳʱ��
 */
function showTime() {
	var date = new Date();
	var h = date.getHours();
	h = h < 10 ? '0' + h : h;
	var m = date.getMinutes();
	m = m < 10 ? '0' + m : m;
	var s = date.getSeconds();
	s = s < 10 ? '0' + s : s;
	document.getElementById('rTime').innerHTML = h + ":" + m + ":" + s;
}

window.onload = function() {
	setInterval("showTime()", 1000);
}
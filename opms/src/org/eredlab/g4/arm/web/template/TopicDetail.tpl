<style type="text/css">
.myDiv {
	font-family: "����";
	font-size: 13px;
}
</style>

<div id="mainDiv_${topicVo.topicid}"></div>
<script type="text/javascript">

Ext.onReady(function() {

  var tb = new Ext.Toolbar();
  tb.add({
			text : '�޸�',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editTopicWindow.show();
				editTopicTypeCombo.setValue('${topicVo.topictype}');
				Ext.getCmp('contentEdit').setValue('${topicVo.content2}');
				titleTextField.setValue('${topicVo.title}');
			}
		}, {
			text : 'ɾ��',
			iconCls : 'deleteIcon',
			id:'id_btn_deltopic',
			handler : function() {
				deleteTopic();
			}
		});
		
			var editTopicTypeStore = new Ext.data.SimpleStore({
						fields : ['text', 'value'],
						data : [['����|Bug�ύ', '2'], ['��ѯ|��������', '3'], ['G4֪ʶ��', '4'], ['G4��ˮ��԰', '5']]
					});

			var editTopicTypeCombo = new Ext.form.ComboBox({
						hiddenName : 'topictype',
						store : editTopicTypeStore,
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text',
						fieldLabel : '����',
						emptyText : '��ѡ���������',
						forceSelection : false,
						allowBlank : false,
						editable : false,
						typeAhead : true,
						anchor : '35%'
					});
			 var titleTextField = new Ext.form.TextField({
				fieldLabel : '����',
				emptyText : '����������������(����ȡ��һ��Ŷ)',
				maxLength : 50,
				name : 'title',
				allowBlank : false,
				anchor : '100%'
			});

			var editTopicPanel = new Ext.form.FormPanel({
						bodyStyle : 'padding:5 5 5 5',
						labelAlign : 'right', // ��ǩ���뷽ʽ
						frame : true,
						labelWidth : 35,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .99,
												layout : 'form',
												defaultType : 'textfield',
												border : false,
												items : [editTopicTypeCombo, titleTextField]
											}]
								}, {
									layout : 'fit',
									border : false,
									items : [{
												id : 'contentEdit',
												name : 'contentEdit',
												xtype : 'htmleditor',
												allowBlank : false,
												enableSourceEdit : false,
												anchor : '99%'
											}]

								}]
					});

			var editTopicWindow = new Ext.Window({
						title : '<span style="font-weight:normal">�޸�������</span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						iconCls : 'message_editIcon',
						modal : true,
						width : 650, // ���ڿ��
						height : 380, // ���ڸ߶�
						closable : true, // �Ƿ�ɹر�
						collapsible : false, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						closeAction : 'hide', // �رղ���
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 50, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 650 / 2, // ҳ�涨λX����
						items : [editTopicPanel], // Ƕ��ı����
						buttons : [{ // ���ڵײ���ť����
							text : '����', // ��ť�ı�
							iconCls : 'acceptIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								saveEdit();
							}
						}, {	// ���ڵײ���ť����
									text : '�ر�', // ��ť�ı�
									iconCls : 'deleteIcon', // ��ťͼ��
									handler : function() { // ��ť��Ӧ����
										editTopicWindow.hide();
									}
								}]
					});
			
				var editReplyPanel = new Ext.form.FormPanel({
						bodyStyle : 'padding:5 5 5 5',
						labelAlign : 'right', // ��ǩ���뷽ʽ
						frame : true,
						labelWidth : 35,
						items : [{
									layout : 'fit',
									border : false,
									items : [{
												id : '_id_reply_content',
												name : 'contentEdit',
												xtype : 'htmleditor',
												allowBlank : false,
												enableSourceEdit : false,
												anchor : '99%'
											},{
												id : '_id_edit_replyid',
												name : 'replyid',
												xtype : 'textfield',
												hidden: true,
												anchor : '99%'
											}]
								}]
					});
				
			var editReplyWindow = new Ext.Window({
						title : '<span style="font-weight:normal">�޸Ļ���</span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						iconCls : 'message_editIcon',
						modal : true,
						width : 650, // ���ڿ��
						height : 380, // ���ڸ߶�
						closable : true, // �Ƿ�ɹر�
						collapsible : false, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						closeAction : 'hide', // �رղ���
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 50, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 650 / 2, // ҳ�涨λX����
						items : [editReplyPanel], // Ƕ��ı����
						buttons : [{ // ���ڵײ���ť����
							text : '����', // ��ť�ı�
							iconCls : 'acceptIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								saveEditReply();
							}
						}, {	// ���ڵײ���ť����
									text : '�ر�', // ��ť�ı�
									iconCls : 'deleteIcon', // ��ťͼ��
									handler : function() { // ��ť��Ӧ����
										editReplyWindow.hide();
									}
								}]
					});					

		
 var panel_top_${topicVo.topicid} = new Ext.Panel({
    id:'panel_top_${topicVo.topicid}',
	border : false,
	frame : false,
	renderTo : 'mainDiv_${topicVo.topicid}',
	items : [{
	    id:'id_panel_main_${topicVo.topicid}',
		title : '<span style="font-weight:normal"><span style="text-underline-position: above; text-decoration: underline; ">${topicVo.title}</span>&nbsp;&nbsp;����:${topicVo.username}&nbsp;&nbsp;����ʱ��:${topicVo.addtime}</span>',
		xtype : 'panel',
		tbar : tb,
		collapsible : true,
		border : false,
		titleCollapse : true,
		contentEl : 'id_div_main_${topicVo.topicid}'
    }
    #foreach($reply in $replyList)
    ,
    {
        id:'id_panel_reply_${reply.replyid}',
		title : '<span style="font-weight:normal">������:${reply.username}&nbsp;&nbsp;����ʱ��:${reply.replytime}</span>',
		xtype : 'panel',
		collapsible : true,
		border : false,
		tbar : [{
			text : '�޸�',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editReplyWindow.show();
				Ext.getCmp('_id_edit_replyid').setValue('${reply.replyid}');
				Ext.getCmp('_id_reply_content').setValue('${reply.replycontent2}');
			}
		}, {
			text : 'ɾ��',
			iconCls : 'deleteIcon',
			id:'id_btn_delreply_${reply.replyid}',
			handler : function() {
				deleteReply('${reply.replyid}');
			}
		}],
		titleCollapse : true,
		contentEl : 'id_div_reply_${reply.replyid}'
    }
    #end
    
    ]
});
#if(${flag1} == '2')
  tb.hide();
#else if(${flag1} == '1')
  Ext.getCmp('id_btn_deltopic').disable();
#end 
#foreach($reply in $replyList)
#if(${flag1} == '2')
  Ext.getCmp('id_panel_reply_${reply.replyid}').getTopToolbar().hide();
#else if(${flag1} == '1')
  Ext.getCmp('id_btn_delreply_${reply.replyid}').disable();
#end 
#end
	function saveEdit() {
		if (!editTopicPanel.getForm().isValid()) {
			return;
		}
		var value = Ext.getCmp('contentEdit').getValue();
		if (value.length < 1) {
			Ext.MessageBox.alert('��ʾ', '�������ݲ���Ϊ��');
			return;
		}
		editTopicPanel.form.submit({
					url : 'projectHome.ered?reqCode=editTopic',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					params:{topicid:'${topicVo.topicid}'},
					success : function(form, action) { // �ص�������2������
						// Ext.MessageBox.alert('��ʾ',
						// action.result.msg);
						editTopicWindow.hide();
						//tabPanel.setActiveTab(0);
						//tabPanel.remove('tab${topicVo.topicid}');
						store.reload();
						tabPanel.getItem('tab${topicVo.topicid}').getUpdater().update({
						  url:'projectHome.ered?reqCode=previewTopicInit',
						  params:{topicid: '${topicVo.topicid}'}
						});
						//alert(tabPanel.getItem('tab${topicVo.topicid}'));
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
					}
				});
	}
	
	function deleteTopic(){
	  		    showWaitMsg();
				Ext.Ajax.request({
							url : 'projectHome.ered?reqCode=deleteTopic',
							success : function(response) {
							    hideWaitMsg();
							    tabPanel.setActiveTab(0);
						        tabPanel.remove('tab${topicVo.topicid}');
								store.reload();
							},
							failure : function(response) {
								Ext.Msg.alert('��ʾ', 'ɾ��ʧ��');
							},
							params : {
								topicid : '${topicVo.topicid}'
							}
						});
	   }
	   
	function saveEditReply() {
		var value = Ext.getCmp('_id_reply_content').getValue();
		if (value.length < 1) {
			Ext.MessageBox.alert('��ʾ', '���ݲ���Ϊ��');
			return;
		}
		editReplyPanel.form.submit({
					url : 'projectHome.ered?reqCode=editReply',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) { // �ص�������2������
						// Ext.MessageBox.alert('��ʾ',
						// action.result.msg);
						editReplyWindow.hide();
						//tabPanel.setActiveTab(0);
						//tabPanel.remove('tab${topicVo.topicid}');
						//store.reload();
						tabPanel.getItem('tab${topicVo.topicid}').getUpdater().update({
						  url:'projectHome.ered?reqCode=previewTopicInit',
						  params:{topicid: '${topicVo.topicid}'}
						});
						//alert(tabPanel.getItem('tab${topicVo.topicid}'));
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
					}
				});
	}
	
	function deleteReply(replyid){
	  		    showWaitMsg();
				Ext.Ajax.request({
							url : 'projectHome.ered?reqCode=deleteReply',
							success : function(response) {
							    hideWaitMsg();
						        //tabPanel.remove('tab${topicVo.topicid}');
						        var panel = Ext.getCmp('panel_top_${topicVo.topicid}');
						        panel.remove('id_panel_reply_' + replyid);
							},
							failure : function(response) {
								Ext.Msg.alert('��ʾ', 'ɾ��ʧ��');
							},
							params : {
								replyid : replyid
							}
						});
	   }
			
});
</script>

<div id="id_div_main_${topicVo.topicid}" class="myDiv" style="margin: 8px;">
 ${topicVo.content}
</div>
#foreach($reply in $replyList)
<div id="id_div_reply_${reply.replyid}" class="myDiv" style="margin: 8px;">${reply.replycontent}</div>
#end

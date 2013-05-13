/**
 * ��������ѡ���(Զ������Դ)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var areaStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'formDemo.ered?reqCode=queryAreaDatas'
								}),
						reader : new Ext.data.JsonReader({}, [{
											name : 'value'
										}, {
											name : 'text'
										}]),
						baseParams : {
							areacode : '53'
						}
					});
			// areaStore.load(); //���mode : 'local',ʱ�����Ҫ�ֶ�load();

			var areaCombo = new Ext.form.ComboBox({
						hiddenName : 'area',
						fieldLabel : '������������',
						emptyText : '��ѡ��...',
						triggerAction : 'all',
						store : areaStore,
						displayField : 'text',
						valueField : 'value',
						loadingText : '���ڼ�������...',
						mode : 'remote', // ���ݻ��Զ���ȡ,�������Ϊlocal�ֵ�����store.load()����ȡ2�Σ�Ҳ���Խ�������Ϊlocal��Ȼ��ͨ��store.load()��������ȡ
						forceSelection : true,
						typeAhead : true,
						resizable : true,
						editable : false,
						anchor : '100%'
					});

			var chinaStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'formDemo.ered?reqCode=queryAreaDatas4Paging'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{
											name : 'value'
										}, {
											name : 'text'
										}]),
						baseParams : {
							areacode : ''
						}
					});

			var chinaCombo = new Ext.form.ComboBox({
						hiddenName : 'area',
						fieldLabel : '�й���������',
						emptyText : '��ѡ��...',
						triggerAction : 'all',
						store : chinaStore,
						displayField : 'text',
						valueField : 'value',
						loadingText : '���ڼ�������...',
						mode : 'remote', // ���ݻ��Զ���ȡ,�������Ϊlocal�ֵ�����store.load()����ȡ2�Σ�Ҳ���Խ�������Ϊlocal��Ȼ��ͨ��store.load()��������ȡ
						forceSelection : true,
						typeAhead : true,
						pageSize : 15,
						minListWidth : 270,
						resizable : true,
						editable : false,
						anchor : '100%'
					});
					
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 80, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [areaCombo, chinaCombo]
					});

			var firstWindow = new Ext.Window({
						title : '����ѡ���(Զ������Դ)', // ���ڱ���
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
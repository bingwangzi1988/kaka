/**
 * ��������ѡ���(N������)
 * 
 * @author XiongChun
 * @since 2010-10-20
 */
Ext.onReady(function() {
			var provinceStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'formDemo.ered?reqCode=queryAreaDatas'
								}),
						reader : new Ext.data.JsonReader({}, [{
											name : 'value'
										}, {
											name : 'text'
										}]),
						baseParams : {
							areacodelength : '2'
						}
					});
			// areaStore.load(); //���mode : 'local',ʱ�����Ҫ�ֶ�load();

			var provinceCombo = new Ext.form.ComboBox({
						hiddenName : 'area',
						fieldLabel : 'ʡ',
						emptyText : '��ѡ��ʡ��...',
						triggerAction : 'all',
						store : provinceStore,
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

			provinceCombo.on('select', function() {
						cityCombo.reset();
						countyCombo.reset();
						var value = provinceCombo.getValue();
						cityStore.load({
									params : {
										areacode : value
									}
								});
					});

			var cityStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'formDemo.ered?reqCode=queryAreaDatas'
								}),
						reader : new Ext.data.JsonReader({}, [{
											name : 'value'
										}, {
											name : 'text'
										}]),
						baseParams : {
							areacodelength : '4'
						}
					});

			var cityCombo = new Ext.form.ComboBox({
						hiddenName : 'area',
						fieldLabel : '�ݡ���',
						emptyText : '��ѡ������...',
						triggerAction : 'all',
						store : cityStore,
						displayField : 'text',
						valueField : 'value',
						loadingText : '���ڼ�������...',
						mode : 'local', // ���ݻ��Զ���ȡ,�������Ϊlocal�ֵ�����store.load()����ȡ2�Σ�Ҳ���Խ�������Ϊlocal��Ȼ��ͨ��store.load()��������ȡ
						forceSelection : true,
						typeAhead : true,
						resizable : true,
						editable : false,
						anchor : '100%'
					});

			cityCombo.on('select', function() {
						countyCombo.reset();
						var value = cityCombo.getValue();
						countyStore.load({
									params : {
										areacode : value
									}
								});
					});

			var countyStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'formDemo.ered?reqCode=queryAreaDatas'
								}),
						reader : new Ext.data.JsonReader({}, [{
											name : 'value'
										}, {
											name : 'text'
										}]),
						baseParams : {
							areacodelength : '6'
						}
					});

			var countyCombo = new Ext.form.ComboBox({
						hiddenName : 'area',
						fieldLabel : '�ء���',
						emptyText : '��ѡ������...',
						triggerAction : 'all',
						store : countyStore,
						displayField : 'text',
						valueField : 'value',
						loadingText : '���ڼ�������...',
						mode : 'local', // ���ݻ��Զ���ȡ,�������Ϊlocal�ֵ�����store.load()����ȡ2�Σ�Ҳ���Խ�������Ϊlocal��Ȼ��ͨ��store.load()��������ȡ
						forceSelection : true,
						typeAhead : true,
						resizable : true,
						editable : false,
						anchor : '100%'
					});

			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 50, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [provinceCombo, cityCombo, countyCombo]
					});

			var firstWindow = new Ext.Window({
						title : '����ѡ���(ʡ������������)', // ���ڱ���
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
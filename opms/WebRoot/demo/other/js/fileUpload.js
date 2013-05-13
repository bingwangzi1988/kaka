/**
 * �ļ��ϴ�
 * 
 * @author XiongChun
 * @since 2010-10-20
 */
Ext.onReady(function() {

	// ��ѡ��
	var sm = new Ext.grid.CheckboxSelectionModel();

	// �����Զ���ǰҳ�к�
	var rownum = new Ext.grid.RowNumberer({
		header : 'NO',
		width : 28
	});

	// ������ģ��
	var cm = new Ext.grid.ColumnModel([ rownum, sm, {
		header : '����', // �б���
		dataIndex : 'download',
		width : 35,
		renderer : downloadColumnRender
	}, {
		header : '�ļ�ID',
		dataIndex : 'fileid',
		sortable : true
	}, {
		header : '����',
		dataIndex : 'title'
	}, {
		header : '��С(byte)',
		dataIndex : 'filesize'
	}, {
		header : '�洢·��',
		dataIndex : 'path',
		width : 280
	}, {
		header : '�ϴ�����',
		dataIndex : 'uploaddate',
		width : 130
	}, {
		header : '����',
		dataIndex : 'remark'
	} ]);

	/**
	 * ���ݴ洢
	 */
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'otherDemo.ered?reqCode=queryFileDatas'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'TOTALCOUNT',
			root : 'ROOT'
		}, [ {
			name : 'fileid'
		}, {
			name : 'title'
		}, {
			name : 'path'
		}, {
			name : 'uploaddate'
		}, {
			name : 'filesize'
		}, {
			name : 'remark'
		} ])
	});

	// ��ҳ����ʱ���ϲ�ѯ����
	store.on('beforeload', function() {
		this.baseParams = {
			title : Ext.getCmp('filetitle').getValue()
		};
	});
	// ÿҳ��ʾ��������ѡ���
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10��/ҳ' ], [ 20, '20��/ҳ' ], [ 50, '50��/ҳ' ],
					[ 100, '100��/ҳ' ], [ 250, '250��/ҳ' ], [ 500, '500��/ҳ' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var number = parseInt(pagesize_combo.getValue());
	// �ı�ÿҳ��ʾ����reload����
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});

	// ��ҳ������
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '��ʾ{0}����{1}��,��{2}��',
		plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
		emptyMsg : "û�з��������ļ�¼",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// ��񹤾���
	var tbar = new Ext.Toolbar({
		items : [ {
			text : '�ϴ�[��ͳWebģʽ]',
			iconCls : 'uploadIcon',
			handler : function() {
				firstWindow.show();
			}
		}, '-', {
			text : '�ϴ�[Flash���ģʽ]',
			iconCls : 'uploadIcon',
			handler : function() {
				win_swfupload.show();
			}
		}, '-', {
			text : 'ɾ���ļ�',
			iconCls : 'deleteIcon',
			handler : function() {
				delFiles();
			}
		}, '->', {
			xtype : 'textfield',
			id : 'filetitle',
			name : 'filetitle',
			emptyText : '�������ļ�����',
			width : 150,
			enableKeyEvents : true,
			// ��Ӧ�س���
			listeners : {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						queryFiles();
					}
				}
			}
		}, {
			text : '��ѯ',
			iconCls : 'page_findIcon',
			handler : function() {
				queryFiles();
			}
		}, {
			text : 'ˢ��',
			iconCls : 'page_refreshIcon',
			handler : function() {
				store.reload();
			}
		} ]
	});

	// ���ʵ��
	var grid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">�ļ��б�</span>',
		renderTo : 'gridDiv', // ��JSPҳ���DIVԪ��ID��Ӧ
		height : 500,
		frame : true,
		autoScroll : true,
		region : 'center', // ��VIEWPORT����ģ�Ͷ�Ӧ���䵱center���򲼾�
		store : store, // ���ݴ洢
		stripeRows : true, // ������
		cm : cm, // ��ģ��
		sm : sm, // ��ѡ��
		tbar : tbar, // ��񹤾���
		bbar : bbar,// ��ҳ������
		viewConfig : {
		// ����������������, �����Զ���չ�Զ�ѹ��, �����������Ƚ��ٵ����
		// forceFit : true
		},
		loadMask : {
			msg : '���ڼ��ر������,���Ե�...'
		}
	});

	grid.on("cellclick", function(pGrid, rowIndex, columnIndex, e) {
		var store = pGrid.getStore();
		var record = store.getAt(rowIndex);
		var fieldName = pGrid.getColumnModel().getDataIndex(columnIndex);
		// columnIndexΪС���������е�����,������0��ʼ
		// ����Ҫ�ǳ�ע��!!!!!
		if (fieldName == 'download' && columnIndex == 2) {
			var fileid = record.get("fileid");
			// ͨ��iFrameʵ����ajax�ļ�����
			// �������Ҫ
			var downloadIframe = document.createElement('iframe');
			downloadIframe.src = 'otherDemo.ered?reqCode=downloadFile&fileid=' + fileid;
			downloadIframe.style.display = "none";
			document.body.appendChild(downloadIframe);
		}
	});

	// ҳ���ʼ�Զ���ѯ����
	store.load({
		params : {
			start : 0,
			limit : bbar.pageSize
		}
	});

	// ����ģ��
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ grid ]
	});

	var firstForm = new Ext.form.FormPanel({
		id : 'firstForm',
		name : 'firstForm',
		fileUpload : true, // һ��Ҫ�����������,�����ȡ�����ϴ������
		labelWidth : 60,
		defaultType : 'textfield',
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		items : [ {
			fieldLabel : 'ѡ���ļ�',
			id : 'file1',
			name : 'file1', // ����Ϊfile1/file2/file3/file4/file5.ĿǰWeb��׼�ϴ�ģʽ֧�����5���ļ��������ϴ�
			xtype : 'fileuploadfield', // �ϴ��ֶ�
			allowBlank : false,
			anchor : '100%'
		}, {
			fieldLabel : '�ļ�����',
			id : 'title',
			name : 'title',
			disabled : true,
			fieldClass : 'x-custom-field-disabled',
			anchor : '100%'
		}, {
			fieldLabel : '�ļ�����',
			id : 'remark',
			name : 'remark',
			xtype : 'textarea',
			anchor : '100%'
		} ]
	});

	var firstWindow = new Ext.Window({
		title : '<span style="font-weight:normal">�ϴ�[��ͳWebģʽ]</span>', // ���ڱ���
		layout : 'fit', // ���ô��ڲ���ģʽ
		width : 500, // ���ڿ��
		height : 200, // ���ڸ߶�
		closable : true, // �Ƿ�ɹر�
		collapsible : true, // �Ƿ������
		maximizable : true, // �����Ƿ�������
		closeAction : 'hide',
		animCollapse : true,
		animateTarget : Ext.getBody(),
		border : false, // �߿�������
		constrain : true, // ���ô����Ƿ�������������
		// pageY : 20, // ҳ�涨λX����
		pageX : document.body.clientWidth / 2 - 500 / 2, // ҳ�涨λY����
		items : [ firstForm ], // Ƕ��ı����
		buttons : [ { // ���ڵײ���ť����
			text : '�ϴ�', // ��ť�ı�
			iconCls : 'uploadIcon', // ��ťͼ��
			handler : function() { // ��ť��Ӧ����
				submitTheForm();
			}
		}, { // ���ڵײ���ť����
			text : '����', // ��ť�ı�
			iconCls : 'tbar_synchronizeIcon', // ��ťͼ��
			handler : function() { // ��ť��Ӧ����
				firstForm.form.reset();
			}
		} ]
	});
	
	//SWFUpload����
	var win_swfupload = new Ext.Window({
		title : '<span style="font-weight:normal">�ϴ�[Flash���ģʽ]</span>',
		width : 500,
		height : 350,
		resizable : false,
		layout : 'fit',
		constrain : true,
		closeAction : 'hide',
		maximizable : true,
		listeners : { 
		    'hide' : function(obj) { 
		      store.reload();
		    } 
		},
		items : [ {
			xtype : 'uploadpanel',
			uploadUrl : webContext + '/demo/otherDemo.ered?reqCode=doUploadBasedFlah',
			filePostName : 'swfUploadFile',
			flashUrl : webContext + '/resource/myux/uploadpanel/swf/swfupload.swf',
			fileSize : '200MB',
			border : false,
			fileTypes : '*.*', // �����������ļ�����:'*.jpg,*.png,*.gif'
			fileTypesDescription : '�����ļ�',
			postParams : {
				postType : 1
			// path : 'upload\\' //��ֻ�Ǹ�����,�ɺ�̨����ȡ;Ҳ����ֱ���ɺ�̨������·��
			}
		} ]
	});

	/**
	 * ���ύ(���Դ�Ajax�ύ)
	 */
	function submitTheForm() {
		if (!firstForm.form.isValid()) {
			return;
		}
		if (runMode == '0') {
			Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
			return;
		}
		firstForm.form.submit({
			url : 'otherDemo.ered?reqCode=doUpload',
			waitTitle : '��ʾ',
			method : 'POST',
			waitMsg : '�����ϴ��ļ�,���Ժ�...',
			timeout: 60000, // 60s
			success : function(form, action) {
				firstForm.form.reset();
				firstWindow.hide();
				store.reload();
				Ext.MessageBox.alert('��ʾ', action.result.msg);

			},
			failure : function(response) {
				Ext.MessageBox.alert('��ʾ', '�ļ��ϴ�ʧ��');
			}
		});
	}

	// ��ѯ�������
	function queryFiles() {
		store.load({
			params : {
				start : 0,
				limit : bbar.pageSize,
				title : Ext.getCmp('filetitle').getValue()
			}
		});
	}

	// ��ȡѡ����
	function getCheckboxValues() {
		// ����һ���м���JS����
		var rows = grid.getSelectionModel().getSelections();
		if (Ext.isEmpty(rows)) {
			Ext.MessageBox.alert('��ʾ', '��û��ѡ���κ�����!');
			return;
		}
		// ��JS�����е��м�������������,�ָ����ַ���
		var strChecked = jsArray2JsString(rows, 'fileid');
		Ext.MessageBox.alert('��ʾ', strChecked);
		// ���ѡ�����ݺ�����Դ����̨��������
	}

	// ����һ������ͼ����
	function downloadColumnRender(value) {
		return "<a href='javascript:void(0);'><img src='" + webContext
				+ "/resource/image/ext/download.png'/></a>";
		;
	}

	/**
	 * ɾ���ļ�
	 */
	function delFiles() {
		if (runMode == '0') {
			Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
			return;
		}
		var rows = grid.getSelectionModel().getSelections();
		if (Ext.isEmpty(rows)) {
			Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ�����ļ�!');
			return;
		}
		var strChecked = jsArray2JsString(rows, 'fileid');
		Ext.Msg.confirm('��ȷ��', '�����Ҫɾ��ѡ�е��ļ���?', function(btn, text) {
			if (btn == 'yes') {
				showWaitMsg();
				Ext.Ajax.request({
					url : 'otherDemo.ered?reqCode=delFiles',
					success : function(response) {
						var resultArray = Ext.util.JSON
								.decode(response.responseText);
						Ext.Msg.alert('��ʾ', resultArray.msg);
						store.reload();
					},
					failure : function(response) {
						Ext.Msg.alert('��ʾ', "�ļ�ɾ��ʧ��");
					},
					params : {
						strChecked : strChecked
					}
				});
			}
		});
	}

});
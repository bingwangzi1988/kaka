Ext.onReady(function() {

	var cmd = '';

	var genCardForm = new Ext.form.FormPanel( {
		region : 'north',
		id : 'genCardForm',
		name : 'genCardForm',
		height : document.body.clientHeight - 5,
		labelWidth : 1,
		frame : true,
		defaultType : 'textfield',
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		items : [ {
			fieldLabel : '',
			name : 'osgi',
			id : 'osgi',
			allowBlank : true,
			xtype : 'textarea',
			height : document.body.clientHeight - 80,
			enableKeyEvents : true,
			value : 'osgi>',
			style : 'background:none repeat scroll 0 0 black;color:white;',
			anchor : '100%'
		} ],
		tbar : [ {
			xtype : "tbfill"
		}, {
			pressed : true,
			text : 'Clear',
			width : 80,
			handler : function() {
				genCardForm.getForm().findField('osgi').setValue('osgi>');
				cmd = '';
				genCardForm.getForm().findField('osgi').focus();
			}
		}, {
			xtype : "tbseparator"
		}, {
			pressed : true,
			text : 'Help',
			width : 80,
			handler : function() {
				helpWindow.show();
			}
		} ]
	});

	var firstWindow = new Ext.Window( {
		title : 'OSGI控制台',
		width : document.body.clientWidth - 8,
		height : document.body.clientHeight - 5,
		closable : false,
		collapsible : false,
		maximizable : false,
		border : false,
		constrain : true,
		draggable:false,//拖动
		resizable:false, //变大小 
		pageY : 100,
		pageX : document.body.clientWidth / 2 - 380 / 2,
		items : [ genCardForm ]
	});
	firstWindow.show();

	genCardForm.getForm().findField('osgi').on('keydown', function(obj, e) {
		var objInfos = obj.getValue();
		if (e.getKey() == Ext.EventObject.ENTER) {
			cmd += objInfos.substring(objInfos.length - 1, objInfos.length);
			// alert("cmd=="+trim(cmd));
			if (cmd.indexOf('>') != -1) {
				cmd = cmd.replace(/\>/g, "");
			}
			if (trim(cmd) != '') {
				alert("cmd==" + trim(cmd));
				if (trim(cmd) != 'ss') {
					alert('aaa');
				}
				autoScrollFoot();
			}
			cmd = '';
		} else {
			stopCursorToEnd();
			cmd += objInfos.substring(objInfos.length - 1, objInfos.length);
		}
	});

	genCardForm.getForm().findField('osgi').on('keyup', function(obj, e) {
		if (e.getKey() == Ext.EventObject.ENTER) {
			var objValue = obj.getValue();
			var s = objValue.substring(objValue.length - 1, objValue.length);
			if (s == '>') {
				obj.setValue(objValue + "\nosgi>");
			} else {
				obj.setValue(objValue + "osgi>");
			}
			autoScrollFoot();
		}
	});

	/**
	 * textarea滚动条始终在最下面 
	 ***/
	function autoScrollFoot() {
		var osgiObj = document.getElementById('osgi');
		osgiObj.scrollTop = osgiObj.scrollHeight;
	}

	/**
	 * textarea光标始终在最后一行 
	 ***/
	function stopCursorToEnd() {
		var e = event.srcElement;
		var r = e.createTextRange();
		r.moveStart('character', e.value.length);
		r.collapse(true);
		r.select();
	}

	var helpForm = new Ext.form.FormPanel( {
		region : 'north',
		id : 'helpForm',
		name : 'helpForm',
		height : 260,
		labelWidth : 70,
		frame : true,
		defaultType : 'textfield',
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		items : [ {
			fieldLabel : 'ss',
			name : 'pwd',
			allowBlank : false,
			value : '查看所有',
			readOnly : true,
			xtype : 'textfield',
			anchor : '100%'
		}, {
			fieldLabel : 'help',
			name : 'despwd',
			allowBlank : true,
			value : '帮助',
			readOnly : true,
			xtype : 'textfield',
			anchor : '100%'
		}

		]
	});

	var helpWindow = new Ext.Window( {
		title : 'HELP',
		width : 400,
		height : 320,
		closable : true,
		collapsible : true,
		maximizable : true,
		border : false,
		constrain : true,
		pageY : 100,
		pageX : document.body.clientWidth / 2 - 380 / 2,
		items : [ helpForm ]
	});
	firstWindow.show();
});
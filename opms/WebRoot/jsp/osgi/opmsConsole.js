Ext.onReady(function() {
	
	var hostStateStore = new Ext.data.Store( {
		baseParams : {
			code : 'HOST_STATE'
		},
		proxy : new Ext.data.HttpProxy( {
			url : _contextPath_ + '/codeSelect.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	hostStateStore.load();
	
	var systemIdStore = new Ext.data.Store( {
		baseParams : {
			code : 'SYSTEM_ID'
		},
		proxy : new Ext.data.HttpProxy( {
			url : _contextPath_ + '/codeSelect.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	systemIdStore.load();
	
	var systemGrid = new Ext.ux.FunctionGrid({
        title:"系统配置列表",
        searchColumn:2,
        dataId:"id",
        columns:[
	        {header:'主机IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'主机端口',sortable:true,dataIndex:'hostPort'
             },
	        {
            	 header:'系统标识',
            	 sortable:true,
            	 dataIndex:'systemId',
            	 renderer : function(value, m) {
 	 				var returnValue;
 	 				systemIdStore.each(function(record) {
 	 					if (record.get('code') != value) {
 	 					} else {
 	 						returnValue = record.get('codedesc');
 	 						return false;
 	 					}
 	 				});
 	 				return returnValue;
 	 			}
             },
	        {
            	 header:'开启标志',
            	 sortable:true,
            	 dataIndex:'state',
	           	 renderer : function(value, m) {
	 				var returnValue;
	 				hostStateStore.each(function(record) {
	 					if (record.get('code') != value) {
	 					} else {
	 						returnValue = record.get('codedesc');
	 						return false;
	 					}
	 				});
	 				if (returnValue == '开启') {
						m.css = 'x-grid-back-green';
					} else {
						m.css = 'x-grid-back-red';
					}
	 				return returnValue;
	 			}
             },
             {
            	 header:'控制台',
            	 sortable:true,
            	 dataIndex:'console',
            	 renderer : function() {
							return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
				 }
             }
        ],
        stripeRows:true,
        columnLines:true,
        autoSearch:true,
        winWidth: 650,
        winHeight:650,
        listAction:"jsp/osgi/OpmsSystemCfg_list.action",
        addTitle:"增加系统配置",
        editTitle:"编辑系统配置",
        viewTitle:"查看系统配置",
        initEditAction:"jsp/osgi/OpmsSystemCfg_find",
        saveAction:"jsp/osgi/OpmsSystemCfg_save",
        deleteAction:"jsp/osgi/OpmsSystemCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"主机IP","name":"hostIp","xtype":"textfield"},
                {"allowDecimals":true,"anchor":"95%","fieldLabel":"主机端口","name":"hostPort","xtype":"textfield"},
                {
                	"anchor":"95%",
                	"fieldLabel":"系统标识",
                	"name":"systemId",
                	"xtype":"dcombo",
                	store: systemIdStore
                },
                {
                	"anchor":"95%",
                	"fieldLabel":"开启标志",
                	"name":"state",
                	"xtype":"dcombo",
                	store: hostStateStore
                }
            ],
        formSet:[
               {xtype:'hidden',fieldLabel:'PK',name:'id'},
                {
                layout:'column',
                items:[
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"主机IP","maxLength":15,"name":"hostIp","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"系统标识","name":"systemId","xtype":"dcombo",store: systemIdStore}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"allowDecimals":true,"anchor":"95%","fieldLabel":"主机端口","name":"hostPort","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"开启标志","name":"state","xtype":"dcombo",store: hostStateStore}
                        ]
                    }
                ]
            }
        ]
    });

	systemGrid.on("cellclick",
			function(grid, rowIndex, columnIndex, e) {
				var store = grid.getStore();
				var record = store.getAt(rowIndex);
				var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
				
				if (fieldName == 'console' && columnIndex == 4) {
					doConsole(record);
				}
			});
	
	function doConsole(record) {
		var cmd='';
		
		var genCardForm = new Ext.form.FormPanel({
			region:'north',
			id : 'genCardForm',
			name : 'genCardForm',
			height:document.body.clientHeight-5,
			labelWidth : 1,
			frame : true,
			defaultType : 'textfield',
			labelAlign : 'right',
			bodyStyle : 'padding:5 5 5 5',
			items : [
					{
						fieldLabel : '',
						name : 'osgi',
						id : 'osgi',
						allowBlank : true,
						xtype : 'textarea',
						height:document.body.clientHeight-80,
						enableKeyEvents:true,
						value:'osgi>',
						style:'background:none repeat scroll 0 0 black;color:white;',
						anchor : '100%'
					}
					],
				tbar:[
				      	{xtype:"tbfill"},
	               		{pressed:true,text:'Clear',width:80,handler : function() {
	               			genCardForm.getForm().findField('osgi').setValue('osgi>');
	               			cmd='';
	               			genCardForm.getForm().findField('osgi').focus();
	               		}},
						{xtype:"tbseparator"},
	               		{pressed:true,text:'Help',width:80,handler : function() {
							helpWindow.show();
	               		}}
	               ]
		});
		
		
		var firstWindow = new Ext.Window({
			title : 'OSGI控制台',
			width : window.screen.width-250,
			height : window.screen.height-360,
			closable : true,//是否可以关闭窗口
			collapsible : false,//是否可以缩进窗口
			maximizable : false,//最大化
			border : false,
			constrain : true,
			draggable:false,//拖动
			resizable:false,//改变大小
//			pageY : 100,
//			pageX : document.body.clientWidth / 2 - 380 / 2,
			items : [genCardForm]
		});
		firstWindow.show();
		
		
		genCardForm.getForm().findField('osgi').on('keydown',function(obj,e){
			var objInfos=obj.getValue();
			if (e.getKey() == Ext.EventObject.ENTER) {
				cmd+=objInfos.substring(objInfos.length-1,objInfos.length);
				if(cmd.indexOf('>')!=-1){
					cmd=cmd.replace(/\>/g,'');
				}
				if(trim(cmd)!='')
				{
//					alert("cmd=="+trim(cmd));
					Ext.Ajax.request({
						url : _contextPath_ + '/jsp/osgi/OpmsConsole_doConsole.action',
						params : {
							cmd : trim(cmd)
						},
						waitMsg : '正在执行命令,请稍候...',
						callback : function(opts, success, response) {
							var datas = response.responseText;
							var objs = Ext.decode(datas);
							try{
								if (objs.success) {
		//							Ext.MessageBox.alert('提示', objs.msg);
									var osgiObj=genCardForm.getForm().findField('osgi');
									if(trim(cmd)=='ss')
									{
										osgiObj.setValue(osgiObj.getValue()+"\n"+objs.msg);
									}else{
										var resultMsg=objs.msg;
										osgiObj.setValue(osgiObj.getValue()+"\n"+resultMsg.substring(0,resultMsg.length-6));
									}
									osgiObj.focus();
									autoScrollFoot();
								} else {
									Ext.MessageBox.alert('提示', objs.msg);
								}
							}catch(err){
//								alert(err.message);
							}
							cmd='';
						}
					});
				}
			}else
			{
				stopCursorToEnd();
				if(e.getKey() == Ext.EventObject.BACKSPACE){
					var s=objInfos.substring(objInfos.length-1,objInfos.length);
					if(s == '>')
					{
						e.stopEvent();
					}
					if(trim(s) != ''){
						cmd=cmd.substring(0,cmd.length-1);
					}
				}else if (e.getKey() == Ext.EventObject.DELETE
						|| e.getKey() == Ext.EventObject.HOME
						|| e.getKey() == Ext.EventObject.END
						|| e.getKey() == Ext.EventObject.TAB
						|| e.getKey() == Ext.EventObject.ESC
						|| e.getKey() == Ext.EventObject.PAGEUP
						|| e.getKey() == Ext.EventObject.PAGEDOWN
						|| e.getKey() == Ext.EventObject.F5
						|| e.getKey() == Ext.EventObject.UP
						|| e.getKey() == Ext.EventObject.DOWN
						|| e.getKey() == Ext.EventObject.LEFT
						|| e.getKey() == Ext.EventObject.RIGHT) {
					e.stopEvent();//按键禁止操作
				}
				else{
					//只记录空格，数字，英文字母(大小写)
					if(e.getKey() == Ext.EventObject.SPACE || (e.getKey() >= 96 && e.getKey() <= 105) || (e.getKey() >= 48 && e.getKey() <= 57) || (e.getKey() >= 65 && e.getKey() <= 90)){
						cmd+=objInfos.substring(objInfos.length-1,objInfos.length);
					}
				}
			}
		});
		
		genCardForm.getForm().findField('osgi').on('keyup',function(obj,e){
			if (e.getKey() == Ext.EventObject.ENTER) {
				var objValue=obj.getValue();
				var s=objValue.substring(objValue.length-1,objValue.length);
				if(trim(s)=='>'){
					obj.setValue(objValue+"\nosgi>");
				}else{
					obj.setValue(objValue+"osgi>");
				}
				autoScrollFoot();
			}
		});
		
		/**
		 * textarea滚动条始终在最下面
		 **/
		function autoScrollFoot()
		{
			var osgiO=document.getElementById('osgi');
			osgiO.scrollTop=osgiO.scrollHeight;
		}
		
		/**
		 * textarea光标始终在最后一行
		 * */
		function stopCursorToEnd()
		{
			var e=event.srcElement;
			var r=e.createTextRange();
			r.moveStart('character',e.value.length);
			r.collapse(true);
			r.select();
		}
		
		var helpForm = new Ext.form.FormPanel({
			region:'north',
			id : 'helpForm',
			name : 'helpForm',
			height:260,
			labelWidth : 70,
			frame : true,
			defaultType : 'textfield',
			labelAlign : 'right',
			bodyStyle : 'padding:5 5 5 5',
			items : [{
						fieldLabel : 'ss',
						name : 'pwd',
						allowBlank : false,
						value:'查看所有',
						readOnly:true,
						xtype : 'textfield',
						anchor : '100%'
					},
					{
						fieldLabel : 'help',
						name : 'despwd',
						allowBlank : true,
						value:'帮助',
						readOnly:true,
						xtype : 'textfield',
						anchor : '100%'
					}
					
					]
		});
		
		var helpWindow = new Ext.Window({
			title : '帮助',
			width : 400,
			height : 320,
			closable : true,
			collapsible : true,
			maximizable : true,
			border : false,
			constrain : true,
//			pageY : 100,
//			pageX : document.body.clientWidth / 2 - 380 / 2,
			items : [helpForm]
		});
		firstWindow.show();
	}
	
	new Ext.Viewport({
        layout:'border',
        items:[systemGrid.panels]
    });
	
});
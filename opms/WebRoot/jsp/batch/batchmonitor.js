Ext.onReady(function() {
	
	var batchStatusStore = new Ext.data.Store( {
		baseParams : {
			code : 'BATCHSTATUS'
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
	batchStatusStore.load();
	
	
	var root = new Ext.tree.AsyncTreeNode({
		text : '����ִ����',
		expanded : true,
		id : '001'
	});

	var deptTree = new Ext.ux.tree.TreeGrid({
		loader : new Ext.tree.TreeLoader({
					dataUrl : 'jsp/opms/batch/BatchJobgrpExecution_list2.action'
				}),
		title : '',
		root : root,
		animate : false,
		frame : true,
		region : 'center',
		width : window.screen.width-230, // ����ָ��,������ʾ������
		//height : 400,
		columns : [{
					header : '����',
					dataIndex : 'name',
					width : 200
				}, {
					header : 'ִ��ID',
					dataIndex : 'id',
					width : 50
				}, {
					header : '���κ�',
					dataIndex : 'batchNo',
					width : 120
				}, {
					header : '������ַ',
					dataIndex : 'ipAddr',
					width : 120
				}, {
					header : '��ʼʱ��',
					dataIndex : 'starttime',
					width : 150
				}, {
					header : '����ʱ��',
					dataIndex : 'endtime',
					width : 150
				}, {
					header : 'ִ��״̬',
					dataIndex : 'status',
					width : 60,
					renderer : function(value, m) {
	  	 				var returnValue;
	  	 				batchStatusStore.each(function(record) {
	  	 					if (record.get('code') != value) {
	  	 					} else {
	  	 						returnValue = record.get('codedesc');
	  	 						return false;
	  	 					}
	  	 				});
	  	 				if (returnValue == 'S') {
							m.css = 'x-grid-back-green';
						} else if (returnValue == 'F') {
							m.css = 'x-grid-back-red';
						} else {
							m.css = '.x-grid-back-yellow';
						}
	  	 				return returnValue;
	  	 			}
				}, {
					header : '�쳣��Ϣ',
					dataIndex : 'errMsg',
					width : 160
				}, {
					header:'type',
					dataIndex:'type',
					width: 60
				}],
		useArrows : true,
		border : true
	});
	
	var panel2 = new Ext.Panel({
		region : 'center',
		title : '��������ִ�����', // ����
		iconCls : 'runitIcon', // ͼ��
		collapsible : false, // �Ƿ������۵�
		width : window.screen.width-300,
		autoScroll : true,
		frame : false,
		height : 200,
		items:[
		       deptTree
		      ]
	});
    
//	deptTree.on("click", function(node, e) {
//		Ext.MessageBox.alert('��ʾ', e);
//	});
	
	reloadData = function() {
		deptTree.root.reload();
	}
	
    addLog = function(msg) {
        var log = form.findById('log');       //ȡ�������ؼ�
        log.setValue(log.getValue() + msg);
        log.focus();
    }
    
    addLog2 = function(msg) {
        var log2 = form2.findById('log2');       //ȡ�������ؼ�
        log2.setValue(log2.getValue() + msg);
        log2.focus();
    }  
    
    function moveEnd(obj){
        obj.focus();
        var len = obj.value.length;
        var sel = obj.createTextRange();
//        sel.moveStart('character',len);
//        sel.collapse();
        sel.select();
//        if (document.selection) {
//        	alert("a");
//            var sel = obj.createTextRange();
//            sel.moveStart('character',len);
//            sel.collapse();
//            sel.select();
//        } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
//        	alert(b);
//            obj.selectionStart = obj.selectionEnd = len;
//        }
    } 
    
    var form = new Ext.form.FormPanel({
    	title:'����������־',  
    	labelWidth:0.1,  
    	iconCls : 'application_view_listicon', // ͼ��
    	bodyStyle:'padding:5 5 5 5',  
    	frame:true,  
    	height:200,  
    	width:window.screen.width-240,
    	region:'south',
    	collapsible:true,
    	items:[
    	       new Ext.form.TextArea({
    	    	   id:'log', 
//    	    	   readOnly:true,
    	    	   anchor:'100%',
    	    	   width:window.screen.width-240,
    	    	   height: 150
    	       })  
    	      ]
//    	buttons:[  
//    	           {text:'ȷ��',handler:addLog}  
//    	        ]  
    });
    
    var form2 = new Ext.form.FormPanel({
    	title:'������־',  
    	labelWidth:0.1,  
    	bodyStyle:'padding:5 5 5 5',  
    	frame:true,  
    	height:310,  
    	width:300,  
    	region:'east',
    	collapsible:true,
    	items:[
    	       new Ext.form.TextArea({
    	    	   id:'log2', 
    	    	   readOnly:true,
    	    	   width:270,
    	    	   height:310
    	       })  
    	      ]
//    	buttons:[  
//    	           {text:'ȷ��',handler:addLog}  
//    	        ]  
    });
    
    new Ext.Viewport({
            layout:'border',
            items:[panel2, form]
        });
 });
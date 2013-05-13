/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-4-11
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */

Ext.ux.FunctionGrid = Ext.extend(Ext.grid.GridPanel, {
            region:'center',
            margins: '5 5 5 5',
            forceFit:true,
            loadMask:true,
            root:'root',
            dataId:'id',
            totalProperty:'total',
            remoteSort:false,
            formWidth:window.screen.width,
            formHeight:window.screen.height,
            searchColumn:4,
            pluginSize:0,
            paging:true,
            edit:true,
            pageSize:20,
            lock:false,
            formLabelWidth:150,
            searchLabelWidth:75,
            winWidth:250,
            winHeight:350,
            remoteLoad:false,
            viewVisible:true,
            addVisible:true,
            editVisible:true,
            removeVisible:true,
            portalColumns:4,
            addText:"增加",
            editText:"修改",
            deleteText:"删除",
            viewText:"查看",
            autoSearch:false,
            initComponent: function(config) {
                this.isPortal = (isPortal != undefined ? isPortal : false);
                var fields = new Array();
                var cms = new Array();
//        fields[0] = {name:this.dataId};

                if (this.cmRender != undefined) {
                    var cmLength = this.isPortal ? this.portalColumns : this.cmRender.length;
                    for (var i = 0; i < cmLength; i++) {
                        fields[i] = {name:this.cmRender[i][2]};
                        cms[i] = {header:this.cmRender[i][0],sortable:this.cmRender[i][1],dataIndex:this.cmRender[i][2]};
                        if (this.cmRender[i][3] != undefined && this.cmRender[i][3] != null && this.cmRender[i][3] != "") {
                            cms[i].renderer = this.cmRender[i][3];
                        }
                        if (this.cmRender[i][4] != undefined && this.cmRender[i][4] != null && this.cmRender[i][4] != "") {
                            cms[i].xtype = 'actioncolumn';
                            cms[i].items = this.cmRender[i][4];
                        }
                        if (this.cmRender[i][5] != undefined && this.cmRender[i][5] != null && this.cmRender[i][5] != "") {
                            cms[i].width = this.cmRender[i][5];
                        }
                    }

                    this.colModel = new Ext.grid.ColumnModel(cms);
                    delete this.cmRender;
                } else if (this.columns != undefined) {
                    var cmLength = this.isPortal ? this.portalColumns : this.columns.length;
                    var columnsTemp = [];
                    for (var i = this.pluginSize; i < cmLength; i++) {
                        fields[i] = {name:this.columns[i].dataIndex};
                    }
                    if (this.isPortal) {
                        for (var i = 0; i < cmLength; i++) {
                            columnsTemp[i] = this.columns[i];
                        }
                        this.columns = columnsTemp;
                        delete columnsTemp;
                    }
                    if (this.lock && !this.isPortal) {
                        this.colModel = new Ext.ux.grid.LockingColumnModel(this.columns);
                        this.view = new Ext.ux.grid.LockingGridView();
                        delete this.columns;
                    }
                } else if (this.colModel != undefined) {
                    var cmLength = this.isPortal ? this.portalColumns : this.colModel.getColumnCount();

                    for (var i = this.pluginSize; i < cmLength; i++) {
                        fields[i] = {name:this.colModel.getDataIndex(i)};
                    }
                }
                var reader = new Ext.data.JsonReader({
                            root:this.root,id:Ext.isString(this.dataId) ? this.dataId : "",totalProperty:this.totalProperty,
                            fields:fields}
                );
                var ds = new Ext.data.Store({
//                            proxy:new Ext.data.HttpProxy({url: _contextPath_ + '/' + this.listAction + '.action'}),
                			proxy:new Ext.data.HttpProxy({url: _contextPath_ + '/' + this.listAction}),
                            remoteSort:this.remoteSort,
                            reader:reader
                        });
                var viewConfig = {forceFit:this.isPortal ? true : this.forceFit};
                this.viewConfig = viewConfig;
                this.store = ds;
                if (this.edit) {
                    var simple = new Ext.FormPanel({
//                                labelWidth: this.formLabelWidth+30,
                                labelAlign:'right',
                                url:_contextPath_ + '/' + this.saveAction + '.action',
                                frame:true,
                                autoScroll:true,
                                plugins:new Ext.ux.AllowBlank({hint:false}),
                                bodyStyle:'padding:5px 5px 0',
//            defaults: {width: 230},
//            defaultType: 'textfield',
                                items: this.formSet
                            });

                    var win = new Ext.Window({
                                layout:'fit',
                                width:this.formWidth-this.winWidth,
                                height:this.formHeight-this.winHeight,
                                closable : true,
                                modal:true,
                                closeAction:'hide',
                                constrain : true, // 设置窗口是否可以溢出父容器
                                plain: true,
                                items:[
                                    simple
                                ],
                                buttons: [
                                    {
                                        text: '保存'
                                        ,handler:this.saveForm
                                        ,scope:this
                                    },
                                    {
                                        text: '取消',
                                        handler:function() {
                                            win.hide();
                                        }
                                    }
                                ]
                            });
                    this.win1 = win;
                }
                if (!this.isPortal) {
                    var items = ['->'];
                    if (this.saveAction != undefined && this.saveAction != null) {
                        if (this.addVisible) {
                            items.push({
                                        text: this.addText,
                                        iconCls:'add'
                                        ,id:'add_id'
                                        ,handler:this.addObject
                                        ,scope:this

                                    }, '-');
                        }
                        if (this.editVisible) {
                            items.push({
                                        text: this.editText,
                                        iconCls:'edit'
                                        ,id:'edit_id'
                                        ,handler:this.editObject
                                        ,scope:this

                                    });
                        }
                    }
                    if (this.deleteAction != undefined && this.deleteAction != null) {
                    	if(this.removeVisible){
	                        items.push('-', {
	                                    text: this.deleteText,
	                                    iconCls:'remove'
	                                    ,handler:this.deleteObject
	                                    ,id:'delete_id'
	                                    ,scope:this
	                                });
                    	}
                    }
                    if (this.viewVisible) {
                        items.push('-', {
                                    text: this.viewText,
                                    iconCls:'view'
                                    ,handler:this.viewObject
                                    ,name:'view'
                                    ,id:'view_id'
                                    ,scope:this
                                });
                    }
                    if (this.paging) {
                        var bbar = new Ext.PagingToolbar({
                                    pageSize:this.pageSize,
//                                    prependButtons:true,
                                    store:ds,
                                    plugins: [new Ext.ux.PageSizePlugin(),new Ext.ux.ProgressBarPager()],
                                    displayInfo:true
                                });
                        if (this.edit) {
                            bbar.add(items);
                        }
                        this.bbar = bbar;
                    } else if (!this.paging && this.edit) {
                        var bbar = new Ext.Toolbar({
                                    items:items
                                });

                        this.bbar = bbar;
                    }
                } else {
                    delete this.title;
                }
//                Ext.apply(this, config);
                Ext.ux.FunctionGrid.superclass.initComponent.call(this);
                //双击一条记录弹出查看页面
                this.addListener('rowdblclick', this.viewObject);
//                ds.load({params:{start:0,limit:this.pageSize}});
                if (this.autoSearch) {
                    if (this.paging) {
                        ds.load({params:{start:0,limit:this.pageSize},callbak:function(r, o, s) {
                                    if (!s) {
                                        Ext.Msg.show({
                                                    title: '错误',
                                                    msg: '列表数据调用出错',
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                    }
                                }});
                    } else {
                        ds.load({callbak:function(r, o, s) {
                                    if (!s) {
                                        Ext.Msg.show({
                                                    title: '错误',
                                                    msg: '列表数据调用出错',
                                                    icon: Ext.MessageBox.ERROR
                                                });
                                    }
                                }});
                    }
                }
                this.panels = [this];
                if (this.searchSet != undefined && !this.isPortal) {
                    var searchItems = new Array();
                    for (var columnIndex = 0; columnIndex < this.searchColumn; columnIndex++) {
                        searchItems[columnIndex] = {
                            columnWidth:1 / this.searchColumn,
                            layout: 'form',
                            items: []
                        };
                    }

                    for (var searchIndex = 0; searchIndex < this.searchSet.length; searchIndex++) {
                        var witchColumn = searchIndex % this.searchColumn;
                        searchItems[witchColumn].items.push(this.searchSet[searchIndex]);
                    }
                    var formheigt = 28 * Math.ceil(this.searchSet.length / this.searchColumn) + 75;
                    var searchFrom = new Ext.FormPanel({
                                labelWidth: this.searchLabelWidth+20,
                                height:formheigt,
                                title:"搜索",
                                region: 'north',
                                labelAlign:'right',
                                url:_contextPath_ + '/' + this.searchAction + '.action',
                                frame:true,
                                collapsible:true,
                                bodyStyle:'padding:5px 5px 0',
                                buttonAlign:"center",
                                items: {
                                    layout:'column',
                                    items:searchItems
                                }
                                ,buttons: [
                                    {
                                        text: '查询',
                                        name:'searchbtn',
                                        id:'searchbtn',
                                        tooltip:'查询'
                                        ,handler:function() {
                                        if (searchFrom.form.isValid()) {
//                            alert(searchFrom.getForm().getFieldValues(true)['user.name']);
                                            ds.on('beforeload', function() {
                                                ds.baseParams = searchFrom.getForm().getValues();
                                            });
                                            if (this.paging) {
                                                var size = this.getBottomToolbar().pageSize;
                                                ds.load({params:{start:0,limit:size}});
                                            } else {
                                                ds.load();
                                            }
                                        }
                                    },scope:this
                                    },
                                    {
                                        text: '重置',
                                        tooltip:'重置'
                                        ,handler:function() {
                                        searchFrom.form.reset();
                                    }
                                    }
                                ]

                            });

                    this.panels = [searchFrom,this];

                }
//                new Ext.Viewport({
//                            layout:'border',
//                            items:[panels]
//                        });
            },

            addObject:function() {
                if (this.initAddHandler) {
                    this.initAddHandler.call(this.scope || this, this, this.win1.items.get(0));
                }
                this.win1.setTitle(this.addTitle);
                this.win1.show();
                this.win1.items.get(0).form.reset();
                this.win1.items.get(0).form.baseParams = {dataEditflag:false};

                var fn = function(c) {
                    var insertItems = c.items;
                    if (insertItems != undefined && insertItems != null) {
                        insertItems.each(fn);
                    } else {
                        if (!this.addReadOnly) {
                            c.setReadOnly(false);
                            c.removeClass("textReadonly");
                        } else {
                            c.setReadOnly(true);
                            c.addClass("textReadonly");
                        }
                    }
                };
                this.win1.items.get(0).items.each(fn);
                this.win1.buttons[0].show();

            }
            ,
            editObject:function () {
                this.win1.items.get(0).form.baseParams = {dataEditflag:true};
                var c = this.getSelectionModel().getSelections();
                if (c.length == 1) {
                    function intForm(o) {
                        if (this.initEditHandler) {
                            this.initEditHandler.call(this.scope || this, this, this.win1.items.get(0));
                        }
                        var resultJson = Ext.util.JSON.decode(o.responseText);
                        this.win1.setTitle(this.editTitle);
                        this.win1.show();
                        this.win1.items.get(0).form.setValues(resultJson);
                        this.win1.items.get(0).items.each(fn);
                    }

                    var fn = function(c) {
                        var insertItems = c.items;
                        if (insertItems != undefined && insertItems != null) {
                            if (c instanceof Ext.form.CheckboxGroup) {
                                raidoOrCheckBoxReadonly(c)
                            }
                            insertItems.each(fn);
                        } else {
                            if (c.editReadOnly != undefined && c.editReadOnly) {
                                c.setReadOnly(true);
                                c.addClass("textReadonly");
//                    c.el.dom.disabled = true;
                            }
                            else {
                                c.setReadOnly(false);
                                c.removeClass("textReadonly");
//                c.el.dom.disabled = false;
                            }

                        }
                    };

                    if (this.remoteLoad) {
                        Ext.Ajax.request({
                                    url : _contextPath_ + '/' + this.initEditAction + '.action',
                                    success : intForm,
                                    failure : function(response) {
                                        var errorWindow = new Ext.Window({
                                                    title:"出错啦！",
                                                    width:200,
                                                    height:200,
                                                    maximizable:true,
                                                    autoScroll:true,

//                        layout:'fit',
                                                    html:response.responseText
                                                });
                                        errorWindow.show();
                                        errorWindow.maximize();
                                    }
                                    ,params:this.makeParams(c[0])
                                    ,scope:this
                                });
                    } else {
                        if (this.initEditHandler) {
                            this.initEditHandler.call(this.scope || this, this, this.win1.items.get(0));
                        }
                        this.win1.setTitle(this.editTitle);
                        this.win1.show();
                        this.win1.items.get(0).form.setValues(c[0].json);
                        this.win1.items.get(0).items.each(fn);
                    }

                    function raidoOrCheckBoxReadonly(c) {
                        var insertItems = c.items;
                        if (c.editReadOnly != undefined && c.editReadOnly) {
                            var func1 = function(cc) {
                                if (!cc.checked) {
                                    cc.hide();
                                }
                            };
                            insertItems.each(func1);
                        }
                    }

                    this.win1.buttons[0].show();
                } else {
                    Ext.MessageBox.show({
                                title: '警告',
                                msg: "请选择一个对象",
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.WARNING
                            });
                }
            }
            ,

            viewObject:function () {
                var c = this.getSelectionModel().getSelections();
                if (c.length == 1) {

                    function intForm(o) {
                        if (this.initViewHandler) {
                            this.initViewHandler.call(this.scope || this, this, this.win1.items.get(0));
                        }
                        var resultJson = Ext.util.JSON.decode(o.responseText);
                        this.win1.setTitle(this.viewTitle);
                        this.win1.show();
                        this.win1.items.get(0).form.setValues(resultJson);
                        this.win1.items.get(0).items.each(fn);
                    }

                    var fn = function(c) {
                        var insertItems = c.items;
                        if (insertItems != undefined && insertItems != null) {
                            insertItems.each(fn);
                        } else {
                            c.setReadOnly(true);
                            c.addClass("textReadonly");
//                    c.el.dom.disabled = true;
                        }
                    };
                    if (this.remoteLoad) {
                        Ext.Ajax.request({
                                    url : _contextPath_ + '/' + this.initEditAction + '.action',
                                    success : intForm,
                                    failure : function(response) {
                                        var errorWindow = new Ext.Window({
                                                    title:"出错啦！",
                                                    width:200,
                                                    height:200,
                                                    maximizable:true,
                                                    autoScroll:true,

//                        layout:'fit',
                                                    html:response.responseText
                                                });
                                        errorWindow.show();
                                        errorWindow.maximize();
                                    }
                                    ,params:this.makeParams(c[0])
                                    ,scope:this
                                });
                    } else {
                        if (this.initViewHandler) {
                            this.initViewHandler.call(this.scope || this, this, this.win1.items.get(0));
                        }

                        this.win1.setTitle(this.viewTitle);
                        this.win1.show();
                        this.win1.items.get(0).form.setValues(c[0].json);
                        this.win1.items.get(0).items.each(fn);
                    }
                    this.win1.buttons[0].hide();
                } else {
                    Ext.MessageBox.show({
                                title: '警告',
                                msg: "请选择一个对象",
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.WARNING
                            });
                }
            }
            ,
            
//            deleteObject:function () {
//                var c = this.getSelectionModel().getSelections();
//                if (c.length > 0)
//                    Ext.MessageBox.confirm('消息', '确认要删除所选对象?', doDelObject, this);
//                else
//                    Ext.MessageBox.show({
//                                title: '警告',
//                                msg: "请选择要删除的对象",
//                                buttons: Ext.MessageBox.OK,
//                                icon: Ext.MessageBox.WARNING
//                            });
//
//                function doDelObject(btn) {
//                    if (btn == 'yes') {
//                        authorise(this,'delete',this.listAction.substring(this.listAction.lastIndexOf('/')+1,this.listAction.lastIndexOf('_')));
//                    }
//                }
//            },
//            saveForm:function () {
//            	if (this.win1.items.get(0).form.isValid()) {
//            		var operate = '';
//            		if(this.win1.items.get(0).form.baseParams.dataEditflag == true){
//            			operate = 'update';
//            		}else{
//            			operate = 'save';
//            		}
//	           		authorise(this,operate,this.listAction.substring(this.listAction.lastIndexOf('/')+1,this.listAction.lastIndexOf('_')));
//            	}else
//            	{
//            		 Ext.MessageBox.show({
//                         title: '警告',
//                         msg: "有必输项未输入或数据输入不正确",
//                         buttons: Ext.MessageBox.OK,
//                         icon: Ext.MessageBox.WARNING
//                     });
//            	}
//            },

            deleteObject:function () {
                var c = this.getSelectionModel().getSelections();
                if (c.length > 0)
                    Ext.MessageBox.confirm('消息', '确认要删除所选对象?', doDelObject, this);
                else
                    Ext.MessageBox.show({
                                title: '警告',
                                msg: "请选择要删除的对象",
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.WARNING
                            });

                function doDelObject(btn) {
                    if (btn == 'yes') {
//                        if (this.getSelectionModel().hasSelection()) {
                        Ext.Ajax.request({
                                    url : _contextPath_ + '/' + this.deleteAction + '.action',
                                    success : onSuccessDel,
                                    failure : function(response) {
                                        var errorWindow = new Ext.Window({
                                                    title:"出错啦！",
                                                    width:200,
                                                    height:200,
                                                    maximizable:true,
                                                    autoScroll:true,

//                        layout:'fit',
                                                    html:response.responseText
                                                });
                                        errorWindow.show();
                                        errorWindow.maximize();
                                    }
                                    ,params:this.makeDelParams(c)
                                    ,scope:this
                                });
//                        }
                    }
                }

                function onSuccessDel(o) {
                	var jsonobjs=Ext.decode(o.responseText);
                	var msg="删除对象成功";
                	if(jsonobjs.msg!=null && jsonobjs.msg!='')
                	{
                		msg=jsonobjs.msg;	
                	}
                    Ext.MessageBox.show({
                                title: '消息',
                                msg: msg,
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.INFO
                            });
                    this.store.reload();
                }
            }
            ,


            saveForm:function () {
                if (this.win1.items.get(0).form.isValid()) {
                    this.win1.items.get(0).form.submit({
                                success: function(form, action) {
                                    this.win1.hide();
                                    this.store.reload();
                                },
                                failure: function(form, action) {
                                    Ext.MessageBox.show({
                                                title: '提示',
                                                msg: action.result.msg,
                                                buttons: Ext.MessageBox.OK,
                                                icon: Ext.MessageBox.WARNING
                                            });
                                },
                                scope:this,
                                waitTitle:"请稍后",
                                waitMsg:"数据保存中……"
                            });
                }
            },

            makeParams:function (record) {
                var params = {};
                if (Ext.isArray(this.dataId)) {
                    for (var i = 0; i < this.dataId.length; i++) {
                        var key = this.dataId[i];
                        var value = record.json[key];
                        var param = {};
                        param[key] = value;
                        Ext.applyIf(params, param);
                    }
                } else {
                    params[this.dataId] = record.id;
                }
                return params;
            },

            makeDelParams:function (records) {
                var ids = new Array();
                for (var i = 0, len = records.length; i < len; i++) {
                    var record = records[i];
                    if (Ext.isArray(this.dataId)) {
                        var params = new Array();
                        for (var j = 0; j < this.dataId.length; j++) {
                            var key = this.dataId[j];
                            var value = record.json[key];
                            params[j] = value;
                        }
                        ids[ids.length] = params;
                    } else {
                        ids[ids.length] = record.id;
                    }
                }
                return {ids:ids.toString()}
            },

            /**
             * @deprecated
             */
            getForm:function() {
                return this.win1.items.get(0);
            },
            /**
             *
             */
            getEditForm:function() {
                return this.win1.items.get(0);
            },
            getSearchForm:function() {
                return this.panels[0];
            },  
            getStore:function(){
            	return this.store;
            }
        });

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
//    ds:ds,
//    cm:cm,
    viewConfig:   {
        forceFit: true
    },
    animCollapse: false,
    loadMask:true,
//    bbar:bbar,
    root:'root',
    dataId:'id',
    totalProperty:'total',
    remoteSort:false,

    initComponent: function(config) {
        Ext.apply(this, config);

        var fields = new Array();
        var cms = new Array();
        fields[0] = {name:this.dataId};
        for (var i = 1; i < this.cmRender.length; i++) {
            fields[i] = {name:this.cmRender[i][2]};
            if (this.cmRender[i][3] != undefined) {
                cms[i - 1] = {header:this.cmRender[i][0],sortable:this.cmRender[i][1],dataIndex:this.cmRender[i][2],renderer:this.cmRender[i][3]};
            } else {
                cms[i - 1] = {header:this.cmRender[i][0],sortable:this.cmRender[i][1],dataIndex:this.cmRender[i][2]};
            }
        }
        var reader = new Ext.data.JsonReader({
            root:this.root,id:this.dataId,totalProperty:this.totalProperty,
            fields:fields}
                );
        var ds = new Ext.data.Store({
//            proxy:new Ext.data.HttpProxy({url: _contextPath_ + '/' + this.listAction + '.action'}),
        	proxy:new Ext.data.HttpProxy({url: _contextPath_ + '/' + this.listAction}),
            remoteSort:this.remoteSort,
            reader:reader
        });
        this.colModel = new Ext.grid.ColumnModel(cms);
        this.store = ds;
        delete this.cmRender;


        var bbar = new Ext.PagingToolbar({
            pageSize:20,
            store:ds,
            plugins: [new Ext.ux.PageSizePlugin(),new Ext.ux.ProgressBarPager()],
//            prependButtons:true,
            displayInfo:true
            ,items:[ '->','-', {
                text: '增加',
                iconCls:'add'
                ,handler:addObject
                ,scope:this

            },
                '-', {
                    text: '修改',
                    iconCls:'edit'
                    ,handler:editObject
                    ,scope:this

                },
                '-', {
                    text: '删除',
                    iconCls:'remove'
                    ,handler:deleteObject
                    ,scope:this
                }
            ]

        });

        this.bbar = bbar;

        Ext.ux.FunctionGrid.superclass.initComponent.call(this);
        ds.load();

        function addObject() {
            win1.setTitle(this.addTitle);
            win1.show();
            win1.items.get(0).form.reset();
        }

        function editObject() {
            var c = this.getSelectionModel().getSelections();
            if (c.length > 0) {
                var id = c[0].id;
                Ext.Ajax.request({
                    url : _contextPath_ + '/' + this.initEditAction + '.action?' + this.dataId + "=" + id,
//                async:false,
                    success : intForm,
                    failure : function(response) {
                        var resultArray = Ext.util.JSON.decode(response.responseText);
                        Ext.Msg.alert('提示', resultArray.msg);
                    }
//                    ,params : {
//                        dataId:id
//                    }
                });
                win1.setTitle(this.editTitle);
                win1.show();

                function intForm(o) {
//                simple.form.reset();
                    var resultJson = Ext.util.JSON.decode(o.responseText);
                    win1.items.get(0).form.setValues(resultJson);
                }
            } else {
                Ext.MessageBox.show({
                    title: '警告',
                    msg: "请选择一个对象",
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                });
            }
        }

        function deleteObject() {
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
        }

        function doDelObject(btn) {
            if (btn == 'yes') {
                if (this.getSelectionModel().hasSelection()) {
                    var ids = new Array();
                    var records = this.getSelectionModel().getSelections();
                    for (var i = 0, len = records.length; i < len; i++) {
                        ids[ids.length] = records[i].id;
                    }
                    Ext.Ajax.request({
                        url : _contextPath_ + '/Functiondelete.action?' + this.dataId + "=" + ids,
                        success : onSuccessDel,
                        failure : function(response) {
                            alert(response.responseText);
                        }
                    });

                }
            }
        }

        function onSuccessDel(o) {
//            var resultJson = Ext.util.JSON.decode(o.responseText);
            Ext.MessageBox.show({
                title: '消息',
//                msg: resultJson.msg,
                msg: "删除对象成功",
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });
            ds.reload();
        }

        function saveForm() {
            if (win1.items.get(0).form.isValid()) {
                win1.items.get(0).form.submit({
                    success: function(form, action) {
                        win1.hide();
                        ds.reload();
                    },
                    waitTitle:"请稍后",
                    waitMsg:"数据保存中……"
                });
            }
        }

        var simple = new Ext.FormPanel({
            labelWidth: 75,
            labelAlign:'right',
            url:_contextPath_ + '/'+this.saveAction+'.action',
            frame:true,
            bodyStyle:'padding:5px 5px 0',
            defaults: {width: 230},
            defaultType: 'textfield',
            items: this.formSet
        });

        var win1 = new Ext.Window({
            layout:'fit',
            width:300,
            height:200,
            closable : true,
            modal:true,
            closeAction:'hide',
            plain: true,
            items:[
                simple
            ],
            buttons: [
                {
                    text: '保存'
                    ,handler:saveForm
                },
                {
                    text: '取消',
                    handler:function() {
                        win1.hide();
//                        win1 = undefined;
                    }
                }
            ]
        });
    }
});

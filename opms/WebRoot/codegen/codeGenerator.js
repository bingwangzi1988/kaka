/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-7
 * Time: ����11:21
 * To change this template use File | Settings | File Templates.
 */
Ext.onReady(function() {

    var hiddenPkgs = [];
    var selectAll = new Ext.form.Checkbox({
                boxLabel: 'ȫѡ'
            });

    var searchNode = new Ext.form.TextField({
                emptyText:'�����',
                enableKeyEvents:true
            });

    var treeloader = new Ext.tree.TreeLoader({
                dataUrl :  _contextPath_ + '/CodeGendbTree.action'
            });
    var root = new Ext.tree.AsyncTreeNode({id: '0',
                text: '��' });
    var dbTree = new Ext.tree.TreePanel({
                region:'west',
                margins: '5 0 5 5',
                collapsible: true,
                title:"���ݿ��",
                split:true,
                rootVisible:false,
                autoScroll:true,
                width: 250,
                minSize: 150,
                maxSize: 400,
                layoutConfig:{
                    animate:true
                },
                containerScroll: true,
                root: root,
                loader :treeloader,
                tbar:[
                    selectAll,"    ",searchNode,"-",
                    {
                        text:"����",pressed:true,
                        handler:function() {
                            var checkedNodes = dbTree.getChecked();
                            if (checkedNodes.length != 0) {
                                if (globalConfig.form.isValid()) {
                                    globalConfig.collapse();

                                    var tables = [];
                                    var j = 0;
                                    for (var i = 0; i < checkedNodes.length; i++) {
                                        if (!checkedNodes[i].hidden) {
                                            tables[j] = checkedNodes[i].id;
                                            j++;
                                        }
                                    }

                                    Ext.MessageBox.show({
                                                title: '��ȴ�',
                                                msg: '������...',
                                                progressText: '����...',
                                                width:300,
                                                progress:true,
                                                closable:false
                                            });

                                    var f = function(v) {
                                        return function() {
                                            var i = v / tables.length;
                                            Ext.MessageBox.updateProgress(i, Math.round(100 * i) + '% ���');
                                        };
                                    };
                                    for (var i = 1; i < tables.length + 1; i++) {
                                        setTimeout(f(i), i * 300);

                                    }
                                    HttpRequest({
                                                url : _contextPath_ + '/CodeGengenerator.action',
                                                params:Ext.applyIf(globalConfig.form.getValues(), {tables:tables + ""}),
                                                success : function(response) {
                                                    var result = Ext.util.JSON.decode(response.responseText);
                                                    if (result.success == true) {
                                                        Ext.MessageBox.confirm('�������', '�Ƿ�������ļ��У�',
                                                                function(btn) {
                                                                    if (btn == 'yes') {
                                                                        HttpRequest({
                                                                                    url : _contextPath_ + '/CodeGenopenOutRoot.action'
                                                                                })
                                                                    }
                                                                },
                                                                this);
                                                    }
//                                                    else if (result.success == false) {
//                                                        Ext.MessageBox.alert('��ʾ', '����д������ȫ�����ò���',
//                                                                function() {
//                                                                    globalConfig.expand();
//                                                                    globalConfig.form.isValid();
//                                                                },
//                                                                this);
//                                                    }
                                                }
                                            });
                                } else {
                                    Ext.MessageBox.alert('��ʾ', '����д������ȫ�����ò���',
                                            function() {
                                                globalConfig.expand();
                                                globalConfig.form.isValid();
                                            },
                                            this);
                                }
                            } else {
                                Ext.MessageBox.alert('��ʾ', '��ѡ��Ҫ���ɵı�');
                            }
                        }
                    }
                ]
            });

    selectAll.on("check", function() {
        Ext.each(root.childNodes, function(n) {
            n.getUI().toggleCheck(selectAll.getValue());
        });
    });
    var treefilter = new Ext.tree.TreeFilter(dbTree, {
                clearBlank: true,
                autoClear: true
            });

    searchNode.on("keyup", function() {
        var text = searchNode.getValue();
        Ext.each(hiddenPkgs, function(n) {
            n.ui.show();
        });
        if (!text) {
            treefilter.clear();
            return;
        }

        dbTree.expandAll();

//        selectAll.setValue(false);
//        Ext.each(root.childNodes, function(n) {
//            n.getUI().toggleCheck(false);
//        });

        var re = new RegExp(Ext.escapeRe(text), 'i');

        treefilter.filterBy(function(n) {
            return !n.isLeaf() || re.test(n.text);
        });

        hiddenPkgs = [];

        dbTree.root.cascade(function(n) {
            if (!n.isLeaf() && n.ui.ctNode.offsetHeight < 3) {
                n.ui.hide();
                hiddenPkgs.push(n);
            }
        });

    });

    dbTree.on("click", function(node, e) {
        HttpRequest({
                    url : _contextPath_ + '/CodeGeninitTable.action',
                    success:function(response) {
                        var result = Ext.util.JSON.decode(response.responseText);
                        paging.setValue(result.paging);
                        columnLock.setValue(result.columnLock);
                        edit.setValue(result.edit);
                        stripeRows.setValue(result.stripeRows);
                        columnLines.setValue(result.columnLines);
                        girdPlugins.setValue(result.girdPlugins);
                    },
                    params:{table:node.id}
                });
        ds.load({params:{table:node.id}});
    });

    var xtypeStore = new Ext.data.SimpleStore({
                fields: ['id', 'name'],
                data : [
                    ["textfield", 'textfield' ],
                    ["numberfield", 'numberfield' ],
                    ["datefield", 'datefield' ],
                    ["dcombo",'select'],
                    ["radiogroup", 'radiogroup'],
                    ["checkboxgroup", 'checkboxgroup'],
                    ["textarea", 'textarea']
                ]
            });
    var vtypeStore = new Ext.data.SimpleStore({
                fields: ['id', 'name'],
                data : [
                    [null,"��"],
                    ["url", 'url' ],
                    ["email", 'email' ],
                    ["phone", '�绰,����' ],
                    ["mobile", '�ֻ�' ],
                    ["idcard", '���֤' ],
                    ["postCode", '�ʱ�' ],
                    ["IPAddress", 'ip']
                ]
            });
    var reader = new Ext.data.JsonReader({
                root:'root',id:'name',
                fields:  [
                    {
                        name:'name'
                    },
                    {
                        name:'remark'
                    },
                    {
                        name:'type'
                    },
                    {
                        name:'size'
                    },
                    {
                        name:'pk'
                    },
                    {
                        name:'grid', type: 'bool'
                    },
                    {
                        name:'search', type: 'bool'
                    },
                    {
                        name:'form', type: 'bool'
                    },
                    {
                        name:'xtype'
                    },
                    {
                        name:'vtype'
                    },
                    {
                        name:'editReadOnly', type: 'bool'
                    }
                ]}
    );

    var ds = new Ext.data.Store({
                proxy:new Ext.data.HttpProxy({url: _contextPath_ + '/CodeGendbColumn.action'}),
                remoteSort:false,
                reader:reader
            });

    var cm = new Ext.ux.grid.LockingColumnModel([
        {
            header:'�ֶ�����',
            sortable: false,
            locked:true,
            width:120,
            dataIndex:'name'
        },
        {
            header:'�ֶα�ע',
            sortable: false,
            locked:true,
            dataIndex:'remark'
        },
        {
            header:'�ֶ�����',
            sortable: false,
            width:80,
            dataIndex:'type'
        },
        {
            header:'��󳤶�',
            sortable: false,
            width:60,
            dataIndex:'size'
        },
        {
            header:'����',
            sortable: false,
            width:60,
            xtype: 'checkcolumn',
            dataIndex:'pk'
        },
        {
            header:'�б���ʾ',
            sortable: false,
            xtype: 'checkcolumn',
            width:60,
            dataIndex:'grid'
        },
        {
            header:'��ѯ�ֶ�',
            sortable: false,
            width:60,
            xtype: 'checkcolumn',
            dataIndex:'search'
        },
        {
            header:'���ֶ�',
            sortable: false,
            width:60,
            xtype: 'checkcolumn',
            dataIndex:'form'
        },
        {
            header:'������',
            sortable: false,
            dataIndex:'xtype',
            editor: new Ext.form.ComboBox({
                        store: xtypeStore,
                        triggerAction: 'all',
                        displayField:'name',
                        valueField:'id',
                        mode: 'local',
                        editable: false,
                        lazyRender: true

                    }),
            renderer: showXtypeDisplay
        },
        {
            header:'����У��',
            sortable: false,
            dataIndex:'vtype',
            editor: new Ext.form.ComboBox({
                        store: vtypeStore,
                        triggerAction: 'all',
                        displayField:'name',
                        valueField:'id',
                        mode: 'local',
                        editable: false,
                        lazyRender: true

                    }),
            renderer: showVtypeDisplay
        },
        {
            header:'�༭���ɸ���',
            sortable: false,
            xtype: 'checkcolumn',
            dataIndex:'editReadOnly'
        }
    ]);

    function showXtypeDisplay(value, cellmeta, record) {
        var index = xtypeStore.find("id", value);
        var record = xtypeStore.getAt(index);
        var displayText = "";
        if (record == null) {
            displayText = value;
        } else {
            displayText = record.get('name');
        }
        return displayText;
    }

    function showVtypeDisplay(value, cellmeta, record) {
        var index = vtypeStore.find("id", value);
        var record = vtypeStore.getAt(index);
        var displayText = "";
        if (record == null) {
            displayText = value;
        } else {
            displayText = record.get('name');
        }
        return displayText;
    }

    var girdPluginStore = new Ext.data.SimpleStore({
                fields: ['id', 'name'],
                data : [
                    ["RowNumberer", '������' ],
                     ["Checkbox", '�ж�ѡ' ],
                    ["RowExpander", '��չ��' ]
                ]
            });

    var tbar = new Ext.Toolbar();
    var paging = new Ext.form.Checkbox({name:"paging"});
    var columnLock = new Ext.form.Checkbox({name:"columnLock"});
    var edit = new Ext.form.Checkbox({name:"edit"});
    var stripeRows = new Ext.form.Checkbox({name:"stripeRows"});
    var columnLines = new Ext.form.Checkbox({name:"columnLines"});
    var girdPlugins = new Ext.ux.form.CheckboxCombo({
                store: girdPluginStore,
                width:100,
                displayField:'name',
                valueField:'id',
                mode: 'local',
                editable: false
            });
    var checkArray = [paging,columnLock,edit,stripeRows,columnLines];
    for (var i = 0; i < checkArray.length; i++) {
        var obj = checkArray[i];
        obj.on("check", function(checkbox, value) {
            HttpRequest({
                        url : _contextPath_ + '/CodeGenchangeTable.action',
                        params:{table:dbTree.getSelectionModel().getSelectedNode().id,attribute:checkbox.name,value:value}
                    });
        });

    }

    girdPlugins.on("collapse", function(cmb, value) {
            HttpRequest({
                        url : _contextPath_ + '/CodeGenchangeTable.action',
                        params:{table:dbTree.getSelectionModel().getSelectedNode().id,attribute:"girdPlugins",value:girdPlugins.getValue()}
                    });
    });
    tbar.add("��ҳ:", paging, "-", "��ɾ�Ĳ�:", edit, "-", "������:", columnLock,
            "-", "������:", stripeRows, "-", "����:", columnLines,"-", "���:", girdPlugins);

    var grid = new Ext.grid.EditorGridPanel({
                region:'center',
                id:'topic-grid',
                title:"�ֶ�",
                margins: '5 5 5 0',
                ds:ds,
                cm:cm,
                columnLines:true,
                stripeRows:true,
//                autoExpandColumn:'name',
                viewConfig:   {
                    forceFit: true
                },
                tbar:tbar,
                view:new Ext.ux.grid.LockingGridView(),
                loadMask:true
            });

    grid.on("afteredit", afterEdit, this);


    var form = new Ext.form.FormPanel({
                labelWidth: 65,
                url:_contextPath_ + '/CodeGensaveCode.action',
                defaultType: 'textfield',
                frame:true,
                items: [
                    {
                        fieldLabel: '������',
                        name: 'tableName',
                        value:'EACODE',
                        allowBlank:false,
                        anchor:'95%'
                    },
                    {
                        fieldLabel: '�����ֶ�',
                        name: 'field',
                        value:'FIELD',
                        anchor:'95%'
                    },
                    {
                        fieldLabel: '�洢�ֶ�',
                        name: 'code',
                        value:'CODE',
                        allowBlank:false,
                        anchor:'95%'
                    },
                    {
                        fieldLabel: '��ʾ�ֶ�',
                        name: 'codedesc',
                        value:'CODEDESC',
                        allowBlank:false,
                        anchor:'95%'
                    },
                    {
                        fieldLabel: '�����ֶ�ֵ',
                        name: 'fieldvalue',
                        anchor:'95%'
                    }
                ]
            });

    var window = new Ext.Window({
                title: '����������',
                width: 250,
                height:240,
                layout:'fit',
                closable:false,
                closeAction:'hide',
                plain:true,
                modal:true,
                allowBlank:false,
                buttonAlign:'center',
                items: form,
                buttons: [
                    {
                        text: 'ȷ��',
                        handler:saveForm
                    }
//                    ,{
//                        text: 'ȡ��',
//                        handler:function() {
//                            window.hide();
//                        }
//                    }
                ]
            });

    function saveForm() {
        if (form.form.isValid()) {
            form.form.submit({
                        success: function(form, action) {
                            window.hide();
                        }
                    });
        }
    }

    function afterEdit(e) {
        var r = e.record;
        var columnName = r.get("name");
        if (e.field == "xtype" && (e.value == "dcombo" || e.value == "radiogroup" || e.value == "checkboxgroup")) {
            form.form.baseParams = {table:dbTree.getSelectionModel().getSelectedNode().id,columnName:columnName};
            form.form.load({
                        url : _contextPath_ + '/CodeGenloadSelectCode.action'
                    });
            window.show();
        }
        if (e.value != e.originalValue) {
            HttpRequest({
                        url : _contextPath_ + '/CodeGenmakeParams.action',
                        params:{table:dbTree.getSelectionModel().getSelectedNode().id,columnName:columnName,field:e.field,value:e.value}
                    });
        }
    }

    var globalConfig = new Ext.FormPanel({
                collapsible: true,
                height:60,
                collapseMode:'mini',
                region:'south',
                title:"ȫ������",
                labelAlign:'right',
                url: _contextPath_ + '/CodeGensaveGlobal.action',
                frame:true,
                items: [
                    {
                        layout:'column',
                        border:false,
                        items:[
                            {
                                columnWidth:.33,
                                layout: 'form',
                                items: [
                                    {
                                        xtype:'textfield',
                                        fieldLabel: 'java����·��',
                                        name: 'basepackage',
                                        emptyText:"com.company.project",
                                        allowBlank : false,
                                        anchor:'95%'
                                    }
                                ]
                            },
                            {
                                columnWidth:.33,
                                layout: 'form',
                                items: [
                                    {
                                        xtype:'textfield',
                                        fieldLabel: 'jsp,js����·��',
                                        name: 'namespace',
                                        emptyText:"jsp/module",
                                        allowBlank : false,
                                        anchor:'95%'
                                    }
                                ]
                            },
                            {
                                columnWidth:.33,
                                layout: 'form',
                                items: [
                                    {
                                        xtype:'textfield',
                                        fieldLabel: '����Ŀ¼',
                                        name: 'outRoot',
                                        value:"c:\\generator-output",
                                        allowBlank : false,
                                        anchor:'95%'
                                    }
                                ]
                            }
                        ]}
                ]

//                ,buttons: [
//                    {
//                        text: '����',
//                        handler:function() {
//                            if (globalConfig.form.isValid()) {
//                                globalConfig.form.submit({
//                                            success: function(form, action) {
//                                                globalConfig.collapse();
//                                            }
//                                        });
//                            }
//
//                        }
//                    }
//                ]

            });
    globalConfig.form.load({
                url : _contextPath_ + '/CodeGeninitglobalConfig.action'
            });


    var viewport = new Ext.Viewport({
                layout:'border',
                items:[
                    dbTree,
                    grid,
                    globalConfig
                ]
            });
});
/**
 * �ֵ����
 *
 * @author XiongChun
 * @since 2010-02-13
 */
Ext.onReady(function() {
    var sm = new Ext.grid.CheckboxSelectionModel();
    var cm = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), sm, {
        header : '�����ֶ�',
        dataIndex : 'field',
        sortable : true,
        width : 130
    }, {
        header : '�ֶ���',
        dataIndex : 'fieldname'
    }, {
        header : '����',
        dataIndex : 'code',
        width : 50
    }, {
        header : '��������',
        dataIndex : 'codedesc'
    }, {
        header : '����״̬',
        dataIndex : 'enabled',
        renderer : function(value) {
            if (value == '1')
                return '����';
            else if (value == '0')
                return 'ͣ��';
        }
    }, {
        header : '�༭ģʽ',
        dataIndex : 'editmode',
        renderer : function(value) {
            if (value == '1')
                return '�ɱ༭';
            else if (value == '0')
                return 'ֻ��';
        }
    }, {
        header : '�ֶα��',
        dataIndex : 'codeid',
        hidden : false,
        width : 80,
        sortable : true
    }, {
        header : '��ע',
        dataIndex : 'remark',
        id : 'remark'
    } ]);

    var store = new Ext.data.Store({
                proxy : new Ext.data.HttpProxy({
                            url : './resource.ered?reqCode=queryCodeItems'
                        }),
                reader : new Ext.data.JsonReader({
                            totalProperty : 'TOTALCOUNT',
                            root : 'ROOT'
                        }, [
                    {
                        name : 'codeid'
                    },
                    {
                        name : 'field'
                    },
                    {
                        name : 'fieldname'
                    },
                    {
                        name : 'code'
                    },
                    {
                        name : 'codedesc'
                    },
                    {
                        name : 'enabled'
                    },
                    {
                        name : 'editmode'
                    },
                    {
                        name : 'remark'
                    }
                ])
            });

    // ��ҳ����ʱ���ϲ�ѯ����
    store.on('beforeload', function() {
        this.baseParams = {
            queryParam : Ext.getCmp('queryParam').getValue()
        };
    });

    var pagesize_combo = new Ext.form.ComboBox({
                name : 'pagesize',
                hiddenName : 'pagesize',
                typeAhead : true,
                triggerAction : 'all',
                lazyRender : true,
                mode : 'local',
                store : new Ext.data.ArrayStore(
                        {
                            fields : [ 'value', 'text' ],
                            data : [
                                [ 10, '10��/ҳ' ],
                                [ 20, '20��/ҳ' ],
                                [ 50, '50��/ҳ' ],
                                [ 100, '100��/ҳ' ],
                                [ 250, '250��/ҳ' ],
                                [ 500, '500��/ҳ' ]
                            ]
                        }),
                valueField : 'value',
                displayField : 'text',
                value : '50',
                editable : false,
                width : 85
            });
    var number = parseInt(pagesize_combo.getValue());
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

    var bbar = new Ext.PagingToolbar({
                pageSize : number,
                store : store,
                displayInfo : true,
                displayMsg : '��ʾ{0}����{1}��,��{2}��',
                plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
                emptyMsg : "û�з��������ļ�¼",
                items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
            })

    var grid = new Ext.grid.GridPanel(
            {
                title : '<span style="font-weight:normal">�ֵ�</span>',
                iconCls: 'application_view_listIcon',
                renderTo : 'codeTableGrid',
                height : 510,
                store : store,
                region : 'center',
                loadMask : {
                    msg : '���ڼ��ر������,���Ե�...'
                },
                stripeRows : true,
                frame : true,
                autoExpandColumn : 'remark',
                cm : cm,
                sm : sm,
                tbar : [
                    {
                        text : '����',
                        iconCls : 'page_addIcon',
                        handler : function() {
                            codeWindow.show();
                        }
                    },
                    '-',
                    {
                        text : '�޸�',
                        iconCls : 'page_edit_1Icon',
                        handler : function() {
                            ininEditCodeWindow();
                        }
                    },
                    '-',
                    {
                        text : 'ɾ��',
                        iconCls : 'page_delIcon',
                        handler : function() {
                            deleteCodeItems();
                        }
                    },
                    '-',
                    {
                        text : '�ڴ�ͬ��',
                        iconCls : 'arrow_refreshIcon',
                        handler : function() {
                            synMemory('Ҫ���ֵ����ݽ����ڴ�ͬ��������?', '1');
                        }
                    },
                    '-',
                    '��ʾ:ά���ֵ�����ִ���ڴ�ͬ��',
                    '->',
                    new Ext.form.TextField({
                                id : 'queryParam',
                                name : 'queryParam',
                                emptyText : '�ֶ�|�ֶ���|��������',
                                enableKeyEvents : true,
                                listeners : {
                                    specialkey : function(field, e) {
                                        if (e.getKey() == Ext.EventObject.ENTER) {
                                            queryCodeItem();
                                        }
                                    }
                                },
                                width : 130
                            }),
                    {
                        text : '��ѯ',
                        iconCls : 'previewIcon',
                        handler : function() {
                            queryCodeItem();
                        }
                    },
                    '-',
                    {
                        text : 'ˢ��',
                        iconCls : 'arrow_refreshIcon',
                        handler : function() {
                            store.reload();
                        }
                    }
                ],
                bbar : bbar
            });
    store.load({
                params : {
                    start : 0,
                    limit : bbar.pageSize
                }
            });

    grid.addListener('rowdblclick', ininEditCodeWindow);
    grid.on('sortchange', function() {
        // grid.getSelectionModel().selectFirstRow();
    });

    bbar.on("change", function() {
        // grid.getSelectionModel().selectFirstRow();
    });
    /**
     * ����������ձ�
     */
    var codeWindow;
    var formPanel;
    var enabledStore = new Ext.data.SimpleStore({
                fields : [ 'value', 'text' ],
                data : [
                    [ '0', '0 ͣ��' ],
                    [ '1', '1 ����' ]
                ]
            });
    var enabledCombo = new Ext.form.ComboBox({
                name : 'enabled',
                hiddenName : 'enabled',
                store : enabledStore,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'value',
                displayField : 'text',
                value : '1',
                fieldLabel : '����״̬',
                emptyText : '��ѡ��...',
                allowBlank : false,
                forceSelection : true,
                editable : false,
                typeAhead : true,
                anchor : '100%'
            });

    var editmodeCombo = new Ext.form.ComboBox({
                name : 'editmode',
                hiddenName : 'editmode',
                typeAhead : true,
                triggerAction : 'all',
                lazyRender : true,
                mode : 'local',
                store : new Ext.data.ArrayStore({
                            fields : [ 'value', 'text' ],
                            data : [
                                [ 0, '0 ֻ��' ],
                                [ 1, '1 �ɱ༭' ]
                            ]
                        }),
                valueField : 'value',
                displayField : 'text',
                anchor : '100%',
                value : '1',
                editable : false,
                emptyText : '��ѡ��...',
                fieldLabel : '�༭ģʽ'
            });

    formPanel = new Ext.form.FormPanel({
                id : 'codeForm',
                name : 'codeForm',
                defaultType : 'textfield',
                labelAlign : 'right',
                labelWidth : 60,
                frame : false,
                bodyStyle : 'padding:5 5 0',
                items : [
                    {
                        fieldLabel : '�����ֶ�',
                        name : 'field',
                        anchor : '100%',
                        maxLength:15,
                        allowBlank : false
                    },
                    {
                        fieldLabel : '�ֶ���',
                        name : 'fieldname',
                        anchor : '100%',
                        maxLength:20,
                        allowBlank : false
                    },
                    {
                        xtype : 'textfield',
                        fieldLabel : '����',
                        name : 'code',
                        anchor : '100%',
                        maxLength:20,
                        allowBlank : false
                    },
                    {
                        fieldLabel : '��������',
                        name : 'codedesc',
                        anchor : '100%',
                        maxLength:50,
                        allowBlank : false
                    },
                    enabledCombo,
                    editmodeCombo,
                    {
                        fieldLabel : '��ע',
                        name : 'remark',
                        anchor : '100%',
                        maxLength:200,
                        allowBlank : true
                    }
                ]
            });

    codeWindow = new Ext.Window(
            {
                layout : 'fit',
                width : 300,
                height : 260,
                resizable : false,
                draggable : true,
                closeAction : 'hide',
                title : '<span style="font-weight:normal">�����ֵ�</span>',
                // iconCls : 'page_addIcon',
                modal : true,
                collapsible : true,
                titleCollapse : true,
                maximizable : false,
                buttonAlign : 'right',
                border : false,
                animCollapse : true,
                animateTarget : Ext.getBody(),
                constrain : true,
                items : [ formPanel ],
                buttons : [
                    {
                        text : '����',
                        iconCls : 'acceptIcon',
                        handler : function() {
                            if (runMode == '0') {
                                Ext.Msg
                                        .alert('��ʾ',
                                        'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
                                return;
                            }
                            if (codeWindow.getComponent('codeForm').form
                                    .isValid()) {
                                codeWindow.getComponent('codeForm').form
                                        .submit({
                                            url : './resource.ered?reqCode=saveCodeItem',
                                            waitTitle : '��ʾ',
                                            method : 'POST',
                                            waitMsg : '���ڴ�������,���Ժ�...',
                                            success : function(form, action) {
                                                store.reload();
                                                Ext.Msg.confirm('��ȷ��','������������ɹ�,��Ҫ������Ӵ��������?',
                                                        function(btn, text) {
                                                            if (btn == 'yes') {

                                                                codeWindow.getComponent('codeForm').form.findField("code").reset();
                                                                codeWindow.getComponent('codeForm').form.findField("codedesc").reset();
                                                                codeWindow.getComponent('codeForm').form.findField("remark").reset();

                                                            } else {
                                                                codeWindow.hide();
                                                                synMemory('Ҫ���������ڴ�ͬ����');
                                                            }
                                                        });
                                            },
                                            failure : function(form, action) {
                                                var msg = action.result.msg;
                                                Ext.MessageBox
                                                        .alert(
                                                        '��ʾ',
                                                        '������ձ���ʧ��:<br>' + msg);
                                                codeWindow
                                                        .getComponent('codeForm').form
                                                        .reset();
                                            }
                                        });
                            } else {
                                // ����֤ʧ��
                            }
                        }
                    },
                    {
                        text : '����',
                        id : 'btnReset',
                        iconCls : 'tbar_synchronizeIcon',
                        handler : function() {
                            clearForm(formPanel.getForm());
                            enabledCombo.setValue('1');
                            editmodeCombo.setValue('1');
                        }
                    },
                    {
                        text : '�ر�',
                        iconCls : 'deleteIcon',
                        handler : function() {
                            codeWindow.hide();
                        }
                    }
                ]
            });

    /** *****************�޸Ĵ������*********************** */
    var enabledCombo_E = new Ext.form.ComboBox({
                name : 'enabled',
                hiddenName : 'enabled',
                store : new Ext.data.ArrayStore({
                            fields : [ 'value', 'text' ],
                            data : [
                                [ '0', '0 ͣ��' ],
                                [ '1', '1 ����' ]
                            ]
                        }),
                mode : 'local',
                triggerAction : 'all',
                valueField : 'value',
                displayField : 'text',
                value : '1',
                fieldLabel : '����״̬',
                emptyText : '��ѡ��...',
                allowBlank : false,
                forceSelection : true,
                editable : false,
                typeAhead : true,
                anchor : '100%'
            });

    var editmodeCombo_E = new Ext.form.ComboBox({
                name : 'editmode',
                hiddenName : 'editmode',
                typeAhead : true,
                triggerAction : 'all',
                lazyRender : true,
                disabled : true,
                fieldClass : 'x-custom-field-disabled',
                mode : 'local',
                store : new Ext.data.ArrayStore({
                            fields : [ 'value', 'text' ],
                            data : [
                                [ 0, '0 ֻ��' ],
                                [ 1, '1 �ɱ༭' ]
                            ]
                        }),
                valueField : 'value',
                displayField : 'text',
                anchor : '100%',
                value : '1',
                editable : false,
                emptyText : '��ѡ��...',
                fieldLabel : '�༭ģʽ'
            });

    var editCodeWindow, editCodeFormPanel;
    editCodeFormPanel = new Ext.form.FormPanel({
                labelAlign : 'right',
                labelWidth : 60,
                defaultType : 'textfield',
                frame : false,
                bodyStyle : 'padding:5 5 0',
                id : 'editCodeFormPanel',
                name : 'editCodeFormPanel',
                items : [
                    {
                        fieldLabel : '�����ֶ�',
                        name : 'field',
                        anchor : '100%',
                        maxLength:15,
                        allowBlank : false
                    },
                    {
                        fieldLabel : '�ֶ���',
                        name : 'fieldname',
                        anchor : '100%',
                        maxLength:20,
                        allowBlank : false
                    },
                    {
                        xtype : 'textfield',
                        fieldLabel : '����',
                        name : 'code',
                        anchor : '100%',
                        maxLength:20,
                        allowBlank : false
                    },
                    {
                        fieldLabel : '��������',
                        name : 'codedesc',
                        anchor : '100%',
                        maxLength:50,
                        allowBlank : false
                    },
                    enabledCombo_E,
                    editmodeCombo_E,
                    {
                        fieldLabel : '��ע',
                        name : 'remark',
                        anchor : '100%',
                        maxLength:200,
                        allowBlank : true
                    },
                    {
                        fieldLabel : '������',
                        name : 'codeid',
                        anchor : '100%',
                        hidden : true,
                        hideLabel : true
                    }
                ]
            });

    editCodeWindow = new Ext.Window({
                layout : 'fit',
                width : 300,
                height : 260,
                resizable : false,
                draggable : true,
                closeAction : 'hide',
                title : '<span style="font-weight:normal">�޸��ֵ�</span>',
                modal : true,
                collapsible : true,
                titleCollapse : true,
                maximizable : false,
                buttonAlign : 'right',
                border : false,
                animCollapse : true,
                animateTarget : Ext.getBody(),
                constrain : true,
                items : [ editCodeFormPanel ],
                buttons : [
                    {
                        text : '����',
                        iconCls : 'acceptIcon',
                        handler : function() {
                            if (runMode == '0') {
                                Ext.Msg.alert('��ʾ',
                                        'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
                                return;
                            }
                            updateCodeItem();
                        }
                    },
                    {
                        text : '�ر�',
                        iconCls : 'deleteIcon',
                        handler : function() {
                            editCodeWindow.hide();
                        }
                    }
                ]

            });
    /**
     * ����
     */
    var viewport = new Ext.Viewport({
                layout : 'border',
                items : [ grid ]
            });

    /**
     * ��ʼ�������޸ĳ���
     */
    function ininEditCodeWindow() {
        var record = grid.getSelectionModel().getSelected();
        if (Ext.isEmpty(record)) {
            Ext.Msg.alert('��ʾ', '����ѡ��Ҫ�޸ĵ���Ŀ');
        }
        record = grid.getSelectionModel().getSelected();
        if (record.get('editmode') == '0') {
            Ext.Msg.alert('��ʾ', '��ѡ�еļ�¼Ϊϵͳ���õĴ������,�������޸�');
            return;
        }
        editCodeWindow.show();
        editCodeFormPanel.getForm().loadRecord(record);
    }

    /**
     * �޸��ֵ�
     */
    function updateCodeItem() {
        if (!editCodeFormPanel.form.isValid()) {
            return;
        }
        editCodeFormPanel.form.submit({
                    url : './resource.ered?reqCode=updateCodeItem',
                    waitTitle : '��ʾ',
                    method : 'POST',
                    waitMsg : '���ڴ�������,���Ժ�...',
                    success : function(form, action) {
                        editCodeWindow.hide();
                        store.reload();
                        synMemory('�ֵ��޸ĳɹ�,Ҫ���������ڴ�ͬ����');
                    },
                    failure : function(form, action) {
                        var msg = action.result.msg;
                        Ext.MessageBox.alert('��ʾ', '������ձ���ʧ��:<br>' + msg);
                    }
                });
    }

    /**
     * �ڴ�ͬ��
     */
    function synMemory(msg, flag) {
        Ext.Msg.confirm('��ȷ��', msg, function(btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                            url : 'resource.ered?reqCode=synMemory',
                            success : function(response) {
                                if (flag == '1') {
                                    store.reload();
                                }
                                Ext.Msg.alert('��ʾ', '�ڴ�ͬ���ɹ�');
                            },
                            failure : function(response) {
                                Ext.Msg.alert('��ʾ', '�ڴ�ͬ��ʧ��');
                            }
                        });
            }
        });
    }

    /**
     * ɾ���������
     */
    function deleteCodeItems() {
        if (runMode == '0') {
            Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
            return;
        }
        var rows = grid.getSelectionModel().getSelections();
        var fields = '';
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].get('editmode') == '0') {
                fields = fields + rows[i].get('fieldname') + '->'
                        + rows[i].get('codedesc') + '<br>';
            }
        }
        if (fields != '') {
            Ext.Msg
                    .alert(
                    '��ʾ',
                    '<b>��ѡ�е���Ŀ�а�������ϵͳ���õ�ֻ����Ŀ</b><br>' + fields + '<font color=red>ֻ����Ŀ����ɾ��!</font>');
            return;
        }
        if (Ext.isEmpty(rows)) {
            Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
            return;
        }
        var strChecked = jsArray2JsString(rows, 'codeid');
        Ext.Msg.confirm('��ȷ��', '�����Ҫɾ���ֵ���?', function(btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                            url : './resource.ered?reqCode=deleteCodeItem',
                            success : function(response) {
                                store.reload();
                                synMemory('�ֵ�ɾ���ɹ�,Ҫ���������ڴ�ͬ����');
                            },
                            failure : function(response) {
                                var resultArray = Ext.util.JSON
                                        .decode(response.responseText);
                                Ext.Msg.alert('��ʾ', resultArray.msg);
                            },
                            params : {
                                strChecked : strChecked
                            }
                        });
            }
        });
    }

    /**
     * ����������ѯ�ֵ�
     */
    function queryCodeItem() {
        store.load({
                    params : {
                        start : 0,
                        limit : bbar.pageSize,
                        queryParam : Ext.getCmp('queryParam').getValue()
                    }
                });
    }

    /**
     * ˢ���ֵ�
     */
    function refreshCodeTable() {
        store.load({
                    params : {
                        start : 0,
                        limit : bbar.pageSize
                    }
                });
    }
});
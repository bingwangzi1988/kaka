Ext.ux.TabCloseMenu = Ext.extend(Object, {

            constructor: function(config) {
                config = config || {};
                Ext.apply(this, config);

            },

            init : function(tp) {
                tp.on('contextmenu', this.onContextMenu);
                if (this.dbClickHandler)  {
                    tp.on('dblclick', this.onDbClick, this);
                    }
            },
            onDbClick: function (ts, item, e) {
                this.dbClickHandler.call(this);
            },

            onContextMenu:function (ts, item, e) {
                if (!this.menu) {
                    this.menu = new Ext.menu.Menu([
                        {
                            id: ts.id + '-close',
                            text: '�رյ�ǰ��ǩҳ',
                            iconCls:'applicationIcon',
                            handler : function() {
                                ts.remove(item);
                            }
                        },
                        {
                            id: ts.id + '-close-others',
                            text: '�ر�������ǩҳ',
                            iconCls:'application_doubleIcon',
                            handler : function() {
                                ts.items.each(function(item) {
                                    if (item.closable && item != this.ctxItem) {
                                        ts.remove(item);
                                    }
                                });
                            }
                        },
                        {
                            id: ts.id + '-close-all',
                            text: '�ر�ȫ����ǩҳ',
                            iconCls:'application_cascadeIcon',
                            handler : function() {
                                ts.items.each(function(item) {
                                    if (item.closable) {
                                        ts.remove(item);
                                    }
                                });
                            }
                        },
                        '-',
                        {
                            id: ts.id + '-cancel',
                            text: 'ȡ��',
                            iconCls:'tbar_synchronizeIcon',
                            handler : function() {
                                this.menu.hide();
                            }
                        }
                    ]);
                }
//        this.ctxItem = item;
                var items = this.menu.items;
                items.get(ts.id + '-close').setDisabled(!item.closable);
                var disableOthers = true;
                ts.items.each(function() {
                    if (this != item && this.closable) {
                        disableOthers = false;
                        return false;
                    }
                });
                items.get(ts.id + '-close-others').setDisabled(disableOthers);
                var disableAll = true;
                ts.items.each(function() {
                    if (this.closable) {
                        disableAll = false;
                        return false;
                    }
                });
                items.get(ts.id + '-close-all').setDisabled(disableAll);
                this.menu.showAt(e.getPoint());
            }
        });
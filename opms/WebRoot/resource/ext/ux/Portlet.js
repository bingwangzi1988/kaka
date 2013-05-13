/*!
 * Ext JS Library 3.3.1
 * Copyright(c) 2006-2010 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */
Ext.ux.Portlet = Ext.extend(Ext.Panel, {
            anchor : '100%',
            frame : true,
            collapsible : true,
            draggable : true,
            cls : 'x-portlet'

            ,onRender : function(ct, component) {
                Ext.ux.Portlet.superclass.onRender.call(this, ct, component);
                var element = this;
                var portlet_resizer = new Ext.Resizable(this.id, {
                            handles : 's',
                            pinned : false
                        });
                portlet_resizer.on("resize", function(resizer, width, height, event) {
                    Ext.Ajax.request({
                                url : _contextPath_ + '/PortalActionresize.action',
                                failure : function(response) {
                                    Ext.Msg.alert('ÌבÊ¾', response.responseText);
                                },
                                params:{menuid:element.id.replace("_for_portal", ""),height:height}
                            });
                    element.setHeight(height);
                });
                if (this.dblclickHandler) {
                    element.header.on("dblclick", function() {
                        this.dblclickHandler.call(this,this);
                    },this);
                }
            }
        });

Ext.reg('portlet', Ext.ux.Portlet);

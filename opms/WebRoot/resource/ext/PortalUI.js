/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-23
 * Time: ÉÏÎç11:01
 * To change this template use File | Settings | File Templates.
 */


function getPortalUI(style) {

    var items;
    switch (style) {
        case '33_33_33':
            items =[
                new Ext.ux.PortalColumn({
                            columnWidth:.33,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.33,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.33,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        })
            ];
        break;
        case '50_50':
            items =[
                new Ext.ux.PortalColumn({
                            columnWidth:.50,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.50,
                            style:'padding:10px 10px 10px 10px',
                            items:[

                            ]
                        })
            ];
        break;
        case '67_33':
            items =[
                new Ext.ux.PortalColumn({
                            columnWidth:.67,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.33,
                            style:'padding:10px 10px 10px 10px',
                            items:[

                            ]
                        })
            ];
        break;
        case '33_67':
            items =[
                new Ext.ux.PortalColumn({
                            columnWidth:.33,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.67,
                            style:'padding:10px 10px 10px 10px',
                            items:[

                            ]
                        })
            ];
        break;
        case '25_25_25_25':
            items =[
                new Ext.ux.PortalColumn({
                            columnWidth:.25,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.25,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.25,
                            style:'padding:10px 0 10px 10px',
                            items:[

                            ]
                        }),
                new Ext.ux.PortalColumn({
                            columnWidth:.25,
                            style:'padding:10px 10px 10px 10px',
                            items:[

                            ]
                        })
            ];
    }

    return items;
}


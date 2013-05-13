/**
 * viewport����Ӧ����ʵ��
 * 
 * @author XiongChun
 * @since 2010-11-27
 */
Ext.onReady(function() {
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [{
									region : 'north',
									split : true,
									title : 'north',
									collapsible : true,
									height : 80,
									html : '����north����'
								}, {
									region : 'south',
									split : true,
									title : 'south',
									collapsible : true,
									height : 50,
									html : '����south����'
								}, {
									region : 'west',
									split : true,
									title : 'west',
									collapsible : true,
									width:150,
									html : '����west����'
								}, {
									region : 'east',
									split : true,
									title : 'east',
									collapsible : true,
									width:100,
									html : '����east����'
								}, {
									region : 'center',
									html : '����center����'
								}]
					});
		});
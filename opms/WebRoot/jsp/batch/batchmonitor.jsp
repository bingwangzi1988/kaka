<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/include/includeHead.jsp" %>
<script type="text/javascript" src="<%=_contextPath_%>/resource/ext/ux/ux-all.js"></script>
<script type="text/javascript" src="<%=_contextPath_%>/jsp/batch/batchmonitor.js"></script>  
<script type="text/javascript" src="<%=_contextPath_%>/dwr/engine.js"></script> 
<script type="text/javascript" src="<%=_contextPath_%>/dwr/util.js"></script> 
<style type="text/css">
        #out {
            padding: 5px;
            overflow:auto;
            border-width:0;
        }
        #out b {
            color:#555;
        }
        #out xmp {
            margin: 5px;
        }
        #out p {
            margin:0;
        }
	.x-grid-back-red { 
		background: red; color: white;
	}
	.x-grid-back-yellow { 
		background: yellow; color: red;
	}
	.x-grid-back-green { 
		background: green; color: yellow;
	} 
    </style>
</head> 
	<body onload="dwr.engine.setActiveReverseAjax(true);"> 
	</body> 
</html>
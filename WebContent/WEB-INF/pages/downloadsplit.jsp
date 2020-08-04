<%@ include file="header.jsp" %>
<html>
<head>
	<link rel="apple-touch-icon" href="img/app-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="img/favicons-pdf/favicon-32x32.png">
	<link href="dist/css/web.b49cf26.css" rel="stylesheet">
	<link href="dist/css/editpdf.b49cf26.css" rel="stylesheet">

</head>
<body>

	<div class="main">
    <div class="tool">
	 	<div class="tool__workarea" id="workArea">
			<div class="tool__header">
            	<h1 class="tool__header__title">PDF Editor</h1>
            	<h2 class="tool__header__subtitle">${ response }</h2>
        	</div>
       		<div id="uploader" class="uploader">
    			<form action="downloadsplit" method="post">
					<input type="hidden" name="name" value=${ name }>
					<input type="submit" STYLE="background-color:#A9A9A9" value="click here for download zip">
				</form>
    		</div>
	</div>
	</div>
	</div>
</body>
</html>
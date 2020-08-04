<%@ include file="header.jsp" %>
<html>
	<link rel="apple-touch-icon" href="img/app-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="img/favicons-pdf/favicon-32x32.png">
	<link href="dist/css/web.b49cf26.css" rel="stylesheet">
	<link href="dist/css/editpdf.b49cf26.css" rel="stylesheet">

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="main">
    <div class="tool">
	 	<div class="tool__workarea" id="workArea">
			<div id="dropArea">
			</div>
        	<div class="tool__header">
            	<h1 class="tool__header__title">PDF Editor</h1>
            	<h2 class="tool__header__subtitle">Edit PDF by adding text, shapes, comments and highlights.</h2>
        	</div>
        	<div id="uploader" class="uploader">
    				<form action="unlockpdf" method="post" enctype="multipart/form-data">
						<input type="password" name="userpass" placeholder="enter user password"><br><br>
						<input type="file" name="userfiles" placeholder="upload a file"><br><br>
						<button >submit</button>
					</form>	
    		</div>
    		<h2 class="tool__header__subtitle">${ invalid }</h2>
    	 </div>
   	</div>


</div>	


</body>
</html>
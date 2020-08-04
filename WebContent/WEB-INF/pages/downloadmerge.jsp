<%@ include file="header.jsp" %>
<html lang="en">
<head>
<link rel="apple-touch-icon" href="img/app-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="img/favicons-pdf/favicon-32x32.png">
	<link href="dist/css/web.b49cf26.css" rel="stylesheet">
	<link href="dist/css/editpdf.b49cf26.css" rel="stylesheet">

</head>
<body class="tool-editpdf  lang-en-US">
<div class="main">
    <div class="tool">
	 	<div class="tool__workarea" id="workArea">
			<div class="tool__header">
            	<h1 class="tool__header__title">PDF Editor</h1>
            	<h2 class="tool__header__subtitle">${ response }</h2>
        	</div>
       		<div id="uploader" class="uploader">
    			<form action="download" method="post">
					<input type="hidden" name="name" value=${ name }>
					<input type="submit" STYLE="background-color:#A9A9A9" value="click here for download pdf">
				</form>
    		</div>
    		<div class="tool__header">
            	<h2 class="tool__header__subtitle">Get Edited Pdf On Your Email</h2>
        	</div>
    		<div id="uploader" class="uploader">
    			<form action="sendmail" method="post">
					<input type="hidden" name="name" value=${ name }>
					<input type="email" name="email" placeholder="enter your email">
					<br><br>
					<input type="submit" STYLE="background-color:#A9A9A9" value="Click Here">
				</form>
    		</div>
    		<h2 class="tool__header__subtitle">${ res }</h2>
  		</div>
	</div>
</div>
	
</body>
</html>
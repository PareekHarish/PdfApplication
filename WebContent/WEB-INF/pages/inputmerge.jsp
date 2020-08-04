<%@ include file="header.jsp" %>
<html lang="en">
<head>
    <link rel="apple-touch-icon" href="img/app-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="img/favicons-pdf/favicon-32x32.png">
	<link href="dist/css/web.b49cf26.css" rel="stylesheet">
	<link href="dist/css/editpdf.b49cf26.css" rel="stylesheet"></head>

<body class="tool-editpdf  lang-en-US">
<div class="main">
    
<div class="tool">
	 <div class="tool__workarea" id="workArea">
		
        <div class="tool__header">
            <h1 class="tool__header__title">PDF Editor</h1>
            <h2 class="tool__header__subtitle">Edit PDF by adding text, shapes, comments and highlights.</h2>
        </div>
        
        <div id="uploader" class="uploader">
    		<form action="mergepdf" method="post" enctype="multipart/form-data">
        		<input type="file" name="userfiles"></input><br><br>
        		<input type="file" name="userfiles"></input><br><br>
        		<button>submit</button>
    		</form>
    	</div>
    	<h2 class="tool__header__subtitle">${ invalid }</h2>
   </div>
</div>
 </div>
</html>
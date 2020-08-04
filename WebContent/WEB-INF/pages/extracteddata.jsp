<%@ include file="header.jsp" %>
<html>
<head>
	<link rel="apple-touch-icon" href="img/app-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="img/favicons-pdf/favicon-32x32.png">
	<link href="dist/css/web.b49cf26.css" rel="stylesheet">
	<link href="dist/css/editpdf.b49cf26.css" rel="stylesheet"></head>
</head>
<body>
<h2 class="tool__header__subtitle">Edit PDF Data</h2><br><br>
<%= request.getAttribute("data") %>
<input type="text" value=${ data }><br>
<textarea>${ data }</textarea>
<form action="downloadedited" method="post" >
		<input type="hidden" value=${ data } name="name" ><br><br>
		<button>submit</button>
</form>	
</body>
</html>
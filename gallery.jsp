<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<link href="gallery.css" rel="stylesheet" type="text/css">
	<title>yoUpload - This is your own, personal space</title>
</head>

<body>

	<h1>yoUpload! - Personal Gallery</h1>

	<h2>Click on any of your images to edit it</h2>

	<ul>
	<%String[] imgstream = (String []) request.getAttribute("imstream");
	  for(int i = 0; i < imgstream.length; i++) {
		  out.println("<li><a href= 'http://83.212.101.28:8080/youpload/edit.jsp?name=" + imgstream[i] + "'><img src = 'images/" + imgstream[i] +"'width = '200'></a><div>"+ imgstream[i].substring(0,imgstream[i].length()-4) +"</div></li>");
	  }
	%>
	</ul>

	<a href='http://83.212.101.28:8080/youpload/index.htm'>Return to Homepage</a>
</body>
</html>
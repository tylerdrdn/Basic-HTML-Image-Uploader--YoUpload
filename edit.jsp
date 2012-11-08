<%String name = request.getParameter("name");
  String justname = name.substring(0,name.length()-4);
  String extension = name.substring(name.length()-4,name.length());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<link href="edit.css" rel="stylesheet" type="text/css">
	<title>yoUpload - Now yoU can modify your images</title>
</head>

<body>
<h1>yoUpload! - Image Editor</h1>
<form enctype="multipart/form-data" action=/youpload/save method="post">
<br/>

<h2>Change Name</h2>
<div id="nm">
<input id="chgname" type=text name="chgname" value="<%=justname%>"readonly/>
<input type=text  id="extension" name="extension" value ="<%=extension%>" readonly/>
<br/>
</div>

<h2>Resizing</h2>
<div id="wd">
<h3>Width:</h3><input id="imgwidth" type=text name="imgwidth" />
<input type=button id="imgwidth" value="Resize" onclick="Resize()" name="imgwidth" />
<br/>
</div>

<div id="ht">
<h3>Height:</h3><input id="imgheight" type=text name="imgheight" readonly/>
</div>
<br/>

<h2>Effects</h2>
<br/>
<div id="gs">
<h3>Grayscale</h3>
<input id="grayscale" type=text name="grayscale"/>
<input type=button id="grayscale" value="Apply" onclick="Grayscale()" name="grayscale" />
</div>

<div id="opa">
<h3>Opacity</h3>
<input id="opacity" type=text name="opacity"/>
<input type=button id="opacity" value="Apply" onclick="Opacity()" name="opacity" />
</div>
<br/>

<h2>Caption</h2>
<br/>
<div id="ca">
<textarea type=input style="resize:none;" id="caption" name="caption" cols=30 rows=10 /></textarea>
<input type=submit id="Save" value="Save Changes" name="Save" />
</div>

<h2>Image Preview:</h2>
<br/>

<div id="newimage">
<img id="modified" src="images/<%=name%>"/>
</div>

<a href='http://83.212.101.28:8080/youpload/index.htm'>Return to Homepage</a>

</form>

<script type="text/javascript">

  d=document.URL;
  d=d.substring(d.lastIndexOf("=")+1);
  a =d.length;
  d=d.substring(0,a-4);

  xmlhttp=new XMLHttpRequest();
  xmlhttp.open("GET","images/" + d+".xml",false);
  xmlhttp.send();
  xmlDoc=xmlhttp.responseXML;

  j = xmlDoc.getElementsByTagName("IMAGE");
  name =(j[0].getElementsByTagName("NAME")[0].childNodes[0].nodeValue);
  height =(j[0].getElementsByTagName("HEIGHT")[0].childNodes[0].nodeValue);
  width =(j[0].getElementsByTagName("WIDTH")[0].childNodes[0].nodeValue);
  gray =(j[0].getElementsByTagName("GRAYSCALE")[0].childNodes[0].nodeValue);
  op =(j[0].getElementsByTagName("OPACITY")[0].childNodes[0].nodeValue);
  capt =(j[0].getElementsByTagName("ZANDA")[0].childNodes[0].nodeValue);
  
	document.getElementById("imgheight").value = height;
    document.getElementById("imgwidth").value = width;
	document.getElementById("grayscale").value = gray;
	document.getElementById("opacity").value = op;
	document.getElementById("caption").value = capt;



  function Resize() {
	var img = document.getElementById("modified");
	var rewidth = document.getElementById("imgwidth").value;
	var reheight = document.getElementById("imgheight").value;
	var f = height / width;
	document.getElementById("imgheight").value = rewidth * f;
	img.width = rewidth;
	img.height = rewidth * f;
  }

function Grayscale() {
	var value = document.getElementById("grayscale").value;
    document.getElementById("modified").setAttribute("style","-webkit-filter:grayscale("+value+"%);");
document.getElementById("imgwidth").value = width;
}

function Opacity() {
	var value = document.getElementById("opacity").value;
    document.getElementById("modified").setAttribute("style","opacity:."+value+";");
}

</script>
</body>
</html>
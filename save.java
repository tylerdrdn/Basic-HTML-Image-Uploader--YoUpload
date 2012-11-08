import java.io.*;
import java.util.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image.*;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


public class save extends HttpServlet {
   
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 500 * 1024;
	private int maxMemSize = 400 * 1024;
	private File file ;
	private String fileName1;

/* Get the location that the file will be stored */

	public void init( ){
		filePath = getServletContext().getInitParameter("file-upload"); 
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException {
		java.io.PrintWriter out = response.getWriter( );
		out.println("<html>");

/* Check if there is a pending request */

		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");

      	if( !isMultipart ){

			out.println("<html>");
        	out.println("<head>");
         	out.println("<title>Servlet change</title>");  
         	out.println("</head>");
         	out.println("<body>");
         	out.println("<p>No file changed</p>"); 
         	out.println("</body>");
         	out.println("</html>");
			return;
      	}

		DiskFileItemFactory factory = new DiskFileItemFactory();

/* maximum size that will be stored in memory */

		factory.setSizeThreshold(maxMemSize);

/* Location to save data that is larger than maxMemSize */

		factory.setRepository(new File("/usr/share/apache-tomcat-7.0.32/webapps/test/data/"));

/* Create a new file upload handler */

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax( maxFileSize );

		try{

/* Parse the request to get file items */
			List fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();

			String[] savexml=new String[20];
			String name="";
			String extension="";
			String width="";
			String height="";
			String gray="";
			String op="";
			String cap="";
			BufferedImage image = null;

			int j=0;

			while ( i.hasNext () ) {

				FileItem fi = (FileItem)i.next();
				savexml[j] = fi.getString();
				j++;
			}

			name=savexml[0];
			extension=savexml[1];
			width=savexml[2];
			height=savexml[3];
			gray=savexml[4];
			op=savexml[5];
			cap=savexml[6];

			File fXmlFile = new File("/usr/share/apache-tomcat-7.0.32/webapps/youpload/images/"+name+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("IMAGE");
			Node m = k.item(0);
			Element element = (Element) m;
			NodeList nodelist;
			Element element1;
			NodeList fstNm;

			if(!height.equals("")) {
				nodelist = element.getElementsByTagName("HEIGHT");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				(fstNm.item(0)).setNodeValue(height);
			}

			if(!width.equals("")) {
				nodelist = element.getElementsByTagName("WIDTH");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				(fstNm.item(0)).setNodeValue(width);
			}

			if(!gray.equals("")) {
				nodelist = element.getElementsByTagName("GRAYSCALE");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				(fstNm.item(0)).setNodeValue(gray);
			}

			if(!op.equals("")) {
				nodelist = element.getElementsByTagName("OPACITY");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				(fstNm.item(0)).setNodeValue(op);
			}

			if(!cap.equals("")) {

				nodelist = element.getElementsByTagName("ZANDA");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				(fstNm.item(0)).setNodeValue(cap);

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result =  new StreamResult("/usr/share/apache-tomcat-7.0.32/webapps/youpload/images/"+name+".xml");
			transformer.transform(source, result);

			response.sendRedirect("edit.jsp?name="+savexml[0]+savexml[1]);

		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException {
		throw new ServletException("GET method used with " + getClass( ).getName( )+": POST method required.");
	} 
}
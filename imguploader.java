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


public class imguploader extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 500 * 1024;
   private int maxMemSize = 400 * 1024;
   private File file ;
   private String fileName1;


 	public void init( ) {													// Get the location where the file will be stored
      filePath = getServletContext().getInitParameter("img-upload");
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException {
		java.io.PrintWriter out = response.getWriter( );
 		out.println("<html>");

/*Check that there is a request to upload*/

	isMultipart = ServletFileUpload.isMultipartContent(request);
	response.setContentType("text/html");

	if( !isMultipart ){

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet upload</title>");  
		out.println("</head>");
		out.println("<body>");
		out.println("<p>No file uploaded</p>"); 
		out.println("</body>");
		out.println("</html>");
		return;
	}

	DiskFileItemFactory factory = new DiskFileItemFactory();

	factory.setSizeThreshold(maxMemSize);									// maximum size of memory that can be stored

	factory.setRepository(new File("/usr/share/apache-tomcat-7.0.32/webapps/youpload/images"));	// Location where to save data larger than maxMemSize

/* Create a new file upload handler */

	ServletFileUpload upload = new ServletFileUpload(factory);

	upload.setSizeMax( maxFileSize );										// maximum size of the file to be uploaded

	try{ 
/* Parse the request to get file items */

		List fileItems = upload.parseRequest(request);

/* Process the uploaded file items */

		Iterator i = fileItems.iterator();

      	out.println("<html>");
      	out.println("<head>");
      	out.println("<title>Servlet upload</title>"); 
		out.println("</head>");
		out.println("<body>");

		String imgsource = "";
    	String imgname = "";
		String caption = "";

		BufferedImage image = null;

    	while ( i.hasNext () ) {
        	FileItem fi = (FileItem)i.next();

			if (fi.isFormField ()){ caption = fi.getString(); }

        	if ( !fi.isFormField () ) {

/* Get the uploaded file parameters */

            	String fieldName = fi.getFieldName();
            	String fileName = fi.getName();
            	String contentType = fi.getContentType();
            	boolean isInMemory = fi.isInMemory();
            	long sizeInBytes = fi.getSize();

/* Write the file */            

				file = new File( filePath + fileName);
				fi.write( file ) ;
            	out.println("Uploaded Filename:  " + fileName + "<br><br>");
				out.println("<img src='images/" + fileName + "'/>");

				imgname = fileName.substring(0,fileName.length()-4);
				imgsource = fileName;
         	}
      	}
		out.println("<br/><br/><a href='http://83.212.101.28:8080/youpload/index.htm'>Return to Homepage</a>");

/**************************************************************************************************/

	if(caption.equals("")) {caption = "Write your description here...";}


	File f,fim;
	fim = new File(filePath+imgsource);
	image =  ImageIO.read(fim);
	f=new File(filePath+imgname+".xml");

	if(!f.exists()){
		f.createNewFile();

		try{
			FileWriter fstream = new FileWriter(filePath+imgname+".xml");
			BufferedWriter output = new BufferedWriter(fstream);

			output.write("<?xml version='1.0' encoding='ISO-8859-1'?>\n"
             +"\n"
		     +"<XML>\n"
		     +"\n"
		     +"<IMAGE>\n"
		     +"\n"
		     +"	<NAME>"+imgname+"</NAME>\n"
		     +"	<WIDTH>"+image.getWidth()+"</WIDTH>\n"
		     +"	<HEIGHT>"+image.getHeight()+"</HEIGHT>\n"
		     +"	<GRAYSCALE>0</GRAYSCALE>\n"
		     +"	<OPACITY>0</OPACITY>\n"
		     +"	<ZANDA>"+caption+"</ZANDA>\n"
		     +"\n"
		     +"</IMAGE>\n"
		     +"\n"
		     +"</XML>");
		output.close();

		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());							//Catch exception -if any
  		}
	}

	response.sendRedirect("/test/index");

   	}catch(Exception ex) {
       System.out.println(ex);
   	}
	}


	public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, java.io.IOException {
        	throw new ServletException("GET method used with " +getClass( ).getName( )+": POST method required.");
		}

}
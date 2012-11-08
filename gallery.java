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

public class gallery extends HttpServlet {

	static final String[] EXTENSIONS = new String[]{
		"gif", "png", "bmp","jpg"
    };

	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
    	public boolean accept(final File dir, final String name) {
	    	for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	static final File dir = new File("/usr/share/apache-tomcat-7.0.32/webapps/youpload/images");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	int j = 0;
	String[] imstream;

	imstream = new String[dir.listFiles().length / 2];				//stores the divided number of the files located in this directory by 2, since we have +1 file (.xml) for every image we upload

	if (dir.isDirectory()) { 
		for (final File f : dir.listFiles(IMAGE_FILTER)) {
			BufferedImage newimg = null;

			try {
				newimg = ImageIO.read(f);

				imstream[j] = f.getName();
				j++;
			}
			catch (final IOException e) {
			}
		}
	}

	request.setAttribute("imstream", imstream);

	try {   
		request.getRequestDispatcher("/gallery.jsp").forward(request, response);
	}
	catch (ServletException ex) {}
	catch (IOException ex) {}      
   }
}
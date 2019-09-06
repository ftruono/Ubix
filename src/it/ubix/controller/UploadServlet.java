package it.ubix.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.ubix.model.Catalogo;
import it.ubix.model.Film;
import it.ubix.model.Product;
import it.ubix.model.Serie;
import it.ubix.model.Utente;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Utente ut;
    private final static String IMAGE_DIR="image" + File.separator;  //si suppone che esiste sempre
    private final static String RESOURCE="resource" + File.separator;
    private final static String FILM_DIR=RESOURCE + "film" + File.separator; //si suppone che esiste gi�
    private final static String SERIE_DIR=RESOURCE + "serie" + File.separator;
    private String context_path;
    private Catalogo cat;
    
    public UploadServlet() {
        super();
        ut=null;
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  ut=(Utente)request.getSession().getAttribute("user");
	  context_path=this.getServletContext().getRealPath("");
	  cat=new Catalogo(context_path);
	  if(ut!=null && ut.getLvl()>=2) {
		if(request.getParameter("admin")!=null) {
			   Product temp=null;
			   String src="";
			   int type=Integer.parseInt(request.getParameter("type"));
			   String nome=(String)request.getParameter("nome");
			   String cast=(String)request.getParameter("cast");
			   String desc=(String)request.getParameter("descrizione");
			   int sellable=Integer.parseInt(request.getParameter("sellable"));
			   int hot=Integer.parseInt(request.getParameter("hot"));
			   float price=0;
			   if(sellable==1)
			      price=Float.parseFloat(request.getParameter("price"));
			   Part image=request.getPart("preview_img"); //per supposto la cartella image esiste sempre
			   String image_name=extractFileName(image);
			   if(image_name.contains(".jpg")|| image_name.contains(".png") || image_name.contains(".bmp")) {
				   try {
				     writeFile(image,context_path + IMAGE_DIR , image_name);
				   }catch(IOException e) {
					     e.printStackTrace();
				   }
			   }else {
				   response.sendError(406);
				   return;
			   }
			   Part file=request.getPart("src");
			   if(type==0) { //film
				   temp=new Film();
				   if(file==null) { response.sendError(406); return ;  }
				   src=extractFileName(file);
				   String ext=src.substring(src.indexOf(".")+1, src.length());
				   if(validVideoAndUpload(file, src, ext)) 
					   temp.setSrc(context_path,src);
				   else {
					   response.sendError(406); //TODO : da fare realmente
					   return ;
				   }					   
			   } else { //serie
				   temp=new Serie();
				   if(file==null) { response.sendError(406); return ;  }
				   src=extractFileName(file);
				   String ext=src.substring(src.indexOf(".")+1, src.length());
				   String nameDirectory=src.substring(0,src.indexOf("."));
				   File f=new File(context_path + SERIE_DIR + nameDirectory);
				   f.mkdirs();
				   if(ext.equals("zip")) {
					   if(!Unzip(file,context_path,nameDirectory)) {
						   recursiveDelete(f);
						   response.sendError(406);
						   return ;
					   }
					   temp.setSrc(context_path, nameDirectory);
				   }
				   
				   
			   }
			   try {
				makeProduct(temp, nome, desc, cast, image_name, type, sellable==1, hot==1, price);
				cat.addProduct(temp);
				response.sendRedirect("admin.jsp?do=carica&message=ok");
			} catch (SQLException e) {
				
			}
			  
			
			   
		   }
	 }
 }

	


private void makeProduct(Product temp,String nome,String descrizione,String cast,String img,int type,boolean sellable,boolean hot,float price) {
	 temp.setNome(nome);
	 temp.setDescrizione(descrizione);
	 temp.setCast(cast);
	 temp.setImg(img);
	 temp.setType(type);
	 temp.setSellable(sellable);
	 temp.setHot(hot);
	 temp.setPrice(price);
	 
	 
}
	
 private synchronized void writeFile(Part file,String path,String name) throws IOException {
	 BufferedInputStream reader=new BufferedInputStream(file.getInputStream());
	 BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(path + name));
	 byte b[]=new byte[8192];
	 while(reader.read(b)>0) {
		 out.write(b);
	 }
	 out.flush();
	 out.close();
	 reader.close();
	 
	 
 }
 
public void recursiveDelete(File f){
     if(f.isDirectory()){
       if(f.list().length>0)
         for (File c : f.listFiles())
              recursiveDelete(c);
     }
     f.delete();
     
     
 }
 
 
private boolean validVideoAndUpload(Part video ,String src,String ext) {
	if(ext.equals("mp4")) {
		   try {
			     writeFile(video,context_path + FILM_DIR, src);
		   }catch(IOException e) {
				     
	     }
     return true;
   }else
	 return false;
	
}

private synchronized boolean Unzip(Part file,String context,String src) throws IOException {
   boolean valid=false;
	byte buffer[]=new byte[8192];
	ZipInputStream zis = new ZipInputStream(file.getInputStream());
    ZipEntry zipEntry = zis.getNextEntry();
    while(zipEntry != null){
        String entryName = zipEntry.getName();
        if(entryName.equals("serie.xml"))
        	valid=true;
        File newFile = new File(context + SERIE_DIR + src + File.separator + entryName );
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        zipEntry = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();
    return valid;
}

 private String extractFileName(Part part) {
		//content-disposition: form-data; name="file"; filename="file.txt"
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
 }
	
	
}

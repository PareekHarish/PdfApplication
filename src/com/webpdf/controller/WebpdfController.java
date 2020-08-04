package com.webpdf.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.webpdf.model.MultiPartModel;
import com.webpdf.model.WebpdfModel;

@Controller
public class WebpdfController {
	@RequestMapping(value="/")
	String m1() throws SQLException
	{
		return "pdfoperations";
	}
	
	@RequestMapping(value="/header")
	String m2() throws SQLException
	{
		return "pdfoperations";
	}
	
	@RequestMapping(value="/merge")
	String insertId(WebpdfModel u) throws SQLException
	{
		return "inputmerge";
	}
	
	

	
	private ArrayList<java.lang.String> uploadPdfOnServer(MultiPartModel user) throws IOException 
	{
		ArrayList<String> list=new ArrayList<String>(); 
		for(MultipartFile filedata:user.getUserfiles())
		{
			String rootdirectory="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files";
			File f=new File(rootdirectory);
			String filename=filedata.getOriginalFilename();
			if(filename!=null && filename.length()>0)
			{
				String filepath=f.getCanonicalPath()+File.separator+filename;
				
				try(BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath)))
				{
					bos.write(filedata.getBytes());
					list.add(filepath);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return list;
	}


	@RequestMapping(value="/mergepdf",method=RequestMethod.POST)
	public String pdfMerge(HttpServletRequest req,HttpServletResponse res,MultiPartModel user) throws IOException,SQLException,FileNotFoundException
	{	
		ArrayList<String> filepath=uploadPdfOnServer(user);
		int i=0;
		for(String s:filepath)
		{
			if(s!=null && s.endsWith(".pdf"))
			{
				i++;
			}
			else
			{
				req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
				return "inputmerge";
			}
		}
		if(i<2)
		{
			req.setAttribute("invalid","Please Enter Both Pdf Files");
			return "inputmerge";
		}
		if(filepath.isEmpty()==false)
		{
			Random rnd = new Random();
		    int number = rnd.nextInt(999999);
			String s2="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files\\"+"merged"+number+".pdf";
			PDFMergerUtility pdf=new PDFMergerUtility();
			pdf.setDestinationFileName(s2);
			for(String s:filepath)
			{
				try
				{
					File f=new File(s);
					if(f.exists())
					{
						pdf.addSource(f);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			pdf.mergeDocuments(null);
			req.setAttribute("name", "C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files\\"+"merged"+number+".pdf");
			req.setAttribute("response","Pdf has been Successfully Merged");
			return "downloadmerge";
		}
		req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
		return "inputmerge";
			
	}
	

	@RequestMapping(value="/download",method=RequestMethod.POST)
	public void Download(HttpServletRequest req,HttpServletResponse res)throws IOException
	{
			String mimetype=null;
			String file=req.getParameter("name");
			File f=new File(file);
			mimetype =getmimetype(f.getCanonicalPath());
			res.setContentType(mimetype);
			res.setContentLength((int)f.length());
			res.setHeader("Content-Disposition","attachment;filename=\""+f.getName()+"\"");
			InputStream is=new FileInputStream(f);
			ServletOutputStream out=res.getOutputStream();
			IOUtils.copy(is, out);
			res.getOutputStream().flush();
			res.getOutputStream().close();
			is.close();
			out.flush();
			out.close();
	}


	private String getmimetype(String canonicalPath) {
		canonicalPath=canonicalPath.toLowerCase();
		if(canonicalPath.endsWith(".jpg")||canonicalPath.endsWith(".jpeg"))
			return "image/jpeg";
		else
			return "application/pdf";
	}
	
	
	@RequestMapping(value="/sendmail", method=RequestMethod.POST)
	public String email(HttpServletRequest req,MultiPartModel user) throws AddressException, MessagingException, IOException
	{
		Properties p=new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.EnableSSL.enable", "true");
		p.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(p, new SimpleAuthenticator("pareekharish23@gmail.com","Hp524675@"));
		Message msg =new MimeMessage(session);
		msg.setFrom(new InternetAddress("pareekharish23@gmail.com"));
		msg.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
		msg.setSubject("I Love Pdf application");
		String file=req.getParameter("name");
		
		Multipart emailcontent= new MimeMultipart();
		
		MimeBodyPart textbodypart=new MimeBodyPart();
		textbodypart.setText("Edited Pdf ");
		
		MimeBodyPart pdfattachment=new MimeBodyPart();
		pdfattachment.attachFile(file);
		
		emailcontent.addBodyPart(textbodypart);
		emailcontent.addBodyPart(pdfattachment);
		msg.setContent(emailcontent);
		Transport.send(msg);
		req.setAttribute("res","File has been Successfully Send On Your E-mail");
		return "downloadmerge";
	}
	
	
	@RequestMapping("/split")
	public String split(HttpServletRequest req)
	{
		req.setAttribute("input","splitpdf");
		return "inputsplit";
	}
	
	@RequestMapping("/splitpdf")
	public String splitPdf(HttpServletRequest req,MultiPartModel user) throws IOException
	{
		String s=uploadPdfOnServer((user.getUserfiles())[0]);
		if(s!=null && s.endsWith(".pdf"))
		{
			int i=1;
			File f=new File(s);
			PDDocument pd=PDDocument.load(f);
			Splitter sp=new Splitter();
			List<PDDocument> pd1=sp.split(pd);
			Iterator<PDDocument> it=pd1.listIterator();
			ArrayList<String> filepaths= new ArrayList<String>();
			while(it.hasNext())
			{
				String s1="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files\\"+"split"+i+".pdf";
				PDDocument pd2=it.next();
				pd2.save(s1);
				filepaths.add(s1);
				i++;
				pd2.close();
			}
			String s2="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files\\splitterzip.zip";
			zipfiles(filepaths,req,s2);
			pd.close();
			req.setAttribute("response","Pdf has been Successfully Splitted and Put into a Zip File");
			return "downloadsplit";
		}
		else
		{
			req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
			req.setAttribute("input","splitpdf");
			return "inputsplit";
		}
	}
	
	private void zipfiles(ArrayList<String> filepaths,HttpServletRequest req,String zipfilename) throws IOException {
		FileOutputStream fos=new FileOutputStream(zipfilename);
		ZipOutputStream zos=new ZipOutputStream(fos);
		for(String s:filepaths) 
		{
			zos.putNextEntry(new ZipEntry(new File(s).getName()));
			byte[] bytes=Files.readAllBytes(Paths.get(s));
			zos.write(bytes);
			zos.closeEntry();
		}
		req.setAttribute("name",zipfilename);
		zos.close();
	}

	private String uploadPdfOnServer(MultipartFile filedata) throws IOException 
	{	
			String rootdirectory="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files";
			File f=new File(rootdirectory);
			String filename=filedata.getOriginalFilename();
			if(filename!=null && filename.length()>0)
			{
				String s=f.getCanonicalPath()+File.separator+filename;
				String filepath = s.replaceAll("\\s","");
				try(BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath)))
				{
					bos.write(filedata.getBytes());
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				return filepath;
			}
		return null;
	}

	@RequestMapping(value="/downloadsplit",method=RequestMethod.POST)
	public void downloadSplit(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		String mimetype;
		String filepath=req.getParameter("name");
		File f =new File(filepath);
		mimetype=getmimetype(f.getCanonicalPath());
		res.setContentType(mimetype);
		res.setContentLength((int) f.length());
		res.setHeader("Content-Disposition","attachment;filename=\""+f.getName()+"\"");
		InputStream is=new FileInputStream(f);
		ServletOutputStream out=res.getOutputStream();
		IOUtils.copy(is, out);
		is.close();
		out.flush();
		out.close();
		
	}
	
	
	@RequestMapping(value="edit")
	public String edit(HttpServletRequest req)
	{
		req.setAttribute("input","editpdf");
		return "inputsplit";
	}
	
	@RequestMapping("editpdf")
	public String editPdf(HttpServletRequest req,MultiPartModel user) throws IOException
	{
		String s=uploadPdfOnServer((user.getUserfiles())[0]);
		if(s!=null && s.endsWith(".pdf"))
		{
			File file =new File(s);
			PDDocument pd=PDDocument.load(file);
			PDFTextStripper pdf=new PDFTextStripper();
			String s1=pdf.getText(pd);
			req.setAttribute("data",s1);
			pd.close();
			return "extracteddata";
		}
		else
		{
			req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
			return "inputsplit";
		}
	}
	
	
	@RequestMapping(value="convert")
	public String pdfToJpeg(HttpServletRequest req)
	{
		req.setAttribute("input","pdftojpeg");
		return "inputsplit";
	}
	
	@RequestMapping(value="pdftojpeg")
	public String pdfToJpeg(MultiPartModel user,HttpServletRequest req) throws IOException
	{
		String s=uploadPdfOnServer(user.getUserfiles()[0]);
		if(s!=null && s.endsWith(".pdf"))
		{
			File file=new File(s);
			PDDocument pd=PDDocument.load(file);
			int n=pd.getNumberOfPages();
			PDFRenderer rd=new PDFRenderer(pd);
			ArrayList<String> list=new ArrayList<String>();
			int i=0;
			while(i<n)
			{
				BufferedImage img=rd.renderImage(i);
				String s1="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files\\img"+i+".jpg";
				ImageIO.write(img, "jpg",new File(s1) );
				list.add(s1);
				i++;
			}
			String zipfilename="C:\\Users\\acer\\eclipse-workspace\\webpdf\\WebContent\\files\\pdftojpegzip.zip";
			zipfiles(list,req,zipfilename);
			req.setAttribute("response","Pdf has been Successfully Converted ito Jpeg and Put into a Zip File");
			pd.close();
			return "downloadsplit";
		}
		else
		{
			req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
			return "inputsplit";
		}
	}
	
	@RequestMapping("remove")
	public String remove()
	{
		return "inputremove";
	}
	
	@RequestMapping("removepage")
	public String removePage(HttpServletRequest req,MultiPartModel user) throws IOException
	{
		String s=uploadPdfOnServer((user.getUserfiles())[0]);
		if(s!=null && s.endsWith(".pdf"))
		{
			File file=new File(s);
			PDDocument pd=PDDocument.load(file);
			
			int totalpage=pd.getNumberOfPages();
			try
			{
				int i=Integer.parseInt(user.getPageno());
				if(totalpage>=i)
				{
					pd.removePage(i-1);
					pd.save(file);
				}
				else
				{
					req.setAttribute("invalid","Invalid Page No");
					return "inputremove";
				}
			}
			catch(Exception e) 
			{
				req.setAttribute("invalid","Invalid Page No");
				return "inputremove";
			}
			finally
			{
				pd.close();
			}
			req.setAttribute("name",s);
			req.setAttribute("response","Given Page is Successfully Removed from Pdf");
			return "downloadmerge";
		}
		else
		{
			req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
			return "inputremove";
		}
	}
	
	
	@RequestMapping("protect")
	public String protect(HttpServletRequest req)
	{
		return "inputprotect";
	}
	
	
	@RequestMapping(value="/protectpdf",method=RequestMethod.POST)
	public String protectPdf(HttpServletRequest req,MultiPartModel user) throws IOException
	{
		String s=uploadPdfOnServer((user.getUserfiles())[0]);
		if(s!=null &&s.endsWith(".pdf"))
		{
			File file=new File(s);
			PDDocument pd=PDDocument.load(file);
			AccessPermission ap=new AccessPermission();
			StandardProtectionPolicy policy=new StandardProtectionPolicy(user.getUserpass(),user.getAdminpass(),ap);
			policy.setEncryptionKeyLength(256);
			policy.setPermissions(ap);
			pd.protect(policy);
			pd.save(s);
			pd.close();
			req.setAttribute("response","Pdf has been Successfully Protected");
			req.setAttribute("name",s);
			return "downloadmerge";
		}
		else
		{
			req.setAttribute("invalid","Invalid File Only Pdf File is Allowed");
			return "inputprotect";
		}
	}

	@RequestMapping(value="unlock")
	public String unlock()
	{
		return "unlockinput";
	}
	
	@RequestMapping(value="unlockpdf")
	public String unlockpdf(HttpServletRequest req,MultiPartModel user) throws IOException
	{
		
		String filepath=uploadPdfOnServer((user.getUserfiles())[0]);
		if(filepath!=null && filepath.endsWith(".pdf"))
		{
			try
			{
				File f=new File(filepath);
				PDDocument pd=PDDocument.load(f,user.getUserpass());
				pd.setAllSecurityToBeRemoved(true);
				pd.save(f);
				pd.close();
			}
			catch(InvalidPasswordException e)
			{
				req.setAttribute("invalid","Invalid password");
				return "unlockinput";
			}
			req.setAttribute("name",filepath);
			req.setAttribute("response","Pdf has been Successfully Unlocked");
			return "downloadmerge";
		}
		else
		{
			req.setAttribute("invalid", "Invalid File Only Pdf File is Allowed");
			return "unlockinput";
		}
	}
	
	
}

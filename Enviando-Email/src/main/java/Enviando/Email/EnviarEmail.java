package Enviando.Email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;

public class EnviarEmail{
	
	private String userName = "luanteste269@gmail.com";
	private String senha ="luan4203";
	private String listDest = "";
	private String nameRemet = "";
	private String assunto = "";
	private String txtEmail="";
	
	
	
	public EnviarEmail (String nameRemet,String listDest,String assunto,String txtEmail){
		this.nameRemet = nameRemet;
		this.listDest = listDest;
		this.assunto = assunto;
		this.txtEmail = txtEmail;
		
	}
	
	
	
	public void EnviaEmail(boolean html) throws Exception {
		/*olhe as configuraçoes do smtp do seu email*/
		
	
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");//autorazição
		properties.put("mail.smtp.starttls", "true");//Autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");//servidor do google
		properties.put("mail.smtp.port", "465");// porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465");//expecifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//Classe socket de conexão ao smtp
		
		Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
		     @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		    	return new PasswordAuthentication(userName, senha);//email que sera utilizado para enviar
		    }
		});
		
		Address[] toUser = InternetAddress.parse(listDest);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName,nameRemet));/*quem esta enviando*/
        message.setRecipients(Message.RecipientType.TO, toUser);/*email ou emails de destino*/
        message.setSubject(assunto);  /*Assuto do email*/
        
        /*Parte 1 do email*/
        MimeBodyPart corpoEmail = new MimeBodyPart();        
        if(html){
        	corpoEmail.setContent(txtEmail,"text/html; charset=utf-8");
        }else {
        	corpoEmail.setText(txtEmail);
        }
        
        
        
        List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
        arquivos.add(simuladordePDF());
        arquivos.add(simuladordePDF());
        arquivos.add(simuladordePDF());
        arquivos.add(simuladordePDF());
        
        
        Multipart multi = new MimeMultipart();
        multi.addBodyPart(corpoEmail);
       
        int index= 0;
        for(FileInputStream arquivo :arquivos) {
        	   MimeBodyPart anexo = new MimeBodyPart();
               anexo.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladordePDF(),"application/pdf")));
               anexo.setFileName("anexo"+index+".pdf");
               multi.addBodyPart(anexo);
               index++;
        }

        
        
       
        
        message.setContent(multi);
        
        Transport.send(message);
        
        /*Caso o email não esteje sendoenviado coloque um thread sleep para não derrubar o o envio domailcmofim da execução*/
        //Thread.sleep(1000);
		
       	
	}
	
	
	//simulando um pdf para simular um envio de um email com anexo
	//mas pode se colocar um endereço de um documento para enviar 
	private FileInputStream simuladordePDF() throws Exception{
       	
		   Document document = new Document();
		   File file = new File("anexo.pdf");
		   file.createNewFile();
		   PdfWriter.getInstance(document, new FileOutputStream(file));
		   document.open();
		   document.add(new Paragraph("Conteudo do pdf, com esse texto e do pdf"));
		   document.close();
		   return new FileInputStream(file);
       	
       }
       
	

}



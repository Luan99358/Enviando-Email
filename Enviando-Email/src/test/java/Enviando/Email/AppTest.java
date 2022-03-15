package Enviando.Email;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest {
	

	@org.junit.Test
	public void testeEmail() throws Exception {
		
		StringBuilder strTxt= new StringBuilder();
	    strTxt.append(" Olá , </br>");
		strTxt.append("<h3>Vocé esta recebendo um aceeso ao curso de java</h3> </br>");
	    strTxt.append("para ter acesso clique no botão abaixo</br></br>");
	    strTxt.append("<a target=\"_blank\" href=\"https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aula?codigoCurso=1\" style=\"color:#2525a7; padding:14px 25px; text-aling:center; text-decoration:none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border: 3px solid green\"> Acessar Portal</a> ");
		
		EnviarEmail envioEmail = new EnviarEmail("Luan","luanteste269@gmail.com,luan99358@gmail.com","Treinamento em Java Mail",strTxt.toString());
	
		envioEmail.EnviaEmail(true);
	}
	
    
}

package com.centurylink.mail.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.centurylink.mail.datamodel.MailInfo;

public class ReadingEmail {
	String host = null;
	String mailId = null;
	String password= null; 
   	Collection<MailInfo> mailInfCollection = null;
   	
   	
	public ReadingEmail() {
		super();
	}
	
	public ReadingEmail(String host, String mailId, String password) {
		super();
		this.host = host;
		this.mailId = mailId;
		this.password = password;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<MailInfo> readMail() {
		Properties props = new Properties();
	    props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imaps.host", host);
		props.put("mail.imaps.port", 143);
	        
		try {
	         Session session = Session.getInstance(props, null);
	         Store store = session.getStore();
	         store.connect(host, mailId, password);
	         Folder inbox = store.getFolder("INBOX");
	         inbox.open(Folder.READ_WRITE);
	         Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
	         mailInfCollection = new HashSet<MailInfo>();
	         MailInfo mailInfo = null;
	         String url =null;
	         for (int i = 0; i < messages.length; i++) {
	        	 Message msg = messages[i];
		         Address[] from = msg.getFrom();
		         StringBuffer stringBuffer = new StringBuffer();
		         url = null;
		         for (Address address : from) {
		        	 stringBuffer.append(address.toString());
		         }
		         
		         url = getURL(msg.getSubject());
		         if( url != null) {
		        	 mailInfo = new MailInfo();
			         mailInfo.setSender(stringBuffer.toString());
			         mailInfo.setRecievedDate(msg.getReceivedDate());
		        	 mailInfo.setSubject(url);
		        	 mailInfCollection.add(mailInfo);
		         } else if(msg instanceof MimeMessage) {
		             MimeMessage m = (MimeMessage)msg;
		             Object contentObject = m.getContent();
		             String result = null;
		             if(contentObject instanceof Multipart)
		             {
		                 Multipart content = (Multipart)contentObject;
		                 int count = content.getCount(); 
		                 
		                 for(int j=0; j<count; j++)
		                 {
		                     BodyPart part =  content.getBodyPart(j);
		                     if(part.isMimeType("text/*")) {
		                    	 result = (String)part.getContent();
		                     }
		                 }

		             } else if (contentObject instanceof String) {
		                 result = (String)contentObject;
		             }
		             
		             Document doc = Jsoup.parse(result);
		             url = getURL(doc.text());
		             if( url != null) {
			        	 mailInfo = new MailInfo();
				         mailInfo.setSender(stringBuffer.toString());
				         mailInfo.setRecievedDate(msg.getReceivedDate());
			        	 mailInfo.setSubject(url);
			        	 mailInfCollection.add(mailInfo);
			         }
		         }
		         
	         }
	         inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
	        } catch (Exception mex) {
	            mex.printStackTrace();
	        }
		return mailInfCollection;
	}
	
	//checks for the URL in the given String
	private String getURL(String s) {
		if(s != null) {
			//Separate input by spaces ( URLs don't have spaces )
	        String [] parts = s.split("\\s");
	        
	        for(String inputUrl : parts){
				try {
					URL url = new URL(inputUrl);
					if(url != null) {
						url.openConnection();
						return inputUrl;
					}
				} catch (MalformedURLException e) {
					
				} catch (IOException e) {
					
				}
	    	    
	        }
		}
		System.out.println(s+" : dont have any Valid URLs/Links.");
       return null;
	}

}
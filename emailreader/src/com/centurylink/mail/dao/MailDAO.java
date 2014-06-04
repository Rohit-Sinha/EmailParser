package com.centurylink.mail.dao;

import java.sql.*;
import java.util.Collection;

import com.centurylink.mail.datamodel.MailInfo;

public class MailDAO {
	
   public static void insertData(Collection<MailInfo> mailCollection) throws Exception {
	   
	   Class.forName("com.mysql.jdbc.Driver");  
       Connection connection = DriverManager.getConnection(  
               "jdbc:mysql://localhost:3306/mail_info", "root", "rohit");
       for(MailInfo mailInfo : mailCollection) {
    	   java.sql.Timestamp sqlDate = new java.sql.Timestamp(mailInfo.getRecievedDate().getTime());
    	   System.out.println(sqlDate);
           
           String insertQuery="insert into mail_info1(sender,subject,date) values (?,?,?)";
           PreparedStatement statement = (PreparedStatement) connection.prepareStatement(insertQuery);
           
           statement.setString(1,mailInfo.getSender());
           statement.setString(2, mailInfo.getSubject());
         statement.setTimestamp(3, sqlDate);
          
           statement.executeUpdate();
           statement.close();   
       }
   }
}

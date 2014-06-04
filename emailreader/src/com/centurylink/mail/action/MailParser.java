package com.centurylink.mail.action;

import java.util.Collection;

import com.centurylink.mail.dao.MailDAO;
import com.centurylink.mail.datamodel.MailInfo;

public class MailParser {
	private static ReadingEmail readingEmail = new ReadingEmail("cas-odc.ctl.intranet", "userid", "password");
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void mailing() throws Exception {
		Collection<MailInfo> mailCollection=readingEmail.readMail();
		if(mailCollection != null){
			for(MailInfo mailInfo : mailCollection) {
				System.out.println(mailInfo.toString());
			}
			MailDAO.insertData(mailCollection);
		}
	}

}

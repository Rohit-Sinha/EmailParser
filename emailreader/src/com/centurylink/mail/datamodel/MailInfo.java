package com.centurylink.mail.datamodel;

import java.util.Date;

public class MailInfo {
	String sender;
	String subject;
	Date recievedDate;
	
	
	public MailInfo() {
		super();
	}

	public MailInfo(String sender, String subject,
			Date recievedDate) {
		super();
		this.sender = sender;
		this.subject = subject;
		this.recievedDate = recievedDate;
	}

	@Override
	public String toString() {
		return "MailInfo [sender=" + sender + ", subject=" + subject
				+ ", recievedDate=" + recievedDate + "]";
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(Date recievedDate) {
		this.recievedDate = recievedDate;
	}
	
}

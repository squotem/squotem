package com.leanlogistics.squotem.webapp.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.User;

@Service("mailService")
public class QuoteMailSender {

    @Autowired
    private MailSender mailSender; // for some reason that only spring knows, it is not instantiated
    
    public QuoteMailSender() {
		super();
	}

    public QuoteMailSender(MailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	//
    //  This method will send compose and send the message
    //
    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // for html: http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/mail.html
    public void sendQuoteNotification(List<User> to, Quote q)
    {
    	//notification type 1 = notify on baseline approval, 2 = notify on business plan approval, 3 = notify on all approval
    	// notify to sales director ... I guess
    			
    	/*String body = "<!DOCTYPE html>"
    			    + "<html>"
    	            + "<body>"
                    + "<p>Quote <a href='http://localhost:8080/QuoterApp/businessPlan.htm?q="+q.getId().toString()+"'>"+q.getQuoteNumber()+"."+q.getRevisionNumber()+"</a> has been approved</p>"
                    + "</body>"
                    + "</html>";*/
    	if (to != null)
    	{
	    	String body = null;
	    	String subjet = "Quote " + q.getQuoteNumber() + " - revision " + q.getRevisionNumber() + " has been approved.";
	    	if (q.getStatus().equals(QuoteWorkflowStatusCO.SUBMITTED))
	    		body = "Qualification has been approved and is now in Business Plan Review.";
	    	else
	    		if (q.getStatus().equals(QuoteWorkflowStatusCO.APPROVED))
	    			body = "Business Plan Review has been approved and is now in Business Plan Approved.";
	    		else
	    			body = "Has been approved.";
	    	
	    	for (User u : to)
	    	{
	    		if (u.geteMail() != null)
	    			sendMail(u.geteMail(), subjet, body);
	    	}
    	}
    }

}

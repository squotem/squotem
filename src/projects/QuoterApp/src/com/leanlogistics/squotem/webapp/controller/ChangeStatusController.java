package com.leanlogistics.squotem.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.exceptions.QuoteEditException;
import com.leanlogistics.squotem.exceptions.QuoterStatusChangeException;
import com.leanlogistics.squotem.model.Quote;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.SquotemService;
import com.leanlogistics.squotem.webapp.screenobjects.CreateRevisionResponse;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteAddCommentResponse;
import com.leanlogistics.squotem.webapp.screenobjects.QuoteStatusChangeResponse;
import com.leanlogistics.squotem.webapp.util.QuoteMailSender;

// TODO: Refactor, should we use a better name for this?
@Controller
public class ChangeStatusController {
    
    
    public ChangeStatusController() {}
    
    @Autowired
    private SquotemService service;

    @Autowired
    private MailSender mailSender;
    
    public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SquotemService getService() {
        return service;
    }

    public void setService(SquotemService service) {
        this.service = service;
    }
    

    @RequestMapping("submitQuote.htm")
    protected ModelAndView submitQuoteForReview(HttpSession session,HttpServletRequest req) {
        // Check if we already have a logged in user
        ModelAndView modelAndView = new ModelAndView();

        User u = (User) session.getAttribute(EntryController.USER_KEY);
        if (u == null) {
            modelAndView.setViewName("redirect:login.htm");
            return modelAndView;
        }   

        long qNum = 0;
        String qStr = req.getParameter("q");
        if (qStr != null) {
            try {
                qNum = Long.valueOf(qStr);                
            }
            catch (NumberFormatException e) {}
        }
        if (qNum == 0) {
            modelAndView.setViewName("generalError");
            return modelAndView;
        }
        
        Quote q = null;
        
        try {
            q = service.submitQuoteForReview(u, qNum);
            
            String comment = "Submitted for review";
            
        	if (q.getStatus().equals(QuoteWorkflowStatusCO.SUBMITTED))
        		comment = "Status Qualification has been approved and is now in Business Plan Review.";
        	else
        		if (q.getStatus().equals(QuoteWorkflowStatusCO.APPROVED))
        			comment = "Status Business Plan Review has been approved and is now in Business Plan Approved.";

            q = service.saveNoteForQuote(u, qNum, comment);
            
            if (q.getSalesDirector().geteMail() != null)
            {
            	QuoteMailSender mail = new QuoteMailSender(mailSender);
            	mail.sendQuoteNotification(service.getNotificationList(q.getStatus()), q);
            }

            modelAndView.addObject("quote", q);
            modelAndView.setViewName("submitSuccess");
        }
        catch (QuoterStatusChangeException e) {
            modelAndView.addObject("generalMessage", e.getMessage());
            modelAndView.addObject("detailMessages", e.getDetailMesssages());
            modelAndView.setViewName("submitFail");
        }
        catch (QuoteEditException e) {
            modelAndView.addObject("generalMessage", e.getMessage());
            modelAndView.addObject("detailMessages", "saving comment error");
            modelAndView.setViewName("submitFail");
        }
        
        return modelAndView;        
        
    }
    
    @RequestMapping("submitQuote.json")
    public @ResponseBody QuoteStatusChangeResponse submitQuoteAjaxForReview(HttpSession session,HttpServletRequest req) {
        // Check if we already have a logged in user
        QuoteStatusChangeResponse result = new QuoteStatusChangeResponse();

        User u = (User) session.getAttribute(EntryController.USER_KEY);
        if (u == null) {
            result.setSuccess(false);
            result.setErrorMessage("Session expired");
            return result;
        }   

        long qNum = 0;
        String qStr = req.getParameter("q");
        if (qStr != null) {
            try {
                qNum = Long.valueOf(qStr);                
            }
            catch (NumberFormatException e) {}
        }
        if (qNum == 0) {
            result.setSuccess(false);
            result.setErrorMessage("Invalid parameters");
            return result;            
        }
        
        Quote q = null;
        
        try {
            q = service.submitQuoteForReview(u, qNum);
            result.setSuccess(true);
        }
        catch (QuoterStatusChangeException e) {
        }

        if (q == null) {
            result.setSuccess(false);
            result.setErrorMessage("Invalid parameters");
            return result;                        
        }
        
        return result;        
        
    }
    
    @RequestMapping(value = "/changeStatus.json")
    public @ResponseBody QuoteStatusChangeResponse changeStatusForQuote(HttpSession session, HttpServletRequest req) {
        QuoteStatusChangeResponse result = new QuoteStatusChangeResponse();
        
        
        User u = (User) session.getAttribute(EntryController.USER_KEY);
        if (u == null) {
            result.setSuccess(false);
            result.setErrorMessage("Session expired");
            return result;
        }
        
        long qNum = 0;
        String qStr = req.getParameter("q");
        if (qStr != null) {
            try {
                qNum = Long.valueOf(qStr);                
            }
            catch (NumberFormatException e) {}
        }
        if (qNum == 0) {
            result.setSuccess(false);
            result.setErrorMessage("Invalid parameters");
            return result;            
        }
        
        QuoteWorkflowStatusCO newStatus = null;
        String newStatusString = req.getParameter("st");
        if (newStatusString != null) {
            try {                
                newStatus = QuoteWorkflowStatusCO.valueOf(newStatusString);            
            }
            catch (IllegalArgumentException e) {};
        }
        
        if (newStatus == null) {
            result.setSuccess(false);
            result.setErrorMessage("Invalid parameters");
            return result;                        
        }
        
        // Optional comment...
        String comment = req.getParameter("comment");
        try {
            service.updateQuoteStatus(u, qNum, newStatus, comment);
            result.setSuccess(true);
            
            Quote q = service.getQuote(u, qNum);
            if (q.getSalesDirector().geteMail() != null)
            {
            	QuoteMailSender mail = new QuoteMailSender(mailSender);
            	mail.sendQuoteNotification(service.getNotificationList(q.getStatus()), q);
            }

        }
        catch (QuoterStatusChangeException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        }        
        return result;
        
    }
    
    @RequestMapping(value = "/createRevision.json")
    public @ResponseBody CreateRevisionResponse createRevisionForQuote(HttpSession session, HttpServletRequest req) {
        CreateRevisionResponse result = new CreateRevisionResponse();
        
        
        User u = (User) session.getAttribute(EntryController.USER_KEY);
        if (u == null) {
            result.setSuccess(false);
            result.setErrorMessage("Session expired");
            return result;
        }
        
        long qNum = 0;
        String qStr = req.getParameter("q");
        if (qStr != null) {
            try {
                qNum = Long.valueOf(qStr);                
            }
            catch (NumberFormatException e) {}
        }
        if (qNum == 0) {
            result.setSuccess(false);
            result.setErrorMessage("Invalid parameters");
            return result;            
        }
        
        
        try {
            Quote q = service.createRevision(u, qNum);           
            result.setSuccess(true);
            result.setNewQuoteId(q.getId());
        }
        catch (QuoterStatusChangeException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        } 
        
        return result;
        
    }
    
    @RequestMapping(value = "/addComment.json")
    public @ResponseBody QuoteAddCommentResponse addCommentForQuote(HttpSession session, HttpServletRequest req) {
        QuoteAddCommentResponse result = new QuoteAddCommentResponse();
        
        
        User u = (User) session.getAttribute(EntryController.USER_KEY);
        if (u == null) {
            result.setSuccess(false);
            result.setErrorMessage("Session expired");
            return result;
        }
        
        long qNum = 0;
        String qStr = req.getParameter("q");
        if (qStr != null) {
            try {
                qNum = Long.valueOf(qStr);                
            }
            catch (NumberFormatException e) {}
        }
        if (qNum == 0) {
            result.setSuccess(false);
            result.setErrorMessage("Invalid parameters");
            return result;            
        }
        
        String comment = req.getParameter("comment");
        try {
            service.saveNoteForQuote(u, qNum, comment);
            result.setSuccess(true);
        }
        catch (QuoteEditException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        }        
        return result;
        
    }
    
        
}

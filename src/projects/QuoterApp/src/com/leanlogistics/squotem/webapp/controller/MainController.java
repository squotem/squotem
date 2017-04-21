package com.leanlogistics.squotem.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.model.QuoteQuery;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.SquotemService;


@Controller
public class MainController {
    
    @Autowired
    private SquotemService service;

    
    public SquotemService getService() {
        return service;
    }

    public void setService(SquotemService service) {
        this.service = service;
    }

    public MainController() {
        
    }
    
    @RequestMapping("main.htm")
    protected ModelAndView showMain(HttpSession session) {
        // Check if we already have a logged in user
        ModelAndView modelAndView = new ModelAndView();

        User u = (User) session.getAttribute("user");
        if (u != null) {
            // Start with a fresh quote query
        	QuoteQuery quoteQuery = new QuoteQuery();
        	quoteQuery.setOnlyActive(true);
        	//quoteQuery.setStatus("DRAFT");
        	//quoteQuery.setOwnerId(u.getId());
        	
        	//quoteQuery.setSalesDirector(salesDirector);
        	
            modelAndView.addObject("quoteQuery", quoteQuery);
            addDataToModel(modelAndView);
            // Add default list of quotes to show
            modelAndView.addObject("quotes", service.getDraftQuotesForUser(u));
            modelAndView.setViewName("main");            
        }
        else {
            modelAndView.setViewName("redirect:login.htm");
        }        
        return modelAndView;        
    }
    
    @RequestMapping("quoteQuery.htm")
    private ModelAndView processQuoteQuery(@ModelAttribute QuoteQuery query, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User u = (User) session.getAttribute("user");
        // Set user id data in query
        query.setQueryUser(u);
        
        // set proper hours for date ranges
        Date fromDate = query.getFrom();
        if (fromDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND , 0);
            query.setFrom(cal.getTime());            
        }
        Date toDate = query.getTo();
        if (toDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            query.setTo(cal.getTime());            
        }
        
        modelAndView.addObject("quoteQuery", query);
        modelAndView.addObject("quotes", service.queryQuotes(query));
        
        addDataToModel(modelAndView);
        modelAndView.setViewName("main");
        return modelAndView;
    }   
    
    
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }    
    
    /* Add objects required in model */
    protected void addDataToModel(ModelAndView modelAndView) {
        // Workflow status
        modelAndView.addObject("quoteStatuses", QuoteWorkflowStatusCO.values());
        // Customers list
        modelAndView.addObject("customers", service.getCustomers());  
        // Product categories
        modelAndView.addObject("productCategories", service.getProductCategories());
        // Users list
        modelAndView.addObject("users", service.getUsers());
        
    }
  
}

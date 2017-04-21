package com.leanlogistics.squotem.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leanlogistics.squotem.service.SquotemService;

@Controller
public class SuccessController {

    public SuccessController() {};
    
	// probably service is not needed
	@Autowired
    private SquotemService service;

    public SquotemService getService() {
        return service;
    }

    public void setService(SquotemService service) {
        this.service = service;
    }
    

    @RequestMapping("success.htm")
    protected ModelAndView showSuccess(HttpSession session,HttpServletRequest req) {
        // Check if we already have a logged in user
        ModelAndView modelAndView = new ModelAndView();
        
       	modelAndView.addObject("mainMessage", req.getParameter("m"));

        modelAndView.setViewName("submitSuccess");
        return modelAndView;        
        
    }
    

}

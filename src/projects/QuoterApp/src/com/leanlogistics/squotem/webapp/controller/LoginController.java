package com.leanlogistics.squotem.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.SquotemService;
import com.leanlogistics.squotem.webapp.screenobjects.Login;

@SessionAttributes("user") 
@Controller
public class LoginController {

    @Autowired
    private SquotemService service;

    public LoginController() {
    }

    public SquotemService getService() {
        return service;
    }

    public void setService(SquotemService service) {
        this.service = service;
    }
    
    
    @RequestMapping(value="login.htm",method=RequestMethod.POST)
    protected ModelAndView login(@Valid @ModelAttribute("commandLogin") Login loginData, BindingResult result) {
        ModelAndView modelAndView  = new ModelAndView();
        User usr = null;
        
        if (! result.hasErrors()) {
            usr = service.getUser(loginData.getUserName(), loginData.getUserPassword());
            if (usr != null) {
                modelAndView.addObject("user", usr);            
            }
            else {
                result.rejectValue("userName", "error.invalidUser", "Invalid User");
            }
        }
        
        
        if (!result.hasErrors()) {
            // User gets saved in session because of the annotation above the class declaration
            modelAndView.setViewName("redirect:main.htm");
        }
        else {
            modelAndView.addObject("commandLogin", loginData);
            modelAndView.setViewName("login");
        }
        
        return modelAndView;        
    }
    
    @RequestMapping(value="login.htm",method=RequestMethod.GET)
    protected ModelAndView showForm() {
        return new ModelAndView("login", "commandLogin", new Login());
    }
    
    @RequestMapping("logout.htm")            
    protected ModelAndView logout(HttpSession session, Model model) {
        session.invalidate();
        model.asMap().clear();
        return new ModelAndView("redirect:login.htm");
    }
    
    
    
}

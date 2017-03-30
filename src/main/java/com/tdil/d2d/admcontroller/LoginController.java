package com.tdil.d2d.admcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.model.LoginBean;


@Controller
public class LoginController {
    
    
    @RequestMapping("login")
    public ModelAndView getLogin() {
        ModelAndView mv = new ModelAndView();
        LoginBean loginBean = new LoginBean();
        loginBean.setUserName("esa");
        mv.addObject("loginObject",loginBean);
        mv.setViewName("login");
        
        return mv;
    }
    
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView setLogin(@ModelAttribute("loginObject") LoginBean loginBean,BindingResult bindingResult, ModelMap model ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        
        System.out.println("Usuario: " + loginBean.getUserName());
        
        mv.addObject("vamo","esa");
        return mv;
    }
    
}
    

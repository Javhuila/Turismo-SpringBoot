package com.product.controller;

import com.product.model.ContactDtls;
import com.product.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeContoller {

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/about2")
    public String acerca(){

        return "about";
    }

    @GetMapping("/contactenos2")
    public String contacto(){

        return "contact-us";
    }

    @GetMapping("/tipografia2")
    public String tipografia(){

        return "typography";
    }

    @GetMapping("/terminos")
    public String termino(){

        return "term_condi";
    }

    @GetMapping("/col-country")
    public String colCountry(){

        return "colombia";
    }

    @PostMapping("/createComm")
    public String createcomm(@ModelAttribute ContactDtls contuser, HttpSession session){

        /*System.out.println(contuser);*/

        ContactDtls contactDtls = contactService.createComm(contuser);

        if(contactDtls != null){
            session.setAttribute("msg", "Mensaje enviado");
            /*System.out.println("Mensaje enviado");*/
        }else {
            session.setAttribute("msg", "Ha ocurrido un error, intente lo nuevamente");
            /*System.out.println("error");*/
        }

        return "redirect:/contactenos2";
    }

}

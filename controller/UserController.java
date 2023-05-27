package com.product.controller;

import com.product.model.UserDtls;
import com.product.repository.UserRepository;
import com.product.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute
    private  void userDetails(Model m, Principal p) {

        if (p != null){
            String email = p.getName();
            UserDtls user = userRepo.findByEmail(email);

            m.addAttribute("user", user);
        }
    }

    @GetMapping("/inicio")
    public String inicio(){
        return "inicio";
    }

    @GetMapping("/registro")
    public String registro (){
        return "registro";
    }

    @GetMapping("/logintur")
    public String login (){
        return "login";
    }

    @PostMapping("/createUser")
    public String createuser(@ModelAttribute UserDtls user, HttpSession session, HttpServletRequest request){

        String url = request.getRequestURL().toString();
        /*url = "http://localhost:8080/createUser" */

        url = url.replace(request.getServletPath(), "");

        /*System.out.println(user);*/
        
        Boolean f = userService.checkEmail(user.getEmail());

        if (f){
            /*System.out.println("El correo ya está en uso");*/
            session.setAttribute("msg", "El correo ya está en uso");
        }else {
            UserDtls userDtls = userService.createUser(user, url);
            if (userDtls !=null){
                /*System.out.println("Registrado exitosamente");*/
                session.setAttribute("msg", "Registrado exitosamente");
            }else {
                /*System.out.println("Error absoluto");*/
                session.setAttribute("msg", "Ha ocurrido un error!!!");
            }
        }

        return "redirect:/registro";
    }

    @GetMapping("/verify")
    public String verificaCuenta(@Param("code") String code){

        if (userService.verificaCuenta(code)){
            return "verifica_exitosa";
        }else {
            return "verifica_fallida";
        }

    }

    @GetMapping("/loadRecoveContra")
    public String loadRecoveContra(){
        return "recove_contra";
    }

    @GetMapping("/loadResetContra/{id}")
    public String loadResetContra(@PathVariable int id){

        /*System.out.println(id);*/

        return "reset_contra";
    }

    @PostMapping("/recoveContra")
    public String contraOlvidada(@RequestParam String email, @RequestParam String telefono, HttpSession session){

        UserDtls user = userRepo.findByEmailAndAndTelefono(email, telefono);

        if (user != null) {
            return "redirect:/loadResetContra/" + user.getId();
        }else  {
            session.setAttribute("msg", "Correo o teléfono inválidos");
            return "recove_contra";
        }

    }

    @PostMapping("/changeContra")
    public String resetContra(@RequestParam String nwcntr, @RequestParam Integer id, HttpSession session){

        UserDtls user = userRepo.findById(id).get();

        String encryptCntr = passwordEncoder.encode(nwcntr);
        user.setPassword(encryptCntr);

        UserDtls updateUser = userRepo.save(user);

        if (updateUser != null){
            session.setAttribute("msg", "La contraseña ha sido cambiada");
        }

        return "redirect:/loadRecoveContra";
    }
}

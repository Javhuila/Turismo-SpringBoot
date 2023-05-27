package com.product.controller;

import com.product.entity.Boleto;
import com.product.model.UserDtls;
import com.product.repository.BoletoRepository;
import com.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class ControlController {

    @Autowired
    private BoletoRepository boletoRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute
    private  void userDetails(Model m, Principal p) {
        String email = p.getName();
        UserDtls user = userRepo.findByEmail(email);

        m.addAttribute("user", user);
    }

    @GetMapping("/")
    public String homeUsu(){
        return "user/home";
    }

    @GetMapping("/load_form")
    public String loadForm(){
        return "user/add";
    }

    @GetMapping("/listboletos/{id}")
    public String listBoletos(@ModelAttribute Boleto boleto ,@PathVariable("id") Integer id, Model m){

        Optional<Boleto> boleto2 = boletoRepo.findById(boleto.getId());

        if(boleto2.isPresent()){
            Boleto bol = boleto2.get();

            Boleto bolLoad = new Boleto();
            bolLoad.setBoletoName(bol.getBoletoName());
            bolLoad.setDescripcion(bol.getDescripcion());
            bolLoad.setPrecio(bol.getPrecio());

            m.addAttribute("boleto", bolLoad);

            return "user/listandoboletos";
        }else {
            m.addAttribute("boleto", "No se encontró el usuario");
            return "redirect:/";
        }
    }

    @PostMapping("/save_boleto")
    public String saveBoleto(@ModelAttribute Boleto boleto, HttpSession session) {
        boletoRepo.save(boleto);
        session.setAttribute("msg", "El boleto ha sido agregado!!!");

        return "redirect:/user/load_form";
    }

    @GetMapping("/edit_form/{id}")
    public String editForm(@PathVariable(value = "id") Integer id, Model m) {

        Optional<UserDtls> user = userRepo.findById(id);

        UserDtls usr = user.get();

        UserDtls userEdit = new UserDtls();
        userEdit.setNombre(usr.getNombre());
        userEdit.setEmail(usr.getEmail());
        userEdit.setTelefono(usr.getTelefono());
        userEdit.setDireccion(usr.getDireccion());
        userEdit.setCiudad(usr.getCiudad());
        userEdit.setEdad(usr.getEdad());

        m.addAttribute("user", userEdit);

        return "/user/edit";
    }

    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute UserDtls user, HttpSession session) {

        Optional<UserDtls> existUser = userRepo.findById(user.getId());

        if (existUser.isPresent()){

            UserDtls userExis = existUser.get();

            userExis.setNombre(user.getNombre());
            userExis.setEmail(user.getEmail());
            userExis.setTelefono(user.getTelefono());
            userExis.setDireccion(user.getDireccion());
            userExis.setCiudad(user.getCiudad());
            userExis.setEdad(user.getEdad());

            userRepo.save(userExis);

            session.setAttribute("msg", "El usuario ha sido modificado!!!");
        }

        return "redirect:/user/";
    }

    @GetMapping("/cambiarPass")
    public String loadCambiarPassword(){
        return "user/cambiar_password";
    }

    @PostMapping("/updatePassword")
    public String cambiarPassword(Principal p, @RequestParam("oldContra") String oldContra,
                                  @RequestParam("newContra") String newContra, HttpSession session){

        String email = p.getName();

        UserDtls loginUser =userRepo.findByEmail(email);

        boolean f = passwordEncoder.matches(oldContra, loginUser.getPassword());

        if (f){

            loginUser.setPassword(passwordEncoder.encode(newContra));

            UserDtls updatePasswordUser = userRepo.save(loginUser);

            if (updatePasswordUser != null){

                session.setAttribute("msg", "La contraseña ha sido cambiada");
            }else {
                session.setAttribute("msg", "Error. Intentalo nuevamente");
            }

            /*System.out.println("La contraseña antigua es correcta");*/
        }else {
            /*System.out.println("La contraseña antigua es incorrecta");*/
            session.setAttribute("msg", "La contraseña antigua es incorrecta");
        }

        return "redirect:/user/cambiarPass";
    }
}

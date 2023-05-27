package com.product.controller;

import com.product.entity.Boleto;
import com.product.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BoletoRepository boletoRepo;

    @GetMapping("/")
    public String home(Model m){

        List<Boleto> list = boletoRepo.findAll();
        m.addAttribute("all_boleto", list);

        return "admin/home";
    }

    @GetMapping("/load_form")
    public String loadForm() {
        return "admin/add";
    }

    @GetMapping("/edit_form/{id}")
    public String editForm(@PathVariable(value = "id") Integer id, Model m) {

        Optional<Boleto> boleto = boletoRepo.findById(id);

        Boleto bol = boleto.get();
        m.addAttribute("boleto", bol);

        return "/admin/edit";
    }

    @PostMapping("/save_boleto")
    public String saveBoleto(@ModelAttribute Boleto boleto, HttpSession session) {
        boletoRepo.save(boleto);
        session.setAttribute("msg", "El boleto ha sido agregado!!!");

        return "redirect:/admin/load_form";
    }

    @PostMapping("/update_boleto")
    public String updateBoleto(@ModelAttribute Boleto boleto, HttpSession session) {
        boletoRepo.save(boleto);
        session.setAttribute("msg", "El boleto ha sido modificado!!!");

        return "redirect:/admin/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBoleto(@PathVariable(value = "id") Integer id, HttpSession session) {
        boletoRepo.deleteById(id);
        session.setAttribute("msg", "El boleto ha sido eleminado!!!");

        return "redirect:/admin/";
    }

}

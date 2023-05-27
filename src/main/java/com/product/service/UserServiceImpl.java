package com.product.service;

import com.product.model.UserDtls;
import com.product.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDtls createUser(UserDtls user, String url) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        user.setDesHabil(false);
        RandomString rnurl = new RandomString();
        user.setVerificaCode(rnurl.make(64));

        UserDtls us = userRepo.save(user);

        enviarVerificaMail(user, url);

        return us;
    }

    @Override
    public Boolean checkEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public void enviarVerificaMail(UserDtls user, String url) {

        String from = "jeferson_valbuenahu@fet.edu.co";
        String to = user.getEmail();
        String subject = "Cuenta de verificación";
        String content = "Señor/a [[nombre]], <br>"
                + "Por favor, haz click en el enlace de abajo para que verifique su registro."
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Verifica</a></h3>"
                + "Gracias,<br>"
                + "Jeferson Huila";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Jeferson Huila");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[nombre]]", user.getNombre());

            /*url = "http://localhost:8080" */
            String sitioUrl = url + "/verify?code="+ user.getVerificaCode();

            content = content.replace("[[URL]]", sitioUrl);

            helper.setText(content, true);

            mailSender.send(message);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Boolean verificaCuenta(String code) {

        UserDtls user = userRepo.findByVerificaCode(code);

        if (user != null){

            user.setDesHabil(true);
            user.setVerificaCode(null);
            userRepo.save(user);

            return true;
        }

        return false;
    }
}

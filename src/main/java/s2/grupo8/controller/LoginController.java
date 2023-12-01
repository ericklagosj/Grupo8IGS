package s2.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import s2.grupo8.service.UsuarioService;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(String id, String password, Model model) {
        if (usuarioService.validarCredenciales(id, password)) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }
}

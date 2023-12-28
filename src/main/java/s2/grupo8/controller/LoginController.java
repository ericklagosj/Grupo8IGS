package s2.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2.grupo8.model.RolUsuario;
import s2.grupo8.service.UsuarioService;
import s2.grupo8.exception.CredencialesInvalidasException;
import s2.grupo8.model.UsuarioAutenticado;
import s2.grupo8.exception.UsuarioNotFoundException;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam Long id, @RequestParam String password, Model model) {
        try {
            usuarioService.autenticarUsuario(id, password);

            RolUsuario rol = UsuarioAutenticado.getUsuarioAutenticado().getRol();

            if (rol == RolUsuario.ADMINISTRADOR) {
                return "redirect:/";
            } else if (rol == RolUsuario.EVALUADOR) {
                return "redirect:/evaluaciones/lista";
            } else if (rol == RolUsuario.VISADOR) {
                return "redirect:/promedio/lista";
            } else {
                model.addAttribute("error", "Rol desconocido");
                return "login";
            }
        } catch (UsuarioNotFoundException e) {
            model.addAttribute("error", "Usuario no encontrado");
        } catch (CredencialesInvalidasException e) {
            model.addAttribute("error", "Credenciales inválidas");
        }

        return "login";
    }
}
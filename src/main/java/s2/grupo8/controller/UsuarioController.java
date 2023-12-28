package s2.grupo8.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import s2.grupo8.model.Usuario;
import s2.grupo8.service.UsuarioService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import s2.grupo8.repository.UsuarioRepository;

@Controller
@RequestMapping(value = "/usuario", produces = "application/json")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/todos")
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public String listaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios";
    }    

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formularioUsuario";
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id") long id) {
        Usuario usuario = usuarioService.findById(id);
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        Long idProporcionado = usuario.getId();
        usuario.setId(idProporcionado);
            usuarioService.guardarUsuario(usuario);
    
        return "redirect:/usuario/lista";
    }
    
    @GetMapping("/editarUsuario/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "formularioUsuario";
    }

    @GetMapping("/eliminarUsuario/{id}")
        public String eliminarUsuario(@PathVariable Long id) {
            usuarioService.deleteById(id);
            return "redirect:/usuario/lista";
        }

    @GetMapping("/cambiarContrasena/{id}")
    public String mostrarFormularioCambioContrasena(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "formularioCambioContrasena";
    }

    @PostMapping("/guardarContrasena")
        public String guardarContrasena(@ModelAttribute Usuario usuario, String nuevaContrasena) {
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                usuarioExistente.setPassword(nuevaContrasena);
            }
            usuarioRepository.save(usuarioExistente);
            return "redirect:/usuario/lista";
        }

        @PostMapping("/enviarCredenciales/{id}")
        public String enviarCredenciales(@PathVariable Long id) {
            Usuario usuario = usuarioService.findById(id);
    
            usuarioService.enviarCredenciales(usuario);
    
            return "redirect:/usuario/lista";
        }

    @GetMapping("/buscarUsuario")
    public String buscarUsuario(@RequestParam String email, Model model) {
        Usuario usuario = usuarioService.findByEmail(email);
        model.addAttribute("usuario", usuario);
        return "formularioUsuario";
    }

}
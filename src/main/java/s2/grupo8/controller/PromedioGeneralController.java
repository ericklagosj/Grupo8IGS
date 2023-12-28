package s2.grupo8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2.grupo8.model.Empresa;
import s2.grupo8.model.Evaluacion;
import s2.grupo8.model.PromedioGeneral;
import s2.grupo8.model.RolUsuario;
import s2.grupo8.model.Usuario;
import s2.grupo8.service.EmpresaService;
import s2.grupo8.service.PromedioGeneralService;
import s2.grupo8.service.UsuarioService;
import s2.grupo8.repository.UsuarioRepository;
import s2.grupo8.repository.EvaluacionRepository;

@Controller
@RequestMapping("/promedio")
public class PromedioGeneralController {
    private final PromedioGeneralService promedioGeneralService;
    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PromedioGeneralController(PromedioGeneralService promedioGeneralService, EmpresaService empresaService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.promedioGeneralService = promedioGeneralService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/lista")
    public String listaPromediosGenerales(Model model) {
        List<PromedioGeneral> promediosGenerales = promedioGeneralService.obtenerTodosLosPromediosGenerales();
        model.addAttribute("promediosGenerales", promediosGenerales);
        return "evaluaciones/promedios";
    }

    @GetMapping("/ver/{id}")
    public String verPromedioGeneral(@PathVariable Long id, Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(id);
        model.addAttribute("promedioGeneral", promedioGeneral);
        return "evaluaciones/verPromedio";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        List<Empresa> empresas = empresaService.findAll();
        List<Usuario> visadores = usuarioService.obtenerUsuariosPorRol(RolUsuario.VISADOR);
        model.addAttribute("empresas", empresas);
        model.addAttribute("visadores", visadores);
        model.addAttribute("promedioGeneral", new PromedioGeneral());
        return "promedio-general/crear";
    }

    @PostMapping("/guardarObservaciones")
    public String guardarObservaciones(
            @RequestParam("promedioId") Long promedioId,
            @RequestParam("observaciones") String observaciones,
            Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(promedioId);
        promedioGeneral.setObservaciones(observaciones);
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);

        return "redirect:/promedio/ver/" + promedioId;
    }

    @PostMapping("/guardarYVisar")
    public String guardarYVisar(
            @RequestParam("promedioId") Long promedioId,
            Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(promedioId);
        promedioGeneral.visar();
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);

        return "redirect:/promedio/ver/" + promedioId;
    }

    @PostMapping("/guardarYPendiente")
    public String guardarYPendiente(
            @RequestParam("promedioId") Long promedioId,
            Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(promedioId);
        promedioGeneral.pendiente();
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);

        return "redirect:/promedio/ver/" + promedioId;
    }

    @PostMapping("/crear")
    public String crearPromedioGeneral(@ModelAttribute PromedioGeneral promedioGeneral) {
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);
        return "redirect:/promedio/lista";
    }

    @GetMapping("/asignarVisador/{promedioId}")
    public String mostrarFormularioAsignarVisador(Model model, @PathVariable Long promedioId) {
        List<Usuario> visadores = usuarioRepository.findByRol(RolUsuario.VISADOR);

        model.addAttribute("visadores", visadores);

        model.addAttribute("promedioId", promedioId);

        return "/evaluaciones/asignarVisador";
    }

    @PostMapping("/asignarVisador")
    public String asignarVisador(@RequestParam Long promedioId, @RequestParam Long idVisador) {
        try {
            promedioGeneralService.asignarVisador(promedioId, idVisador);
            return "redirect:/promedio/lista";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/promedio/asignarVisador/" + promedioId + "?error";
        }
    }
}
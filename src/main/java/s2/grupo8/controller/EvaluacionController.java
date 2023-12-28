package s2.grupo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2.grupo8.model.Empresa;
import s2.grupo8.model.EstadoEvaluacion;
import s2.grupo8.model.Evaluacion;
import s2.grupo8.model.EvaluacionTipo;
import s2.grupo8.model.PromedioGeneral;
import s2.grupo8.model.RolUsuario;
import s2.grupo8.model.Usuario;
import s2.grupo8.repository.UsuarioRepository;
import s2.grupo8.service.EvaluacionService;
import s2.grupo8.service.UsuarioService;
import s2.grupo8.service.EmpresaService;
import s2.grupo8.model.RolUsuario;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/evaluaciones")
public class EvaluacionController {
    private final EvaluacionService evaluacionService;
    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;        
    private final UsuarioRepository usuarioRepository;

    public EvaluacionController(EvaluacionService evaluacionService, EmpresaService empresaService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.evaluacionService = evaluacionService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/lista")
    public String listaEvaluaciones(Model model) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodasLasEvaluaciones();
        model.addAttribute("evaluaciones", evaluaciones);
        return "evaluaciones/lista";
    }

    @GetMapping("/ver/{id}")
    public String verEvaluacion(@PathVariable Long id, Model model) {
        Evaluacion evaluacion = evaluacionService.obtenerEvaluacionPorId(id);
        model.addAttribute("evaluacion", evaluacion);
        return "evaluaciones/ver";
    }

    @GetMapping("/empresa/{empresaId}")
    public String evaluacionesPorEmpresa(@PathVariable Long empresaId, Model model) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorEmpresa(empresaId);
        model.addAttribute("evaluacion", evaluaciones);
        return "evaluaciones/lista";
    }

    @PostMapping("/aprobar/{id}")
    public String aprobarEvaluacion(@PathVariable Long id) {
        evaluacionService.aprobarEvaluacion(id);
        return "redirect:/evaluaciones/lista";
    }

    @PostMapping("/desaprobar/{id}")
public String desaprobarEvaluacion(@PathVariable Long id) {
    Evaluacion evaluacion = evaluacionService.obtenerEvaluacionPorId(id);
    
    if (evaluacion != null) {
        evaluacion.desaprobar();
        evaluacionService.guardarEvaluacion(evaluacion);
    } else {
        System.out.println("No se encontró la evaluación con id: " + id);
    }

    return "redirect:/evaluaciones/lista";
}


    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        List<Empresa> empresas = empresaService.findAll();
        List<Usuario> evaluadores = usuarioService.obtenerUsuariosPorRol(RolUsuario.EVALUADOR);
        model.addAttribute("empresas", empresas);
        model.addAttribute("evaluadores", evaluadores);
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluacion/crear";
    }

    @PostMapping("/crear")
    public String crearEvaluacion(@ModelAttribute Evaluacion evaluacion) {
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones/lista";
    }

    @GetMapping("/formCompatibilidad")
    public String mostrarFormCompatibilidad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formCompatibilidad";
    }

    @PostMapping("/guardarCompatibilidad")
    public String guardarCompatibilidad(Evaluacion evaluacion) {
        evaluacion.setTipo(EvaluacionTipo.COMPATIBILIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @GetMapping("/formUsabilidad")
    public String mostrarFormUsabilidad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formUsabilidad";
    }

    @PostMapping("/guardarUsabilidad")
    public String guardarUsabilidad(Evaluacion evaluacion) {
        evaluacion.setTipo(EvaluacionTipo.USABILIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @GetMapping("/formFiabilidad")
    public String mostrarFormFiabilidad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formFiabilidad";
    }

    @PostMapping("/guardarFiabilidad")
    public String guardarFiabilidad(Evaluacion evaluacion) {
        evaluacion.setTipo(EvaluacionTipo.FIABILIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @GetMapping("/formSeguridad")
    public String mostrarFormSeguridad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formSeguridad";
    }

    @PostMapping("/guardarSeguridad")
    public String guardarSeguridad(Evaluacion evaluacion) {
        evaluacion.setTipo(EvaluacionTipo.SEGURIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @PostMapping("/guardarEvaluacion")
    public String guardarEvaluacion(@ModelAttribute Evaluacion evaluacion) {
        Evaluacion evaluacionExistente = evaluacionService.obtenerEvaluacionPorId(evaluacion.getId());

        evaluacionExistente.setFecha(LocalDateTime.now());

        evaluacionExistente.setPregunta1(evaluacion.getPregunta1());
        evaluacionExistente.setPregunta2(evaluacion.getPregunta2());
        evaluacionExistente.setPregunta3(evaluacion.getPregunta3());
        evaluacionExistente.setPregunta4(evaluacion.getPregunta4());
        evaluacionExistente.setPregunta5(evaluacion.getPregunta5());

        int sumaPreguntas = evaluacion.getPregunta1() + evaluacion.getPregunta2() + evaluacion.getPregunta3() + evaluacion.getPregunta4() + evaluacion.getPregunta5();
        evaluacionExistente.setNota(sumaPreguntas);

        if (evaluacionExistente.getTipo() == EvaluacionTipo.COMPATIBILIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaCompatibilidad(sumaPreguntas);
        } else if (evaluacionExistente.getTipo() == EvaluacionTipo.FIABILIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaFiabilidad(sumaPreguntas);
        } else if (evaluacionExistente.getTipo() == EvaluacionTipo.USABILIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaUsabilidad(sumaPreguntas);
        } else if (evaluacionExistente.getTipo() == EvaluacionTipo.SEGURIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaSeguridad(sumaPreguntas);
        }

        double promedioGeneral = evaluacionExistente.getEmpresa().getPromedioGeneral().calculatePromedioGeneral();
        evaluacionExistente.getEmpresa().getPromedioGeneral().setPromedioFinal(promedioGeneral);

        evaluacionExistente.setEstado(EstadoEvaluacion.APROBADA);

        evaluacionService.guardarEvaluacion(evaluacionExistente);

        return "redirect:/evaluaciones/lista";
    }

    @GetMapping("/asignarEvaluador/{evaluacionId}")
    public String mostrarFormularioAsignarEvaluador(Model model, @PathVariable Long evaluacionId) {
        List<Usuario> evaluadores = usuarioRepository.findByRol(RolUsuario.EVALUADOR);

        model.addAttribute("evaluadores", evaluadores);

        model.addAttribute("evaluacionId", evaluacionId);

        return "/evaluaciones/asignarEvaluador";
    }

    @PostMapping("/asignarEvaluador")
    public String asignarEvaluador(@RequestParam Long idEvaluacion, @RequestParam Long idEvaluador) {
    try {
        evaluacionService.asignarEvaluador(idEvaluacion, idEvaluador);
        return "redirect:/evaluaciones/lista";
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/evaluaciones/asignarEvaluador/" + idEvaluacion + "?error";
    }
}

}

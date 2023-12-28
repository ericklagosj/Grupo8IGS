package s2.grupo8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s2.grupo8.model.Evaluacion;
import s2.grupo8.model.Empresa;
import s2.grupo8.model.Usuario;
import s2.grupo8.repository.EvaluacionRepository;
import s2.grupo8.repository.UsuarioRepository;

import java.util.List;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {
    private final EvaluacionRepository evaluacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EvaluacionServiceImpl(EvaluacionRepository evaluacionRepository, UsuarioRepository usuarioRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Evaluacion> obtenerTodasLasEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    @Override
    public Evaluacion obtenerEvaluacionPorId(Long id) {
        return evaluacionRepository.findById(id).orElse(null);
    }

    @Override
    public void asignarEvaluacion(Long idEvaluacion, Long idEvaluador) {
        Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).orElse(null);
        Usuario evaluador = usuarioRepository.findById(idEvaluador).orElse(null);

        if (evaluacion != null && evaluador != null) {
            evaluacion.setEvaluador(evaluador);
            evaluacionRepository.save(evaluacion);
        }
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesPorEmpresa(Long empresaId) {

        return null;
    }

    @Override
    public void aprobarEvaluacion(Long evaluacionId) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId).orElse(null);
        if (evaluacion != null) {
            evaluacion.aprobar();
            evaluacionRepository.save(evaluacion);
        }
    }

    @Override
    public void desaprobarEvaluacion(Long evaluacionId) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId).orElse(null);
        if (evaluacion != null) {
            evaluacion.desaprobar();
            evaluacionRepository.save(evaluacion);
        }
    }

    @Override
    public void guardarEvaluacion(Evaluacion evaluacion) {
        evaluacionRepository.save(evaluacion);
    }


    @Override
    public void asignarEvaluador(Long idEvaluacion, Long idEvaluador) {
        Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).orElseThrow(() -> new RuntimeException("Evaluacion no encontrada con ID: " + idEvaluacion));
        Usuario evaluador = usuarioRepository.findById(idEvaluador).orElseThrow(() -> new RuntimeException("Usuario evaluador no encontrado con ID: " + idEvaluador));

        evaluacion.setEvaluador(evaluador);

        evaluacionRepository.save(evaluacion);
    }


    @Override
    public List<Evaluacion> obtenerEvaluacionesSinAsignar() {

        return null;
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesAsignadas() {

        return null;
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesAsignadasPorEvaluador(Usuario evaluador) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerEvaluacionesAsignadasPorEvaluador'");
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesSinAsignarPorEvaluador(Usuario evaluador) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerEvaluacionesSinAsignarPorEvaluador'");
    }

    @Override
    public void desasignarEvaluacionesPorEvaluador(Usuario evaluador) {
        throw new UnsupportedOperationException("Unimplemented method 'desasignarEvaluacionesPorEvaluador'");
    }

    

}

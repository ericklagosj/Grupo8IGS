package s2.grupo8.service;

import s2.grupo8.model.Evaluacion;
import s2.grupo8.model.Usuario;

import java.util.List;

public interface EvaluacionService {
    List<Evaluacion> obtenerTodasLasEvaluaciones();
    Evaluacion obtenerEvaluacionPorId(Long id);
    List<Evaluacion> obtenerEvaluacionesPorEmpresa(Long empresaId);
    void aprobarEvaluacion(Long evaluacionId);
    void desaprobarEvaluacion(Long evaluacionId);
    void guardarEvaluacion(Evaluacion evaluacion);
    void asignarEvaluador(Long idEvaluacion, Long idEvaluador);
    List<Evaluacion> obtenerEvaluacionesSinAsignar();
    List<Evaluacion> obtenerEvaluacionesAsignadas();
    List<Evaluacion> obtenerEvaluacionesAsignadasPorEvaluador(Usuario evaluador);
    List<Evaluacion> obtenerEvaluacionesSinAsignarPorEvaluador(Usuario evaluador);
    void asignarEvaluacion(Long idEvaluacion, Long idEvaluador);
    void desasignarEvaluacionesPorEvaluador(Usuario evaluador);
    
}

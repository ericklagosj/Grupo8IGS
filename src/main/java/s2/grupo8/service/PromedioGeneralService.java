package s2.grupo8.service;

import java.util.List;

import s2.grupo8.model.PromedioGeneral;

public interface PromedioGeneralService {
    List<PromedioGeneral> obtenerTodosLosPromediosGenerales();
    PromedioGeneral obtenerPromedioGeneralPorId(Long id);
    void guardarPromedioGeneral(PromedioGeneral promedioGeneral);
    void save(PromedioGeneral promedioGeneral);
    double calcularNotaFinal(PromedioGeneral promedioGeneral);
    void asignarVisador(Long idPromedioGeneral, Long idVisador);
}

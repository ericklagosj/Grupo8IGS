package s2.grupo8.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import s2.grupo8.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    Evaluacion findById(long id);

    Evaluacion findByEmpresaId(long id);

    Evaluacion findByEvaluadorId(long id);

    List<Evaluacion> findByFecha(LocalDateTime fecha);

    Evaluacion findByTipo(String tipo);
    
    Evaluacion findByEstado(String estado);

    @Query("SELECT e FROM Evaluacion e WHERE e.empresa.id = :empresaId")
    List<Evaluacion> obtenerEvaluacionesPorEmpresa(@Param("empresaId") Long empresaId);

}
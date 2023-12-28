package s2.grupo8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s2.grupo8.model.EstadoPromedio;
import s2.grupo8.model.PromedioGeneral;

import java.util.List;

@Repository
public interface PromedioGeneralRepository extends JpaRepository<PromedioGeneral, Long> {
    List<PromedioGeneral> findByEmpresaId(Long empresaId);

    List<PromedioGeneral> findByVisadorId(Long visadorId);

    List<PromedioGeneral> findByEstado(EstadoPromedio estado);

    List<PromedioGeneral> findByEmpresaIdAndEstado(Long empresaId, EstadoPromedio estado);

    

}
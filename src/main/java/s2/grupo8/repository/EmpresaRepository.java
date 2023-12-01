package s2.grupo8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s2.grupo8.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findById(long id);

    Empresa findByNombre(String nombre);

    Empresa findByEmail(String email);

    Empresa findByTelefono(String telefono);

}

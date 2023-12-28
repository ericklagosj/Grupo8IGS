package s2.grupo8.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2.grupo8.model.Empresa;
import s2.grupo8.repository.EmpresaRepository;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa findByNombre(String nombre) {
        return empresaRepository.findByNombre(nombre);
    }

    public Empresa findByEmail(String email) {
        return empresaRepository.findByEmail(email);
    }

    public Empresa findByTelefono(String telefono) {
        return empresaRepository.findByTelefono(telefono);
    }

    public Empresa findById(long id) {
        return empresaRepository.findById(id);
    }

    public void guardarEmpresa(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public void deleteById(long id) {
        empresaRepository.deleteById(id);
    }
}

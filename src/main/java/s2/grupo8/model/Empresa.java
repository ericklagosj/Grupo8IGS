package s2.grupo8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa {
    
    @Id
    private Long id;

    private String nombre;

    private String direccion;

    private String telefono;

    private String email;

    private Long id_cens;

    private Long id_anexo_tec;

    private double promedio_at;

    private double promedio_cens;

    public Empresa() {
    }

    public Empresa(String nombre, String direccion, String telefono, String email, Long id_cens, Long id_anexo_tec) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.id_cens = id_cens;
        this.id_anexo_tec = id_anexo_tec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_cens() {
        return id_cens;
    }

    public void setId_cens(Long id_cens) {
        this.id_cens = id_cens;
    }

    public Long getId_anexo_tec() {
        return id_anexo_tec;
    }

    public void setId_anexo_tec(Long id_anexo_tec) {
        this.id_anexo_tec = id_anexo_tec;
    }

    public double getPromedio_at() {
        return promedio_at;
    }

    public void setPromedio_at(double promedio_at) {
        this.promedio_at = promedio_at;
    }

    public double getPromedio_cens() {
        return promedio_cens;
    }

    public void setPromedio_cens(double promedio_cens) {
        this.promedio_cens = promedio_cens;
    }
}

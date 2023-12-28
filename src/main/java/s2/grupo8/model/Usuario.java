package s2.grupo8.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private RolUsuario rol;

    @OneToMany(mappedBy = "evaluador")
    private List<Evaluacion> evaluaciones;

    @OneToMany(mappedBy = "visador")    
    private List<PromedioGeneral> promedioGenerales;

    public Usuario() {
        this.evaluaciones = new ArrayList<>();
        this.promedioGenerales = new ArrayList<>();
    }

    public Usuario(String nombre, String apellido, String email, String telefono, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    public Usuario(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validarCredenciales(String id, String password) {
        Long idLong = Long.parseLong(id);
        return this.id.equals(idLong) && this.password.equals(password);
    }

    public Evaluacion getEvaluacionPorTipo(EvaluacionTipo tipo) {
        for (Evaluacion evaluacion : evaluaciones) {
            if (evaluacion.getTipo() == tipo) {
                return evaluacion;
            }
        }
        return null;
    }

    public void cambiarRol(RolUsuario nuevoRol) {
        this.rol = nuevoRol;
    }

    public void asignarEvaluador(Usuario evaluador, EvaluacionTipo tipoEvaluacion) {
        if (this.rol == RolUsuario.ADMINISTRADOR) {
            if (evaluador.getRol() == RolUsuario.EVALUADOR) {
                if (evaluaciones.stream().noneMatch(e -> e.getTipo() == tipoEvaluacion)) {
                    Evaluacion evaluacion = new Evaluacion(tipoEvaluacion, this, evaluador);
                    evaluaciones.add(evaluacion);
                    System.out.println("Evaluación asignada con éxito.");
                } else {
                    System.out.println("Error: Ya existe una evaluación del mismo tipo para este usuario.");
                }
            } else {
                System.out.println("Error: El usuario a asignar debe tener el rol de EVALUADOR.");
            }
        } else {
            System.out.println("Error: Solo un ADMINISTRADOR puede asignar evaluadores.");
        }
    }

    public void asignarVisador(Usuario visador, double promedioGeneral) {
        if (this.rol == RolUsuario.ADMINISTRADOR) {
            if (visador.getRol() == RolUsuario.VISADOR) {
                if (promedioGenerales.stream().noneMatch(pg -> pg.getVisador() == visador)) {
                    PromedioGeneral promedioGeneralObj = new PromedioGeneral(visador, promedioGeneral);
                    promedioGenerales.add(promedioGeneralObj);
                    System.out.println("Promedio general asignado con éxito.");
                } else {
                    System.out.println("Error: Ya existe un promedio general para este visador.");
                }
            } else {
                System.out.println("Error: El usuario a asignar debe tener el rol de VISADOR.");
            }
        } else {
            System.out.println("Error: Solo un ADMINISTRADOR puede asignar visadores.");
        }
    }
}

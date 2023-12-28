package s2.grupo8.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "id_cens")
    private Long id_cens;

    @Column(name = "id_anexo_tec")
    private Long id_anexo_tec;

    @Column(name = "promedio_cens")
    private Long promedio_cens;

    @Column(name = "promedio_at")
    private Long promedio_at;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones;

    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private PromedioGeneral promedioGeneral;

    public Empresa() {
        super();
        evaluaciones = new ArrayList<>();
        initEvaluaciones();
        initPromedioGeneral();
    }

    public Empresa(Long id, String nombre, String direccion, String telefono, String email, Long id_cens, Long id_anexo_tec, Long promedio_cens, Long promedio_at) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.id_cens = id_cens;
        this.id_anexo_tec = id_anexo_tec;
        this.promedio_cens = promedio_cens;
        this.promedio_at = promedio_at;
        evaluaciones = new ArrayList<>();
        initEvaluaciones();
        initPromedioGeneral();
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

    public Long getPromedio_cens() {
        return promedio_cens;
    }

    public void setPromedio_cens(Long promedio_cens) {
        this.promedio_cens = promedio_cens;
    }

    public Long getPromedio_at() {
        return promedio_at;
    }

    public void setPromedio_at(Long promedio_at) {
        this.promedio_at = promedio_at;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        for (Evaluacion evaluacion : evaluaciones) {
            evaluacion.setEmpresa(this);
        }
        this.evaluaciones = evaluaciones;
    }

    public void agregarEvaluacion(Evaluacion evaluacion) {
        evaluacion.setEmpresa(this);
        evaluaciones.add(evaluacion);
    }

    public void eliminarEvaluacion(Evaluacion evaluacion) {
        evaluaciones.remove(evaluacion);
    }

    public Evaluacion getEvaluacionPorTipo(EvaluacionTipo tipo) {
        for (Evaluacion evaluacion : evaluaciones) {
            if (evaluacion.getTipo() == tipo) {
                return evaluacion;
            }
        }
        return null;
    }

    public List<TipoEvaluacion> getTiposEvaluacion() {
        List<TipoEvaluacion> tiposEvaluacion = new ArrayList<>();
        for (Evaluacion evaluacion : evaluaciones) {
            tiposEvaluacion.add(evaluacion.getTipoEvaluacion());
        }
        return tiposEvaluacion;
    }

    private List<Pregunta> initPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
    
        for (int i = 1; i <= 5; i++) {
            Pregunta pregunta = new Pregunta();
            pregunta.setEnunciado("Pregunta " + i);
            preguntas.add(pregunta);
        }
    
        return preguntas;
    }

    @PostPersist
    public void initEvaluaciones() {
        Evaluacion evaluacion1 = new Evaluacion();
        evaluacion1.setTipo(EvaluacionTipo.COMPATIBILIDAD);
        evaluacion1.setEmpresa(this);
        evaluacion1.setPromedioGeneral(promedioGeneral);
    
        Evaluacion evaluacion2 = new Evaluacion();
        evaluacion2.setTipo(EvaluacionTipo.FIABILIDAD);
        evaluacion2.setEmpresa(this);
        evaluacion2.setPromedioGeneral(promedioGeneral);
    
        Evaluacion evaluacion3 = new Evaluacion();
        evaluacion3.setTipo(EvaluacionTipo.USABILIDAD);
        evaluacion3.setEmpresa(this);
        evaluacion3.setPromedioGeneral(promedioGeneral);
    
        Evaluacion evaluacion4 = new Evaluacion();
        evaluacion4.setTipo(EvaluacionTipo.SEGURIDAD);
        evaluacion4.setEmpresa(this);
        evaluacion4.setPromedioGeneral(promedioGeneral);
    
        evaluaciones.add(evaluacion1);
        evaluaciones.add(evaluacion2);
        evaluaciones.add(evaluacion3);
        evaluaciones.add(evaluacion4);
    
        TipoEvaluacion tipo1 = new TipoEvaluacion();
        tipo1.setTipo(EvaluacionTipo.COMPATIBILIDAD);
        tipo1.setEmpresa(this);
    
        TipoEvaluacion tipo2 = new TipoEvaluacion();
        tipo2.setTipo(EvaluacionTipo.FIABILIDAD);
        tipo2.setEmpresa(this);
    
        TipoEvaluacion tipo3 = new TipoEvaluacion();
        tipo3.setTipo(EvaluacionTipo.USABILIDAD);
        tipo3.setEmpresa(this);
    
        TipoEvaluacion tipo4 = new TipoEvaluacion();
        tipo4.setTipo(EvaluacionTipo.SEGURIDAD);
        tipo4.setEmpresa(this);
    
        tipo1.setPreguntas(initPreguntas());
        tipo2.setPreguntas(initPreguntas());
        tipo3.setPreguntas(initPreguntas());
        tipo4.setPreguntas(initPreguntas());
    }

    public void initPromedioGeneral() {
        PromedioGeneral promedioGeneral = new PromedioGeneral();
        promedioGeneral.setEmpresa(this);
        promedioGeneral.setEstado(EstadoPromedio.PENDIENTE);
        promedioGeneral.setPromedioFinal(0.0);
        promedioGeneral.setObservaciones("");
        promedioGeneral.setNotaCompatibilidad(0);
        promedioGeneral.setNotaFiabilidad(0);
        promedioGeneral.setNotaUsabilidad(0);
        promedioGeneral.setNotaSeguridad(0);
        this.promedioGeneral = promedioGeneral;
    }

    public PromedioGeneral getPromedioGeneral() {
        return promedioGeneral;
    }
}


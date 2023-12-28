package s2.grupo8.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_evaluador")
    private Usuario evaluador;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private EvaluacionTipo tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEvaluacion estado;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "pregunta1")
    private int pregunta1;

    @Column(name = "pregunta2")
    private int pregunta2;

    @Column(name = "pregunta3")
    private int pregunta3;

    @Column(name = "pregunta4")
    private int pregunta4;

    @Column(name = "pregunta5")
    private int pregunta5;

    @ManyToOne
    @JoinColumn(name = "tipo_evaluacion_id")
    private TipoEvaluacion tipoEvaluacion;

    @Column(name = "nota")
    private int nota;

    @ManyToOne
    @JoinColumn(name = "promedio_general_id")
    private PromedioGeneral promedioGeneral;

    public Evaluacion() {
        super();
        estado = EstadoEvaluacion.PENDIENTE;
        nota = 0;
    }

    public Evaluacion(Empresa empresa, Usuario evaluador, EvaluacionTipo tipo, LocalDateTime fecha, PromedioGeneral promedioGeneral) {
        super();
        this.empresa = empresa;
        this.evaluador = evaluador;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.promedioGeneral = promedioGeneral;
        estado = EstadoEvaluacion.PENDIENTE;
        nota = 0;
    }

    public Evaluacion(EvaluacionTipo tipoEvaluacion2, Usuario usuario, Usuario evaluador2) {
    }

    public Long getId() {
        return id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Usuario getEvaluador() {
        return evaluador;
    }

    public EvaluacionTipo getTipo() {
        return tipo;
    }

    public EstadoEvaluacion getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getPregunta1() {
        return pregunta1;
    }

    public int getPregunta2() {
        return pregunta2;
    }

    public int getPregunta3() {
        return pregunta3;
    }

    public int getPregunta4() {
        return pregunta4;
    }

    public int getPregunta5() {
        return pregunta5;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setEvaluador(Usuario evaluador) {
        this.evaluador = evaluador;
    }

    public void setTipo(EvaluacionTipo tipo) {
        this.tipo = tipo;
    }

    public void setEstado(EstadoEvaluacion estado) {
        this.estado = estado;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setPregunta1(int pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public void setPregunta2(int pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public void setPregunta3(int pregunta3) {
        this.pregunta3 = pregunta3;
    }

    public void setPregunta4(int pregunta4) {
        this.pregunta4 = pregunta4;
    }

    public void setPregunta5(int pregunta5) {
        this.pregunta5 = pregunta5;
    }
    
    public TipoEvaluacion getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public List<Evaluacion> obtenerEvaluacionesPorEmpresa(Long empresaId) {
        List<Evaluacion> evaluacionesPorEmpresa = new ArrayList<>();
        for (Evaluacion evaluacion : empresa.getEvaluaciones()) {
            if (evaluacion.getEmpresa().getId() == empresaId) {
                evaluacionesPorEmpresa.add(evaluacion);
            }
        }
        return evaluacionesPorEmpresa;
    }

    public void aprobar() {
        estado = EstadoEvaluacion.APROBADA;
    }

    public void desaprobar() {
        estado = EstadoEvaluacion.DESAPROBADA;
    }

    public void setPromedioGeneral(PromedioGeneral promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }
}
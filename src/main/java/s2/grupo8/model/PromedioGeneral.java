package s2.grupo8.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "promedio_general")
public class PromedioGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_visador")
    private Usuario visador;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPromedio estado;

    @Column(name = "promedio_final", columnDefinition = "double")
    private double promedioFinal;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "notaCompatibilidad")
    private int notaCompatibilidad;

    @Column(name = "notaUsabilidad")
    private int notaUsabilidad;

    @Column(name = "notaFiabilidad")
    private int notaFiabilidad;

    @Column(name = "notaSeguridad")
    private int notaSeguridad;

    public PromedioGeneral() {
    }

    public PromedioGeneral(Empresa empresa, Usuario visador, EstadoPromedio estado, double promedioFinal,
            String observaciones, int notaCompatibilidad, int notaUsabilidad, int notaFiabilidad, int notaSeguridad) {
        this.empresa = empresa;
        this.visador = visador;
        this.estado = estado;
        this.promedioFinal = promedioFinal;
        this.observaciones = observaciones;
        notaCompatibilidad = 0;
        notaUsabilidad = 0;
        notaFiabilidad = 0;
        notaSeguridad = 0;
    }

    public PromedioGeneral(Usuario visador2, double promedioGeneral) {
    }

    public Long getId() {
        return id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Usuario getVisador() {
        return visador;
    }

    public EstadoPromedio getEstado() {
        return estado;
    }

    public double getPromedioFinal() {
        return promedioFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setVisador(Usuario visador) {
        this.visador = visador;
    }

    public void setEstado(EstadoPromedio estado) {
        this.estado = estado;
    }

    public void setPromedioFinal(double promedioFinal) {
        this.promedioFinal = promedioFinal;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double calculatePromedioGeneral() {
        double sum = 0.0;

        sum += notaCompatibilidad;
        sum += notaUsabilidad;
        sum += notaFiabilidad;
        sum += notaSeguridad;

        return sum / 4;
    }

    public int getNotaCompatibilidad() {
        return notaCompatibilidad;
    }

    public void setNotaCompatibilidad(int notaCompatibilidad) {
        this.notaCompatibilidad = notaCompatibilidad;
    }

    public int getNotaUsabilidad() {
        return notaUsabilidad;
    }

    public void setNotaUsabilidad(int notaUsabilidad) {
        this.notaUsabilidad = notaUsabilidad;
    }

    public int getNotaFiabilidad() {
        return notaFiabilidad;
    }

    public void setNotaFiabilidad(int notaFiabilidad) {
        this.notaFiabilidad = notaFiabilidad;
    }

    public int getNotaSeguridad() {
        return notaSeguridad;
    }

    public void setNotaSeguridad(int notaSeguridad) {
        this.notaSeguridad = notaSeguridad;
    }
    
    public void visar() {
        this.estado = EstadoPromedio.VISADO;
    }

    public void pendiente() {
        this.estado = EstadoPromedio.PENDIENTE;
    }
    
}

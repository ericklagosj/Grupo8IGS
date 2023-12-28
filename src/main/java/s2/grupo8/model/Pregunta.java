package s2.grupo8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pregunta")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enunciado;

    private int valor;

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "tipo_evaluacion_id")
    private TipoEvaluacion tipoEvaluacion;

    public Pregunta() {
        super();
        this.valor = 0;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        if (valor >= 0 && valor <= 3) {
            this.valor = valor;
        } else {

            this.valor = 0;
        }
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        if (enunciado != null) {
            this.enunciado = enunciado;
        } else {

            this.enunciado = "";
        }
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

}

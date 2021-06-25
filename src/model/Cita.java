package model;

import java.util.GregorianCalendar;
import java.util.Objects;

public class Cita {

    private int numeroRegistro;
    private int numeroFicha;
    private GregorianCalendar fecha;
    private String anamnesis;
    private String receta;
    private String diagnostico;
    private String examenes;

    private Paciente paciente;

    public Cita() {
        paciente = new Paciente();
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public int getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getExamenes() {
        return examenes;
    }

    public void setExamenes(String examenes) {
        this.examenes = examenes;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return numeroRegistro == cita.numeroRegistro && numeroFicha == cita.numeroFicha && Objects.equals(fecha, cita.fecha) && Objects.equals(anamnesis, cita.anamnesis) && Objects.equals(receta, cita.receta) && diagnostico.equals(cita.diagnostico) && Objects.equals(examenes, cita.examenes) && paciente.equals(cita.paciente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroRegistro, numeroFicha, fecha, anamnesis, receta, diagnostico, examenes, paciente);
    }

    @Override
    public String toString() {
        return "Cita{" +
                "numeroRegistro=" + numeroRegistro +
                ", numeroFicha=" + numeroFicha +
                ", fecha=" + fecha +
                ", anamnesis='" + anamnesis + '\'' +
                ", receta='" + receta + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", examenes='" + examenes + '\'' +
                ", paciente=" + paciente +
                '}';
    }
}

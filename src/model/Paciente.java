package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class Paciente {

    private int numeroFicha;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String celular;
    private String estadoCivil;
    private String procedencia;
    private String residencia;
    private GregorianCalendar fechaNacimiento;
    private String genero;
    private String antecedentes;
    private double peso;
    private double talla;
    private String grupoSanguineo;

    private List<Cita> citas;

    public Paciente(){
        citas = new ArrayList<>();
    }

    public int getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public GregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(GregorianCalendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public boolean addCita(Cita cita){
        return citas.add(cita);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return numeroFicha == paciente.numeroFicha && Double.compare(paciente.peso, peso) == 0 && Double.compare(paciente.talla, talla) == 0 && cedula.equals(paciente.cedula) && nombres.equals(paciente.nombres) && apellidos.equals(paciente.apellidos) && Objects.equals(direccion, paciente.direccion) && Objects.equals(telefono, paciente.telefono) && Objects.equals(celular, paciente.celular) && Objects.equals(estadoCivil, paciente.estadoCivil) && Objects.equals(procedencia, paciente.procedencia) && Objects.equals(residencia, paciente.residencia) && Objects.equals(fechaNacimiento, paciente.fechaNacimiento) && Objects.equals(genero, paciente.genero) && antecedentes.equals(paciente.antecedentes) && Objects.equals(grupoSanguineo, paciente.grupoSanguineo) && Objects.equals(citas, paciente.citas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroFicha, cedula, nombres, apellidos, direccion, telefono, celular, estadoCivil, procedencia, residencia, fechaNacimiento, genero, antecedentes, peso, talla, grupoSanguineo, citas);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "numeroFicha=" + numeroFicha +
                ", cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", procedencia='" + procedencia + '\'' +
                ", residencia='" + residencia + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", antecedentes='" + antecedentes + '\'' +
                ", peso=" + peso +
                ", talla=" + talla +
                ", grupoSanguineo='" + grupoSanguineo + '\'' +
                '}';
    }
}

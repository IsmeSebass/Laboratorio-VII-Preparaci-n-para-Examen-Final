package com.laboratorio.proveedores.model;

public class Proveedor {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String categoria;
    private Estado estado;

    public Proveedor() {
    }

    public Proveedor(Long id, String nombre, String apellido, String correo, String categoria, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.categoria = categoria;
        this.estado = estado;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}

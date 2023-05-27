package com.product.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "boleto_dtls")
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "boleto_name")
    private String boletoName;

    private String descripcion;

    private String lugar;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_tour")
    private Date fechaTour;

    private String precio;

    private String cantidad;

    public Boleto() {
        super();
    }

    public Boleto(String boletoName, String descripcion, String lugar, Date fechaTour, String precio, String cantidad) {
        super();
        this.boletoName = boletoName;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fechaTour = fechaTour;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoletoName() {
        return boletoName;
    }

    public void setBoletoName(String boletoName) {
        this.boletoName = boletoName;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFechaTour() {
        return fechaTour;
    }

    public void setFechaTour( Date fechaTour) {
        this.fechaTour = fechaTour;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Boleto{" +
                "id=" + id +
                ", boletoName='" + boletoName + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fechaTour='" + fechaTour + '\'' +
                ", precio='" + precio + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}

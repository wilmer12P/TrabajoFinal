/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.banco.modelo;

import java.util.Date;

/**
 *
 * @author Wilmer
 */
public class MovimientoBD {
  private int id_movimient;
  private int id_cliente;
  private String nro_cliente;
  private String tipo_movi;
  private String tipo_mone;
  private Date fecha;
  private double monto;

    public MovimientoBD() {
    }

    public MovimientoBD(int id_movimient, int id_cliente, String nro_cliente, String tipo_movi, String tipo_mone, Date fecha, double monto) {
        this.id_movimient = id_movimient;
        this.id_cliente = id_cliente;
        this.nro_cliente = nro_cliente;
        this.tipo_movi = tipo_movi;
        this.tipo_mone = tipo_mone;
        this.fecha = fecha;
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getId_movimient() {
        return id_movimient;
    }

    public void setId_movimient(int id_movimient) {
        this.id_movimient = id_movimient;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNro_cliente() {
        return nro_cliente;
    }

    public void setNro_cliente(String nro_cliente) {
        this.nro_cliente = nro_cliente;
    }

    public String getTipo_movi() {
        return tipo_movi;
    }

    public void setTipo_movi(String tipo_movi) {
        this.tipo_movi = tipo_movi;
    }

    public String getTipo_mone() {
        return tipo_mone;
    }

    public void setTipo_mone(String tipo_mone) {
        this.tipo_mone = tipo_mone;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
  
          
}

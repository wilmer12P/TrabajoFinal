package com.example.banco.modelo;

public class Movimiento {

    
    private int idCliente;
    private int idClienteOtro;
    private String nrCuenta;
    private String nroCuentaOtro;
    private String tipoMovi;
    private Double monto;
    private String color;
    private String tipomonto;
    private String nombClienteOtro;
    private String nombCliente;
    private Double montoActualHaceDeposito;
    private Double montoActualReciboDeposito;

    public Movimiento(int idCliente, int idClienteOtro, String nrCuenta, String nroCuentaOtro, String tipoMovi, Double monto, String color, String tipomonto, String nombClienteOtro, String nombCliente, Double montoActualHaceDeposito, Double montoActualReciboDeposito) {
        this.idCliente = idCliente;
        this.idClienteOtro = idClienteOtro;
        this.nrCuenta = nrCuenta;
        this.nroCuentaOtro = nroCuentaOtro;
        this.tipoMovi = tipoMovi;
        this.monto = monto;
        this.color = color;
        this.tipomonto = tipomonto;
        this.nombClienteOtro = nombClienteOtro;
        this.nombCliente = nombCliente;
        this.montoActualHaceDeposito = montoActualHaceDeposito;
        this.montoActualReciboDeposito = montoActualReciboDeposito;
    }

    public Movimiento() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdClienteOtro() {
        return idClienteOtro;
    }

    public void setIdClienteOtro(int idClienteOtro) {
        this.idClienteOtro = idClienteOtro;
    }

    public String getNrCuenta() {
        return nrCuenta;
    }

    public void setNrCuenta(String nrCuenta) {
        this.nrCuenta = nrCuenta;
    }

    public String getNroCuentaOtro() {
        return nroCuentaOtro;
    }

    public void setNroCuentaOtro(String nroCuentaOtro) {
        this.nroCuentaOtro = nroCuentaOtro;
    }

    public String getTipoMovi() {
        return tipoMovi;
    }

    public void setTipoMovi(String tipoMovi) {
        this.tipoMovi = tipoMovi;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipomonto() {
        return tipomonto;
    }

    public void setTipomonto(String tipomonto) {
        this.tipomonto = tipomonto;
    }

    public String getNombClienteOtro() {
        return nombClienteOtro;
    }

    public void setNombClienteOtro(String nombClienteOtro) {
        this.nombClienteOtro = nombClienteOtro;
    }

    public String getNombCliente() {
        return nombCliente;
    }

    public void setNombCliente(String nombCliente) {
        this.nombCliente = nombCliente;
    }

    public Double getMontoActualHaceDeposito() {
        return montoActualHaceDeposito;
    }

    public void setMontoActualHaceDeposito(Double montoActualHaceDeposito) {
        this.montoActualHaceDeposito = montoActualHaceDeposito;
    }

    public Double getMontoActualReciboDeposito() {
        return montoActualReciboDeposito;
    }

    public void setMontoActualReciboDeposito(Double montoActualReciboDeposito) {
        this.montoActualReciboDeposito = montoActualReciboDeposito;
    }

}
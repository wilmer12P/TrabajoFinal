package com.example.banco.dao;

import com.example.banco.modelo.Movimiento;
import com.example.banco.modelo.MovimientoBD;
import com.example.banco.modelo.RptaMovimiento;
import java.util.List;

public interface IDao {
    RptaMovimiento realizarTransferencia(Movimiento movi);
    public List<MovimientoBD>MostrarTranferencias(int idCliente);
    int existeCliente(int idCliente);
    String getNroCuentaByCliente(int idCliente);
    Double getMontoDepositoHoyByCliente(int idCliente);
    int getCantDepositosHoyByCliente(int idCliente);
    Double getSaldoByCliente(int idCliente);
}
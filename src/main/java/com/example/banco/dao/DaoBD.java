package com.example.banco.dao;

import com.example.banco.modelo.Movimiento;
import com.example.banco.modelo.MovimientoBD;
import com.example.banco.modelo.RptaMovimiento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("daobd")
public class DaoBD implements IDao{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
       ResultSet rs;
    @Override
    public RptaMovimiento realizarTransferencia(Movimiento movi) {
        RptaMovimiento rpta = new RptaMovimiento();
        try {
            System.err.println("realizarTransferencia....");
            String sql = "call realizar_trasnferencia(?, ?, ?, ?, ?, ?, ?)";
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement callableSt = connection.prepareCall(sql);
            callableSt.setInt(1, movi.getIdCliente());
            callableSt.setInt(2, movi.getIdClienteOtro());
            callableSt.setString(3, movi.getNrCuenta());
            callableSt.setString(4, movi.getTipomonto());
            callableSt.setDouble(5, movi.getMonto());
            callableSt.registerOutParameter(6, Types.INTEGER);
            callableSt.registerOutParameter(7, Types.VARCHAR);
            //Call Stored Procedure
            callableSt.executeUpdate();
            rpta.setCodigo_error(callableSt.getInt(6));
            rpta.setMsj_error(callableSt.getString(7));
            System.err.println("retorno: "+rpta.getCodigo_error()+" -- "+rpta.getMsj_error());
        } catch (Exception e) {
            e.printStackTrace();
            rpta.setCodigo_error(-1);
            rpta.setMsj_error(e.getMessage());
        }
        return rpta;
    }

    @Override
    public List<MovimientoBD>MostrarTranferencias(int idCliente){
        RptaMovimiento rpta = new RptaMovimiento();
        List<MovimientoBD> lista=new ArrayList<>();
        try {
            
            System.err.println("mostrartransferencias....");
            String sql = "{call mostrar_transferencias(?)}";
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement pst = connection.prepareCall(sql);
            pst.setInt(1, idCliente);
            rs=pst.executeQuery();
            while (rs.next()) {                
                 MovimientoBD objmovimiento= new MovimientoBD();
                
                objmovimiento.setId_movimient(rs.getInt("id_movimiento"));
                objmovimiento.setId_cliente(rs.getInt("id_cliente"));
                objmovimiento.setNro_cliente(rs.getString("nro_cuenta"));
                objmovimiento.setTipo_movi(rs.getString("tipo_movi"));
                objmovimiento.setTipo_mone(rs.getString("tipo_mone"));
                objmovimiento.setFecha(rs.getDate("fecha_movi"));
                objmovimiento.setMonto(rs.getDouble("monto"));
                
                 lista.add(objmovimiento);
            }
            
        } catch (Exception e) {
             e.printStackTrace();
            rpta.setCodigo_error(-1);
            rpta.setMsj_error(e.getMessage());
        }
        return lista;
    }

    @Override
    public int existeCliente(int idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String getNroCuentaByCliente(int idCliente) {
        try {
            System.err.println("ejecutando....");
            String sql = "call get_nro_cuenta_by_cliente(?, ?)";
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement callableSt = connection.prepareCall(sql);
            callableSt.setInt(1, idCliente);
            callableSt.registerOutParameter(2, Types.VARCHAR);
            callableSt.executeUpdate();
            System.err.println("retorno: "+callableSt.getString(2));
            return callableSt.getString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getMontoDepositoHoyByCliente(int idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCantDepositosHoyByCliente(int idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double getSaldoByCliente(int idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
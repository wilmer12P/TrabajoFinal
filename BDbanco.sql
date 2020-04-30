create database banco;
use banco;
create table movimiento (
id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
id_cliente INT,
nro_cuenta VARCHAR(5),
 tipo_movi VARCHAR(3),
 tipo_mone VARCHAR(10),
 fecha_movi DATETIME DEFAULT CURRENT_TIMESTAMP,
 monto NUMERIC);


create table cliente (
id_cliente int primary key auto_increment,
nomb_cliente varchar(30),
saldo NUMERIC,
nro_cuenta VARCHAR(5)
);
insert into cliente(nomb_cliente,saldo,nro_cuenta) values('RONALDINHO',5000,'10100');
insert into cliente(nomb_cliente,saldo,nro_cuenta) values('CUEVITA',4000,'10200');
insert into cliente(nomb_cliente,saldo,nro_cuenta) values('PIZZARRO',4500,'10300');
insert into cliente(nomb_cliente,saldo,nro_cuenta) values('FARFAN',6000,'10400');

SET GLOBAL time_zone = '-5:00';

select * from movimiento;
select*from cliente;

DELIMITER $$
CREATE PROCEDURE get_nro_cuenta_by_cliente(
    IN _p_id_cliente INT,
    OUT _p_nro_cuenta VARCHAR(5)
)
BEGIN
    SELECT c.nro_cuenta
      INTO _p_nro_cuenta
      FROM cliente c
     WHERE c.id_cliente = _p_id_cliente;

END $$
DELIMITER ;
call get_nro_cuenta_by_cliente (1, @cuenta);
DELIMITER $$
CREATE PROCEDURE get_existe_cliente(
    IN _p_id_cliente INT,
    out p_existe int
)
BEGIN
    SELECT COUNT(1) AS existe
      INTO p_existe
      FROM cliente c
     WHERE c.id_cliente = _p_id_cliente;

END $$
DELIMITER ;


drop procedure if exists mostrar_transferencias;
delimiter $$
create procedure mostrar_transferencias(
IN idmovimiento int
)
begin
select *
from movimiento
where id_movimiento = idmovimiento;
end $$
delimiter ;
call mostrar_transferencias(1);


DELIMITER $$
DROP PROCEDURE IF EXISTS realizar_trasnferencia;
CREATE PROCEDURE realizar_trasnferencia(
    IN _p_id_cliente_hace_depo INT,
    IN _p_id_cliente_reci_depo INT,
    IN _p_nro_cuenta_reci_depo VARCHAR(5),
    IN _p_tipo_mone            VARCHAR(10),
    IN _p_monto                NUMERIC(6,2),
    OUT _p_cod_error           INT,
    OUT _p_msj_error           VARCHAR(100)
)
this_proc:BEGIN
    DECLARE _v_existe INT;
    DECLARE _v_monto_depositado NUMERIC;
    DECLARE _v_cnt_depos INT;
    DECLARE _v_nro_cuenta VARCHAR(5);
    DECLARE _v_saldo_hace NUMERIC;
    DECLARE _v_saldo_reci NUMERIC;
    DECLARE _v_monto NUMERIC;
    --
    IF _p_monto <= 0 THEN
        SET _p_cod_error = 1;
        SET _p_msj_error = 'El monto tiene que ser un número positivo.';
        LEAVE this_proc;
    END IF;
    --
    SELECT COUNT(1)
      INTO _v_existe
      FROM cliente c
     WHERE c.id_cliente = _p_id_cliente_hace_depo;
    --
    IF _v_existe = 0 THEN
        SET _p_cod_error = 2;
        SET _p_msj_error = CONCAT('El cliente con el id ', _p_id_cliente_hace_depo, ' no existe.');
        LEAVE this_proc;
    END IF;
    --
    SELECT COALESCE(SUM(m.monto), 0)
      INTO _v_monto_depositado
      FROM movimiento m
     WHERE m.id_cliente = _p_id_cliente_hace_depo
       AND m.tipo_movi  = 'EGR'
       AND DATE(m.fecha_movi) = CURRENT_DATE
    ;
    IF ( _v_monto_depositado + _p_monto > 1500) THEN
        SET _p_cod_error = 3;
        SET _p_msj_error = 'El cliente supera el límite de depósito del día.';
        LEAVE this_proc;
    END IF;
    --
      IF ( _v_monto_depositado + _p_monto > 500) THEN
        SET _p_monto= (monto+(_p_monto*0.5));
        LEAVE this_proc;
    END IF;
    --
    SELECT COUNT(1)
      INTO _v_cnt_depos
      FROM movimiento m
     WHERE m.id_cliente = _p_id_cliente_hace_depo
       AND m.tipo_movi = 'EGR'
       AND DATE(m.fecha_movi) = CURRENT_DATE
    ;
    IF _v_cnt_depos + 1 >= 5 THEN
        SET _p_cod_error = 4;
        SET _p_msj_error = 'El cliente ha llegado al límite de transferencias diarias.';
        LEAVE this_proc;
    END IF;
    --
    SELECT COUNT(1)
      INTO _v_existe
      FROM cliente c
     WHERE c.id_cliente = _p_id_cliente_reci_depo;
    --
    IF _v_existe = 0 THEN
        SET _p_cod_error = 5;
        SET _p_msj_error = CONCAT('El cliente con el id ', _p_id_cliente_reci_depo, ' no existe.');
        LEAVE this_proc;
    END IF;
    --
    SELECT nro_cuenta
      INTO _v_nro_cuenta
      FROM cliente
     WHERE id_cliente = _p_id_cliente_hace_depo;
    IF _v_nro_cuenta = _p_nro_cuenta_reci_depo THEN
        SET _p_cod_error = 6;
        SET _p_msj_error = 'No se puede depositar a si mismo.';
        LEAVE this_proc;
    END IF;
    --
    SELECT saldo
      INTO _v_saldo_hace
      FROM cliente
     WHERE id_cliente = _p_id_cliente_hace_depo;
    IF _p_monto > _v_saldo_hace THEN
        SET _p_cod_error = 7;
        SET _p_msj_error = 'El cliente no tiene el saldo suficiente.';
        LEAVE this_proc;
    END IF;
    --
    --
    SELECT saldo
      INTO _v_saldo_reci
      FROM cliente
     WHERE id_cliente = _p_id_cliente_reci_depo;
    --
    INSERT INTO movimiento (id_cliente, nro_cuenta, tipo_movi,tipo_mone, monto)
        VALUES (_p_id_cliente_hace_depo, _p_nro_cuenta_reci_depo, 'EGR',_p_tipo_mone, _p_monto);
    INSERT INTO movimiento (id_cliente, nro_cuenta, tipo_movi,tipo_mone, monto)
        VALUES (_p_id_cliente_reci_depo, _v_nro_cuenta, 'ING',_p_tipo_mone, _p_monto);
    UPDATE cliente SET saldo = ( _v_saldo_hace - _p_monto )
     WHERE id_cliente = _p_id_cliente_hace_depo;
    UPDATE cliente SET saldo = ( _v_saldo_reci + _p_monto )
     WHERE id_cliente = _p_id_cliente_reci_depo;
    SET _p_cod_error = 0;
    SET _p_msj_error = 'El depósito fue realizado correctamente.';
    --
    SELECT monto
      INTO _v_monto
      FROM movimiento
     WHERE id_cliente = _p_id_cliente_hace_depo;

     IF _p_tipo_mone = 'dolares' THEN
         SET _p_monto=(_p_monto*3.40);
    END IF;
END$$
DELIMITER ;                    
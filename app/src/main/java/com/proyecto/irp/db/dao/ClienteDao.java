package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.Cliente;

import java.util.List;

@Dao
public interface ClienteDao {
    @Insert
    void insert(Cliente cliente);

    @Update
    void update(Cliente cliente);

    @Delete
    void delete(Cliente cliente);

    @Query("DELETE FROM cliente")
    void deleteAllClientes();

    @Query("SELECT * FROM cliente order by idcliente ASC")
    LiveData<List<Cliente>> getAllClientes();

    //PARA VERIFICA SI YA EXISTE EL USUARIO Cliente POR NRO. DE CEDULA
    @Query("SELECT COUNT(*) FROM cliente where ruc = :id" )
    int verificaCedula(String id);

    @Query("SELECT * FROM cliente order by idcliente ASC")
    List<Cliente> getAllClienteCombo();

    //PARA VERIFICA EL CODIGO YA TIENE REFERENCIA EN OTRA TABLA
    @Query("SELECT COUNT(*) FROM facturaventa where id_cliente = :id" )
    int verificaCliente(int id);
}

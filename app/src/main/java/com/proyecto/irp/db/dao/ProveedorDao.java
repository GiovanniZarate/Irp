package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.Proveedor;

import java.util.List;

@Dao
public interface ProveedorDao {
    @Insert
    void insert(Proveedor proveedor);

    @Update
    void update(Proveedor proveedor);

    @Delete
    void delete(Proveedor proveedor);

    @Query("DELETE FROM proveedor")
    void deleteAllProveedor();

    @Query("SELECT * FROM proveedor order by idproveedor ASC")
    LiveData<List<Proveedor>> getAllProveedor();

    //PARA VERIFICA SI YA EXISTE EL USUARIO Cliente POR NRO. DE CEDULA
    @Query("SELECT COUNT(*) FROM proveedor where ruc = :id" )
    int verificaCedula(String id);


    @Query("SELECT * FROM proveedor order by idproveedor ASC")
    List<Proveedor> getAllProveedorCombo();
}

package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.db.entity.TipoEgreso;

import java.util.List;

@Dao
public interface TipoEgresoDao {
    @Insert
    void insert(TipoEgreso tipoegreso);

    @Update
    void update(TipoEgreso tipoegreso);

    @Delete
    void delete(TipoEgreso tipoegreso);

    @Query("SELECT * FROM tipoegreso order by idtipoegreso ASC")
    LiveData<List<TipoEgreso>> getAllTipoEgreso();


    @Query("SELECT * FROM tipoegreso order by idtipoegreso ASC")
    List<TipoEgreso> getAllTipoEgresoCombo();

    //PARA VERIFICA EL CODIGO YA TIENE REFERENCIA EN OTRA TABLA
    @Query("SELECT COUNT(*) FROM facturacompra where id_tipoegresocompra = :id" )
    int verificaTipoEgreso(int id);
}

package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.TipoComprobante;

import java.util.List;

@Dao
public interface TipoComprobanteDao {
    @Insert
    void insert(TipoComprobante tipoComprobante);

    @Update
    void update(TipoComprobante tipoComprobante);

    @Delete
    void delete(TipoComprobante tipoComprobante);

    @Query("SELECT * FROM tipocomprobante order by idtipocomprobante ASC")
    LiveData<List<TipoComprobante>> getAllTipoComprobante();

    @Query("SELECT * FROM tipocomprobante order by idtipocomprobante ASC")
    List<TipoComprobante> getAllTipoComprobanteCombo();


    //PARA VERIFICA EL CODIGO YA TIENE REFERENCIA EN OTRA TABLA
    @Query("SELECT COUNT(*) FROM facturaventa where id_comprobante = :id" )
    int verificaTipocomprobante(int id);
}

package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.Facturaventa;

import java.util.List;

@Dao
public interface FacturaVentaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Facturaventa facturaventa);

    @Update
    void update(Facturaventa facturaventa);

    @Delete
    void delete(Facturaventa facturaventa);


    @Query("SELECT facturaventa.*,tipocomprobante.descripcion_tipocomprobante,cliente.nombre,clasificacioningreso.descripcion_clasificacioningreso " +
            "FROM facturaventa " +
            "INNER JOIN tipocomprobante on facturaventa.id_comprobante=tipocomprobante.idtipocomprobante " +
            "INNER JOIN cliente on facturaventa.id_cliente=cliente.idcliente " +
            "INNER JOIN clasificacioningreso on facturaventa.id_clasificacioningreso=clasificacioningreso.idclasificacioningreso " +
            "order by idfacturaventa ASC")
    LiveData<List<Facturaventa>> getAllFacturaventa();



    /*@Query("SELECT clasificacionegreso.*,tipoegreso.descripcion_tipoegreso AS descripcion_tipoegreso
    FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.id_tipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();*/
}

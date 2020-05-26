package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.EstadisticaVentas;
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


    @Query("SELECT facturaventa.*,tipocomprobante.descripcion_tipocomprobante,cliente.nombre, " +
            "clasificacioningreso.descripcion_clasificacioningreso " +
            "FROM facturaventa " +
            "INNER JOIN tipocomprobante on facturaventa.id_comprobante=tipocomprobante.idtipocomprobante " +
            "INNER JOIN cliente on facturaventa.id_cliente=cliente.idcliente " +
            "INNER JOIN clasificacioningreso on facturaventa.id_clasificacioningreso " +
            "=clasificacioningreso.idclasificacioningreso " +
            "where facturaventa.id_contribuyente= :codcontribuyente " +
            "and facturaventa.id_ejercicio= :codejercicio " +
            "order by idfacturaventa ASC")
    LiveData<List<Facturaventa>> getAllFacturaventa(int codcontribuyente,int codejercicio);


    //PARA TRAER EL TOTAL DE VENTA PARA MOSTRAR EN EL GRAFICO

    @Query("SELECT anho,sum(total_venta) totalventa from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio " +
            "union " +
            "SELECT anho,1500000 totalventa from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio ")
    LiveData<List<EstadisticaVentas>> getTotalVenta(int codcontribuyente, int codejercicio);






    /*@Query("SELECT clasificacionegreso.*,tipoegreso.descripcion_tipoegreso AS descripcion_tipoegreso
    FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.id_tipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();*/
}

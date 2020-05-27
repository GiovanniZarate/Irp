package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.entity.Facturaventa;

import java.util.List;

@Dao
public interface FacturaCompraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Facturacompra facturacompra);

    @Update
    void update(Facturacompra facturacompra);

    @Delete
    void delete(Facturacompra facturacompra);


    @Query("SELECT facturacompra.*,tipocomprobante.descripcion_tipocomprobante,proveedor.nombre, " +
            "clasificacionegreso.descripcion, tipoegreso.descripcion_tipoegreso " +
            "FROM facturacompra " +
            "INNER JOIN tipocomprobante on facturacompra.id_comprobante=tipocomprobante.idtipocomprobante " +
            "INNER JOIN proveedor on facturacompra.id_proveedor=proveedor.idproveedor " +
            "INNER JOIN tipoegreso on facturacompra.id_tipoegresocompra=tipoegreso.idtipoegreso " +
            "INNER JOIN clasificacionegreso on facturacompra.id_clasificacionegreso " +
            "=clasificacionegreso.idclasificacionegreso " +
            "where facturacompra.id_contribuyente= :codcontribuyente " +
            "and facturacompra.id_ejercicio= :codejercicio " +
            "order by idfacturacompra ASC")
    LiveData<List<Facturacompra>> getAllFacturacompra(int codcontribuyente, int codejercicio);


    //PARA TRAER EL TOTAL DE VENTA PARA MOSTRAR EN EL GRAFICO

    /*@Query("SELECT anho,sum(total_venta) totalventa from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio " +
            "union " +
            "SELECT anho,1500000 totalventa from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio ")
    LiveData<List<EstadisticaVentas>> getTotalVenta(int codcontribuyente, int codejercicio);*/






    /*@Query("SELECT clasificacionegreso.*,tipoegreso.descripcion_tipoegreso AS descripcion_tipoegreso
    FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.id_tipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();*/
}

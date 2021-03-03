package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.ClasificacionIngreso;

import java.util.List;

@Dao
public interface ClasificacionIngresoDao {
    @Insert
    void insert(ClasificacionIngreso clasificacionIngreso);

    @Update
    void update(ClasificacionIngreso clasificacionIngreso);

    @Delete
    void delete(ClasificacionIngreso clasificacionIngreso);

    @Query("DELETE FROM clasificacioningreso")
    void deleteAllClasificaioningreso();

    @Query("SELECT * FROM clasificacioningreso order by idclasificacioningreso ASC")
    LiveData<List<ClasificacionIngreso>> getAllClasificacioningreso();

    @Query("SELECT * FROM clasificacioningreso order by idclasificacioningreso ASC")
    List<ClasificacionIngreso> getAllTipoIngresoCombo();

    //PARA VERIFICA EL CODIGO YA TIENE REFERENCIA EN OTRA TABLA
    @Query("SELECT COUNT(*) FROM facturaventa where id_clasificacioningreso = :id" )
    int verificaClasingreso(int id);


}

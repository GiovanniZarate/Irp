package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.ClasificacionEgreso;

import java.util.List;

@Dao
public interface ClasificacionEgresoDao {
    @Insert
    void insert(ClasificacionEgreso clasificacionEgreso);

    @Update
    void update(ClasificacionEgreso clasificacionEgreso);

    @Delete
    void delete(ClasificacionEgreso clasificacionEgreso);

    @Query("DELETE FROM clasificacionegreso")
    void deleteAllClasificaionEgreso();

    @Query("SELECT * FROM clasificacionegreso order by idclasificacionegreso ASC")
    LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();
}

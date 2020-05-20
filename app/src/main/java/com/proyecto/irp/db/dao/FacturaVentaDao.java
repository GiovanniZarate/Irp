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


    @Query("SELECT * FROM facturaventa order by idfacturaventa ASC")
    LiveData<List<Facturaventa>> getAllFacturaventa();
}

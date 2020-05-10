package com.proyecto.irp.db.dao;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.Contribuyente;

import java.util.List;

@Dao
public interface ContribuyenteDao {

    @Insert
    void insert(Contribuyente contribuyente);

    @Update
    void update(Contribuyente contribuyente);

    @Delete
    void delete(Contribuyente contribuyente);

    @Query("DELETE FROM contribuyente")
    void deleteAllContribuyentes();

    @Query("SELECT * FROM contribuyente order by idcontribuyente ASC")
    LiveData<List<Contribuyente>> getAllContribuyentes();


    //PARA VERIFICA SI YA EXISTE EL USUARIO CONTRIBUYENTE POR NRO. DE CEDULA
    @Query("SELECT COUNT(*) FROM contribuyente where documento = :id" )
    int verificaCedula(String id);

    //PARA ACCESO AL SISTEMA LOGIN
    @Query("SELECT * FROM contribuyente WHERE documento = :ci AND   contrasena = :password")
    LiveData<List<Contribuyente>> verificaLogin(String ci, String  password);



}

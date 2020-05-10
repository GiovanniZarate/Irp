package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.Ejercicio;

import java.util.List;

@Dao
public interface EjercicioDao {

    //aqui declaramos los metodos o las acciones para la bd
    //seleccionar cantidades
    @Query("SELECT COUNT(*) FROM " + Ejercicio.TABLE_NAME)
    int count(); //metodo

    //seleccionar todo
    @Query("SELECT * FROM "+Ejercicio.TABLE_NAME)
    List<Ejercicio> getAllEjercicios();

    //PARA USAR EL LIVEDATA: que es una clase para que la vista este actualizada en todo momento.
    @Query("SELECT * FROM "+Ejercicio.TABLE_NAME)
    LiveData<List<Ejercicio>> getAllEjerciciosLV();

    //insertar
    @Insert
    void insertarAll(Ejercicio ... ejercicios);

    //eliminar
    @Query("DELETE FROM " + Ejercicio.TABLE_NAME + " WHERE " + Ejercicio.COLUMN_ID + " = :ide")
    int deleteById(long ide);

    //actualizar
    @Update
    int update(Ejercicio obj);


    //insertar 2
    @Insert
    long insert(Ejercicio ejercicios);


    /*
    * @Query("SELECT * FROM nota")
    List<Nota> getNotas();

    @Query("SELECT * FROM nota WHERE mId LIKE :uuid")
    Nota getNota(String uuid);

    @Insert
    void addNota(Nota book);

    @Delete
    void deleteNota(Nota book);

    @Update
    void updateNota(Nota book);
    * */


    /*
    DOCUMENTACION OFICIAL:

    *@Query("SELECT * FROM user")
        List<User> getAll();

        @Query("SELECT * FROM user WHERE uid IN (:userIds)")
        List<User> loadAllByIds(int[] userIds);

        @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
               "last_name LIKE :last LIMIT 1")
        User findByName(String first, String last);

        @Insert
        void insertAll(User... users);

        @Delete
        void delete(User user);
    * */
    
}

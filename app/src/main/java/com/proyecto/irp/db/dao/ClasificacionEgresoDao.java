package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.ClasificacionEgreso;

import java.util.List;


@Dao
public interface ClasificacionEgresoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClasificacionEgreso clasificacionEgreso);

    @Update
    void update(ClasificacionEgreso clasificacionEgreso);

    @Delete
    void delete(ClasificacionEgreso clasificacionEgreso);

    @Query("DELETE FROM clasificacionegreso")
    void deleteAllClasificaionEgreso();

    /*@Query("SELECT * FROM clasificacionegreso order by idclasificacionegreso ASC")
    LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();*/

    /*@Query("SELECT * FROM playlist " +
            "INNER JOIN playlist_song_join " +
            "ON playlist.id=playlist_song_join.playlistId " +
            "WHERE playlist_song_join.songId=:songId")*/

    //PARA MOSTRAR RELACION
    @Query("SELECT clasificacionegreso.*,tipoegreso.descripcion_tipoegreso AS descripcion_tipoegreso FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.id_tipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();

    /*@Query("SELECT User.*, Organization.name AS org_name, Organization.id as org_id " +
            "FROM User INNER JOIN Organization " +
            "ON User.organization_id = Organization.id WHERE User.id = :id")*/



    @Query("SELECT * FROM clasificacionegreso order by idclasificacionegreso ASC")
    List<ClasificacionEgreso> getAllClasificacionegresoCombo();

    @Query("SELECT * FROM clasificacionegreso where id_tipoegreso=:cod order by idclasificacionegreso ASC")
    List<ClasificacionEgreso> getAllClasificacionegresoXTipoEgresoCombo(int cod);

   /* @Query("SELECT idclasificacionegreso AS vidclasificacionegreso, "
            +"clasificacionegreso.idtipoegreso AS vidtipoegreso,clasificacionegreso.descripcion AS vdescripcionclasegreso, "
            +" tipoegreso.descripcion AS vdescripcionegreso  FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.idtipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<Clasegreso>> getAllClasificacionegreso();

    // You can also define this class in a separate file, as long as you add the
    // "public" access modifier.
    static class Clasegreso {
        public int vidclasificacionegreso;
        public int vidtipoegreso;
        public String vdescripcionclasegreso;
        public String vdescripcionegreso;
    }*/

  /*  @DatabaseView("SELECT user.id, user.name, user.departmentId," +
            "department.name AS departmentName FROM user " +
            "INNER JOIN department ON user.departmentId = department.id")
    public class UserDetail {
        public long id;
        public String name;
        public long departmentId;
        public String departmentName;
    }*/

    //PARA VERIFICA EL CODIGO YA TIENE REFERENCIA EN OTRA TABLA
    @Query("SELECT COUNT(*) FROM facturacompra where id_clasificacionegreso = :id" )
    int verificaClasegreso(int id);


}

package com.main.getOpenData.DAO;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface BildingDao extends CrudRepository<Bilding,Long> {

    @Query(value = "select b from Bilding b " +
            "where (lat BETWEEN ?1 AND ?2) AND (lng BETWEEN  ?3 AND ?4)")
    List<Bilding> findByRadius(double latLeft,double latRight,double longBottom,double longTop);

    @Query(value ="SELECT id FROM Bilding WHERE lat=?1 AND lng=?2")
    long findByLatLng(double lat, double lng);

    @Query(value ="SELECT b FROM Bilding b WHERE id=?1")
    Bilding findById(long id);

}

package com.main.getOpenData.DAO;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KindergardenDao extends CrudRepository<Kindergarden,Long> {

    @Query(value ="SELECT k FROM Kindergarden k WHERE id_bilding=?1")
    List<Kindergarden> findByIdBilding(long id);

}

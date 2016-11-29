package com.main.getOpenData.DAO;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicalFacilityDao extends CrudRepository<MedicalFacility,Long> {

    @Query(value ="SELECT m FROM MedicalFacility m WHERE id_bilding = ?1")
    List<MedicalFacility> findByIdBilding(long id_bilding);

}

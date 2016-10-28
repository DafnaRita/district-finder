package com.main.getOpenData.DAO;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CompanyDao extends CrudRepository<Company,Long> {

}

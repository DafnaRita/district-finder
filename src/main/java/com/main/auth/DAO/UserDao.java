package com.main.auth.DAO;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity UserHebirnate is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface UserDao extends CrudRepository<UserHebirnate, Long> {

  /**
   * Return the user having the passed email or null if no user is found.
   * 
   * @param login the user email.
   */
  public UserHebirnate findByLogin(String login);
} // class UserDao

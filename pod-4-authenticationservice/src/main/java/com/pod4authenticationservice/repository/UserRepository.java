package com.pod4authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pod4authenticationservice.entity.UserCredential;

import java.util.Optional;

/**
 * This is user Repository Interface
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserCredential, Long> {
   /**
 * @param userName
 * @return
 * 
 * this is use for finding User By userName.
 */
 Optional<UserCredential> findByUserName(String userName);

    /**
     * @param userName
     * @return
     * 
     * this is use for checking user exist or not.
     */
    Boolean existsByUserName(String userName);
}

package com.metro.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metro.entity.UserMetro;

@Repository
public interface MetroDao extends JpaRepository<UserMetro, String> {

	//search record by username 
	public UserMetro searchUserMetroByUserName(String userName);
	
	//update balance- for adding money (when swipping out, need to give a 
	//neagive value for increment)
	@Transactional
	@Modifying
	@Query("update UserMetro set balance = balance + :b where userName = :un")
	int updateBalance(@Param("un") String userName, @Param("b") double balance); 
	
}

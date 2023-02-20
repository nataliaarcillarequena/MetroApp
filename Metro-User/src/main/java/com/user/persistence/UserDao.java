package com.user.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String>{
	
	//find by userName is already existing- this is just findById can be used straight in service layer
	
	//check credentials for when existing user is logging in
	public User findByUserNameAndPassword(String userName, String password);
	
	//insert record for registering user- can do UserDao.save() in service layer- saves and updates 

	//insert via native query
	@Modifying
	@Transactional
	@Query(value = "insert into user values(:na, :sn, :id, :pass, :bal)", nativeQuery = true)
	int insertUser(@Param("na") String name, @Param("sn") String surname, 
			@Param("id") String userName, @Param("pass") String password, 
			@Param("bal") double balance);
	
//	//search dao by username
	public User searchUserByUserName(String username);
	
}

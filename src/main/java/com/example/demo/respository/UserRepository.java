package com.example.demo.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository(value = "UserRepository")
// kế thừa jpa nên sẽ có sẵn các hàm  method 
public interface UserRepository extends JpaRepository<User, Long> {
//	
	Optional<User> findByName(String name);
//	List<User> findByFull_name(String full_name);
//	c2 
//	List<User> findByFull_nameContaining(String full_name);
//	c3 dùng % phải sửa trong controll thêm dấu %
//	List<User> findByFull_nameLike(String full_name);
//	c4 dùng sql thuan phải sửa trong controll thêm dấu %
//	@Modifying
//	@Query(nativeQuery = true,value="select * where user_name like :full_name")
//	List<User> findByFull_nameSQL(String full_name);
}

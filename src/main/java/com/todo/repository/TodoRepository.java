package com.todo.repository;

import com.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo , Long > {

    boolean existsByTitleAndUsernameAndDeletedFalse(String title, String username);

    @Query(value = "select * from todos where username = :username and deleted = :isDeleted " , nativeQuery = true)
    List<Todo> findByTodoByUserName( String username , boolean isDeleted);

    @Query(value = "select * from todos where id = :id and deleted = :isDeleted " , nativeQuery = true)
    Todo findById(@Param("id") Long id ,@Param("isDeleted") boolean isDeleted);

    @Query(value = "SELECT views_access_usernames FROM todos WHERE username = :username AND deleted = :isDeleted", nativeQuery = true)
    String findViewAccessByUsernameAndDeleted(@Param("username") String username, @Param("isDeleted") boolean isDeleted);

}

package com.group.backend.repository;

import com.group.backend.entity.User;
import com.group.backend.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role AND MONTH(u.createdAt) = :month AND YEAR(u.createdAt) = :year")
    Long countStudentByMonthAndYear(@Param("month") int month, @Param("year") int year, @Param("role") Role role);
}

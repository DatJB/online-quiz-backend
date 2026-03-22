package com.group.backend.repository.admin;

import com.group.backend.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
    @EntityGraph(attributePaths = "user")
    @Query("""
        SELECT s FROM Student s
        WHERE LOWER(s.user.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(s.studentCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(s.user.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Student> findByKeyword(String keyword, Pageable pageable);
}

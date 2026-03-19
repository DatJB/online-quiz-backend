package com.group.backend.repository;

import com.group.backend.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentOptionRepository extends JpaRepository<Option, Integer>
{
}

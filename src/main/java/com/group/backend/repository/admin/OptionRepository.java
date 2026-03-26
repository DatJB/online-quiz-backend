package com.group.backend.repository.admin;

import com.group.backend.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    List<Option> findByQuestionId(int questionId);
}

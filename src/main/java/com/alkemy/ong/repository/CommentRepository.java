package com.alkemy.ong.repository;

import com.alkemy.ong.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByNewsId(Long id);
    List<Comment> findAllByOrderByCreateAtAsc();
}

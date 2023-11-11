package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}

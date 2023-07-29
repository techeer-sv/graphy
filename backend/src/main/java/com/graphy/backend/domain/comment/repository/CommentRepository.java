package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.dto.ReplyListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

    @Query(value =
            "SELECT " +
                    "   CASE " +
                    "       WHEN parent.is_deleted = TRUE THEN '삭제된 댓글입니다.' " +
                    "       ELSE parent.content " +
                    "   END AS content, " +
                    "   parent.comment_id AS commentId, " +
                    "   parent.created_at AS createdAt, " +
                    "   member.nickname AS nickname, " +
                    "   COUNT(child.comment_id) AS childCount " +
                    "FROM comment AS parent " +
                    "LEFT JOIN comment AS child ON parent.comment_id = child.parent_id " +
                    "INNER JOIN member ON parent.member_id = member.id " +
                    "WHERE parent.parent_id IS NULL AND parent.project_id = :id " +
                    "GROUP BY parent.comment_id", nativeQuery = true)
    List<CommentWithMaskingDto> findCommentsWithMasking(Long id);


    @Query(value =
            "SELECT " +
                    "   CASE " +
                    "       WHEN comment.is_deleted = TRUE THEN '삭제된 댓글입니다.' " +
                    "       ELSE comment.content " +
                    "   END AS content, " +
                    "   comment.comment_id AS commentId, " +
                    "   comment.created_at AS createdAt, " +
                    "   member.nickname AS nickname " +
                    "FROM comment " +
                    "INNER JOIN member ON comment.member_id = member.id " +
                    "WHERE comment.parent_id = :parentId " +
                    "ORDER BY comment.created_at ASC", nativeQuery = true)
    List<ReplyListDto> findReplyList(Long parentId);
}

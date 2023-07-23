package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.dto.ReplyListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

    @Query(value = "SELECT CASE\n" +
            "           WHEN parent.is_deleted\n" +
            "               THEN '삭제된 댓글입니다.'\n" +
            "           ELSE parent.content\n" +
            "           END                 AS content,\n" +
            "       parent.comment_id       AS commentId,\n" +
            "       parent.created_at       AS createdAt,\n" +
            "       member.nickname         AS nickname,\n" +
            "       COUNT(child.comment_id) AS childCount\n" +
            "FROM (SELECT *\n" +
            "      FROM comment\n" +
            "      WHERE parent_id IS NULL\n" +
            "        AND project_id = :id) AS parent\n" +
            "         LEFT JOIN\n" +
            "     comment AS child\n" +
            "     ON\n" +
            "         parent.comment_id = child.parent_id\n" +
            "         JOIN\n" +
            "     member\n" +
            "     ON\n" +
            "         parent.member_id = member.id\n" +
            "GROUP BY parent.comment_id;\n", nativeQuery = true)
    List<CommentWithMaskingDto> findCommentsWithMasking(@Param("id") Long id);

    @Query(value = "SELECT CASE\n" +
            "           WHEN comment.is_deleted\n" +
            "               THEN '삭제된 댓글입니다.'\n" +
            "           ELSE comment.content\n" +
            "           END         AS content,\n" +
            "       comment_id      AS commentId,\n" +
            "       comment.created_at      AS createdAt,\n" +
            "       member.nickname AS nickname\n" +
            "FROM comment\n" +
            "         JOIN member ON comment.member_id = member.id\n" +
            "WHERE parent_id = :parentId\n" +
            "ORDER BY comment.created_at ASC", nativeQuery = true)
    List<ReplyListDto> findReplyList(@Param("parentId") Long parentId);

}

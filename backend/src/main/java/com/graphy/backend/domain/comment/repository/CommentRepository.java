package com.graphy.backend.domain.comment.repository;

import com.graphy.backend.domain.comment.domain.Comment;
import com.graphy.backend.domain.comment.dto.CommentWithMaskingDto;
import com.graphy.backend.domain.comment.dto.ReplyListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

    @Query(value = "SELECT\n" +
            "    CASE\n" +
            "           WHEN parent.is_deleted\n" +
            "               THEN '삭제된 댓글입니다.'\n" +
            "           ELSE parent.content\n" +
            "           END                 AS content,\n" +
            "       parent.comment_id AS commentId,\n" +
            "       parent.created_at AS createdAt, \n" +
            "       COUNT(child.comment_id) AS childCount\n" +
            "FROM (SELECT *\n" +
            "      FROM comment\n" +
            "      WHERE parent_id IS NULL\n" +
            "        AND project_id = :id) AS parent\n" +
            "         LEFT JOIN\n" +
            "     comment AS child\n" +
            "     ON\n" +
            "         parent.comment_id = child.parent_id\n" +
            "GROUP BY parent.comment_id", nativeQuery = true)
    List<CommentWithMaskingDto> findCommentsWithMasking(Long id);

@Query(value = "SELECT\n" +
        "    CASE\n" +
        "        WHEN is_deleted\n" +
        "            THEN '삭제된 댓글입니다.'\n" +
        "        ELSE content\n" +
        "        END AS content,\n" +
        "    comment_id AS commentId,\n" +
        "    created_at AS createdAt\n" +
        "FROM comment\n" +
        "WHERE parent_id = :parentId", nativeQuery = true)
     List<ReplyListDto> findReplyList(Long parentId);
}

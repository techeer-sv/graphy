package com.graphy.backend.domain.comment.controller;

import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.graphy.backend.domain.comment.dto.CommentDto.*;

@Tag(name = "CommentController", description = "댓글 관련 API")
@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "createComment", description = "댓글 생성")
    @PostMapping
    public ResponseEntity<ResultResponse> createComment(@Validated @RequestBody CreateCommentRequest dto) {
        CreateCommentResponse response = commentService.createComment(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.COMMENT_CREATE_SUCCESS, response));
    }

    @Operation(summary = "updateComment", description = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<ResultResponse> updateComment(@Validated @RequestBody UpdateCommentRequest dto, @PathVariable Long commentId) {
        commentService.updateComment(commentId, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.COMMENT_UPDATE_SUCCESS));
    }

    @Operation(summary = "deleteComment", description = "댓글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.COMMENT_DELETE_SUCCESS));
    }
}

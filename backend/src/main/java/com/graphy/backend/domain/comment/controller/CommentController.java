package com.graphy.backend.domain.comment.controller;

import com.graphy.backend.domain.comment.dto.request.CreateCommentRequest;
import com.graphy.backend.domain.comment.dto.request.UpdateCommentRequest;
import com.graphy.backend.domain.comment.dto.response.CreateCommentResponse;
import com.graphy.backend.domain.comment.dto.response.GetReplyListResponse;
import com.graphy.backend.domain.comment.service.CommentService;
import com.graphy.backend.domain.member.domain.Member;
import com.graphy.backend.global.auth.jwt.annotation.CurrentUser;
import com.graphy.backend.global.result.ResultCode;
import com.graphy.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Tag(name = "CommentController", description = "댓글 관련 API")
@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "createComment", description = "댓글 생성")
    @PostMapping
    public ResponseEntity<ResultResponse> commentAdd(@Validated @RequestBody CreateCommentRequest dto,
                                                     @CurrentUser Member loginUser) {
        commentService.addComment(dto, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultResponse.of(ResultCode.COMMENT_CREATE_SUCCESS));
    }

    @Operation(summary = "updateComment", description = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<ResultResponse> commentUpdate(@Validated @RequestBody UpdateCommentRequest dto,
                                                        @PathVariable Long commentId,
                                                        @CurrentUser Member loginUser) {
        commentService.modifyComment(commentId, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.COMMENT_UPDATE_SUCCESS));
    }

    @Operation(summary = "deleteComment", description = "댓글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> commentDelete(@PathVariable Long id,
                                                        @CurrentUser Member loginUser) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.COMMENT_DELETE_SUCCESS));
    }

    @Operation(summary = "getReComment", description = "답글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<ResultResponse> commentDetails(@PathVariable Long commentId) {
        List<GetReplyListResponse> result = commentService.findCommentList(commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResultResponse.of(ResultCode.RECOMMENT_GET_SUCCESS, result));
    }
}

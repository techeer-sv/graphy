package com.graphy.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/** {주체}_{이유} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "서버 오류"),
  INPUT_INVALID_VALUE(HttpStatus.BAD_REQUEST, "G002", "잘못된 입력"),

  // Auth
  INPUT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않는 토큰"),
  TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "A002", "존재하지 않는 토큰"),

  // Member
  MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 사용자"),
  MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 이메일"),
  INVALID_MEMBER(HttpStatus.FORBIDDEN, "M003", "권한이 없는 사용자"),

  // Follow
  FOLLOW_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "F001", "이미 존재하는 팔로우"),
  FOLLOW_NOT_EXIST(HttpStatus.NOT_FOUND, "M002", "존재하지 않는 팔로우"),
  // Project
  PROJECT_DELETED_OR_NOT_EXIST(HttpStatus.NOT_FOUND, "P001", "이미 삭제되거나 존재하지 않는 프로젝트"),

  // Comment
  COMMENT_DELETED_OR_NOT_EXIST(HttpStatus.NOT_FOUND, "C001", "이미 삭제되거나 존재하지 않는 댓글"),

  // Recruitment
  RECRUITMENT_NOT_EXIST(HttpStatus.NOT_FOUND, "R001", "존재하지 않는 구인 게시글"),

  // ChatGPT,
  REQUEST_TOO_MUCH_TOKENS(HttpStatus.BAD_REQUEST, "AI001", "GPT에 보내야 할 요청 길이 제한 초과");


  private final HttpStatus status;
  private final String errorCode;
  private final String message;
}

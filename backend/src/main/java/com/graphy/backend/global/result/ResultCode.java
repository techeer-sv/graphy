package com.graphy.backend.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 도메인 별로 나눠서 관리(ex: User 도메인)
    // user

    // project
    PROJECT_CREATE_SUCCESS("P001", "프로젝트 생성 성공"),
    PROJECT_GET_SUCCESS("P002", "프로젝트 조회 성공"),
    PROJECT_DELETE_SUCCESS("P003", "프로젝트 삭제 성공"),
    PROJECT_UPDATE_SUCCESS("P004", "프로젝트 수정 성공"),
    PROJECT_PAGING_GET_SUCCESS("P005", "프로젝트 페이징 조회 성공"),

    // comment
    RECOMMENT_GET_SUCCESS("C001", "답글 조회 성공"),
    COMMENT_CREATE_SUCCESS("C002", "댓글 생성 성공"),
    COMMENT_DELETE_SUCCESS("C003", "댓글 삭제 성공"),
    COMMENT_UPDATE_SUCCESS("C004", "댓글 수정 성공"),

    // plan
    PLAN_CREATE_SUCCESS("PL001", "고도화 계획 생성 성공"),

    ;

    private final String code;
    private final String message;
}
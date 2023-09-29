package com.graphy.backend.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    // Auth
    AUTH_SIGNUP_SUCCESS("A001", "사용자 등록 성공"),
    AUTH_SIGNIN_SUCCESS("A002", "로그인 성공"),
    AUTH_LOGOUT_SUCCESS("A003", "로그아웃 성공"),
    AUTH_REISSUE_SUCCESS("A004", "토큰 재발급 성공"),

    // Member
    MEMBER_GET_SUCCESS("M001", "사용자 조회 성공"),
    MYPAGE_GET_SUCCESS("M002", "마이페이지 조회 성공"),


    // Follow
    FOLLOWER_GET_SUCCESS("F001", "팔로워 조회 성공"),
    FOLLOWING_CREATE_SUCCESS("F002", "팔로잉 성공"),
    FOLLOW_DELETE_SUCCESS("F003", "언팔로잉 성공"),


    // Like
    LIKE_PROJECT_SUCCESS("L001", "프로젝트 좋아요/좋아요 취소 성공"),
    LIKED_MEMBER_GET_SUCCESS("L002", "좋아요 누른 유저 조회 성공"),

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

    // Recruitment
    RECRUITMENT_CREATE_SUCCESS("R001", "구인 게시글 생성 성공"),
    RECRUITMENT_GET_SUCCESS("R002", "구인 게시글 조회 성공"),
    RECRUITMENT_DELETE_SUCCESS("R003", "구인 게시글 삭제 성공"),
    RECRUITMENT_UPDATE_SUCCESS("R004", "구인 게시글 수정 성공"),
    RECRUITMENT_PAGING_GET_SUCCESS("R005", "구인 게시글 페이징 조회 성공"),

    // plan,
    PLAN_CREATE_SUCCESS("PL001", "고도화 계획 생성 성공");




    private final String code;
    private final String message;
}
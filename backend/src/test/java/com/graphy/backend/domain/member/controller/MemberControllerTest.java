package com.graphy.backend.domain.member.controller;

import com.graphy.backend.domain.member.service.MemberService;
import com.graphy.backend.test.MockApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.web.context.WebApplicationContext;

import static com.graphy.backend.domain.member.dto.MemberDto.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

@WebMvcTest(MemberControllerTest.class)
@ExtendWith(RestDocumentationExtension.class)
public class MemberControllerTest extends MockApiTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    MemberService memberService;
    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {
        this.mvc = buildMockMvc(context, provider);
    }
    private static String baseUrl = "/api/v1/members";
    
    @Test
//    @WithAuthUser
    @DisplayName("회원가입이 된다")
    public void joinTest() throws Exception {
        //given
        LoginMemberRequest request = LoginMemberRequest.builder()
                .email("email")
                .password("pwd")
                .build();

        //when
        mvc.perform(get(baseUrl + "join"));
//                .andExpect(status.)
        
        
        //then
    }
}

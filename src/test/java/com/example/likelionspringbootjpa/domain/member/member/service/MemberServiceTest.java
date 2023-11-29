package com.example.likelionspringbootjpa.domain.member.member.service;

import com.example.likelionspringbootjpa.domain.member.member.entity.Member;
import com.example.likelionspringbootjpa.global.rsData.RsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @DisplayName("member join")
    @Test
    void t1() {
        RsData<Member> joinRs = memberService.join("usertest", "1234");
        Member member = joinRs.getData();

        assertThat(member.getId()).isGreaterThan(0L);
    }
}

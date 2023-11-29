package com.example.likelionspringbootjpa.global.initData;

import com.example.likelionspringbootjpa.domain.member.member.entity.Member;
import com.example.likelionspringbootjpa.domain.member.member.service.MemberService;
import com.example.likelionspringbootjpa.global.rsData.RsData;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Configuration
public class NotProd {
    @Bean
    public ApplicationRunner initNotProdData(
            MemberService memberService
    ) {
        return args -> {
            RsData<Member> joinRs = memberService.join("user1", "1234");

            System.out.println(joinRs.getMsg());
        };
    }
}

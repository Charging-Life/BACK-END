package com.example.charging_life.member;

import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.dto.LoginReqDto;
import com.example.charging_life.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final JpaMemberRepo jpaMemberRepo;

    public void join(Member member) {
        jpaMemberRepo.save(member);
    }

    public Member findMemberByEmail(String email) {
        Member member = jpaMemberRepo.findByEmail(email)
                .orElseThrow(()->new CustomException(ExceptionEnum.EmailNotMatched));
        return member;
    }

    @Override // UserDetailsService 를 상속하여 해당 메소드를 구현해야지만 사용가능하다.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return jpaMemberRepo.findByEmail(email)
                .orElseThrow(()->new CustomException(ExceptionEnum.EmailNotMatched));
    }
}

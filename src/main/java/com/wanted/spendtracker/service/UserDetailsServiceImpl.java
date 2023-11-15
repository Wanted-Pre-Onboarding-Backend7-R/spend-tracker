package com.wanted.spendtracker.service;

import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.exception.CustomException;
import com.wanted.spendtracker.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.wanted.spendtracker.exception.ErrorCode.AUTH_MEMBER_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccountName(accountName)
                .orElseThrow(() -> new CustomException(AUTH_MEMBER_NOT_EXISTS));
        return MemberAdapter.from(member);
    }

}

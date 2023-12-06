package com.wanted.spendtracker.domain.member.repository;

import com.wanted.spendtracker.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountName(String accountName);

}

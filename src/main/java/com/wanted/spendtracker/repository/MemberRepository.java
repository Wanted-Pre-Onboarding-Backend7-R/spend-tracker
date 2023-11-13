package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountName(String accountName);

}

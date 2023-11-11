package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

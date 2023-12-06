package com.wanted.spendtracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA에 관련된 설정
 * EnableJpaAuditing 을 통해, CreatedDate 등 사용 가능하게 설정
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

}

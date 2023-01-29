package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Member;
import com.msgrserver.model.entity.chat.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
    Set<Member> findMembersByChatId(Long chatId);

    Set<Member> findMembersByUserId(Long userId);

    void deleteByChatId(Long chatId);

    Optional<Member> findByChatIdAndUserId(Long chatId, Long userId);
}

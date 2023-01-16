package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Member;
import com.msgrserver.model.entity.chat.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {

    @Query(value = "SELECT * FROM member m WHERE chat_id=?1", nativeQuery = true)
    Set<Member> findMembersByChatId(Long chatId);
}

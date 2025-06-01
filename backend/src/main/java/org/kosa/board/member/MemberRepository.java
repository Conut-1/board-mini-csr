package org.kosa.board.member;

import java.util.Optional;

import org.kosa.board.member.summary.MemberDetailSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Page<Member> findByIdContaining(String searchValue, Pageable pageable);
    Optional<Member> findById(String id);
    Optional<MemberDetailSummary> findSummaryById(String id);
}

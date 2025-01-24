package com.freeboard.project.freeboard.repository;

import com.freeboard.project.freeboard.entity.FreeBoard;
import com.freeboard.project.security.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    FreeBoard findByMemberAndId(Member member, Long freeBoardId);

}

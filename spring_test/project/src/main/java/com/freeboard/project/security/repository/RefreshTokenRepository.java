package com.freeboard.project.security.repository;

import com.freeboard.project.security.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByMemberId(Long memberId);
}

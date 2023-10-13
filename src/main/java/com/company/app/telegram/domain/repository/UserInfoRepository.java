package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}

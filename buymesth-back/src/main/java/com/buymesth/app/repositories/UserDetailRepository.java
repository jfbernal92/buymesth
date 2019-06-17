package com.buymesth.app.repositories;

import com.buymesth.app.models.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}

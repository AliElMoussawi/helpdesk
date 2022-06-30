package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<ModelUserGroup, Long> {

    List<ModelUser> findAllByGroupId(Long groupId);

    ModelUserGroup findById(long id);

    ModelUserGroup findByUserId(Long id);
}

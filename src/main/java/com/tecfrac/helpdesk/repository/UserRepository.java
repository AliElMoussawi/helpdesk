package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Integer> {

    ModelUser findByUsername(String username);

    ModelUser findById(long id);

    ModelUser findByEmail(String email);

    public ModelUser findUserByEmail(String userEmail);

    public List<ModelUser> findAllByUserTypeId(int i, int j);
}

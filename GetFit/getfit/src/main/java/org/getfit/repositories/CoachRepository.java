package org.getfit.repositories;

import org.getfit.entities.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, String> {
    Coach findByUsername(String username);

}

package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

}

package com.example.userservice.Repository;

import com.example.userservice.Redis.JwtSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtSessionRepository extends CrudRepository<JwtSession, String> {
}

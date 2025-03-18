package com.example.orderservice.Repository;

import com.example.orderservice.Redis.JwtSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtSessionRepository extends CrudRepository<JwtSession, String> {
}

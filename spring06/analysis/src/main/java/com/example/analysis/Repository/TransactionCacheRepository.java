package com.example.analysis.Repository;

import com.example.analysis.Redis.CachedTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionCacheRepository extends CrudRepository<CachedTransaction, UUID> {
}

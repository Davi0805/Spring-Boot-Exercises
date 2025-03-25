package com.example.analysis.Service;

import com.example.analysis.DTO.TransactionDTO;
import com.example.analysis.Redis.CachedTransaction;
import com.example.analysis.Repository.TransactionCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionAnalyser {
    private final TransactionCacheRepository redisCursor;

    @Autowired
    public TransactionAnalyser(TransactionCacheRepository cursor)
    {
        this.redisCursor = cursor;
    }

    public void run(TransactionDTO transaction)
    {
        redisCursor.save(new CachedTransaction(transaction));

        Optional<CachedTransaction> current = redisCursor
                .findByUserIdAndAmount(transaction.getUser_id(), transaction.getAmount());


        if (current.get().getAmount() > 2000 && current != null)
            throw new RuntimeException("Transacao acima do limite");
        else if (current != null)
            throw new RuntimeException("Transacao duplicada");
    }
}

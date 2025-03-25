package com.example.analysis.Service;

import com.example.analysis.DTO.TransactionDTO;
import com.example.analysis.Redis.CachedTransaction;
import com.example.analysis.Repository.TransactionCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Regras a ser implementado para gerar alerta
// 1 - Checar transacao duplicada com
// range de 1-5 min
// 2 - Transacoes acima de 2000

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
        Optional<CachedTransaction> current = redisCursor
                .findByUserIdAndAmount(transaction.getUser_id(), transaction.getAmount());

        redisCursor.save(new CachedTransaction(transaction));


        if (current.isPresent())
            throw new RuntimeException("1");
        else if (transaction.getAmount() > 2000)
            throw new RuntimeException("2");
    }
}

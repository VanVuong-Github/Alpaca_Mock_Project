package com.alpaca.Alpaca_Mock_Project.cacheService;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomerCacheService {
    Customer getCacheById(final Long id);
    Page<Customer> getAllCache(Pageable pageable);
    void putCache(final Customer customer);
    void putAllCache(final Map<Object, Object> customerMap);
    void deleteCache(final Long id);
}

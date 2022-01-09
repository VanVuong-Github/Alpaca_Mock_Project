package com.alpaca.Alpaca_Mock_Project.cacheService;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerCacheService {
    Object getCacheById(final Long id);
    List<Object> getAllCache();
    void putCache(final Customer customer);
    void putAllCache(final Map<Object, Object> customerMap);
    void deleteCache(final Long id);
}

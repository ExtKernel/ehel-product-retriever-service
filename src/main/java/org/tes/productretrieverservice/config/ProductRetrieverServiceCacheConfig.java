package org.tes.productretrieverservice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class ProductRetrieverServiceCacheConfig {

    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(500)
                .expireAfterWrite(Duration.ofMinutes(10));
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("EbayItemsCache", "EbayItemCache");
        cacheManager.setCaffeine(caffeineCacheBuilder());

        return cacheManager;
    }
}

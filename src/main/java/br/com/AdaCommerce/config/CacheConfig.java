package br.com.AdaCommerce.config;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.github.benmanes.caffeine.cache.Caffeine;


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("clientes", "produtos", "pedidos");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(500));
        return cacheManager;
    }

    @Bean
    public CacheManager pesquisaCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("produtos-pesquisa");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .maximumSize(1000));
        return cacheManager;
    }
}
package com.musinsa.product.global.service.redis;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RedisZSetService {

    private final RedisTemplate<String, String> redisTemplate;

    @Nullable
    public String getLowestScoreMember(@NotNull String key)
    {
        Set<String> members = redisTemplate.opsForZSet().range(key, 0, 0);
        return findOneInSetElements(members);
    }

    @Nullable
    public String getHighestScoreMember(@NotNull String key)
    {
        Set<String> members = redisTemplate.opsForZSet().range(key, -1, -1);
        return findOneInSetElements(members);
    }

    @Nullable
    public Long getLowestMemberPrice(@NotNull String key)
    {
        String lowestScoreMember = getLowestScoreMember(key);

        if (lowestScoreMember == null)
        {
            return null;
        }

        return getMemberScore(key, lowestScoreMember);
    }

    @Nullable
    public Long getMemberScore(String key, String member)
    {
        Double score = redisTemplate.opsForZSet().score(key, member);

        if (score == null)
        {
            return null;
        }

        return score.longValue();
    }

    private static String findOneInSetElements(Set<String> elements)
    {
        if (CollectionUtils.isEmpty(elements))
        {
            return null;
        }

        return elements.stream()
                .findFirst()
                .orElse(null);
    }
}

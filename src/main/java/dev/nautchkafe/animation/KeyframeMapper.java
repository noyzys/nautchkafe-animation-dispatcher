package dev.nautchkafe.animation;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.vavr.Function1;
import io.vavr.control.Option;

import java.util.concurrent.TimeUnit;

/**
 * The {@code KeyframeMapper} class is responsible for managing a cache of keyframes.
 * This class provides the functionality to retrieve a keyframe from the cache or create
 * a new one if it does not exist, using the provided keyframe creator function.
 */
final class KeyframeMapper {

    private final Cache<Integer, Keyframe> keyframeCache;

    KeyframeMapper() {
        keyframeCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }

    /**
     * Retrieves a keyframe from the cache using the given index. If the keyframe
     * is not present in the cache, it utilizes the provided keyframe creator function
     * to create and cache the keyframe before returning it.
     *
     * @param index the index of the keyframe to retrieve or create
     * @param keyframeCreator a function that creates a keyframe for a given index
     * @return the retrieved or created keyframe
     */
    public Keyframe getOrCache(final int index, final Function1<Integer, Keyframe> keyframeCreator) {
        return Option.of(keyframeCache.getIfPresent(index))
                .getOrElse(() -> {
                    final Keyframe keyframe = keyframeCreator.apply(index);

                    keyframeCache.put(index, keyframe);
                    return keyframe;
                });
    }
}

package com.enonic.cms.plugin.confluence.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public final class CacheStore<K, V>
    extends LinkedHashMap<K, V>
{
    private final int maxSize;

    public CacheStore( int maxSize )
    {
        this.maxSize = maxSize;
    }

    protected boolean removeEldestEntry( Map.Entry eldest )
    {
        return size() > this.maxSize;
    }
}

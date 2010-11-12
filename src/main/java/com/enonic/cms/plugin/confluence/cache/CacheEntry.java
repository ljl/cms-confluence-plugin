package com.enonic.cms.plugin.confluence.cache;

public final class CacheEntry<T>
{
    private final long timestamp;

    private final T value;

    public CacheEntry( T value )
    {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public T getValue()
    {
        return this.value;
    }

    public boolean expired( long timeout )
    {
        return System.currentTimeMillis() > ( this.timestamp + timeout );
    }
}

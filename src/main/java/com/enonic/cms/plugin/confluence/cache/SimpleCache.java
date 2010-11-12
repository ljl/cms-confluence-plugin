package com.enonic.cms.plugin.confluence.cache;

public final class SimpleCache<T>
{
    private final CacheStore<String, CacheEntry<T>> store;

    private final long timeout;

    public SimpleCache( int maxSize, long timeout )
    {
        this.store = new CacheStore<String, CacheEntry<T>>( maxSize );
        this.timeout = timeout;
    }

    public synchronized T get( String key )
    {
        CacheEntry<T> entry = this.store.get( key );
        if ( entry == null )
        {
            return null;
        }

        if ( entry.expired( this.timeout ) )
        {
            this.store.remove( key );
            return null;
        }

        return entry.getValue();
    }

    public synchronized void put( String key, T value )
    {
        this.store.put( key, new CacheEntry<T>( value ) );
    }
}

package com.enonic.cms.plugin.confluence.service;

import com.enonic.cms.plugin.confluence.cache.SimpleCache;
import com.enonic.cms.plugin.confluence.model.FileModel;
import com.enonic.cms.plugin.confluence.model.SpaceModel;

public final class CachedWikiDocService
    implements WikiDocService
{
    private WikiDocService service;

    private SimpleCache<Object> cache;

    private long timeout;

    private int maxSize;

    private boolean enabled = true;

    public void setTimeout( int timeout )
    {
        this.timeout = timeout * 1000L;
    }

    public void setMaxSize( int maxSize )
    {
        this.maxSize = maxSize;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    public void setService( WikiDocService service )
    {
        this.service = service;
    }

    private synchronized Object getFromCache( String key )
    {
        if ( this.enabled )
        {
            if ( this.cache == null )
            {
                this.cache = new SimpleCache<Object>( this.maxSize, this.timeout );
            }

            return this.cache.get( key );
        }
        else
        {
            return null;
        }
    }

    private void putInCache( String key, Object value )
    {
        if ( this.enabled )
        {
            this.cache.put( key, value );
        }
    }

    public SpaceModel getSpace( String spaceKey )
        throws Exception
    {
        SpaceModel cached = (SpaceModel) getFromCache( spaceKey );
        if ( cached == null )
        {
            cached = this.service.getSpace( spaceKey );
            putInCache( spaceKey, cached );
        }

        return cached;
    }

    public String renderPage( String spaceKey, String pageId )
        throws Exception
    {
        String cacheKey = spaceKey + ":" + pageId;
        String cached = (String) getFromCache( cacheKey );
        if ( cached == null )
        {
            cached = this.service.renderPage( spaceKey, pageId );
            putInCache( cacheKey, cached );
        }

        return cached;
    }

    public FileModel getFile( String pageId, String fileName )
        throws Exception
    {
        String cacheKey = pageId + ":" + fileName;
        FileModel cached = (FileModel) getFromCache( cacheKey );
        if ( cached == null )
        {
            cached = this.service.getFile( pageId, fileName );
            putInCache( cacheKey, cached );
        }

        return cached;
    }
}

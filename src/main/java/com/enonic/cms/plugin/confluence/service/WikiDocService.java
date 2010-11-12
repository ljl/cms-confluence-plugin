package com.enonic.cms.plugin.confluence.service;

import com.enonic.cms.plugin.confluence.model.FileModel;
import com.enonic.cms.plugin.confluence.model.SpaceModel;

/**
 * This interface defines the doc service.
 */
public interface WikiDocService
{
    /**
     * Return the space.
     */
    public SpaceModel getSpace( String spaceKey )
        throws Exception;

    /**
     * Render page.
     */
    public String renderPage( String spaceKey, String pageId )
        throws Exception;

    /**
     * Return the attachement.
     */
    public FileModel getFile( String pageId, String fileName )
        throws Exception;
}

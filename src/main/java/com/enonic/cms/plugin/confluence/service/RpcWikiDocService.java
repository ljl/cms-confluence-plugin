package com.enonic.cms.plugin.confluence.service;

import com.enonic.cms.plugin.confluence.model.FileModel;
import com.enonic.cms.plugin.confluence.model.PageModel;
import com.enonic.cms.plugin.confluence.model.SpaceModel;
import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcStruct;

import java.net.URL;

/**
 * This class implements the doc service based on xmlrpc.
 */
public final class RpcWikiDocService
    implements WikiDocService
{
    /**
     * Xml rpc user.
     */
    private String user;

    /**
     * Xml rpc password.
     */
    private String password;

    /**
     * Rpc client.
     */
    private ConfluenceService client;

    public void setUrl( URL url )
    {
        this.client = new ConfluenceService( url );
    }

    public void setUser( String user )
    {
        this.user = user;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    /**
     * Returns the space.
     */
    public SpaceModel getSpace( String spaceKey )
        throws Exception
    {
        String ticket = this.client.login( this.user, this.password );

        try
        {
            SpaceModel model = loadSpace( ticket, spaceKey );
            model.setRootPage( loadRootPage( ticket, model.getHomePage() ) );
            return model;
        }
        finally
        {
            this.client.logout( ticket );
        }
    }

    /**
     * Return the page by id.
     */
    public String renderPage( String spaceKey, String pageId )
        throws Exception
    {
        String ticket = this.client.login( this.user, this.password );

        try
        {
            return this.client.renderContent( ticket, spaceKey, pageId );
        }
        finally
        {
            this.client.logout( ticket );
        }
    }

    /**
     * Return the root page.
     */
    private PageModel loadRootPage( String ticket, String pageId )
        throws Exception
    {
        XmlRpcStruct data = this.client.getPage( ticket, pageId );
        PageModel model = createPageModel( data, null );
        loadChildPages( ticket, model );
        return model;
    }

    /**
     * Load child pages.
     */
    private void loadChildPages( String ticket, PageModel parent )
        throws Exception
    {
        XmlRpcArray list = this.client.getChildren( ticket, String.valueOf( parent.getId() ) );
        for ( int i = 0; i < list.size(); i++ )
        {
            PageModel page = createPageModel( list.getStruct( i ), parent );
            loadChildPages( ticket, page );
            parent.addChild( page );
        }
    }

    /**
     * Return the root page.
     */
    private PageModel createPageModel( XmlRpcStruct data, PageModel parent )
        throws Exception
    {
        PageModel model = new PageModel();
        model.setId( data.getString( "id" ) );
        model.setParent( parent );
        model.setTitle( data.getString( "title" ) );
        return model;
    }

    /**
     * Load the space.
     */
    private SpaceModel loadSpace( String ticket, String spaceKey )
        throws Exception
    {
        XmlRpcStruct data = this.client.getSpace( ticket, spaceKey );
        SpaceModel model = new SpaceModel();
        model.setKey( data.getString( "key" ) );
        model.setName( data.getString( "name" ) );
        model.setHomePage( data.getString( "homePage" ) );
        return model;
    }

    private FileModel loadFile( String ticket, String pageId, String fileName )
        throws Exception
    {
        XmlRpcStruct data = this.client.getAttachment( ticket, pageId, fileName );
        if ( data == null )
        {
            return null;
        }

        FileModel model = new FileModel();
        model.setFileName( data.getString( "fileName" ) );
        model.setContentType( data.getString( "contentType" ) );
        model.setData( this.client.getAttachmentData( ticket, pageId, fileName ) );

        if ( model.getData() == null )
        {
            return null;
        }

        return model;
    }

    public FileModel getFile( String pageId, String fileName )
        throws Exception
    {
        String ticket = this.client.login( this.user, this.password );

        try
        {
            return loadFile( ticket, pageId, fileName );
        }
        finally
        {
            this.client.logout( ticket );
        }
    }
}

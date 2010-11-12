package com.enonic.cms.plugin.confluence;

import com.enonic.cms.plugin.confluence.model.PageModel;
import com.enonic.cms.plugin.confluence.model.SpaceModel;
import com.enonic.cms.plugin.confluence.service.WikiDocService;
import com.enonic.cms.plugin.confluence.xml.XmlModelBuilder;
import com.enonic.cms.plugin.confluence.xml.XmlModelBuilderOldModel;
import org.jdom.Document;
import org.jdom.Element;

public final class ConfluenceFunctionLibrary
{
    private WikiDocService service;

    public void setService( WikiDocService service )
    {
        this.service = service;
    }

    @Deprecated
    public Document getSpace( String spaceKey )
        throws Exception
    {
        return XmlModelBuilderOldModel.build( this.service.getSpace( spaceKey ) );
    }

    public Document getSpaceMenu( String spaceKey, String pageKey, String customItemKey )
        throws Exception
    {
        SpaceModel spaceModel = this.service.getSpace( spaceKey );

        PageModel activePage = spaceModel.findPageByName( customItemKey );

        if ( activePage == null )
        {
            activePage = spaceModel.getRootPage();
        }

        XmlModelBuilder modelBuilder = new XmlModelBuilder( pageKey, activePage );

        return modelBuilder.build( spaceModel );
    }

    @Deprecated
    public Document getPageById( String spaceKey, String pageId, boolean render )
        throws Exception
    {
        SpaceModel model = this.service.getSpace( spaceKey );
        return getPageOldModel( spaceKey, model.findPageById( pageId ), render );
    }

    @Deprecated
    public Document getPageByName( String spaceKey, String pageName, boolean render )
        throws Exception
    {
        SpaceModel model = this.service.getSpace( spaceKey );
        return getPageOldModel( spaceKey, model.findPageByName( pageName ), render );
    }

    public Document getPageByName( String spaceKey, String pageName, boolean render, String pageKey )
        throws Exception
    {
        SpaceModel model = this.service.getSpace( spaceKey );
        return getPage( model, model.findPageByName( pageName ), render, pageKey );
    }

    public Document getPageById( String spaceKey, String pageId, boolean render, String pageKey )
        throws Exception
    {
        SpaceModel model = this.service.getSpace( spaceKey );
        return getPage( model, model.findPageById( pageId ), render, pageKey );
    }

    @Deprecated
    private Document getPageOldModel( String spaceKey, PageModel model, boolean render )
        throws Exception
    {
        if ( model == null )
        {
            return new Document( new Element( "page" ) );
        }

        String content = render ? this.service.renderPage( spaceKey, model.getId() ) : null;
        return XmlModelBuilderOldModel.build( model, content );
    }

    private Document getPage( SpaceModel spaceModel, PageModel model, boolean render, String pageKey )
        throws Exception
    {
        if ( model == null )
        {
            model = spaceModel.getRootPage();
        }

        String content = render ? this.service.renderPage( spaceModel.getKey(), model.getId() ) : null;

        XmlModelBuilder modelBuilder = new XmlModelBuilder( pageKey, model );

        return modelBuilder.build( model, content );
    }

}

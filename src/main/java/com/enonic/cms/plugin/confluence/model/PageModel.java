package com.enonic.cms.plugin.confluence.model;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the page model.
 */
public final class PageModel
{
    private String id;

    private PageModel parent;

    private String title;

    private final List<PageModel> children;

    public PageModel()
    {
        this.children = new LinkedList<PageModel>();
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public PageModel getParent()
    {
        return parent;
    }

    public void setParent( PageModel parent )
    {
        this.parent = parent;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getName()
    {
        try
        {
            return URLEncoder.encode( this.title, "UTF-8" );
        }
        catch ( Exception e )
        {
            return this.title;
        }
    }

    public boolean isActive( String pageId )
    {
        if ( pageId != null && pageId.equals( this.getId() ) )
        {
            return true;
        }

        return false;
    }

    public boolean isInPath( PageModel activePage )
    {
        if ( activePage == null )
        {
            return false;
        }

        return isParentOf( activePage );
    }


    public boolean isParentOf( PageModel potentialChild )
    {
        return potentialChild.hasAsParent( this );
    }

    public boolean hasAsParent( PageModel potentialParent )
    {
        boolean iAmTheParent = potentialParent.getId().equals( this.getId() );
        if ( iAmTheParent )
        {
            return true;
        }

        PageModel currentParent = this;

        while ( currentParent != null )
        {

            if ( potentialParent.equals( currentParent ) )
            {
                return true;
            }

            currentParent = currentParent.getParent();
        }

        return false;
    }

    public List<PageModel> getChildren()
    {
        return children;
    }

    public void addChild( PageModel child )
    {
        this.children.add( child );
    }
}

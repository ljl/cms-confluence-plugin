package com.enonic.cms.plugin.confluence.model;

public final class SpaceModel
{
    private String key;

    private String name;

    private String homePage;

    private PageModel rootPage;

    public String getKey()
    {
        return key;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getHomePage()
    {
        return homePage;
    }

    public void setHomePage( String homePage )
    {
        this.homePage = homePage;
    }

    public PageModel getRootPage()
    {
        return rootPage;
    }

    public void setRootPage( PageModel rootPage )
    {
        this.rootPage = rootPage;
    }

    public PageModel findPageById( String id )
    {
        return findPageById( id, this.rootPage );
    }

    private PageModel findPageById( String id, PageModel parent )
    {
        if ( id == null )
        {
            return null;
        }

        if ( id.equals( parent.getId() ) )
        {
            return parent;
        }

        for ( PageModel child : parent.getChildren() )
        {
            PageModel model = findPageById( id, child );
            if ( model != null )
            {
                return model;
            }
        }

        return null;
    }

    public PageModel findPageByName( String name )
    {
        return findPageByName( name, this.rootPage );
    }

    private PageModel findPageByName( String name, PageModel parent )
    {
        if ( name.equals( parent.getName() ) )
        {
            return parent;
        }

        if ( name.equals( parent.getTitle() ) )
        {
            return parent;
        }

        for ( PageModel child : parent.getChildren() )
        {
            PageModel model = findPageByName( name, child );
            if ( model != null )
            {
                return model;
            }
        }

        return null;
    }
}

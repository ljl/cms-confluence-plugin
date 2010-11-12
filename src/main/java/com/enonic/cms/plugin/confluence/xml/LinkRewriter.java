package com.enonic.cms.plugin.confluence.xml;

/**
 * This class implements the link rewriter.
 */
public final class LinkRewriter
{
    /**
     * Patterns.
     */
    private final static String[] PATTERNS =
        {"href=\"/display/([^/]+)/([^\"]+)\"", "href='/display/([^/]+)/([^']+)'", "href=\"#([^\"]+)\"", "href='#([^']+)'",
            "src=\"/download/attachments/([0-9]+)/([^\"]+)\"", "src='/download/attachments/([0-9]+)/([^']+)'", "src=\"/images/([^\"]+)\"",
            "src='/images/([^']+)'"};

    /**
     * Patterns.
     */
    private final static String[] REPLACEMENTS =
        {"href=\":::page:$1:$2:::\"", "href=\":::page:$1:$2:::\"", "href=\":::anchor:$1:::\"", "href=\":::anchor:$1:::\"",
            "src=\":::attachment:$1:$2:::\"", "src=\":::attachment:$1:$2:::\"", "src=\":::image:$1:::\"", "src=\":::image:$1:::\""};

    /**
     * Rewrite links.
     */
    public static String rewriteLinks( String content )
    {
        for ( int i = 0; i < PATTERNS.length; i++ )
        {
            content = content.replaceAll( PATTERNS[i], REPLACEMENTS[i] );
        }

        return content;
    }
}

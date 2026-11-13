/*
 * Copyright (c) 2002-2026, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.avatar.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.test.LuteceTestCase;

/**
 * Renders the avatar image of AbstractAvatarProvider with the HTML auto-escaping of the core (LUT-33153): the image URL
 * is written in the src attribute escaped once, so a URL with several parameters stays a valid attribute value.
 */
public class AvatarRenderTest extends LuteceTestCase
{
    private static final String URL = "https://avatar.example.org/abc?d=mm&s=80&t=\"x\"";
    private static final String SRC = "src=\"https://avatar.example.org/abc?d=mm&amp;s=80&amp;t=&quot;x&quot;\"";

    /**
     * The URL given by the provider is escaped once in the src attribute.
     */
    @Test
    public void testAvatarUrlEscapedOnceInSrc( )
    {
        AvatarProvider provider = new AbstractAvatarProvider( )
        {
            @Override
            public String getAvatarUrl( String strAvatarId )
            {
                return URL;
            }
        };

        String strHtml = provider.getAvatar( "test@test.com" );

        assertTrue( strHtml.contains( "<img " ), strHtml );
        assertTrue( strHtml.contains( SRC ), strHtml );
        assertFalse( strHtml.contains( "&amp;amp;" ), strHtml );
    }

    /**
     * The avatar image is decorative, whatever the provider: its alt text is empty, the caller writes the name of the
     * person next to it.
     */
    @Test
    public void testAvatarImageIsDecorative( )
    {
        String strHtml = new LuteceAvatarProvider( ).getAvatar( "test@test.com" );

        assertTrue( strHtml.contains( "alt=\"\"" ), strHtml );
        assertFalse( strHtml.contains( "gravatar.com\"" ), strHtml );
    }

    /**
     * The Gravatar provider writes an image whose URL holds the hash of the email, not the email.
     */
    @Test
    public void testGravatarImage( )
    {
        String strHtml = new GravatarProvider( ).getAvatar( "test@test.com" );

        assertTrue( strHtml.contains( "<img " ), strHtml );
        assertTrue( strHtml.contains( "/avatar/b642b4217b34b1e8d3bd915fc65c4452" ), strHtml );
        assertFalse( strHtml.contains( "test@test.com" ), strHtml );
    }
}

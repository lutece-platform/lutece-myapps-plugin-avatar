/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * Abstract Avatar provider
 */
public abstract class AbstractAvatarProvider implements AvatarProvider
{
    private static final String TEMPLATE_GRAVATAR = "/admin/plugins/avatar/avatar.html";
    private static final String MARK_AVATAR_URL = "avatar_url";

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAvatar( String strId )
    {
        String strAvatar;
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_AVATAR_URL, getAvatarUrl( strId ) );

        HtmlTemplate t = AppTemplateService.getTemplate( TEMPLATE_GRAVATAR, Locale.getDefault(  ), model );
        strAvatar = t.getHtml(  );

        return strAvatar;
    }

    /**
     * Gat a MD5 hash from an email
     *
     * @param strAvatarEmail The email
     * @return The hash
     */
    protected String getHashFromEmail( String strAvatarEmail )
    {
        String strHash = "";
        String strEmail = strAvatarEmail.toLowerCase(  );
        MessageDigest md;

        try
        {
            md = MessageDigest.getInstance( "MD5" );

            byte[] hash = md.digest( strEmail.getBytes(  ) );
            StringBuilder sb = new StringBuilder(  );

            for ( int i = 0; i < hash.length; i++ )
            {
                sb.append( Integer.toString( ( hash[i] & 0xff ) + 0x100, 16 ).substring( 1 ) );
            }

            strHash = sb.toString(  );
        }
        catch ( NoSuchAlgorithmException ex )
        {
            AppLogService.error( "Avatar service : Error hashing the email : " + ex.getMessage(  ), ex );
        }

        return strHash;
    }
}

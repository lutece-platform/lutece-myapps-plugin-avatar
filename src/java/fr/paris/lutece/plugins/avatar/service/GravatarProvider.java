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

import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.plugins.priority.annotation.LutecePriority;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Gravatar provider
 */
@ApplicationScoped
@LutecePriority( "avatar.provider.priority.gravatar" )
public class GravatarProvider extends AbstractAvatarProvider
{
    private static final String URL_GRAVATAR = "http://www.gravatar.com/avatar/";
    private static final String PROPERTY_DEFAULT = "avatar.gravatar.default";
    private static final String PROPERTY_URL_GRAVATAR = "avatar.gravatar.url";
    private static final String PROPERTY_SIZE = "avatar.gravatar.size";
    private static final String PROPERTY_RATING = "avatar.gravatar.rating";
    private static final String PARAMETER_DEFAULT = "d";
    private static final String PARAMETER_SIZE = "s";
    private static final String PARAMETER_RATING = "r";
    private static final String DEFAULT_AVATAR = "mm";

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAvatarUrl( String strAvatarId )
    {
        String strUrl;

        String strHash = getHashFromEmail( strAvatarId );

        String strUrlGravatar = AppPropertiesService.getProperty( PROPERTY_URL_GRAVATAR, URL_GRAVATAR );
        String strDefault = AppPropertiesService.getProperty( PROPERTY_DEFAULT, DEFAULT_AVATAR );

        UrlItem url = new UrlItem( strUrlGravatar + strHash );
        url.addParameter( PARAMETER_DEFAULT, strDefault );

        String strSize = AppPropertiesService.getProperty( PROPERTY_SIZE );

        if ( ( strSize != null ) && !"".equals( strSize ) )
        {
            url.addParameter( PARAMETER_SIZE, strSize );
        }

        String strRating = AppPropertiesService.getProperty( PROPERTY_RATING );

        if ( ( strRating != null ) && !"".equals( strRating ) )
        {
            url.addParameter( PARAMETER_RATING, strRating );
        }

        strUrl = url.getUrl(  );

        return strUrl;
    }
}

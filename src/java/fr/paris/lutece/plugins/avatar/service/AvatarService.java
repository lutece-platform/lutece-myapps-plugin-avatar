/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * Avatar Service
 */
public final class AvatarService
{
    private static final String PLUGIN_NAME = "avatar";
    private static final String PROPERTY_BEAN_PROVIDER = "avatar.provider";

    /** Private constructor */
    private AvatarService(  )
    {
    }

    /**
     * Returns the HTML code to insert to display the avatar
     * @param strId The avatar ID
     * @return The HTML code
     */
    public static String getAvatar( String strId )
    {
        String strAvatar = "";

        Plugin plugin = PluginService.getPlugin( PLUGIN_NAME );

        if ( plugin.isInstalled(  ) )
        {
            strAvatar = getProvider(  ).getAvatar( strId );
        }

        return strAvatar;
    }

    /**
     * Returns the avatar Url
     * @param strId The avatar ID
     * @return The Url
     */
    public static String getAvatarUrl( String strId )
    {
        String strAvatarUrl = "";

        Plugin plugin = PluginService.getPlugin( PLUGIN_NAME );

        if ( plugin.isInstalled(  ) )
        {
            strAvatarUrl = getProvider(  ).getAvatarUrl( strId );
        }

        return strAvatarUrl;
    }

    /**
     * Get the avatar provider
     * @return The provider
     */
    private static AvatarProvider getProvider(  )
    {
        String strBean = AppPropertiesService.getProperty( PROPERTY_BEAN_PROVIDER );

        return SpringContextService.getBean( strBean );
    }
}

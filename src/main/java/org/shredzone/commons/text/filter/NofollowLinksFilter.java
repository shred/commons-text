/*
 * Shredzone Commons
 *
 * Copyright (C) 2012 Richard "Shred" Körber
 *   http://commons.shredzone.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Library General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.shredzone.commons.text.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shredzone.commons.text.TextFilter;

/**
 * A filter that detects HTML hyperlinks, and adds a {@code rel="nofollow"} attribute.
 * This filter can be used to post-process HTML content created by a site visitor, so
 * web crawlers won't follow their links.
 *
 * @author Richard "Shred" Körber
 */
public class NofollowLinksFilter implements TextFilter {

    private static final Pattern HREF_PATTERN = Pattern.compile(
            "(.*?)(<a[^>]+?href\\s*=\\s*[\"']?(?:https?|ftp|mailto|file):.+?)>",
            Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence apply(CharSequence text) {
        Matcher m = HREF_PATTERN.matcher(text);
        if (!m.matches()) {
            return text;
        }
        return m.replaceAll("$1$2 rel=\"nofollow\">");
    }

}

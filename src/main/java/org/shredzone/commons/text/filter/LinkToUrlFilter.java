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
 * A filter that detects links in a text, and creates an HTML &lt;a> tag around each link.
 * http, https and ftp protocols are detected.
 *
 * @author Richard "Shred" Körber
 */
public class LinkToUrlFilter implements TextFilter {

    // TODO: allow trailing period, comma etc.
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(.*?)((?:https?|ftp)://\\S+?)(?=[.,;:!)]?\\s|$)",
            Pattern.CASE_INSENSITIVE
            );

    private boolean follow = true;
    private String target = null;
    private String tag = null;

    /**
     * Creates a new {@link LinkToUrlFilter}.
     */
    public LinkToUrlFilter() {
        updateTag();
    }

    /**
     * Sets the way search engines evaluate the created link. If set to {@code false}, a
     * {@code rel="nofollow"} attribute is added to the link, so web crawlers will not
     * follow to the target.
     *
     * @param follow
     *            {@code true} if links should be followed by web crawlers. Defaults to
     *            {@code true}.
     */
    public void setFollow(boolean follow) {
        this.follow = follow;
        updateTag();
    }

    /**
     * Sets the link's target attribute.
     *
     * @param target
     *            Link target, or {@code null} if no target is to be set.
     */
    public void setTarget(String target) {
        this.target = target;
        updateTag();
    }

    /**
     * Updates the tag template from the current settings.
     */
    private void updateTag() {
        StringBuilder sb = new StringBuilder("$1<a href=\"$2\"");
        if (!follow) sb.append(" rel=\"nofollow\"");
        if (target != null) sb.append(" target=\"").append(target).append('"');
        sb.append(">$2</a>");
        tag = sb.toString();
    }

    @Override
    public StringBuilder filter(StringBuilder text) {
        Matcher m = URL_PATTERN.matcher(text);
        if (!m.matches()) {
            return text;
        }
        return new StringBuilder(m.replaceAll(tag));
    }

}

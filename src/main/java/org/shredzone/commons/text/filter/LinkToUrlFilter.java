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

import edu.umd.cs.findbugs.annotations.Nullable;
import org.shredzone.commons.text.TextFilter;

/**
 * A filter that detects links in a text, and creates an HTML &lt;a&gt; tag around each
 * link. http, https and ftp protocols are detected.
 *
 * @author Richard "Shred" Körber
 */
public class LinkToUrlFilter implements TextFilter {

    // TODO: allow trailing period, comma etc.
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(.*?)((?:https?|ftp)://\\S+?)(?=[.,;:!)]?\\s|$)",
            Pattern.CASE_INSENSITIVE
            );

    private boolean noFollow = false;
    private boolean noReferrer = false;
    private boolean noOpener = true;
    private @Nullable String target = null;
    private @Nullable String tag = null;

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
     *         {@code true} if links should be followed by web crawlers. Defaults to
     *         {@code true}.
     * @deprecated It is confusing that this property must be set to {@code false} in
     * order to have a "nofollow" relationship. Use {@link #setNoFollow(boolean)}
     * instead.
     */
    @Deprecated
    public void setFollow(boolean follow) {
        setNoFollow(!follow);
    }

    /**
     * Sets the way search engines evaluate the created link. If set to {@code true}, a
     * {@code rel="nofollow"} attribute is added to the link, so web crawlers will not
     * follow to the target.
     *
     * @param noFollow
     *         {@code true} if links should not be followed by web crawlers. Defaults to
     *         {@code false}.
     * @since 2.6
     */
    public void setNoFollow(boolean noFollow) {
        this.noFollow = noFollow;
        updateTag();
    }

    /**
     * Sets whether links with target "_blank" should have a "noopener" relationship.
     * Activated by default. Note that deactivation poses a security risk for your
     * website, and should only be done for a very good reason!
     *
     * @param noOpener
     *         {@code true} to set "noopener" relationships on all links with a "_blank"
     *         target. This is the default.
     * @since 2.6
     */
    public void setNoOpener(boolean noOpener) {
        this.noOpener = noOpener;
        updateTag();
    }

    /**
     * Sets wheter a "noreferrer" relationship shall be used. If {@code true} (and
     * supported by the browser), the browser won't send a "Referer" header when following
     * a link.
     *
     * @param noReferrer
     *         If {@code true}, a "noreferrer" relation is added to each link. {@code
     *         false} by default.
     * @since 2.6
     */
    public void setNoReferrer(boolean noReferrer) {
        this.noReferrer = noReferrer;
        updateTag();
    }

    /**
     * Sets the link's target attribute.
     *
     * @param target
     *            Link target, or {@code null} if no target is to be set.
     */
    public void setTarget(@Nullable String target) {
        this.target = target;
        updateTag();
    }

    /**
     * Updates the tag template from the current settings.
     */
    private void updateTag() {
        StringBuilder sb = new StringBuilder("$1<a href=\"$2\"");
        StringBuilder relSb = new StringBuilder();
        if (noFollow) {
            relSb.append("nofollow ");
        }
        if (target != null) {
            sb.append(" target=\"").append(target).append('"');
            if (noOpener && "_blank".equals(target)) {
                relSb.append("noopener ");
            }
        }
        if (noReferrer) {
            relSb.append("noreferrer ");
        }
        if (relSb.length() > 0) {
            sb.append(" rel=\"").append(relSb.toString().trim()).append('"');
        }
        sb.append(">$2</a>");
        tag = sb.toString();
    }

    @Override
    public CharSequence apply(CharSequence text) {
        Matcher m = URL_PATTERN.matcher(text);
        if (!m.matches()) {
            return text;
        }
        return m.replaceAll(tag);
    }

}

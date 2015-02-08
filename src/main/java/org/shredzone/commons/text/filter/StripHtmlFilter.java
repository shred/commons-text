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
 * A filter that strips HTML markup from a text.
 * <p>
 * This filter distinguishes between block and inline tags. Block tags are replaced by a
 * whitespace (unless there already was a whitespace before the tag), while inline tags
 * are just removed.
 * <p>
 * Rationale: "foo&lt;br>bar" is converted to "foo bar", while "foo&lt;i>bar&lt;/i>" is
 * converted to "foobar", similar to the way it is displayed in a web browser.
 *
 * @author Richard "Shred" Körber
 */
public class StripHtmlFilter implements TextFilter {

    // Inline tags that do not need to be replaced by a whitespace
    private static final Pattern INLINE_TAGS = Pattern.compile("code|em|strong|samp|" +
            "kbd|var|cite|dfn|abbr|acronym|q|del|ins|bdo|b|i|u|tt|s|strike|big|small|" +
            "sup|sub|span|img", Pattern.CASE_INSENSITIVE);

    private static final Pattern TAG_PATTERN = Pattern.compile("</?([a-zA-Z0-9]+)[^>]*>", Pattern.DOTALL);

    @Override
    public CharSequence apply(CharSequence text) {
        StringBuffer sb = new StringBuffer();

        Matcher m = TAG_PATTERN.matcher(text);
        while (m.find()) {
            String tag = m.group(1);

            boolean isInline = INLINE_TAGS.matcher(tag).matches();
            if (!isInline && sb.length() > 0 && !Character.isWhitespace(sb.charAt(sb.length() - 1))) {
                m.appendReplacement(sb, " ");
            } else {
                m.appendReplacement(sb, "");
            }
        }
        m.appendTail(sb);

        if (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb;
    }

}

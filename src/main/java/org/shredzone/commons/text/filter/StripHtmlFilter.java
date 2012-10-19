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

import java.util.regex.Pattern;

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
public class StripHtmlFilter extends ProcessorTextFilter {

    // Inline tags that do not need to be replaced by a whitespace
    private static final Pattern INLINE_TAGS = Pattern.compile("code|em|strong|samp|" +
            "kbd|var|cite|dfn|abbr|acronym|q|del|ins|bdo|b|i|u|tt|s|strike|big|small|" +
            "sup|sub|span|img", Pattern.CASE_INSENSITIVE);

    @Override
    public int process(StringBuilder text, int start, int end) {
        int ix = start;
        int max = end;
        boolean lastWhitespace = false;

        while (ix < max) {
            if (text.charAt(ix) == '<') {
                int endPos = ix + 1;
                while (endPos < max && text.charAt(endPos) != '>') {
                    endPos++;
                }
                if (endPos == max) {
                    return max;
                }

                boolean isInline = isInline(text, ix, endPos + 1);

                text.delete(ix, endPos + 1);
                max -= endPos + 1 - ix;

                if (!isInline && ix > 0 && !Character.isWhitespace(text.charAt(ix - 1))) {
                    text.insert(ix, ' ');
                    ix++;
                    max++;
                    lastWhitespace = true;
                }
            } else {
                ix++;
                lastWhitespace = false;
            }
        }

        // Trim trailing whitespace
        if (lastWhitespace) {
            text.deleteCharAt(--max);
        }

        return max;
    }

    /**
     * Tests if the given text snippet contains an inline tag name.
     *
     * @param text
     *            Text to check
     * @param start
     *            Starting position, must be at the opening angle bracket of the tag
     * @param end
     *            Ending position, must be at the closing angle bracket of the tag
     * @return {@code true} if this is an inline tag (either empty, opening or closing)
     */
    private boolean isInline(CharSequence text, int start, int end) {
        int ix = start + 1;

        if (ix < end && text.charAt(ix) == '/') {
            ix++;
        }

        int last = ix;
        while (last < end && Character.isLetter(text.charAt(last))) {
            last++;
        }

        if (ix >= end || last >= end) {
            return false;
        }

        return INLINE_TAGS.matcher(text.subSequence(ix, last)).matches();
    }

}

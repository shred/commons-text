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

import javax.annotation.ParametersAreNonnullByDefault;

import org.shredzone.commons.text.TextFilter;

/**
 * Escapes a plain text so it can be safely used in HTML. '&lt;', '&amp;' and '&quot;' are
 * replaced by their respective HTML entities.
 *
 * @author Richard "Shred" Körber
 */
@ParametersAreNonnullByDefault
public class HtmlEscapeFilter implements TextFilter {

    @Override
    public CharSequence apply(CharSequence text) {
        int len = text.length();
        StringBuilder sb = new StringBuilder(len * 11 / 10);
        for (int ix = 0; ix < len; ix++) {
            char ch = text.charAt(ix);
            if (ch == '<') {
                sb.append("&lt;");
            } else if (ch == '&') {
                sb.append("&amp;");
            } else if (ch == '"') {
                sb.append("&quot;");
            } else {
                sb.append(ch);
            }
        }
        return sb;
    }

}

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

/**
 * Escapes a plain text so it can be safely used in HTML. '&lt;', '&amp;' and '&quot;' are
 * replaced by their respective HTML entities.
 *
 * @author Richard "Shred" Körber
 */
public class HtmlEscapeFilter extends ProcessorTextFilter {

    @Override
    public int process(StringBuilder text, int start, int end) {
        int ix = start;
        int max = end;
        while (ix < max) {
            switch (text.charAt(ix)) {
                case '<': text.replace(ix, ix + 1, "&lt;");   ix += 4; max += 3; break;
                case '&': text.replace(ix, ix + 1, "&amp;");  ix += 5; max += 4; break;
                case '"': text.replace(ix, ix + 1, "&quot;"); ix += 6; max += 5; break;
                default: ix++;
            }
        }
        return max;
    }

}

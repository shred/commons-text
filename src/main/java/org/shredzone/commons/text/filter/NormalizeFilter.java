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
 * A filter that normalizes EOL markers. CR and CRLF are converted to LF.
 * 
 * @author Richard "Shred" Körber
 */
public class NormalizeFilter extends ProcessorTextFilter {

    @Override
    public int process(StringBuilder text, int start, int end) {
        int max = end;
        for (int ix = start; ix < max; ix++) {
            if (text.charAt(ix) == '\r') {
                if ((ix + 1) < text.length() && text.charAt(ix + 1) == '\n') {
                    text.deleteCharAt(ix);
                    max--;
                } else {
                    text.setCharAt(ix, '\n');
                }
            }
        }
        return max;
    }

}

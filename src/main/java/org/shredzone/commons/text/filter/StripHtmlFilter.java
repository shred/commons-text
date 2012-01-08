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
 * A filter that strips HTML markup from a text.
 *
 * @author Richard "Shred" Körber
 */
public class StripHtmlFilter extends ProcessorTextFilter {

    @Override
    public int process(StringBuilder text, int start, int end) {
        int ix = start;
        int max = end;
        
        while (ix < max) {
            if (text.charAt(ix) == '<') {
                int endPos = ix + 1;
                while (endPos < max && text.charAt(endPos) != '>') {
                    endPos++;
                }
                if (endPos == max) {
                    return max;
                }
                text.delete(ix, endPos + 1);
                max -= endPos + 1 - ix;
            } else {
                ix++;
            }
        }
        
        return max;
    }

}

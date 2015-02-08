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

import org.shredzone.commons.text.TextFilter;

/**
 * A filter that detects paragraphs and surrounds them with a HTML {@code &lt;p>}
 * container. Paragraphs are separated by empty lines. Additionally, single EOLs can be
 * replaced with {@code &lt;br />} tags.
 * <p>
 * This filter expects a normalized text (only LF is accepted as EOL marker, see
 * {@link NormalizeFilter}).
 *
 * @author Richard "Shred" Körber
 */
public class ParagraphFilter implements TextFilter {

    private boolean foldLines = true;

    /**
     * Also fold single EOL marker.
     *
     * @param foldLines
     *            {@code true} to create {@code &lt;br />} tags for single EOL markers,
     *            {@code false} to keep the EOL marker. Defaults to {@code true}.
     */
    public void setFoldLines(boolean foldLines) {
        this.foldLines = foldLines;
    }

    @Override
    public CharSequence apply(CharSequence text) {
        StringBuilder sb = toStringBuilder(text);

        sb.insert(0, "<p>").append("</p>");

        int max = sb.length() - 3 - 4;
        int ix = 3;

        while (ix < max) {
            if (sb.charAt(ix) == '\n') {
                int lineEnd = ix + 1;
                while (lineEnd < max && sb.charAt(lineEnd) == '\n') {
                    lineEnd++;
                }

                if (lineEnd > ix + 1) {
                    sb.replace(ix, lineEnd, "</p><p>");
                    max += 7 - (lineEnd - ix);
                    ix += 7;
                } else if (foldLines) {
                    sb.replace(ix, lineEnd, "<br />");
                    max += 6 - (lineEnd - ix);
                    ix += 6;
                } else {
                    ix++;
                }
            } else {
                ix++;
            }
        }

        return sb;
    }

}

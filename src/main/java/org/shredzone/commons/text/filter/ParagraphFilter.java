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
 * A filter that detects paragraphs and surrounds them with a HTML {@code &lt;p>}
 * container. Paragraphs are separated by empty lines. Additionally, single EOLs can be
 * replaced with {@code &lt;br />} tags.
 * <p>
 * This filter expects a normalized text (only LF is accepted as EOL marker, see
 * {@link NormalizeFilter}).
 *
 * @author Richard "Shred" Körber
 */
public class ParagraphFilter extends ProcessorTextFilter {

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
    public int process(StringBuilder text, int start, int end) {
        text.insert(end, "</p>").insert(start, "<p>");

        int max = end + 3 + 4;
        int ix = start + 3;

        while (ix < max) {
            if (text.charAt(ix) == '\n') {
                int lineEnd = ix + 1;
                while (lineEnd < max && text.charAt(lineEnd) == '\n') {
                    lineEnd++;
                }

                String replacement = (lineEnd > ix + 1) ? "</p><p>" : (foldLines ? "<br />" : "\n");

                text.replace(ix, lineEnd, replacement);

                max += replacement.length() - (lineEnd - ix);
                ix += replacement.length();
            } else {
                ix++;
            }
        }

        return max;
    }

}

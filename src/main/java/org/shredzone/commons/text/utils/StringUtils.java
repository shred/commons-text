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
package org.shredzone.commons.text.utils;

/**
 * A collection of String operations.
 * 
 * @author Richard "Shred" Körber
 */
public final class StringUtils {

    private StringUtils() {
        // Utility class without constructor
    }

    /**
     * Replaces a section of the {@link StringBuilder} with the given replacement string.
     * 
     * @param sb
     *            {@link StringBuilder} to operate on
     * @param start
     *            Starting position of the section
     * @param end
     *            Ending position of the section (exclusive)
     * @param replacement
     *            Replacement string
     * @return new ending position
     */
    public static int replace(StringBuilder sb, int start, int end, String replacement) {
        sb.replace(start, end + 1, replacement);
        return replacement.length() - (end - start) - 1;
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is prepended by a
     * digit.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @return result of the test
     */
    public static boolean isLeadingDigit(CharSequence text, int pos) {
        if (pos <= 0) return false;
        return (Character.isDigit(text.charAt(pos - 1)));
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is followed by a
     * digit.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @return result of the test
     */
    public static boolean isTrailingDigit(CharSequence text, int pos) {
        if (pos + 1 >= text.length()) return false;
        return (Character.isDigit(text.charAt(pos + 1)));
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is prepended by
     * the given character.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @param ch
     *            Character
     * @return result of the test
     */
    public static boolean isLeadingChar(CharSequence text, int pos, char ch) {
        if (pos <= 0) return false;
        return text.charAt(pos - 1) == ch;
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is followed by the
     * given character.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @param ch
     *            Character
     * @return result of the test
     */
    public static boolean isTrailingChar(CharSequence text, int pos, char ch) {
        if (pos + 1 >= text.length()) return false;
        return text.charAt(pos + 1) == ch;
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is prepended by
     * the given string.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @param leading
     *            String that must be prepended
     * @return result of the test
     */
    public static boolean isLeadingString(CharSequence text, int pos, CharSequence leading) {
        if (pos < leading.length()) return false;
        return leading.equals(text.subSequence(pos - leading.length(), pos));
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is prepended by a
     * whitespace character.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @return result of the test
     */
    public static boolean isLeadingSpace(CharSequence text, int pos) {
        if (pos <= 0) return false;
        return (Character.isWhitespace(text.charAt(pos - 1)));
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} is followed by a
     * whitespace character.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @return result of the test
     */
    public static boolean isTrailingSpace(CharSequence text, int pos) {
        if (pos + 1 >= text.length()) return false;
        return (Character.isWhitespace(text.charAt(pos + 1)));
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} represents the
     * start of a line.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @return result of the test
     */
    public static boolean isStartOfLine(CharSequence text, int pos) {
        if (pos == 0) return true;
        char ch = text.charAt(pos - 1);
        return (ch == '\n' || ch == '\r');
    }

    /**
     * Returns {@code true} if the position of the {@link CharSequence} represents the
     * end of a line.
     * 
     * @param text
     *            {@link CharSequence} to test
     * @param pos
     *            position
     * @return result of the test
     */
    public static boolean isEndOfLine(CharSequence text, int pos) {
        if (pos >= text.length()) return true;
        char ch = text.charAt(pos);
        return (ch == '\n' || ch == '\r');
    }

    /**
     * Safely fetch the character at the given position. If the position is outside of the
     * text boundaries, NUL (0x00) is returned.
     * 
     * @param text
     *            Text to fetch the character from
     * @param pos
     *            position
     * @return Character at the position, or NUL
     */
    public static char getChar(CharSequence text, int pos) {
        if (pos < 0 || pos >= text.length()) {
            return '\0';
        } else {
            return text.charAt(pos);
        }
    }

    /**
     * Escapes a text so it can be safely used in HTML.
     * 
     * @param text
     *            Text to be escaped
     * @return Escaped text
     */
    public static String escapeHtml(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace("\"", "&quot;");
    }

}

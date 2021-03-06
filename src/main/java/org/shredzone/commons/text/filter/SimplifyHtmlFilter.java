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

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.shredzone.commons.text.TextFilter;

/**
 * A filter that simplifies a HTML text. Only a given set of HTML tags (and attributes)
 * are accepted. Other HTML tags are completely removed.
 * <p>
 * This filter can be used to allow a site visitor to enter marked-up text, but remove
 * everything that might be harmful or induce Cross Site Scripting.
 *
 * @author Richard "Shred" Körber
 */
public class SimplifyHtmlFilter implements TextFilter {

    private static final Pattern TAG_PATTERN = Pattern.compile("<[^>]+(>|$)", Pattern.DOTALL);
    private static final Pattern TAG_OPEN = Pattern.compile("<(\\w+)\\s*(.*?)\\s*(/?)>");
    private static final Pattern TAG_CLOSE = Pattern.compile("</(\\w+)\\s*>");

    private final Map<String, Set<String>> acceptedTags = new HashMap<>();

    /**
     * Adds a tag that is accepted by this filter, with all its attributes.
     *
     * @param tag
     *            HTML tag that is accepted (without angle brackets, e.g. "strong")
     */
    public void addAcceptedTag(String tag) {
        addAcceptedTag(tag, (String[]) null);
    }

    /**
     * Adds a tag that is accepted by this filter, along with accepted attributes.
     *
     * @param tag
     *            HTML tag that is accepted (without angle brackets, e.g. "img")
     * @param attributes
     *            an array of accepted attributes (e.g. "src", "alt")
     */
    public void addAcceptedTag(String tag, String... attributes) {
        Objects.requireNonNull(tag);

        if (attributes == null || attributes.length == 0) {
            acceptedTags.put(tag.toLowerCase(), null);
            return;
        }

        Set<String> attributeSet = Arrays.stream(attributes)
                .map(String::toLowerCase)
                .collect(toSet());

        acceptedTags.put(tag.toLowerCase(), attributeSet);
    }

    @Override
    public CharSequence apply(CharSequence text) {
        StringBuffer sb = new StringBuffer(text.length() * 11 / 10);

        Matcher m = TAG_PATTERN.matcher(text);
        while (m.find()) {
            m.appendReplacement(sb, processTag(m.group(0)));
        }
        m.appendTail(sb);

        return sb;
    }

    /**
     * Processes a tag that was spotted.
     *
     * @param text
     *            Tag (complete tag including the angle brackets)
     * @return Cleaned up tag, or empty string if the tag was not accepted
     */
    private String processTag(CharSequence text) {
        if (text.charAt(1) != '/') {
            // Opening tag or empty element shorthand
            Matcher m1 = TAG_OPEN.matcher(text);
            if (m1.matches()) {
                String tag = m1.group(1).toLowerCase();
                String attr = m1.group(2);
                String closing = m1.group(3);
                if (acceptedTags.containsKey(tag)) {
                    StringBuilder result = new StringBuilder();
                    result.append('<').append(tag);
                    processAttributes(attr, result, acceptedTags.get(tag));
                    if ("/".equals(closing)) {
                        result.append(" /");
                    }
                    result.append('>');
                    return result.toString();
                }
            }

        } else {
            // Closing tag
            Matcher m2 = TAG_CLOSE.matcher(text);
            if (m2.matches()) {
                String tag = m2.group(1).toLowerCase();
                if (acceptedTags.containsKey(tag)) {
                    return "</" + tag + ">";
                }
            }
        }

        return "";
    }

    /**
     * Processes an attribute string and builds clean attributes if accepted.
     *
     * @param attr
     *            Raw attribute string
     * @param result
     *            StringBuilder where to append clean attributes to
     * @param accepted
     *            Set of accepted attributes, may be empty or {@code null} if any
     *            attribute is accepted
     */
    private void processAttributes(String attr, StringBuilder result, @Nullable Set<String> accepted) {
        // If we accept no attributes, we will not change the result anyways.
        if (accepted == null || accepted.isEmpty()) {
            return;
        }

        int pos = 0;
        int max = attr.length();

        while (pos < max) {
            // Attribute name
            StringBuilder attrName = new StringBuilder(attr.length() * 11 / 10);
            StringBuilder attrValue = null;

            while (pos < max) {
                char ch = attr.charAt(pos);
                if (!Character.isLetterOrDigit(ch)) {
                    break;
                }
                attrName.append(ch);
                pos++;
            }

            // Skip Whitespaces
            while (pos < max && Character.isWhitespace(attr.charAt(pos))) {
                pos++;
            }

            if (pos < max && attr.charAt(pos) == '=') {
                attrValue = new StringBuilder();

                // Attribute with value
                pos++;

                // Skip Whitespaces
                while (pos < max && Character.isWhitespace(attr.charAt(pos))) {
                    pos++;
                }

                if (pos < max) {
                    char quote = attr.charAt(pos);
                    if (quote == '"' || quote == '\'') {
                        pos++;  // skip opening quote

                        while (pos < max && attr.charAt(pos) != quote) {
                            attrValue.append(attr.charAt(pos));
                            pos++;
                        }

                        pos++;  // skip closing quote

                    } else {
                        // Attributes without quotes, just copy to the end
                        while (pos < max && !Character.isWhitespace(attr.charAt(pos))) {
                            attrValue.append(attr.charAt(pos));
                            pos++;
                        }
                    }
                }

                // Skip trailing whitespaces
                while (pos < max && Character.isWhitespace(attr.charAt(pos))) {
                    pos++;
                }
            }

            if (accepted.contains(attrName.toString().toLowerCase())) {
                result.append(' ').append(attrName).append('=');
                if (attrValue != null) {
                    result.append('"');
                    // There should never be plain quotes in an attribute value!
                    result.append(attrValue.toString().replace("\"", "&quot;"));
                    result.append('"');
                } else {
                    result.append('"').append(attrName).append('"');
                }
            }
        }
    }

}

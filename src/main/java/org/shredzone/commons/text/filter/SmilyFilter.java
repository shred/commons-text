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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shredzone.commons.text.TextFilter;
import org.shredzone.commons.text.utils.StringUtils;

/**
 * A filter that detects smily sequences, and replaces them with an image. The filter
 * tries to find the best match by the string length of the smily code, so it can safely
 * distinguish between smilies like ":-)" and ":-))".
 * 
 * @author Richard "Shred" Körber
 */
public class SmilyFilter implements TextFilter {

    private static final Comparator<String> STRING_LENGTH_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.length() - o1.length();
        }
    };
    
    private String baseUrl = "";
    private Map<String, String> smilyMap = new HashMap<String, String>();
    private Pattern smilyPattern;

    /**
     * Creates a new {@link SmilyFilter}.
     */
    public SmilyFilter() {
        updatePattern();
    }

    /**
     * Adds a smily to be detected.
     * 
     * @param smily
     *            Smily code to detect (e.g. ":-)")
     * @param image
     *            Image file name to be shown instead
     */
    public void addSmily(String smily, String image) {
        if (smily == null || image == null) throw new NullPointerException("null parameter");
        smilyMap.put(smily, image);
        updatePattern();
    }

    /**
     * Sets the base url that is prepended to the image file names.
     * 
     * @param url
     *            Base url (e.g. "/img/smiles"), defaults to the current directory
     */
    public void setBaseUrl(String url) {
        if (url == null) throw new NullPointerException("url must not be null");
        this.baseUrl = url;
        if (baseUrl.length() > 0 && !baseUrl.endsWith("/")) {
            baseUrl += '/';
        }
    }

    /**
     * Updates the regular expression for smily detection.
     */
    private void updatePattern() {
        StringBuilder pattern = new StringBuilder();
        
        Set<String> smilySet = smilyMap.keySet();
        String[] smilys = smilySet.toArray(new String[smilySet.size()]);
        
        // We need to sort the smilys by their string length (descending), so the regex
        // will match the longest smilys first (":-))" before ":-)").
        Arrays.sort(smilys, STRING_LENGTH_COMPARATOR);

        boolean separator = false;
        for (String smily : smilys) {
            if (separator) pattern.append('|');
            pattern.append(Pattern.quote(smily));
            separator = true;
        }

        smilyPattern = Pattern.compile(pattern.toString(), Pattern.DOTALL);
    }
    
    @Override
    public StringBuilder filter(StringBuilder text) {
        Matcher m = smilyPattern.matcher(text);
        
        StringBuilder result = null;
        int lastEnd = 0;
        
        m.reset();
        while(m.find()) {
            if (result == null) {
                result = new StringBuilder();
            }

            result.append(text, lastEnd, m.start());
            
            String smily = m.group();
            String smilyUrl = smilyMap.get(smily);
            if (smilyUrl != null) {
                result.append("<img src=\"").append(baseUrl).append(smilyUrl).append('"');
                // TODO: Add optional class/style and width/height attributes
                result.append(" alt=\"").append(StringUtils.escapeHtml(smily)).append('"');
                result.append(" />");
                
            } else {
                // Append the smily code. Should never happen, as the regex is built from
                // the map keys...
                result.append(smily);
            }
            
            lastEnd = m.end();
        }
        
        if (result == null) {
            return text;
        }
        
        if (lastEnd < text.length()) {
            result.append(text, lastEnd, text.length());
        }
        
        return result;
    }

}
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
package org.shredzone.commons.text;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link TextFilter} that uses a sequence of other {@link TextFilter} to process a
 * text.
 *
 * @author Richard "Shred" Körber
 */
public class TextFilterSequence implements TextFilter {

    private final List<TextFilter> filterList = new ArrayList<TextFilter>();
    
    /**
     * Adds a new {@link TextFilter} to the filter chain. Filters are invoked in the order
     * they were added.
     * 
     * @param filter
     *            {@link TextFilter} to add
     */
    public void addTextFilter(TextFilter filter) {
        filterList.add(filter);
    }
    
    @Override
    public StringBuilder filter(StringBuilder text) {
        StringBuilder result = text;
        
        for (TextFilter filter : filterList) {
            result = filter.filter(result);
        }
        
        return result;
    }

}

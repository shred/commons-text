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

/**
 * Analyzes internal and external links.
 *
 * @author Richard "Shred" Körber
 */
public interface LinkAnalyzer {

    /**
     * Returns the type of a link. The returned string is used as CSS class name for the
     * resulting link. Used e.g. to highlight external links.
     *
     * @param url
     *            URL to be analyzed
     * @return CSS class name, or {@code null} if this is no special link
     */
    String linkType(String url);

    /**
     * Returns a resolved link.
     *
     * @param url
     *            URL to be analyzed, may be a relative link
     * @return Link to be used, never {@code null}
     */
    String linkUrl(String url);

    /**
     * Image a resolved image URL.
     *
     * @param url
     *            URL to be analyzed, may be a relative link
     * @return Image URL to be used, never {@code null}
     */
    String imageUrl(String url);

}

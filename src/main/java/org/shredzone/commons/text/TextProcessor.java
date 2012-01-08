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
 * A text processor modifies a section of the given {@link StringBuilder}.
 *
 * @author Richard "Shred" Körber
 */
public interface TextProcessor {

    /**
     * Modifies a section of the {@link StringBuilder}.
     * 
     * @param text
     *            {@link StringBuilder} to modify
     * @param start
     *            Starting position within the {@link StringBuilder}
     * @param end
     *            Ending position within the {@link StringBuilder} (exclusive)
     * @return New ending position after procession
     */
    int process(StringBuilder text, int start, int end);

}

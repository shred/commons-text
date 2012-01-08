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

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for {@link ParagraphFilterTest}.
 * 
 * @author Richard "Shred" Körber
 */
public class ParagraphFilterTest {
    
    @Test
    public void simpleTest() {
        ParagraphFilter filter = new ParagraphFilter();
        filter.setFoldLines(false);

        StringBuilder sb = new StringBuilder();
        sb.append("A normal double\n\nline feed.\n");
        sb.append("Another\nline feed\n\n\ntripled.");
        
        sb = filter.filter(sb);
        
        StringBuilder expect = new StringBuilder();
        expect.append("<p>A normal double</p><p>line feed.\n");
        expect.append("Another\nline feed</p><p>tripled.</p>");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

    @Test
    public void foldTest() {
        ParagraphFilter filter = new ParagraphFilter();
        filter.setFoldLines(true);

        StringBuilder sb = new StringBuilder();
        sb.append("A normal double\n\nline feed.\n");
        sb.append("Another\nline feed\n\n\ntripled.");
        
        sb = filter.filter(sb);
        
        StringBuilder expect = new StringBuilder();
        expect.append("<p>A normal double</p><p>line feed.<br />");
        expect.append("Another<br />line feed</p><p>tripled.</p>");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

}

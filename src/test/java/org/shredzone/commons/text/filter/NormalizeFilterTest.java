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
 * Unit test for {@link NormalizeFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class NormalizeFilterTest {
    
    @Test
    public void simpleTest() {
        NormalizeFilter filter = new NormalizeFilter();

        StringBuilder sb = new StringBuilder();
        sb.append("A normal double\n\nline feed.\n");
        sb.append("A CRLF\r\nline feed\r\n\r\ndoubled.");
        sb.append("A mac\rline feed\r\rdoubled.");
        
        sb = filter.filter(sb);
        
        StringBuilder expect = new StringBuilder();
        expect.append("A normal double\n\nline feed.\n");
        expect.append("A CRLF\nline feed\n\ndoubled.");
        expect.append("A mac\nline feed\n\ndoubled.");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

}

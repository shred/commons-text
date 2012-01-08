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
 * Unit test for {@link HtmlEscapeFilter}.
 *
 * @author Richard "Shred" Körber
 */
public class HtmlEscapeFilterTest {
    
    @Test
    public void filterTest() {
        HtmlEscapeFilter filter = new HtmlEscapeFilter();
        
        StringBuilder sb = new StringBuilder();
        sb.append("&Test <i>text</i> &\n with a \"quote\" << <& &<");
        
        sb = filter.filter(sb);
        
        StringBuilder expect = new StringBuilder();
        expect.append("&amp;Test &lt;i>text&lt;/i> &amp;\n");
        expect.append(" with a &quot;quote&quot; &lt;&lt; &lt;&amp; &amp;&lt;");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

}

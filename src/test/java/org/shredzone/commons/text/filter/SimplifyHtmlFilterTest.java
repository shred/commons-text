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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link SimplifyHtmlFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class SimplifyHtmlFilterTest {

    private SimplifyHtmlFilter filter;

    @Before
    public void setup() {
        filter = new SimplifyHtmlFilter();
        filter.addAcceptedTag("b");
        filter.addAcceptedTag("br");
        filter.addAcceptedTag("img", "src", "alt");
    }

    @Test
    public void simpleTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("<hr>This is <b  >a bad content</b>.");
        sb.append("<script>window.alert('Oops!')</script>");
        sb.append("<br /><img \nsrc=\"foo.gif\"><img src='foo2.gif'>");
        sb.append("<img src=type1.gif alt>");
        sb.append("<img src='type\"2.gif' alt=f\"oo noscale>");
        sb.append("<img     src = \"type3.gif\"  alt=\"bar foo\" noscale=\"no\">");
        sb = filter.filter(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("This is <b>a bad content</b>.");
        expect.append("window.alert('Oops!')");
        expect.append("<br /><img src=\"foo.gif\"><img src=\"foo2.gif\">");
        expect.append("<img src=\"type1.gif\" alt=\"alt\">");
        expect.append("<img src=\"type&quot;2.gif\" alt=\"f&quot;oo\">");
        expect.append("<img src=\"type3.gif\" alt=\"bar foo\">");

        Assert.assertEquals(expect.toString(), sb.toString());
    }

    @Test
    public void brokenTest() {
        StringBuilder sb = new StringBuilder();
        sb.append(">broken content<br");

        sb = filter.filter(sb);

        // Incomplete tags at the end are stripped as well
        StringBuilder expect = new StringBuilder();
        expect.append(">broken content");

        Assert.assertEquals(expect.toString(), sb.toString());
    }

}

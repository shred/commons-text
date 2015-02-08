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
import org.junit.Test;

/**
 * Unit test for {@link StripHtmlFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class StripHtmlFilterTest {

    @Test
    public void simpleTest() {
        StripHtmlFilter filter = new StripHtmlFilter();

        StringBuilder sb = new StringBuilder();
        sb.append("<hr>This is <b>a bad content</b>.");
        sb.append("<script>window.alert('Oops!')</script>");
        sb.append("<br /><IMG \nsrc=\"foo.gif\"><img src='foo2.gif'>");

        CharSequence out = filter.apply(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("This is a bad content.");
        expect.append(" window.alert('Oops!')");

        Assert.assertEquals(expect.toString(), out.toString());
    }

    @Test
    public void brokenTest() {
        StripHtmlFilter filter = new StripHtmlFilter();

        StringBuilder sb = new StringBuilder();
        sb.append(">broken content<br");

        CharSequence out = filter.apply(sb);

        // Incomplete tags at the end are stripped as well
        StringBuilder expect = new StringBuilder();
        expect.append(">broken content<br");

        Assert.assertEquals(expect.toString(), out.toString());
    }

}

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
 * Unit test for {@link SmilyFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class SmilyFilterTest {

    private SmilyFilter filter;

    @Before
    public void setup() {
        filter = new SmilyFilter();
        filter.setBaseUrl("foo/smileys");
        filter.addSmily(":-)", "happy.png");
        filter.addSmily(":-(", "sad.png");
        filter.addSmily(":-P", "tongue.png");
        filter.addSmily(":-))", "veryhappy.png");
    }

    @Test
    public void filterTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi! :-P\n");
        sb.append("I am happy :-)), yeah!\n");
        sb.append("(Even more happy :-)))");

        sb = filter.filter(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("Hi! <img src=\"foo/smileys/tongue.png\" alt=\":-P\" />\n");
        expect.append("I am happy <img src=\"foo/smileys/veryhappy.png\" alt=\":-))\" />, yeah!\n");
        expect.append("(Even more happy <img src=\"foo/smileys/veryhappy.png\" alt=\":-))\" />)");

        Assert.assertEquals(expect.toString(), sb.toString());
    }

    @Test
    public void noSmilyTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("There is no smile in here.");

        StringBuilder returned = filter.filter(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("There is no smile in here.");

        Assert.assertEquals(expect.toString(), returned.toString());

        // As the filter will not change the StringBuilder at all, we expect the
        // very same instance back.
        Assert.assertSame(sb, returned);
    }

}

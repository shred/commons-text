/*
 * Shredzone Commons
 *
 * Copyright (C) 2016 Richard "Shred" Körber
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
 * Unit test for {@link MarkdownFilter}.
 *
 * @author Richard "Shred" Körber
 */
public class MarkdownFilterTest {

    @Test
    public void simpleTest() {
        MarkdownFilter filter = new MarkdownFilter();

        StringBuilder sb = new StringBuilder();
        sb.append("A **bold** text.");

        CharSequence out = filter.apply(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("<p>A <strong>bold</strong> text.</p>\n");

        Assert.assertEquals(expect.toString(), out.toString());
    }

}

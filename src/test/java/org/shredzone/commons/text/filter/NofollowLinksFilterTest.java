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
 * Unit test for {@link NofollowLinksFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class NofollowLinksFilterTest {

    @Test
    public void simpleTest() {
        NofollowLinksFilter filter = new NofollowLinksFilter();

        StringBuilder sb = new StringBuilder();
        sb.append("This is a <a href=\"http://www.link.example/to/somewhere.gif\">http://www.link.example/to/somewhere.gif</a>.");
        sb.append("This is another <a href=\"https://www.link.example/to/\">https://www.link.example/to/</a> a directory.");
        sb.append("Download here <a href=\"ftp://ftp.foobar.example/file.png\">ftp://ftp.foobar.example/file.png</a>");

        CharSequence out = filter.apply(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("This is a <a href=\"http://www.link.example/to/somewhere.gif\" rel=\"nofollow\">http://www.link.example/to/somewhere.gif</a>.");
        expect.append("This is another <a href=\"https://www.link.example/to/\" rel=\"nofollow\">https://www.link.example/to/</a> a directory.");
        expect.append("Download here <a href=\"ftp://ftp.foobar.example/file.png\" rel=\"nofollow\">ftp://ftp.foobar.example/file.png</a>");

        Assert.assertEquals(expect.toString(), out.toString());
    }

}

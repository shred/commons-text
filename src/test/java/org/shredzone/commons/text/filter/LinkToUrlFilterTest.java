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
 * Unit test for {@link LinkToUrlFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class LinkToUrlFilterTest {
    
    @Test
    public void simpleTest() {
        LinkToUrlFilter filter = new LinkToUrlFilter();

        StringBuilder sb = new StringBuilder();
        sb.append("This is a http://www.link.example/to/somewhere.gif. ");
        sb.append("This is another https://www.link.example/to/ a directory. ");
        sb.append("Download here ftp://ftp.foobar.example/file.png");
        
        sb = filter.filter(sb);
       
        StringBuilder expect = new StringBuilder();
        expect.append("This is a <a href=\"http://www.link.example/to/somewhere.gif\">http://www.link.example/to/somewhere.gif</a>. ");
        expect.append("This is another <a href=\"https://www.link.example/to/\">https://www.link.example/to/</a> a directory. ");
        expect.append("Download here <a href=\"ftp://ftp.foobar.example/file.png\">ftp://ftp.foobar.example/file.png</a>");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

    @Test
    public void nofollowTest() {
        LinkToUrlFilter filter = new LinkToUrlFilter();
        filter.setFollow(false);

        StringBuilder sb = new StringBuilder();
        sb.append("This is a http://www.link.example/to/somewhere.gif ");
        sb.append("This is another HTTPS://www.link.example/to/ a directory. ");
        sb.append("Download here ftp://ftp.foobar.example/file.png");
        
        sb = filter.filter(sb);
        
        StringBuilder expect = new StringBuilder();
        expect.append("This is a <a href=\"http://www.link.example/to/somewhere.gif\" rel=\"nofollow\">http://www.link.example/to/somewhere.gif</a> ");
        expect.append("This is another <a href=\"HTTPS://www.link.example/to/\" rel=\"nofollow\">HTTPS://www.link.example/to/</a> a directory. ");
        expect.append("Download here <a href=\"ftp://ftp.foobar.example/file.png\" rel=\"nofollow\">ftp://ftp.foobar.example/file.png</a>");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

    @Test
    public void targetTest() {
        LinkToUrlFilter filter = new LinkToUrlFilter();
        filter.setTarget("_blank");

        StringBuilder sb = new StringBuilder();
        sb.append("This is a http://www.link.example/to/somewhere.gif ");
        sb.append("This is another https://www.link.example/to/ a directory. ");
        sb.append("Download here ftp://ftp.foobar.example/file.png");
        
        sb = filter.filter(sb);
        
        StringBuilder expect = new StringBuilder();
        expect.append("This is a <a href=\"http://www.link.example/to/somewhere.gif\" target=\"_blank\">http://www.link.example/to/somewhere.gif</a> ");
        expect.append("This is another <a href=\"https://www.link.example/to/\" target=\"_blank\">https://www.link.example/to/</a> a directory. ");
        expect.append("Download here <a href=\"ftp://ftp.foobar.example/file.png\" target=\"_blank\">ftp://ftp.foobar.example/file.png</a>");
        
        Assert.assertEquals(expect.toString(), sb.toString());
    }

}

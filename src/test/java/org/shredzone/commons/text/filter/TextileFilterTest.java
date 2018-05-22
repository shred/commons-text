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
import org.shredzone.commons.text.LinkAnalyzer;

/**
 * Unit test for {@link TextileFilterTest}.
 *
 * @author Richard "Shred" Körber
 */
public class TextileFilterTest {

    @Test
    public void simpleTest() {
        TextileFilter filter = new TextileFilter();

        StringBuilder sb = new StringBuilder();
        sb.append("A *bold* text.");

        CharSequence out = filter.apply(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("<p>A <strong>bold</strong> text.</p>");

        Assert.assertEquals(expect.toString(), out.toString());
    }

    @Test
    public void linkAnalyzerTest() {
        LinkAnalyzer analyzer = new LinkAnalyzer() {
            @Override
            public String linkUrl(String url) {
                return url + "?passed";
            }

            @Override
            public String linkType(String url) {
                return "external";
            }

            @Override
            public String imageUrl(String url) {
                return url + "?image";
            }
        };

        TextileFilter filter = new TextileFilter();
        filter.setAnalyzer(analyzer);

        StringBuilder sb = new StringBuilder();
        sb.append("A \"link\":http://example.com/page/1 to somewhere.");
        sb.append(" !/img/photo.jpeg!");

        CharSequence out = filter.apply(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("<p>");
        expect.append("A <a href=\"http://example.com/page/1?passed\" class=\"external\">link</a> to somewhere.");
        expect.append(" <img border=\"0\" src=\"/img/photo.jpeg?image\"/>");
        expect.append("</p>");

        Assert.assertEquals(expect.toString(), out.toString());
    }

}

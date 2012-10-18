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
package org.shredzone.commons.text;

import org.junit.Assert;
import org.junit.Test;
import org.shredzone.commons.text.filter.ParagraphFilter;
import org.shredzone.commons.text.filter.StripHtmlFilter;

/**
 * Unit test for {@link TextFilterSequence}.
 *
 * @author Richard "Shred" Körber
 */
public class TextFilterSequenceTest {

    @Test
    public void filterTest() {
        TextFilterSequence filter = new TextFilterSequence();
        filter.addTextFilter(new ParagraphFilter());
        filter.addTextFilter(new StripHtmlFilter());

        StringBuilder sb = new StringBuilder();
        sb.append("This is \n\na paragraph \nand a line break.");

        sb = filter.filter(sb);

        StringBuilder expect = new StringBuilder();
        expect.append("This is a paragraph and a line break.");

        Assert.assertEquals(expect.toString(), sb.toString());
    }

}

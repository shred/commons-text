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

import java.io.StringWriter;
import java.io.Writer;

import net.java.textilej.parser.Attributes;
import net.java.textilej.parser.DocumentBuilder;
import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.textile.TextileDialect;

import org.shredzone.commons.text.LinkAnalyzer;
import org.shredzone.commons.text.TextFilter;

/**
 * A filter that converts Textile markup to HTML.
 * <p>
 * Currently, Textile-j is used for conversion. Future releases may come with an own,
 * lightweight implementation.
 * 
 * @see <a href="http://java.net/projects/textile-j">Textile-j</a>
 * @author Richard "Shred" Körber
 */
public class TextileFilter implements TextFilter {

    private LinkAnalyzer analyzer;

    /**
     * Sets a {@link LinkAnalyzer} to be used for converting links and image source URLs.
     * 
     * @param analyzer
     *            {@link LinkAnalyzer} to be used
     */
    public void setAnalyzer(LinkAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Creates a Textile-j {@link DocumentBuilder} to be used for writing.
     * <p>
     * Note that this method is Textile-j specific and might be removed in future
     * versions.
     * 
     * @param writer
     *            {@link StringWriter} to write the HTML output to
     * @return {@link DocumentBuilder} to be used for the markup parser
     */
    protected DocumentBuilder createDocumentBuilder(StringWriter writer) {
        if (analyzer != null) {
            return new LinkAnalyzingHtmlDocumentBuilder(writer, analyzer);
        } else {
            HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
            builder.setEmitAsDocument(false);
            return builder;
        }
    }

    @Override
    public StringBuilder filter(StringBuilder text) {
        StringWriter writer = new StringWriter();

        MarkupParser parser = new MarkupParser(new TextileDialect());
        parser.setBuilder(createDocumentBuilder(writer));
        parser.parse(text.toString());

        return new StringBuilder(writer.toString());
    }

    /**
     * A {@link HtmlDocumentBuilder} that uses a {@link LinkAnalyzer}.
     */
    public static class LinkAnalyzingHtmlDocumentBuilder extends HtmlDocumentBuilder {

        private final LinkAnalyzer analyzer;

        public LinkAnalyzingHtmlDocumentBuilder(Writer writer, LinkAnalyzer analyzer) {
            super(writer);
            this.analyzer = analyzer;
            setEmitAsDocument(false);
        }

        @Override
        public void charactersUnescaped(String literal) {
            super.charactersUnescaped(literal);
        }

        @Override
        public void link(Attributes attributes, String hrefOrHashName, String text) {
            hrefOrHashName = analyzer.linkUrl(hrefOrHashName);
            String type = analyzer.linkType(hrefOrHashName);
            if (type != null) {
                attributes.setCssClass(type);
            }
            super.link(attributes, hrefOrHashName, text);
        }

        @Override
        public void imageLink(Attributes linkAttributes, Attributes imageAttributes, String href, String imageUrl) {
            href = analyzer.linkUrl(href);
            imageUrl = analyzer.imageUrl(imageUrl);
            super.imageLink(linkAttributes, imageAttributes, href, imageUrl);
        }

        @Override
        public void image(Attributes attributes, String url) {
            super.image(attributes, analyzer.imageUrl(url));
        }
    }

}
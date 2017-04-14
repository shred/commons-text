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

import java.util.Map;

import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;
import org.shredzone.commons.text.LinkAnalyzer;
import org.shredzone.commons.text.TextFilter;

/**
 * A filter that converts Markdown to HTML.
 * <p>
 * Uses <a href="https://github.com/atlassian/commonmark-java">commonmark-java</a> for
 * converting Markdown to HTML.
 *
 * @see <a href="https://github.com/atlassian/commonmark-java">commonmark-java</a>
 * @author Richard "Shred" Körber
 */
public class MarkdownFilter implements TextFilter {

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

    @Override
    public CharSequence apply(CharSequence text) {
        Node document = createParserBuilder().build().parse(text.toString());
        return createHtmlRendererBuilder().build().render(document);
    }

    /**
     * Creates and configures a commonmark {@link Parser.Builder} to be used for parsing.
     * <p>
     * Note that this method is commonmark specific and might be removed in future
     * versions.
     * <p>
     * Override this method to add commonmark extensions to the builder.
     *
     * @return {@link Parser.Builder} to be used for the markup parser
     */
    protected Parser.Builder createParserBuilder() {
        return Parser.builder();
    }

    /**
     * Creates and configures a commonmark {@link HtmlRenderer.Builder} to be used for
     * rendering HTML. The default implementation adds an {@link AttributeProvider} that
     * uses the {@link LinkAnalyzer} for analyzing links and generating HTML attributes.
     * <p>
     * Note that this method is commonmark specific and might be removed in future
     * versions.
     * <p>
     * Override this method to add commonmark extensions to the builder.
     *
     * @return {@link HtmlRenderer.Builder} to be used for HTML rendering
     */
    protected HtmlRenderer.Builder createHtmlRendererBuilder() {
        HtmlRenderer.Builder builder = HtmlRenderer.builder();
        if (analyzer != null) {
            builder.attributeProviderFactory(context -> new LinkAnalyzingAttributeProvider(analyzer));
        }
        return builder;
    }

    /**
     * An {@link AttributeProvider} that uses {@link LinkAnalyzer}.
     */
    private static class LinkAnalyzingAttributeProvider implements AttributeProvider {
        private static final String HTML_SRC = "src";
        private static final String HTML_HREF = "href";
        private static final String HTML_CLASS = "class";

        private final LinkAnalyzer analyzer;

        public LinkAnalyzingAttributeProvider(LinkAnalyzer analyzer) {
            this.analyzer = analyzer;
        }

        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof Image) {
                String src = attributes.get(HTML_SRC);
                if (src != null) {
                    attributes.put(HTML_SRC, analyzer.imageUrl(src));
                }
            } else if (node instanceof Link) {
                String href = attributes.get(HTML_HREF);
                if (href != null) {
                    attributes.put(HTML_HREF, analyzer.linkUrl(href));
                    String type = analyzer.linkType(href);
                    if (type != null) {
                        String cssClass = attributes.get(HTML_CLASS);
                        if (cssClass != null) {
                            attributes.put(HTML_CLASS, cssClass + ' ' + type);
                        } else {
                            attributes.put(HTML_CLASS, type);
                        }
                    }
                }
            }
        }
    }

}

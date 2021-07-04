/*
 * Shredzone Commons
 *
 * Copyright (C) 2021 Richard "Shred" Körber
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

import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;

/**
 * A filter that converts Markdown to HTML, but adding GitHub Flavored Markdown.
 *
 * @see <a href="https://github.github.com/gfm/">GitHub Flavored Markdown Spec</a>
 * @see <a href="https://github.com/atlassian/commonmark-java">commonmark-java</a>
 * @author Richard "Shred" Körber
 * @since 2.8
 */
public class GithubFlavoredMarkdownFilter extends MarkdownFilter {
    private boolean autolink;

    /**
     * Enables the autolink extension. It is disabled by default, to give the user full
     * control about link placement in the text.
     *
     * @param autolink
     *         {@code true} to enable the autolink extension. {@code false} by default.
     */
    public void setAutolinkEnabled(boolean autolink) {
        this.autolink = autolink;
    }

    @Override
    protected List<Extension> createExtensionList() {
        List<Extension> extensions = super.createExtensionList();
        extensions.add(TablesExtension.create());
        extensions.add(TaskListItemsExtension.create());
        extensions.add(StrikethroughExtension.create());
        extensions.add(HeadingAnchorExtension.create());
        if (autolink) {
            extensions.add(AutolinkExtension.create());
        }
        return extensions;
    }

}

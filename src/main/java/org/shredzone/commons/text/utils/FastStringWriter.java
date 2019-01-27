/*
 * Shredzone Commons
 *
 * Copyright (C) 2014 Richard "Shred" Körber
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
package org.shredzone.commons.text.utils;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A fast String writer that does not synchronize.
 *
 * @author Richard "Shred" Körber
 */
@ParametersAreNonnullByDefault
public class FastStringWriter extends Writer {

    private final StringBuilder sb;

    /**
     * Creates a new {@link FastStringWriter}.
     */
    public FastStringWriter() {
        sb = new StringBuilder();
    }

    /**
     * Creates a new {@link FastStringWriter}.
     *
     * @param capacity
     *            Initial capacity of the collector
     */
    public FastStringWriter(int capacity) {
        sb = new StringBuilder(capacity);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        sb.append(cbuf, off, len);
    }

    @Override
    public void write(int c) throws IOException {
        // superclass synchronizes
        sb.append((char) c);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        // superclass synchronizes
        sb.append(str, off, len);
    }

    @Override
    public void flush() throws IOException {
        // do nothing
    }

    @Override
    public void close() throws IOException {
        // do nothing
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    /**
     * Returns a {@link StringBuilder} with the contents.
     */
    public @Nonnull StringBuilder toStringBuilder() {
        return sb;
    }

}

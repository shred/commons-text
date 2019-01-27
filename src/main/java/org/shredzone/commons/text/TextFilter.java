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

import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A text filter modifies a text in a defined manner. It should be highly optimized for
 * speed.
 * <p>
 * Text filters are thread safe unless stated otherwise.
 * <p>
 * Text filters are stateless unless stated otherwise.
 *
 * @author Richard "Shred" Körber
 */
@FunctionalInterface
@ParametersAreNonnullByDefault
public interface TextFilter extends Function<CharSequence, CharSequence> {

    /**
     * Applies the filter on a {@link CharSequence} and returns a new {@link CharSequence}
     * with the modified text.
     *
     * @param t
     *            {@link CharSequence} with the contents to be filtered. If this is a
     *            {@link StringBuilder} instance, its contents <em>may</em> have changed
     *            after invocation, and this instance should not be used any more.
     * @return {@link CharSequence} with the filtered text.
     */
    @Override
    @Nonnull CharSequence apply(CharSequence t);

    /**
     * Returns a {@link StringBuilder} for the given {@link CharSequence}. If the
     * {@link CharSequence} is a {@link StringBuilder} instance, it will be reused.
     *
     * @param text
     *            {@link CharSequence} to get a {@link StringBuilder} from
     * @return {@link StringBuilder} instance
     */
    default @Nonnull StringBuilder toStringBuilder(CharSequence text) {
        if (text instanceof StringBuilder) {
            return (StringBuilder) text;
        } else {
            return new StringBuilder(text);
        }
    }

}

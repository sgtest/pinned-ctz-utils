package ru.concerteza.util.string.function;

import com.google.common.base.Function;

import javax.annotation.Nullable;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Guava function, transforms provided String to upper case
 *
 * @author  alexey
 * Date: 2/9/12
 */
public class UpperStringFunction implements Function<String, String> {
    public static UpperStringFunction INSTANCE = new UpperStringFunction();

    /**
     * @param input string
     * @return <code>input.toUpperCase()</code>
     */
    @Override
    public String apply(@Nullable String input) {
        checkNotNull(input);
        return input.toUpperCase(Locale.ENGLISH);
    }
}

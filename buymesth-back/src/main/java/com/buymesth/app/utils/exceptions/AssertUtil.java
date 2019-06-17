package com.buymesth.app.utils.exceptions;

import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class AssertUtil extends Assert {

    public static <X extends Throwable> void checkNotNull(@Nullable Object o, Supplier<? extends X> exceptionSupplier) throws X {
        if (o == null)
            throw exceptionSupplier.get();
    }

    public static <X extends Throwable> void isTrue(@Nullable Boolean o, Supplier<? extends X> exceptionSupplier) throws X {
        checkNotNull(o, exceptionSupplier);
        if (!o)
            throw exceptionSupplier.get();
    }

    public static <X extends Throwable> void checkNull(@Nullable Object o, Supplier<? extends X> exceptionSupplier) throws X {
        if (o != null)
            throw exceptionSupplier.get();
    }

}

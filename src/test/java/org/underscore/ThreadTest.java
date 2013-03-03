package org.underscore;

import junit.framework.TestCase;
import org.junit.Test;
import org.underscore.wrappers.$O;
import static org.underscore.wrappers.$T.$T;
import org.underscore.$;
import org.underscore.wrappers.$T;

import static org.underscore.$.$;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    @Test
    public void testTaskDelay() {
        $O<Boolean> result = $(false);
        $.delay(200, TimeUnit.MILLISECONDS, () -> result.set(true));


        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            return;
        }

        TestCase.assertTrue(result.get());
    }

    @Test
    public void testTaskScheduling() {
        $O<Boolean> result = $(false);
        $.schedule(200, TimeUnit.MILLISECONDS, () -> result.set(true));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            return;
        }

        TestCase.assertTrue(result.get());
        result.set(false);

        $T task =  $T(() -> result.set(true));
        task.schedule(200, TimeUnit.MILLISECONDS);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            return;
        }

        TestCase.assertTrue(result.get());

    }

}

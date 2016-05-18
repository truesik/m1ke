package org.j2overhead.m1ke;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

import static org.j2overhead.m1ke.TestData.ANOTHER_TEST_FILE_PATH;
import static org.j2overhead.m1ke.TestData.TEST_FILE_PATH;

public abstract class AbstractTest {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTest.class);

    @Before
    public void removeTestData() {
        try {
            FileUtils.deleteDirectory(new File(TEST_FILE_PATH));
            FileUtils.deleteDirectory(new File(ANOTHER_TEST_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public TestWatcher timeTestWatcher = new TestWatcher() {
        long start;
        @Override
        protected void starting(Description description) {
            start = System.nanoTime();
            LOG.info("+++ Test {} starting {}", description.getMethodName(), LocalTime.now());
        }

        @Override
        protected void finished(Description description) {
            long finish = System.nanoTime();
            final double seconds = ((double)(finish - start) / 1000000000);
            LOG.info("--- Test {} finished at {} for {} sec", description.getMethodName(), LocalTime.now(), seconds);
        }
    };
}
package com.chbi.ui;

import com.chbi.ApplicationConfiguration;
import com.chbi.rest.DataProvider;
import com.chbi.rest.UrlRewriter;
import com.chbi.ui.entities.BuildBox;
import com.chbi.ui.entities.JobColor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildJobControllerTest {

    private BuildJobController jobController;
    private BuildBox box;

    @Before
    public void setUp() {
        jobController = new BuildJobController(Mockito.mock(DataProvider.class), Mockito.mock(ApplicationConfiguration.class),
                Mockito.mock(UrlRewriter.class), Mockito.mock(GifProvider.class));

        box = new BuildBox();
    }

    @Test
    public void test_that_animated_blue_is_handled_as_green() {
        box.withColor(JobColor.blue_anime);

        assertThat(jobController.isNotGreen().apply(box)).isFalse();
    }

    @Test
    public void test_that_blue_is_handled_as_green() {
        box.withColor(JobColor.blue);

        assertThat(jobController.isNotGreen().apply(box)).isFalse();
    }

    @Test
    public void test_that_aborted_is_handled_as_not_green() {
        box.withColor(JobColor.aborted);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_aborted_anime_is_handled_as_not_green() {
        box.withColor(JobColor.aborted_anime);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_notbuilt_is_handled_as_not_green() {
        box.withColor(JobColor.notbuilt);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_notbuilt_anime_is_handled_as_not_green() {
        box.withColor(JobColor.notbuilt_anime);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_red_is_handled_as_not_green() {
        box.withColor(JobColor.red);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_red_anime_is_handled_as_not_green() {
        box.withColor(JobColor.red_anime);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_yellow_is_handled_as_not_green() {
        box.withColor(JobColor.yellow);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }

    @Test
    public void test_that_yellow_anime_is_handled_as_not_green() {
        box.withColor(JobColor.yellow_anime);

        assertThat(jobController.isNotGreen().apply(box)).isTrue();
    }
}
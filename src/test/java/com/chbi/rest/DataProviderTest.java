package com.chbi.rest;

import com.chbi.jenkins.JenkinsView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import sun.net.www.http.HttpClient;

@RunWith(SpringRunner.class)
@JsonTest
public class DataProviderTest {

    @Autowired
    private JacksonTester<JenkinsView> json;


    @Test
    public void test_that_jobs_can_deserialized() throws Exception {
        String content = "{\"_class\": \"hudson.model.AllView\",\"description\": null,\t\"jobs\": [{\"_class\": \"hudson.matrix.MatrixProject\",\"name\": \"build-package-dpkg-llvm\",\t\t\"url\": \"https://jenkins.mono-project.com/job/build-package-dpkg-llvm/\",\t\t\"color\": \"blue\"\t}, {\t\t\"_class\": \"hudson.matrix.MatrixProject\",\t\t\"name\": \"build-package-dpkg-mono\",\t\t\"url\": \"https://jenkins.mono-project.com/job/build-package-dpkg-mono/\",\t\t\"color\": \"blue\"\t}],\t\"name\": \"All\",\t\"property\": [],\t\"url\": \"https://jenkins.mono-project.com/view/All/\"}";
        ObjectContent<JenkinsView> jenkinsViewObjectContent = this.json.parse(content);
        System.out.println(jenkinsViewObjectContent);
    }


}
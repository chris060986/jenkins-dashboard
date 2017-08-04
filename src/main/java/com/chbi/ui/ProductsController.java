package com.chbi.ui;

import com.chbi.jenkins.JenkinsJob;
import com.chbi.rest.DataProvider;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.Charset;
import java.util.List;

@Controller
public class ProductsController {

  private DataProvider dataProvider;

  @Autowired
  ProductsController(DataProvider productService) {
    this.dataProvider = productService;
  }

  @RequestMapping("/jenkinsJobs")
  public String products(Model model) {
    List<JenkinsJob> jenkinsJobs = dataProvider.getJenkinsJobs();

    model.addAttribute("jenkinsJobs", jenkinsJobs);

    return "jenkinsJobs";
  }


}
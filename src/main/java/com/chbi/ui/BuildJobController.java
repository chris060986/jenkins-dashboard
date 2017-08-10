package com.chbi.ui;

import com.chbi.json.entities.JenkinsJob;
import com.chbi.rest.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BuildJobController {

  private DataProvider dataProvider;

  @Autowired
  BuildJobController(DataProvider productService) {
    this.dataProvider = productService;
  }

  @RequestMapping("/jenkinsJobs")
  public String products(Model model) {
    List<JenkinsJob> jenkinsJobs = dataProvider.getJenkinsJobs();

    model.addAttribute("jenkinsJobs", jenkinsJobs);

    return "jenkinsJobs";
  }


}
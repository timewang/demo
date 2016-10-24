package org.snailgary.demo.controller;

import org.snailgary.demo.service.CacheDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangzhongfu on 2016/5/17.
 */
@Controller
//@EnableAutoConfiguration
public class SampleController {

    @Autowired
    private CacheDemoService cacheDemoService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        this.cacheDemoService.createDemoCacheObj("12345678").toString();
        return "Hello World!";
    }

   /* public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }*/

}

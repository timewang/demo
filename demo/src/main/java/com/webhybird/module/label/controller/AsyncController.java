package com.webhybird.module.label.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by wangzhongfu on 2016/7/9.
 */
//@Controller
public class AsyncController {

    /*private final Queue<DeferredResult<ModelAndView>> eventQueue = new ConcurrentLinkedQueue<>();

    @RequestMapping("/normal")
    public String normalCall() throws InterruptedException {
        Thread.sleep(5000);
        return "normal";
    }

    @RequestMapping("/async")
    @ResponseBody
    public Callable<String> asyncCall(final Model model) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "async";
            }
        };
    }

    @RequestMapping("/deferred")
    public
    @ResponseBody
    DeferredResult<ModelAndView> deferredCall() {
        DeferredResult<ModelAndView> result = new DeferredResult<>();
        this.eventQueue.add(result);
        return result;
    }

    @Scheduled(fixedRate = 5000)
    public void simulateExternalThread() throws InterruptedException {
       // Thread.sleep(2000);
        for (DeferredResult<ModelAndView> result : this.eventQueue) {
            result.setResult(new ModelAndView("deferred"));
            this.eventQueue.remove(result);
        }
    }*/

}

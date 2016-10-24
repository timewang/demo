package com.webhybird.module.tree.controller;

import com.webhybird.framework.base.FreemarkerBaseController;
import com.webhybird.module.tree.constants.ZtreeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangzhongfu on 2015/5/27.
 */
@Controller
@RequestMapping(value = "tree")
public class ZtreeControllerFreemarker extends FreemarkerBaseController {


    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(){
        this.session.invalidate();
        return "redirect:https://demo.snailgary.org:8443/cas/logout?service=https://demo.snailgary.org:8443/demo";
    }

    /**
     * 跳转到查看页面
     * @return
     */
    @RequestMapping(value = "page")
    public String orgTree(Model model){
        model.addAttribute("type", ZtreeEnum.typeOrg.getEnumName());
        return "org/tree";
    }

    /**
     * 跳转到编辑页面
     * @return
     */
    @RequestMapping(value = "editpage")
    public String editPage(Model model){
        model.addAttribute("type", ZtreeEnum.typeOrg.getEnumName());
        return "org/edittree";
    }

}

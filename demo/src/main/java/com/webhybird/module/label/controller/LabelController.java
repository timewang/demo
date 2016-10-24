package com.webhybird.module.label.controller;

import com.webhybird.framework.base.ThymeleafBaseController;
import com.webhybird.module.label.entity.Label;
import com.webhybird.module.label.exception.DuplicateLabelException;
import com.webhybird.module.label.service.LabelService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
@Controller
public class LabelController extends ThymeleafBaseController {

    @Autowired
    private LabelService labelServiceImpl;
    private String fileNameExtractorRegex = "filename=\".+\"";

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(){
        this.session.invalidate();
        return "redirect:https://demo.snailgary.org:8443/cas/logout?service=https://demo.snailgary.org:8443/demo";
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping(value = "label",method = RequestMethod.GET)
    public String toAddLabel(SitePreference sitePreference,Model model){
        this.log.info("*********************************登录用户名：" + request.getRemoteUser());
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        model.addAttribute("_method","POST");
        model.addAttribute("label",new Label());
        if(principal != null){
            model.addAttribute("username",principal.getName());
        }

        return "label/compile";
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @return
     */
    @RequestMapping("label/editor/{id}")
    public String editor(@PathVariable("id") String id,Model model){
        Label label = this.labelServiceImpl.findById(id);

        model.addAttribute("_method","PUT");
        model.addAttribute("label",label);
        return "label/compile";
    }
    @RequestMapping("label/listAll")
    public String listAll(Model model){
        model.addAttribute("list",this.labelServiceImpl.findAllEntity());
        model.addAttribute("empltyList", Collections.EMPTY_LIST);
        return "label/listAll";
    }

    /**
     * 文件上传页面
     * @param model
     * @return
     */
    @RequestMapping("uploadImage")
    public String upload(Model model){
        model.addAttribute("_method","POST");
        return "upload";
    }

    @RequestMapping(value = "uploadImage",method = RequestMethod.POST)
    public String uploadImage(@RequestPart("value") Part part) throws IOException {
        System.out.println(part.getHeaderNames());
        System.out.println(part.getName());
        System.out.println(part.getContentType());
        System.out.println(part.getSize());
        System.out.println(part.getHeaders("content-disposition"));
        System.out.println(part.getHeader("content-disposition"));
        String fileName = "";
        String cotentDesc = part.getHeader("content-disposition");
        Pattern pattern = Pattern.compile(fileNameExtractorRegex);
        Matcher matcher = pattern.matcher(cotentDesc);
        if(matcher.find()){
            fileName = matcher.group();
            fileName = fileName.substring(10, fileName.length()-1);
        }
        System.out.println(fileName);
        part.write("C:\\WORK\\upload\\" + fileName);
        return "upload";
    }

    @RequestMapping(value = "uploadImage1",method = RequestMethod.POST)
    public String uploadImage1(@RequestPart("value") byte[] picture){

        return "upload";
    }

    @RequestMapping(value = "uploadImage2",method = RequestMethod.POST)
    public String uploadImage2(@RequestParam("value")MultipartFile multipartFile) throws IOException {
        multipartFile.transferTo(new File("C:\\WORK\\upload\\" + multipartFile.getOriginalFilename()));
        return "upload";
    }


   /* @RequestMapping("download")
    public ResponseEntity<byte[]> download() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "dict.txt");
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(getDictionaryFile()),
                headers, HttpStatus.CREATED);
    }*/

    /**
     * 统一的异常处理方法
     * @return
     */
    @ExceptionHandler(DuplicateLabelException.class)
    public String handleDuplicationException(){
        return "error/duplicate";
    }
}

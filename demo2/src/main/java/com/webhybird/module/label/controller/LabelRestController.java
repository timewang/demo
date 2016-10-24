package com.webhybird.module.label.controller;

import com.webhybird.framework.base.ThymeleafBaseController;
import com.webhybird.framework.constants.StaticConstants;
import com.webhybird.framework.dataquery.QueryParameter;
import com.webhybird.module.label.entity.Label;
import com.webhybird.module.label.service.LabelService;
import com.webhybird.module.label.vo.LabelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
@RestController
public class LabelRestController extends ThymeleafBaseController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private LabelService labelServiceImpl;


    /**
     *  保存标签
     * @param label 标签对象
     * @return Map json
     */
    @RequestMapping(value = "label",method = RequestMethod.POST)
    public Map<String,String> saveLabel(Label label){

        this.labelServiceImpl.saveLabel(label);

        return StaticConstants.SUCCESS;
    }

    /**
     *
     * @param label
     * @return
     */
    @RequestMapping(value = "label",method = RequestMethod.PUT)
    public Map<String,String> updateLabel(Label label){
        this.labelServiceImpl.updateLabel(label);
        return StaticConstants.SUCCESS;
    }

    @RequestMapping(value = "labels",method = RequestMethod.GET)
    public List<Label> findByCustomMethod(String value,String username){
        return this.labelServiceImpl.findByCustomMethod(value, username);
    }

    /**
     * 分页最终返回的是 PageImpl 对象
     * @param pageable
     * @return
     */
    @RequestMapping(value = "labelpage",method = RequestMethod.GET)
    public Page<Label> findLabelPage(@PageableDefault Pageable pageable){
        return this.labelServiceImpl.findLabelPage(pageable);
    }

    @RequestMapping(value = "labelpages",method = RequestMethod.GET)
    public Page<Label> findLabelPage(@PageableDefault Pageable pageable,Label label){
        return this.labelServiceImpl.findLabelPage(label,pageable);
    }

    @RequestMapping(value = "labelpages2",method = RequestMethod.GET)
    public Page<Object[]> findLabelPage2(@PageableDefault Pageable pageable,Label label){
        return this.labelServiceImpl.findAllConstructor(label, pageable);
    }

    @RequestMapping(value = "labelpages3",method = RequestMethod.GET)
    public List<Object[]> findLabelPage3(@PageableDefault Pageable pageable,Label label){
        return this.labelServiceImpl.findAll();
    }

    @RequestMapping(value = "labelpages4",method = RequestMethod.GET)
    public List<Object[]> findLabelPage4(@PageableDefault Pageable pageable,Label label){
        return this.labelServiceImpl.findAll(pageable);
    }
    @RequestMapping(value = "labelvos",method = RequestMethod.GET)
    List<LabelVO> findAllLabelVO(){
        return this.labelServiceImpl.findAllLabelVO();
    }
    @RequestMapping(value = "labelvos2",method = RequestMethod.GET)
    List<LabelVO> findAllLabelVO2(){
        return this.labelServiceImpl.findAllLabelVO2();
    }

    @RequestMapping(value = "labelsdata",method = RequestMethod.GET)
    public Page<Label> findLabelPage( QueryParameter queryDto,Pageable pageable){
        return this.labelServiceImpl.findLabelPage(queryDto, pageable);
    }

    @RequestMapping(value = "labelpages5",method = RequestMethod.GET)
    public  Page<Label>  findLabelPage5(Pageable pageable){
        return this.labelServiceImpl.findAllPage(pageable);
    }

    @RequestMapping(value = "labelsdata2",method = RequestMethod.GET)
    public Page<Label> findLabelPage2( QueryParameter queryDto,Pageable pageable){
        return this.labelServiceImpl.findAllPage(queryDto, pageable);
    }

    @RequestMapping(value = "labelsdata3",method = RequestMethod.GET)
    public List<Label> findLabelPage3(){
        return this.labelServiceImpl.findByCreatedBy();
    }

    @RequestMapping(value = "labelsdata4",method = RequestMethod.GET)
    public List<Label> findLabelPage4(){
        return this.labelServiceImpl.findByCreatedBy2();
    }

    @RequestMapping(value = "requestbody_test",method = RequestMethod.POST)
    public Map<String,String> requestBodyTest(@RequestBody Map<String,String> map){
        map.put("receive_rest_json","通过@RequestBody接收JSON参数成功");
        return map;
    }

    @RequestMapping(value = "requestbody_test2",method = RequestMethod.POST)
    public String requestBodyTest(@RequestBody Label label){
        return "SUCCESS" + label.getValue();
    }

}

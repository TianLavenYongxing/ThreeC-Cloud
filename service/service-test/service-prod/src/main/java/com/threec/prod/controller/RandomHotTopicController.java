package com.threec.prod.controller;

import com.threec.prod.utils.RandomHotTopicsUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.threec.tools.utils.R;

/**
 * Class RandomHotTopicController.
 * <p>
 * 随机热点话题
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 14/6/24
 */
@RestController
@RequestMapping("hot")
public class RandomHotTopicController {

    @GetMapping()
    public  R<Object> hot(){
       return new R<>().ok(RandomHotTopicsUtils.getRandomHotTopic());
    }
}

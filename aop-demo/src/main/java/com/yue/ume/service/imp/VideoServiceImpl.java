package com.yue.ume.service.imp;

import com.yue.ume.annotation.VideoAnnotation;
import com.yue.ume.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * @author YueYue
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Override
    @VideoAnnotation
    public String beforeAdvice(String beforeName) {
        System.out.println("beforeName = " + beforeName);
        return beforeName;
    }

    @Override
    public String afterAdvice(String afterName) {
        System.out.println("afterName = " + afterName);
        return afterName;
    }

    @Override
    public String aroundAdvice(String aroundName) {
        System.out.println("aroundName = " + aroundName);
        return aroundName;
    }

    @Override
    public String returnAdvice(String returnAdvice) {
        System.out.println("returnAdvice = " + returnAdvice);
        return returnAdvice;
    }

    @Override
    public String exceptionAdvice(String exceptionName) {
        throw new RuntimeException();
    }
}

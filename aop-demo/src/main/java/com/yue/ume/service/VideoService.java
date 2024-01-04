package com.yue.ume.service;

/**
 * @author YueYue
 */
public interface VideoService {
    String beforeAdvice(String beforeName);

    String afterAdvice(String afterName);

    String aroundAdvice(String aroundName);

    String returnAdvice(String returnAdive);

    String exceptionAdvice(String exceptionName);
}

package com.redis.demo.exception;

import lombok.extern.slf4j.Slf4j;
import com.redis.demo.controller.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UnionExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LOGGER.info("binder.getFieldDefaultPrefix {}",binder.getFieldDefaultPrefix());
        LOGGER.info("binder.getFieldMarkerPrefix {}",binder.getFieldMarkerPrefix());
    }
    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "harry");
    }
    /**
     * Description : 全局异常捕捉处理
     * Group :
     *
     * @author honghh
     * @date  2019/4/1 0001 10:34
     * @param ex
     * @return
     */
//    @ExceptionHandler(RRException.class)
//    public R apiExceptionHandler(RRException ex) {
//        log.error("ApiException 异常抛出：{}", ex);
//        return R.fail(ex);
//    }

}

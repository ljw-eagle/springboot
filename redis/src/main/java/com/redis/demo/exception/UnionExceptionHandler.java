package com.redis.demo.exception;

import com.redis.demo.bean.Result;
import lombok.extern.slf4j.Slf4j;
import com.redis.demo.controller.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 使用 @ControllerAdvice，不用任何的配置，
 * 只要把这个类放在项目中，Spring能扫描到的地方。就可以实现全局异常的回调。
 */
@Slf4j
@RestControllerAdvice
public class UnionExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
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
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Result handleException(Exception e){
        LOGGER.error(e.getMessage(), e);
        return Result.error();
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(RRException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Result handleRRException(RRException e){
        Result r = new Result();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }


}

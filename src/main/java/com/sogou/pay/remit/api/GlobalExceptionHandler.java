package com.sogou.pay.remit.api;

import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.base.Throwables;
import com.sogou.pay.remit.config.ProjectInfo;
import com.sogou.pay.remit.model.ApiResult;
import com.sogou.pay.remit.model.BadRequestException;
import com.sogou.pay.remit.model.ErrorCode;
import com.sogou.pay.remit.model.InternalErrorException;

import commons.utils.EnumConverter;
import commons.utils.ReflectUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ApiResult<?> internalServerError(Exception e) {
    e.printStackTrace();
    return new ApiResult<>(ErrorCode.INTERNAL_ERROR, Throwables.getStackTraceAsString(e));
  }

  @ExceptionHandler(DataAccessException.class)
  public ApiResult<?> dataAccessExcption(Exception e) {
    e.printStackTrace();
    return new ApiResult<>(ErrorCode.INTERNAL_ERROR, "interal db error");
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  public ApiResult<?> servletRequestBindingException(Exception e) {
    return new ApiResult<>(ErrorCode.BAD_REQUEST, Throwables.getStackTraceAsString(e));
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  public ApiResult<?> badRequestException(Exception e) {
    return new ApiResult<>(ErrorCode.BAD_REQUEST, Throwables.getStackTraceAsString(e));
  }

  @ExceptionHandler(InternalErrorException.class)
  public ApiResult<?> internalErrorException(Exception e) {
    return new ApiResult<>(ErrorCode.INTERNAL_ERROR, Throwables.getStackTraceAsString(e));
  }

  @ExceptionHandler(TypeMismatchException.class)
  public ApiResult<?> typeMismatchException(Exception e) {
    return new ApiResult<>(ErrorCode.BAD_REQUEST, e.toString());
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    List<Class<? extends Enum<?>>> enums = ReflectUtil.findEnums(ProjectInfo.PKG_PREFIX);
    for (Class<? extends Enum<?>> e : enums) {
      try {
        e.getMethod(EnumConverter.METHOD_NAME);
      } catch (Exception ex) {
        continue;
      }
      binder.registerCustomEditor(e, new EnumConverter(e));
    }
  }
}

package ch.fhnw.deardevbackend.aspect;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.util.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class UserValidationAspect {

    @Before("@annotation(ch.fhnw.deardevbackend.annotations.ValidateUserId) && args(dto,..)")
    public void validateUserId(Object dto) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        Integer userId;

        try {
            userId = (Integer) dto.getClass().getMethod("getUserId").invoke(dto);
        } catch (Exception e) {
            throw new YappiException("Unable to validate user ID");
        }

        assert currentUserId != null;
        if (!currentUserId.equals(userId)) {
            throw new YappiException("User ID mismatch");
        }
    }

    @Before("execution(* *(.., @ch.fhnw.deardevbackend.annotations.ValidateUserIdParam (*), ..))")
    public void validateUserIdParam(JoinPoint joinPoint) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof ch.fhnw.deardevbackend.annotations.ValidateUserIdParam) {
                    Integer userId = (Integer) args[i];
                    assert currentUserId != null;
                    if (!currentUserId.equals(userId)) {
                        throw new YappiException("User ID mismatch");
                    }
                }
            }
        }
    }
}

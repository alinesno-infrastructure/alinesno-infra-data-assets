package com.alinesno.infra.data.assets.collector.aspect;

import com.alinesno.infra.data.assets.key.KeyGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * 数据权限过滤
 * 
 * @author WeiXiaoJin
 * @version 1.0.0
 */
@Slf4j
@Component
@Aspect
public class DataPermissionSaveAdvice {

	@Autowired
	private HttpServletRequest request;

	// 拦截指定的方法,这里指只拦截TestService.getResultData这个方法
	@Pointcut("@annotation(com.alinesno.infra.data.assets.collector.aspect.PushAccess)")
	public void pointcut() {

	}

	// 执行方法前的拦截方法
	@SuppressWarnings("rawtypes")
	@Before("pointcut()")
	public void doBeforeMethod(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {

		// 获取目标方法的参数信息
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs(); // 请求接收的参数args
		Class<?> targetClass = joinPoint.getTarget().getClass();
		Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
		Method methodClass = targetClass.getMethod(methodName, parameterTypes);

		PushAccess dataSave = methodClass.getAnnotation(PushAccess.class);
		log.debug("methodClass = {}" , methodClass);
		log.debug("dataSave = {}" , dataSave);

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;

        HttpServletRequest request = attributes.getRequest();

		// 判断是否包含头
		String apiKey = request.getHeader(KeyGenerator.API_KEY) ;
		Assert.notNull(apiKey , "密钥 api-key is null") ;

		log.debug("api-key = {}" , apiKey) ;

		try{
			String[] ids = KeyGenerator.parseIdsFromKey(apiKey);
			String userId = ids[0] ;
			String orgId = ids[1] ;

			Assert.isTrue(StringUtils.isNoneBlank(userId), "userId is null");
			Assert.isTrue(StringUtils.isNoneBlank(orgId), "orgId is null");

			log.debug("userId = {}" , userId) ;
			log.debug("orgId = {}" , orgId) ;
		}catch (Exception e){
			throw new RuntimeException("密钥 api-key 格式错误:" + apiKey) ;
		}

	}

}

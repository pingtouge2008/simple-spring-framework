package com.ptg.springframework.aop.aspectj;

import com.ptg.springframework.aop.Pointcut;
import com.ptg.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * Aspect = PointCut + Advice, 可以把这个类理解为切面(Aspect)
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    /**
     * 切点: 表示一组 joint point，这些 joint point 或是通过逻辑关系组合起来，
     * 或是通过通配、正则表达式等方式集中起来，它定义了相应的 Advice 将要发生的地方。
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 增强：Advice 定义了在 Pointcut 里面定义的程序点具体要做的操作，它通过
     * before、after 和 around 来区别是在每个 joint point 之前、之后还是代替执行的代码。
     */
    private Advice advice;
    /**
     * 切点表达式
     */
    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
    @Override
    public Advice getAdvice() {
        return advice;
    }
    public void setAdvice(Advice advice){
        this.advice = advice;
    }
}

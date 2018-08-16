package com.github.alonwang.test.v5;

import com.github.alonwang.aop.aspectj.AspectJAfterReturningAdvice;
import com.github.alonwang.aop.aspectj.AspectJBeforeAdvice;
import com.github.alonwang.aop.aspectj.AspectJExpressionPointcut;
import com.github.alonwang.aop.config.AopConfig;
import com.github.alonwang.aop.config.AspectInstanceFactory;
import com.github.alonwang.aop.framework.AopConfigSupport;
import com.github.alonwang.aop.framework.CglibProxyFactory;
import com.github.alonwang.beans.factory.BeanFactory;
import com.github.alonwang.service.v5.PetStoreService;
import com.github.alonwang.tx.TransactionManager;
import com.github.alonwang.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CGLibAopProxyTest extends AbstractV5Test {
    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;
    private static AspectJExpressionPointcut pc = null;
    private TransactionManager tx;
	private BeanFactory beanFactory = null;
	private AspectInstanceFactory aspectInstanceFactory = null;
    @Before
    public void setUp() throws Exception {

		MessageTracker.clearMsgs();
        String expression = "execution(* com.github.alonwang.service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);
		beanFactory = this.getBeanFactory("petStore-v5.xml");
		aspectInstanceFactory = this.getAspectInstanceFactory("tx");
		aspectInstanceFactory.setBeanFactory(beanFactory);
        beforeAdvice = new AspectJBeforeAdvice(
				getAdviceMethod("start"),
                pc,
				aspectInstanceFactory);

        afterAdvice = new AspectJAfterReturningAdvice(
				getAdviceMethod("commit"),
                pc,
				aspectInstanceFactory);

    }

    @Test
    public void testGetProxy(){

        AopConfig config = new AopConfigSupport();

        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new PetStoreService());


        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStoreService proxy = (PetStoreService)proxyFactory.getProxy();

        proxy.placeOrder();


        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        proxy.toString();
    }
}

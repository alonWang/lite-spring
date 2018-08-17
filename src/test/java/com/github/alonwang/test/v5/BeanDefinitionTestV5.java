package com.github.alonwang.test.v5;

import com.github.alonwang.aop.aspectj.AspectJBeforeAdvice;
import com.github.alonwang.aop.aspectj.AspectJExpressionPointcut;
import com.github.alonwang.aop.config.AspectInstanceFactory;
import com.github.alonwang.aop.config.MethodLocatingFactory;
import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.ConstructorArgument;
import com.github.alonwang.beans.PropertyValue;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author weilong.wang Created on 2018/8/16
 */
public class BeanDefinitionTestV5 extends AbstractV5Test {
	@Test
	public void testAOPBean() {
		DefaultBeanFactory factory = (DefaultBeanFactory) this
				.getBeanFactory("petStore-v5.xml");
		{
			BeanDefinition bd = factory.getBeanDefinition("tx");
			Assert.assertTrue(bd.getBeanClassName()
					.equals(TransactionManager.class.getName()));
		}

		{
			BeanDefinition bd = factory.getBeanDefinition("placeOrder");
			Assert.assertTrue(bd.isSynthetic());
			Assert.assertTrue(
					bd.getBeanClass().equals(AspectJExpressionPointcut.class));

			PropertyValue pv = bd.getPropertyValues().get(0);
			Assert.assertEquals("expression", pv.getName());
			Assert.assertEquals(
					"execution(* com.github.alonwang.service.v5.*.placeOrder(..))",
					pv.getValue());
		}
		{
			String name = AspectJBeforeAdvice.class.getName() + "#0";
			BeanDefinition bd = factory.getBeanDefinition(name);
			Assert.assertTrue(
					bd.getBeanClass().equals(AspectJBeforeAdvice.class));

			Assert.assertTrue(bd.isSynthetic());

			List<ConstructorArgument.ValueHolder> args = bd
					.getConstructArgument().getArgumentValues();
			Assert.assertEquals(3, args.size());

			{
				BeanDefinition innerBeanDef = (BeanDefinition) args.get(0)
						.getValue();
				Assert.assertTrue(innerBeanDef.isSynthetic());
				Assert.assertTrue(innerBeanDef.getBeanClass()
						.equals(MethodLocatingFactory.class));

				List<PropertyValue> pvs = innerBeanDef.getPropertyValues();
				Assert.assertEquals("targetBeanName", pvs.get(0).getName());
				Assert.assertEquals("tx", pvs.get(0).getValue());
				Assert.assertEquals("methodName", pvs.get(1).getName());
				Assert.assertEquals("start", pvs.get(1).getValue());
			}

			{
				RuntimeBeanReference ref = (RuntimeBeanReference) args.get(1)
						.getValue();
				Assert.assertEquals("placeOrder", ref.getBeanName());
			}

			{
				BeanDefinition innerBeanDef = (BeanDefinition) args.get(2)
						.getValue();
				Assert.assertTrue(innerBeanDef.isSynthetic());
				Assert.assertTrue(innerBeanDef.getBeanClass()
						.equals(AspectInstanceFactory.class));

				List<PropertyValue> pvs = innerBeanDef.getPropertyValues();
				Assert.assertEquals("aspectBeanName", pvs.get(0).getName());
				Assert.assertEquals("tx", pvs.get(0).getValue());
			}
		}

	}
}

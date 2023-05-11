package tobyspring.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ConditionalTest {

	@Test
	void conditional() {
		//true
		ApplicationContextRunner contextRunner = new ApplicationContextRunner();
		contextRunner.withUserConfiguration(Config1.class)
				.run(context -> {
					assertThat(context).hasSingleBean(Config1.class);
					assertThat(context).hasSingleBean(MyBean.class);
				});

/*
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(Config1.class);
		ac.refresh();

		MyBean bean = ac.getBean(MyBean.class);
*/

		//false
		new ApplicationContextRunner().withUserConfiguration(Config2.class)
				.run(context -> {
					assertThat(context).doesNotHaveBean(Config2.class);
					assertThat(context).doesNotHaveBean(MyBean.class);
				});

/*
		AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
		ac2.register(Config2.class);
		ac2.refresh();

		MyBean bean2 = ac2.getBean(MyBean.class);
*/
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Conditional(TrueCondition.class)
	@interface TrueConditional {}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Conditional(BooleanCondition.class)
	@interface BooleanConditional {
		boolean value();
	}

	@Configuration
	@BooleanConditional(true)
	static class Config1 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Conditional(FalseCondition.class)
	@interface FalseConditional {}

	@Configuration
	@BooleanConditional(false)
	static class Config2 {
		@Bean
		MyBean myBean() {
			return new MyBean();
		}
	}

	static class MyBean {}

	static class TrueCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return true;
		}
	}

	static class BooleanCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
			Boolean value = (Boolean) annotationAttributes.get("value");
			return value;
		}
	}

	static class FalseCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return false;
		}
	}
}

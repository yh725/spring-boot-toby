package tobyspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

	private final ClassLoader classLoader;

	public MyAutoConfigImportSelector(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//		return new String[] {
//				"tobyspring.config.autoconfig.DispatcherServletConfig",
//				"tobyspring.config.autoconfig.TomcatWebServerConfig"
//		}
//		Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//		return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);

		List<String> autoConfigs = new ArrayList<>();
//		for (String candidates : ImportCandidates.load(MyAutoConfiguration.class, classLoader)) {
//			autoConfigs.add(candidates);
//		}

		ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

//		return autoConfigs.toArray(new String[0]);
//		return autoConfigs.stream().toArray(String[]::new);
		return Arrays.copyOf(autoConfigs.toArray(), autoConfigs.size(), String[].class);
	}
}

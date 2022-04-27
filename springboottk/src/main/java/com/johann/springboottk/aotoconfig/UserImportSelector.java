package com.johann.springboottk.aotoconfig;

import com.johann.springboottk.aotoconfig.dto.UserA;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @ClassName: UserImportSelector
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class UserImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        Map<String,Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(UserScan.class.getName());
        if (CollectionUtils.isEmpty(annotationAttributes)){
            return new String[0];
        }
        String[] basePackages = (String[])annotationAttributes.get("basePackages");

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        //这里相当于实现包含，@ComponentScan includeFilters
        scanner.addIncludeFilter(new AssignableTypeFilter(Object.class));
        //这里相当于实现包含，@ComponentScan excludeFilters
        //scanner.addExcludeFilter(new AssignableTypeFilter(Object.class));

        Set<String> classes = new HashSet<>();

        for (String basePackage :basePackages){
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            candidateComponents.forEach(e -> {
                classes.add(e.getBeanClassName());
            });
        }

        return classes.toArray(new String[classes.size()]);

        //return new String[]{UserA.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }
}

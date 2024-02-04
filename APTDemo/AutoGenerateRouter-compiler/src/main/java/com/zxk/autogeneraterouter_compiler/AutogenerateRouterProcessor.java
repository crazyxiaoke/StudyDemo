package com.zxk.autogeneraterouter_compiler;

import static com.alibaba.android.arouter.facade.enums.RouteType.ACTIVITY;
import static com.alibaba.android.arouter.facade.enums.RouteType.FRAGMENT;
import static com.alibaba.android.arouter.facade.enums.RouteType.PROVIDER;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author zhengxiaoke
 * @ClassName com.zxk.autogeneraterouter_compiler
 * @Description
 * @Date： 2024-01-19 15:08
 */
@SupportedOptions("MODULE_NAME")
@SupportedAnnotationTypes({"com.alibaba.android.arouter.facade.annotation.Route"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AutogenerateRouterProcessor extends AbstractProcessor {

    private Filer mFiler;

    private Messager messager;

    private Elements mElementUtils;

    private Types mTypesUtils;

    private TypeMirror mTypeActivity;
    private TypeMirror mTypeFragment;
    private TypeMirror mTypeProvider;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mTypesUtils = processingEnv.getTypeUtils();
        processingEnv.getTypeUtils();
        messager.printMessage(Diagnostic.Kind.NOTE, "AutogenerateRouterProcessor init");

        mTypeActivity = mElementUtils.getTypeElement(ACTIVITY.getClassName()).asType();
        mTypeFragment = mElementUtils.getTypeElement(FRAGMENT.getClassName()).asType();
        mTypeProvider = mElementUtils.getTypeElement(PROVIDER.getClassName()).asType();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) {
            return false;
        }
        Set<? extends Element> rootElement = roundEnvironment.getElementsAnnotatedWithAny(Set.of(Route.class, Autowired.class));

        if (rootElement == null || rootElement.isEmpty()) {
            return false;
        }
        List<MethodSpec> methods = new ArrayList<>();
        for (Element element : rootElement) {
            MethodSpec methodSpec = null;
            if (mTypesUtils.isSubtype(element.asType(), mTypeActivity)) {
                //Activity
                methodSpec = gotoActivity(element);
            } else if (mTypesUtils.isSubtype(element.asType(), mTypeFragment)) {
                //Fragment
            } else if (mTypesUtils.isSubtype(element.asType(), mTypeProvider)) {
                //Provider
            }
            if (methodSpec != null) {
                methods.add(methodSpec);
            }
            messager.printMessage(Diagnostic.Kind.NOTE, "开始解析Router." + element.getSimpleName());
            messager.printMessage(Diagnostic.Kind.NOTE, element.asType().toString());
//            MethodSpec methodSpec=MethodSpec.methodBuilder("goto").build();
        }

        TypeSpec typeSpec = TypeSpec.classBuilder("RouterManager")
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methods)
                .build();
        JavaFile javaFile = JavaFile.builder("com.zxk.manager", typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private MethodSpec gotoActivity(Element element) {
        MethodSpec methodSpec = MethodSpec.methodBuilder("").build();
        return methodSpec;
    }

    private MethodSpec getFragment(Element element) {
        MethodSpec methodSpec = MethodSpec.methodBuilder("").build();
        return methodSpec;
    }

    private MethodSpec getProvider(Element element) {
        MethodSpec methodSpec = MethodSpec.methodBuilder("").build();
        return methodSpec;
    }

}

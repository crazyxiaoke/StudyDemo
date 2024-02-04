package com.zxk.autogeneraterouter_compiler;

import static com.alibaba.android.arouter.facade.enums.RouteType.ACTIVITY;
import static com.alibaba.android.arouter.facade.enums.RouteType.FRAGMENT;
import static com.alibaba.android.arouter.facade.enums.RouteType.PROVIDER;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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
@SupportedAnnotationTypes({"com.alibaba.android.arouter.facade.annotation.Route","com.alibaba.android.arouter.facade.annotation.Autowired"})
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

    private String moduleName="";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        moduleName=processingEnv.getOptions().get("MODULE_NAME");
        mFiler = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mTypesUtils = processingEnv.getTypeUtils();
        processingEnv.getTypeUtils();
        messager.printMessage(Diagnostic.Kind.NOTE, "AutogenerateRouterProcessor init");
        messager.printMessage(Diagnostic.Kind.NOTE,"moduleName="+moduleName);
        mTypeActivity = mElementUtils.getTypeElement(ACTIVITY.getClassName()).asType();
        mTypeFragment = mElementUtils.getTypeElement(FRAGMENT.getClassName()).asType();
        mTypeProvider = mElementUtils.getTypeElement(PROVIDER.getClassName()).asType();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) {
            return false;
        }
        for (TypeElement typeElement : set) {
            messager.printMessage(Diagnostic.Kind.NOTE,"typeElement="+typeElement.getSimpleName());
            messager.printMessage(Diagnostic.Kind.NOTE,"type="+typeElement.asType());
        }
        Set<? extends Element> rootElement = roundEnvironment.getElementsAnnotatedWithAny(Set.of(Route.class, Autowired.class));

        if (rootElement == null || rootElement.isEmpty()) {
            return false;
        }
        List<MethodSpec> methods = new ArrayList<>();
        for (Element element : rootElement) {
            MethodSpec methodSpec = null;
            messager.printMessage(Diagnostic.Kind.NOTE,"type="+element.asType());
            messager.printMessage(Diagnostic.Kind.NOTE,"e.type="+element.getEnclosingElement().asType());
            Autowired autowired=element.getAnnotation(Autowired.class);
            messager.printMessage(Diagnostic.Kind.NOTE,"autowired="+autowired);
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

        TypeSpec typeSpec = TypeSpec.classBuilder("RouterManager"+moduleName)
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
        String simpleName=element.getSimpleName().toString();
        String path=element.getAnnotation(Route.class).path();
        messager.printMessage(Diagnostic.Kind.NOTE,element.getSimpleName());
        messager.printMessage(Diagnostic.Kind.NOTE,"path="+path);
        return MethodSpec.methodBuilder("goto"+simpleName)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .returns(void.class)
                .addStatement("$T.getInstance().build($S).navigation()", ClassName.get("com.alibaba.android.arouter.launcher","ARouter"),path)
                .build();
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

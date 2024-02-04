package com.zxk.apt_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zxk.apt_annotation.AptDemoAnnotation;

import java.io.IOException;
import java.lang.reflect.Method;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author zhengxiaoke
 * @ClassName com.zxk.apt_compiler
 * @Description
 * @Date： 2024-01-18 14:29
 */
@SupportedOptions("MODULE_NAME")
@SupportedAnnotationTypes({"com.zxk.apt_annotation.AptDemoAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AptDemoProcessor extends AbstractProcessor {

    private Elements mElementsUtils;

    private Types mTypesUtils;

    private Filer mFiler;

    private Messager mMessager;

    private String mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementsUtils = processingEnv.getElementUtils();
        mTypesUtils = processingEnv.getTypeUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();

        mModuleName = processingEnv.getOptions().get("MODULE_NAME");

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }
        Set<? extends Element> rootElement = roundEnv.getElementsAnnotatedWith(AptDemoAnnotation.class);
        if (rootElement != null && !rootElement.isEmpty()) {
            for (Element element : rootElement) {
                TypeElement typeElement=(TypeElement) element.getEnclosingElement();
                mMessager.printMessage(Diagnostic.Kind.NOTE, typeElement.getSimpleName());
                MethodSpec.Builder builder = MethodSpec.methodBuilder("test")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(String.class, "param")
                        .addStatement("$T.out.println($S)", System.class, mModuleName);
                String elementName = element.getSimpleName().toString();
                String desc = element.getAnnotation(AptDemoAnnotation.class).desc();
                builder.addStatement("$T.out.println($S)", System.class, "节点：" + elementName + "  描述：" + desc);

                MethodSpec main = builder.build();

                FieldSpec fieldSpec=FieldSpec.builder(String.class,"auto",Modifier.PRIVATE)
                        .initializer("$S","1").build();

                TypeSpec helloWorld = TypeSpec.classBuilder(typeElement.getSimpleName()+"_ViewBinding")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(main)
                        .addField(fieldSpec)
                        .build();
                String packageName=mElementsUtils.getPackageOf(typeElement).getQualifiedName().toString();
                mMessager.printMessage(Diagnostic.Kind.NOTE,"packageName="+packageName);
                JavaFile javaFile = JavaFile.builder(packageName, helloWorld).build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}

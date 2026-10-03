package com.one_half_men.annotations;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Generated;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import com.google.auto.service.AutoService;
import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;

import lombok.Builder;

@SupportedAnnotationTypes({ "com.one_half_men.annotations.*" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class QueryProcessor extends AbstractProcessor {
    protected static final String QUERY_SUFFIX = "Query";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(Query.class)) {
            switch (element.getKind()) {
                case ElementKind.CLASS:
                case ElementKind.RECORD:
                case ElementKind.ENUM:
                    break;
                default:
                    error("Query can only apply to classes, records, and enums", element);
                    return false;
            }

            try {
                generateClass(element);
            } catch (Exception e) {
                error(e.getMessage(), element);
            }
        }

        return false;
    }

    private void generateClass(Element element) throws Exception {
        Types typeUtils = processingEnv.getTypeUtils();
        Elements elementUtils = processingEnv.getElementUtils();

        PackageElement pkg = processingEnv.getElementUtils().getPackageOf(element);

        Stream<FieldSpec> fields = element.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD)
                .map(field -> {
                    TypeName optionalFieldType = TypeName.get(typeUtils.getDeclaredType(
                            elementUtils.getTypeElement("java.util.Optional"),
                            field.asType()));

                    FieldSpec fieldSpec = FieldSpec
                            .builder(optionalFieldType, field.getSimpleName().toString(), Modifier.PUBLIC)
                            .build();

                    return fieldSpec;
                });

        String queryClassName = element.getSimpleName() + QUERY_SUFFIX;

        AnnotationSpec generated = AnnotationSpec.builder(Generated.class)
                .addMember("value", String.format("\"%s\"", getClass().getPackageName()))
                .build();

        TypeSpec classBuilder = TypeSpec.classBuilder(queryClassName)
                .addFields((Iterable<FieldSpec>) fields::iterator)
                .addAnnotation(generated)
                .addAnnotation(Builder.class).build();

        JavaFile javaFile = JavaFile.builder(pkg.toString(), classBuilder).build();

        javaFile.writeTo(processingEnv.getFiler());
    }

    private void note(String msg, Element e) {
        printMessage(Diagnostic.Kind.NOTE, msg, e);
    }

    private void error(String msg, Element e) {
        printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    private void printMessage(Diagnostic.Kind kind, String msg, Element e) {
        processingEnv.getMessager().printMessage(kind, msg, e);
    }
}

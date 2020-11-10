package com.async4j.core.annotationprocessor;

import com.async4j.core.Async;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;

import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@SupportedAnnotationTypes({"Async"})
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AsyncAnnotationProcessor extends AbstractProcessor
{
    private Messager messager;
    private Elements elementUtils;

    @Override
    public void init(ProcessingEnvironment processingEnvironment)
    {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Async.class);
        for (Element element : annotatedElements)
        {
            if (element.getKind() == ElementKind.METHOD)
            {
                checkMethod((ExecutableElement) element);
            }
        }
        // don't claim annotations to allow other processors to process them
        return false;
    }

    private synchronized void checkMethod(ExecutableElement method)
    {
        Element enclosingElement = method.getEnclosingElement();
        if(enclosingElement.getKind() != ElementKind.INTERFACE)
        {
            printError(method, "Only methods in interface can be annotated as async.");
        }
        if (method.getModifiers().contains(Modifier.STATIC))
        {
            printError(method, "Conventions is that all async method should never be static");
        }
        List <? extends VariableElement> parameters = method.getParameters();
        if(parameters.size() < 1)
        {
            printError(method, "Async Method should have at least one parameter." +
                    "The first parameter of Async method should be of type boolean which will be used to determine if method going to blocking or non-blocking.");
        }
        else
        {
            if (!parameters.get(0).asType().toString().equals("boolean"))
            {
                printError(method, "Async Method should have at least one parameter."+
                        "The first parameter of Async method should be of type boolean which will be used to determine if method going to blocking or non-blocking.");
            }
        }
        String name = method.getSimpleName().toString();
        if(!name.startsWith("async"))
        {
            printWarning(method, "Conventions is that all async method should start with name \"async\"");
        }
    }

    private synchronized void printWarning(Element element, String message)
    {
        messager.printMessage(Diagnostic.Kind.WARNING, message, element);
    }

    private synchronized void printError(Element element, String message)
    {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }


    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        return new HashSet<>(Arrays.asList(Async.class.getName()));
    }


}

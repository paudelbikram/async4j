package com.async4j.annotationprocessor;

import com.async4j.Async;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"com.async4j.Async"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AsyncAnnotationProcessor extends AbstractProcessor
{
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        // get messager for printing errors
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //Get elements annotated with the @Async annotation
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Async.class);
        for (Element element : annotatedElements) {
            if (element.getKind() == ElementKind.METHOD) {
                // only handle methods as targets
                checkMethod((ExecutableElement) element);
            }
        }
        // don't claim annotations to allow other processors to process them
        return false;
    }


    private synchronized void checkMethod(ExecutableElement method)
    {
        Class clazz = method.getClass();

        if(!clazz.isInterface())
        {
            System.out.println(clazz.getSimpleName());
            System.out.println(clazz);
            System.out.println(!clazz.isInterface());
            printError(method, "Only methods in interface can be annotated as async. "+clazz.getSimpleName()+" is not interface.");
        }
        if (method.getModifiers().contains(Modifier.STATIC))
        {
            printError(method, "Conventions is that all async method should never be static");
        }

        List parameters = method.getParameters();
        if(parameters.size() < 1)
        {
            printError(method, "Async Method should have at least one parameter. " +
                    "The first parameter of Async method should be of type boolean which will be used to determine if method going to blocking or non-blocking.");
        }
        else
        {
            if (!parameters.get(0).getClass().equals(boolean.class))
            {
                printError(method, "Async Method should have at least one parameter. " +
                        "The first parameter of Async method should be of type boolean which will be used to determine if method going to blocking or non-blocking.");
            }
        }

        String name = method.getSimpleName().toString();
        if(!name.startsWith("async"))
        {
            printWarning(method, "Conventions is that all async method should start with name \"async\"");
        }
    }


    private synchronized void printWarning(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message, element);
    }


    private synchronized void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

}

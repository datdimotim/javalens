package com.dimotim.lensjava.codegen;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SupportedAnnotationTypes("com.dimotim.lensjava.codegen.MakeLens")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class MakeLensProcessor extends AbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // Iterate over annotations that this processor supports
        annotations.forEach(annotation -> {
            // Get annotated elements (should be classes)
            roundEnv.getElementsAnnotatedWith(annotation).forEach(element -> {

                // Get fully-qualified class name
                String qualifiedClassName = ((TypeElement)element).getQualifiedName().toString();

                // Extract package name
                int index = qualifiedClassName.lastIndexOf('.');
                String packageName = null;
                if(index > 0) {
                    packageName = qualifiedClassName.substring(0,index);
                }

                // Extract class name
                String className = qualifiedClassName.substring(index + 1);

                // Get annotation
                MakeLens builder = element.getAnnotation(MakeLens.class);

                // Construct builder class name
                String generatedClassName = className + builder.suffix();

                // Get fields (assume that all fields have associated setters)
                // field name [String] -> field type [String]
                Map<String,String> fields = element.getEnclosedElements().stream()
                        .filter(e -> e instanceof VariableElement)
                        .collect(Collectors.toMap(e -> e.getSimpleName().toString(),e -> e.asType().toString()));

                // Write source file
                try {
                    writeBuilder(className, generatedClassName, packageName, fields);
                } catch(IOException ex) {
                    System.err.printf("Unable to generate builder class `%s` for `%s` class",generatedClassName,className);
                }
            });
        });
        return true;
    }

    private void writeBuilder(String className, String builderClassName, String packageName, Map<String,String> fields) throws IOException{

        // Create builder source file
        JavaFileObject builderSource = processingEnv.getFiler().createSourceFile(packageName + "." + builderClassName);

        try(PrintWriter out = new PrintWriter(builderSource.openWriter())) {

            // Write package name
            if(packageName != null) {
                out.printf("package %s;%n%n",packageName);
            }

            // Write import lens
            out.println("import com.dimotim.lensjava.Lens;");

            // Write class name
            out.printf("public class %s {%n",builderClassName);


            // Write setter methods
            fields.forEach((fieldName, fieldType) -> {
                String boxedType = convertTypeToGenericCompatible(fieldType);
                String getter = evalGetterPrefix(fieldType) + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                String wither = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                out.printf("  public static final Lens<%s,%s> %s = Lens.of(%s::%s, %s::%s);%n%n"
                        ,className,boxedType,fieldName,className,getter, className, wither);
            });

            // Close class
            out.print("}");
        }
    }

    private static String evalGetterPrefix(String type){
        if(type.equals("boolean") || type.equals("Boolean")){
            return "is";
        } else {
            return "get";
        }
    }

    private static String convertTypeToGenericCompatible(String type){
        Map<String, String> box = new HashMap<>();
        box.put("byte", "Byte");
        box.put("short", "Short");
        box.put("char", "Character");
        box.put("int", "Integer");
        box.put("long", "Long");
        box.put("boolean", "Boolean");
        box.put("float", "Float");
        box.put("double", "Double");

        return box.getOrDefault(type,type);
    }

    /*

    class PersonL {
        public static Lens<Person, String> name = Lens.of(Person::getName, Person::withName);
    }

    @Value
    @With
    public class Person {
        String name;
        int age;
    }

    */
}

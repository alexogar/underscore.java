package org.underscore.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.underscore.util.VariationType;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: alexogar
 * Date: 3/1/13
 * Time: 12:21 AM
 */
@SupportedAnnotationTypes("org.underscore.processor.IncludeInMain")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MainGenerator extends AbstractProcessor {

    private List<String> methods = new ArrayList<>();

    private final TreePathScanner<Object, Trees> visitor = new TreePathScanner<Object, Trees>() {

        @Override
        public Object visitMethod(MethodTree node, Trees trees) {

            String method = node.toString();

            //We need to check for variations
//            dblbreak:
//            for (VariableTree vt : node.getParameters()) {
//                for (AnnotationTree at : vt.getModifiers().getAnnotations()) {
//                    String atStr = at.toString();
//                    if (atStr.contains("WithVariation")) {
//                        //Sorry for that hack, I`m lazy to traverse all that.
//
//                        Matcher m = Pattern.compile("generic\\s+=\\s+\"(\\w+)\"").matcher(atStr);
//
//                        m.find();
//                        String genericName = m.group(1);
//
//                        for (VariationType type : VariationType.values()) {
//                            if (atStr.contains(type.name())) {
//
//                                String newMethod = method.replaceFirst("@WithVariation\\([\\w\\s=\\{\\},\\\"]+\\)\\s([\\w\\<\\>]+)",type.present(genericName));
//
//                                methods.add(newMethod);
//
//                            }
//                        }
//
//                    } else {
//                        //Sorry for that too.
//
//                        break dblbreak;
//                    }
//                }
//            }

            methods.add(method);

            return super.visitMethod(node, trees);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };
    private Trees trees;

    @Override
    public void init(ProcessingEnvironment pe) {
        super.init(pe);
        trees = Trees.instance(pe);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.out.println(roundEnv);
        for (Element e : roundEnv.getElementsAnnotatedWith(IncludeInMain.class)) {
            // Normally the ellement should represent a class

            TreePath tp = trees.getPath(e);
            // invoke the scanner
            visitor.scan(tp, trees);
        }

        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile("org.underscore.$");

            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(new ClassTemplateLoader(MainGenerator.class, "/"));

            Template template = cfg.getTemplate("$.ftl");

            OutputStream stream = jfo.openOutputStream();

            Writer out = new OutputStreamWriter(stream);
            Map<String, Object> data = new HashMap<>();
            data.put("methods", methods);
            template.process(data, out);
            out.flush();

            out.close();
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return true;

    }


}

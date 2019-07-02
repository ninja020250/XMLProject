/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author nhatc
 */
public class XJCGenerateJavaObj {

    public static void main(String[] args) {
        try {
            String output = "src/java";
            SchemaCompiler sc = XJC.createSchemaCompiler();

            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(SAXParseException saxpe) {
                    System.out.println("error: " + saxpe.getMessage());
                }

                @Override
                public void fatalError(SAXParseException saxpe) {
                    System.out.println("fatal: " + saxpe.getMessage());
                }

                @Override
                public void warning(SAXParseException saxpe) {
                    System.out.println("warming: " + saxpe.getMessage());
                }

                @Override
                public void info(SAXParseException saxpe) {
                    System.out.println("info: " + saxpe.getMessage());
                }
            });
            sc.forcePackageName("cuonghn.jaxb");
            File schema = new File("web/schema/storeSchema.xsd");
            InputSource is =  new  InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model =  sc.bind();
            JCodeModel code =  model.generateCode(null, null);
            code.build(new File(output));
            System.out.println(" create jaxb obj is Finised");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package hierlmeier

import org.springframework.context.ApplicationContextAware  //@todo delete this? not needed anymore i guess

import grails.converters.XML

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;


class PdfService {

    static transactional = false
    static scope = "singleton"

    FopFactory fopFactory = FopFactory.newInstance();
    TransformerFactory transFactory = TransformerFactory.newInstance();
    
    def File generatePDF(generateThis, useThisXslFile, outputFilePath) {
        
        def xmlstring
        
        if(generateThis instanceof java.lang.String) {
            xmlstring = generateThis
        } else {           // convert Object to XML String
            def obj2xmlconverter
            XML.use("deep"){ obj2xmlconverter = generateThis as XML }  //@todo "deep" führt dazu das ALLE anderen Belege des entsprechenden Kunden auch ausgelesen werden
            // führt zwar zu keinem fehlerhaften verhalten, aber joa ... großer xml string oida
            xmlstring = obj2xmlconverter.toString()
        }
        
        // setup input
        Source src = new StreamSource(new StringReader(xmlstring))           
                    
        // debug msgs
        println("Input: XML (" + xmlstring + ")")
        println("Stylesheet: " + useThisXslFile)
        println("Transforming...")

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent()
        // FOUseragent can be configured further if needed
        foUserAgent.setProducer("PV_Hiermleier")
        //foUserAgent.setCreationDate(new Date()); //Rechnung/Belegdatum?
        //foUserAgent.setTitle("Invoice No 138716847");

        // setup output
        File file = new File(outputFilePath)
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file))            
        
        try {
            // construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out)

            // setup XSLT
            Transformer transformer = transFactory.newTransformer(new StreamSource(useThisXslFile))

            // set the value of a <param> in the stylesheet, irgendwie unnötig der Xalan kann eh kein 2.0
            transformer.setParameter("versionParam", "2.0")
        
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler())

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res)
        } catch (Exception e) {
            println("** Exception during PdfService.generatePDF(input, transform, output): " + e.toString())
        }
        finally {
            out.close();
        }
        
        return file
    }
    
    //@deprecated
    def ByteArrayOutputStream generatePDF(printThis, useThisXslFile) {
        
        def xmlstring
        
        if(printThis instanceof java.lang.String) {
            xmlstring = printThis
        } else {           // convert Object to XML String
            def obj2xmlconverter
            XML.use("deep"){ obj2xmlconverter = printThis as XML }  //@todo "deep" führt dazu das ALLE anderen Belege des entsprechenden Kunden auch ausgelesen werden
            // führt zwar zu keinem fehlerhaften verhalten, aber joa ... großer xml string oida
            xmlstring = obj2xmlconverter.toString()
        }
        
        Source src = new StreamSource(new StringReader(xmlstring))
                    
        // debug msgs
        println("Input: XML (" + xmlstring + ")")
        println("Stylesheet: " + useThisXslFile)
        println("Transforming...")

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent()
        // FOUseragent can be configured further if needed
        foUserAgent.setProducer("PV_Hiermleier")
        //foUserAgent.setCreationDate(new Date()); //Rechnung/Belegdatum?
        //foUserAgent.setTitle("Invoice No 138716847");

        // setup output
        ByteArrayOutputStream out = new ByteArrayOutputStream()

        // construct fop with desired output format
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out)

        // setup XSLT
        Transformer transformer = transFactory.newTransformer(new StreamSource(useThisXslFile))

        // set the value of a <param> in the stylesheet, irgendwie unnötig der Xalan kann eh kein 2.0
        transformer.setParameter("versionParam", "2.0")
        
        // Resulting SAX events (the generated FO) must be piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler())

        // Start XSLT transformation and FOP processing
        transformer.transform(src, res)
            
        return out
    }
}
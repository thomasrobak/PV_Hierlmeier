package hierlmeier

import org.springframework.context.ApplicationContextAware

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


class PrintService {

    static transactional = false
    static scope = "singleton"
    
    // configure fopFactory as desired
    FopFactory fopFactory = FopFactory.newInstance();
    TransformerFactory transFactory = TransformerFactory.newInstance();
    

    def ByteArrayOutputStream generatePDF(printThisDomainClass, useThisXslFile) {
        
        // convert Beleg to XML String
        def obj2xmlconverter
        XML.use("deep"){ obj2xmlconverter = printThisDomainClass as XML }  //@todo "deep" führt dazu das ALLE anderen Belege des entsprechenden Kunden auch ausgelesen werden
                                                                           // führt zwar zu keinem fehlerhaften verhalten, aber joa ... großer xml string oida
        def xmlstring = obj2xmlconverter.toString()
        Source src = new StreamSource(new StringReader(xmlstring))
                    
        // debug msgs
        println("Input: XML (" + xmlstring + ")")
        println("Stylesheet: " + useThisXslFile)
        println()
        println("Transforming...")

        // FOUseragent can be configured further if needed
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent()

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
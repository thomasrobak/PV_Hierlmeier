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


class PrintBelegService {

    static transactional = false
    static scope = "singleton"
    
    // configure fopFactory as desired
    FopFactory fopFactory = FopFactory.newInstance();
    TransformerFactory transFactory = TransformerFactory.newInstance();

    def ByteArrayOutputStream generatePDF(Beleg beleg, xsltfileURL) {
        
        def obj2xmlconverter
        XML.use("deep"){ obj2xmlconverter = beleg as XML }
        
        // Setup directories
        //File baseDir = new File(".");
        //File outDir = new File(baseDir, "out");
        //outDir.mkdirs();

        // Setup input files
        def xmlstring = obj2xmlconverter.toString()
        File xsltfile
        
        if(xsltfileURL != null){
            xsltfile = new File(xsltfileURL)
        }
        else {
            xsltfile = new File("src/java/belegstylesheet.xsl")
        }
            
        
        println("Input: XML (" + xmlstring + ")");
        println("Stylesheet: " + xsltfile);
        println();
        println("Transforming...");

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // configure foUserAgent as desired

        // Setup output
        //OutputStream out = new java.io.FileOutputStream(pdffile);
        //out = new java.io.BufferedOutputStream(out);
        
        //****************Setup a buffer to obtain the content length
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Construct fop with desired output format
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

        // Setup XSLT
        Transformer transformer = transFactory.newTransformer(new StreamSource(xsltfile));

        // Set the value of a <param> in the stylesheet
        transformer.setParameter("versionParam", "2.0");

        // Setup input for XSLT transformation
        Source src = new StreamSource(new StringReader(xmlstring));

        // Resulting SAX events (the generated FO) must be piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler());

        // Start XSLT transformation and FOP processing
        transformer.transform(src, res);
            
        return out
    }
}
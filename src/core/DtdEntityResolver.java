package core;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// class for parsing data into xml with dtd
public class DtdEntityResolver implements EntityResolver2 {
    private final File dtdFile;

    public DtdEntityResolver(File dtdFile) {
        this.dtdFile = dtdFile;
    }

    @Override
    public InputSource getExternalSubset(String name, String baseURI) {
        return null;
    }

    @Override
    public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
        if (systemId != null && systemId.endsWith(".dtd")) {
            return new InputSource(new FileInputStream(dtdFile));
        }
        return null;
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return resolveEntity(null, publicId, null, systemId);
    }
}
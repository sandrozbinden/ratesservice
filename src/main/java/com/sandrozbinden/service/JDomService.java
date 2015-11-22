package com.sandrozbinden.service;

import java.io.IOException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;

@Component
public class JDomService {

	public Document loadDocument(URL url) throws IOException {
		SAXBuilder jdomBuilder = new SAXBuilder();
		try {
			return jdomBuilder.build(url);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		}
	}
}

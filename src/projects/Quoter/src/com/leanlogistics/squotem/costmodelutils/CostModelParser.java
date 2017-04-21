package com.leanlogistics.squotem.costmodelutils;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.leanlogistics.squotem.model.CostModel;
import com.leanlogistics.squotem.model.costmodel.CostModelRules;

public class CostModelParser {
    
    public static final void setCostModelRulesInCostModel(CostModel cm) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CostModelRules.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(cm.getXmlContent());
            cm.setCostModelObject((CostModelRules) jaxbUnmarshaller.unmarshal(reader));
        }
        catch (JAXBException e) {
            // TODO: We should set up a proper warning because this probably means incorrect XML content
            e.printStackTrace(System.out);
            cm.setErrorMessage(e.getMessage());                        
        }
    }

}

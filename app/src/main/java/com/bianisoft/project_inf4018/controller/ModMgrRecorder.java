package com.bianisoft.project_inf4018.controller;

import java.io.StringWriter;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.bianisoft.project_inf4018.model.DomainFacade;
import com.bianisoft.project_inf4018.model.GameCommand;
import com.bianisoft.project_inf4018.model.RawSensorsCommand;


public class ModMgrRecorder {
    private int nIdxToStartRawSensor;
    private int nIdxToStopRawSensor;
    private int nIdxToStartGameCommand;
    private int nIdxToStopGameCommand;
    
    
    public ModMgrRecorder(){
        reset();
    }

    public void reset(){
        nIdxToStartRawSensor = -1;
        nIdxToStopRawSensor = -1;
        nIdxToStartGameCommand = -1;
        nIdxToStopGameCommand = -1;
    }
    
    public void start(){
        nIdxToStartRawSensor = ApplicationFacade.getFacadeObject().GetRawStartIdx();
        nIdxToStartGameCommand = ApplicationFacade.getFacadeObject().GetCommandStartIdx();
        
        nIdxToStopRawSensor = -1;
        nIdxToStopGameCommand = -1;
    }

    public void stop(){
        if((nIdxToStartRawSensor != -1) && (nIdxToStartGameCommand != -1)){
            nIdxToStopRawSensor = ApplicationFacade.getFacadeObject().GetRawStartIdx();
            nIdxToStopGameCommand = ApplicationFacade.getFacadeObject().GetCommandStartIdx();
        }
    }

    private String convertXML2String(Document xmlDocument){
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;

        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter writer = new StringWriter();

            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

            String xmlString = writer.getBuffer().toString();
            return xmlString;
        }catch (TransformerException | IllegalArgumentException e){
            e.printStackTrace();
        }

        return "";
    }

    public String getDOMString(){
        DomainFacade objDomain= DomainFacade.getFacadeObject();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //Level 1
            Element rootElement = doc.createElement("EventsLog");
            doc.appendChild(rootElement);

            //Level 2
            Element UserID = doc.createElement("UserID");
            UserID.appendChild(doc.createTextNode(objDomain.getWorld().getPlayerShip().GetPlayerName()));
            rootElement.appendChild(UserID);

            Element EpochTime = doc.createElement("EpochTime");
            EpochTime.appendChild(doc.createTextNode(Long.toString(Calendar.getInstance().getTime().getTime())));
            rootElement.appendChild(EpochTime);
/*
            Element GameCommands = doc.createElement("GameCommands");
            rootElement.appendChild(GameCommands);
*/
            Element RawSensors = doc.createElement("RawSensors");
            rootElement.appendChild(RawSensors);
/*
            //Level 3, under GameCommands
            for(GameCommand objGameCommands : objDomain.getGameCommandsFromTo(nIdxToStartGameCommand, nIdxToStopGameCommand)){
                Element GameCommandEvent = doc.createElement("GameCommandEvent");
                GameCommands.appendChild(GameCommandEvent);

                //Level 4
                Element TransformationX = doc.createElement("TransformationX");
                TransformationX.appendChild(doc.createTextNode(Integer.toString(objGameCommands.nTransformationX)));
                GameCommandEvent.appendChild(TransformationX);

                Element TransformationY = doc.createElement("TransformationY");
                TransformationY.appendChild(doc.createTextNode(Integer.toString(objGameCommands.nTransformationY)));
                GameCommandEvent.appendChild(TransformationY);

                Element TransformationZ = doc.createElement("TransformationZ");
                TransformationZ.appendChild(doc.createTextNode(Integer.toString(objGameCommands.nTransformationZ)));
                GameCommandEvent.appendChild(TransformationZ);
            }
*/
            //Level 3, under RawSensors
            for(RawSensorsCommand objRawSensors : objDomain.getRawSensorCommandsFromTo(nIdxToStartRawSensor, nIdxToStopRawSensor)){
                Element RawSensorEvent = doc.createElement("RawSensorEvent");
                RawSensors.appendChild(RawSensorEvent);

                //Level 4
                Element RotationRoll = doc.createElement("RotationRoll");
                RotationRoll.appendChild(doc.createTextNode(Integer.toString(objRawSensors.nRotationRoll)));
                RawSensorEvent.appendChild(RotationRoll);

                Element RotationPitch = doc.createElement("RotationPitch");
                RotationPitch.appendChild(doc.createTextNode(Integer.toString(objRawSensors.nRotationPitch)));
                RawSensorEvent.appendChild(RotationPitch);
/*
                Element AcceleratingZ = doc.createElement("AcceleratingZ");
                AcceleratingZ.appendChild(doc.createTextNode(Integer.toString(objRawSensors.nAcceleratingZ)));
                RawSensorEvent.appendChild(AcceleratingZ);*/
            }

            return convertXML2String(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

        return "";
    }

    public int getStartRawSensor(){
        return nIdxToStartRawSensor;
    }
    
    public int getStopRawSensor(){
        return nIdxToStopRawSensor;
    }
    
    public int getStartGameCommand(){
        return nIdxToStartGameCommand;
    }
    
    public int getStopGameCommand(){
        return nIdxToStopGameCommand;
    }
}

/*
<EventsLog>
    <UserID>player12345</UserID>
    <EpochTime>12345.12345</EpochTime>
    <RawSensors>
        <RawSensorEvent>
            <RotationRoll>0</RotationRoll>
            <RotationPitch>0</RotationPitch>
        </RawSensorEvent>
    <RawSensors>
</EvenstLog>
*/
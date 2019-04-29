package com.bianisoft.androittest.application;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.GameCommand;
import com.bianisoft.androittest.domain.RawSensorsCommand;


public class ModMgrRecorderPoster {
    private int nIdxToStartRawSensor;
    private int nIdxToStopRawSensor;
    private int nIdxToStartGameCommand;
    private int nIdxToStopGameCommand;
    
    
    public ModMgrRecorderPoster(){
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

    private void sendPOSTRequest(String p_stPost) throws IOException {
        System.out.println(p_stPost);
        
        String stURL = "http://192.168.0.133:8081/rest_service";
        stURL+= "?userID=" + DomainFacade.getFacadeObject().getWorld().getPlayerShip().GetPlayerName();
        stURL+= "&time=" + Long.toString(Calendar.getInstance().getTime().getTime());
        
        URL obj = new URL(stURL);
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        // postConnection.setRequestProperty("userId", "a1bcdefgh");
        postConnection.setRequestProperty("Content-Type", "text/xml");
        postConnection.setDoOutput(true);

        OutputStream os = postConnection.getOutputStream();
        os.write(p_stPost.getBytes());
        os.flush();
        os.close();
        
        int responseCode = postConnection.getResponseCode();
        System.out.println("POST Response Code :  " + responseCode);
        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
        
        if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
            System.out.println("POST WORKED");
        } else {
            System.out.println("POST NOT WORKED");
        }
    }    
    
    public void sendToServer(){
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

            Element GameCommands = doc.createElement("GameCommands");
            rootElement.appendChild(GameCommands);

            Element RawSensors = doc.createElement("RawSensors");
            rootElement.appendChild(RawSensors);

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

                Element AcceleratingZ = doc.createElement("AcceleratingZ");
                AcceleratingZ.appendChild(doc.createTextNode(Integer.toString(objRawSensors.nAcceleratingZ)));
                RawSensorEvent.appendChild(AcceleratingZ);
            }

            sendPOSTRequest(convertXML2String(doc));
        } catch (ParserConfigurationException | IOException pce) {
          pce.printStackTrace();
        }
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
    <UserID></UserID>
    <EpochTime></EpochTime>
    <GameCommands>
        <GameCommandEvent>
            <TransformationX> </TransformationX>
            <TransformationY> </TransformationY>
            <TransformationZ> </TransformationZ>
        </GameCommandEvent>
    </GameCommands>
    <RawSensors>
        <RawSensorEvent>
            <RotationRoll> </RotationRoll>
            <RotationPitch> </RotationPitch>
            <AcceleratingZ> </AcceleratingZ>
        </RawSensorEvent>
    <RawSensors>
</EvenstLog>
*/
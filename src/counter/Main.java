
package counter;



import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class Main {

    Snmp snmp = null;
    public String address = null;
    public String Oid = ".1.3.6.1.2.1.1.1.0";
    public String color = ".1.3.6.1.4.1.18334.1.1.1.5.7.2.2.1.5.2.1";
    public String colorP = ".1.3.6.1.4.1.18334.1.1.1.5.7.2.2.1.5.2.2";
    public String Oid3 = " 1.3.6.1.2.1.43.5.1.1.17.1";
    public String negro = ".1.3.6.1.4.1.18334.1.1.1.5.7.2.2.1.5.1.1";
    public String negroP = ".1.3.6.1.4.1.18334.1.1.1.5.7.2.2.1.5.1.2";
    public String Ricoh = ".1.3.6.1.4.1.367.3.2.1.2.19.1.0";
    /*

    */

    /**
     * Constructor
     *
     * @param add
     */
    public Main(String add) {

        address = add;

    }

    public static void main(String[] args) throws IOException {

          Ventana ventana = new Ventana();
          ventana.setVisible(false);

    }


    /**
     * Start the Snmp session. If you forget the listen() method you will not
     * get any answers because the communication is asynchronous and the
     * listen() method listens for answers.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();

    }

    /**
     * Method which takes a single OID and returns the response from the agent
     * as a String.
     *
     * @param oid
     * @return
     */
    public String getAsString(OID oid) {

        ResponseEvent event = null;
        try {
            event = get(new OID[]{oid});
            return event.getResponse().get(0).getVariable().toString();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Impresora Apagada");
        
        return "No conectado";
}
    }

    /**
     * This method is capable of handling multiple OIDs
     *
     * @param oids
     * @return
     * @throws IOException
     */
    public ResponseEvent get(OID oids[]) throws IOException {
        PDU pdu = new PDU();
        for (OID oid : oids) {
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("GET timed out");
    }

    /**
     * This method returns a Target, which contains information about where the
     * data should be fetched and how.
     *
     * @return
     */
    public Target getTarget() {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(20);
        target.setVersion(SnmpConstants.version2c);

        return target;

    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author 5927161
 */
public class MultiagentesTrem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        jade.core.Runtime rt = jade.core.Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createMainContainer(p);

        try {
            AgentController carroController, tremController, semaforoController;
            
            carroController = cc.createNewAgent("AgenteCarro", "multiagentestrem.AgenteCarro", args);
            carroController.start();
            
            tremController = cc.createNewAgent("AgenteTrem", "multiagentestrem.AgenteTrem", args);
            tremController.start();
            
            semaforoController = cc.createNewAgent("AgenteSemaforo", "multiagentestrem.AgenteSemaforo", args);
            semaforoController.start();
            
        } catch (StaleProxyException ex) {
            Logger.getLogger(MultiagentesTrem.class.getName()).log(Level.SEVERE, null, ex);
        }

        //JFrame jf = new TelaPrincipal();
        //jf.setVisible(true);
    }

}

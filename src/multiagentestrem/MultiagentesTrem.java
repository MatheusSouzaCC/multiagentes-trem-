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
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author 5927161
 */
public class MultiagentesTrem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TelaPrincipal telaPrincipal = new TelaPrincipal();

        jade.core.Runtime rt = jade.core.Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createMainContainer(p);

        try {
            AgentController carroController, carroController2, tremController, semaforoController;

            carroController = cc.createNewAgent("AgenteCarro", "multiagentestrem.AgenteCarro", new Object[]{telaPrincipal,  1});
            carroController.start();

            carroController2 = cc.createNewAgent("AgenteCarro2", "multiagentestrem.AgenteCarro", new Object[]{telaPrincipal, 2});
            carroController2.start();

            tremController = cc.createNewAgent("AgenteTrem", "multiagentestrem.AgenteTrem", args);
            tremController.start();

            semaforoController = cc.createNewAgent("AgenteSemaforo", "multiagentestrem.AgenteSemaforo", new Object[]{telaPrincipal});
            semaforoController.start();

            telaPrincipal.setVisible(true);

        } catch (StaleProxyException ex) {
            Logger.getLogger(MultiagentesTrem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

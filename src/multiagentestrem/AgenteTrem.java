/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.Random;

/**
 *
 * @author julio
 */
public class AgenteTrem extends Agent {
    private int velocidade = 100;
    
    protected void setup() {
        //System.out.println("Trem inicializado");
        //Recebendo Mensagem do Semaforo

        // Enviando Mensagem pro AgenteSemaforo avisando que está perto!
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                Random rand = new Random();
                String mensagem;
                System.out.println("Trem Inicializado");

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgenteSemaforo", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("Distância");

                int n = rand.nextInt(30) + 1;

                // Manda mensagem apenas se está próximo
                mensagem = ("Estou no KM:" + n);
                System.out.println(mensagem);
                msg.setContent(mensagem);

                myAgent.send(msg);
            }
        });

    }

}

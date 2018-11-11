/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author julio
 */
public class AgenteTrem extends Agent {

    protected void setup() {

        //Recebendo Mensagem do Semaforo
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    ACLMessage reply = msg.createReply();
                    String content = msg.getContent();

                    //Recebendo a mensagem do Semaforo.
                    //Recebendo a pergunta do Semaforo: "Está Próximo?"
                    if (content.equalsIgnoreCase("Proximo")) {
                        reply.setPerformative(ACLMessage.INFORM);
                        
                        //Verifica a distancia
                        if (true) {
                            reply.setContent("Recebi sua Pergunta! Estou Próximo!");
                        } else {
                            reply.setContent("Recebi sua Pergunta! Estou Longe!");
                        }

                        myAgent.send(reply);
                        System.out.println("O agente " + msg.getSender().getName() + " avisou que o Semaforo está Fechado");
                        System.out.println("Vou aguardar a liberação!");

                    }
                } else {
                    block();
                }
            }
        });

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.AID;

/**
 *
 * @author julio
 */
public class AgenteSemaforo extends Agent {

    boolean tremProximo;
    boolean ultimoTremProximo;

    protected void setup() {

        //Recebendo Mensagem do Trem!
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    String content = msg.getContent();
                    String ontology = msg.getOntology();
                    String array[] = new String[2];

                    //Recebendo a mensagem do Trem.
                    if (ontology.matches("Distância")) {

                        //Verifica a distancia
                        array = content.split(":");

                        if ((Integer.parseInt(array[1]) > 5) && (Integer.parseInt(array[1]) < 15)) {
                            tremProximo = true; //Se o Trem está próximo
                        } else {
                            tremProximo = false; //Se o Trem está Longe
                        }

                    } else if (ontology.matches("Sinaleira")) {

                        ACLMessage reply = msg.createReply();

                        reply.setPerformative(ACLMessage.INFORM);
                        if (tremProximo) {
                            reply.setContent("Fechado");
                        } else {
                            reply.setContent("Aberto");
                        }

                        myAgent.send(reply);

                    }
                } else {
                    block();
                }
            }
        });

        // Enviando Mensagem pro AgenteCarro que o Sinal Está fechado!
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgenteCarro", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("Estrada");

                if (tremProximo) {
                    if (ultimoTremProximo == false) {
                        msg.setContent("Fechado");
                        ultimoTremProximo = true;
                        myAgent.send(msg);
                    }
                } else {
                    if (ultimoTremProximo == true) {
                        msg.setContent("Aberto");
                        ultimoTremProximo = false;
                        myAgent.send(msg);
                    }
                }

            }
        });
    }

}

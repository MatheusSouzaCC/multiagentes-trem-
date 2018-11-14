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
    
    protected void setup() {

        // Enviando Mensagem pro AgenteTrem perguntando se está perto!
        addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgenteCarro", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("Linha");
                msg.setContent("Proximidade");
                myAgent.send(msg);
            }
        });

        //Recebendo Retorno do Trem!
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("--> " + msg.getSender().getName() + ":" + content);
                    
                    //Verificar a Resposta do Trem se Perto ou não
                    if (content.matches("Próximo")) {
                        tremProximo = true;
                    }
                    
                } else //Com o block() bloqueamos o comportamento até que uma nova
                //mensagem chegue ao agente e assim evitamos consumir ciclos
                // da CPU.
                {
                    block();
                }
            }
        });

        // Enviando Mensagem pro AgenteCarro que o Sinal Está fechado!
        addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgenteCarro", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("Estrada");
                
                if (tremProximo) {
                msg.setContent("Fechado");
                }else{
                msg.setContent("Aberto");
                }
                
                myAgent.send(msg);
            }
        });
    }
}

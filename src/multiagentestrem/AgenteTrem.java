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
import java.awt.Rectangle;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author julio
 */
public class AgenteTrem extends Agent {

    private int velocidade = 50;
    private int direcao = 1;
    private JLabel trem, trilho;
    private int estradaAtual = 3;//1 para estrada 1, 2 para estrada 2, 3 para trilho

    protected void setup() {
        InicializarTrem();
        //Recebendo Mensagem do Semaforo

        // Enviando Mensagem pro AgenteSemaforo avisando que está perto!
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {                
                Random rand = new Random();
                String mensagem;

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgenteSemaforo", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("Distância");

                Rectangle bounds = trem.getBounds();

                // Manda mensagem apenas se está próximo
                mensagem = ("Estou no KM:" + bounds.x);
                msg.setContent(mensagem);

                myAgent.send(msg);
                
                block(1000);
            }
        });       
        
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                Rectangle bounds = trem.getBounds();
                trem.setBounds(bounds.x - 5, bounds.y, bounds.width, bounds.height);
                
                if(bounds.x == -700){
                    trem.setBounds(1300, 17, 718, 66);
                    block(5000);
                }
                
                block(40);
            }
        });
    }
    
        /*
        Inicializa o carro e adiciona na tela
     */
    private void InicializarTrem() {
        trem = new JLabel();
        Object[] args = getArguments();
        //a instância da tela é passada como primeiro argumento
        TelaPrincipal telaPrincipal = (TelaPrincipal) args[0];

        trilho = telaPrincipal.getTrilho();

        //o parâmtro [1] é a direção (1 pra cima e 2 pra baixo)
        int x = 1300, y = 17;

        trem.setBounds(x, y, 718, 66);

        String path = System.getProperty("user.dir");
        ImageIcon image = new ImageIcon(path + File.separator + "src" + File.separator + "multiagentestrem" + File.separator +"imagens" + File.separator + "trem" + ".png");
        trem.setIcon(image);


        trilho.add(trem);
        trilho.revalidate();
        trilho.repaint();

    }
}

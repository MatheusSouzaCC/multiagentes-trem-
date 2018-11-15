/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author julio
 */
public class AgenteCarro extends Agent {
    private int velocidade = 50;

    protected void setup() {
        InicializarCarro();
        
        
//Recebendo Mensagem do Semaforo
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    ACLMessage reply = msg.createReply();
                    String content = msg.getContent();

                    //Recebendo a mensagem do Semaforo.
                    //Recebendo que o Semaforo está fechado, e que vai aguardar a liberação
                    if (content.equalsIgnoreCase("Fechado")) {
                        System.out.println("O agente " + msg.getSender().getName() + " avisou que o Semaforo está Fechado");
                        System.out.println("Vou aguardar a liberação!");

                    //Recebendo que o Semaforo está Aberto, e que vai Continuar o trajeto
                    } else if (content.equalsIgnoreCase("Aberto")) {
                        System.out.println("O agente " + msg.getSender().getName() + " avisou que o Semaforo está Aberto");
                        System.out.println("Vou continuar meu Trajeto!");
                    }
                } else {
                    block();
                }
            }
        });
    }

    /*
        Inicializa o carro e adiciona na tela
    */
    private void InicializarCarro() {
        Object[] args = getArguments() ;
        //a instância da telaé passada como primeiro argumento
        TelaPrincipal telaPrincipal = (TelaPrincipal) args[0];
        
       JLabel estrada1 = telaPrincipal.getEstrada1();
       JLabel estrada2 = telaPrincipal.getEstrada2();
        
        //o parâmtro [1] é a direção (1 pra cima e 2 pra baixo)
        int direcao = 1;
        if(args[1] != null){
            direcao = (int)args[1];
        }
        int x = 110, y = 275;
        
        if(direcao == 2){
            x = 35;
            y = 0;
        }
        
        JLabel carro = new JLabel();
        carro.setBounds(x, y, 20, 41);
        

        String path =  System.getProperty("user.dir");
        ImageIcon image = new ImageIcon(path + "\\src\\multiagentestrem\\imagens\\carro" + direcao + ".png");
        carro.setIcon(image);
        
        if(direcao == 1){
            estrada1.add(carro);
            estrada1.revalidate();
            estrada1.repaint();
        }else{
            estrada2.add(carro);
            estrada2.revalidate();
            estrada2.repaint();
        }
        
        System.out.println("Carro inicializado");
    }

}

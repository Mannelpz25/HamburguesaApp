package hamburguesa;

public class Hamburguesa {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HamburguesaServidor().setVisible(true);
            }
        });
        
        new HamburguesaCliente().setVisible(true);
        
    }
    
}

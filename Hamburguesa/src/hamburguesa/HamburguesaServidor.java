package hamburguesa;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class HamburguesaServidor extends javax.swing.JFrame{
    // Declaración de variables para apariencia
    javax.swing.JLabel lblNombreRepartidor;    
    javax.swing.JLabel lblTiempoEntrega;
    javax.swing.JTextField txtNombreRepartidor;
    javax.swing.JTextField txtTiempoEntrega;
    javax.swing.JTextArea txtAreaEstatus;
    javax.swing.JScrollPane ScrollEstatus;
    javax.swing.JButton btnAsignarPedido;
    //Socket Servidor que aceptara conexiones de clientes
    ServerSocket serverSocket;
    //Objeto que se comunicará con los clientes para enviar y recibir datos.
    Socket cliente;
    //Objetos que permitiran leer y escribir flujos de bits través del socket.
    InputStream entrada;
    OutputStream salida;
    //Objetos que permitiran leer valores de algun tipo desde el socket.
    DataInputStream lector;
    DataOutputStream escritor;

    //Esta variable sirve para mantener o cerrar el ciclo de lectura de datos
    //desde el cliente
    boolean leyendo;
    
    public HamburguesaServidor(){
        initComponents();
        abrirSocket();
    }
    
    private void initComponents() {
        /* En esta sección se tienen los elementos que compondrán la apariencia grafica
        de la aplicación, declaraciones y acomodos en la pantalla */
        java.awt.GridBagConstraints gridBagConstraints;
        lblNombreRepartidor = new javax.swing.JLabel();
        lblTiempoEntrega = new javax.swing.JLabel(); 
        txtNombreRepartidor = new javax.swing.JTextField();   
        txtTiempoEntrega= new javax.swing.JTextField();      
        txtAreaEstatus = new javax.swing.JTextArea();
        ScrollEstatus = new javax.swing.JScrollPane();   
        btnAsignarPedido = new javax.swing.JButton();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
                cerrarSocket();
            }
        }); 
        
        setTitle("La Hamburguesa (Servidor)");
        lblNombreRepartidor.setText("Nombre Repartidor:");
        lblTiempoEntrega.setText("Tiempo de Entrega:");
        txtNombreRepartidor.setEditable(false);  
        txtTiempoEntrega.setEditable(false);  
        txtAreaEstatus.setEditable(false);        
        btnAsignarPedido.setEnabled(false);
        btnAsignarPedido.setText("Asignar Pedido");
        btnAsignarPedido.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                asignarPedido();     
            }
        });
        
        getContentPane().setLayout(new java.awt.GridBagLayout());
        setBounds(250, 160, 500, 320);
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblNombreRepartidor, gridBagConstraints);

        lblTiempoEntrega.setPreferredSize(new java.awt.Dimension(80, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(lblTiempoEntrega, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        getContentPane().add(txtNombreRepartidor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        getContentPane().add(txtTiempoEntrega, gridBagConstraints);

        txtAreaEstatus.setColumns(20);
        txtAreaEstatus.setRows(5);
        ScrollEstatus.setViewportView(txtAreaEstatus);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(ScrollEstatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnAsignarPedido, gridBagConstraints);
        
    }
    
   
    
    
    /* A continuación se presenta el método de abrirSocket */
    public void abrirSocket(){
       String msg = null;
       int puerto = 1025;
       try {
           //Se asigna ese Puerto al socket creado
           serverSocket = new ServerSocket(puerto);

           //Notificamos que estamos a la espera de una solicitud de parte del cliente.
           msg ="Esperando nueva solicitud...";
           txtAreaEstatus.setText(msg);

           //Esperamos a que un cliente se conecte.
           cliente = serverSocket.accept();

           //Obtenemos los objetos para poder leer y escribir a través
           //del socket
           entrada = cliente.getInputStream();
           salida = cliente.getOutputStream();
           lector = new DataInputStream(entrada);
           escritor = new DataOutputStream(salida);
           leerDatosDelCliente();
       }
       //Si el usuario captura un número de puerto incorrecto se captura el error y se le notifica al usuario.
       catch (NumberFormatException nfe){
           //Notificamos error de puerto
           JOptionPane.showMessageDialog(this,"Numero de puerto incorrecto","Error",JOptionPane.ERROR_MESSAGE);
       }
       //Si el usuario intenta acceder a un numero de puerto no disponible se captura el error y se le notifica al usuario.
       catch (Exception e){
           //Notificamos cualquier otro error
           JOptionPane.showMessageDialog(this, "Error de conexión", "Error",
           JOptionPane.ERROR_MESSAGE);
       }
    }

    // Inicia el método de cerrarSocket
    public void cerrarSocket(){
        String msg = null;
        try{
            leyendo = false;
            // Se está en espera de recibir comunicación por parte del cliente.
            if (cliente != null){
                //Con esto se cierra la conexión con el cliente.
                cliente.close();
                //Con esto se cierra el socket servidor, ya no atendemos más conexiones.
                serverSocket.close();
                cliente = null;
                serverSocket = null;
                
            }
            
        }
        /* En el caso de que no se pueda cerrar el socket se captura la excepción y
        se cierra el sistema */
        catch (Exception e){
            //Si el usuario intenta cerrar un socket no disponible se captura el error y se le notifica al usuario.
             msg = "Error al intentar cerrar conexión";
             JOptionPane.showMessageDialog(this, msg, "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

     /* El siguiente método es para leer los datos que el cliente envía hacia el
     servidor */
    public void leerDatosDelCliente(){
        Thread t = null;
        /*Creamos un objeto para leer datos del cliente indefinidamente
        a través de un hilo. La interface Runnable solamente declara una función
        miembro denominada run, que han de definir las clases que implementen este interface
        */
        Runnable r = new Runnable(){
            /* Se declara entonces la función run, que implementa la interface
           Runnable*/
            @Override
            public void run(){
                if (leyendo)
                    return;
                leyendo = true;
                while (leyendo){
                    /*Mientras se está leyendo se toman los datos que se están
                   introduciendo, en la caja de texto txtaDatosEntrantes*/
                    try{
                        //Se asignan los datos leidos a sus respectivos componentes                        
                        txtAreaEstatus.setText("Nombre Cliente: "+lector.readUTF()+"\nDirección de entrega: "+ lector.readUTF()+ "\nTeléfono: "+lector.readUTF()+"\nPack "+lector.readUTF());
                        if(entrada != InputStream.nullInputStream()){
                            txtNombreRepartidor.setEditable(true);
                            txtTiempoEntrega.setEditable(true);
                            btnAsignarPedido.setEnabled(true);
                        }                      
                    } 
                    catch (Exception e){
                    }
                }
            }
        };
        t = new Thread(r);
        //Se inicializa el hilo de lectura
        t.start();
    }
    
    //Esta funcion se encarga asignar lso datos, enviarlos y volver a quedar a la espera de otra conexión
    public void asignarPedido() {        
        if("".equals(txtNombreRepartidor.getText()) || "".equals(txtTiempoEntrega.getText())){
            JOptionPane.showMessageDialog(this, "Alguno de los campos esta vacio", "Error",JOptionPane.ERROR_MESSAGE);
        }else{                    
            txtAreaEstatus.append("\nNombre repartidor: "+txtNombreRepartidor.getText());
            txtAreaEstatus.append("\nTiempo de entrega: "+txtTiempoEntrega.getText());            
            txtAreaEstatus.append("\n\nPedido en proceso... ");
            enviarDatos();
            txtNombreRepartidor.setText("");  
            txtTiempoEntrega.setText("");   
            txtNombreRepartidor.setEditable(false);  
            txtTiempoEntrega.setEditable(false);   
            btnAsignarPedido.setEnabled(false);
            try {
                cliente.close();
                //Esperamos a que un cliente se conecte.
                cliente = serverSocket.accept();
                entrada = cliente.getInputStream();
                salida = cliente.getOutputStream();
                lector = new DataInputStream(entrada);
                escritor = new DataOutputStream(salida);
                leerDatosDelCliente();
            } catch (IOException ex) {
                Logger.getLogger(HamburguesaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    //Función encargada de Enviar los datos al cliente
    public void enviarDatos(){        
        try{
            //Se obtienen los datos de los componentes y se asignan a una variable String            
            String datos = "\nen "+ txtTiempoEntrega.getText()+" minutos, su repartidor es "+ txtNombreRepartidor.getText() + "\ngracias por su preferencia.";
            //Se envian al cliente
            escritor.writeUTF(datos);
        }
        catch (Exception e){
        }
    }    
}

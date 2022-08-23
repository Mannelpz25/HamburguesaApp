package hamburguesa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public final class HamburguesaCliente extends javax.swing.JFrame {
    // Declaración de variables para apariencia
    javax.swing.JButton btnRealizarPedido;
    javax.swing.JButton btnPack1;
    javax.swing.JButton btnPack2;
    javax.swing.JButton btnPack3;
    javax.swing.JButton btnPack4;
    javax.swing.JLabel lblNombreCliente;
    javax.swing.JLabel lblDireccion;
    javax.swing.JLabel lblTelefono;
    javax.swing.JTextField txtNombreCliente;
    javax.swing.JTextField txtDireccion;
    javax.swing.JTextField txtTelefono;
    javax.swing.JTextArea txtAreaEstatus;
    javax.swing.JScrollPane ScrollEstatus;
    int pack=0;
    //Objeto que se comunicará con el servidor para enviarle datos.
    Socket cliente;
    //Objetos que permitirán leer y escribir flujos de bits través del socket.
    InputStream entrada;
    OutputStream salida; 
    //Objetos que permitirán leer valores de algún tipo desde el socket.
    DataInputStream lector;
    DataOutputStream escritor;
    //Esta variable sirve para mantener o cerrar el ciclo de lectura de datos
    //desde el cliente
    boolean leyendo;
    
    public HamburguesaCliente(){
        initComponents();
        abrirConexion();
    }
    
    private void initComponents() {
        /* En esta sección se tienen los elementos que compondrán la apariencia grafica
        de la aplicación, declaraciones y acomodos en la pantalla */
        java.awt.GridBagConstraints gridBagConstraints;
        lblNombreCliente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        ScrollEstatus = new javax.swing.JScrollPane();
        txtAreaEstatus = new javax.swing.JTextArea();
        btnRealizarPedido = new javax.swing.JButton();
        btnPack1 = new javax.swing.JButton();
        btnPack2 = new javax.swing.JButton();
        btnPack3 = new javax.swing.JButton();
        btnPack4 = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("La Hamburguesa (Cliente)");
        lblNombreCliente.setText("Nombre Cliente:");
        lblDireccion.setText("Dirección de entrega:");
        lblTelefono.setText("Teléfono:");
        txtAreaEstatus.setEditable(false);
        
        btnPack1.setText("Pack 1");
        btnPack1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pack=1;
            }
        });
        
        btnPack2.setText("Pack 2");
        btnPack2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pack=2;
            }
        });
        
        btnPack3.setText("Pack 3");
        btnPack3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pack=2;
            }
        });
        
        btnPack4.setText("Pack 4");
        btnPack4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pack=4;
            }
        });
        
        btnRealizarPedido.setText("Realizar Pedido");
        btnRealizarPedido.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarPedido();
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());
        setBounds(250, 160, 500, 320);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(lblNombreCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(lblDireccion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(lblTelefono, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        getContentPane().add(txtNombreCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        getContentPane().add(txtDireccion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        getContentPane().add(txtTelefono, gridBagConstraints);

        txtAreaEstatus.setColumns(20);
        txtAreaEstatus.setRows(5);
        ScrollEstatus.setViewportView(txtAreaEstatus);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(ScrollEstatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnPack1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnPack2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnPack3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnPack4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnRealizarPedido, gridBagConstraints);
        
    }
    
    //Con esta funcion se mandan a llamar las funciones para enviar los datos
    private void realizarPedido() {
        if(pack != 0){
            
            //Se llama la funcion para enviar datos
            enviarDatos(); 
            //Se llama la función para leer los datos del servidor
            leerDatos(); 
            
        }
        else{
            //Notificamos que nos se ha seleccionado ningun pack
            JOptionPane.showMessageDialog(this,"No se ha seleccionado el Pack a pedir","Error",JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
    
    
    /* A continuación se presenta el método de abrirConexionConElServidor para estar
    enviando los datos desde el cliente*/
    public void abrirConexion(){
        String ip = "127.0.0.1";
        int puerto = 1025;
        try{
            //Establecemos la conexión con el servidor
            cliente = new Socket(ip, puerto);
            //Obtenemos los objetos para poder leer y escribir a través
            //del socket
            entrada = cliente.getInputStream();
            salida = cliente.getOutputStream();
            lector = new DataInputStream(entrada);
            escritor = new DataOutputStream(salida);
        }
        catch (NumberFormatException nfe){
            //Notificamos error de puerto
            JOptionPane.showMessageDialog(this,"Numero de puerto incorrecto","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e){
            //Notificamos cualquier otro error
            JOptionPane.showMessageDialog(this, "Error de conexión", "Error",
            JOptionPane.ERROR_MESSAGE);
        }
    }

    // Se tiene el método para cerrar el socket previamente abierto.
    public void cerrarSocket(){
        String msg = null;
        try{
            leyendo = false;
            if (cliente != null){
                //Con esto se cierra la conexión con el servidor
                cliente.close();
                cliente = null;
            }
        }
        catch (Exception e){
            //Si el usuario intenta cerrar un socket no disponible se captura el error y se le notifica al usuario.
             msg = "Error al intentar cerrar conexión";
             JOptionPane.showMessageDialog(this, msg, "Error",
             JOptionPane.ERROR_MESSAGE);
        }
    }

    /*A continuación se presenta el método que envía datos, el cual primero los obtiene
    de txtDatos y después los envía mediante un DataOutputStream */
    public void enviarDatos(){    
        
        try{
            escritor.writeUTF(txtNombreCliente.getText());
            escritor.writeUTF(txtDireccion.getText());
            escritor.writeUTF(txtTelefono.getText());
            escritor.writeUTF(String.valueOf(pack));
            txtAreaEstatus.setText("Pedido enviado al restaurant, esperando respuesta...");
        }
        catch (Exception e){
        }
    }   
    public void leerDatos(){
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
                   introduciendo, en la caja de texto txtAreaRespuesta*/
                    try{
                        //Se escribe lo que se recibe del servidor
                        txtAreaEstatus.setText("Estimado cliente "+txtNombreCliente.getText()+"\nhemos recibido su pedido Pack "+String.valueOf(pack)+"\nlo estaremos enviando a la dirección "+txtDireccion.getText()+lector.readUTF()); 
                        
                        // Se resetean los componentes de texto
                        txtNombreCliente.setText("");
                        txtDireccion.setText("");
                        txtTelefono.setText("");  
                        // Cerramos la conexión
                        cerrarSocket(); 
                        // la volvemos abrir
                        abrirConexion();
                        
                        pack=0;
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
}

package pms;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class gui implements ActionListener{
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JButton labelButton;
    private JPanel controlPanel;
    private JPanel headerpanel;
    private JTextArea msglabel;
    private CarPark carpark;

    public gui(CarPark carpark){
        this.carpark = carpark;
        prepareGUI();
    }

    private void prepareGUI(){
        mainFrame = new JFrame("PMS - SpotSmart");              // Init new JFrame
        mainFrame.setSize(1000,600);
        mainFrame.setLayout(new GridLayout(3, 1));                        // JFrame is 3 rows 1 column

        mainFrame.addWindowListener(new WindowAdapter() {                 // Exit on clicking top right cross button
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        addVisStafParking();                                             // function for adding visitor and staff parking

        JPanel imagePanel = new JPanel(new GridLayout(2, 1));            // adding header panel containing image and text  with 2 rows 1 column
        JLabel textLabel = new JLabel("PMS - SpotSmart");      // Text label for headerpanel
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);         // Editing text properties
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Serif", Font.BOLD, 40));
        imagePanel.add(textLabel);
        JLabel picLabel = new JLabel();                                  // new label for icon in the header
        picLabel.setIcon(new ImageIcon(new ImageIcon("pms/car_icon.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        picLabel.setHorizontalAlignment(SwingConstants.CENTER);
        picLabel.setVerticalAlignment(SwingConstants.CENTER);
        imagePanel.add(picLabel);

        mainFrame.add(imagePanel);                                      //add everything in the image panel to the main JFrame


        headerpanel = new JPanel(new GridLayout(2, 1, 10, 5));          // middle Jpanel with grid layout

        headerpanel.setBackground(Color.WHITE);
        Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY); // add a border to it
        headerpanel.setBorder(blackline);
        mainFrame.add(headerpanel);                                      // add to main JFrame
        JButton button1 = new JButton("Show All Parking Spots");
        button1.setBackground(Color.LIGHT_GRAY);
        JButton button2 = new JButton("Find Car");                        // new JButtons for last panel
        button2.setBackground(Color.LIGHT_GRAY);
        JButton button3 = new JButton("Park Car");
        button3.setBackground(Color.LIGHT_GRAY);
        JButton button4 = new JButton("Delete Spot");
        button4.setBackground(Color.LIGHT_GRAY);
        JButton button5 = new JButton("Remove Car");
        button5.setBackground(Color.LIGHT_GRAY);
        JButton button6 = new JButton("Add Parking Spot");
        button6.setBackground(Color.LIGHT_GRAY);
        JButton button7 = new JButton("Exit Application");
        JButton button8 = new JButton("Clear Screen");
        button7.setBackground(Color.LIGHT_GRAY);
        button8.setBackground(Color.LIGHT_GRAY);
        JPanel main = new JPanel(new GridLayout(4,2));                     // 4 rows, 2 columns in last JPanel

        button1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                headerpanel.removeAll();
                showAllParkings();
            }
        });
        button2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                findCar();
            }
        });

        button3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parkCar();
            }
        });

        button4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                headerpanel.removeAll();
                deleteParking();
            }
        });

        button5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                removeVehicle();
            }
        });

        button6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addParkingSpot();
            }
        });


        button7.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        button8.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                showHomeScreen();
            }
        });

        main.add(button1);
        main.add(button2);
        main.add(button3);                             // add all buttons to last JPanel
        main.add(button4);
        main.add(button5);
        main.add(button6);
        main.add(button7);
        main.add(button8);

        mainFrame.add(main);                           // add everything to JFrame

        mainFrame.setVisible(true);
    }

    private void showHomeScreen(){
        headerpanel.removeAll();
        headerLabel= new JLabel("Welcome to [insert car park here]");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setVerticalAlignment(SwingConstants.CENTER);
        headerpanel.add(headerLabel);
        headerpanel.revalidate();
        headerpanel.repaint();   // This is required if changing components
        mainFrame.setVisible(true);
    }

    public void addVisStafParking(){
        JOptionPane jO = new JOptionPane();
        boolean query = false;
        while (!query){
            String vis = jO.showInputDialog("Enter Number of Visitor Parking");
            String stf = jO.showInputDialog("Enter Number of Staff Parking");
            if (vis!=null && stf!=null){

                if(!vis.chars().allMatch( Character::isDigit ) || !stf.chars().allMatch( Character::isDigit ))
                {
                    jO.showMessageDialog(null, "Please enter a numeric value eg. (1 or 5 )");

                }
                else if(stf.equals("") || vis.equals(""))
                {
                    jO.showMessageDialog(null, "Please don't leave input blank. Enter a numeric value eg. (1 or 5 )");

                }
                else
                {
                    int vid = Integer.parseInt(vis);
                    int nid = Integer.parseInt(stf);

                    int p = 0;
                    int z = 0;

                    while (p<vid)
                    {
                        p++;                                                                       //increment i
                        String uniqueNum = String.format("%03d", p);                               // 3 digit sequential id "001", "002" etc
                        ParkingSlot aParkingSlot = new ParkingSlot("V" + uniqueNum);               // V + 001, V+ 002 and so on
                        carpark.addNewParkingSlot(aParkingSlot);
                    }
                    while (z<nid)
                    {
                        z++;                                                                       //increment i
                        String uniqueNum = String.format("%03d", z);                               // 3 digit sequential id "001", "002" etc
                        ParkingSlot aParkingSlot = new ParkingSlot("S" + uniqueNum, true);               // V + 001, V+ 002 and so on
                        carpark.addNewParkingSlot(aParkingSlot);
                    }
                    JOptionPane.showMessageDialog(null, p + " visitor parking spots created and " + z + " staff parking spots created!");
                    query = true;
                }
            }
        }
    }

    public void deleteParking(){

        showAllParkings();
        String spot_ID = JOptionPane.showInputDialog("Select the spot you would like to delete eg. (V001)");
        if (spot_ID!=null)
        {
            if (spot_ID.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Alert: Please don't enter a blank value");
            }
            else
            {
                if(carpark.removeAParkingSlot(spot_ID) )
                {
                    JOptionPane.showMessageDialog(null, "Success: spot deleted!","Spot Deleted Message", JOptionPane.INFORMATION_MESSAGE);
                    headerpanel.removeAll();
                    showAllParkings();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Alert: Spot doesn't exist or is currently occupied");
                }
            }
        }
    }

    public void addParkingSpot(){

        String spot_ID = JOptionPane.showInputDialog("Add Spot ID eg. (S110)");
        boolean isParkingSlotDuplicate = false;
        if (spot_ID!=null)
        {
            if (spot_ID.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Error: do not leave the id blank!");
            }
            else
            {
                for(ParkingSlot aParkingSlot : carpark.getAllParkingSlots())
                {
                    /* checking if id already exists */
                    if(aParkingSlot.getParkingSlotId().equalsIgnoreCase(spot_ID))
                    {
                        isParkingSlotDuplicate = true;
                        JOptionPane.showMessageDialog(null, "Alert: Spot "+spot_ID+" already exists!. ");
                    }
                }

                if(!isParkingSlotDuplicate)
                {
                    String spotstfvis = JOptionPane.showInputDialog("Is this a staff or visitor parking?").toUpperCase();
                    if(spotstfvis.contains("STAFF"))
                    {
                        /* checking if id starts with S, total length is 4 and contains numerics after S */
                        if (spot_ID.startsWith("S") && spot_ID.length()==4 && spot_ID.substring(1).chars().allMatch( Character::isDigit ))
                        {
                            JOptionPane.showMessageDialog(null, "Success! Staff Parking Added.");
                            ParkingSlot aParkingSlot = new ParkingSlot(spot_ID, true);         // new parking slot with isStaff as true
                            carpark.addNewParkingSlot(aParkingSlot);
                            headerpanel.removeAll();
                            showAllParkings();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Staff Parking must start with S and be 4 digits eg. (S110). Try again.");
                        }
                    }
                    else if(spotstfvis.contains("VISITOR"))
                    {
                        /* checking if id starts with V, total length is 4 and contains numerics after V */
                        if (spot_ID.startsWith("V")  && spot_ID.length()==4 && spot_ID.substring(1).chars().allMatch( Character::isDigit ))
                        {
                            JOptionPane.showMessageDialog(null, "Success! Visitor Parking Added");
                            ParkingSlot aParkingSlot = new ParkingSlot(spot_ID);       // new parking spot by default isStaff is false
                            carpark.addNewParkingSlot(aParkingSlot);
                            headerpanel.removeAll();
                            showAllParkings();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Visitor Parking must start with V should be 4 digits eg. (V999). Try again");
                        }
                    }
                    /* if user doesn't choose staff or visitor. Display an error */
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Please choose from \"staff\" or \"visitor\"");
                    }
                }
            }
        }
    }

    public void removeVehicle(){
        boolean userQuery = false;
        String regNum = JOptionPane.showInputDialog("Enter Registration Number of the Car eg. (A12345)");
        if(regNum!=null)
        {
            for(ParkingSlot aParkingSlot : carpark.getAllParkingSlots())
            {
                /*  checking if parking is occupied and vehicle is an instance of Car */
                if(aParkingSlot.getIsOccupied() && aParkingSlot.getVehicle() instanceof Car )
                {
                    /*  checking if registration matches. */
                    if(((Car)aParkingSlot.getVehicle()).getregNum().equals(regNum))
                    {
                        aParkingSlot.removeVehicle();
                        userQuery = true;
                        JOptionPane.showMessageDialog(null, "Success: Car Removed");
                        headerpanel.removeAll();
                        showAllParkings();
                    }
                }
            }
        }
        /*  if user query isn't true, display an error */
        if(!userQuery)
        {
            JOptionPane.showMessageDialog(null, "Alert: No Car Found");
        }

    }

    public void actionPerformed(ActionEvent e)
    {

        String spot_id = e.getActionCommand().substring(6, 10);
        final JPopupMenu menu = new JPopupMenu("Menu");
        menu.add("Add Parking Spot").addActionListener(ee-> {
            addParkingSpot();                                 // run addParkingSpot function
        });
        menu.add("Delete Spot "+spot_id ).addActionListener(ee-> {
            removeParkingSpotSubMenu(spot_id);                // run removeParkingSpotSubMenu function taking spot_id as parameter
        });
        if(carpark.findParkingbyID(spot_id).getIsOccupied()){

            menu.add("Remove Vehicle" ).addActionListener(ee-> {
                removeCarSubMenu(spot_id);                     // run removeCarSubMenu function taking spot_id
                //  as parameter if spot is occupied
            });

        }

        if(!carpark.findParkingbyID(spot_id).getIsOccupied()){

            menu.add("Park Vehicle" ).addActionListener(ee-> {
                parkCarSubMenu(spot_id);                             // run parkCarSubMenu function taking spot_id
            });                                                     //   as parameter if spot is vacant

        }
        menu.show(labelButton, labelButton.getWidth(), labelButton.getHeight());   // display menu on parking slot button click
    }

    public void parkCarSubMenu(String spot_id)
    {
        String reg = JOptionPane.showInputDialog(null, "Enter Registration Number eg. (A12345)", "Enter Registration Window", JOptionPane.INFORMATION_MESSAGE);
        if (reg !=null)
        {
            if (carpark.findVehicleRegNum(reg).isEmpty())
            {
                if (Character.isUpperCase(reg.charAt(0)) && reg.length()==6 && reg.substring(1).chars().allMatch( Character::isDigit )){
                    String owner = JOptionPane.showInputDialog(null, "Enter Owner Name", "Enter Owner Window", JOptionPane.INFORMATION_MESSAGE);
                    if(owner !=null)
                    {
                        Car car = new Car(reg , owner);                          // init new car
                        Vehicle vehicle = car;
                        int reply = JOptionPane.showConfirmDialog(null, "Is this a Staff Parking", "Staff Confirmation Window", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION)
                        {
                            if(carpark.findParkingbyID(spot_id).getIsStaff())
                            {
                                carpark.findParkingbyID(spot_id).addStaffVehicle(vehicle);
                                JOptionPane.showMessageDialog(null, "Success Staff Car is Parked");
                                headerpanel.removeAll();
                                showAllParkings();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "This Spot " + spot_id +" Isn't a Staff Spot", "Not a Staff Spot", JOptionPane.ERROR_MESSAGE);

                            }
                        }
                        else
                        {
                            if(!carpark.findParkingbyID(spot_id).getIsStaff())
                            {
                                carpark.findParkingbyID(spot_id).addVisitorVehicle(vehicle);
                                JOptionPane.showMessageDialog(null, "Success Visitor Car is Parked", "Car Parked", JOptionPane.INFORMATION_MESSAGE);
                                headerpanel.removeAll();
                                showAllParkings();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "This Spot " + spot_id +" Isn't a Visitor Spot", "Not a Visitor Spot", JOptionPane.ERROR_MESSAGE);

                            }

                        }
                    }
                }
                else
                {

                    JOptionPane.showMessageDialog(null, "Enter registration with proper format eg. (A12345)", "Wrong Registration Format", JOptionPane.ERROR_MESSAGE);

                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Registration Already Exists In Car Park", "Duplicate Registration", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    public void removeParkingSpotSubMenu(String spot_id){
        if(carpark.removeAParkingSlot(spot_id))
        {
            JOptionPane.showMessageDialog(null, "Success: Parking Spot Deleted");
            headerpanel.removeAll();
            showAllParkings();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Alert: Parking Spot is Occupied. Try again.");

        }
    }

    public void removeCarSubMenu(String id){
        carpark.findParkingbyID(id).removeVehicle();
        JOptionPane.showMessageDialog(null, "Success: Vehicle Removed");
        headerpanel.removeAll();
        showAllParkings();
    }



    public void showAllParkings(){
        String value = String.format("%-20s","ID");
        for(ParkingSlot aParkingSlot: carpark.getAllParkingSlots())
        {
            if (aParkingSlot.getVehicle() != null && aParkingSlot.getVehicle() instanceof Car && aParkingSlot.getIsOccupied())
            {
                labelButton = new JButton();
                labelButton.setText("<html>"+aParkingSlot.getParkingSlotId()+"<br/>"+ ((aParkingSlot.getIsStaff())?"Staff":"Visitor")+"<br/>"+((aParkingSlot.getIsOccupied())?"Occupied":"Vacant")+"<br/>"+aParkingSlot.getVehicle().getOwner()+"<br/>"+((Car)aParkingSlot.getVehicle()).getregNum()+"</html>");
                labelButton.setBackground(Color.RED);
                headerpanel.add(labelButton);


                labelButton.addActionListener(this);         // using this class as a listener for displayed buttons

            }
            else
            {
                labelButton = new JButton();
                labelButton.setText("<html>"+aParkingSlot.getParkingSlotId()+"<br/>"+ ((aParkingSlot.getIsStaff())?"Staff":"Visitor")+"<br/>"+((aParkingSlot.getIsOccupied())?"Occupied":"Vacant")+"</html>");
                headerpanel.add(labelButton);
                labelButton.addActionListener(this);

                //value += String.format("%-20s",aParkingSlot.getParkingSlotId());
                //value += String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aParkingSlot.getParkingSlotId(), ((aParkingSlot.getIsStaff())?"Staff":"Visitor") , ((aParkingSlot.getIsOccupied())?"Occupied":"Vacant"),"---","---","<br>");
            }
        }
        headerpanel.revalidate();
        headerpanel.repaint();
        mainFrame.setVisible(true);
        //headerLabel.setText(String.format("<html><body style=\"text-align: justify;  text-justify: inter-word;\">%s</body></html>",value));
        //headerLabel.setText("<html>" +value +"</html>");
    }

    public void findCar(){
        headerpanel.removeAll();
        headerLabel= new JLabel("");
        String value ="";
        boolean query = false;
        String regNum = JOptionPane.showInputDialog("The Register Number of the Car eg. (P12345)");
        if(regNum!=null){
            value ="Your Parking details are as following: <br><p style='font-size:11px;'>"+"ID " + " Status " + " Vacancy " + " Registration "+ " Owner " + " Time In "+"</p>"+ "<br>";
            for(ParkingSlot aParkingSlot : carpark.getAllParkingSlots())
            {
                if(aParkingSlot.getIsOccupied() && aParkingSlot.getVehicle() instanceof Car )
                {
                    if(((Car)aParkingSlot.getVehicle()).getregNum().equals(regNum))
                    {
                        value += " " +aParkingSlot.getParkingSlotId() +" "+((aParkingSlot.getIsStaff())?"Staff Parking":"Visitor Parking")+ " "+ ((aParkingSlot.getIsOccupied())?"Occupied":"Vacant")+" "+((Car)aParkingSlot.getVehicle()).getregNum()+" "+ aParkingSlot.getVehicle().getOwner()+ " "+ aParkingSlot.getVehicle().getTimeIn()+". min"+"<br>";
                        query = true;
                    }
                }
            }
        }
        if (!query)
        {
            value = "<p style='font-size:11px;'>Welcome to SpotSmart</p>";
            JOptionPane.showMessageDialog(null, "No Car Found");
        }
        headerLabel.setText("<html><div style='text-align: center;'>" +value +"</div></html>");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setVerticalAlignment(SwingConstants.CENTER);
        headerpanel.add(headerLabel);
        headerpanel.revalidate();
        headerpanel.repaint();
    }

    public void parkCar(){
        String regNum = JOptionPane.showInputDialog("The Register Number of the Car eg (A12345)");
        String value = "";
        if(regNum !=null){
            if (regNum.length()==6 && !carpark.findVehicleRegNum(regNum).contains(regNum) && Character.isUpperCase(regNum.charAt(0)))
            {
                String owner = JOptionPane.showInputDialog("Enter Name of the owner");
                Car car = new Car(regNum, owner);                          // init new car
                Vehicle vehicle = car;
                if(carpark.getAvailableParkingSlots().isEmpty()){
                    headerLabel.setText("No Car Park Available");
                }
                else if(!carpark.findVehicleRegNum(regNum).isEmpty()){
                    JOptionPane.showMessageDialog(headerLabel, "Registration exists in car park. Please select another Registration", "Duplicate Vehicle", JOptionPane.WARNING_MESSAGE);
                    headerLabel.setText("<html>Alert: Vehicle Registration Already exists! Can't Park more than one Vehicle.</html>");
                }
                else
                {
                    //value = "Please select a Parking Spot from below: " + "<br>";
                    headerpanel.removeAll();
                    showAllParkings();
                    String parkingSpot = JOptionPane.showInputDialog("Enter Spot You Want to Park eg. (S001)");
                    String staffMember = JOptionPane.showInputDialog("Are you a staff member or visitor?");
                    if (parkingSpot !=null && staffMember !=null){
                        if(carpark.findParkingbyID(parkingSpot).getParkingSlotId().equals(parkingSpot) && !carpark.findParkingbyID(parkingSpot).getIsOccupied() )
                        {
                            switch(staffMember)
                            {
                                case "staff":
                                    if(carpark.findParkingbyID(parkingSpot).getIsStaff()){
                                        carpark.findParkingbyID(parkingSpot).addStaffVehicle(vehicle);
                                        JOptionPane.showMessageDialog(null, "Success Staff Car is Parked");
                                        headerpanel.removeAll();
                                        showAllParkings();
                                        break;
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "The spot you have chosen is not a staff parking spot");
                                        break;
                                    }

                                case "visitor":
                                    if(!carpark.findParkingbyID(parkingSpot).getIsStaff()){
                                        carpark.findParkingbyID(parkingSpot).addVisitorVehicle(vehicle);
                                        JOptionPane.showMessageDialog(null, "Success Visitor Car is Parked");
                                        headerpanel.removeAll();
                                        showAllParkings();
                                        break;
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "The spot you have chosen is not a visitor parking spot");
                                        break;
                                    }

                                default:
                                    JOptionPane.showMessageDialog(null,"Please enter \"staff\" or \"visitor\" ");

                            }

                        }
                        else
                        {
                            headerLabel.setText("<html><div style='text-align: center;'> Spot is occupied or no parking exists</div></html>");
                            JOptionPane.showMessageDialog(headerLabel, "Spot is occupied or doesn't exist", "Parking Spot Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(headerLabel, "Make Sure Registration follows proper format eg.(A12345).", "Registration input incorrect", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args)
    {
        CarPark carpark = new CarPark();
        gui  newGUI = new gui(carpark);
        newGUI.showHomeScreen();
    }
}

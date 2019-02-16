package diffeqs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import function.Vector;

/**
 * The ProjectDisplay class creates an interface where the user can input parameters that define 
 * the projectile and the environment surrounding it. The user can run the simulation from the 
 * interface without having to recompile the program.
 * 
 * @author Matthew Jin
 * @version 12/10/17
 */
public class ProjectileDisplay extends JComponent
{

    private static final int NUMPARAMS = 14;

    private JFrame frame;
    private JPanel inputPanel, buttonPanel;
    JFormattedTextField[] fields;
    private JButton button;

    private ProjectileMotion proj;

    /**
     * Constructor for the ProjectileDisplay class. It creates the JFrame and the components that 
     * serve as input boxes for the ProjectileMotion parameters. A button is also added below the 
     * input boxes, and it will update the parameters in the ProjectileMotion class with the ones 
     * in the text fields. Then, it will run the simulation again with those new parameters. 
     * The output file for the previous simulation will be overidden, so make sure to save the 
     * results before starting a new simulation. 
     * 
     * @param proj the ProjectileMotion object that will calculate the motion of the projectile
     */
    public ProjectileDisplay(ProjectileMotion proj)
    {
        this.proj = proj;
        
        frame = new JFrame("Parameters");
        inputPanel = new JPanel(new GridLayout(8, 4));
        buttonPanel = new JPanel();
        fields = new JFormattedTextField[NUMPARAMS];
        button = new JButton("Run Simulation");
        JLabel[] labels = {
                new JLabel("Model:", SwingConstants.CENTER),
                new JLabel("∆t (s):", SwingConstants.CENTER),
                new JLabel("Radius (m):", SwingConstants.CENTER),
                new JLabel("Mass (kg):", SwingConstants.CENTER),
                new JLabel("Velocity (m/s):", SwingConstants.CENTER),
                new JLabel("θ (º):", SwingConstants.CENTER),
                new JLabel("X-position (m):", SwingConstants.CENTER),
                new JLabel("Y-position (m):", SwingConstants.CENTER),

                new JLabel("g (m/s^2):", SwingConstants.CENTER),
                new JLabel("T0 (K):", SwingConstants.CENTER),
                new JLabel("a (K/m):", SwingConstants.CENTER),
                new JLabel("α:", SwingConstants.CENTER),
                new JLabel("ρ0 (kg/m^3):", SwingConstants.CENTER),
                new JLabel("y0 (m):", SwingConstants.CENTER),
        };

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runSimulation();
            }
        });

        for(int i=0; i<NUMPARAMS; i++) {
            NumberFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(1000);
            format.setGroupingUsed(false);
            fields[i] = new JFormattedTextField(format);
            fields[i].setValue(new Double(0.0));
            inputPanel.add(labels[i]);
            inputPanel.add(fields[i]);
        }
        buttonPanel.add(button);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(600, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * This method will read in the parameters from the input fields and update them in the 
     * ProjectileMotion class. Then it will rerun the simulation and print the results into an 
     * output file.
     */
    public void runSimulation()
    {
        System.out.println("Running simulation...");
        
        double model = Double.parseDouble(fields[0].getText()); //No units
        double dt = Double.parseDouble(fields[1].getText()); //seconds (s)
        double R = Double.parseDouble(fields[2].getText()); //meters (m)
        double m = Double.parseDouble(fields[3].getText()); //kilograms (kg)
        double v = Double.parseDouble(fields[4].getText()); //meters/second (m/s)
        double theta = Double.parseDouble(fields[5].getText()); //degrees (º)
        double x = Double.parseDouble(fields[6].getText()); //meters (m)
        double y = Double.parseDouble(fields[7].getText()); //meters (m)

        proj.setG(Double.parseDouble(fields[8].getText())); //meters/second^2 (m/s^2)
        proj.setInitT(Double.parseDouble(fields[9].getText())); //Kelvin (K) 
        proj.setA(Double.parseDouble(fields[10].getText())); //Kelvin/meter (K/m)
        proj.setAlpha(Double.parseDouble(fields[11].getText())); //No units
        proj.setInitRho(Double.parseDouble(fields[12].getText())); //kilogram/meter^3 (kg/m^3)
        proj.setInitY(Double.parseDouble(fields[13].getText())); //meters (m)

        TreeMap<Double, Vector> map= proj.move(model, dt, R, m, v, theta, x, y);
       
        try
        {
            proj.printOutput(map);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        System.out.println("Simulation complete.");
    }
    
}

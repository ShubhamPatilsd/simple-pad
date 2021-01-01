import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand() == "Save"){
            SimplePad.save();
        }else if(e.getActionCommand()=="Save As"){
            try {
                SimplePad.saveAs();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

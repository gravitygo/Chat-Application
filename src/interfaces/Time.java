/**======================================
 *______  __    __       ___   .___________.    ___      .______   .______    __       __    ______     ___   .___________. __    ______   .__   __. 
 /      ||  |  |  |     /   \  |           |   /   \     |   _  \  |   _  \  |  |     |  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  | 
|  ,----'|  |__|  |    /  ^  \ `---|  |----`  /  ^  \    |  |_)  | |  |_)  | |  |     |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  | 
|  |     |   __   |   /  /_\  \    |  |      /  /_\  \   |   ___/  |   ___/  |  |     |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  | 
|  `----.|  |  |  |  /  _____  \   |  |     /  _____  \  |  |      |  |      |  `----.|  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   | 
 \______||__|  |__| /__/     \__\  |__|    /__/     \__\ | _|      | _|      |_______||__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__| 
                                                                                                                                                     
 * Made by: Chyle Andrei Lee &
 * 			Clyde Adrian Ramos
 * ======================================
 */
package interfaces;
//import packages
import java.util.TimerTask;
import javax.swing.JTable;
//To update chat
public class Time extends TimerTask{
//declaration of variables
	int i=0;

	public void run() {
		Chat.updateChat();
		Chat.chatTable = new JTable(Chat.tableModel);
	}

}

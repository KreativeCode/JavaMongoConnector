import java.util.Scanner;


public class ZipcodeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZipcodeDatabaseViewer dbView = new ZipcodeDatabaseViewer();
		
		Scanner console = new Scanner(System.in);
		while(console.hasNext()){
			switch(console.nextLine()){
			case "query":
				dbView.query();
				break;
			case "delete":
				dbView.delete(console);
				break;
			case "insert":
				dbView.insert(console);
				break;
			case "update":
				dbView.update();
				break;
			case "view all collections":
				dbView.viewAllCollections();
				break;
			case "create collection":
				dbView.createCollection(console);
				break;
			case "delete collection":
				dbView.deleteCollection();
				break;
			case "change collection":
				dbView.changeCollection(console);
				break;
			case "get count":
				dbView.getCount();
				break;
			case "close":
				dbView.close();
				console.close();
				break;
			default :
				System.out.println("ERROR: not a valid command");
				break;
			}
		}
	}

}

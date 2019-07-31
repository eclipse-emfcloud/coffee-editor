package SuperBrewer3000.tests;
import SuperBrewer3000.Brew;
public class Dummy {

    public static void main(String[] args){
        Brew b = new Brew();
        try {
			b.doExecute();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
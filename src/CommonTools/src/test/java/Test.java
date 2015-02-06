import com.dbs.util.CipherHelper;


public class Test {
	public static void main(String[] args){
		new Test();
	}
	
	public Test(){
		CipherHelper helper = new CipherHelper();
		String encrypt = helper.encrypt("smw2013");
		System.out.println(encrypt);
		
		System.out.println(helper.decrypt(encrypt));
	}
}

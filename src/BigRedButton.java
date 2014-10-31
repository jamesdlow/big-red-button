import com.jameslow.*;

public class BigRedButton extends Main {
	public BigRedButton(String args[]) {
		super(args,null,null,null,BigRedButtonWindow.class.getName(),null,null,null);
		//super(args,null,null,TemplateSettings.class.getName(),TemplateWindow.class.getName(),null,null,TemplatePref.class.getName());
	}
	public static void main(String args[]) {
		instance = new BigRedButton(args);
	}
}
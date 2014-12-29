package tools;

import java.security.MessageDigest;
import java.text.BreakIterator;
import java.util.List;

import models.BlogPost;

public class StringUtil {
    public static String encrypt(String algo, String secret, String seed) {

        String ret = "";
        String se = secret + seed;

        byte[] defaultBytes = se.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(algo);
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            ret = hexString + "";
        } catch (Exception e) {

        }
        return ret;
    }
    
    
	public static class TextParts {
		public String one;
		public String two;
		
		public TextParts(String s1, String s2) {
			this.one = s1;
			this.two = s2;
		}
	}
    public static TextParts getParts(String s, int nbChars) {
    	BreakIterator bi = BreakIterator.getWordInstance();
    	
    	// dirty hack to avoid cutting funky characters, such as &quot; 
    	String text =  s.replaceAll("[^A-Za-z ]", "x");
    	
    	bi.setText(text);
    	int first_after = bi.following(nbChars);
    	
    	String s1 = s.substring(0, first_after);
    	String s2 = s.substring(first_after, s.length());
    	return new TextParts(s1,s2);
    }
    public static String frenchDate(int pubDate) {
    	String date = pubDate + "";
    	int month = Integer.parseInt(date.substring(4,6));
    	return date.substring(6,8) + " " + getFrenchMonth(month) + " " + date.substring(0,4) ;
    }
    private static String getFrenchMonth(int month) {
    	if (month==1) { return "janvier";}
    	if (month==2) { return "février";}
    	if (month==3) { return "mars";}
    	if (month==4) { return "avril";}
    	if (month==5) { return "mai";}
    	if (month==6) { return "juin";}
    	if (month==7) { return "juillet";}
    	if (month==8) { return "août";}
    	if (month==9) { return "septembre";}
    	if (month==10) { return "octobre";}
    	if (month==11) { return "novembre";}
    	return "décembre";
    }
    
    public static String postDate(int pubDate) {
    	String date = pubDate + "";
    	return date.substring(6,8) + " " + date.substring(4,6) + " " + date.substring(0,4) ;
    }
    
    public static String cleanHtml(String s) {
    	String ret = s.replaceAll("<p>", "");
    	ret = ret.replaceAll("</p>", "<br><br>");
    	
    	return ret;
    }
}

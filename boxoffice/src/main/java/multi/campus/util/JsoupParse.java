package multi.campus.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupParse {
	static void getImageUrl() throws Exception {
		try {
            // 1. URL 선언
            String connUrl = "https://search.naver.com/search.naver?where=nexearch&query=" + "박스오피스순위";
            Document doc = Jsoup.connect(connUrl).get();
            
            Element element = doc.select("div._content").first();
            // ul > li > div.thumb > a > img#src
            Elements list = element.select("ul > li > div.thumb");
            System.out.println("-----------------------");
            
            StringBuilder sb = new StringBuilder();
            
			for (Element imgUrl : list) {
				imgUrl.attr("onclick", "openFunction('"+element.attr("href")+"')");
				imgUrl.attr("href", "#");
				//imgUrl.select("a > img").attr("src");
				System.out.println(imgUrl.select("a > img").attr("src"));
			}
			
			System.out.println(sb.toString());
            System.out.println("-----------------------");
            
        } catch (Exception e) {
            // Exp : Connection Fail
            e.printStackTrace();
        } 
    }
}
package ScrapeAmazonCsv;

import Tools.ElapsedTimer;
import DownloadImage.DownloadImage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Idris on 09/07/2017.
 */
public class GetAmazonData {

    public String postTitle, by, url, postContent, description, tech, featuredImage, postCategory, customerReviews, review, html, price, test, longest, imageString, byString, byTest, imageTest;
    public String[] extraImages  = new String[5];
    public String[] fImages = new String[5];
    public boolean skip, proxyBan, noImg = false;
    public static String[] userAgent = new String[34];
    public ArrayList<String> savedTitles = new ArrayList<>();
    public boolean noHttp = false;

    Document document;
    public DownloadImage downloadImage = new DownloadImage("Z:\\Coding\\My Projects\\AmazonScraper\\scraped\\exportedImages3");


    String desc2, getBy, desc6;

    public static int count2 = 0;


    public static void main(String[]args) throws Exception {
        GetAmazonData getAmazonData = new GetAmazonData();

        boolean running = true;
        while (running) {
            getAmazonData.get("https://www.amazon.com/002R3X-PS4110XV-Compatible-Product-NETCNA/dp/B07371NFGN/");



            System.out.println(ElapsedTimer.getElapsedTime());



        }
    }

    public void get(String getLinks) throws Exception{

        getUserAgents();
        resetStrings();



       try{

           System.setProperty("https.proxyHost", ExportCSV.proxy); //proxy[count]
           System.setProperty("https.proxyPort", ExportCSV.port); //port



           document = Jsoup.connect(getLinks).userAgent(userAgent[count2]).timeout(30000).followRedirects(true).get(); //get websitelink
           String text = document.body().text();
           //System.out.print(document.text());

           checkCaptcha();
           //postTitle = document.select("#productTitle").text();

           if(fImages[0].contains("captcha")){
               proxyBan();
           }else if(document.body().html().contains("aloha-return-policy") || document.body().html().contains("width: 316px;" ) || savedTitles.contains(postTitle)){
               skipProduct();
           }else {

               proxyBan = false;
               html = document.html();
               skip = false;
               noImg = false;
               noHttp = false;

               getImages();

               url = document.location();
               getTitle();
               getBy();
               getDescriptions();
               featuredText();
               getPrice();
               getReviews();
               getCategories();

               getImageString();
               getProductImages();
               getExtraImages();
               getFeaturedImages();
               removeFromImage();
               removeUselessImages();

           }

           removeDuplicateDescription();

           increaseCount();



       }catch (Exception e){



           if (e.getMessage().contains("error fetching URL")) {
                noHttp = true;
               System.out.println("Not a valid site");
           }

           if (e.getMessage().contains("Connection reset")) {
               System.out.println("Connection Error");
           }


           System.out.println("Error code : " + e.getMessage());
           skip = true;
       }

    }

    public void resetStrings(){
        postTitle = "";
        by = "";
        postContent = "";
        description = "";
        tech = "";
        featuredImage = "";
        postCategory = "";
        customerReviews = "";
        review = "";
        html = "";
        price = "";

        desc2 = "";
        getBy = "";
        desc6 = "";
    }

    public void checkCaptcha(){
        longest = "";

        Element fimageE2 = document.select(longest + " img[src]").get(0);
        String fimage2 = fimageE2.attr("src") ;
        fImages[0] = fimage2;
        //System.out.println("testing 1 = " + fImages[0]);

        if(fimage2.length() == 0){
            fImages[0] = "";
        }
    }

    public void proxyBan(){

            proxyBan = true;
            skip = true;
            //System.out.println("ip Address = " + proxy);
            System.out.println("This Proxy is Banned");


    }

    public void skipProduct() {

            //System.out.println("It contains return policy");
            System.out.println("Skipping Product...." );
            skip = true;

            String getTitle = document.select("#productTitle" ).html();
            System.out.println("Title - " + getTitle);
            postTitle = getTitle;

    }

    public void getUserAgents(){
        userAgent[0] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0";
        userAgent[1] = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
        userAgent[2] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
        userAgent[3] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
        userAgent[4] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
        userAgent[5] = "Mozilla/5.0 (Windows NT 6.4; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36";
        userAgent[6] = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36";
        userAgent[7] = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:24.0) Gecko/20100101 Firefox/24.0";
        userAgent[8] = "Mozilla/5.0 (Windows NT 6.0; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0";
        userAgent[9] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:24.0) Gecko/20100101 Firefox/24.0";
        userAgent[10] = "Mozilla/5.0 (compatible; MSIE 10.0; Macintosh; Intel Mac OS X 10_7_3; Trident/6.0)";
        userAgent[11] = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
        userAgent[12] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0) chromeframe/10.0.648.205";
        userAgent[13] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; FunWebProducts)";
        userAgent[14] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0";
        userAgent[15] = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
        userAgent[16] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
        userAgent[17] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
        userAgent[18] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
        userAgent[19] = "Mozilla/5.0 (Windows NT 6.4; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36";
        userAgent[20] = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36";
        userAgent[21] = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:24.0) Gecko/20100101 Firefox/24.0";
        userAgent[22] = "Mozilla/5.0 (Windows NT 6.0; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0";
        userAgent[23] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:24.0) Gecko/20100101 Firefox/24.0";
        userAgent[24] = "Mozilla/5.0 (compatible; MSIE 10.0; Macintosh; Intel Mac OS X 10_7_3; Trident/6.0)";
        userAgent[25] = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
        userAgent[26] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0) chromeframe/10.0.648.205";
        userAgent[27] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; FunWebProducts)";
        userAgent[28] = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0";
        userAgent[29] = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
        userAgent[30] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
        userAgent[31] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
        userAgent[32] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
        userAgent[33] = "Mozilla/5.0 (Windows NT 6.4; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36";

    }

    public void getImages(){
        for (Element element: document.select("div.images img[src]"))
        {

            String imgSrc = element.attr("src");
            System.out.println("image - " +  imgSrc);

        }
    }

    public void getTitle(){
        try{
            //title
            postTitle = document.select("#productTitle").text();
            System.out.println("Title - " + postTitle);

            if(postTitle.length()==0){
                skip = true;
            }

            //savedTitles.add(postTitle);

        }catch(Exception e){
            //System.out.println("Cant get Product title");
        }
    }

    public void getBy(){

        try {
            byTest = document.select("#bylineInfo_feature_div").text();

                byString = byTest;


        }catch(Exception e){
        }



        try {
            byTest = document.select("#brandBylineWrapper").text();
            if(byString.equals("")) {
                byString = byTest;
            }
        }catch (Exception f){
        }


        try {
            byTest = document.select("#brandByline_feature_div").text();
           if(byString.equals("")) {
                byString = byTest;
            }
        }catch (Exception f){
        }


        try{

            by = byString;
            //System.out.println("Brand - " + by);
        }catch(Exception e){
            //System.out.println("Cant get by");
        }
    }

    public void getDescriptions(){
        try{
            //description 1
            String words = document.select("#feature-bullets").html();
            String temp = words.replace("<br>", "$$$");
            Document doc1 = Jsoup.parse(temp);
            String desc1fix = doc1.body().text().replace("$$$", "\n").toString();
            String desc1 = desc1fix.replace("This fits your  . Enter your model number to make sure this fits. ","");
            //System.out.println("Description 1 - " + desc1);
            postContent = desc1;


            //description 2
            String words2 = document.select("#productDescription p").html();
            String temp2 = words2.replace("<br>", "$$$");
            Document doc2 = Jsoup.parse(temp2);
            desc2 = doc2.body().text().replace("$$$", "\n").toString();
        }catch(Exception e){
            //System.out.println("Cant get Description 1 or 2");
        }


        desc6 = "";
        try{
            String words6 = document.getElementById("dpx-product-description_feature_div").html();
            String temp6 = words6.replace("<br>", "$$$");
            Document doc6 = Jsoup.parse(temp6);
            desc6 = doc6.body().text().replace("$$$", "\n").toString();

        }
        catch(Exception e){
            //System.out.println("Cant get Description 3");
        }

        if(desc6.length() > desc2.length()){
            //System.out.println("Description 3 - " + desc6);
            description = desc6;
        }else{
            //System.out.println("Description 2 - " + desc2);
            description = desc2;
        }

        //System.out.print("test description: " + document.getElementById("productDescription").html());

    }

    public void featuredText(){
        try {String words3 = document.select("div#aplus_feature_div.feature p").html();
            String temp3 = words3.replace("<br>", "$$$");
            Document doc3 = Jsoup.parse(temp3);
            String desc3 = doc3.body().text().replace("$$$", "\n").toString();


            String words4 = document.select("div#aplus3p_feature_div.feature p").html();
            String temp4 = words4.replace("<br>", "$$$");
            Document doc4 = Jsoup.parse(temp4);
            String desc4 = doc4.body().text().replace("$$$", "\n").toString();


            if (desc4.length() > desc3.length()){
                //System.out.println("Description Feature - " + desc4);
                tech = desc4;
            }
            if(desc4.length() == 0 && desc3.length() == 0){
                tech = "";

            }
            else{
                //System.out.println("Description Feature - " + desc3);
                tech = desc3;
            }}
        catch(Exception e){
            //System.out.println("Theres no featured description");
        }
    }

    public void getPrice(){
        try{
            Element prices = document.getElementById("priceblock_ourprice");
            price = prices.text().replace("$ ", "").replace(" ", ".").replace("$", "");



            //System.out.println(price);

        }catch(Exception e){
            //System.out.println("There is no price");
        }
    }

    public void getReviews(){
        try{
            Element custReviews = document.getElementById("acrCustomerReviewLink");

            String customer = custReviews.text();
            //System.out.println(customer);

            customerReviews = customer;


        }catch(Exception e){
            //System.out.println("Theres no review");
        }

        try{
            Elements reviews = document.getElementsByClass("a-row review-data");

            String words5 = reviews.first().html();
            String temp5 = words5.replace("<br>", "$$$");
            Document doc5 = Jsoup.parse(temp5);
            String desc5 = doc5.body().text().replace("$$$", "\n").replace("Read more", "").toString();

            //System.out.println("Reviews - " + desc5);

            review = desc5;

            //Thread.sleep((long)(Math.random() * delay)); //delay 2

        }catch(Exception e){
            // System.out.println("Theres no review");
        }

    }

    public void getCategories(){
        try{ Element navigation = document.getElementById("wayfinding-breadcrumbs_container");
            String navEdit = navigation.text().replaceAll(" › ", "|");

            //System.out.println("Navigation - " + navEdit + "|" + getBy);
            postCategory = navEdit + "|" + getBy;
        }
        catch(Exception e){
            // System.out.println("Navigation - " + getBy);
            postCategory = getBy;
        }
    }

    public void getImageString(){
        try {
            Elements test = document.select("div#leftCol img[src]");
            imageString = "div#leftCol img[src]";
        }catch(Exception e){
            imageString = "div#altImages img[src]";
        }
    }

    public void getProductImages(){

        try{
            Elements imageE = document.select(imageString);
            String image = imageE.attr("src") ;


            //String imgReplace = image.substring(image.length()-10,image.length() -3);
            //System.out.println(imgReplace);
            //featuredImage = image.replace(image.substring(image.length()-10,image.length() -3), "");
            featuredImage = image;

            if (image.contains("G/01/")||!image.startsWith("http")){
                System.out.println("There is no featured image... Skipped....");
                skip = true;
                noImg = true;

            }
        }catch(Exception e){
            System.out.println("getProduct Images failed");
        }

         //System.out.println("Image - " + featuredImage);
    }

    public void getExtraImages(){
        extraImages[0] = "";
        extraImages[1] = "";
        extraImages[2] = "";
        extraImages[3] = "";
        extraImages[4] = "";


        try{
            Element imageE2 = document.select(imageString).get(0);
            String image2 = imageE2.attr("src") ;
            extraImages[0] = image2;
            //System.out.println("testing = " + extraImages[0]);
            if(image2.length() == 0 || image2.contains("data")){
                extraImages[0] = "";
            }


            Element imageE3 = document.select(imageString).get(1);
            String image3 = imageE3.attr("src") ;
            extraImages[1] = image3;
            //System.out.println("testing = " + extraImages[1]);
            if(image3.length() == 0 || image3.contains("data")){
                extraImages[1] = "";
            }

            Element imageE4 = document.select(imageString).get(2);
            String image4 = imageE4.attr("src") ;
            extraImages[2] = image4;
            //System.out.println("testing = " + extraImages[2]);
            if(image4.length() == 0 || image4.contains("data")){
                extraImages[2] = "";
            }


            Element imageE5 = document.select(imageString).get(3);
            String image5 = imageE5.attr("src") ;
            extraImages[3] = image5;
            //System.out.println("testing = " + extraImages[3]);
            if(image5.length() == 0 || image5.contains("data")){
                extraImages[3] = "";
            }


            Element imageE6 = document.select(imageString).get(4);
            String image6 = imageE6.attr("src") ;
            extraImages[4] = image6;
            //System.out.println("testing = " + extraImages[4]);
            if(image6.length() == 0  || image6.contains("data")){
                extraImages[4] = "";
            }

        }catch(Exception e){
           // System.out.println("There are no extra Images");
        }
    }

    public void getFeaturedImages(){
        try{
            longest = "";
            String aplus1 = document.select("div#productDescription").text();
            String aplus2 = document.select("div#aplus").text();
            String aplus3 = document.select("div#aplus3p_feature_div.feature").text();

            if ((aplus1.length() > aplus2.length()) && (aplus1.length() > aplus3.length())){
                longest = "div#productDescription";
            }
            if ((aplus2.length() > aplus1.length()) && (aplus2.length() > aplus3.length())){
                longest = "div#aplus";
            }
            if ((aplus3.length() > aplus1.length()) && (aplus3.length() > aplus2.length())){
                longest = "div#aplus3p_feature_div.feature";
            }

            fImages[0] = "";
            fImages[1] = "";
            fImages[2] = "";
            fImages[3] = "";
            fImages[4] = "";

            Element fimageE3 = document.select(longest + " img[src]").get(1);
            String fimage3 = fimageE3.attr("src") ;
            fImages[1] = fimage3;
            //System.out.println("testing 2 = " + fImages[1]);

            if(fimage3.length() == 0 || fimage3.contains("data")){
                fImages[1] = "";
            }

            Element fimageE4 = document.select(longest + " img[src]").get(2);
            String fimage4 = fimageE4.attr("src") ;
            fImages[2] = fimage4;
            //System.out.println("testing 3 = " + fImages[2]);

            if(fimage4.length() == 0 || fimage4.contains("data")){
                fImages[2] = "";
            }

            Element fimageE5 = document.select(longest + " img[src]").get(3);
            String fimage5 = fimageE5.attr("src") ;
            fImages[3] = fimage5;
            //System.out.println("testing 4 = " + fImages[3]);

            if(fimage5.length() == 0 || fimage5.contains("data")){
                fImages[3] = "";
            }

            Element fimageE6 = document.select(longest + " img[src]").get(4);
            String fimage6 = fimageE6.attr("src") ;
            fImages[4] = fimage6;
            //System.out.println("testing 5 = " + fImages[4]);

            if(fimage6.length() == 0 || fimage6.contains("data")){
                fImages[4] = "";
            }

        }catch(Exception e){
            //System.out.println("There are no extra Images");
        }
    }

    public void removeFromImage(){
        for(int i = 0; i < remove().length; i++){
            featuredImage = featuredImage.replace(remove()[i], "");
            extraImages[0] = extraImages[0].replace(remove()[i], "");
            extraImages[1] = extraImages[1].replace(remove()[i], "");
            extraImages[2] = extraImages[2].replace(remove()[i], "");
            extraImages[3] = extraImages[3].replace(remove()[i], "");
            extraImages[4] = extraImages[4].replace(remove()[i], "");
            fImages[0] = fImages[0].replace(remove()[i], "");
            fImages[1] = fImages[1].replace(remove()[i], "");
            fImages[2] = fImages[2].replace(remove()[i], "");
            fImages[3] = fImages[3].replace(remove()[i], "");
            fImages[4] = fImages[4].replace(remove()[i], "");

            postTitle = postTitle.replace(remove()[i], "").replace("\n","<br>").replace(",","<c>");
            postContent = postContent.replace(remove()[i], "").replace("\n","<br>").replace(",","<c>");
            description= description.replace(remove()[i], "").replace("\n","<br>").replace(",","<c>");
            tech = tech.replace(remove()[i], "").replace("\n","<br>").replace(",","<c>");
            review = review.replace(remove()[i], "").replace("\n","<br>").replace(",","<c>");

            //System.out.println(featuredImage + "\n" + extraImages[0] + "\n" + extraImages[1] + "\n" + extraImages[2] + "\n" + fImages[0] + "\n" + fImages[1] + "\n" + fImages[2] + "\n" + fImages[3] + "\n" );
            //Thread.sleep(1000);
        }
    }

    public void removeUselessImages(){
        for(int i = 0; i < extraImages.length; i++){
            if(extraImages[i].contains("//data")){
                extraImages[i] = "";
            }
            if(extraImages[i].contains("//fls")){
                extraImages[i] = "";
            }
            if(extraImages[i].contains("nav-sprite")){
                extraImages[i] = "";
            }
            if(extraImages[i].contains("loadIndicator-large.gif")){
                extraImages[i] = "";
            }
            if(fImages[i].contains("//data")){
                fImages[i] = "";
            }
            if(fImages[i].contains("//fls")){
                fImages[i] = "";
            }
            if(fImages[i].contains("nav-sprite")){
                fImages[i] = "";
            }
            if(fImages[i].contains("loadIndicator-large.gif")){
                fImages[i] = "";
            }
        }
    }

    public void removeDuplicateDescription(){
        if (postContent == description||postContent.length() == description.length()){
            description = "";
        }
    }

    public void increaseCount(){
        //System.out.println("ip Address = " + proxy);
        //System.out.println("User Agent = " + userAgent[count2]);

        count2++;
        if(count2 >= userAgent.length - 1){
            count2 = 0;
        }
    }

    public String[] remove() {
        String[] badString = {
        "› See more product details",
                "_SY355_.",
                "_SX355_.",
                "_SX425_.",
                "_SX522_.",
                "_SR38,50_.",
                "_US40_.",
                "_SX450_.",
                "_SX342_.",
                "_SY450_.",
                "_SY550_.",
                "_SX385_.",
                "_SY679_.",
                "_SY606_.",
                "_SY445_.",
                "_SX466_.",
                "",
                "_SS40_.",
                "_QL70_.",
                "_SX300.",
                "_SY300.",
                "_SX342.",
                "_SR970,300_.",
                "_SL300__.",
                "_SR300,300_.",
                "read more",
                "_SX38_SY50_CR,0,0,38,50_.",
                "_SX38_SY50_CR,0,0,",
                ".38,50_",
                "\\",
                "*",
                "[",
                "]",
                "?",
                "=",
                "Product description ",
                "\"",
                "_SX38_SY50_CR,0,0,38,50_.",
                "_SS40_.",
                "<",
                ">"

    };
    
        return badString;
    }

}

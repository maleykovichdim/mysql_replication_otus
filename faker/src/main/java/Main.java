
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.google.gson.Gson;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

    static private final boolean isInFile  = false;

    public static void main(String[] args) throws ParseException, IOException {

        //in file block
        String path = "c:\\work\\highload\\users.csv";
        HashSet<String> emails = new HashSet<>();
        String postUrl="http://localhost/api/user";
        Gson         gson          = new Gson();

        RequestConfig requestConfig = RequestConfig.custom().
                setConnectionRequestTimeout(1000).setConnectTimeout(1000).setSocketTimeout(1000).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);
        CloseableHttpClient httpClient    = builder.create().build();


        HttpPost     post          = new HttpPost(postUrl);
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        Faker faker = new Faker(new Locale("en-US"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        for (int i=1; i<1000001; i++){
            int id = i;
            String firstName  = "zzz"+faker.name().firstName();
            String lastName = faker.name().lastName();
            Date date_ = faker.date().between(formatter.parse("1945-10-08"),formatter.parse("2020-10-08"));
            //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(date_);
            String gender = (Math.random() < 0.5)?"male": "female";
            String city = faker.address().city();
            String email = "xxx"+fakeValuesService.bothify("??????????##@gmail.com");
            String password = "$2a$14$zvWCmfqJN4bvMC1LRaaeVOxSH6kJUrCGJ4csmbPVdJkuf02mwkveW";
            String avatar = "NULL";
            String has_personal_page = "0";

            String out = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    id,
                    firstName,
                    lastName,
                    date,
                    gender,
                    city,
                    email,
                    password,
                    avatar,
                    has_personal_page
            );

            if (emails.contains(email)){ //UNIQUE FIELD
                i--;
            }else{
                emails.add(email);
                if (isInFile) {
                    Files.writeString(Paths.get(path), out, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }else{
                    System.out.println("--------------------------------");
                    Message mes = new Message(firstName,lastName,date,gender,city,email,password);
                    System.out.println(mes.toString());
                    StringEntity postingString = new StringEntity(gson.toJson(mes));//gson.tojson() converts your pojo to json
                    post.setEntity(postingString);
                    HttpResponse  response = httpClient.execute(post);
                    System.out.println("------------------------------");
                    System.out.println(response.toString());
                    System.out.println("------------------------------");
                }
            }
        }
    }
}

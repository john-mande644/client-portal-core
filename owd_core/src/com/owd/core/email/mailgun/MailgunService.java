package com.owd.core.email.mailgun;

/**
 * Created by danny on 8/30/2019.
 */
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;

public class MailgunService {
    public final static String MAILGUN_API_USERNAME = "api";

    private final HttpUrl mailgunApiUrl;
    private final String authHeaderValue;

    public MailgunService(HttpUrl mailgunbaseUrl, String mailgunDomain, String mailgunApiKey) {
        this.mailgunApiUrl = HttpUrl.parse(mailgunbaseUrl.toString() + "/" + mailgunDomain);
        this.authHeaderValue = buildAuthHeader(mailgunApiKey);
    }

    public boolean sendMail(MailgunEmail email) throws IOException {
        Request request = new Request.Builder()
                .url(mailgunApiUrl.toString() + "/messages")
                .post(email.toMultipartBody())
                .addHeader("Authentication", authHeaderValue)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        return response.isSuccessful();
    }

    private static String buildAuthHeader(String mailgunApiKey) {
        String authString = String.format("%s:%s", MAILGUN_API_USERNAME, mailgunApiKey);
        ByteString authData = ByteString.encodeUtf8(authString);
        return "Basic " + authData.base64();
    }

    public static void main(String[] args) throws IOException {
        MailgunService mailgunService = new MailgunService(
                HttpUrl.parse("https://api.mailgun.net/v3"),
                "<YOUR MAILGUN DOMAIN>",
                "<YOUR MAILGUN API KEY>");

        MailgunEmail email = new MailgunEmail.Builder()
                .from(new MailgunEmail.Contact("Marcelo Cortes", "marcelo@example.com"))
                .addTo(new MailgunEmail.Contact("Marcelo +1", "marcelo+1@example.com"))
                .addCc(new MailgunEmail.Contact("Marcelo Cortes + 3", "marcelo+3@example.com"))
                .addBcc(new MailgunEmail.Contact("Marcelo +2", "marcelo+2@example.com"))
                .subject("Hi here!")
                .html("<html>Inline image here: <img src=\"cid:profile.jpg\"></html>")
                .addInline(new MailgunEmail.Attachment("profile.jpg",
                        RequestBody.create(
                                MediaType.parse("image/jpg"),
                                new File("/Users/marcelo/Documents/profile.jpg"))))
                .build();

        boolean success = mailgunService.sendMail(email);
        System.out.println(success ? "MESSAGE SENT" : "FAILED TO SEND");
    }
}
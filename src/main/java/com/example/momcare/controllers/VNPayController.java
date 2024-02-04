package com.example.momcare.controllers;

import com.example.momcare.config.VNPayConfig;
import com.example.momcare.models.User;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class VNPayController {
    @Autowired
    private UserService userService;
    @GetMapping("payment/create")
    public Response create(@RequestParam String id) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");
        long amount = 1000000*100;
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        //String vnp_IpAddr = VNPayConfig.getIpAddress(req);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl+id);
        String vnp_IpAddr = VNPayConfig.getIpAddress();
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        cld.add(Calendar.HOUR, 7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
        List<String> urls = new ArrayList<>();
        urls.add(paymentUrl);
        return new Response((HttpStatus.OK.getReasonPhrase()),urls , "success");
    }

    @GetMapping(value = "payment/success", produces = MediaType.TEXT_HTML_VALUE)
    public String successPayment(@RequestParam String id){
        User check = userService.findAccountByID(id);
        check.setPremium(true);
        userService.update(check);
        return "<html>\n" +
                "  <head>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap\" rel=\"stylesheet\">\n" +
                "  </head>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        text-align: center;\n" +
                "        padding: 40px 0;\n" +
                "        background: #EBF0F5;\n" +
                "      }\n" +
                "        h1 {\n" +
                "          color: #88B04B;\n" +
                "          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\n" +
                "          font-weight: 900;\n" +
                "          font-size: 40px;\n" +
                "          margin-bottom: 10px;\n" +
                "        }\n" +
                "        p {\n" +
                "          color: #404F5E;\n" +
                "          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\n" +
                "          font-size:20px;\n" +
                "          margin: 0;\n" +
                "        }\n" +
                "      i {\n" +
                "        color: #9ABC66;\n" +
                "        font-size: 100px;\n" +
                "        line-height: 200px;\n" +
                "        margin-left:-15px;\n" +
                "      }\n" +
                "      .card {\n" +
                "        background: white;\n" +
                "        padding: 60px;\n" +
                "        border-radius: 4px;\n" +
                "        box-shadow: 0 2px 3px #C8D0D8;\n" +
                "        display: inline-block;\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <body>\n" +
                "      <div class=\"card\">\n" +
                "      <div style=\"border-radius:200px; height:200px; width:200px; background: #F8FAF5; margin:0 auto;\">\n" +
                "        <i class=\"checkmark\">✓</i>\n" +
                "      </div>\n" +
                "        <h1>Success</h1> \n" +
                "        <p>We received your purchase request;<br/> you can close this!</p>\n" +
                "      </div>\n" +
                "    </body>\n" +
                "</html>";
    }

}

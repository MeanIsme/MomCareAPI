package com.example.momcare.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private String css = "<html lang=\"en\">\n"+
            "<head>\n"+
            "  <meta charset=\"UTF-8\">\n"+
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"+
            "  <title>Alert Animation</title>\n"+
            "  <style>\n"+
            "    .box {\n"+
            "      margin-top: 60px;\n"+
            "      display: flex;\n"+
            "      justify-content: space-around;\n"+
            "      flex-wrap: wrap;\n"+
            "    }\n"+
            "\n"+
            "    .alert {\n"+
            "      margin-top: 25px;\n"+
            "      background-color: #fff;\n"+
            "      font-size: 25px;\n"+
            "      font-family: sans-serif;\n"+
            "      text-align: center;\n"+
            "      width: 300px;\n"+
            "      height: 100px;\n"+
            "      padding-top: 150px;\n"+
            "      position: relative;\n"+
            "      border: 1px solid #efefda;\n"+
            "      border-radius: 2%;\n"+
            "      box-shadow: 0px 0px 3px 1px #ccc;\n"+
            "    }\n"+
            "\n"+
            "    .alert::before {\n"+
            "      width: 100px;\n"+
            "      height: 100px;\n"+
            "      position: absolute;\n"+
            "      border-radius: 100%;\n"+
            "      inset: 20px 0px 0px 100px;\n"+
            "      font-size: 60px;\n"+
            "      line-height: 100px;\n"+
            "      border: 5px solid gray;\n"+
            "      animation-name: reveal;\n"+
            "      animation-duration: 1.5s;\n"+
            "      animation-timing-function: ease-in-out;\n"+
            "    }\n"+
            "\n"+
            "    .alert > .alert-body {\n"+
            "      opacity: 0;\n"+
            "      animation-name: reveal-message;\n"+
            "      animation-duration: 1s;\n"+
            "      animation-timing-function: ease-out;\n"+
            "      animation-delay: 1.5s;\n"+
            "      animation-fill-mode: forwards;\n"+
            "    }\n"+
            "\n"+
            "    @keyframes reveal-message {\n"+
            "      from {\n"+
            "        opacity: 0;\n"+
            "      }\n"+
            "      to {\n"+
            "        opacity: 1;\n"+
            "      }\n"+
            "    }\n"+
            "\n"+
            "    .success {\n"+
            "      color: green;\n"+
            "    }\n"+
            "\n"+
            "    .success::before {\n"+
            "      content: '✓';\n"+
            "      background-color: #eff;\n"+
            "      box-shadow: 0px 0px 12px 7px rgba(200, 255, 150, 0.8) inset;\n"+
            "      border: 5px solid green;\n"+
            "    }\n"+
            "\n"+
            "    .error {\n"+
            "      color: red;\n"+
            "    }\n"+
            "\n"+
            "    .error::before {\n"+
            "      content: '✗';\n"+
            "      background-color: #fef;\n"+
            "      box-shadow: 0px 0px 12px 7px rgba(255, 200, 150, 0.8) inset;\n"+
            "      border: 5px solid red;\n"+
            "    }\n"+
            "\n"+
            "    @keyframes reveal {\n"+
            "      0% {\n"+
            "        border: 5px solid transparent;\n"+
            "        color: transparent;\n"+
            "        box-shadow: 0px 0px 12px 7px rgba(255, 250, 250, 0.8) inset;\n"+
            "        transform: rotate(1000deg);\n"+
            "      }\n"+
            "      25% {\n"+
            "        border-top: 5px solid gray;\n"+
            "        color: transparent;\n"+
            "        box-shadow: 0px 0px 17px 10px rgba(255, 250, 250, 0.8) inset;\n"+
            "      }\n"+
            "      50% {\n"+
            "        border-right: 5px solid gray;\n"+
            "        border-left: 5px solid gray;\n"+
            "        color: transparent;\n"+
            "        box-shadow: 0px 0px 17px 10px rgba(200, 200, 200, 0.8) inset;\n"+
            "      }\n"+
            "      75% {\n"+
            "        border-bottom: 5px solid gray;\n"+
            "        color: gray;\n"+
            "        box-shadow: 0px 0px 12px 7px rgba(200, 200, 200, 0.8) inset;\n"+
            "      }\n"+
            "      100% {\n"+
            "        border: 5px solid gray;\n"+
            "        box-shadow: 0px 0px 12px 7px rgba(200, 200, 200, 0.8) inset;\n"+
            "      }\n"+
            "    }\n"+
            "  </style>\n"+
            "</head>\n"+
            "<body>\n";

    public String success = css +
            "  <div class=\"box\">\n" +
            "    <div class=\"success alert\">\n" +
            "      <div class=\"alert-body\">\n" +
            "        Success !\n" +
            "           <br/> You can safely close this !" +
            "      </div>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "</body>\n" +
            "</html>";
    public String error = css +
            "  <div class=\"box\">\n" +
            "    <div class=\"error alert\">\n" +
            "      <div class=\"alert-body\">\n" +
            "        Error !\n" +
            "        <br/> Token is not exits"+
            "        <br/>Contact us!!!" +
            "      </div>\n" +
            "  </div>" +
            "  </div>\n" +
            "</body>\n" +
            "</html>";

    public void sendEmail(String to, String subject, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String body = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "  <title>Email Confirmation</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <style type=\"text/css\">\n" +
                "  /**\n" +
                "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
                "   */\n" +
                "  @media screen {\n" +
                "    @font-face {\n" +
                "      font-family: 'Source Sans Pro';\n" +
                "      font-style: normal;\n" +
                "      font-weight: 400;\n" +
                "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                "    }\n" +
                "    @font-face {\n" +
                "      font-family: 'Source Sans Pro';\n" +
                "      font-style: normal;\n" +
                "      font-weight: 700;\n" +
                "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                "    }\n" +
                "  }\n" +
                "  /**\n" +
                "   * Avoid browser level font resizing.\n" +
                "   * 1. Windows Mobile\n" +
                "   * 2. iOS / OSX\n" +
                "   */\n" +
                "  body,\n" +
                "  table,\n" +
                "  td,\n" +
                "  a {\n" +
                "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                "  }\n" +
                "  /**\n" +
                "   * Remove extra space added to tables and cells in Outlook.\n" +
                "   */\n" +
                "  table,\n" +
                "  td {\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    mso-table-lspace: 0pt;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Better fluid images in Internet Explorer.\n" +
                "   */\n" +
                "  img {\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Remove blue links for iOS devices.\n" +
                "   */\n" +
                "  a[x-apple-data-detectors] {\n" +
                "    font-family: inherit !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Fix centering issues in Android 4.4.\n" +
                "   */\n" +
                "  div[style*=\"margin: 16px 0;\"] {\n" +
                "    margin: 0 !important;\n" +
                "  }\n" +
                "  body {\n" +
                "    width: 100% !important;\n" +
                "    height: 100% !important;\n" +
                "    padding: 0 !important;\n" +
                "    margin: 0 !important;\n" +
                "  }\n" +
                "  /**\n" +
                "   * Collapse table borders to avoid space between cells.\n" +
                "   */\n" +
                "  table {\n" +
                "    border-collapse: collapse !important;\n" +
                "  }\n" +
                "  a {\n" +
                "    color: #1a82e2;\n" +
                "  }\n" +
                "  img {\n" +
                "    height: auto;\n" +
                "    line-height: 100%;\n" +
                "    text-decoration: none;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "  }\n" +
                "  </style>\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"background-color: #e9ecef;\">\n" +
                "\n" +
                "  <!-- start preheader -->\n" +
                "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
                "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                "  </div>\n" +
                "  <!-- end preheader -->\n" +
                "\n" +
                "  <!-- start body -->\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "\n" +
                "\n" +
                "   \n" +
                "\n" +
                "    <!-- start hero -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Confirm Your Email Address</h1>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end hero -->\n" +
                "\n" +
                "    <!-- start copy block -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "              <p style=\"margin: 0;\">Tap the button below to confirm your email address. If you didn't create an account with MomCare</a>, you can safely delete this email.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "          <!-- start button -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                <tr>\n" +
                "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                      <tr>\n" +
                "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                "                          <a href=\"https://momcarevn-9e328ff56775.herokuapp.com/verifyemail?token=" + token + "\" target=\"_blank\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Verify email</a>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end button -->\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "              <p style=\"margin: 0;\">If that doesn't work, email us: nghiapv74@gmail.com</p>\n" +
                "\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                "              <p style=\"margin: 0;\">Thanks & Best regards,<br> Mom Care</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end copy block -->\n" +
                "\n" +
                "    <!-- start footer -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "          <!-- start permission -->\n" +
                "          <tr>\n" +
                "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                "              <p style=\"margin: 0;\">You received this email because we received a request for create for your account. If you didn't request create you can safely delete this email.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end permission -->\n" +
                "\n" +
                "          <!-- start unsubscribe -->\n" +
                "          <tr>\n" +
                "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                "              \n" +
                "              <p style=\"margin: 0;\">1 Vo Van Ngan, Ho Chi Minh city</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end unsubscribe -->\n" +
                "\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end footer -->\n" +
                "\n" +
                "  </table>\n" +
                "  <!-- end body -->\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        helper.setText(body, true); // Use this or above line.
        helper.setTo(to);
        helper.setSubject("Verify email");

        mailSender.send(mimeMessage);
    }
}

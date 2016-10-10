<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="header1">text-align: center; font-size: 32px; padding: 35px 0 0 0; margin: 0; color: #ab0000;</c:set>
<c:set var="header2">text-align: center; font-size: 22px; padding: 10px 0 0 0; margin: 0;</c:set>
<c:set var="header3">text-align: center; font-size: 20px; padding: 50px 0 0 0; margin: 0;</c:set>
<c:set var="header4">text-align: center; font-size: 17px; padding: 40px 0 0 0; margin: 0;</c:set>
<c:set var="header5">text-align: center; font-size: 16px; padding: 30px 0 0 0; margin: 0;</c:set>
<c:set var="btn_td">background: #2fbf6f; -moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px; border-bottom: 2px solid #03883e !important;" valign="top</c:set>
<c:set var="btn_a">color: #FFFFFF; display: block; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; font-family:Arial, Helvetica, sans-serif;</c:set>
 <!--[if mso]>
 	<center>
 	<table><tr><td width="680">
 <![endif]-->
 
 <table id="main" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin: 0 auto; padding: 0; max-width: 680px; background-color: #FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 18px;">
   <tr><td>
     <img id="headImage" src="https://images.la-etienda.com/weddingToste_680x265.jpg" width="680" height="265" alt="head image"  border="0" style="margin:0;"/>
   </td></tr>
   <tr><td style="${header1}"><b>${lang.out('email_signup_title')}</b></td></tr>
   <tr><td style="${header5}">${lang.out('email_signup_text') }</td></tr>
   
   <tr><td align="center">
       <table border="0" cellpadding="10" cellspacing="0" width="250" style="margin: 15px 0 0 0">
         <tr>
           <td align="center" style="${btn_td}">
             <a href="${page.url}/user/password/confirm?id=${page.encode(emailuser.username)}" style="${btn_a}" target="_blank">${lang.out('email_signup_password_button') }</a>
           </td>
         </tr>
       </table>
     </td>
   </tr>
 </table>
 
 <!--[if mso]>
 	</td></tr></table>
 	</center>
 <![endif]-->
 
 <style type="text/css">
  @media only screen  and (max-width: 480px) {
    img[id="headImage"]{
      height: auto !important;
      max-width: 480px !important;
      width: 100% !important;
    }
  }
</style>
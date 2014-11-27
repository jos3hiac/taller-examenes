package controllers;

import com.avaje.ebean.ExpressionList;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import models.*;
import views.html.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static play.data.Form.*;

public class Application extends Controller {
    public static Result index() {
        //return ok(index.render("Your new application is ready."));
        return redirect("/login");
    }
    public static Result login(){
        if(Auth()!=null)return Auth();
        return ok(login.render());
    }
    private static Result Auth(){
        if(session("email")!=null) {
            User user = User.findByEmail(session("email"));
            if(user.professor!=null)
                return redirect(routes.Profesor.index());
            if(user.student!=null)
                return redirect(routes.Alumno.index());
            if(user.admin!=null)
                return redirect(routes.Administrador.index());
        }
        return null;
    }
    public static Result authenticate() {
        Form<User> loginForm = form(User.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render());
        } else {
            User user=User.authenticate(loginForm.get().email,loginForm.get().pass);
            if(user==null) {
                flash("type","danger");
                flash("danger","El usuario o la contraseña son incorrectos");
                return redirect(routes.Application.login());
            }
            else if(user.active==0){
                flash("type","info");
                flash("info","Su cuenta de usuario está desactivada");
                return redirect(routes.Application.login());
            }
            else{
                session("email", user.email);
                if(user.professor!=null)
                    return redirect(routes.Profesor.index());
                if(user.student!=null)
                    return redirect(routes.Alumno.index());
                if(user.admin!=null)
                    return redirect(routes.Administrador.index());
                return redirect(routes.Application.login());
            }
        }
    }
    public static Result logout() {
        session().clear();
        flash("type", "info");
        flash("info", "La sesión ha sido cerrada");
        return redirect(routes.Application.login());
    }
    public static Result validateDate(){
        DynamicForm data = form().bindFromRequest();
        String error="";
        if(data.get("required")!=null){
            if(getDate(data.get("value"))==null)error="La fecha debe ser válida";
        }
        if(data.get("min")!=null && error==""){
            if(data.get("min").equals("today")){
                if(getDate(data.get("value")).before(new Date()))
                    error="La fecha debe ser mínimo la fecha actual";
            }
        }
        return ok(error);
    }
    public static Result validateListExist(){
        DynamicForm data = form().bindFromRequest();
        String values=data.get("values");
        String lista="",message=data.get("message"),error="";
        for(String value:values.split(data.get("delimiter"))){
            if(exist(data.get("model"),data.get("column"),value,data.get("condition")))lista+=","+value;
        }
        if(!lista.equals("")){
            lista=lista.substring(1);
            error=message.split("\\?").length==1?message:message.split("\\?")[0]+lista+message.split("\\?")[1];
        }
        return ok(error);
    }
    public static Result validateExist(){
        DynamicForm data = form().bindFromRequest();
        String error="",message=data.get("message");
        if(exist(data.get("model"),data.get("column"),data.get("value"),data.get("condition")))
            error=message.split("\\?").length==1?message:message.split("\\?")[0]+data.get("value")+message.split("\\?")[1];
        return ok(error);
    }
    private static boolean exist(String model,String column,String value,String conditions){
        boolean exist=false;
        switch(model) {
            case "Course":exist=exist(Course.find.where(),column,value,conditions);break;
            case "Theme":exist=exist(Theme.find.where(),column,value,conditions);break;
            case "Cycle":exist=exist(Cycle.find.where(),column,value,conditions);break;
            case "Section":exist=exist(Section.find.where(),column,value,conditions);break;
            case "Professor":exist=exist(Professor.find.where(),column,value,conditions);break;
            case "Student":exist=exist(Student.find.where(),column,value,conditions);break;
            case "User":exist=exist(User.find.where(),column,value,conditions);break;
            case "Asignature":exist=exist(Asignature.find.where(),column,value,conditions);break;
            case "Exam":exist=exist(Exam.find.where(),column,value,conditions);break;
        }
        return exist;
    }
    private static <T> boolean exist(ExpressionList<T> expression,String column,String value,String conditions){
        return exist(expression.eq(column,value),conditions);
    }
    private static <T> boolean exist(ExpressionList<T> expression,String conditions){
        if(conditions!=null && conditions.split(",").length>0){
            for (Map.Entry<String, String> e : getMap(conditions).entrySet()) {
                expression = expression.eq(e.getKey(), e.getValue());
            }
        }
        return !expression.findList().isEmpty();
    }
    private static HashMap<String,String> getMap(String conditions){
        HashMap<String,String> eq = new HashMap<>();
        for(String condition:conditions.split(",")){
            eq.put(condition.split(":")[0],condition.split(":")[1]);
        }
        return eq;
    }
    public static User user(){
        return User.findByEmail(session("email"));
    }
    public static String getDate(Date date){
        return new SimpleDateFormat("dd/MM/yy").format(date);
    }
    public static String getDateTime(Date date){return new SimpleDateFormat("dd/MM/yy HH:mm").format(date);}
    public static String getDate(Date date,String format){
        return new SimpleDateFormat(format).format(date);
    }
    public static Date getDate(String input){
        List<SimpleDateFormat> formats = new ArrayList<>();
        formats.add(new SimpleDateFormat("dd/MM/yy HH:mm"));formats.add(new SimpleDateFormat("dd-MM-yy HH:mm"));
        formats.add(new SimpleDateFormat("dd/MM/yy"));formats.add(new SimpleDateFormat("dd-MM-yy"));
        Date date = null;
        if(input == null) {
            return null;
        }
        for (SimpleDateFormat format : formats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {
                //Shhh.. try other formats
            }
            if (date != null) {
                break;
            }
        }
        return date;
    }
    public static boolean CopyFile(File dirOrigen, File dirDestino){
        boolean error=false;
        try {
            InputStream in = new FileInputStream(dirOrigen);
            OutputStream out = new FileOutputStream(dirDestino);

            byte[] buffer = new byte[1024];
            int len;
            // recorrer el array de bytes y recomponerlo
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            } // end while
            out.flush();
             in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();error=true;
        }
        return !error;
    }
    public static boolean B64ToFile(String base,String path,String filename){
        boolean error=false;
        FileOutputStream fileOutputStream=null;
        try {
            byte[] decode = Base64Coder.decode(base);
            File file=new File(path+"/"+filename);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(decode);
            fileOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();error=true;
        }
        return !error;
    }
}
class Base64Coder {

    // The line separator string of the operating system.
    private static final String systemLineSeparator = System.getProperty("line.separator");

    // Mapping table from 6-bit nibbles to Base64 characters.
    private static final char[] map1 = new char[64];
    static {
        int i=0;
        for (char c='A'; c<='Z'; c++) map1[i++] = c;
        for (char c='a'; c<='z'; c++) map1[i++] = c;
        for (char c='0'; c<='9'; c++) map1[i++] = c;
        map1[i++] = '+'; map1[i++] = '/'; }

    // Mapping table from Base64 characters to 6-bit nibbles.
    private static final byte[] map2 = new byte[128];
    static {
        for (int i=0; i<map2.length; i++) map2[i] = -1;
        for (int i=0; i<64; i++) map2[map1[i]] = (byte)i; }

    /**
     * Encodes a string into Base64 format.
     * No blanks or line breaks are inserted.
     * @param s  A String to be encoded.
     * @return   A String containing the Base64 encoded data.
     */
    public static String encodeString (String s) {
        return new String(encode(s.getBytes())); }

    /**
     * Encodes a byte array into Base 64 format and breaks the output into lines of 76 characters.
     * This method is compatible with <code>sun.misc.BASE64Encoder.encodeBuffer(byte[])</code>.
     * @param in  An array containing the data bytes to be encoded.
     * @return    A String containing the Base64 encoded data, broken into lines.
     */
    public static String encodeLines (byte[] in) {
        return encodeLines(in, 0, in.length, 76, systemLineSeparator); }

    /**
     * Encodes a byte array into Base 64 format and breaks the output into lines.
     * @param in            An array containing the data bytes to be encoded.
     * @param iOff          Offset of the first byte in <code>in</code> to be processed.
     * @param iLen          Number of bytes to be processed in <code>in</code>, starting at <code>iOff</code>.
     * @param lineLen       Line length for the output data. Should be a multiple of 4.
     * @param lineSeparator The line separator to be used to separate the output lines.
     * @return              A String containing the Base64 encoded data, broken into lines.
     */
    public static String encodeLines (byte[] in, int iOff, int iLen, int lineLen, String lineSeparator) {
        int blockLen = (lineLen*3) / 4;
        if (blockLen <= 0) throw new IllegalArgumentException();
        int lines = (iLen+blockLen-1) / blockLen;
        int bufLen = ((iLen+2)/3)*4 + lines*lineSeparator.length();
        StringBuilder buf = new StringBuilder(bufLen);
        int ip = 0;
        while (ip < iLen) {
            int l = Math.min(iLen-ip, blockLen);
            buf.append (encode(in, iOff+ip, l));
            buf.append (lineSeparator);
            ip += l; }
        return buf.toString(); }

    /**
     * Encodes a byte array into Base64 format.
     * No blanks or line breaks are inserted in the output.
     * @param in  An array containing the data bytes to be encoded.
     * @return    A character array containing the Base64 encoded data.
     */
    public static char[] encode (byte[] in) {
        return encode(in, 0, in.length); }

    /**
     * Encodes a byte array into Base64 format.
     * No blanks or line breaks are inserted in the output.
     * @param in    An array containing the data bytes to be encoded.
     * @param iLen  Number of bytes to process in <code>in</code>.
     * @return      A character array containing the Base64 encoded data.
     */
    public static char[] encode (byte[] in, int iLen) {
        return encode(in, 0, iLen); }

    /**
     * Encodes a byte array into Base64 format.
     * No blanks or line breaks are inserted in the output.
     * @param in    An array containing the data bytes to be encoded.
     * @param iOff  Offset of the first byte in <code>in</code> to be processed.
     * @param iLen  Number of bytes to process in <code>in</code>, starting at <code>iOff</code>.
     * @return      A character array containing the Base64 encoded data.
     */
    public static char[] encode (byte[] in, int iOff, int iLen) {
        int oDataLen = (iLen*4+2)/3;       // output length without padding
        int oLen = ((iLen+2)/3)*4;         // output length including padding
        char[] out = new char[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
            int i0 = in[ip++] & 0xff;
            int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
            int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            out[op++] = map1[o0];
            out[op++] = map1[o1];
            out[op] = op < oDataLen ? map1[o2] : '='; op++;
            out[op] = op < oDataLen ? map1[o3] : '='; op++; }
        return out; }

    /**
     * Decodes a string from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     * @param s  A Base64 String to be decoded.
     * @return   A String containing the decoded data.
     * @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static String decodeString (String s) {
        return new String(decode(s)); }

    /**
     * Decodes a byte array from Base64 format and ignores line separators, tabs and blanks.
     * CR, LF, Tab and Space characters are ignored in the input data.
     * This method is compatible with <code>sun.misc.BASE64Decoder.decodeBuffer(String)</code>.
     * @param s  A Base64 String to be decoded.
     * @return   An array containing the decoded data bytes.
     * @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decodeLines (String s) {
        char[] buf = new char[s.length()];
        int p = 0;
        for (int ip = 0; ip < s.length(); ip++) {
            char c = s.charAt(ip);
            if (c != ' ' && c != '\r' && c != '\n' && c != '\t')
                buf[p++] = c; }
        return decode(buf, 0, p); }

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     * @param s  A Base64 String to be decoded.
     * @return   An array containing the decoded data bytes.
     * @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decode (String s) {
        return decode(s.toCharArray()); }

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     * @param in  A character array containing the Base64 encoded data.
     * @return    An array containing the decoded data bytes.
     * @throws    IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decode (char[] in) {
        return decode(in, 0, in.length); }

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded input data.
     * @param in    A character array containing the Base64 encoded data.
     * @param iOff  Offset of the first character in <code>in</code> to be processed.
     * @param iLen  Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
     * @return      An array containing the decoded data bytes.
     * @throws      IllegalArgumentException If the input is not valid Base64 encoded data.
     */
    public static byte[] decode (char[] in, int iOff, int iLen) {
        if (iLen%4 != 0) throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
        while (iLen > 0 && in[iOff+iLen-1] == '=') iLen--;
        int oLen = (iLen*3) / 4;
        byte[] out = new byte[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
            int i0 = in[ip++];
            int i1 = in[ip++];
            int i2 = ip < iEnd ? in[ip++] : 'A';
            int i3 = ip < iEnd ? in[ip++] : 'A';
            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
                throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
            int b0 = map2[i0];
            int b1 = map2[i1];
            int b2 = map2[i2];
            int b3 = map2[i3];
            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
                throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
            int o0 = ( b0       <<2) | (b1>>>4);
            int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
            int o2 = ((b2 &   3)<<6) |  b3;
            out[op++] = (byte)o0;
            if (op<oLen) out[op++] = (byte)o1;
            if (op<oLen) out[op++] = (byte)o2; }
        return out; }

    // Dummy constructor.
    private Base64Coder() {}

} // end class Base64Coder
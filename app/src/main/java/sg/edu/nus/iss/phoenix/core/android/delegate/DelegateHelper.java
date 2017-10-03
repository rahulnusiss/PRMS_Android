package sg.edu.nus.iss.phoenix.core.android.delegate;

public class DelegateHelper {

    public final static String SERVER = "http://172.17.249.149:8080";
    // RESTful parameters.
    public final static String PRMS_BASE_URL_AUTHENTICATE = SERVER + "/phoenix/rest/Login/doLogin?";
    public final static String PRMS_BASE_URL_RADIO_PROGRAM = SERVER + "/phoenix/rest/radioprogram";
    public final static String PRMS_BASE_URL_USER = SERVER + "/phoenix/rest/maintainuser";


    // "http://10.0.3.2:9000/phoenix/rest/programslot"; - genymotion local
    // 10.202.16.89 - pp in mentorica  "http://10.202.16.89:15856/phoenix/rest/programslot"
    // 172.17.249.149
    // 10.0.3.2:15856
    public final static String PRMS_BASE_URL_SCHEDULE = SERVER + "/phoenix/rest/programslot";

}

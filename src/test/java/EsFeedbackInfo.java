import lombok.Data;
import java.util.Date;

/**
 * Created by xinmei365 on 2016-10-28
 */
@Data
public class EsFeedbackInfo {
    Boolean parseSuccess;
    String logId;
    String ip;
    Date date;
    String type;//gif,sticker
    String version;
    Boolean ret;
    String duid;
    Integer sourceId;
    String id;
    Integer index;
    String appPkgName;
    String locale;
    String query;
    String timeMillis;
    String text;

    String appName;
    String appVersion;
    String appKey;
    String country;
    String language;
    String system;
    String apiLevel;
    String screen;
    String timeStamp;
}

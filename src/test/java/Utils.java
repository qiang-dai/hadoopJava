import lombok.extern.log4j.Log4j2;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xinmei365 on 2017-02-13
 */
@Log4j2
public class Utils {
    public static String getShortName(String fileName) {
        Integer pos = -1;
        if (fileName.lastIndexOf("/") != -1) {
            pos = fileName.lastIndexOf("/");
        }
        if (fileName.lastIndexOf("\\") != -1) {
            pos = fileName.lastIndexOf("/");
        }
        return fileName.substring(pos + 1);
    }
    public static String getTextMd5(String text) {
        String value = null;
        try {
            ByteBuffer sendBuffer=ByteBuffer.wrap(text.getBytes("UTF-8"));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(sendBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //pass
        }
        return value;
    }
    public static EsFeedbackInfo ParseFeedbackLine(String line, String fileName, String type) {
        EsFeedbackInfo esFeedbackInfo = new EsFeedbackInfo();
        esFeedbackInfo.setType(type);
        esFeedbackInfo.setText(line);

        line = line.replace(": ", ":");
        line = line.replace(" :", ":");
        //纠错
        line = line.replace("ret:false duid", "ret:false, duid");
        line = line.replace("appName:", ", appName:");
        line = line.replace(", , appName:", ", appName:");

        //切分
        Integer posLast = 0;
        Integer posCur = line.indexOf("INFO", posLast);
        String day = line.substring(posLast, posCur);
        String contentWithoutDay = "";
        if (posCur + 1 >= line.length()) {
            return esFeedbackInfo;
        }
        //Date
        try {
            contentWithoutDay = line.substring(posCur + 1);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime localDateTime = LocalDateTime.parse(day.trim(), dateTimeFormatter);
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            esFeedbackInfo.setDate(date);
            //设定id
            String id = getTextMd5(contentWithoutDay);
            esFeedbackInfo.setLogId(id);

            //String id = dateTimeFormatter.format(localDateTime);
            String fileIp = getShortName(fileName);
            int pos = fileIp.lastIndexOf(".");
            fileIp = fileIp.substring(0, pos);
            pos = fileIp.indexOf(".txt.");
            fileIp = fileIp.substring(pos + 5, fileIp.length());

            esFeedbackInfo.setIp(fileIp);
        } catch (Exception e) {
            log.warn(e);
            return esFeedbackInfo;
        }

        //version
        posLast = posCur;
        posCur = line.indexOf(" v1", posLast);
        posLast = posCur;
        posCur = line.indexOf(type, posLast);
        String version = line.substring(posLast, posCur);
        esFeedbackInfo.setVersion(version.trim());
        //ret
        posLast = posCur;
        posCur = line.indexOf("ret:");

        String kvInfo = line.substring(posCur);
        List<String> itemList = Arrays.asList(kvInfo.split(",")).stream()
                .filter(entity -> entity.trim().length() > 0)
                .map(e -> e.trim())
                .collect(Collectors.toList());

        for (int i = 0; i < itemList.size(); i++) {
            //避免解析出错
            try {
                String text = itemList.get(i);
                if (!text.contains(":")) {
                    continue;
                }
                //List<String> subList = Arrays.asList(text.split(":"));
                //根据第一个冒号分割
                Integer pos = text.indexOf(":");
                List<String> subList = new ArrayList<>();
                subList.add(text.substring(0, pos));
                if (text.length() > pos + 1) {
                    subList.add(text.substring(pos + 1));
                } else {
                    subList.add("");
                }
                String value = subList.get(1);

                if (subList.get(0).compareToIgnoreCase("ret") == 0) {
                    Boolean ret = Boolean.valueOf(value);
                    esFeedbackInfo.setRet(ret);
                } else if (subList.get(0).compareToIgnoreCase("duid") == 0) {
                    esFeedbackInfo.setDuid(value);
                } else if (subList.get(0).compareToIgnoreCase("sourceId") == 0) {
                    if (value.length() == 0) {
                        value = "-1";
                    }
                    Integer val = Integer.valueOf(value);
                    esFeedbackInfo.setSourceId(val);
                } else if (subList.get(0).compareToIgnoreCase("index") == 0) {
                    if (value.length() == 0) {
                        value = "-1";
                    }
                    Integer val = Integer.valueOf(value);
                    esFeedbackInfo.setIndex(val);
                } else if (subList.get(0).compareToIgnoreCase("appPkgName") == 0) {
                    esFeedbackInfo.setAppPkgName(value);
                } else if (subList.get(0).compareToIgnoreCase("locale") == 0) {
                    esFeedbackInfo.setLocale(value);
                } else if (subList.get(0).compareToIgnoreCase("query") == 0) {
                    esFeedbackInfo.setQuery(value);
                } else if (subList.get(0).compareToIgnoreCase("timeMillis") == 0) {
                    esFeedbackInfo.setTimeMillis(value);
                } else if (subList.get(0).compareToIgnoreCase("id") == 0) {
                    esFeedbackInfo.setId(value);
                } else if (subList.get(0).compareToIgnoreCase("appName") == 0) {
                    esFeedbackInfo.setAppName(value);
                } else if (subList.get(0).compareToIgnoreCase("appVersion") == 0) {
                    esFeedbackInfo.setAppVersion(value);
                } else if (subList.get(0).compareToIgnoreCase("appKey") == 0) {
                    esFeedbackInfo.setAppKey(value);
                } else if (subList.get(0).compareToIgnoreCase("country") == 0) {
                    esFeedbackInfo.setCountry(value);
                } else if (subList.get(0).compareToIgnoreCase("language") == 0) {
                    esFeedbackInfo.setLanguage(value);
                } else if (subList.get(0).compareToIgnoreCase("system") == 0) {
                    esFeedbackInfo.setSystem(value);
                } else if (subList.get(0).compareToIgnoreCase("apiLevel") == 0) {
                    esFeedbackInfo.setApiLevel(value);
                } else if (subList.get(0).compareToIgnoreCase("screen") == 0) {
                    esFeedbackInfo.setScreen(value);
                } else if (subList.get(0).compareToIgnoreCase("timeStamp") == 0) {
                    if (value.equals("null")) {
                        esFeedbackInfo.setTimeStamp(null);
                    } else {
                        esFeedbackInfo.setTimeStamp(value);
                    }
                }
            } catch (Exception e) {
                log.warn(e);
                log.warn(line);
                esFeedbackInfo.setParseSuccess(false);

                return esFeedbackInfo;
            }
        }

        //判断
        if (esFeedbackInfo.getIndex()== null || esFeedbackInfo.getIndex() < 0) {
            log.error("bad line:{}", line);
            return esFeedbackInfo;
        }
        esFeedbackInfo.setParseSuccess(true);
        return esFeedbackInfo;
    }
}

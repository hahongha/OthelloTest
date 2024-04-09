package control;

public class TimeControl {
    private long elapsedMS;
    private long fastestMS;
    private long startTime;
    private long timeNow;
    private String formatedTime;

    private boolean hasStarted;

    public TimeControl(){
        startTime = System.nanoTime();
        timeNow= 0;
        hasStarted= false;
        formatedTime = "00:00";
//        elapsedMS= System.nanoTime()- startTime;
    }
    public void timeRun(boolean hasStarted){
        if (hasStarted) {// dang choi
            elapsedMS = (System.nanoTime() - startTime) / 1000000+timeNow;// cap nhat thoi gian
            formatedTime = formatTime(elapsedMS);// gan form cho thoi gian
        }
        else {// tam dung
            timeNow= elapsedMS;
            startTime = System.nanoTime();// thoi gian bat dau tu hien tai
        }
    }

    public void setStartTime(long startTime1){
        startTime= startTime1;
    }
    public String getFormatedTime(){return formatedTime;}
    private String formatTime(long millis) {
        String formatedTime;

        String minuteFormat = "";
        int minutes = (int) (millis / 60000);
        if (minutes >= 1) {
            millis -= minutes * 60000;
            if (minutes < 10) {
                minuteFormat = "0" + minutes;
            } else {
                minuteFormat = "" + minutes;
            }
        } else
            minuteFormat = "00";
//		minuteFormat+=":";

        String secondFormat = "";
        int seconds = (int) (millis / 1000);
        if (seconds >= 1) {
            millis -= seconds * 1000;
            if (seconds < 10) {
                secondFormat = "0" + seconds;
            } else {
                secondFormat = "" + seconds;
            }
        } else
            secondFormat = "00";

        String milliFormat;
        if (millis > 99) {
            milliFormat = "" + millis;
        } else if (millis > 9) {
            milliFormat = "0" + millis;
        } else {
            milliFormat = "00" + millis;
        }

        formatedTime = minuteFormat + ":" + secondFormat;
        return formatedTime;

    }
}

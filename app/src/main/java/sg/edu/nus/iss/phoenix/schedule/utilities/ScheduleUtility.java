package sg.edu.nus.iss.phoenix.schedule.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleList;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 * Created by rahul on 9/23/2017.
 */

/* Use following logic for date
    Date date; // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
// etc.*/

public class ScheduleUtility {

    public static AnnualScheduleList prepareLists(List<ProgramSlot> iListProgramSlot){
        Calendar calendar = Calendar.getInstance();
        AnnualScheduleList annSchedules = new AnnualScheduleList();

        int size = iListProgramSlot.size();
        for (int i = 0; i < size; ++i){
            ProgramSlot currProgramSlot = iListProgramSlot.get(i) ;
            Date currPSDate = currProgramSlot.getDateOfProgram();
            calendar.setTime(currPSDate);
            int year = calendar.get(Calendar.YEAR);
            int week = calendar.get(Calendar.WEEK_OF_YEAR);

            AnnualSchedule currAnnualSchedule = annSchedules.getYearlySchedule(year);
            WeeklySchedule currWeeklySchedule = currAnnualSchedule.getWeeklySchedule(week);
            currWeeklySchedule.addProgramSlot(currProgramSlot);

        }
        return annSchedules;
    }

    public static boolean isProgramSlotOverlap(AnnualScheduleList iAnnualScheduleList, ProgramSlot iProgramSlot){
        boolean isAssigned = false;
        List<AnnualSchedule> annualSchedules = iAnnualScheduleList.retrieveAllAnnualSchedules();
        for (int i = 0; i < annualSchedules.size(); i++) {
            AnnualSchedule annSch = annualSchedules.get(i);
            if (isProgramSlotOverlap(annSch, iProgramSlot)) {
                isAssigned = true;
                break;
            }
        }
        return isAssigned;
    }

    public static boolean isProgramSlotOverlap(AnnualSchedule iAnnualSchedule, ProgramSlot iProgramSlot){
        boolean isAssigned = false;
        List<WeeklySchedule> weeks = iAnnualSchedule.retrieveAllWeeklySchedules();
        int size2 = weeks.size();
        for ( int i = 0; i < size2; ++i) {
            WeeklySchedule ws = weeks.get(i);
            if (isProgramSlotOverlap(ws, iProgramSlot)) {
                isAssigned = true;
                break;
            }
        }
        return isAssigned;
    }

    public static boolean isProgramSlotOverlap(WeeklySchedule iWeeklySchedule, ProgramSlot iProgramSlot){
        boolean isAssigned = false;
        int size = iWeeklySchedule.retrieveAllProgramSlot().size();
        for ( int i = 0; i <size; ++i){
            ProgramSlot currProgramSlot = iWeeklySchedule.retrieveAllProgramSlot().get(i);
            if ( isProgramSlotOverlap(currProgramSlot, iProgramSlot)){
                isAssigned = true;
                break;
            }
        }
        return isAssigned;
    }

    public static boolean isProgramSlotOverlap(ProgramSlot iSlot, ProgramSlot iProgramSlot){

        java.sql.Date PSDate = iProgramSlot.getDateOfProgram();
        int PSStartTime = iProgramSlot.getStartTime().intValue();
        int PSDuration = iProgramSlot.getDuration().intValue();
        Calendar cal = Calendar.getInstance();

        cal.setTime(PSDate); // iPSDate  is start date
        cal.add(Calendar.SECOND, PSStartTime); // Add start time to the day: Start time
        Date psDateStart = cal.getTime();
        cal.add(Calendar.SECOND, PSDuration); // Add duration to the start time: End time
        Date psDateEnd = cal.getTime();

        java.sql.Date dateStart = iSlot.getDateOfProgram();
        cal.setTime(dateStart);
        cal.add(Calendar.SECOND, iSlot.getStartTime().intValue());
        Date slotDateStart = cal.getTime();
        cal.add(Calendar.SECOND,iSlot.getDuration().intValue());
        Date slotDateEnd = cal.getTime();
        if ( overlap(slotDateStart, slotDateEnd, psDateStart, psDateEnd)){
            return true;
        }
        return false;
    }

    private static boolean overlap(Date start1, Date end1, Date start2, Date end2){
        return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
    }

    public static Integer parseDuration(String iDurationString){
        if ( iDurationString.length() == 0){
            return 0;
        }
        String[] data = iDurationString.split(":|\\+");

        int hours  = Integer.parseInt(data[0]);
        int minutes = Integer.parseInt(data[1]);
        int seconds = Integer.parseInt(data[2]);
        return (seconds + 60 * minutes + 3600 * hours);
    }

    public static String parseDuration(Integer iDurationInt){
        int hour = iDurationInt.intValue()/3600;
        Integer hr = new Integer(hour);
        int rem = iDurationInt.intValue()%3600;
        Integer min = new Integer(rem/60);
        Integer sec = new Integer(rem % 60);

        String strHour = "", strMinute = "", strSecond = "";
        if( hr < 10){strHour = "0"+hr.toString();}else{strHour = hr.toString();}
        if( min < 10){strMinute = "0"+min.toString();}else{strMinute = min.toString();}
        if( sec < 10){strSecond = "0"+sec.toString();}else{strSecond = sec.toString();}
        return (strHour+":"+strMinute+":"+strSecond);
    }

    public static boolean validateDuration(String iDurationString){
        if ( iDurationString.length() == 0){
            return false;
        }
        String[] data = iDurationString.split(":|\\+");

        int hours  = Integer.parseInt(data[0]);
        int minutes = Integer.parseInt(data[1]);
        int seconds = Integer.parseInt(data[2]);
        if ( hours >= 24 || minutes >=60 || seconds >=60){
            return false;
        }
        return true;
    }

    // iTime is start time
    public static boolean validateTime(String iDate, Integer iTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        boolean status = false;
        Date date = new Date();
        try {
            date = sdf.parse(iDate);
        }
        catch(Exception e){
            //Failed
            return status;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, iTime);
        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        if ( currentDate.after(date)){
            return status;
        }

        Date startTime = new Date(cal.getTime().getTime());
        Date currUtilDateTime = new Date(Calendar.getInstance().getTime().getTime());

        if ( startTime.after(currUtilDateTime)){
            status = true;
        }
        return status;
    }
}

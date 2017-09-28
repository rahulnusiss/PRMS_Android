package sg.edu.nus.iss.phoenix.schedule.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        Date PSDate = iProgramSlot.getStartTime();
        int PSDuration = iProgramSlot.getDuration().intValue();
        Calendar cal = Calendar.getInstance();

        cal.setTime(PSDate); // iPSDate  is start date
        cal.add(Calendar.SECOND, PSDuration);
        Date psDateEnd = cal.getTime();

        Date dateStart = iSlot.getStartTime();
        cal.setTime(dateStart);
        cal.add(Calendar.SECOND,iSlot.getDuration().intValue());
        Date dateEnd = cal.getTime();
        if ( overlap(dateStart, dateEnd, PSDate, psDateEnd)){
            return true;
        }
        return false;
    }

    private static boolean overlap(Date start1, Date end1, Date start2, Date end2){
        return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
    }

    public static Integer parseDuration(String iDurationString){
        String[] data = iDurationString.split(":");

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

        return (hr.toString()+":"+min.toString()+":"+sec.toString());
    }

}

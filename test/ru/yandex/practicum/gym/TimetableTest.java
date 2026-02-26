package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.gym.enums.Age;
import ru.yandex.practicum.gym.enums.DayOfWeek;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        TimeOfDay timeOfDay1300 = new TimeOfDay(13, 0);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, timeOfDay1300);
        timetable.addNewTrainingSession(singleTrainingSession);


        //Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay1300).size());

        //Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        TimeOfDay timeOfDay1000 = new TimeOfDay(10, 0);
        TimeOfDay timeOfDay1300 = new TimeOfDay(13, 0);
        TimeOfDay timeOfDay2000 = new TimeOfDay(20, 0);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, timeOfDay2000);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, timeOfDay1300);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, timeOfDay1300);
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, timeOfDay1000);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);


        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay1300).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTrainings = (TreeMap<TimeOfDay, List<TrainingSession>>) timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdayTrainings.size());

        int i = 0;
        for (TimeOfDay key : thursdayTrainings.navigableKeySet()){
            switch (i){
                case 0:
                    assertEquals(timeOfDay1300, key);
                    break;
                case 1:
                    assertEquals(timeOfDay2000, key);
                    break;
            }
            i++;
        }

        // Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        TimeOfDay timeOfDay1300 = new TimeOfDay(13, 0);
        TimeOfDay timeOfDay1400 = new TimeOfDay(14, 0);
        TimeOfDay timeOfDay2000 = new TimeOfDay(20, 0);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, timeOfDay1300));

        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, timeOfDay2000));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, timeOfDay2000));


        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay1300).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay1400).size());

        //Проверить, что за понедельник в 20:00 вернулось два занятия
        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay2000).size());
    }

    @Test
    void testGetCountByCoaches(){
        Timetable timetable = new Timetable();

        TimeOfDay timeOfDay1000 = new TimeOfDay(10, 0);
        TimeOfDay timeOfDay1300 = new TimeOfDay(13, 0);
        TimeOfDay timeOfDay2000 = new TimeOfDay(20, 0);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, timeOfDay2000);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, timeOfDay1300);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.THURSDAY, timeOfDay1300);
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.SATURDAY, timeOfDay1000);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);


        //Проверка
        List<CounterOfTrainings> coachWorkCounterList = timetable.getCountByCoaches();
        assertNotNull(coachWorkCounterList);
        assertEquals(3, coachWorkCounterList.get(0).getCount());
        assertEquals(1, coachWorkCounterList.get(1).getCount());
    }
}

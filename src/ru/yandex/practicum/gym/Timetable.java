package ru.yandex.practicum.gym;

import ru.yandex.practicum.gym.enums.DayOfWeek;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    private HashMap<Coach, Integer> coachWorkCounter = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        if (trainingSession == null) {
            return;
        }

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeOfDayTable = timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        ArrayList<TrainingSession> trainingSessionList = timeOfDayTable.getOrDefault(timeOfDay, new ArrayList<>());
        if (!trainingSessionList.contains(trainingSession)) {
            trainingSessionList.add(trainingSession);
        }
        timeOfDayTable.put(timeOfDay, trainingSessionList);
        timetable.put(dayOfWeek, timeOfDayTable);

        //обновляем счетчик занятий у тренера
        coachWorkCounter.put(trainingSession.getCoach(), coachWorkCounter.getOrDefault(trainingSession.getCoach(), 0) + 1);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть O(log(n))
        if (timetable.get(dayOfWeek) == null) {
            return null;
        }
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        ArrayList<CounterOfTrainings> coachWorkCounterList = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachWorkCounter.entrySet()) {
            coachWorkCounterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        coachWorkCounterList.sort(getComparatorCoachWorkCounter());

        return coachWorkCounterList;
    }

    private Comparator<CounterOfTrainings> getComparatorCoachWorkCounter() {
        return new Comparator<>() {
            @Override
            public int compare(CounterOfTrainings counter1, CounterOfTrainings counter2) {
                return - (counter1.getCount() - counter2.getCount());
            }
        };
    }
}

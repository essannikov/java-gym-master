package ru.yandex.practicum.gym;

import ru.yandex.practicum.gym.enums.DayOfWeek;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;
    private Map<Coach, Integer> coachWorkCounter;

    public Timetable() {
        timetable = new HashMap<>();
        for (DayOfWeek dayOfWeek: DayOfWeek.values()) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        coachWorkCounter = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        if (trainingSession == null) {
            return;
        }

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        Map<TimeOfDay, List<TrainingSession>> timeOfDayTable = timetable.get(dayOfWeek);
        List<TrainingSession> trainingSessionList = timeOfDayTable.getOrDefault(timeOfDay, new ArrayList<>());
        if (!trainingSessionList.contains(trainingSession)) {
            trainingSessionList.add(trainingSession);
        }
        timeOfDayTable.put(timeOfDay, trainingSessionList);
        timetable.put(dayOfWeek, timeOfDayTable);

        //обновляем счетчик занятий у тренера
        coachWorkCounter.compute(trainingSession.getCoach(), (k, v) -> (v == null) ? 1 : v + 1);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть O(log(n))
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        List<CounterOfTrainings> coachWorkCounterList = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachWorkCounter.entrySet()) {
            coachWorkCounterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(coachWorkCounterList);

        return coachWorkCounterList;
    }
}
